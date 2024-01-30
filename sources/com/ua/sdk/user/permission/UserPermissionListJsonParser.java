package com.ua.sdk.user.permission;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.ua.sdk.UaException;
import com.ua.sdk.internal.AbstractGsonParser;
import java.lang.reflect.Type;

public class UserPermissionListJsonParser extends AbstractGsonParser<UserPermissionList> {
    public UserPermissionListJsonParser(Gson gson) {
        super(gson);
    }

    /* access modifiers changed from: protected */
    public UserPermissionList read(Gson gson, JsonReader reader) throws UaException {
        return UserPermissionPagedTO.toPage((UserPermissionPagedTO) gson.fromJson(reader, (Type) UserPermissionPagedTO.class));
    }
}
