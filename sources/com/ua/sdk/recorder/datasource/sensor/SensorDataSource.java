package com.ua.sdk.recorder.datasource.sensor;

import com.ua.sdk.datapoint.DataPoint;
import com.ua.sdk.datapoint.DataTypeRef;
import com.ua.sdk.datasourceidentifier.DataSourceIdentifier;
import com.ua.sdk.recorder.SensorHealth;
import com.ua.sdk.recorder.SensorStatus;
import com.ua.sdk.recorder.datasource.DataSource;
import com.ua.sdk.recorder.producer.SensorMessageProducer;
import java.util.List;

public abstract class SensorDataSource extends DataSource {
    protected SensorDataSourceListener sensorDataSourceListener;
    protected SensorHealth sensorHealth = SensorHealth.UNKNOWN;
    protected SensorMessageProducer sensorMessageProducer;
    protected SensorStatus sensorStatus = SensorStatus.DISCONNECTED;

    public interface SensorDataSourceListener {
        void onSensorStateChanged(DataSourceIdentifier dataSourceIdentifier, SensorStatus sensorStatus, SensorHealth sensorHealth);
    }

    public SensorDataSource(DataSourceIdentifier dataSourceIdentifier, List<DataTypeRef> dataTypeRefs, SensorMessageProducer sensorMessageProducer2, SensorDataSourceListener listener) {
        super(dataSourceIdentifier, dataTypeRefs);
        this.sensorMessageProducer = sensorMessageProducer2;
        this.sensorDataSourceListener = listener;
    }

    public void sendData(DataPoint dataPoint, DataTypeRef dataTypeRef, DataSourceIdentifier dataSourceIdentifier) {
        this.sensorMessageProducer.dealWithIt(dataSourceIdentifier, dataPoint, dataTypeRef);
    }

    public SensorStatus getSensorStatus() {
        return this.sensorStatus;
    }

    public SensorHealth getSensorHealth() {
        return this.sensorHealth;
    }
}
