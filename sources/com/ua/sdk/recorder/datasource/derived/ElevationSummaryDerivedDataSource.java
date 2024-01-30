package com.ua.sdk.recorder.datasource.derived;

import com.ua.sdk.datapoint.BaseDataTypes;
import com.ua.sdk.datapoint.DataField;
import com.ua.sdk.datapoint.DataPoint;
import com.ua.sdk.datapoint.DataPointImpl;
import com.ua.sdk.datapoint.DataTypeRef;
import com.ua.sdk.datasourceidentifier.DataSourceIdentifier;
import com.ua.sdk.recorder.RecorderCalculator;
import com.ua.sdk.recorder.datasource.Average;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ElevationSummaryDerivedDataSource extends DerivedDataSource {
    private Average<Double> average = new Average<>();
    private List<DataTypeRef> dataTypeRefTriggers = new ArrayList(Arrays.asList(new DataTypeRef[]{(DataTypeRef) BaseDataTypes.TYPE_ELEVATION.getRef()}));
    private double gainMeters = 0.0d;
    private Double previousElevation = null;
    private boolean recordData = false;
    private DataPointImpl summaryDataPoint;

    protected ElevationSummaryDerivedDataSource(DataSourceIdentifier dataSourceIdentifier, List<DataTypeRef> dataTypeRefs) {
        super(dataSourceIdentifier, dataTypeRefs);
    }

    public void deriveDataPoint(RecorderCalculator recorderCalculator, DataSourceIdentifier triggerDataSourceIdentifier, DataTypeRef triggerDataTypeRef, DataPoint triggerDataPoint) {
        if (this.dataTypeRefTriggers.contains(triggerDataTypeRef) && this.recordData) {
            if (this.summaryDataPoint == null) {
                this.summaryDataPoint = new DataPointImpl();
                this.summaryDataPoint.setStartDatetime(triggerDataPoint.getDatetime());
            }
            this.summaryDataPoint.setDatetime(triggerDataPoint.getDatetime());
            DataField max = BaseDataTypes.FIELD_ELEVATION_MAX;
            DataField min = BaseDataTypes.FIELD_ELEVATION_MIN;
            DataField avg = BaseDataTypes.FIELD_ELEVATION_AVG;
            DataField gain = BaseDataTypes.FIELD_ELEVATION_GAIN;
            Double current = triggerDataPoint.getValueDouble(BaseDataTypes.FIELD_ELEVATION);
            Double currentMax = this.summaryDataPoint.getValueDouble(max);
            Double currentMin = this.summaryDataPoint.getValueDouble(min);
            if (currentMax == null || current.doubleValue() > currentMax.doubleValue()) {
                this.summaryDataPoint.setValue(max, current);
            }
            if (currentMin == null || current.doubleValue() < currentMin.doubleValue()) {
                this.summaryDataPoint.setValue(min, current);
            }
            this.average.addValue(current);
            this.summaryDataPoint.setValue(avg, Double.valueOf(this.average.getAverage()));
            if (this.previousElevation != null && this.previousElevation.doubleValue() < current.doubleValue()) {
                this.gainMeters += current.doubleValue() - this.previousElevation.doubleValue();
            }
            this.summaryDataPoint.setValue(gain, Double.valueOf(this.gainMeters));
            this.previousElevation = current;
            recorderCalculator.updateDataPoint(this.dataSourceIdentifier, this.summaryDataPoint, (DataTypeRef) BaseDataTypes.TYPE_ELEVATION_SUMMARY.getRef());
        }
    }

    public List<DataTypeRef> getDataTypeRefTriggers() {
        return this.dataTypeRefTriggers;
    }

    public void connectDataSource() {
    }

    public void disconnectDataSource() {
        this.average.reset();
        this.gainMeters = 0.0d;
    }

    public void startSegment() {
        this.recordData = true;
    }

    public void stopSegment() {
        this.recordData = false;
    }
}
