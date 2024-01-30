package com.ua.sdk.user.permission;

import android.os.Parcel;
import android.os.Parcelable;
import com.ua.sdk.EntityListRef;
import com.ua.sdk.Resource;

public class UserPermissionListRef implements EntityListRef<UserPermission> {
    public static Parcelable.Creator<UserPermissionListRef> CREATOR = new Parcelable.Creator<UserPermissionListRef>() {
        public UserPermissionListRef createFromParcel(Parcel source) {
            return new UserPermissionListRef(source);
        }

        public UserPermissionListRef[] newArray(int size) {
            return new UserPermissionListRef[size];
        }
    };
    private final String href;
    private final String id;

    private UserPermissionListRef(Builder init) {
        this.id = init.id;
        this.href = init.href;
    }

    private UserPermissionListRef(Parcel in) {
        this.id = in.readString();
        this.href = in.readString();
    }

    public String getId() {
        return this.id;
    }

    public String getHref() {
        return this.href;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.href);
    }

    public static class Builder {
        /* access modifiers changed from: private */
        public String href;
        /* access modifiers changed from: private */
        public final String id;

        public Builder(Resource resource) {
            this.id = resource.getRef().getId();
            this.href = resource.getRef().getHref();
        }

        public Builder(String id2) {
            this.id = id2;
        }

        public Builder href(String href2) {
            this.href = href2;
            return this;
        }

        public UserPermissionListRef build() {
            return new UserPermissionListRef(this);
        }
    }
}
