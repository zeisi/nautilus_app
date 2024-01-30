package com.ua.sdk.role;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.ua.sdk.UaException;
import com.ua.sdk.internal.AbstractGsonParser;
import java.lang.reflect.Type;

public class RoleListJsonParser extends AbstractGsonParser<RoleList> {
    public RoleListJsonParser(Gson gson) {
        super(gson);
    }

    /* access modifiers changed from: protected */
    public RoleList read(Gson gson, JsonReader reader) throws UaException {
        return RolePagedTO.toPage((RolePagedTO) gson.fromJson(reader, (Type) RolePagedTO.class));
    }
}
