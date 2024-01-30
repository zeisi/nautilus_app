package com.ua.sdk.datapoint.wrapper;

import com.ua.sdk.datapoint.HeartRateDataPoint;
import java.util.Date;

public class HeartRateDataPointImpl implements HeartRateDataPoint {
    Date dateTime;
    Long hearRate;

    public HeartRateDataPointImpl(Long hearRate2, Date dateTime2) {
        this.dateTime = dateTime2;
        this.hearRate = hearRate2;
    }

    public Long getHeartRate() {
        return this.hearRate;
    }

    public Date getDateTime() {
        return this.dateTime;
    }
}
