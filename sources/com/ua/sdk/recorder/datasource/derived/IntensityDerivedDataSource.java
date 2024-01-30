package com.ua.sdk.recorder.datasource.derived;

import com.ua.sdk.IntensityCalculator;
import com.ua.sdk.datapoint.BaseDataTypes;
import com.ua.sdk.datapoint.DataPoint;
import com.ua.sdk.datapoint.DataPointImpl;
import com.ua.sdk.datapoint.DataTypeRef;
import com.ua.sdk.datasourceidentifier.DataSourceIdentifier;
import com.ua.sdk.internal.IntensityCalculatorImpl;
import com.ua.sdk.recorder.RecorderCalculator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IntensityDerivedDataSource extends DerivedDataSource {
    private List<DataTypeRef> dataTypeRefTriggers = new ArrayList(Arrays.asList(new DataTypeRef[]{(DataTypeRef) BaseDataTypes.TYPE_HEART_RATE.getRef()}));
    private IntensityCalculator intensityCalculator = new IntensityCalculatorImpl();

    protected IntensityDerivedDataSource(DataSourceIdentifier dataSourceIdentifier, List<DataTypeRef> dataTypeRefs) {
        super(dataSourceIdentifier, dataTypeRefs);
    }

    public void deriveDataPoint(RecorderCalculator recorderCalculator, DataSourceIdentifier triggerDataSourceIdentifier, DataTypeRef triggerDataTypeRef, DataPoint triggerDataPoint) {
        if (this.dataTypeRefTriggers.contains(triggerDataTypeRef)) {
            double intensity = this.intensityCalculator.calculateCurrentIntensity(this.recorderContext.getHeartRateZones(), triggerDataPoint.getValueDouble(BaseDataTypes.FIELD_HEART_RATE).doubleValue());
            DataPointImpl intensityDataPoint = new DataPointImpl();
            intensityDataPoint.setValue(BaseDataTypes.FIELD_INTENSITY, Double.valueOf(intensity));
            intensityDataPoint.setDatetime(triggerDataPoint.getDatetime());
            recorderCalculator.updateDataPoint(this.dataSourceIdentifier, intensityDataPoint, (DataTypeRef) BaseDataTypes.TYPE_INTENSITY.getRef());
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
