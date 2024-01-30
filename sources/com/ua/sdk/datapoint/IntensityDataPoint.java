package com.ua.sdk.datapoint;

import java.util.Date;

public interface IntensityDataPoint {
    Date getDateTime();

    Double getIntensity();
}
