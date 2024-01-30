package com.ua.sdk.recorder.datasource.sensor.location;

import android.location.Location;
import android.location.LocationManager;
import com.ua.sdk.datapoint.BaseDataTypes;
import com.ua.sdk.datapoint.DataPointImpl;
import com.ua.sdk.datapoint.DataTypeRef;
import com.ua.sdk.datasourceidentifier.DataSourceIdentifier;
import com.ua.sdk.recorder.RecorderContext;
import com.ua.sdk.recorder.SensorHealth;
import com.ua.sdk.recorder.SensorStatus;
import com.ua.sdk.recorder.datasource.sensor.SensorDataSource;
import com.ua.sdk.recorder.datasource.sensor.location.LocationClient;
import com.ua.sdk.recorder.producer.SensorMessageProducer;
import java.util.Date;
import java.util.List;

public class LocationSensorDataSource extends SensorDataSource {
    /* access modifiers changed from: private */
    public LocationDeviceHealth deviceHealth = new LocationDeviceHealth();
    private LocationClient locationClient;

    public LocationSensorDataSource(DataSourceIdentifier dataSourceIdentifier, List<DataTypeRef> dataTypeRefs, SensorMessageProducer sensorMessageProducer, SensorDataSource.SensorDataSourceListener listener) {
        super(dataSourceIdentifier, dataTypeRefs, sensorMessageProducer, listener);
    }

    public void configure(RecorderContext recorderContext) {
        super.configure(recorderContext);
        this.locationClient = new AndroidLocationClient((LocationManager) recorderContext.getApplicationContext().getSystemService(BaseDataTypes.ID_LOCATION));
    }

    public void connectDataSource() {
        this.locationClient.connect(new MyLocationClientListener());
    }

    public void disconnectDataSource() {
        this.locationClient.disconnect();
    }

    public void startSegment() {
    }

    public void stopSegment() {
    }

    protected class MyLocationClientListener implements LocationClient.LocationClientListener {
        protected MyLocationClientListener() {
        }

        public void onLocation(Location location) {
            long timestampInMillis = ((long) LocationSensorDataSource.this.clock.getTimestamp()) * 1000;
            DataPointImpl dataPoint = new DataPointImpl();
            dataPoint.setDatetime(new Date(timestampInMillis));
            dataPoint.setValue(BaseDataTypes.FIELD_LATITUDE, Double.valueOf(location.getLatitude()));
            dataPoint.setValue(BaseDataTypes.FIELD_LONGITUDE, Double.valueOf(location.getLongitude()));
            dataPoint.setValue(BaseDataTypes.FIELD_HORIZONTAL_ACCURACY, Double.valueOf((double) location.getAccuracy()));
            LocationSensorDataSource.this.sendData(dataPoint, (DataTypeRef) BaseDataTypes.TYPE_LOCATION.getRef(), LocationSensorDataSource.this.dataSourceIdentifier);
            DataPointImpl elevationDataPoint = new DataPointImpl();
            elevationDataPoint.setDatetime(new Date(timestampInMillis));
            elevationDataPoint.setValue(BaseDataTypes.FIELD_ELEVATION, Double.valueOf(location.getAltitude()));
            elevationDataPoint.setValue(BaseDataTypes.FIELD_VERTICAL_ACCURACY, Double.valueOf((double) location.getAccuracy()));
            LocationSensorDataSource.this.sendData(elevationDataPoint, (DataTypeRef) BaseDataTypes.TYPE_ELEVATION.getRef(), LocationSensorDataSource.this.dataSourceIdentifier);
            DataPointImpl bearingDataPoint = new DataPointImpl();
            bearingDataPoint.setDatetime(new Date(timestampInMillis));
            bearingDataPoint.setValue(BaseDataTypes.FIELD_BEARING, Double.valueOf((double) location.getBearing()));
            LocationSensorDataSource.this.sendData(bearingDataPoint, (DataTypeRef) BaseDataTypes.TYPE_BEARING.getRef(), LocationSensorDataSource.this.dataSourceIdentifier);
            LocationSensorDataSource.this.deviceHealth.setAccuracy(location.getAccuracy());
        }

        public void onStatus(boolean gpsEnabled, boolean gpsFix, float accuracy) {
            SensorHealth unused = LocationSensorDataSource.this.sensorHealth = LocationSensorDataSource.this.deviceHealth.calculateOverallHealth();
            if (!gpsEnabled) {
                SensorStatus unused2 = LocationSensorDataSource.this.sensorStatus = SensorStatus.DISCONNECTED;
            } else if (gpsFix) {
                SensorStatus unused3 = LocationSensorDataSource.this.sensorStatus = SensorStatus.CONNECTED;
            } else {
                SensorStatus unused4 = LocationSensorDataSource.this.sensorStatus = SensorStatus.CONNECTING;
            }
            if (LocationSensorDataSource.this.sensorDataSourceListener != null) {
                LocationSensorDataSource.this.sensorDataSourceListener.onSensorStateChanged(LocationSensorDataSource.this.dataSourceIdentifier, LocationSensorDataSource.this.sensorStatus, LocationSensorDataSource.this.sensorHealth);
            }
        }
    }
}
