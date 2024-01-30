package com.ua.sdk.actigraphy;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.internal.AbstractEntityList;

public class ActigraphyList extends AbstractEntityList<Actigraphy> {
    public static Parcelable.Creator<ActigraphyList> CREATOR = new Parcelable.Creator<ActigraphyList>() {
        public ActigraphyList createFromParcel(Parcel source) {
            return new ActigraphyList(source);
        }

        public ActigraphyList[] newArray(int size) {
            return new ActigraphyList[size];
        }
    };
    private static final String LIST_KEY = "actigraphies";

    public ActigraphyList() {
    }

    private ActigraphyList(Parcel in) {
        super(in);
    }

    /* access modifiers changed from: protected */
    public String getListKey() {
        return "actigraphies";
    }
}
