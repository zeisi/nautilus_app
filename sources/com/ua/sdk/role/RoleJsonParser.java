package com.ua.sdk.role;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.ua.sdk.UaException;
import com.ua.sdk.internal.AbstractGsonParser;
import java.lang.reflect.Type;

public class RoleJsonParser extends AbstractGsonParser<Role> {
    public RoleJsonParser(Gson gson) {
        super(gson);
    }

    /* access modifiers changed from: protected */
    public Role read(Gson gson, JsonReader reader) throws UaException {
        return RoleTO.fromTransferObject((RoleTO) gson.fromJson(reader, (Type) RoleTO.class));
    }
}
