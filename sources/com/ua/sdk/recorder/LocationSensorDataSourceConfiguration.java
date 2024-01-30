package com.ua.sdk.recorder;

public interface LocationSensorDataSourceConfiguration extends DataSourceConfiguration<LocationSensorDataSourceConfiguration> {
    LocationSensorDataSourceConfiguration setElevationPriority(int i);

    LocationSensorDataSourceConfiguration setLocationPriority(int i);
}
