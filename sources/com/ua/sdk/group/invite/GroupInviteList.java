package com.ua.sdk.group.invite;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.internal.AbstractEntityList;

public class GroupInviteList extends AbstractEntityList<GroupInvite> {
    public static Parcelable.Creator<GroupInviteList> CREATOR = new Parcelable.Creator<GroupInviteList>() {
        public GroupInviteList createFromParcel(Parcel source) {
            return new GroupInviteList(source);
        }

        public GroupInviteList[] newArray(int size) {
            return new GroupInviteList[size];
        }
    };
    private static final String LIST_KEY = "group_invites";

    public GroupInviteList() {
    }

    private GroupInviteList(Parcel in) {
        super(in);
    }

    /* access modifiers changed from: protected */
    public String getListKey() {
        return LIST_KEY;
    }
}
