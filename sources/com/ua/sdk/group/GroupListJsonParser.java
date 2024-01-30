package com.ua.sdk.group;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.ua.sdk.EntityList;
import com.ua.sdk.UaException;
import com.ua.sdk.internal.AbstractGsonParser;
import com.ua.sdk.net.json.GsonFactory;

public class GroupListJsonParser extends AbstractGsonParser<EntityList<Group>> {
    public GroupListJsonParser() {
        super(GsonFactory.newGroupInstance());
    }

    /* access modifiers changed from: protected */
    public EntityList<Group> read(Gson gson, JsonReader reader) throws UaException {
        return (EntityList) gson.fromJson(reader, new TypeToken<GroupList>() {
        }.getType());
    }
}
