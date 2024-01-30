package com.ua.sdk.recorder.datasource.sensor.bluetooth.services;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import com.ua.sdk.LocalDate;
import com.ua.sdk.UaLog;
import com.ua.sdk.heartrate.HeartRateZones;
import com.ua.sdk.recorder.datasource.sensor.bluetooth.BaseGattCallback;
import com.ua.sdk.recorder.datasource.sensor.bluetooth.BluetoothAction;
import com.ua.sdk.recorder.datasource.sensor.bluetooth.BluetoothActionQueue;
import com.ua.sdk.recorder.datasource.sensor.bluetooth.BluetoothClient;
import com.ua.sdk.recorder.datasource.sensor.bluetooth.GattAttributes;
import com.ua.sdk.user.User;
import java.util.Calendar;
import java.util.UUID;

@TargetApi(18)
public class BluetoothArmour39Service implements BaseGattCallback.GattCallbackListener {
    private BluetoothActionQueue actionQueue;
    private Integer age;
    private Integer gender = 0;
    private boolean isReconnect;
    private boolean isStarted;
    private BluetoothClient.BluetoothClientListener listener;
    private Integer maxHR = 185;
    private BluetoothGattCharacteristic sensorConnectStateCharacteristic;
    private BluetoothGattCharacteristic sensorStateCharacteristic;
    private BluetoothGattCharacteristic userDataCharacteristic;
    private Integer weight;
    private BluetoothGattCharacteristic workoutDataCharacteristic;

    public void setClientListener(BluetoothClient.BluetoothClientListener listener2) {
        this.listener = listener2;
    }

    public void startWorkout() {
        if (!this.isStarted && this.sensorStateCharacteristic != null) {
            this.actionQueue.addAction(new BluetoothAction(BluetoothAction.Action.READ, this.sensorStateCharacteristic, (byte[]) null));
        }
        this.isStarted = true;
    }

    public void stopWorkout() {
        UaLog.debug("workout stopped. changing sensor state to stopped");
        if (this.isStarted && this.sensorStateCharacteristic != null) {
            this.actionQueue.addAction(new BluetoothAction(BluetoothAction.Action.WRITE, this.sensorStateCharacteristic, new byte[]{0}));
        }
        this.isStarted = false;
    }

    public void disconnect() {
        UaLog.debug("workout disconnected. Disconnecting from armour39");
        if (this.sensorConnectStateCharacteristic != null) {
            this.actionQueue.addAction(new BluetoothAction(BluetoothAction.Action.WRITE, this.sensorConnectStateCharacteristic, new byte[]{123}));
        }
    }

    public void configureUser(User user, HeartRateZones heartRateZones) {
        if (user != null) {
            switch (user.getGender()) {
                case MALE:
                    this.gender = 0;
                    break;
                case FEMALE:
                    this.gender = 1;
                    break;
            }
            if (user.getWeight() != null) {
                this.weight = Integer.valueOf(user.getWeight().intValue());
            }
            if (user.getBirthdate() != null) {
                LocalDate birthDate = user.getBirthdate();
                Calendar today = Calendar.getInstance();
                this.age = Integer.valueOf(today.get(1) - birthDate.getYear());
                if (today.get(2) < birthDate.getMonth()) {
                    Integer num = this.age;
                    this.age = Integer.valueOf(this.age.intValue() - 1);
                } else if (today.get(2) == birthDate.getMonth() && today.get(5) < birthDate.getDayOfMonth()) {
                    Integer num2 = this.age;
                    this.age = Integer.valueOf(this.age.intValue() - 1);
                }
            }
        }
        if (heartRateZones != null) {
            this.maxHR = Integer.valueOf(heartRateZones.getZone(heartRateZones.getZones().size() - 1).getEnd());
        }
    }

    private void parseCharacteristic(BluetoothGattCharacteristic characteristic) {
        if (characteristic.getUuid().equals(UUID.fromString(GattAttributes.WORKOUT_DATA_CHARACTERISTIC))) {
            int steps = characteristic.getIntValue(18, 0).intValue();
            int offset = 0 + 2;
            int calories = characteristic.getIntValue(18, offset).intValue();
            int offset2 = offset + 2;
            double willpower = characteristic.getIntValue(18, offset2).doubleValue() / 1000.0d;
            int offset3 = offset2 + 2;
            this.listener.onArmour39Measurement((long) steps, (long) calories, willpower, (long) characteristic.getIntValue(17, offset3).intValue(), (long) characteristic.getIntValue(17, offset3 + 1).intValue());
        } else if (characteristic.getUuid().equals(UUID.fromString(GattAttributes.SENSOR_STATE_CHARACTERISTIC))) {
            byte[] value = characteristic.getValue();
            if (value[0] == 0 && this.isStarted) {
                UaLog.debug("workout started. changing sensor state to started");
                this.actionQueue.addAction(new BluetoothAction(BluetoothAction.Action.WRITE, this.sensorStateCharacteristic, new byte[]{1}));
            } else if (value[0] == 1 && !this.isReconnect && !this.isStarted) {
                UaLog.debug("Not a reconnect so zero out value");
                this.actionQueue.addAction(new BluetoothAction(BluetoothAction.Action.WRITE, this.sensorStateCharacteristic, new byte[]{0}));
                this.isReconnect = false;
            }
        }
    }

    public void setBluetoothActionQueue(BluetoothActionQueue actionQueue2) {
        this.actionQueue = actionQueue2;
    }

    public void onUnexpectedDisconnect() {
        this.isReconnect = true;
    }

    public void onServicesDiscovered(BluetoothGatt gatt, int status) {
        if (this.weight != null && this.age != null && status == 0 && gatt.getService(UUID.fromString(GattAttributes.ARMOUR39_SERVICE)) != null) {
            this.userDataCharacteristic = gatt.getService(UUID.fromString(GattAttributes.ARMOUR39_SERVICE)).getCharacteristic(UUID.fromString(GattAttributes.USER_DATA_CHARACTERISTIC));
            this.sensorStateCharacteristic = gatt.getService(UUID.fromString(GattAttributes.ARMOUR39_SERVICE)).getCharacteristic(UUID.fromString(GattAttributes.SENSOR_STATE_CHARACTERISTIC));
            this.sensorConnectStateCharacteristic = gatt.getService(UUID.fromString(GattAttributes.ARMOUR39_SERVICE)).getCharacteristic(UUID.fromString(GattAttributes.SENSOR_CONNECT_STATE_CHARACTERISTIC));
            this.workoutDataCharacteristic = gatt.getService(UUID.fromString(GattAttributes.ARMOUR39_SERVICE)).getCharacteristic(UUID.fromString(GattAttributes.WORKOUT_DATA_CHARACTERISTIC));
            this.actionQueue.addAction(new BluetoothAction(BluetoothAction.Action.WRITE, this.userDataCharacteristic, new byte[]{this.gender.byteValue(), this.weight.byteValue(), this.age.byteValue(), this.maxHR.byteValue()}));
            this.actionQueue.addAction(new BluetoothAction(BluetoothAction.Action.NOTIFY, this.workoutDataCharacteristic, BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE));
            this.actionQueue.addAction(new BluetoothAction(BluetoothAction.Action.READ, this.sensorStateCharacteristic, (byte[]) null));
        }
    }

    public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        if (status == 0 && gatt.getService(UUID.fromString(GattAttributes.ARMOUR39_SERVICE)) != null) {
            parseCharacteristic(characteristic);
        }
    }

    public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
    }

    public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
        if (gatt.getService(UUID.fromString(GattAttributes.ARMOUR39_SERVICE)) != null) {
            parseCharacteristic(characteristic);
        }
    }

    public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
    }

    public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
    }
}
