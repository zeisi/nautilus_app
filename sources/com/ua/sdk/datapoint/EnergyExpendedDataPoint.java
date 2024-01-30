package com.ua.sdk.datapoint;

import java.util.Date;

public interface EnergyExpendedDataPoint {
    Date getDateTime();

    Double getEnergyExpended();
}
