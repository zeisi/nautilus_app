package com.ua.sdk.group.objective;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.internal.AbstractEntityList;

public class GroupObjectiveList extends AbstractEntityList<GroupObjective> {
    public static Parcelable.Creator<GroupObjectiveList> CREATOR = new Parcelable.Creator<GroupObjectiveList>() {
        public GroupObjectiveList createFromParcel(Parcel source) {
            return new GroupObjectiveList(source);
        }

        public GroupObjectiveList[] newArray(int size) {
            return new GroupObjectiveList[size];
        }
    };
    private static final String LIST_KEY = "group_objectives";

    public GroupObjectiveList() {
    }

    private GroupObjectiveList(Parcel in) {
        super(in);
    }

    /* access modifiers changed from: protected */
    public String getListKey() {
        return LIST_KEY;
    }
}
