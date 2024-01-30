package com.ua.sdk.gear.brand;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.internal.AbstractEntityList;

public class GearBrandList extends AbstractEntityList<GearBrand> {
    public static final Parcelable.Creator<GearBrandList> CREATOR = new Parcelable.Creator<GearBrandList>() {
        public GearBrandList createFromParcel(Parcel source) {
            return new GearBrandList(source);
        }

        public GearBrandList[] newArray(int size) {
            return new GearBrandList[size];
        }
    };

    public GearBrandList() {
    }

    /* access modifiers changed from: protected */
    public String getListKey() {
        return "gearbrand";
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    private GearBrandList(Parcel in) {
        super(in);
    }
}
