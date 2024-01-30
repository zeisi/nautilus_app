package com.ua.sdk.group.objective;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.ua.sdk.UaException;
import com.ua.sdk.internal.AbstractGsonParser;
import com.ua.sdk.net.json.GsonFactory;

public class GroupObjectiveListJsonParser extends AbstractGsonParser<GroupObjectiveList> {
    public GroupObjectiveListJsonParser() {
        super(GsonFactory.newGroupInstance());
    }

    /* access modifiers changed from: protected */
    public GroupObjectiveList read(Gson gson, JsonReader reader) throws UaException {
        return (GroupObjectiveList) gson.fromJson(reader, new TypeToken<GroupObjectiveList>() {
        }.getType());
    }
}
