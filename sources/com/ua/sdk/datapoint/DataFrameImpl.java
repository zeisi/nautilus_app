package com.ua.sdk.datapoint;

import com.ua.sdk.datapoint.wrapper.DistanceDataPointImpl;
import com.ua.sdk.datapoint.wrapper.EnergyExpendedDataPointImpl;
import com.ua.sdk.datapoint.wrapper.HeartRateDataPointImpl;
import com.ua.sdk.datapoint.wrapper.IntensityDataPointImpl;
import com.ua.sdk.datapoint.wrapper.LocationDataPointImpl;
import com.ua.sdk.datapoint.wrapper.RunCadenceDataPointImpl;
import com.ua.sdk.datapoint.wrapper.SpeedDataPointImpl;
import com.ua.sdk.datapoint.wrapper.WillPowerDataPointImpl;
import com.ua.sdk.datasourceidentifier.DataSourceIdentifier;
import com.ua.sdk.recorder.TypeSourceKey;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DataFrameImpl implements DataFrame {
    private Double activeTime;
    private Map<String, DataPoint> dataPoints;
    private List<DataSourceIdentifier> dataSourceIdentifiersChanged;
    private Set<DataTypeRef> dataTypesChanged;
    private Double elapsedTime;
    private Double firstSegmentStartTime;
    private boolean isSegmentStarted;
    private Map<DataTypeRef, DataSourceIdentifier> primaryDataSources;

    public Double getFirstSegmentStartTime() {
        return this.firstSegmentStartTime;
    }

    public void setFirstSegmentStartTime(Double firstSegmentStartTime2) {
        this.firstSegmentStartTime = firstSegmentStartTime2;
    }

    public Double getElapsedTime() {
        return this.elapsedTime;
    }

    public void setElapsedTime(Double elapsedTime2) {
        this.elapsedTime = elapsedTime2;
    }

    public Double getActiveTime() {
        return this.activeTime;
    }

    public void setActiveTime(Double activeTime2) {
        this.activeTime = activeTime2;
    }

    public boolean isSegmentStarted() {
        return this.isSegmentStarted;
    }

    public void setSegmentStarted(boolean isSegmentStarted2) {
        this.isSegmentStarted = isSegmentStarted2;
    }

    public DataPoint getDataPoint(DataTypeRef dataTypeRef) {
        DataSourceIdentifier dataSourceIdentifier = this.primaryDataSources.get(dataTypeRef);
        if (dataSourceIdentifier == null) {
            return null;
        }
        return this.dataPoints.get(TypeSourceKey.createDataPointKey(dataTypeRef, dataSourceIdentifier));
    }

    public DataPoint getDataPoint(DataTypeRef dataTypeRef, DataSourceIdentifier dataSourceIdentifier) {
        return this.dataPoints.get(TypeSourceKey.createDataPointKey(dataTypeRef, dataSourceIdentifier));
    }

    public Map<String, DataPoint> getDataPoints() {
        return this.dataPoints;
    }

    public void setDataPoints(Map<String, DataPoint> dataPoints2) {
        this.dataPoints = dataPoints2;
    }

    public void setPrimaryDataSources(Map<DataTypeRef, DataSourceIdentifier> primaryDataSources2) {
        this.primaryDataSources = primaryDataSources2;
    }

    public Map<DataTypeRef, DataSourceIdentifier> getPrimaryDataSources() {
        return this.primaryDataSources;
    }

    public Set<DataTypeRef> getDataTypesChanged() {
        return this.dataTypesChanged;
    }

    public void setDataTypesChanged(Set<DataTypeRef> dataTypesChanged2) {
        this.dataTypesChanged = dataTypesChanged2;
    }

    public List<DataSourceIdentifier> getDataSourceIdentifiersChanged() {
        return this.dataSourceIdentifiersChanged;
    }

    public void setDataSourceIdentifiersChanged(List<DataSourceIdentifier> dataSourceIdentifiersChanged2) {
        this.dataSourceIdentifiersChanged = dataSourceIdentifiersChanged2;
    }

    public DistanceDataPoint getDistanceDataPoint(DataSourceIdentifier dataSourceIdentifier) {
        DataPoint dataPoint = getDataPoint((DataTypeRef) BaseDataTypes.TYPE_DISTANCE.getRef(), dataSourceIdentifier);
        if (dataPoint != null) {
            return new DistanceDataPointImpl(dataPoint.getValueDouble(BaseDataTypes.FIELD_DISTANCE), dataPoint.getDatetime());
        }
        return null;
    }

    public DistanceDataPoint getDistanceDataPoint() {
        DataPoint dataPoint = getDataPoint((DataTypeRef) BaseDataTypes.TYPE_DISTANCE.getRef());
        if (dataPoint != null) {
            return new DistanceDataPointImpl(dataPoint.getValueDouble(BaseDataTypes.FIELD_DISTANCE), dataPoint.getDatetime());
        }
        return null;
    }

    public EnergyExpendedDataPoint getEnergyExpendedDataPoint(DataSourceIdentifier dataSourceIdentifier) {
        DataPoint dataPoint = getDataPoint((DataTypeRef) BaseDataTypes.TYPE_ENERGY_EXPENDED.getRef(), dataSourceIdentifier);
        if (dataPoint != null) {
            return new EnergyExpendedDataPointImpl(dataPoint.getValueDouble(BaseDataTypes.FIELD_ENERGY_EXPENDED), dataPoint.getDatetime());
        }
        return null;
    }

    public EnergyExpendedDataPoint getEnergyExpendedDataPoint() {
        DataPoint dataPoint = getDataPoint((DataTypeRef) BaseDataTypes.TYPE_ENERGY_EXPENDED.getRef());
        if (dataPoint != null) {
            return new EnergyExpendedDataPointImpl(dataPoint.getValueDouble(BaseDataTypes.FIELD_ENERGY_EXPENDED), dataPoint.getDatetime());
        }
        return null;
    }

    public HeartRateDataPoint getHeartRateDataPoint(DataSourceIdentifier dataSourceIdentifier) {
        DataPoint dataPoint = getDataPoint((DataTypeRef) BaseDataTypes.TYPE_HEART_RATE.getRef(), dataSourceIdentifier);
        if (dataPoint != null) {
            return new HeartRateDataPointImpl(dataPoint.getValueLong(BaseDataTypes.FIELD_HEART_RATE), dataPoint.getDatetime());
        }
        return null;
    }

    public HeartRateDataPoint getHeartRateDataPoint() {
        DataPoint dataPoint = getDataPoint((DataTypeRef) BaseDataTypes.TYPE_HEART_RATE.getRef());
        if (dataPoint != null) {
            return new HeartRateDataPointImpl(dataPoint.getValueLong(BaseDataTypes.FIELD_HEART_RATE), dataPoint.getDatetime());
        }
        return null;
    }

    public IntensityDataPoint getIntensityDataPoint(DataSourceIdentifier dataSourceIdentifier) {
        DataPoint dataPoint = getDataPoint((DataTypeRef) BaseDataTypes.TYPE_INTENSITY.getRef(), dataSourceIdentifier);
        if (dataPoint != null) {
            return new IntensityDataPointImpl(dataPoint.getValueDouble(BaseDataTypes.FIELD_INTENSITY), dataPoint.getDatetime());
        }
        return null;
    }

    public IntensityDataPoint getIntensityDataPoint() {
        DataPoint dataPoint = getDataPoint((DataTypeRef) BaseDataTypes.TYPE_INTENSITY.getRef());
        if (dataPoint != null) {
            return new IntensityDataPointImpl(dataPoint.getValueDouble(BaseDataTypes.FIELD_INTENSITY), dataPoint.getDatetime());
        }
        return null;
    }

    public LocationDataPoint getLocationDataPoint(DataSourceIdentifier dataSourceIdentifier) {
        DataPoint dataPoint = getDataPoint((DataTypeRef) BaseDataTypes.TYPE_LOCATION.getRef(), dataSourceIdentifier);
        if (dataPoint != null) {
            return new LocationDataPointImpl(dataPoint.getValueDouble(BaseDataTypes.FIELD_LATITUDE), dataPoint.getValueDouble(BaseDataTypes.FIELD_LONGITUDE), dataPoint.getValueDouble(BaseDataTypes.FIELD_HORIZONTAL_ACCURACY), dataPoint.getDatetime());
        }
        return null;
    }

    public LocationDataPoint getLocationDataPoint() {
        DataPoint dataPoint = getDataPoint((DataTypeRef) BaseDataTypes.TYPE_LOCATION.getRef());
        if (dataPoint != null) {
            return new LocationDataPointImpl(dataPoint.getValueDouble(BaseDataTypes.FIELD_LATITUDE), dataPoint.getValueDouble(BaseDataTypes.FIELD_LONGITUDE), dataPoint.getValueDouble(BaseDataTypes.FIELD_HORIZONTAL_ACCURACY), dataPoint.getDatetime());
        }
        return null;
    }

    public RunCadenceDataPoint getRunCadenceDataPoint(DataSourceIdentifier dataSourceIdentifier) {
        DataPoint dataPoint = getDataPoint((DataTypeRef) BaseDataTypes.TYPE_RUN_CADENCE.getRef(), dataSourceIdentifier);
        if (dataPoint != null) {
            return new RunCadenceDataPointImpl(dataPoint.getValueLong(BaseDataTypes.FIELD_RUN_CADENCE), dataPoint.getDatetime());
        }
        return null;
    }

    public RunCadenceDataPoint getRunCadenceDataPoint() {
        DataPoint dataPoint = getDataPoint((DataTypeRef) BaseDataTypes.TYPE_RUN_CADENCE.getRef());
        if (dataPoint != null) {
            return new RunCadenceDataPointImpl(dataPoint.getValueLong(BaseDataTypes.FIELD_RUN_CADENCE), dataPoint.getDatetime());
        }
        return null;
    }

    public SpeedDataPoint getSpeedDataPoint(DataSourceIdentifier dataSourceIdentifier) {
        DataPoint dataPoint = getDataPoint((DataTypeRef) BaseDataTypes.TYPE_SPEED.getRef(), dataSourceIdentifier);
        if (dataPoint != null) {
            return new SpeedDataPointImpl(dataPoint.getValueDouble(BaseDataTypes.FIELD_SPEED), dataPoint.getDatetime());
        }
        return null;
    }

    public SpeedDataPoint getSpeedDataPoint() {
        DataPoint dataPoint = getDataPoint((DataTypeRef) BaseDataTypes.TYPE_SPEED.getRef());
        if (dataPoint != null) {
            return new SpeedDataPointImpl(dataPoint.getValueDouble(BaseDataTypes.FIELD_SPEED), dataPoint.getDatetime());
        }
        return null;
    }

    public WillPowerDataPoint getWillPowerDataPoint(DataSourceIdentifier dataSourceIdentifier) {
        DataPoint dataPoint = getDataPoint((DataTypeRef) BaseDataTypes.TYPE_WILLPOWER.getRef(), dataSourceIdentifier);
        if (dataPoint != null) {
            return new WillPowerDataPointImpl(dataPoint.getValueDouble(BaseDataTypes.FIELD_WILLPOWER), dataPoint.getDatetime());
        }
        return null;
    }

    public WillPowerDataPoint getWillPowerDataPoint() {
        DataPoint dataPoint = getDataPoint((DataTypeRef) BaseDataTypes.TYPE_WILLPOWER.getRef());
        if (dataPoint != null) {
            return new WillPowerDataPointImpl(dataPoint.getValueDouble(BaseDataTypes.FIELD_WILLPOWER), dataPoint.getDatetime());
        }
        return null;
    }
}
