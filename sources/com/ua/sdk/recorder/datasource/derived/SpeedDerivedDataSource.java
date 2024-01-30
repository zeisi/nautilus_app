package com.ua.sdk.recorder.datasource.derived;

import com.ua.sdk.datapoint.BaseDataTypes;
import com.ua.sdk.datapoint.DataPoint;
import com.ua.sdk.datapoint.DataPointImpl;
import com.ua.sdk.datapoint.DataTypeRef;
import com.ua.sdk.datasourceidentifier.DataSourceIdentifier;
import com.ua.sdk.recorder.RecorderCalculator;
import com.ua.sdk.recorder.datasource.RollingAverage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SpeedDerivedDataSource extends DerivedDataSource {
    private static final int ROLLING_AVG_SIZE = 8;
    private List<DataTypeRef> dataTypeRefTriggers = new ArrayList(Arrays.asList(new DataTypeRef[]{(DataTypeRef) BaseDataTypes.TYPE_LOCATION.getRef()}));
    private DataPoint previousDataPoint;
    private Double previousDistance;
    private RollingAverage<Double> rollingAverage = new RollingAverage<>(8);

    public SpeedDerivedDataSource(DataSourceIdentifier dataSourceIdentifier, List<DataTypeRef> dataTypeRefs) {
        super(dataSourceIdentifier, dataTypeRefs);
    }

    public void deriveDataPoint(RecorderCalculator recorderCalculator, DataSourceIdentifier triggerDataSourceIdentifier, DataTypeRef triggerDataTypeRef, DataPoint triggerDataPoint) {
        if (this.dataTypeRefTriggers.contains(triggerDataTypeRef)) {
            if (this.previousDataPoint != null) {
                float[] distanceBearingResult = new float[2];
                DistanceBearingCalculator.calculateDistanceAndBearing(this.previousDataPoint.getValueDouble(BaseDataTypes.FIELD_LATITUDE).doubleValue(), this.previousDataPoint.getValueDouble(BaseDataTypes.FIELD_LONGITUDE).doubleValue(), triggerDataPoint.getValueDouble(BaseDataTypes.FIELD_LATITUDE).doubleValue(), triggerDataPoint.getValueDouble(BaseDataTypes.FIELD_LONGITUDE).doubleValue(), distanceBearingResult);
                double curDistance = this.previousDistance == null ? 0.0d : this.previousDistance.doubleValue();
                double newDistance = curDistance + ((double) distanceBearingResult[0]);
                double speed = (newDistance - curDistance) / ((double) ((triggerDataPoint.getDatetime().getTime() - this.previousDataPoint.getDatetime().getTime()) / 1000));
                if (speed < 50.0d && !Double.isInfinite(speed) && !Double.isNaN(speed)) {
                    this.rollingAverage.addValue(Double.valueOf(speed));
                    double speed2 = this.rollingAverage.getAverage();
                    DataPointImpl speedDataPoint = new DataPointImpl();
                    speedDataPoint.setDatetime(triggerDataPoint.getDatetime());
                    speedDataPoint.setValue(BaseDataTypes.FIELD_SPEED, Double.valueOf(speed2));
                    recorderCalculator.updateDataPoint(this.dataSourceIdentifier, speedDataPoint, (DataTypeRef) BaseDataTypes.TYPE_SPEED.getRef());
                }
                this.previousDistance = Double.valueOf(newDistance);
            }
            this.previousDataPoint = triggerDataPoint;
        }
    }

    public List<DataTypeRef> getDataTypeRefTriggers() {
        return this.dataTypeRefTriggers;
    }

    public void connectDataSource() {
    }

    public void disconnectDataSource() {
    }

    public void startSegment() {
    }

    public void stopSegment() {
    }
}
