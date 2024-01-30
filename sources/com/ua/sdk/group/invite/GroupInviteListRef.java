package com.ua.sdk.group.invite;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.EntityListRef;
import com.ua.sdk.internal.BaseReferenceBuilder;

public class GroupInviteListRef implements EntityListRef<GroupInvite> {
    public static Parcelable.Creator<GroupInviteListRef> CREATOR = new Parcelable.Creator<GroupInviteListRef>() {
        public GroupInviteListRef createFromParcel(Parcel source) {
            return new GroupInviteListRef(source);
        }

        public GroupInviteListRef[] newArray(int size) {
            return new GroupInviteListRef[size];
        }
    };
    private final String href;

    public enum Type {
        GROUP,
        USER
    }

    private GroupInviteListRef(Parcel in) {
        this.href = in.readString();
    }

    private GroupInviteListRef(Builder init) {
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
            super("/v7.0/group_invite/");
        }

        public Builder setType(Type type2) {
            this.type = type2;
            return this;
        }

        public Builder setId(String id2) {
            this.id = id2;
            return this;
        }

        public GroupInviteListRef build() {
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
                return new GroupInviteListRef(this);
            }
        }
    }
}
