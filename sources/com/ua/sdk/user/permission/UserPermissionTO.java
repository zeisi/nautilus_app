package com.ua.sdk.user.permission;

import android.os.Parcel;
import com.google.gson.annotations.SerializedName;
import com.ua.sdk.Reference;
import com.ua.sdk.Resource;

public class UserPermissionTO implements Resource {
    @SerializedName("permission")
    String permission;
    @SerializedName("resource")
    String resource;

    public static UserPermissionTO toTransferObject(UserPermissionImpl userPermission) {
        UserPermissionTO to = new UserPermissionTO();
        to.resource = userPermission.getResource();
        to.permission = userPermission.getPermission();
        return to;
    }

    public static UserPermission fromTransferObject(UserPermissionTO to) {
        UserPermissionImpl answer = new UserPermissionImpl();
        answer.setResource(to.resource);
        answer.setPermission(to.permission);
        return answer;
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
