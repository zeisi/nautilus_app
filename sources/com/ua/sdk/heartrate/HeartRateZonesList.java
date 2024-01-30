package com.ua.sdk.heartrate;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.internal.AbstractEntityList;

public class HeartRateZonesList extends AbstractEntityList<HeartRateZones> {
    public static Parcelable.Creator<HeartRateZonesList> CREATOR = new Parcelable.Creator<HeartRateZonesList>() {
        public HeartRateZonesList createFromParcel(Parcel source) {
            return new HeartRateZonesList(source);
        }

        public HeartRateZonesList[] newArray(int size) {
            return new HeartRateZonesList[size];
        }
    };
    private static final String LIST_KEY = "heart_rate_zones";

    public HeartRateZonesList() {
    }

    private HeartRateZonesList(Parcel in) {
        super(in);
    }

    /* access modifiers changed from: protected */
    public String getListKey() {
        return LIST_KEY;
    }
}
