package com.ua.sdk.recorder;

import com.ua.sdk.datasourceidentifier.DataSourceIdentifier;

public interface SensorDataSourceObserver {
    void onSensorDataSourceStatus(DataSourceIdentifier dataSourceIdentifier, SensorStatus sensorStatus, SensorHealth sensorHealth);
}
