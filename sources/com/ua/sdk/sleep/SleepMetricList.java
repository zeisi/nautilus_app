package com.ua.sdk.sleep;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.internal.AbstractEntityList;

public class SleepMetricList extends AbstractEntityList<SleepMetric> {
    public static Parcelable.Creator<SleepMetricList> CREATOR = new Parcelable.Creator<SleepMetricList>() {
        public SleepMetricList createFromParcel(Parcel source) {
            return new SleepMetricList(source);
        }

        public SleepMetricList[] newArray(int size) {
            return new SleepMetricList[size];
        }
    };
    private static final String LIST_KEY = "sleeps";

    /* access modifiers changed from: protected */
    public String getListKey() {
        return LIST_KEY;
    }

    public SleepMetricList() {
    }

    private SleepMetricList(Parcel in) {
        super(in);
    }
}
