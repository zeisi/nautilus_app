package com.ua.sdk.datapoint.wrapper;

import com.ua.sdk.datapoint.RunCadenceDataPoint;
import java.util.Date;

public class RunCadenceDataPointImpl implements RunCadenceDataPoint {
    Long cadence;
    Date dateTime;

    public RunCadenceDataPointImpl(Long cadence2, Date dateTime2) {
        this.dateTime = dateTime2;
        this.cadence = cadence2;
    }

    public Long getRunCadence() {
        return this.cadence;
    }

    public Date getDateTime() {
        return this.dateTime;
    }
}
