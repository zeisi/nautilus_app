package com.ua.sdk.recorder.datasource.sensor.bluetooth;

import android.bluetooth.BluetoothGattCharacteristic;

public class BluetoothAction {
    protected Action actionType;
    protected BluetoothGattCharacteristic characteristic;
    protected byte[] value;

    public enum Action {
        READ,
        NOTIFY,
        WRITE
    }

    public BluetoothAction(Action action, BluetoothGattCharacteristic characteristic2, byte[] value2) {
        this.actionType = action;
        this.characteristic = characteristic2;
        this.value = value2;
    }

    public BluetoothAction(Action action, BluetoothGattCharacteristic characteristic2) {
        this(action, characteristic2, (byte[]) null);
    }
}
