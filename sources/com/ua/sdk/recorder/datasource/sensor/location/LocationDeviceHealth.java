package com.ua.sdk.recorder.datasource.sensor.location;

import com.ua.sdk.recorder.SensorHealth;
import com.ua.sdk.recorder.datasource.sensor.DeviceHealth;

public class LocationDeviceHealth extends DeviceHealth {
    private float accuracy;

    public void setAccuracy(float accuracy2) {
        this.accuracy = accuracy2;
    }

    public SensorHealth calculateOverallHealth() {
        if (this.accuracy > 400.0f) {
            return SensorHealth.VERY_BAD;
        }
        if (this.accuracy > 275.0f) {
            return SensorHealth.BAD;
        }
        if (this.accuracy > 150.0f) {
            return SensorHealth.AVERAGE;
        }
        if (this.accuracy > 50.0f) {
            return SensorHealth.GOOD;
        }
        if (this.accuracy > 0.0f) {
            return SensorHealth.VERY_GOOD;
        }
        return SensorHealth.UNKNOWN;
    }
}
