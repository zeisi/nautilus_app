package com.ua.sdk.group.user;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.ua.sdk.UaException;
import com.ua.sdk.internal.AbstractGsonParser;
import com.ua.sdk.net.json.GsonFactory;
import java.lang.reflect.Type;

public class GroupUserJsonParser extends AbstractGsonParser<GroupUser> {
    public GroupUserJsonParser() {
        super(GsonFactory.newGroupInstance());
    }

    /* access modifiers changed from: protected */
    public GroupUser read(Gson gson, JsonReader reader) throws UaException {
        return (GroupUser) gson.fromJson(reader, (Type) GroupUser.class);
    }
}
