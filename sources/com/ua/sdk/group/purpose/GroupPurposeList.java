package com.ua.sdk.group.purpose;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.internal.AbstractEntityList;

public class GroupPurposeList extends AbstractEntityList<GroupPurpose> {
    public static Parcelable.Creator<GroupPurposeList> CREATOR = new Parcelable.Creator<GroupPurposeList>() {
        public GroupPurposeList createFromParcel(Parcel source) {
            return new GroupPurposeList(source);
        }

        public GroupPurposeList[] newArray(int size) {
            return new GroupPurposeList[size];
        }
    };
    private static final String LIST_KEY = "group_purposes";

    public GroupPurposeList() {
    }

    private GroupPurposeList(Parcel in) {
        super(in);
    }

    /* access modifiers changed from: protected */
    public String getListKey() {
        return LIST_KEY;
    }
}
