package com.ua.sdk.group.invite;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.ua.sdk.UaException;
import com.ua.sdk.internal.AbstractGsonParser;
import com.ua.sdk.net.json.GsonFactory;

public class GroupInviteListJsonParser extends AbstractGsonParser<GroupInviteList> {
    public GroupInviteListJsonParser() {
        super(GsonFactory.newGroupInstance());
    }

    /* access modifiers changed from: protected */
    public GroupInviteList read(Gson gson, JsonReader reader) throws UaException {
        return (GroupInviteList) gson.fromJson(reader, new TypeToken<GroupInviteList>() {
        }.getType());
    }
}
