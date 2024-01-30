package com.ua.sdk.group;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.ua.sdk.UaException;
import com.ua.sdk.internal.AbstractGsonParser;
import com.ua.sdk.net.json.GsonFactory;

public class GroupJsonParser extends AbstractGsonParser<Group> {
    public GroupJsonParser() {
        super(GsonFactory.newGroupInstance());
    }

    /* access modifiers changed from: protected */
    public Group read(Gson gson, JsonReader reader) throws UaException {
        return (Group) gson.fromJson(reader, new TypeToken<Group>() {
        }.getType());
    }
}
