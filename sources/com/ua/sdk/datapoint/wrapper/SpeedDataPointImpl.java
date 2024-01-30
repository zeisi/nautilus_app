package com.ua.sdk.datapoint.wrapper;

import com.ua.sdk.datapoint.SpeedDataPoint;
import java.util.Date;

public class SpeedDataPointImpl implements SpeedDataPoint {
    Date dateTime;
    Double speed;

    public SpeedDataPointImpl(Double speed2, Date dateTime2) {
        this.dateTime = dateTime2;
        this.speed = speed2;
    }

    public Double getSpeed() {
        return this.speed;
    }

    public Date getDateTime() {
        return this.dateTime;
    }
}
