package com.nautilus.omni.bleservices;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanRecord;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.os.Build;
import android.os.ParcelUuid;
import android.util.Log;
import android.util.SparseArray;
import com.nautilus.omni.bleservices.ble.OmniDevice;
import com.nautilus.omni.bleservices.ble.OmniDeviceType;
import com.nautilus.omni.bleservices.ble.OmniGattAttributes;
import com.nautilus.omni.bleservices.ble.ThreadLocker;
import com.nautilus.omni.bleservices.ble.omnitrainer.OmniConnectionState;
import com.nautilus.omni.bleservices.ble.omnitrainer.OmniMY16Constants;
import com.nautilus.omni.util.bleutil.BLEUtil;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

@TargetApi(21)
public class BLEScanManager {
    public static final int CONSOLE_BUSY = 503;
    public static final int CONSOLE_CONNECTABLE = 502;
    public static final int CONSOLE_STATE = 501;
    public static final int CONVERT_TO_SIGNAL_STRENGTH = 125;
    public static final double DEFAULT_ALPHA = 0.2d;
    public static final int LOWEST_RSSI_THRESHOLD = -110;
    public static final int NORMAL_RSSI_THRESHOLD = -60;
    /* access modifiers changed from: private */
    public static final String TAG = BLEScanManager.class.getSimpleName();
    public static final int UNIQUE_ID_LEN = 6;
    public static final int USER_NUMBER = 500;
    private static int defaultRSSI = -60;
    private OmniCallbacks clientDelegate;
    /* access modifiers changed from: private */
    public ArrayList foundDevices;
    /* access modifiers changed from: private */
    public ArrayList foundDevicesRSSI;
    /* access modifiers changed from: private */
    public boolean isScanning;
    /* access modifiers changed from: private */
    public BluetoothAdapter mBluetoothAdapter;
    private final ScheduledExecutorService mExecutorService = new ScheduledThreadPoolExecutor(1);
    private OmniConnectionState mMaxConnectionState;
    private ScanHandler mScanHandler;
    private ScanHandler21 mScanHandler21;
    /* access modifiers changed from: private */
    public ArrayList omniObjects;
    private ThreadLocker waitLock = new ThreadLocker();

    public BLEScanManager(BluetoothAdapter adapter, OmniCallbacks theCallback) {
        this.mBluetoothAdapter = adapter;
        this.clientDelegate = theCallback;
        this.mMaxConnectionState = OmniConnectionState.DISCONNECTED;
        this.foundDevices = new ArrayList();
        this.foundDevicesRSSI = new ArrayList();
        this.omniObjects = new ArrayList();
    }

    public void startScan() {
        initScanHandler();
        if (this.mMaxConnectionState == OmniConnectionState.CONNECTED_NO_BLE || this.mMaxConnectionState == OmniConnectionState.CONNECTING) {
            Log.d(TAG, "Device is already connecting or connected, nothing to do");
        } else {
            startScanning();
        }
    }

    private void initScanHandler() {
        if (Build.VERSION.SDK_INT < 21) {
            if (this.mScanHandler == null) {
                this.mScanHandler = new ScanHandler();
            }
        } else if (this.mScanHandler21 == null) {
            this.mScanHandler21 = new ScanHandler21();
        }
    }

    private void startScanning() {
        if (!this.mBluetoothAdapter.isEnabled()) {
            return;
        }
        if (Build.VERSION.SDK_INT < 21) {
            this.mScanHandler.startScan();
        } else {
            this.mScanHandler21.startScan();
        }
    }

    public void stopScan() {
        if (!this.mBluetoothAdapter.isEnabled()) {
            return;
        }
        if (Build.VERSION.SDK_INT < 21) {
            if (this.mScanHandler != null) {
                this.mScanHandler.stopScan();
            }
        } else if (this.mScanHandler21 != null) {
            this.mScanHandler21.stopScan();
        }
    }

    private int smoothRSSI(int current, int last) {
        double alphaNormalizer;
        if (current < -60) {
            alphaNormalizer = 1.0d - (0.5d * ((double) ((-15 - current) / 60)));
        } else {
            alphaNormalizer = 0.2d;
        }
        return (int) Math.round((((double) current) * alphaNormalizer) + ((1.0d - alphaNormalizer) * ((double) last)));
    }

    private ByteBuffer getUniqueID(ByteBuffer scanAddData) {
        byte[] adDataArray = scanAddData.array();
        ByteBuffer uniqueID = ByteBuffer.allocate(6);
        uniqueID.put(adDataArray, 0, 6);
        return uniqueID;
    }

    /* access modifiers changed from: private */
    public void processDeviceSignalStrength(BluetoothDevice theDevice, OmniDeviceType omniType, ByteBuffer my16consoleUniqueID, int rssi, SparseArray omniAdData) {
        String omniUniqueID = theDevice.getAddress();
        if (!this.foundDevices.contains(theDevice)) {
            if (omniType == OmniDeviceType.MY14_BIKE_ELLIPTICAL) {
                Log.d(TAG, "*****Found a new MY14 Bike/Elliptical*****");
            } else if (omniType == OmniDeviceType.MY16_BIKE_ELLIPTICAL) {
                Log.d(TAG, "*****Found a new MY16 Bike/Elliptical*****");
            } else if (omniType == OmniDeviceType.MY14_TREADMILL) {
                Log.d(TAG, "*****Found a new MY14 Treadmill*****");
            } else if (omniType == OmniDeviceType.MY16_TREADMILL) {
                Log.d(TAG, "*****Found a new MY16 Treadmill*****");
            } else if (omniType == OmniDeviceType.MY16_INTERNATIONAL_TREADMILL) {
                Log.d(TAG, "*****Found a new MY16 International Treadmill*****");
            } else if (omniType == OmniDeviceType.ELLIPTICAL_E628_INTERNATIONAL) {
                Log.d(TAG, "*****Found a new E628 International Elliptical*****");
            } else if (omniType == OmniDeviceType.UPRIGHT_BIKE_UR628_INTERNATIONAL) {
                Log.d(TAG, "*****Found a new UR628 International Upright Bike*****");
            } else if (omniType == OmniDeviceType.MY16_INTERNATIONAL_RECUMBENT_BIKE) {
                Log.d(TAG, "*****Found a new MY16 International Recumbent Bike*****");
            } else if (omniType == OmniDeviceType.MY17_TREADMILL_T618) {
                Log.d(TAG, "*****Found a new MY17 T618 Treadmill*****");
            } else if (omniType == OmniDeviceType.MY17_ELLIPTICAL_E618) {
                Log.d(TAG, "*****Found a new MY17 E618 Elliptical*****");
            } else if (omniType == OmniDeviceType.MY17_BIKE_B618) {
                Log.d(TAG, "*****Found a new MY17 B618  Bike*****");
            } else if (omniType == OmniDeviceType.MY17_TREADMILL_T616) {
                Log.d(TAG, "*****Found a new MY17 T616 Treadmill*****");
            } else if (omniType == OmniDeviceType.MY17_ELLIPTICAL_E616) {
                Log.d(TAG, "*****Found a new MY17 E616 Elliptical*****");
            } else if (omniType == OmniDeviceType.MY17_UPRIGHT_BIKE_UR616) {
                Log.d(TAG, "*****Found a new MY17 UR616 Upright Bike*****");
            }
            this.foundDevices.add(theDevice);
            int deviceIndex = this.foundDevices.indexOf(theDevice);
            OmniDevice newOmni = new OmniDevice(omniType);
            newOmni.setBluetoothDevice(theDevice);
            newOmni.setAddress(omniUniqueID);
            this.omniObjects.add(deviceIndex, newOmni);
            this.foundDevicesRSSI.add(deviceIndex, Integer.valueOf(rssi));
            int signalStrength = smoothRSSI(rssi, defaultRSSI) + CONVERT_TO_SIGNAL_STRENGTH;
            if (this.clientDelegate == null) {
                Log.d(TAG, "Client delegate is null on found scan");
            } else if (omniType == OmniDeviceType.MY14_BIKE_ELLIPTICAL || omniType == OmniDeviceType.MY14_TREADMILL) {
                this.clientDelegate.onFoundOmni(newOmni, omniUniqueID, theDevice.getName(), signalStrength);
            } else {
                boolean consoleConnectable = isMY16ConsoleConnectable(omniAdData);
                this.clientDelegate.onFoundMY16(newOmni, my16consoleUniqueID, theDevice.getName(), signalStrength, ((Integer) omniAdData.get(CONSOLE_STATE)).intValue(), ((Integer) omniAdData.get(500)).intValue(), consoleConnectable);
            }
        } else {
            int deviceIndex2 = this.foundDevices.indexOf(theDevice);
            OmniDevice currentOmni = (OmniDevice) this.omniObjects.get(deviceIndex2);
            int signalStrength2 = smoothRSSI(rssi, ((Integer) this.foundDevicesRSSI.get(deviceIndex2)).intValue());
            this.foundDevicesRSSI.remove(deviceIndex2);
            this.foundDevicesRSSI.add(deviceIndex2, Integer.valueOf(rssi));
            int signalStrength3 = signalStrength2 + CONVERT_TO_SIGNAL_STRENGTH;
            if (this.clientDelegate == null) {
                Log.d(TAG, "Client delegate is null on update scan");
            } else if (omniType == OmniDeviceType.MY14_BIKE_ELLIPTICAL || omniType == OmniDeviceType.MY14_TREADMILL) {
                this.clientDelegate.omniSignalUpdate(currentOmni, omniUniqueID, signalStrength3);
            } else {
                ByteBuffer byteBuffer = my16consoleUniqueID;
                int i = signalStrength3;
                this.clientDelegate.mY16SignalUpdate(currentOmni, byteBuffer, i, ((Integer) omniAdData.get(CONSOLE_STATE)).intValue(), ((Integer) omniAdData.get(500)).intValue(), isMY16ConsoleConnectable(omniAdData));
            }
        }
    }

    private boolean isMY16ConsoleConnectable(SparseArray omniAdData) {
        if (((Integer) omniAdData.get(CONSOLE_CONNECTABLE)).intValue() > 0) {
            return true;
        }
        return false;
    }

    public class ScanHandler {
        private BluetoothAdapter.LeScanCallback leScanCallback = new BluetoothAdapter.LeScanCallback() {
            public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
                List<UUID> myUUIDList = BLEUtil.parseUUIDs(scanRecord);
                OmniDeviceType foundDevice = OmniDeviceType.UNKNOWN;
                if (!myUUIDList.isEmpty()) {
                    Iterator<UUID> it = myUUIDList.iterator();
                    while (true) {
                        OmniDeviceType foundDevice2 = foundDevice;
                        if (it.hasNext()) {
                            UUID deviceUUID = it.next();
                            if (rssi >= -110) {
                                if (deviceUUID.equals(UUID.fromString(OmniGattAttributes.MY14_BIKE_ELLIPTICAL_SERVICE))) {
                                    BLEScanManager.this.processDeviceSignalStrength(device, OmniDeviceType.MY14_BIKE_ELLIPTICAL, (ByteBuffer) null, rssi, (SparseArray) null);
                                    foundDevice = foundDevice2;
                                } else if (deviceUUID.equals(UUID.fromString(OmniGattAttributes.MY14_TREADMILL_SERVICE))) {
                                    BLEScanManager.this.processDeviceSignalStrength(device, OmniDeviceType.MY14_TREADMILL, (ByteBuffer) null, rssi, (SparseArray) null);
                                    foundDevice = foundDevice2;
                                } else if (deviceUUID.equals(UUID.fromString(OmniGattAttributes.MY16_BIKE_ELLIPTICAL_SERVICE))) {
                                    foundDevice = OmniDeviceType.MY16_BIKE_ELLIPTICAL;
                                } else if (deviceUUID.equals(UUID.fromString(OmniGattAttributes.MY16_TREADMILL_SERVICE))) {
                                    foundDevice = OmniDeviceType.MY16_TREADMILL;
                                } else if (deviceUUID.equals(UUID.fromString(OmniGattAttributes.MY16_TREADMILL_INTERNATIONAL_SERVICE))) {
                                    foundDevice = OmniDeviceType.MY16_INTERNATIONAL_TREADMILL;
                                } else if (deviceUUID.equals(UUID.fromString(OmniGattAttributes.ELLIPTICAL_E628_INTERNATIONAL_SERVICE))) {
                                    foundDevice = OmniDeviceType.ELLIPTICAL_E628_INTERNATIONAL;
                                } else if (deviceUUID.equals(UUID.fromString(OmniGattAttributes.UPRIGHT_BIKE_UR628_INTERNATIONAL_SERVICE))) {
                                    foundDevice = OmniDeviceType.UPRIGHT_BIKE_UR628_INTERNATIONAL;
                                } else if (deviceUUID.equals(UUID.fromString(OmniGattAttributes.MY16_RECUMBENT_BIKE_INTERNATIONAL_SERVICE))) {
                                    foundDevice = OmniDeviceType.MY16_INTERNATIONAL_RECUMBENT_BIKE;
                                } else if (deviceUUID.equals(UUID.fromString(OmniGattAttributes.MY17_TREADMILL_T618_SERVICE))) {
                                    foundDevice = OmniDeviceType.MY17_TREADMILL_T618;
                                } else if (deviceUUID.equals(UUID.fromString(OmniGattAttributes.MY17_ELLIPTICAL_E618_SERVICE))) {
                                    foundDevice = OmniDeviceType.MY17_ELLIPTICAL_E618;
                                } else if (deviceUUID.equals(UUID.fromString(OmniGattAttributes.MY17_BIKE_B618_SERVICE))) {
                                    foundDevice = OmniDeviceType.MY17_BIKE_B618;
                                } else if (deviceUUID.equals(UUID.fromString(OmniGattAttributes.MY17_TREADMILL_T616_SERVICE))) {
                                    foundDevice = OmniDeviceType.MY17_TREADMILL_T616;
                                } else if (deviceUUID.equals(UUID.fromString(OmniGattAttributes.MY17_ELLIPTICAL_E616_SERVICE))) {
                                    foundDevice = OmniDeviceType.MY17_ELLIPTICAL_E616;
                                } else if (deviceUUID.equals(UUID.fromString(OmniGattAttributes.MY17_UPRIGHT_BIKE_UR616_SERVICE))) {
                                    foundDevice = OmniDeviceType.MY17_UPRIGHT_BIKE_UR616;
                                } else {
                                    foundDevice = foundDevice2;
                                }
                                if (foundDevice != OmniDeviceType.UNKNOWN) {
                                    SparseArray omniAdInfo = BLEScanManager.this.getOmniAdData(scanRecord);
                                    if (omniAdInfo != null) {
                                        BLEScanManager.this.processDeviceSignalStrength(device, foundDevice, BLEScanManager.this.getMY16consoleId(scanRecord), rssi, omniAdInfo);
                                    }
                                    foundDevice = OmniDeviceType.UNKNOWN;
                                }
                            } else {
                                foundDevice = foundDevice2;
                            }
                        } else {
                            return;
                        }
                    }
                }
            }
        };

        public ScanHandler() {
        }

        public void startScan() {
            if (!BLEScanManager.this.isScanning) {
                boolean unused = BLEScanManager.this.isScanning = true;
                BLEScanManager.this.foundDevices.clear();
                BLEScanManager.this.foundDevicesRSSI.clear();
                BLEScanManager.this.omniObjects.clear();
                BLEScanManager.this.mBluetoothAdapter.startLeScan(this.leScanCallback);
            }
        }

        public void stopScan() {
            if (BLEScanManager.this.isScanning) {
                boolean unused = BLEScanManager.this.isScanning = false;
                BLEScanManager.this.mBluetoothAdapter.stopLeScan(this.leScanCallback);
            }
        }
    }

    @TargetApi(21)
    public class ScanHandler21 {
        private BluetoothLeScanner mLeScanner;
        private List<ScanFilter> mScanFilters;
        private ScanSettings mScanSettings;
        private ScanCallback scanCallback = new ScanCallback() {
            public void onScanResult(int callbackType, ScanResult result) {
                List<ParcelUuid> listOfUUIDs;
                super.onScanResult(callbackType, result);
                ScanRecord mScanRecord = result.getScanRecord();
                OmniDeviceType foundDevice = OmniDeviceType.UNKNOWN;
                if (mScanRecord != null && (listOfUUIDs = mScanRecord.getServiceUuids()) != null && !listOfUUIDs.isEmpty()) {
                    Iterator<ParcelUuid> it = listOfUUIDs.iterator();
                    while (true) {
                        OmniDeviceType foundDevice2 = foundDevice;
                        if (it.hasNext()) {
                            UUID deviceUUID = it.next().getUuid();
                            int rssi = result.getRssi();
                            if (rssi >= -110) {
                                if (deviceUUID.equals(UUID.fromString(OmniGattAttributes.MY14_BIKE_ELLIPTICAL_SERVICE))) {
                                    BLEScanManager.this.processDeviceSignalStrength(result.getDevice(), OmniDeviceType.MY14_BIKE_ELLIPTICAL, (ByteBuffer) null, rssi, (SparseArray) null);
                                    foundDevice = foundDevice2;
                                } else if (deviceUUID.equals(UUID.fromString(OmniGattAttributes.MY14_TREADMILL_SERVICE))) {
                                    BLEScanManager.this.processDeviceSignalStrength(result.getDevice(), OmniDeviceType.MY14_TREADMILL, (ByteBuffer) null, rssi, (SparseArray) null);
                                    foundDevice = foundDevice2;
                                } else if (deviceUUID.equals(UUID.fromString(OmniGattAttributes.MY16_BIKE_ELLIPTICAL_SERVICE))) {
                                    foundDevice = OmniDeviceType.MY16_BIKE_ELLIPTICAL;
                                } else if (deviceUUID.equals(UUID.fromString(OmniGattAttributes.MY16_TREADMILL_SERVICE))) {
                                    foundDevice = OmniDeviceType.MY16_TREADMILL;
                                } else if (deviceUUID.equals(UUID.fromString(OmniGattAttributes.MY16_TREADMILL_INTERNATIONAL_SERVICE))) {
                                    foundDevice = OmniDeviceType.MY16_INTERNATIONAL_TREADMILL;
                                } else if (deviceUUID.equals(UUID.fromString(OmniGattAttributes.ELLIPTICAL_E628_INTERNATIONAL_SERVICE))) {
                                    foundDevice = OmniDeviceType.ELLIPTICAL_E628_INTERNATIONAL;
                                } else if (deviceUUID.equals(UUID.fromString(OmniGattAttributes.UPRIGHT_BIKE_UR628_INTERNATIONAL_SERVICE))) {
                                    foundDevice = OmniDeviceType.UPRIGHT_BIKE_UR628_INTERNATIONAL;
                                } else if (deviceUUID.equals(UUID.fromString(OmniGattAttributes.MY16_RECUMBENT_BIKE_INTERNATIONAL_SERVICE))) {
                                    foundDevice = OmniDeviceType.MY16_INTERNATIONAL_RECUMBENT_BIKE;
                                } else if (deviceUUID.equals(UUID.fromString(OmniGattAttributes.MY17_TREADMILL_T618_SERVICE))) {
                                    foundDevice = OmniDeviceType.MY17_TREADMILL_T618;
                                } else if (deviceUUID.equals(UUID.fromString(OmniGattAttributes.MY17_ELLIPTICAL_E618_SERVICE))) {
                                    foundDevice = OmniDeviceType.MY17_ELLIPTICAL_E618;
                                } else if (deviceUUID.equals(UUID.fromString(OmniGattAttributes.MY17_BIKE_B618_SERVICE))) {
                                    foundDevice = OmniDeviceType.MY17_BIKE_B618;
                                } else if (deviceUUID.equals(UUID.fromString(OmniGattAttributes.MY17_TREADMILL_T616_SERVICE))) {
                                    foundDevice = OmniDeviceType.MY17_TREADMILL_T616;
                                } else if (deviceUUID.equals(UUID.fromString(OmniGattAttributes.MY17_ELLIPTICAL_E616_SERVICE))) {
                                    foundDevice = OmniDeviceType.MY17_ELLIPTICAL_E616;
                                } else if (deviceUUID.equals(UUID.fromString(OmniGattAttributes.MY17_UPRIGHT_BIKE_UR616_SERVICE))) {
                                    foundDevice = OmniDeviceType.MY17_UPRIGHT_BIKE_UR616;
                                } else {
                                    foundDevice = foundDevice2;
                                }
                                if (foundDevice != OmniDeviceType.UNKNOWN) {
                                    SparseArray omniAdInfo = BLEScanManager.this.getOmniAdData(mScanRecord.getBytes());
                                    if (omniAdInfo != null) {
                                        BLEScanManager.this.processDeviceSignalStrength(result.getDevice(), foundDevice, BLEScanManager.this.getMY16consoleId(mScanRecord.getBytes()), rssi, omniAdInfo);
                                    }
                                    foundDevice = OmniDeviceType.UNKNOWN;
                                }
                            } else {
                                foundDevice = foundDevice2;
                            }
                        } else {
                            return;
                        }
                    }
                }
            }

            public void onBatchScanResults(List<ScanResult> results) {
                super.onBatchScanResults(results);
                Log.d(BLEScanManager.TAG, "Got batch results");
            }

            public void onScanFailed(int errorCode) {
                super.onScanFailed(errorCode);
                Log.d(BLEScanManager.TAG, "Got an error code during scan: " + errorCode);
            }
        };

        public ScanHandler21() {
            this.mLeScanner = BLEScanManager.this.mBluetoothAdapter.getBluetoothLeScanner();
        }

        public void startScan() {
            if (!BLEScanManager.this.isScanning) {
                boolean unused = BLEScanManager.this.isScanning = true;
                BLEScanManager.this.foundDevices.clear();
                BLEScanManager.this.foundDevicesRSSI.clear();
                BLEScanManager.this.omniObjects.clear();
                try {
                    this.mLeScanner.startScan(this.scanCallback);
                } catch (Exception excep) {
                    Log.e(BLEScanManager.TAG, "Got exception in start scan: " + excep);
                    boolean unused2 = BLEScanManager.this.isScanning = false;
                }
            }
        }

        public void stopScan() {
            if (BLEScanManager.this.isScanning) {
                boolean unused = BLEScanManager.this.isScanning = false;
                this.mLeScanner.stopScan(this.scanCallback);
            }
        }
    }

    /* access modifiers changed from: private */
    public ByteBuffer getMY16consoleId(byte[] scanRecord) {
        return getUniqueID(BLEUtil.getMFGData(scanRecord));
    }

    /* access modifiers changed from: private */
    public SparseArray getOmniAdData(byte[] scanRecord) {
        SparseArray omniAdInfo = new SparseArray(3);
        ByteBuffer omniAdData = BLEUtil.getMFGData(scanRecord);
        if (omniAdData == null) {
            return null;
        }
        byte[] adDataArray = omniAdData.array();
        byte consoleStatusByte = adDataArray[6];
        omniAdInfo.append(500, Integer.valueOf(consoleStatusByte & 3 & 255));
        omniAdInfo.append(CONSOLE_STATE, Integer.valueOf(((consoleStatusByte & OmniMY16Constants.MY16_CONSOLE_STATE_MASK) & 255) >> 4));
        omniAdInfo.append(CONSOLE_CONNECTABLE, Integer.valueOf((((byte) (adDataArray[7] & 255)) & 128) >> 7));
        return omniAdInfo;
    }
}
