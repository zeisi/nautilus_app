package com.ua.sdk.user.role;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.ua.sdk.UaException;
import com.ua.sdk.internal.AbstractGsonParser;
import java.lang.reflect.Type;

public class UserRoleJsonParser extends AbstractGsonParser<UserRole> {
    public UserRoleJsonParser(Gson gson) {
        super(gson);
    }

    /* access modifiers changed from: protected */
    public UserRole read(Gson gson, JsonReader reader) throws UaException {
        return UserRoleTO.fromTransferObject((UserRoleTO) gson.fromJson(reader, (Type) UserRoleTO.class));
    }
}
