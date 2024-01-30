package com.ua.sdk.group.user;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.EntityListRef;
import com.ua.sdk.internal.BaseReferenceBuilder;

public class GroupUserListRef implements EntityListRef<GroupUser> {
    public static Parcelable.Creator<GroupUserListRef> CREATOR = new Parcelable.Creator<GroupUserListRef>() {
        public GroupUserListRef createFromParcel(Parcel source) {
            return new GroupUserListRef(source);
        }

        public GroupUserListRef[] newArray(int size) {
            return new GroupUserListRef[size];
        }
    };
    private final String href;

    public enum Type {
        GROUP,
        USER
    }

    private GroupUserListRef(Parcel in) {
        this.href = in.readString();
    }

    private GroupUserListRef(Builder init) {
        this.href = init.getHref();
    }

    public String getId() {
        return null;
    }

    public String getHref() {
        return this.href;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.href);
    }

    public static class Builder extends BaseReferenceBuilder {
        private static final String GROUP_ID_KEY = "group_id";
        private static final String USER_ID_KEY = "user_id";
        private String id;
        private Type type;

        public Builder() {
            super("/v7.0/group_user/");
        }

        public Builder setType(Type type2) {
            this.type = type2;
            return this;
        }

        public Builder setId(String id2) {
            this.id = id2;
            return this;
        }

        public GroupUserListRef build() {
            if (this.type == null) {
                throw new IllegalArgumentException("Builder type must be initialized!");
            } else if (this.id == null) {
                throw new IllegalArgumentException("ID must be initialized!");
            } else {
                if (this.type == Type.GROUP) {
                    setParam(GROUP_ID_KEY, this.id);
                } else {
                    setParam("user_id", this.id);
                }
                return new GroupUserListRef(this);
            }
        }
    }
}
