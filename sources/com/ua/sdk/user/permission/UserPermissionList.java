package com.ua.sdk.user.permission;

import com.ua.sdk.internal.AbstractEntityList;

public class UserPermissionList extends AbstractEntityList<UserPermission> {
    private static final String LIST_KEY = "user_permissions";

    /* access modifiers changed from: protected */
    public String getListKey() {
        return LIST_KEY;
    }
}
