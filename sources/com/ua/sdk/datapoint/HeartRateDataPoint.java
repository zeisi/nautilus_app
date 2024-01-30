package com.ua.sdk.datapoint;

import java.util.Date;

public interface HeartRateDataPoint {
    Date getDateTime();

    Long getHeartRate();
}
