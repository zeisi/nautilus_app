package com.ua.sdk.recorder.datasource.sensor.bluetooth.services;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import com.ua.sdk.recorder.datasource.sensor.bluetooth.BaseGattCallback;
import com.ua.sdk.recorder.datasource.sensor.bluetooth.BluetoothAction;
import com.ua.sdk.recorder.datasource.sensor.bluetooth.BluetoothActionQueue;
import com.ua.sdk.recorder.datasource.sensor.bluetooth.BluetoothClient;
import com.ua.sdk.recorder.datasource.sensor.bluetooth.GattAttributes;
import java.util.UUID;

@TargetApi(18)
public class BluetoothBatteryService implements BaseGattCallback.GattCallbackListener {
    private BluetoothActionQueue actionQueue;
    private BluetoothClient.BluetoothClientListener listener;

    public void setClientListener(BluetoothClient.BluetoothClientListener listener2) {
        this.listener = listener2;
    }

    public void setBluetoothActionQueue(BluetoothActionQueue actionQueue2) {
        this.actionQueue = actionQueue2;
    }

    public void onUnexpectedDisconnect() {
    }

    public void onServicesDiscovered(BluetoothGatt gatt, int status) {
        if (status == 0 && gatt.getService(UUID.fromString(GattAttributes.BATTERY_SERVICE)) != null) {
            BluetoothGattCharacteristic characteristic = gatt.getService(UUID.fromString(GattAttributes.BATTERY_SERVICE)).getCharacteristic(UUID.fromString(GattAttributes.BATTERY_LEVEL_CHARACTERISTIC));
            this.actionQueue.addAction(new BluetoothAction(BluetoothAction.Action.NOTIFY, characteristic, BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE));
            this.actionQueue.addAction(new BluetoothAction(BluetoothAction.Action.READ, characteristic));
        }
    }

    public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        if (status == 0) {
            parseCharacteristic(characteristic);
        }
    }

    public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
    }

    public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
        parseCharacteristic(characteristic);
    }

    public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
    }

    public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
    }

    private void parseCharacteristic(BluetoothGattCharacteristic characteristic) {
        if (characteristic.getUuid().equals(UUID.fromString(GattAttributes.BATTERY_LEVEL_CHARACTERISTIC))) {
            this.listener.onBatteryLevelMeasurement((long) characteristic.getIntValue(17, 0).intValue());
        }
    }
}
