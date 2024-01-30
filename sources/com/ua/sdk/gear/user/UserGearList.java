package com.ua.sdk.gear.user;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.internal.AbstractEntityList;

public class UserGearList extends AbstractEntityList<UserGear> implements Parcelable {
    public static final Parcelable.Creator<UserGearList> CREATOR = new Parcelable.Creator<UserGearList>() {
        public UserGearList createFromParcel(Parcel source) {
            return new UserGearList(source);
        }

        public UserGearList[] newArray(int size) {
            return new UserGearList[size];
        }
    };

    public UserGearList() {
    }

    /* access modifiers changed from: protected */
    public String getListKey() {
        return "usergear";
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    private UserGearList(Parcel in) {
        super(in);
    }
}
