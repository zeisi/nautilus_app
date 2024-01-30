package com.ua.sdk.group.purpose;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.ua.sdk.UaException;
import com.ua.sdk.internal.AbstractGsonParser;
import com.ua.sdk.net.json.GsonFactory;
import java.lang.reflect.Type;

public class GroupPurposeJsonParser extends AbstractGsonParser<GroupPurpose> {
    public GroupPurposeJsonParser() {
        super(GsonFactory.newGroupInstance());
    }

    /* access modifiers changed from: protected */
    public GroupPurpose read(Gson gson, JsonReader reader) throws UaException {
        return (GroupPurpose) gson.fromJson(reader, (Type) GroupPurpose.class);
    }
}
