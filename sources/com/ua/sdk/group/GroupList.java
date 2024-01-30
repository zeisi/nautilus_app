package com.ua.sdk.group;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.internal.AbstractEntityList;

public class GroupList extends AbstractEntityList<Group> implements Parcelable {
    public static final Parcelable.Creator<GroupList> CREATOR = new Parcelable.Creator<GroupList>() {
        public GroupList createFromParcel(Parcel source) {
            return new GroupList(source);
        }

        public GroupList[] newArray(int size) {
            return new GroupList[size];
        }
    };

    public GroupList() {
    }

    /* access modifiers changed from: protected */
    public String getListKey() {
        return "groups";
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    private GroupList(Parcel in) {
        super(in);
    }
}
