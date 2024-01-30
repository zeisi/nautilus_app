package com.ua.sdk.group.purpose;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.ua.sdk.UaException;
import com.ua.sdk.internal.AbstractGsonParser;
import com.ua.sdk.net.json.GsonFactory;

public class GroupPurposeListJsonParser extends AbstractGsonParser<GroupPurposeList> {
    public GroupPurposeListJsonParser() {
        super(GsonFactory.newGroupInstance());
    }

    /* access modifiers changed from: protected */
    public GroupPurposeList read(Gson gson, JsonReader reader) throws UaException {
        return (GroupPurposeList) gson.fromJson(reader, new TypeToken<GroupPurposeList>() {
        }.getType());
    }
}
