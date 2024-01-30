package com.ua.sdk.aggregate;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.internal.AbstractEntityList;

public class AggregateList extends AbstractEntityList<Aggregate> {
    public static Parcelable.Creator<AggregateList> CREATOR = new Parcelable.Creator<AggregateList>() {
        public AggregateList createFromParcel(Parcel source) {
            return new AggregateList(source);
        }

        public AggregateList[] newArray(int size) {
            return new AggregateList[size];
        }
    };
    private static final String LIST_KEY = "aggregates";

    public AggregateList() {
    }

    protected AggregateList(Parcel in) {
        super(in);
    }

    /* access modifiers changed from: protected */
    public String getListKey() {
        return LIST_KEY;
    }
}
