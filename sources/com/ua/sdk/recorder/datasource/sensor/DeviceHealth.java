package com.ua.sdk.recorder.datasource.sensor;

import com.ua.sdk.recorder.SensorHealth;

public abstract class DeviceHealth {
    public abstract SensorHealth calculateOverallHealth();
}
