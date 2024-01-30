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

public class HeartRateSummaryDerivedDataSource extends DerivedDataSource {
    private Average<Double> average = new Average<>();
    private List<DataTypeRef> dataTypeRefTriggers = new ArrayList(Arrays.asList(new DataTypeRef[]{(DataTypeRef) BaseDataTypes.TYPE_HEART_RATE.getRef()}));
    private boolean recordData = false;
    private DataPointImpl summaryDataPoint;

    protected HeartRateSummaryDerivedDataSource(DataSourceIdentifier dataSourceIdentifier, List<DataTypeRef> dataTypeRefs) {
        super(dataSourceIdentifier, dataTypeRefs);
    }

    public void deriveDataPoint(RecorderCalculator recorderCalculator, DataSourceIdentifier triggerDataSourceIdentifier, DataTypeRef triggerDataTypeRef, DataPoint triggerDataPoint) {
        if (this.dataTypeRefTriggers.contains(triggerDataTypeRef) && this.recordData) {
            if (this.summaryDataPoint == null) {
                this.summaryDataPoint = new DataPointImpl();
                this.summaryDataPoint.setStartDatetime(triggerDataPoint.getDatetime());
            }
            this.summaryDataPoint.setDatetime(triggerDataPoint.getDatetime());
            DataField max = BaseDataTypes.FIELD_HEART_RATE_MAX;
            DataField min = BaseDataTypes.FIELD_HEART_RATE_MIN;
            DataField avg = BaseDataTypes.FIELD_HEART_RATE_AVG;
            Double current = triggerDataPoint.getValueDouble(BaseDataTypes.FIELD_HEART_RATE);
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
            recorderCalculator.updateDataPoint(this.dataSourceIdentifier, this.summaryDataPoint, (DataTypeRef) BaseDataTypes.TYPE_HEART_RATE_SUMMARY.getRef());
        }
    }

    public List<DataTypeRef> getDataTypeRefTriggers() {
        return this.dataTypeRefTriggers;
    }

    public void connectDataSource() {
    }

    public void disconnectDataSource() {
        this.average.reset();
    }

    public void startSegment() {
        this.recordData = true;
    }

    public void stopSegment() {
        this.recordData = false;
    }
}
