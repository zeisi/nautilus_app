package com.ua.sdk.bodymass;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.internal.AbstractEntityList;

public class BodyMassList extends AbstractEntityList<BodyMass> {
    public static Parcelable.Creator<BodyMassList> CREATOR = new Parcelable.Creator<BodyMassList>() {
        public BodyMassList createFromParcel(Parcel source) {
            return new BodyMassList(source);
        }

        public BodyMassList[] newArray(int size) {
            return new BodyMassList[size];
        }
    };
    private static final String LIST_KEY = "bodymasses";

    public BodyMassList() {
    }

    private BodyMassList(Parcel in) {
        super(in);
    }

    /* access modifiers changed from: protected */
    public String getListKey() {
        return LIST_KEY;
    }
}
