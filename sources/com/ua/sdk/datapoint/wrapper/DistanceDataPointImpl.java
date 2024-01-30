package com.ua.sdk.datapoint.wrapper;

import com.ua.sdk.datapoint.DistanceDataPoint;
import java.util.Date;

public class DistanceDataPointImpl implements DistanceDataPoint {
    Date dateTime;
    Double distance;

    public DistanceDataPointImpl(Double distance2, Date dateTime2) {
        this.distance = distance2;
        this.dateTime = dateTime2;
    }

    public Double getDistance() {
        return this.distance;
    }

    public Date getDateTime() {
        return this.dateTime;
    }
}
