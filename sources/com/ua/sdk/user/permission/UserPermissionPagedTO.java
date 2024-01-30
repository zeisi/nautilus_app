package com.ua.sdk.user.permission;

import com.google.gson.annotations.SerializedName;
import com.ua.sdk.internal.ApiTransferObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class UserPermissionPagedTO extends ApiTransferObject {
    private static final String KEY_PERMISSIONS = "user_permissions";
    @SerializedName("total_count")
    public Integer totalUserCount;
    @SerializedName("_embedded")
    public Map<String, ArrayList<UserPermissionTO>> userPermissions;

    private ArrayList<UserPermissionTO> getUserPermissionList() {
        if (this.userPermissions == null) {
            return null;
        }
        return this.userPermissions.get(KEY_PERMISSIONS);
    }

    public static UserPermissionList toPage(UserPermissionPagedTO to) {
        UserPermissionList list = new UserPermissionList();
        Iterator i$ = to.getUserPermissionList().iterator();
        while (i$.hasNext()) {
            list.add(UserPermissionTO.fromTransferObject(i$.next()));
        }
        list.setLinkMap(to.getLinkMap());
        list.setTotalCount(to.totalUserCount.intValue());
        return list;
    }
}
