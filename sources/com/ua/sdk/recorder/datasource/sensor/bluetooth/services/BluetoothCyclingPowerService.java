package com.ua.sdk.recorder.datasource.sensor.bluetooth.services;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.support.v4.view.MotionEventCompat;
import com.ua.sdk.recorder.datasource.sensor.bluetooth.BaseGattCallback;
import com.ua.sdk.recorder.datasource.sensor.bluetooth.BluetoothAction;
import com.ua.sdk.recorder.datasource.sensor.bluetooth.BluetoothActionQueue;
import com.ua.sdk.recorder.datasource.sensor.bluetooth.BluetoothClient;
import com.ua.sdk.recorder.datasource.sensor.bluetooth.GattAttributes;
import java.util.UUID;

@TargetApi(18)
public class BluetoothCyclingPowerService implements BaseGattCallback.GattCallbackListener {
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
        if (status == 0 && gatt.getService(UUID.fromString(GattAttributes.CYCLING_POWER_CHARACTERISTIC)) != null) {
            this.actionQueue.addAction(new BluetoothAction(BluetoothAction.Action.NOTIFY, gatt.getService(UUID.fromString(GattAttributes.CYCLING_POWER_SERVICE)).getCharacteristic(UUID.fromString(GattAttributes.CYCLING_POWER_CHARACTERISTIC)), BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE));
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
        if (UUID.fromString(GattAttributes.CYCLING_POWER_CHARACTERISTIC).equals(characteristic.getUuid())) {
            int flags = characteristic.getIntValue(18, 0).intValue();
            int offset = 0 + 2;
            int power = characteristic.getIntValue(34, offset).intValue();
            int offset2 = offset + 2;
            double pedalPowerBalance = -1.0d;
            if ((flags & 1) != 0) {
                pedalPowerBalance = ((double) characteristic.getIntValue(17, offset2).intValue()) / 2.0d;
                offset2++;
            }
            double accumulatedTorque = -1.0d;
            if ((flags & 4) != 0) {
                accumulatedTorque = ((double) characteristic.getIntValue(18, offset2).intValue()) / 32.0d;
                offset2 += 2;
            }
            long wheelRevs = -1;
            if ((flags & 16) != 0) {
                wheelRevs = (long) characteristic.getIntValue(20, offset2).intValue();
                offset2 += 4;
            }
            long crankRevs = -1;
            if ((flags & 32) != 0) {
                crankRevs = (long) characteristic.getIntValue(18, offset2).intValue();
                offset2 += 2;
            }
            long maxForce = -1;
            long minForce = -1;
            if ((flags & 64) != 0) {
                maxForce = (long) characteristic.getIntValue(34, offset2).intValue();
                int offset3 = offset2 + 2;
                minForce = (long) characteristic.getIntValue(34, offset3).intValue();
                offset2 = offset3 + 2;
            }
            double maxTorque = -1.0d;
            double minTorque = -1.0d;
            if ((flags & 128) != 0) {
                maxTorque = ((double) characteristic.getIntValue(34, offset2).intValue()) / 32.0d;
                int offset4 = offset2 + 2;
                minTorque = ((double) characteristic.getIntValue(34, offset4).intValue()) / 32.0d;
                offset2 = offset4 + 2;
            }
            long maxAngle = -1;
            long minAngle = -1;
            if ((flags & 256) != 0) {
                byte[] data = characteristic.getValue();
                byte data0 = data[offset2];
                byte data1 = data[offset2 + 1];
                maxAngle = (long) (data0 + (data1 & 255));
                minAngle = (long) (data[offset2 + 2] + (data1 & MotionEventCompat.ACTION_POINTER_INDEX_MASK));
                offset2 += 3;
            }
            long topDeadSpot = -1;
            if ((flags & 512) != 0) {
                topDeadSpot = (long) characteristic.getIntValue(18, offset2).intValue();
                offset2 += 2;
            }
            long bottomDeadSpot = -1;
            if ((flags & 1024) != 0) {
                bottomDeadSpot = (long) characteristic.getIntValue(18, offset2).intValue();
                offset2 += 2;
            }
            long joules = -1;
            if ((flags & 2048) != 0) {
                joules = (long) (characteristic.getIntValue(18, offset2).intValue() / 1000);
            }
            this.listener.onCyclingPowerMeasurement((long) power, pedalPowerBalance, accumulatedTorque, wheelRevs, crankRevs, maxForce, minForce, maxTorque, minTorque, maxAngle, minAngle, topDeadSpot, bottomDeadSpot, joules);
        }
    }
}
