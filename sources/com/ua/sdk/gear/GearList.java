package com.ua.sdk.gear;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.internal.AbstractEntityList;

public class GearList extends AbstractEntityList<Gear> {
    public static final Parcelable.Creator<GearList> CREATOR = new Parcelable.Creator<GearList>() {
        public GearList createFromParcel(Parcel source) {
            return new GearList(source);
        }

        public GearList[] newArray(int size) {
            return new GearList[size];
        }
    };
    private static final String LIST_KEY = "gear";

    public GearList() {
    }

    /* access modifiers changed from: protected */
    public String getListKey() {
        return LIST_KEY;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    private GearList(Parcel in) {
        super(in);
    }
}
