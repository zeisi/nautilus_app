package com.ua.sdk.recorder.datasource.derived;

import com.ua.sdk.MetabolicEnergyCalculator;
import com.ua.sdk.UaLog;
import com.ua.sdk.datapoint.BaseDataTypes;
import com.ua.sdk.datapoint.DataPoint;
import com.ua.sdk.datapoint.DataPointImpl;
import com.ua.sdk.datapoint.DataTypeRef;
import com.ua.sdk.datasourceidentifier.DataSourceIdentifier;
import com.ua.sdk.internal.MetabolicEnergyCalculatorImpl;
import com.ua.sdk.recorder.RecorderCalculator;
import com.ua.sdk.recorder.RecorderContext;
import com.ua.sdk.user.User;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EnergyExpendedDerivedDataSource extends DerivedDataSource {
    private List<DataTypeRef> dataTypeRefTriggers = new ArrayList(Arrays.asList(new DataTypeRef[]{(DataTypeRef) BaseDataTypes.TYPE_DISTANCE.getRef()}));
    private boolean hasUserError;
    private boolean isStarted;
    private MetabolicEnergyCalculator metabolicEnergyCalculator = new MetabolicEnergyCalculatorImpl();

    public EnergyExpendedDerivedDataSource(DataSourceIdentifier dataSourceIdentifier, List<DataTypeRef> dataTypeRefs) {
        super(dataSourceIdentifier, dataTypeRefs);
    }

    public void deriveDataPoint(RecorderCalculator recorderCalculator, DataSourceIdentifier triggerDataSourceIdentifier, DataTypeRef triggerDataTypeRef, DataPoint triggerDataPoint) {
        if (this.dataTypeRefTriggers.contains(triggerDataTypeRef) && this.isStarted && !this.hasUserError) {
            double metabolicEnergy = this.metabolicEnergyCalculator.calculateJoules(this.recorderContext.getUser(), this.recorderContext.getActivityType(), recorderCalculator.getElapsedTime(), triggerDataPoint.getValueDouble(BaseDataTypes.FIELD_DISTANCE).doubleValue());
            DataPointImpl energyDataPoint = new DataPointImpl();
            energyDataPoint.setDatetime(triggerDataPoint.getDatetime());
            energyDataPoint.setValue(BaseDataTypes.FIELD_ENERGY_EXPENDED, Double.valueOf(metabolicEnergy));
            recorderCalculator.updateDataPoint(this.dataSourceIdentifier, energyDataPoint, (DataTypeRef) BaseDataTypes.TYPE_ENERGY_EXPENDED.getRef());
        }
    }

    public void configure(RecorderContext recorderContext) {
        super.configure(recorderContext);
        User user = recorderContext.getUser();
        this.hasUserError = false;
        if (user.getHeight() == null) {
            UaLog.error("The user has no registered height. Energy will not be calculated.");
            this.hasUserError = true;
        }
        if (user.getWeight() == null) {
            UaLog.error("The user has no registered weight. Energy will not be calculated.");
            this.hasUserError = true;
        }
        if (user.getBirthdate() == null) {
            UaLog.error("The user has no registered birth date. Energy will not be calculated.");
            this.hasUserError = true;
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
        this.isStarted = true;
    }

    public void stopSegment() {
        this.isStarted = false;
    }
}
