package com.ua.sdk.user.permission;

import android.os.Parcel;
import com.google.gson.annotations.SerializedName;
import com.ua.sdk.Reference;

public class UserPermissionImpl implements UserPermission {
    @SerializedName("permission")
    private String permission;
    @SerializedName("resource")
    private String resource;

    public String getResource() {
        return this.resource;
    }

    public String getPermission() {
        return this.permission;
    }

    public void setResource(String resource2) {
        this.resource = resource2;
    }

    public void setPermission(String permission2) {
        this.permission = permission2;
    }

    public Reference getRef() {
        return null;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
    }
}
