package com.ua.sdk.route.bookmark;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.internal.AbstractEntityList;
import com.ua.sdk.route.RouteBookmark;

public class RouteBookmarkList extends AbstractEntityList<RouteBookmark> {
    public static Parcelable.Creator<RouteBookmarkList> CREATOR = new Parcelable.Creator<RouteBookmarkList>() {
        public RouteBookmarkList createFromParcel(Parcel source) {
            return new RouteBookmarkList(source);
        }

        public RouteBookmarkList[] newArray(int size) {
            return new RouteBookmarkList[size];
        }
    };
    private static final String LIST_KEY = "route_bookmarks";

    /* access modifiers changed from: protected */
    public String getListKey() {
        return LIST_KEY;
    }

    public RouteBookmarkList() {
    }

    private RouteBookmarkList(Parcel in) {
        super(in);
    }
}
