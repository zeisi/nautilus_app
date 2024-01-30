package com.ua.sdk.recorder.datasource.sensor.bluetooth;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattDescriptor;
import java.util.ArrayDeque;
import java.util.UUID;

@TargetApi(18)
public class BluetoothActionQueue {
    private final ArrayDeque<BluetoothAction> actions = new ArrayDeque<>(32);
    private BluetoothGatt gatt;

    public BluetoothActionQueue(BluetoothGatt gatt2) {
        this.gatt = gatt2;
    }

    public void addAction(BluetoothAction action) {
        this.actions.add(action);
    }

    /* access modifiers changed from: protected */
    public boolean isEmpty() {
        return this.actions.isEmpty();
    }

    /* access modifiers changed from: protected */
    public void poll() {
        synchronized (this.actions) {
            if (this.actions.peek() != null) {
                BluetoothAction action = this.actions.poll();
                switch (action.actionType) {
                    case WRITE:
                        action.characteristic.setValue(action.value);
                        this.gatt.writeCharacteristic(action.characteristic);
                        break;
                    case NOTIFY:
                        this.gatt.setCharacteristicNotification(action.characteristic, true);
                        BluetoothGattDescriptor descriptor = action.characteristic.getDescriptor(UUID.fromString("00002902-0000-1000-8000-00805f9b34fb"));
                        descriptor.setValue(action.value);
                        this.gatt.writeDescriptor(descriptor);
                        break;
                    case READ:
                        this.gatt.readCharacteristic(action.characteristic);
                        break;
                }
            }
        }
    }
}
