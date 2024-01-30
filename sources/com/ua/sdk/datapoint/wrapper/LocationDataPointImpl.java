package com.ua.sdk.datapoint.wrapper;

import com.ua.sdk.datapoint.LocationDataPoint;
import java.util.Date;

public class LocationDataPointImpl implements LocationDataPoint {
    Double accuracy;
    Date dateTime;
    Double latitude;
    Double longitude;

    public LocationDataPointImpl(Double latitude2, Double longitude2, Double accuracy2, Date dateTime2) {
        this.dateTime = dateTime2;
        this.latitude = latitude2;
        this.longitude = longitude2;
        this.accuracy = accuracy2;
    }

    public Double getLatitude() {
        return this.latitude;
    }

    public Double getLongitude() {
        return this.longitude;
    }

    public Double getAccuracy() {
        return this.accuracy;
    }

    public Date getDateTime() {
        return this.dateTime;
    }
}
