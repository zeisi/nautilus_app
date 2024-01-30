package com.ua.sdk.recorder.datasource.sensor.location;

import com.google.gson.annotations.SerializedName;
import com.ua.sdk.datapoint.BaseDataTypes;
import com.ua.sdk.datapoint.DataTypeRef;
import com.ua.sdk.datasourceidentifier.DataSourceIdentifier;
import com.ua.sdk.recorder.LocationSensorDataSourceConfiguration;
import com.ua.sdk.recorder.datasource.AbstractDataSourceConfiguration;
import com.ua.sdk.recorder.datasource.DataSource;
import com.ua.sdk.recorder.datasource.sensor.SensorDataSource;
import com.ua.sdk.recorder.producer.SensorMessageProducer;
import java.util.ArrayList;
import java.util.List;

public class LocationSensorSensorDataSourceConfigurationImpl extends AbstractDataSourceConfiguration implements LocationSensorDataSourceConfiguration {
    @SerializedName("elevation_priority")
    public int elevationPriority;
    @SerializedName("location_priority")
    public int locationPriority;

    public LocationSensorDataSourceConfiguration setLocationPriority(int priority) {
        this.locationPriority = priority;
        return this;
    }

    public LocationSensorDataSourceConfiguration setElevationPriority(int priority) {
        this.elevationPriority = priority;
        return this;
    }

    public LocationSensorDataSourceConfiguration setDataSourceIdentifier(DataSourceIdentifier dataSourceIdentifier) {
        this.dataSourceIdentifier = dataSourceIdentifier;
        return this;
    }

    public DataSource build(SensorMessageProducer sensorMessageProducer, SensorDataSource.SensorDataSourceListener listener) {
        List<DataTypeRef> locationDataTypeRefs = new ArrayList<>();
        locationDataTypeRefs.add(BaseDataTypes.TYPE_LOCATION.getRef());
        locationDataTypeRefs.add(BaseDataTypes.TYPE_ELEVATION.getRef());
        return new LocationSensorDataSource(this.dataSourceIdentifier, locationDataTypeRefs, sensorMessageProducer, listener);
    }
}
