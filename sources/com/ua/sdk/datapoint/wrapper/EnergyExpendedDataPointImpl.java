package com.ua.sdk.datapoint.wrapper;

import com.ua.sdk.datapoint.EnergyExpendedDataPoint;
import java.util.Date;

public class EnergyExpendedDataPointImpl implements EnergyExpendedDataPoint {
    Date dateTime;
    Double energy;

    public EnergyExpendedDataPointImpl(Double energy2, Date dateTime2) {
        this.energy = energy2;
        this.dateTime = dateTime2;
    }

    public Double getEnergyExpended() {
        return this.energy;
    }

    public Date getDateTime() {
        return this.dateTime;
    }
}
