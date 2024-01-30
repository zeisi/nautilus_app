package com.ua.sdk.group.objective;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.ua.sdk.UaException;
import com.ua.sdk.internal.AbstractGsonParser;
import com.ua.sdk.net.json.GsonFactory;
import java.lang.reflect.Type;

public class GroupObjectiveJsonParser extends AbstractGsonParser<GroupObjective> {
    public GroupObjectiveJsonParser() {
        super(GsonFactory.newGroupInstance());
    }

    /* access modifiers changed from: protected */
    public GroupObjective read(Gson gson, JsonReader reader) throws UaException {
        return (GroupObjective) gson.fromJson(reader, (Type) GroupObjective.class);
    }
}
