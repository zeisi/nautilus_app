package com.ua.sdk.recorder.datasource.sensor.bluetooth;

import com.ua.sdk.recorder.SensorHealth;
import com.ua.sdk.recorder.datasource.sensor.DeviceHealth;

public class BluetoothDeviceHealth extends DeviceHealth {
    private int batteryRemaining;
    private int rssi;

    public void setRssi(int rssi2) {
        this.rssi = rssi2;
    }

    public void setBatteryRemaining(int batteryRemaining2) {
        this.batteryRemaining = batteryRemaining2;
    }

    public SensorHealth calculateOverallHealth() {
        SensorHealth health;
        int signalQuality = calculateRssi();
        if (signalQuality >= 70) {
            health = SensorHealth.VERY_GOOD;
        } else if (signalQuality >= 50) {
            health = SensorHealth.GOOD;
        } else if (signalQuality >= 30) {
            health = SensorHealth.AVERAGE;
        } else if (signalQuality >= 20) {
            health = SensorHealth.BAD;
        } else {
            health = SensorHealth.VERY_BAD;
        }
        if (this.batteryRemaining > 25) {
            return health;
        }
        int ordinal = health.ordinal();
        if (ordinal < 4) {
            ordinal++;
        }
        if (this.batteryRemaining <= 10 && ordinal < 4) {
            ordinal++;
        }
        return SensorHealth.values()[ordinal];
    }

    private int calculateRssi() {
        if (this.rssi <= -100) {
            return 0;
        }
        if (this.rssi >= -50) {
            return 100;
        }
        return (this.rssi + 100) * 2;
    }
}
