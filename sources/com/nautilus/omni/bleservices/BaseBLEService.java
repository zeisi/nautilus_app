package com.nautilus.omni.bleservices;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import com.nautilus.omni.bleservices.ble.OmniDevice;
import com.nautilus.omni.bleservices.ble.omnitrainer.OmniConnectionState;

public class BaseBLEService extends Service {
    /* access modifiers changed from: private */
    public static final String TAG = BaseBLEService.class.getSimpleName();
    protected OmniCallbacks clientDelegate;
    protected OmniDevice mBLEDevice = null;
    protected Binder mBinder;
    protected BluetoothAdapter mBluetoothAdapter;
    protected String mBluetoothDeviceAddress;
    protected BluetoothGatt mBluetoothGatt;
    protected String mDeviceAddress;
    private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            if (status != 0) {
                Log.d(BaseBLEService.TAG, "*****************************");
                Log.d(BaseBLEService.TAG, "Services error: " + status);
                Log.d(BaseBLEService.TAG, "*****************************");
                BaseBLEService.this.executeDeviceDisconnection();
            } else if (newState == 2) {
                BaseBLEService.this.mBLEDevice.setDeviceStatus(OmniConnectionState.CONNECTED_NO_BLE);
                BaseBLEService.this.mBLEDevice.setDeviceName(gatt.getDevice().getName());
                BaseBLEService.this.mBLEDevice.setParingInProcess(false);
                Log.d(BaseBLEService.TAG, "Device is connected");
                gatt.discoverServices();
                Log.d(BaseBLEService.TAG, "Discovering services.");
            } else if (newState == 0) {
                BaseBLEService.this.mBLEDevice.setDeviceStatus(OmniConnectionState.DISCONNECTED);
                BaseBLEService.this.mBLEDevice.setHaveBLE(false);
                BaseBLEService.this.mBLEDevice.setParingInProcess(false);
                BaseBLEService.this.close();
                Log.d(BaseBLEService.TAG, "*****************************");
                Log.d(BaseBLEService.TAG, "Device is disconnected");
                Log.d(BaseBLEService.TAG, "*****************************");
                if (BaseBLEService.this.clientDelegate != null) {
                    BaseBLEService.this.clientDelegate.nautilusDeviceConnectionStateChange(OmniConnectionState.DISCONNECTED);
                }
            }
        }

        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            super.onServicesDiscovered(gatt, status);
            Log.d(BaseBLEService.TAG, "Base services discovered");
            BaseBLEService.this.onBLEServicesDiscovered(gatt, status);
        }

        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicRead(gatt, characteristic, status);
            BaseBLEService.this.onBLECharacteristicRead(gatt, characteristic, status);
        }

        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicWrite(gatt, characteristic, status);
            BaseBLEService.this.onBLECharacteristicWrite(gatt, characteristic, status);
        }

        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorWrite(gatt, descriptor, status);
            BaseBLEService.this.onBLEDescriptorWrite(gatt, descriptor, status);
        }

        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            super.onCharacteristicChanged(gatt, characteristic);
            Log.d(BaseBLEService.TAG, "Base Characteristics have changed");
            BaseBLEService.this.onBLECharacteristicChanged(gatt, characteristic);
        }
    };

    public OmniDevice getBLEDevice() {
        return this.mBLEDevice;
    }

    public void executeDeviceDisconnection() {
        if (this.clientDelegate != null) {
            this.clientDelegate.nautilusDeviceConnectionStateChange(OmniConnectionState.ERROR);
        }
        disconnectFromBLEDevice();
        close();
        if (this.mBLEDevice != null) {
            this.mBLEDevice.setDeviceStatus(OmniConnectionState.DISCONNECTED);
            this.mBLEDevice.setParingInProcess(false);
        }
    }

    public void onBLEServicesDiscovered(BluetoothGatt gatt, int status) {
    }

    public void onBLECharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
    }

    public void onBLECharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
    }

    public void onBLEDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
    }

    public void onBLECharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
    }

    public IBinder onBind(Intent intent) {
        return this.mBinder;
    }

    public boolean onUnbind(Intent intent) {
        close();
        return super.onUnbind(intent);
    }

    public boolean connectToBLEDevice(OmniDevice deviceToConnect) {
        BluetoothDevice currentDevice;
        BluetoothGatt currentGhatt;
        if (this.mBluetoothAdapter == null || deviceToConnect == null) {
            Log.w(TAG, "Bluetooth not initialized or no device to connect to");
            return false;
        }
        if (!(this.mBLEDevice == null || (currentDevice = this.mBLEDevice.getBluetoothDevice()) == null)) {
            if (this.mBLEDevice.getDeviceStatus() == OmniConnectionState.CONNECTED_NO_BLE || this.mBLEDevice.getDeviceStatus() == OmniConnectionState.CONNECTING) {
                if (currentDevice.equals(deviceToConnect.getBluetoothDevice())) {
                    return true;
                }
                Log.d(TAG, "Connect called to a different device while already connecting or connected");
                return false;
            } else if (this.mBLEDevice.equals(deviceToConnect) && (currentGhatt = this.mBLEDevice.getBluetoothGhatt()) != null) {
                if (currentGhatt.connect()) {
                    this.mBLEDevice.setDeviceStatus(OmniConnectionState.CONNECTING);
                    return true;
                }
                Log.d(TAG, "Tried to reconnect but failed");
                return false;
            }
        }
        this.mBLEDevice = deviceToConnect;
        BluetoothDevice currentDevice2 = this.mBLEDevice.getBluetoothDevice();
        if (currentDevice2 != null) {
            this.mBLEDevice.setDeviceStatus(OmniConnectionState.CONNECTING);
            this.mBluetoothGatt = currentDevice2.connectGatt(this, false, this.mGattCallback);
            this.mBLEDevice.setBluetoothGhatt(this.mBluetoothGatt);
            Log.d(TAG, "Trying to create a new connection.");
            return true;
        }
        Log.d(TAG, "Bad BT device in connect");
        return false;
    }

    public void disconnectFromBLEDevice() {
        if (this.mBluetoothAdapter == null || this.mBluetoothGatt == null || this.mBLEDevice == null) {
            Log.w(TAG, "Disconnect called but BT is not setup");
        } else if (this.mBLEDevice.getDeviceStatus() == OmniConnectionState.CONNECTED_NO_BLE || this.mBLEDevice.getDeviceStatus() == OmniConnectionState.CONNECTED_WITH_BLE) {
            this.mBLEDevice.setHaveBLE(false);
            this.mBLEDevice.setDeviceStatus(OmniConnectionState.DISCONNECTED);
            this.mBluetoothGatt.disconnect();
        } else {
            Log.d(TAG, "Disconnect called when not connected");
        }
    }

    public void close() {
        if (this.mBluetoothGatt != null) {
            this.mBluetoothGatt.close();
            this.mBluetoothGatt = null;
            if (this.mBLEDevice != null) {
                this.mBLEDevice.setBluetoothGhatt((BluetoothGatt) null);
                this.mBLEDevice.setBluetoothDevice((BluetoothDevice) null);
            }
        }
    }

    public void onDestroy() {
        super.onDestroy();
        disconnectFromBLEDevice();
        close();
    }
}
