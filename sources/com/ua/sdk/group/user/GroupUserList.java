package com.ua.sdk.group.user;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.internal.AbstractEntityList;

public class GroupUserList extends AbstractEntityList<GroupUser> {
    public static Parcelable.Creator<GroupUserList> CREATOR = new Parcelable.Creator<GroupUserList>() {
        public GroupUserList createFromParcel(Parcel source) {
            return new GroupUserList(source);
        }

        public GroupUserList[] newArray(int size) {
            return new GroupUserList[size];
        }
    };
    private static final String LIST_KEY = "group_users";

    public GroupUserList() {
    }

    private GroupUserList(Parcel in) {
        super(in);
    }

    /* access modifiers changed from: protected */
    public String getListKey() {
        return LIST_KEY;
    }
}
