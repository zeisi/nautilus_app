package com.ua.sdk.recorder.datasource.derived;

import com.ua.sdk.datapoint.BaseDataTypes;
import com.ua.sdk.datapoint.DataPoint;
import com.ua.sdk.datapoint.DataPointImpl;
import com.ua.sdk.datapoint.DataTypeRef;
import com.ua.sdk.datasourceidentifier.DataSourceIdentifier;
import com.ua.sdk.recorder.RecorderCalculator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BearingDerivedDataSource extends DerivedDataSource {
    private List<DataTypeRef> dataTypeRefTriggers = new ArrayList(Arrays.asList(new DataTypeRef[]{(DataTypeRef) BaseDataTypes.TYPE_LOCATION.getRef()}));
    private DataPoint previousDataPoint;

    protected BearingDerivedDataSource(DataSourceIdentifier dataSourceIdentifier, List<DataTypeRef> dataTypeRefs) {
        super(dataSourceIdentifier, dataTypeRefs);
    }

    public void deriveDataPoint(RecorderCalculator recorderCalculator, DataSourceIdentifier triggerDataSourceIdentifier, DataTypeRef triggerDataTypeRef, DataPoint triggerDataPoint) {
        if (this.dataTypeRefTriggers.contains(triggerDataTypeRef)) {
            if (this.previousDataPoint != null) {
                float[] distanceBearingResult = new float[2];
                DistanceBearingCalculator.calculateDistanceAndBearing(this.previousDataPoint.getValueDouble(BaseDataTypes.FIELD_LATITUDE).doubleValue(), this.previousDataPoint.getValueDouble(BaseDataTypes.FIELD_LONGITUDE).doubleValue(), triggerDataPoint.getValueDouble(BaseDataTypes.FIELD_LATITUDE).doubleValue(), triggerDataPoint.getValueDouble(BaseDataTypes.FIELD_LONGITUDE).doubleValue(), distanceBearingResult);
                DataPointImpl bearingDataPoint = new DataPointImpl();
                bearingDataPoint.setValue(BaseDataTypes.FIELD_BEARING, Double.valueOf((double) distanceBearingResult[1]));
                bearingDataPoint.setDatetime(triggerDataPoint.getDatetime());
                recorderCalculator.updateDataPoint(this.dataSourceIdentifier, bearingDataPoint, (DataTypeRef) BaseDataTypes.TYPE_BEARING.getRef());
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
