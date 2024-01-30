package com.ua.sdk.datapoint.wrapper;

import com.ua.sdk.datapoint.IntensityDataPoint;
import java.util.Date;

public class IntensityDataPointImpl implements IntensityDataPoint {
    Date dateTime;
    Double intensity;

    public IntensityDataPointImpl(Double intensity2, Date dateTime2) {
        this.dateTime = dateTime2;
        this.intensity = intensity2;
    }

    public Double getIntensity() {
        return this.intensity;
    }

    public Date getDateTime() {
        return this.dateTime;
    }
}
