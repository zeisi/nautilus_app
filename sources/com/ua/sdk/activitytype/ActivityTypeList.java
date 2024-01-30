package com.ua.sdk.activitytype;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.internal.AbstractEntityList;

public class ActivityTypeList extends AbstractEntityList<ActivityType> {
    public static Parcelable.Creator<ActivityTypeList> CREATOR = new Parcelable.Creator<ActivityTypeList>() {
        public ActivityTypeList createFromParcel(Parcel source) {
            return new ActivityTypeList(source);
        }

        public ActivityTypeList[] newArray(int size) {
            return new ActivityTypeList[size];
        }
    };
    private static final String LIST_KEY = "activity_types";

    /* access modifiers changed from: protected */
    public String getListKey() {
        return LIST_KEY;
    }

    public ActivityTypeList() {
    }

    private ActivityTypeList(Parcel in) {
        super(in);
    }
}
