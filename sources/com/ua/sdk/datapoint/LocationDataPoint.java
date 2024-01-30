package com.ua.sdk.datapoint;

import java.util.Date;

public interface LocationDataPoint {
    Double getAccuracy();

    Date getDateTime();

    Double getLatitude();

    Double getLongitude();
}
