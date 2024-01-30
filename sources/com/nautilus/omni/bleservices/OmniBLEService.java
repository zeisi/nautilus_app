package com.nautilus.omni.bleservices;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.os.Handler;
import android.util.Log;
import android.util.SparseArray;
import com.nautilus.omni.bleservices.ble.OmniDevice;
import com.nautilus.omni.bleservices.ble.OmniDeviceType;
import com.nautilus.omni.bleservices.ble.OmniGattAttributes;
import com.nautilus.omni.bleservices.ble.ThreadLocker;
import com.nautilus.omni.bleservices.ble.omnitrainer.OmniCommands;
import com.nautilus.omni.bleservices.ble.omnitrainer.OmniConnectionState;
import com.nautilus.omni.bleservices.ble.omnitrainer.OmniConstants;
import com.nautilus.omni.bleservices.ble.omnitrainer.OmniDictionaryKeys;
import com.nautilus.omni.bleservices.ble.omnitrainer.OmniErrorTypes;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class OmniBLEService extends BaseBLEService {
    private static final int OMNI_KEY_CHARACTERISTIC_COUNT = 6;
    private static final int PRODUCT_DATA_DONE_COUNT = 4;
    /* access modifiers changed from: private */
    public static final String TAG = OmniBLEService.class.getSimpleName();
    private static final byte[] omniGetProductDataCmd = {5, 4, OmniConstants.OBTAIN_PRODUCT_DATA_CHKSUM, 0, (byte) OmniCommands.GET_PRODUCT_DATA.getCmdVal()};
    private static final byte[] omniGetStatusCmd = {5, 3, OmniConstants.GET_STATUS_CHKSUM, 0, (byte) OmniCommands.GET_STATUS.getCmdVal()};
    private static final byte[] omniGetSystemDataCmd = {5, 5, OmniConstants.OBTAIN_SYSTEM_DATA_CHKSUM, 0, (byte) OmniCommands.GET_SYSTEM_DATA.getCmdVal()};
    private static final byte[] omniGiveupBLEAccessCmd = {5, 2, OmniConstants.GIVEUP_BLE_ACCESS_CHKSUM, 0, (byte) OmniCommands.GIVEUP_BLE_ACCESS.getCmdVal()};
    private static final byte[] omniRequestBLEAccessCmd = {5, 1, OmniConstants.REQUEST_BLE_ACCESS_CHKSUM, 0, (byte) OmniCommands.REQUEST_BLE_ACCESS.getCmdVal()};
    private static final byte[] resultsClearNVMCmd = {5, 2, OmniConstants.CLEAR_NVM_CHECKSUM, 0, (byte) OmniCommands.CLEAR_NVM_FLAGS.getCmdVal()};
    /* access modifiers changed from: private */
    public final ExecutorService WORKER = Executors.newFixedThreadPool(1);
    private final ScheduledExecutorService WORKERDELAY = Executors.newScheduledThreadPool(1);
    private BluetoothGattCharacteristic ackRecordCharacteristic = null;
    /* access modifiers changed from: private */
    public BluetoothGattCharacteristic commandRecordCharacteristic = null;
    private BluetoothGattCharacteristic dataRecordCharacteristic = null;
    private BluetoothGatt deviceGatt = null;
    private BluetoothGattCharacteristic deviceNameCharacteristic;
    private BluetoothGattCharacteristic eventRecordCharacteristic = null;
    private BluetoothGattCharacteristic fwRevCharacteristic;
    private BluetoothGattCharacteristic hwRevCharacteristic;
    private BLEScanManager mBLEScanManager = null;
    /* access modifiers changed from: private */
    public ThreadLocker mDescriptorWriteLock = new ThreadLocker();
    /* access modifiers changed from: private */
    public boolean mIsAppGettingAcksFromMy16;
    private int mKeyCharacteristicCount = 0;
    /* access modifiers changed from: private */
    public ThreadLocker mReadLock = new ThreadLocker();
    /* access modifiers changed from: private */
    public ThreadLocker mWriteLock = new ThreadLocker();
    private BluetoothGattCharacteristic mfgNameCharacteristic = null;
    private BluetoothGattCharacteristic modelNumberCharacteristic;
    private byte[] omniGetUserDataCmd = {7, 6, OmniConstants.OBTAIN_USER_DATA_CHKSUM, 0, (byte) OmniCommands.GET_USER_DATA.getCmdVal(), OmniConstants.USER_INDEX_DATA_TYPE, 0};
    private byte[] omniGetUserWorkoutDataCmd = {10, 0, 0, 0, (byte) OmniCommands.GET_WORKOUT_DATA.getCmdVal(), OmniConstants.USER_INDEX_DATA_TYPE, 0, OmniConstants.WORKOUT_INDEX_DATA_TYPE, 0, 0};
    private boolean recordReadRequired = false;
    public boolean requestConsoleState;
    private BluetoothGattCharacteristic statusRecordCharacteristic = null;
    private BluetoothGattCharacteristic swRevCharacteristic;
    private BluetoothGattCharacteristic uniqueIDRecordCharacteristic = null;

    public boolean omniInitBLEService(BluetoothAdapter btAdaptor, OmniCallbacks tcCallbackMgr) {
        if (btAdaptor == null || tcCallbackMgr == null) {
            Log.e(TAG, "Invalid BT adaptor or callback handler");
            return false;
        }
        this.clientDelegate = tcCallbackMgr;
        this.mBluetoothAdapter = btAdaptor;
        this.mBLEScanManager = new BLEScanManager(this.mBluetoothAdapter, tcCallbackMgr);
        if (this.mBLEScanManager != null) {
            return true;
        }
        Log.e(TAG, "Failed to initialize the Scan Manager");
        return false;
    }

    private void checkForOmniClientConnectNotification() {
        this.mKeyCharacteristicCount++;
        if (this.mKeyCharacteristicCount == 6 && this.clientDelegate != null) {
            this.clientDelegate.nautilusDeviceConnectionStateChange(OmniConnectionState.CONNECTED_NO_BLE);
        }
    }

    public void onCreate() {
        super.onCreate();
    }

    public void onBLEServicesDiscovered(BluetoothGatt gatt, int status) {
        super.onBLEServicesDiscovered(gatt, status);
        if (status == 0) {
            processDiscoveredServices(gatt);
        }
    }

    public void onBLECharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        super.onBLECharacteristicRead(gatt, characteristic, status);
        this.mReadLock.unlockThread();
        if (status == 0) {
            Log.d(TAG, "Successful read");
            processCharacteristicUpdate(characteristic);
            return;
        }
        Log.d(TAG, "Failed characteristic read, status: " + status + ", characteristic: " + characteristic);
    }

    public void onBLECharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
        super.onBLECharacteristicChanged(gatt, characteristic);
        processCharacteristicUpdate(characteristic);
    }

    public void onBLEDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
        super.onBLEDescriptorWrite(gatt, descriptor, status);
        this.mDescriptorWriteLock.unlockThread();
    }

    public void onBLECharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        super.onBLECharacteristicWrite(gatt, characteristic, status);
        if (!isMY16orMY17Console()) {
            this.mWriteLock.unlockThread();
        }
        if (status == 0) {
            Log.d(TAG, "Successful write");
        } else {
            Log.d(TAG, "Failed characteristic write, status: " + status + ", characteristic: " + characteristic);
        }
    }

    private void processDiscoveredServices(BluetoothGatt gatt) {
        List<BluetoothGattService> gattServices = gatt.getServices();
        if (gattServices != null) {
            this.deviceGatt = gatt;
            for (BluetoothGattService gattService : gattServices) {
                if (this.mBLEDevice != null) {
                    if (gattService.getUuid().equals(UUID.fromString(OmniGattAttributes.MY14_BIKE_ELLIPTICAL_SERVICE))) {
                        Log.d(TAG, "We have an MY14 Bike/Elliptical device");
                        this.mBLEDevice.setDeviceType(OmniDeviceType.MY14_BIKE_ELLIPTICAL);
                    } else if (gattService.getUuid().equals(UUID.fromString(OmniGattAttributes.MY14_TREADMILL_SERVICE))) {
                        Log.d(TAG, "We have an MY14 Treadmill device");
                        this.mBLEDevice.setDeviceType(OmniDeviceType.MY14_TREADMILL);
                    } else if (gattService.getUuid().equals(UUID.fromString(OmniGattAttributes.MY16_BIKE_ELLIPTICAL_SERVICE))) {
                        Log.d(TAG, "We have an MY16 Bike/Elliptical device");
                        this.mBLEDevice.setDeviceType(OmniDeviceType.MY16_BIKE_ELLIPTICAL);
                    } else if (gattService.getUuid().equals(UUID.fromString(OmniGattAttributes.MY16_TREADMILL_SERVICE))) {
                        Log.d(TAG, "We have an MY16 Treadmill device");
                        this.mBLEDevice.setDeviceType(OmniDeviceType.MY16_TREADMILL);
                    } else if (gattService.getUuid().equals(UUID.fromString(OmniGattAttributes.MY16_TREADMILL_INTERNATIONAL_SERVICE))) {
                        Log.d(TAG, "We have an MY16 International Treadmill device");
                        this.mBLEDevice.setDeviceType(OmniDeviceType.MY16_INTERNATIONAL_TREADMILL);
                    } else if (gattService.getUuid().equals(UUID.fromString(OmniGattAttributes.ELLIPTICAL_E628_INTERNATIONAL_SERVICE))) {
                        Log.d(TAG, "We have an MY16 International Elliptical device");
                        this.mBLEDevice.setDeviceType(OmniDeviceType.ELLIPTICAL_E628_INTERNATIONAL);
                    } else if (gattService.getUuid().equals(UUID.fromString(OmniGattAttributes.MY16_RECUMBENT_BIKE_INTERNATIONAL_SERVICE))) {
                        Log.d(TAG, "We have an MY16 International Recumbent Bike device");
                        this.mBLEDevice.setDeviceType(OmniDeviceType.MY16_INTERNATIONAL_RECUMBENT_BIKE);
                    } else if (gattService.getUuid().equals(UUID.fromString(OmniGattAttributes.UPRIGHT_BIKE_UR628_INTERNATIONAL_SERVICE))) {
                        Log.d(TAG, "We have an MY16 International Upright Bike device");
                        this.mBLEDevice.setDeviceType(OmniDeviceType.UPRIGHT_BIKE_UR628_INTERNATIONAL);
                    } else if (gattService.getUuid().equals(UUID.fromString(OmniGattAttributes.MY17_TREADMILL_T618_SERVICE))) {
                        Log.d(TAG, "We have an MY17 T618 Treadmill");
                        this.mBLEDevice.setDeviceType(OmniDeviceType.MY17_TREADMILL_T618);
                    } else if (gattService.getUuid().equals(UUID.fromString(OmniGattAttributes.MY17_ELLIPTICAL_E618_SERVICE))) {
                        Log.d(TAG, "We have an MY17 E618 Elliptical");
                        this.mBLEDevice.setDeviceType(OmniDeviceType.MY17_ELLIPTICAL_E618);
                    } else if (gattService.getUuid().equals(UUID.fromString(OmniGattAttributes.MY17_BIKE_B618_SERVICE))) {
                        Log.d(TAG, "We have an MY17 B618 Upright Bike");
                        this.mBLEDevice.setDeviceType(OmniDeviceType.MY17_BIKE_B618);
                    } else if (gattService.getUuid().equals(UUID.fromString(OmniGattAttributes.MY17_TREADMILL_T616_SERVICE))) {
                        Log.d(TAG, "We have an MY17 T616 Treadmill");
                        this.mBLEDevice.setDeviceType(OmniDeviceType.MY17_TREADMILL_T616);
                    } else if (gattService.getUuid().equals(UUID.fromString(OmniGattAttributes.MY17_ELLIPTICAL_E616_SERVICE))) {
                        Log.d(TAG, "We have an MY17 E616 Elliptical");
                        this.mBLEDevice.setDeviceType(OmniDeviceType.MY17_ELLIPTICAL_E616);
                    } else if (gattService.getUuid().equals(UUID.fromString(OmniGattAttributes.MY17_UPRIGHT_BIKE_UR616_SERVICE))) {
                        Log.d(TAG, "We have an MY17 UR616 Upright bike");
                        this.mBLEDevice.setDeviceType(OmniDeviceType.MY17_UPRIGHT_BIKE_UR616);
                    }
                    for (BluetoothGattCharacteristic gattCharacteristic : gattService.getCharacteristics()) {
                        String uuid = gattCharacteristic.getUuid().toString();
                        int charaProp = gattCharacteristic.getProperties();
                        char c = 65535;
                        switch (uuid.hashCode()) {
                            case -1574446325:
                                if (uuid.equals(OmniGattAttributes.OMNI_SOFTWARE_REV)) {
                                    c = 9;
                                    break;
                                }
                                break;
                            case -881344628:
                                if (uuid.equals(OmniGattAttributes.OMNI_MANUFACTURER_NAME)) {
                                    c = 11;
                                    break;
                                }
                                break;
                            case -874909087:
                                if (uuid.equals(OmniGattAttributes.OMNI_ACK_RECORD)) {
                                    c = 1;
                                    break;
                                }
                                break;
                            case -139284131:
                                if (uuid.equals(OmniGattAttributes.OMNI_COMMAND_RECORD)) {
                                    c = 0;
                                    break;
                                }
                                break;
                            case -51885817:
                                if (uuid.equals(OmniGattAttributes.OMNI_MODEL_NUMBER)) {
                                    c = 7;
                                    break;
                                }
                                break;
                            case 107283770:
                                if (uuid.equals(OmniGattAttributes.OMNI_STATUS_RECORD)) {
                                    c = 4;
                                    break;
                                }
                                break;
                            case 263785380:
                                if (uuid.equals(OmniGattAttributes.OMNI_EVENT_RECORD)) {
                                    c = 3;
                                    break;
                                }
                                break;
                            case 1308038563:
                                if (uuid.equals(OmniGattAttributes.OMNI_DATA_RECORD)) {
                                    c = 2;
                                    break;
                                }
                                break;
                            case 1334317577:
                                if (uuid.equals(OmniGattAttributes.OMNI_FIRMWARE_REV)) {
                                    c = 8;
                                    break;
                                }
                                break;
                            case 1448042437:
                                if (uuid.equals(OmniGattAttributes.OMNI_DEVICE_NAME)) {
                                    c = 6;
                                    break;
                                }
                                break;
                            case 1805678160:
                                if (uuid.equals(OmniGattAttributes.OMNI_UNIQUE_ID)) {
                                    c = 5;
                                    break;
                                }
                                break;
                            case 2027419274:
                                if (uuid.equals(OmniGattAttributes.OMNI_HARDWARE_REV)) {
                                    c = 10;
                                    break;
                                }
                                break;
                        }
                        switch (c) {
                            case 0:
                                Log.d(TAG, "Found OmniData Command Record Characteristic");
                                this.commandRecordCharacteristic = gattCharacteristic;
                                checkForOmniClientConnectNotification();
                                break;
                            case 1:
                                Log.d(TAG, "Found OmniData ACK Record Characteristic");
                                this.ackRecordCharacteristic = gattCharacteristic;
                                if ((charaProp | 16) > 0) {
                                    Log.d(TAG, "Setting up ACK record notification");
                                    setupNotification(gattCharacteristic);
                                }
                                checkForOmniClientConnectNotification();
                                break;
                            case 2:
                                Log.d(TAG, "Found OmniData Data Record Characteristic");
                                this.dataRecordCharacteristic = gattCharacteristic;
                                checkForOmniClientConnectNotification();
                                break;
                            case 3:
                                Log.d(TAG, "Found OmniData Event Record Characteristic");
                                this.eventRecordCharacteristic = gattCharacteristic;
                                checkForOmniClientConnectNotification();
                                break;
                            case 4:
                                Log.d(TAG, "Found OmniData Status Record Characteristic");
                                this.statusRecordCharacteristic = gattCharacteristic;
                                checkForOmniClientConnectNotification();
                                break;
                            case 5:
                                Log.d(TAG, "Found OmniData Unique ID Record Characteristic");
                                this.uniqueIDRecordCharacteristic = gattCharacteristic;
                                readCharacteristic(gatt, gattCharacteristic);
                                checkForOmniClientConnectNotification();
                                break;
                            case 6:
                                Log.d(TAG, "Found Device Name Characteristic");
                                this.deviceNameCharacteristic = gattCharacteristic;
                                break;
                            case 7:
                                Log.d(TAG, "Found Model Num Characteristic");
                                this.modelNumberCharacteristic = gattCharacteristic;
                                break;
                            case 8:
                                Log.d(TAG, "Found FW Rev Characteristic");
                                this.fwRevCharacteristic = gattCharacteristic;
                                break;
                            case 9:
                                Log.d(TAG, "Found SW Rev Characteristic");
                                this.swRevCharacteristic = gattCharacteristic;
                                break;
                            case 10:
                                Log.d(TAG, "Found HW Rev Characteristic");
                                this.hwRevCharacteristic = gattCharacteristic;
                                break;
                            case 11:
                                Log.d(TAG, "Found MFG Name Characteristic");
                                this.mBLEDevice.setManufacturerName(OmniDevice.mfgNameStr);
                                this.mfgNameCharacteristic = gattCharacteristic;
                                break;
                        }
                    }
                } else {
                    Log.d(TAG, "Null BLE device in process services");
                    return;
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void bleWasReleasedNotification() {
        if (this.mBLEDevice == null) {
            Log.d(TAG, "Null BLE device in BLE release notification");
            return;
        }
        this.mBLEDevice.setHaveBLE(false);
        this.mBLEDevice.setDeviceStatus(OmniConnectionState.CONNECTED_NO_BLE);
        Log.d(TAG, "BLE Successfully given up");
        if (this.clientDelegate != null) {
            this.clientDelegate.nautilusDeviceConnectionStateChange(this.mBLEDevice.getDeviceStatus());
        }
    }

    /* access modifiers changed from: private */
    public void bleWasAcquiredNotification() {
        if (this.mBLEDevice == null) {
            Log.d(TAG, "Null BLE device in BLE acquired notification");
            return;
        }
        this.mBLEDevice.setHaveBLE(true);
        this.mBLEDevice.setDeviceStatus(OmniConnectionState.CONNECTED_WITH_BLE);
        if (this.clientDelegate != null) {
            this.clientDelegate.nautilusDeviceConnectionStateChange(this.mBLEDevice.getDeviceStatus());
        }
    }

    private String extractString(ByteBuffer recordData, int currentOffset, int stringLen) {
        byte[] tmpStr = new byte[stringLen];
        int i = 0;
        int currentOffset2 = currentOffset;
        while (i < stringLen) {
            tmpStr[i] = recordData.get(currentOffset2);
            i++;
            currentOffset2++;
        }
        return new String(tmpStr);
    }

    private void processProductData(ByteBuffer recordData) {
        String mfgName = new String("Unknown");
        String modelName = new String("Unknown");
        String fwVersion = new String("Unknown");
        short productVar = 0;
        short consumerVar = 0;
        recordData.order(ByteOrder.LITTLE_ENDIAN);
        int lenOfRecordData = recordData.get(0);
        int currentOffset = 5;
        while (currentOffset < lenOfRecordData) {
            int currentOffset2 = currentOffset + 1;
            switch (recordData.get(currentOffset)) {
                case 11:
                    Log.d(TAG, "Got product consumer varient");
                    consumerVar = recordData.getShort(currentOffset2);
                    currentOffset = currentOffset2 + 2;
                    break;
                case 12:
                    Log.d(TAG, "Got product HW Varient varient");
                    productVar = recordData.getShort(currentOffset2);
                    currentOffset = currentOffset2 + 2;
                    break;
                case 13:
                    Log.d(TAG, "Got product MFG Name varient");
                    int currentOffset3 = currentOffset2 + 1;
                    int stringLen = recordData.get(currentOffset2);
                    mfgName = extractString(recordData, currentOffset3, stringLen);
                    currentOffset = currentOffset3 + stringLen;
                    break;
                case 14:
                    Log.d(TAG, "Got product Model Name varient");
                    int currentOffset4 = currentOffset2 + 1;
                    int stringLen2 = recordData.get(currentOffset2);
                    modelName = extractString(recordData, currentOffset4, stringLen2);
                    currentOffset = currentOffset4 + stringLen2;
                    break;
                case 20:
                    Log.d(TAG, "Got product FW Version varient");
                    fwVersion = String.format("%c%02d.%02d.%02d", new Object[]{Byte.valueOf(recordData.get(currentOffset2 + 1)), Byte.valueOf(recordData.get(currentOffset2)), Byte.valueOf(recordData.get(currentOffset2 + 3)), Byte.valueOf(recordData.get(currentOffset2 + 2))});
                    currentOffset = currentOffset2 + 4;
                    break;
                default:
                    Log.d(TAG, "Unhandled type in process product data");
                    currentOffset = currentOffset2;
                    break;
            }
        }
        SparseArray productInfo = new SparseArray(5);
        productInfo.append(200, Short.valueOf(consumerVar));
        productInfo.append(OmniDictionaryKeys.ProductHWVarient, Short.valueOf(productVar));
        productInfo.append(OmniDictionaryKeys.ProductMfgName, mfgName);
        productInfo.append(OmniDictionaryKeys.ProductModelName, modelName);
        productInfo.append(OmniDictionaryKeys.ProductFWVersion, fwVersion);
        Log.d(TAG, "**********************************");
        Log.d(TAG, "Product Data: " + productInfo);
        Log.d(TAG, "**********************************");
        if (this.clientDelegate != null) {
            this.clientDelegate.nautilusDeviceProductData(productInfo);
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v6, resolved type: byte} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void processSystemData(java.nio.ByteBuffer r9) {
        /*
            r8 = this;
            r4 = 0
            r5 = 0
            r0 = 0
            r1 = 5
            r6 = 0
            byte r3 = r9.get(r6)
            r2 = r1
        L_0x000a:
            if (r2 >= r3) goto L_0x003a
            int r1 = r2 + 1
            byte r0 = r9.get(r2)
            switch(r0) {
                case 36: goto L_0x001e;
                case 37: goto L_0x002c;
                default: goto L_0x0015;
            }
        L_0x0015:
            java.lang.String r6 = TAG
            java.lang.String r7 = "Unhandled type in process system data"
            android.util.Log.d(r6, r7)
            r2 = r1
            goto L_0x000a
        L_0x001e:
            java.lang.String r6 = TAG
            java.lang.String r7 = "Got max users"
            android.util.Log.d(r6, r7)
            int r2 = r1 + 1
            byte r4 = r9.get(r1)
            goto L_0x000a
        L_0x002c:
            java.lang.String r6 = TAG
            java.lang.String r7 = "Got number of programs"
            android.util.Log.d(r6, r7)
            int r2 = r1 + 1
            byte r5 = r9.get(r1)
            goto L_0x000a
        L_0x003a:
            com.nautilus.omni.bleservices.OmniCallbacks r6 = r8.clientDelegate
            if (r6 == 0) goto L_0x0043
            com.nautilus.omni.bleservices.OmniCallbacks r6 = r8.clientDelegate
            r6.nautilusDeviceSystemData(r4, r5)
        L_0x0043:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.nautilus.omni.bleservices.OmniBLEService.processSystemData(java.nio.ByteBuffer):void");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v7, resolved type: byte} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void processGetStatusData(java.nio.ByteBuffer r15) {
        /*
            r14 = this;
            r9 = 0
            r6 = 0
            r5 = 0
            r7 = 0
            r8 = 0
            r10 = 0
            java.nio.ByteOrder r11 = java.nio.ByteOrder.LITTLE_ENDIAN
            r15.order(r11)
            r1 = 5
            r11 = 0
            byte r4 = r15.get(r11)
            r2 = r1
        L_0x0012:
            if (r2 >= r4) goto L_0x0068
            int r1 = r2 + 1
            byte r0 = r15.get(r2)
            switch(r0) {
                case 25: goto L_0x002f;
                case 40: goto L_0x0028;
                case 207: goto L_0x0036;
                default: goto L_0x001d;
            }
        L_0x001d:
            java.lang.String r11 = TAG
            java.lang.String r12 = "Unhandled type in process get status data"
            android.util.Log.d(r11, r12)
            int r1 = r1 + 1
            r2 = r1
            goto L_0x0012
        L_0x0028:
            int r2 = r1 + 1
            byte r9 = r15.get(r1)
            goto L_0x0012
        L_0x002f:
            int r2 = r1 + 1
            byte r6 = r15.get(r1)
            goto L_0x0012
        L_0x0036:
            int r2 = r1 + 1
            byte r5 = r15.get(r1)
            boolean r11 = r14.isMY17Console()
            if (r11 == 0) goto L_0x0052
            r11 = r5 & 2
            if (r11 <= 0) goto L_0x0047
            r8 = 1
        L_0x0047:
            r11 = r5 & 4
            if (r11 <= 0) goto L_0x004c
            r7 = 1
        L_0x004c:
            r11 = r5 & 8
            if (r11 <= 0) goto L_0x0012
            r10 = 1
            goto L_0x0012
        L_0x0052:
            boolean r11 = r14.isMY16Console()
            if (r11 == 0) goto L_0x0012
            r11 = r5 & 4
            if (r11 <= 0) goto L_0x005d
            r8 = 1
        L_0x005d:
            r11 = r5 & 16
            if (r11 <= 0) goto L_0x0062
            r7 = 1
        L_0x0062:
            r11 = r5 & 8
            if (r11 <= 0) goto L_0x0012
            r10 = 1
            goto L_0x0012
        L_0x0068:
            android.util.SparseArray r3 = new android.util.SparseArray
            r11 = 5
            r3.<init>(r11)
            r11 = 220(0xdc, float:3.08E-43)
            java.lang.Integer r12 = java.lang.Integer.valueOf(r9)
            r3.append(r11, r12)
            r11 = 301(0x12d, float:4.22E-43)
            java.lang.Integer r12 = java.lang.Integer.valueOf(r6)
            r3.append(r11, r12)
            r11 = 350(0x15e, float:4.9E-43)
            java.lang.Integer r12 = java.lang.Integer.valueOf(r8)
            r3.append(r11, r12)
            r11 = 351(0x15f, float:4.92E-43)
            java.lang.Integer r12 = java.lang.Integer.valueOf(r7)
            r3.append(r11, r12)
            r11 = 352(0x160, float:4.93E-43)
            java.lang.Integer r12 = java.lang.Integer.valueOf(r10)
            r3.append(r11, r12)
            java.lang.String r11 = TAG
            java.lang.String r12 = "**********************************"
            android.util.Log.d(r11, r12)
            java.lang.String r11 = TAG
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            java.lang.String r13 = "Get Status Data: "
            java.lang.StringBuilder r12 = r12.append(r13)
            java.lang.StringBuilder r12 = r12.append(r3)
            java.lang.String r12 = r12.toString()
            android.util.Log.d(r11, r12)
            java.lang.String r11 = TAG
            java.lang.String r12 = "**********************************"
            android.util.Log.d(r11, r12)
            com.nautilus.omni.bleservices.OmniCallbacks r11 = r14.clientDelegate
            if (r11 == 0) goto L_0x00ca
            com.nautilus.omni.bleservices.OmniCallbacks r11 = r14.clientDelegate
            r11.nautilusDeviceStatus(r3)
        L_0x00ca:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.nautilus.omni.bleservices.OmniBLEService.processGetStatusData(java.nio.ByteBuffer):void");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v13, resolved type: byte} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void processUserData(java.nio.ByteBuffer r21) {
        /*
            r20 = this;
            r11 = 0
            r14 = 0
            r15 = 0
            r16 = 0
            r8 = 0
            r9 = 0
            r10 = 0
            java.lang.String r13 = "No Name"
            java.nio.ByteOrder r17 = java.nio.ByteOrder.LITTLE_ENDIAN
            r0 = r21
            r1 = r17
            r0.order(r1)
            r3 = 5
            r17 = 0
            r0 = r21
            r1 = r17
            byte r5 = r0.get(r1)
            r4 = r3
        L_0x001f:
            if (r4 >= r5) goto L_0x008e
            int r3 = r4 + 1
            r0 = r21
            byte r2 = r0.get(r4)
            switch(r2) {
                case 40: goto L_0x0037;
                case 41: goto L_0x0053;
                case 42: goto L_0x005d;
                case 43: goto L_0x0066;
                case 44: goto L_0x0040;
                case 45: goto L_0x0049;
                case 46: goto L_0x006f;
                case 47: goto L_0x0079;
                default: goto L_0x002c;
            }
        L_0x002c:
            java.lang.String r17 = TAG
            java.lang.String r18 = "Unhandled type in process user data"
            android.util.Log.d(r17, r18)
            int r3 = r3 + 1
            r4 = r3
            goto L_0x001f
        L_0x0037:
            int r4 = r3 + 1
            r0 = r21
            byte r11 = r0.get(r3)
            goto L_0x001f
        L_0x0040:
            int r4 = r3 + 1
            r0 = r21
            byte r14 = r0.get(r3)
            goto L_0x001f
        L_0x0049:
            r0 = r21
            short r15 = r0.getShort(r3)
            int r3 = r3 + 2
            r4 = r3
            goto L_0x001f
        L_0x0053:
            r0 = r21
            short r16 = r0.getShort(r3)
            int r3 = r3 + 2
            r4 = r3
            goto L_0x001f
        L_0x005d:
            int r4 = r3 + 1
            r0 = r21
            byte r8 = r0.get(r3)
            goto L_0x001f
        L_0x0066:
            int r4 = r3 + 1
            r0 = r21
            byte r9 = r0.get(r3)
            goto L_0x001f
        L_0x006f:
            r0 = r21
            short r10 = r0.getShort(r3)
            int r3 = r3 + 2
            r4 = r3
            goto L_0x001f
        L_0x0079:
            int r4 = r3 + 1
            r0 = r21
            byte r7 = r0.get(r3)
            byte[] r6 = r21.array()
            java.lang.String r13 = new java.lang.String
            r13.<init>(r6, r4, r7)
            int r3 = r4 + r7
            r4 = r3
            goto L_0x001f
        L_0x008e:
            android.util.SparseArray r12 = new android.util.SparseArray
            r17 = 8
            r0 = r17
            r12.<init>(r0)
            r17 = 220(0xdc, float:3.08E-43)
            java.lang.Integer r18 = java.lang.Integer.valueOf(r11)
            r0 = r17
            r1 = r18
            r12.append(r0, r1)
            r17 = 223(0xdf, float:3.12E-43)
            java.lang.Integer r18 = java.lang.Integer.valueOf(r14)
            r0 = r17
            r1 = r18
            r12.append(r0, r1)
            r17 = 224(0xe0, float:3.14E-43)
            java.lang.Integer r18 = java.lang.Integer.valueOf(r15)
            r0 = r17
            r1 = r18
            r12.append(r0, r1)
            r17 = 225(0xe1, float:3.15E-43)
            java.lang.Integer r18 = java.lang.Integer.valueOf(r16)
            r0 = r17
            r1 = r18
            r12.append(r0, r1)
            r17 = 221(0xdd, float:3.1E-43)
            java.lang.Integer r18 = java.lang.Integer.valueOf(r8)
            r0 = r17
            r1 = r18
            r12.append(r0, r1)
            r17 = 222(0xde, float:3.11E-43)
            java.lang.Integer r18 = java.lang.Integer.valueOf(r9)
            r0 = r17
            r1 = r18
            r12.append(r0, r1)
            r17 = 230(0xe6, float:3.22E-43)
            java.lang.Integer r18 = java.lang.Integer.valueOf(r10)
            r0 = r17
            r1 = r18
            r12.append(r0, r1)
            r17 = 231(0xe7, float:3.24E-43)
            r0 = r17
            r12.append(r0, r13)
            java.lang.String r17 = TAG
            java.lang.String r18 = "**********************************"
            android.util.Log.d(r17, r18)
            java.lang.String r17 = TAG
            java.lang.StringBuilder r18 = new java.lang.StringBuilder
            r18.<init>()
            java.lang.String r19 = "User Data: "
            java.lang.StringBuilder r18 = r18.append(r19)
            r0 = r18
            java.lang.StringBuilder r18 = r0.append(r12)
            java.lang.String r18 = r18.toString()
            android.util.Log.d(r17, r18)
            java.lang.String r17 = TAG
            java.lang.String r18 = "**********************************"
            android.util.Log.d(r17, r18)
            r0 = r20
            com.nautilus.omni.bleservices.OmniCallbacks r0 = r0.clientDelegate
            r17 = r0
            if (r17 == 0) goto L_0x0134
            r0 = r20
            com.nautilus.omni.bleservices.OmniCallbacks r0 = r0.clientDelegate
            r17 = r0
            r0 = r17
            r0.nautilusDeviceUserData(r12)
        L_0x0134:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.nautilus.omni.bleservices.OmniBLEService.processUserData(java.nio.ByteBuffer):void");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v28, resolved type: byte} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void processWorkoutData(java.nio.ByteBuffer r34) {
        /*
            r33 = this;
            r28 = 0
            r27 = 0
            r26 = 0
            r14 = 0
            r17 = 0
            r19 = 0
            r15 = 0
            r16 = 0
            r18 = 0
            r21 = 0
            r10 = 0
            r20 = 0
            r2 = 0
            r3 = 0
            r8 = 0
            r7 = 0
            r6 = 0
            r9 = 0
            r4 = 0
            r5 = 0
            r24 = 0
            r22 = 0
            r23 = 0
            java.nio.ByteOrder r30 = java.nio.ByteOrder.LITTLE_ENDIAN
            r0 = r34
            r1 = r30
            r0.order(r1)
            r12 = 5
            r30 = 0
            r0 = r34
            r1 = r30
            byte r25 = r0.get(r1)
            r13 = r12
        L_0x0038:
            r0 = r25
            if (r13 >= r0) goto L_0x0197
            int r12 = r13 + 1
            r0 = r34
            byte r30 = r0.get(r13)
            r0 = r30
            r11 = r0 & 255(0xff, float:3.57E-43)
            switch(r11) {
                case 25: goto L_0x006e;
                case 40: goto L_0x0056;
                case 51: goto L_0x005f;
                case 52: goto L_0x007d;
                case 54: goto L_0x009d;
                case 55: goto L_0x00c8;
                case 60: goto L_0x00b8;
                case 61: goto L_0x00cd;
                case 62: goto L_0x00dd;
                case 63: goto L_0x016d;
                case 64: goto L_0x017d;
                case 70: goto L_0x00ed;
                case 71: goto L_0x00f9;
                case 72: goto L_0x0109;
                case 75: goto L_0x0115;
                case 76: goto L_0x0121;
                case 77: goto L_0x0131;
                case 78: goto L_0x0141;
                case 79: goto L_0x014d;
                case 80: goto L_0x015d;
                case 122: goto L_0x018d;
                case 207: goto L_0x0192;
                default: goto L_0x004b;
            }
        L_0x004b:
            java.lang.String r30 = TAG
            java.lang.String r31 = "Unhandled type in process system data"
            android.util.Log.d(r30, r31)
            int r12 = r12 + 1
            r13 = r12
            goto L_0x0038
        L_0x0056:
            int r13 = r12 + 1
            r0 = r34
            byte r28 = r0.get(r12)
            goto L_0x0038
        L_0x005f:
            r0 = r34
            short r27 = r0.getShort(r12)
            r30 = 65535(0xffff, float:9.1834E-41)
            r27 = r27 & r30
            int r12 = r12 + 2
            r13 = r12
            goto L_0x0038
        L_0x006e:
            int r13 = r12 + 1
            r0 = r34
            byte r26 = r0.get(r12)
            r0 = r26
            r0 = r0 & 255(0xff, float:3.57E-43)
            r26 = r0
            goto L_0x0038
        L_0x007d:
            int r13 = r12 + 1
            r0 = r34
            byte r14 = r0.get(r12)
            int r12 = r13 + 1
            r0 = r34
            byte r17 = r0.get(r13)
            r0 = r34
            short r19 = r0.getShort(r12)
            r0 = r26
            r0 = r0 & 255(0xff, float:3.57E-43)
            r26 = r0
            int r12 = r12 + 2
            r13 = r12
            goto L_0x0038
        L_0x009d:
            int r12 = r12 + 1
            int r13 = r12 + 1
            r0 = r34
            byte r18 = r0.get(r12)
            int r12 = r13 + 1
            r0 = r34
            byte r16 = r0.get(r13)
            int r13 = r12 + 1
            r0 = r34
            byte r15 = r0.get(r12)
            goto L_0x0038
        L_0x00b8:
            r0 = r34
            short r21 = r0.getShort(r12)
            r30 = 65535(0xffff, float:9.1834E-41)
            r21 = r21 & r30
            int r12 = r12 + 2
            r13 = r12
            goto L_0x0038
        L_0x00c8:
            int r12 = r12 + 4
            r13 = r12
            goto L_0x0038
        L_0x00cd:
            r0 = r34
            short r10 = r0.getShort(r12)
            r30 = 65535(0xffff, float:9.1834E-41)
            r10 = r10 & r30
            int r12 = r12 + 2
            r13 = r12
            goto L_0x0038
        L_0x00dd:
            r0 = r34
            short r20 = r0.getShort(r12)
            r30 = 65535(0xffff, float:9.1834E-41)
            r20 = r20 & r30
            int r12 = r12 + 2
            r13 = r12
            goto L_0x0038
        L_0x00ed:
            int r13 = r12 + 1
            r0 = r34
            byte r2 = r0.get(r12)
            r2 = r2 & 255(0xff, float:3.57E-43)
            goto L_0x0038
        L_0x00f9:
            r0 = r34
            short r3 = r0.getShort(r12)
            r30 = 65535(0xffff, float:9.1834E-41)
            r3 = r3 & r30
            int r12 = r12 + 2
            r13 = r12
            goto L_0x0038
        L_0x0109:
            int r13 = r12 + 1
            r0 = r34
            byte r8 = r0.get(r12)
            r8 = r8 & 255(0xff, float:3.57E-43)
            goto L_0x0038
        L_0x0115:
            int r13 = r12 + 1
            r0 = r34
            byte r7 = r0.get(r12)
            r7 = r7 & 255(0xff, float:3.57E-43)
            goto L_0x0038
        L_0x0121:
            r0 = r34
            short r6 = r0.getShort(r12)
            r30 = 65535(0xffff, float:9.1834E-41)
            r6 = r6 & r30
            int r12 = r12 + 2
            r13 = r12
            goto L_0x0038
        L_0x0131:
            r0 = r34
            short r9 = r0.getShort(r12)
            r30 = 65535(0xffff, float:9.1834E-41)
            r9 = r9 & r30
            int r12 = r12 + 2
            r13 = r12
            goto L_0x0038
        L_0x0141:
            int r13 = r12 + 1
            r0 = r34
            byte r4 = r0.get(r12)
            r4 = r4 & 255(0xff, float:3.57E-43)
            goto L_0x0038
        L_0x014d:
            r0 = r34
            short r5 = r0.getShort(r12)
            r30 = 65535(0xffff, float:9.1834E-41)
            r5 = r5 & r30
            int r12 = r12 + 2
            r13 = r12
            goto L_0x0038
        L_0x015d:
            r0 = r34
            short r24 = r0.getShort(r12)
            r30 = 65535(0xffff, float:9.1834E-41)
            r24 = r24 & r30
            int r12 = r12 + 2
            r13 = r12
            goto L_0x0038
        L_0x016d:
            int r13 = r12 + 1
            r0 = r34
            byte r22 = r0.get(r12)
            r0 = r22
            r0 = r0 & 255(0xff, float:3.57E-43)
            r22 = r0
            goto L_0x0038
        L_0x017d:
            int r13 = r12 + 1
            r0 = r34
            byte r23 = r0.get(r12)
            r0 = r23
            r0 = r0 & 255(0xff, float:3.57E-43)
            r23 = r0
            goto L_0x0038
        L_0x018d:
            int r12 = r12 + 2
            r13 = r12
            goto L_0x0038
        L_0x0192:
            int r12 = r12 + 1
            r13 = r12
            goto L_0x0038
        L_0x0197:
            android.util.SparseArray r29 = new android.util.SparseArray
            r30 = 25
            r29.<init>(r30)
            r30 = 220(0xdc, float:3.08E-43)
            java.lang.Integer r31 = java.lang.Integer.valueOf(r28)
            r29.append(r30, r31)
            r30 = 300(0x12c, float:4.2E-43)
            java.lang.Integer r31 = java.lang.Integer.valueOf(r27)
            r29.append(r30, r31)
            r30 = 301(0x12d, float:4.22E-43)
            java.lang.Integer r31 = java.lang.Integer.valueOf(r26)
            r29.append(r30, r31)
            r30 = 302(0x12e, float:4.23E-43)
            java.lang.Integer r31 = java.lang.Integer.valueOf(r14)
            r29.append(r30, r31)
            r30 = 303(0x12f, float:4.25E-43)
            java.lang.Integer r31 = java.lang.Integer.valueOf(r17)
            r29.append(r30, r31)
            r30 = 304(0x130, float:4.26E-43)
            java.lang.Integer r31 = java.lang.Integer.valueOf(r19)
            r29.append(r30, r31)
            r30 = 305(0x131, float:4.27E-43)
            java.lang.Integer r31 = java.lang.Integer.valueOf(r15)
            r29.append(r30, r31)
            r30 = 306(0x132, float:4.29E-43)
            java.lang.Integer r31 = java.lang.Integer.valueOf(r16)
            r29.append(r30, r31)
            r30 = 307(0x133, float:4.3E-43)
            java.lang.Integer r31 = java.lang.Integer.valueOf(r18)
            r29.append(r30, r31)
            r30 = 308(0x134, float:4.32E-43)
            java.lang.Integer r31 = java.lang.Integer.valueOf(r21)
            r29.append(r30, r31)
            r30 = 309(0x135, float:4.33E-43)
            java.lang.Integer r31 = java.lang.Integer.valueOf(r10)
            r29.append(r30, r31)
            r30 = 310(0x136, float:4.34E-43)
            java.lang.Integer r31 = java.lang.Integer.valueOf(r20)
            r29.append(r30, r31)
            r30 = 311(0x137, float:4.36E-43)
            java.lang.Integer r31 = java.lang.Integer.valueOf(r2)
            r29.append(r30, r31)
            r30 = 312(0x138, float:4.37E-43)
            java.lang.Integer r31 = java.lang.Integer.valueOf(r3)
            r29.append(r30, r31)
            r30 = 313(0x139, float:4.39E-43)
            java.lang.Integer r31 = java.lang.Integer.valueOf(r6)
            r29.append(r30, r31)
            r30 = 314(0x13a, float:4.4E-43)
            java.lang.Integer r31 = java.lang.Integer.valueOf(r8)
            r29.append(r30, r31)
            r30 = 315(0x13b, float:4.41E-43)
            java.lang.Integer r31 = java.lang.Integer.valueOf(r7)
            r29.append(r30, r31)
            r30 = 313(0x139, float:4.39E-43)
            java.lang.Integer r31 = java.lang.Integer.valueOf(r6)
            r29.append(r30, r31)
            r30 = 316(0x13c, float:4.43E-43)
            java.lang.Integer r31 = java.lang.Integer.valueOf(r9)
            r29.append(r30, r31)
            r30 = 317(0x13d, float:4.44E-43)
            java.lang.Integer r31 = java.lang.Integer.valueOf(r4)
            r29.append(r30, r31)
            r30 = 319(0x13f, float:4.47E-43)
            java.lang.Integer r31 = java.lang.Integer.valueOf(r5)
            r29.append(r30, r31)
            r30 = 320(0x140, float:4.48E-43)
            java.lang.Integer r31 = java.lang.Integer.valueOf(r24)
            r29.append(r30, r31)
            r30 = 321(0x141, float:4.5E-43)
            java.lang.Integer r31 = java.lang.Integer.valueOf(r22)
            r29.append(r30, r31)
            r30 = 322(0x142, float:4.51E-43)
            java.lang.Integer r31 = java.lang.Integer.valueOf(r23)
            r29.append(r30, r31)
            java.lang.String r30 = TAG
            java.lang.String r31 = "**********************************"
            android.util.Log.d(r30, r31)
            java.lang.String r30 = TAG
            java.lang.StringBuilder r31 = new java.lang.StringBuilder
            r31.<init>()
            java.lang.String r32 = "User WO Record: "
            java.lang.StringBuilder r31 = r31.append(r32)
            r0 = r31
            r1 = r29
            java.lang.StringBuilder r31 = r0.append(r1)
            java.lang.String r31 = r31.toString()
            android.util.Log.d(r30, r31)
            java.lang.String r30 = TAG
            java.lang.String r31 = "**********************************"
            android.util.Log.d(r30, r31)
            r0 = r33
            com.nautilus.omni.bleservices.OmniCallbacks r0 = r0.clientDelegate
            r30 = r0
            if (r30 == 0) goto L_0x02b5
            r0 = r33
            com.nautilus.omni.bleservices.OmniCallbacks r0 = r0.clientDelegate
            r30 = r0
            r0 = r30
            r1 = r29
            r0.nautilusDeviceUserWorkoutData(r1)
        L_0x02b5:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.nautilus.omni.bleservices.OmniBLEService.processWorkoutData(java.nio.ByteBuffer):void");
    }

    private void processOmniCharacteristicUpdate(BluetoothGattCharacteristic characteristic) {
        if (this.mBLEDevice == null) {
            Log.d(TAG, "Null BLE device in process characteristic");
        } else if (UUID.fromString(OmniGattAttributes.OMNI_ACK_RECORD).equals(characteristic.getUuid())) {
            Log.d(TAG, "ACK " + characteristic.getValue());
            ByteBuffer recordData = ByteBuffer.allocate(OmniConstants.MAX_DATA_RECORD_LEN);
            recordData.put(characteristic.getValue());
            processCommandQueue(recordData);
            if (this.recordReadRequired) {
                if (this.dataRecordCharacteristic != null) {
                    readCharacteristic(this.deviceGatt, this.dataRecordCharacteristic);
                } else {
                    Log.d(TAG, "Data characteristic is null on ACK");
                }
                this.recordReadRequired = false;
            }
        } else if (UUID.fromString(OmniGattAttributes.OMNI_DATA_RECORD).equals(characteristic.getUuid())) {
            Log.d(TAG, "Data record updated: " + characteristic);
            ByteBuffer recordData2 = ByteBuffer.allocate(OmniConstants.MAX_DATA_RECORD_LEN);
            recordData2.put(characteristic.getValue());
            byte cmdCode = recordData2.get(4);
            byte statusCode = recordData2.get(3);
            if (statusCode > 0) {
                switch (statusCode) {
                    case 1:
                        Log.d(TAG, "ERROR:  Console is busy");
                        if (cmdCode == OmniCommands.REQUEST_BLE_ACCESS.getCmdVal()) {
                            if (this.clientDelegate != null) {
                                this.clientDelegate.nautilusDeviceError(OmniErrorTypes.UNABLE_TO_GET_BLE);
                                return;
                            }
                            return;
                        } else if (cmdCode == OmniCommands.GIVEUP_BLE_ACCESS.getCmdVal()) {
                            if (this.clientDelegate != null) {
                                this.clientDelegate.nautilusDeviceError(OmniErrorTypes.UNABLE_TO_RELEASE_BLE);
                                return;
                            }
                            return;
                        } else if (this.clientDelegate != null) {
                            this.clientDelegate.nautilusDeviceError(OmniErrorTypes.CONSOLE_IS_BUSY);
                            return;
                        } else {
                            return;
                        }
                    case 2:
                        Log.d(TAG, "ERROR:  No workout record");
                        this.clientDelegate.nautilusDeviceError(OmniErrorTypes.NO_WORKOUT_RECORD);
                        return;
                    case 4:
                        Log.d(TAG, "ERROR:  User index is not valid");
                        this.clientDelegate.nautilusDeviceError(OmniErrorTypes.USER_INDEX_INVALID);
                        return;
                    case 8:
                        Log.d(TAG, "ERROR:  Checksum was not correct");
                        return;
                    case 9:
                        Log.d(TAG, "ERROR:  Parameter was invalid");
                        this.clientDelegate.nautilusDeviceError(OmniErrorTypes.PARAMETER_INVALID);
                        return;
                    case 10:
                        Log.d(TAG, "ERROR:  Command unknown");
                        return;
                    default:
                        Log.d(TAG, "ERROR: Unhandled error code");
                        return;
                }
            } else {
                if (cmdCode == OmniCommands.REQUEST_BLE_ACCESS.getCmdVal()) {
                    bleWasAcquiredNotification();
                }
                if (cmdCode == OmniCommands.GET_STATUS.getCmdVal()) {
                    Log.d(TAG, "Get status returned a value");
                    processGetStatusData(recordData2);
                }
                if (cmdCode == OmniCommands.CLEAR_NVM_FLAGS.getCmdVal()) {
                    Log.d(TAG, "CLEAR NVM FLAGS WAS SUCCESSFUL!!");
                    if (this.clientDelegate != null) {
                        this.clientDelegate.nvmClearFlagsStatus(true);
                    }
                }
                if (cmdCode == OmniCommands.GET_SYSTEM_DATA.getCmdVal()) {
                    Log.d(TAG, "Get system data returned a value");
                    processSystemData(recordData2);
                }
                if (cmdCode == OmniCommands.GET_PRODUCT_DATA.getCmdVal()) {
                    Log.d(TAG, "Get product data returned a value");
                    processProductData(recordData2);
                }
                if (cmdCode == OmniCommands.GET_USER_DATA.getCmdVal()) {
                    Log.d(TAG, "Get user data returned a value");
                    processUserData(recordData2);
                }
                if (cmdCode == OmniCommands.GET_WORKOUT_DATA.getCmdVal()) {
                    Log.d(TAG, "Get workout data returned a value");
                    processWorkoutData(recordData2);
                }
                if (cmdCode == OmniCommands.GIVEUP_BLE_ACCESS.getCmdVal()) {
                    bleWasReleasedNotification();
                }
            }
        } else if (UUID.fromString(OmniGattAttributes.OMNI_EVENT_RECORD).equals(characteristic.getUuid())) {
            Log.d(TAG, "Event record updated: " + characteristic);
            ByteBuffer recordData3 = ByteBuffer.allocate(20);
            recordData3.put(characteristic.getValue());
            Log.d(TAG, "Got event: " + recordData3.get(1));
        } else if (UUID.fromString(OmniGattAttributes.OMNI_DEVICE_NAME).equals(characteristic.getUuid())) {
            String deviceName = characteristic.getStringValue(0);
            this.mBLEDevice.setDeviceName(deviceName);
            Log.d(TAG, "DEVICE_NAME " + deviceName);
        } else if (UUID.fromString(OmniGattAttributes.OMNI_MODEL_NUMBER).equals(characteristic.getUuid())) {
            String modelNumber = characteristic.getStringValue(0);
            this.mBLEDevice.setModelNumber(modelNumber);
            Log.d(TAG, "MODEL_NUMBER " + modelNumber);
        } else if (UUID.fromString(OmniGattAttributes.OMNI_FIRMWARE_REV).equals(characteristic.getUuid())) {
            String fwRev = characteristic.getStringValue(0);
            this.mBLEDevice.setFirmwareRev(fwRev);
            Log.d(TAG, "FIRMWARE_REV " + fwRev);
        } else if (UUID.fromString(OmniGattAttributes.OMNI_HARDWARE_REV).equals(characteristic.getUuid())) {
            String hwRev = characteristic.getStringValue(0);
            this.mBLEDevice.setHardwareRev(hwRev);
            Log.d(TAG, "HARDWARE_REV " + hwRev);
        } else if (UUID.fromString(OmniGattAttributes.OMNI_MANUFACTURER_NAME).equals(characteristic.getUuid())) {
            String manufacturerName = characteristic.getStringValue(0);
            this.mBLEDevice.setManufacturerName(manufacturerName);
            Log.d(TAG, "MANUFACTURER_NAME " + manufacturerName);
        }
    }

    private void processCommandQueue(ByteBuffer recordData) {
        if (isMY16orMY17Console()) {
            Log.d(TAG, "ACK MY16");
            this.mIsAppGettingAcksFromMy16 = true;
            if (((byte) (recordData.get(2) & 1)) < 1) {
                Log.d(TAG, "MY16 command has completed");
                this.mWriteLock.unlockThread();
                return;
            }
            Log.d(TAG, "Command is busy.  Perform a read on ACK to checkIfUnlockByTimeout again");
            readCharacteristicDelay(this.deviceGatt, this.ackRecordCharacteristic, 500);
        }
    }

    private void processCharacteristicUpdate(BluetoothGattCharacteristic characteristic) {
        int flag = characteristic.getProperties();
        if (this.mBLEDevice == null) {
            Log.d(TAG, "Null BLE device in process characteristic");
            return;
        }
        if ((flag & 1) != 0) {
        }
        processOmniCharacteristicUpdate(characteristic);
    }

    private void setupNotification(BluetoothGattCharacteristic characteristic) {
        if (this.mBluetoothAdapter == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
        } else if (this.mBluetoothGatt.setCharacteristicNotification(characteristic, true)) {
            this.WORKER.submit(new WriteDescriptorHandlerThread(characteristic));
        } else {
            Log.d(TAG, "Failed to set notification");
        }
    }

    private boolean isMY14Console() {
        return this.mBLEDevice.getDeviceType() == OmniDeviceType.MY14_BIKE_ELLIPTICAL || this.mBLEDevice.getDeviceType() == OmniDeviceType.MY14_TREADMILL;
    }

    private boolean isMY16Console() {
        return this.mBLEDevice.getDeviceType() == OmniDeviceType.MY16_BIKE_ELLIPTICAL || this.mBLEDevice.getDeviceType() == OmniDeviceType.MY16_TREADMILL || this.mBLEDevice.getDeviceType() == OmniDeviceType.MY16_INTERNATIONAL_TREADMILL || this.mBLEDevice.getDeviceType() == OmniDeviceType.MY16_INTERNATIONAL_RECUMBENT_BIKE;
    }

    private boolean isMY17Console() {
        if (this.mBLEDevice.getDeviceType() == OmniDeviceType.ELLIPTICAL_E628_INTERNATIONAL || this.mBLEDevice.getDeviceType() == OmniDeviceType.UPRIGHT_BIKE_UR628_INTERNATIONAL || this.mBLEDevice.getDeviceType() == OmniDeviceType.MY17_TREADMILL_T618 || this.mBLEDevice.getDeviceType() == OmniDeviceType.MY17_ELLIPTICAL_E618 || this.mBLEDevice.getDeviceType() == OmniDeviceType.MY17_BIKE_B618 || this.mBLEDevice.getDeviceType() == OmniDeviceType.MY17_TREADMILL_T616 || this.mBLEDevice.getDeviceType() == OmniDeviceType.MY17_ELLIPTICAL_E616 || this.mBLEDevice.getDeviceType() == OmniDeviceType.MY17_UPRIGHT_BIKE_UR616) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: private */
    public boolean isMY16orMY17Console() {
        return isMY16Console() || isMY17Console();
    }

    public boolean startOmniTrainerScan() {
        if (this.mBLEScanManager != null) {
            this.mBLEScanManager.startScan();
            return true;
        }
        Log.d(TAG, "Do not have a connection manager to start scan");
        return false;
    }

    public boolean stopOmniTrainerScan() {
        if (this.mBLEScanManager != null) {
            this.mBLEScanManager.stopScan();
            return true;
        }
        Log.d(TAG, "Do not have a connection manager to start scan");
        return false;
    }

    public boolean connectToOmniTrainer(OmniDevice maxObject) {
        Log.d(TAG, "CONNECT WAS CALLED");
        if (maxObject == null) {
            return false;
        }
        boolean success = connectToBLEDevice(maxObject);
        if (!success) {
            return success;
        }
        this.mKeyCharacteristicCount = 0;
        return success;
    }

    public void disconnectOmniTrainer() {
        Log.d(TAG, "DISCONNECT WAS CALLED");
        disconnectFromBLEDevice();
    }

    private boolean sendCommandToOmni(byte[] cmdRecord) {
        if (this.mBluetoothAdapter == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return false;
        }
        this.WORKER.submit(new WriteRequestHandlerThread(cmdRecord));
        return true;
    }

    public boolean getBLE() {
        boolean success = false;
        if (this.mBLEDevice == null) {
            Log.d(TAG, "Null BLE device in get BLE");
            return false;
        } else if (this.mBLEDevice.getDeviceStatus() != OmniConnectionState.CONNECTED_NO_BLE) {
            Log.d(TAG, "Wrong state for getting BLE");
            return false;
        } else {
            Log.d(TAG, "Acquiring the BLE");
            if (isMY14Console()) {
                this.recordReadRequired = true;
                success = sendCommandToOmni(omniRequestBLEAccessCmd);
            } else if (isMY16orMY17Console()) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        OmniBLEService.this.bleWasAcquiredNotification();
                    }
                }, 200);
                success = true;
            }
            return success;
        }
    }

    public boolean releaseBLE() {
        boolean success = false;
        if (this.mBLEDevice == null) {
            Log.d(TAG, "Null BLE device in release");
            return false;
        } else if (this.mBLEDevice.getDeviceStatus() != OmniConnectionState.CONNECTED_WITH_BLE) {
            Log.d(TAG, "Wrong state for releasing BLE");
            return false;
        } else {
            Log.d(TAG, "Releasing the BLE");
            if (isMY14Console()) {
                this.recordReadRequired = true;
                success = sendCommandToOmni(omniGiveupBLEAccessCmd);
            } else if (isMY16orMY17Console()) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        OmniBLEService.this.bleWasReleasedNotification();
                    }
                }, 200);
                success = true;
            }
            return success;
        }
    }

    public boolean clearNVMFlags() {
        boolean success = false;
        if (!this.mBLEDevice.isHaveBLE()) {
            Log.d(TAG, "Wrong state for clear NVM flags");
            return false;
        }
        if (isMY14Console()) {
            success = false;
        } else if (isMY16orMY17Console()) {
            this.recordReadRequired = true;
            success = sendCommandToOmni(resultsClearNVMCmd);
        }
        return success;
    }

    public boolean getStatus() {
        if (!this.mBLEDevice.isHaveBLE()) {
            return false;
        }
        if (!this.mBLEDevice.isHaveBLE()) {
            Log.d(TAG, "Wrong state for get status");
            return false;
        }
        this.recordReadRequired = true;
        return sendCommandToOmni(omniGetStatusCmd);
    }

    public boolean getProductData() {
        if (this.mBLEDevice == null) {
            Log.d(TAG, "Null BLE device in get product data");
            return false;
        } else if (!this.mBLEDevice.isHaveBLE()) {
            return false;
        } else {
            this.recordReadRequired = true;
            return sendCommandToOmni(omniGetProductDataCmd);
        }
    }

    public boolean getSystemData() {
        if (this.mBLEDevice == null) {
            Log.d(TAG, "Null BLE device in get system data");
            return false;
        } else if (!this.mBLEDevice.isHaveBLE()) {
            return false;
        } else {
            this.recordReadRequired = true;
            return sendCommandToOmni(omniGetSystemDataCmd);
        }
    }

    public boolean getUserData(int userIndex) {
        if (this.mBLEDevice == null) {
            Log.d(TAG, "Null BLE device in get user data");
            return false;
        } else if (!this.mBLEDevice.isHaveBLE()) {
            return false;
        } else {
            Log.d(TAG, "Getting user data");
            this.omniGetUserDataCmd[2] = (byte) (-77 - userIndex);
            this.omniGetUserDataCmd[6] = (byte) userIndex;
            this.recordReadRequired = true;
            return sendCommandToOmni(this.omniGetUserDataCmd);
        }
    }

    public boolean getWorkoutData(int userIndex, int recordIndex) {
        if (this.mBLEDevice == null) {
            Log.d(TAG, "Null BLE device in get WO data");
            return false;
        } else if (!this.mBLEDevice.isHaveBLE()) {
            return false;
        } else {
            this.omniGetUserWorkoutDataCmd[2] = (byte) ((((byte) ((((userIndex + BLEScanManager.CONVERT_TO_SIGNAL_STRENGTH) + recordIndex) + 20) % 256)) ^ -1) + 1);
            this.omniGetUserWorkoutDataCmd[1] = 20;
            this.omniGetUserWorkoutDataCmd[6] = (byte) userIndex;
            this.omniGetUserWorkoutDataCmd[8] = (byte) recordIndex;
            this.recordReadRequired = true;
            return sendCommandToOmni(this.omniGetUserWorkoutDataCmd);
        }
    }

    public boolean startPairingProcess() {
        return false;
    }

    public boolean endPairingProcess() {
        return true;
    }

    private void readCharacteristic(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic characteristic) {
        this.WORKER.execute(new ReadRequestHandlerThread(bluetoothGatt, characteristic));
    }

    private void readCharacteristicDelay(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic characteristic, int delayInMilliseconds) {
        this.WORKERDELAY.schedule(new ReadRequestHandlerThread(bluetoothGatt, characteristic), (long) delayInMilliseconds, TimeUnit.MILLISECONDS);
    }

    class WriteDescriptorHandlerThread extends Thread {
        private BluetoothGattCharacteristic mCharacteristic;

        public WriteDescriptorHandlerThread(BluetoothGattCharacteristic characteristic) {
            this.mCharacteristic = characteristic;
        }

        public void run() {
            BluetoothGattDescriptor descriptor = this.mCharacteristic.getDescriptor(UUID.fromString("00002902-0000-1000-8000-00805f9b34fb"));
            if (descriptor != null) {
                try {
                    if (descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE)) {
                        executeWriteDescriptor(descriptor, false);
                    }
                } catch (InterruptedException e) {
                    Log.e(OmniBLEService.TAG, "Write descriptor thread interrupted");
                    e.printStackTrace();
                    OmniBLEService.this.mDescriptorWriteLock.unlockThread();
                    OmniBLEService.this.executeDeviceDisconnection();
                }
            }
        }

        private void executeWriteDescriptor(BluetoothGattDescriptor descriptor, boolean isSecondWriteDescriptorAttempt) throws InterruptedException {
            if (!OmniBLEService.this.mBluetoothGatt.writeDescriptor(descriptor)) {
                Log.d(OmniBLEService.TAG, "Could not write descriptor for characteristic");
            } else if (OmniBLEService.this.mDescriptorWriteLock.lockThread(500)) {
                Log.d(OmniBLEService.TAG, "Successful write descriptor!");
            } else if (!isSecondWriteDescriptorAttempt) {
                Log.d(OmniBLEService.TAG, "Executing second write descriptor attempt!");
                executeWriteDescriptor(descriptor, true);
            } else {
                Log.d(OmniBLEService.TAG, "Executing disconnection after unsuccessful second write descriptor attempt!");
                OmniBLEService.this.executeDeviceDisconnection();
            }
        }
    }

    class ReadRequestHandlerThread extends Thread {
        private BluetoothGattCharacteristic mCharacteristic;
        private BluetoothGatt mGatt;

        public ReadRequestHandlerThread(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            this.mGatt = gatt;
            this.mCharacteristic = characteristic;
        }

        public void run() {
            if (this.mGatt == null) {
                Log.e(OmniBLEService.TAG, "Null gatt on read characteristic");
                return;
            }
            try {
                if (!this.mGatt.readCharacteristic(this.mCharacteristic)) {
                    Log.e(OmniBLEService.TAG, "First Read characteristic failed, retrying...");
                    Thread.sleep(500);
                    if (!this.mGatt.readCharacteristic(this.mCharacteristic)) {
                        Log.e(OmniBLEService.TAG, "Second Read characteristic failed.  Read aborted");
                        return;
                    }
                    Log.d(OmniBLEService.TAG, "DEBUG - THREAD UNLOCKED BY NOTIFY: " + OmniBLEService.this.mReadLock.lockThread(1000));
                    return;
                }
                Log.d(OmniBLEService.TAG, "DEBUG - THREAD UNLOCKED BY NOTIFY: " + OmniBLEService.this.mReadLock.lockThread(1000));
            } catch (InterruptedException e) {
                Log.e(OmniBLEService.TAG, "Read thread interrupted");
                e.printStackTrace();
                OmniBLEService.this.mReadLock.unlockThread();
            }
        }
    }

    class WriteRequestHandlerThread extends Thread {
        private byte[] mCmdRecord;

        public WriteRequestHandlerThread(byte[] cmdRecord) {
            this.mCmdRecord = cmdRecord;
        }

        public void run() {
            try {
                boolean unused = OmniBLEService.this.mIsAppGettingAcksFromMy16 = false;
                if (OmniBLEService.this.commandRecordCharacteristic == null) {
                    Log.w(OmniBLEService.TAG, "Command Characteristic is null!!!!");
                } else if (OmniBLEService.this.commandRecordCharacteristic.setValue(this.mCmdRecord)) {
                    executeWriteCharacteristic(true);
                } else {
                    Log.w(OmniBLEService.TAG, "Set command value failed");
                }
            } catch (InterruptedException e) {
                Log.e(OmniBLEService.TAG, "Error processing command characteristic write");
                e.printStackTrace();
                OmniBLEService.this.mWriteLock.unlockThread();
            }
        }

        private void executeWriteCharacteristic(boolean isFirstWriteCharacteristicAttempt) throws InterruptedException {
            if (!OmniBLEService.this.mBluetoothGatt.writeCharacteristic(OmniBLEService.this.commandRecordCharacteristic)) {
                Log.d(OmniBLEService.TAG, "Write command characteristic failed");
                reportConsoleNotResponsiveError();
            } else if (OmniBLEService.this.isMY16orMY17Console()) {
                checkIfUnlockByTimeout(isFirstWriteCharacteristicAttempt);
            } else {
                OmniBLEService.this.mWriteLock.lockThread(500);
            }
        }

        private void checkIfUnlockByTimeout(boolean isFirstWriteCharacteristicAttempt) throws InterruptedException {
            if (!OmniBLEService.this.mWriteLock.lockThread(5000)) {
                executeWriteCharacteristicRetryIfNeeded(isFirstWriteCharacteristicAttempt);
            }
        }

        private void executeWriteCharacteristicRetryIfNeeded(boolean isFirstWriteCharacteristicAttempt) throws InterruptedException {
            if (OmniBLEService.this.mIsAppGettingAcksFromMy16) {
                reportConsoleNotResponsiveError();
            } else if (isFirstWriteCharacteristicAttempt) {
                Log.d(OmniBLEService.TAG, "Executing write characteristic retry....");
                executeWriteCharacteristic(false);
            } else {
                reportConsoleNotResponsiveError();
            }
        }

        private void reportConsoleNotResponsiveError() {
            Log.d(OmniBLEService.TAG, "Command timed out.  Closing connection!!!");
            Log.d(OmniBLEService.TAG, "Console Not Responsive!!!");
            OmniBLEService.this.WORKER.shutdownNow();
            OmniBLEService.this.clientDelegate.nautilusDeviceError(OmniErrorTypes.CONSOLE_NOT_RESPONSIVE);
        }
    }
}
