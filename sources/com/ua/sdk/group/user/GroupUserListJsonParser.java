package com.ua.sdk.group.user;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.ua.sdk.UaException;
import com.ua.sdk.internal.AbstractGsonParser;
import com.ua.sdk.net.json.GsonFactory;

public class GroupUserListJsonParser extends AbstractGsonParser<GroupUserList> {
    public GroupUserListJsonParser() {
        super(GsonFactory.newGroupInstance());
    }

    /* access modifiers changed from: protected */
    public GroupUserList read(Gson gson, JsonReader reader) throws UaException {
        return (GroupUserList) gson.fromJson(reader, new TypeToken<GroupUserList>() {
        }.getType());
    }
}
