package com.ua.sdk.user.permission;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.ua.sdk.UaException;
import com.ua.sdk.internal.AbstractGsonParser;
import java.lang.reflect.Type;

public class UserPermissionJsonParser extends AbstractGsonParser<UserPermission> {
    public UserPermissionJsonParser(Gson gson) {
        super(gson);
    }

    /* access modifiers changed from: protected */
    public UserPermission read(Gson gson, JsonReader reader) throws UaException {
        return UserPermissionTO.fromTransferObject((UserPermissionTO) gson.fromJson(reader, (Type) UserPermissionTO.class));
    }
}
