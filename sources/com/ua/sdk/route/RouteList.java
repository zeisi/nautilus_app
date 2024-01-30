package com.ua.sdk.route;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.internal.AbstractEntityList;

public class RouteList extends AbstractEntityList<Route> {
    public static final Parcelable.Creator<RouteList> CREATOR = new Parcelable.Creator<RouteList>() {
        public RouteList createFromParcel(Parcel source) {
            return new RouteList(source);
        }

        public RouteList[] newArray(int size) {
            return new RouteList[size];
        }
    };
    public static final String LIST_KEY = "routes";

    /* access modifiers changed from: protected */
    public String getListKey() {
        return "routes";
    }

    public RouteList() {
    }

    private RouteList(Parcel in) {
    }
}
