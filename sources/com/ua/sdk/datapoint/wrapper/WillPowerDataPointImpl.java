package com.ua.sdk.datapoint.wrapper;

import com.ua.sdk.datapoint.WillPowerDataPoint;
import java.util.Date;

public class WillPowerDataPointImpl implements WillPowerDataPoint {
    Date dateTime;
    Double willPower;

    public WillPowerDataPointImpl(Double willPower2, Date dateTime2) {
        this.dateTime = dateTime2;
        this.willPower = willPower2;
    }

    public Double getWillPower() {
        return this.willPower;
    }

    public Date getDateTime() {
        return this.dateTime;
    }
}
