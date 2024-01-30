package com.ua.sdk.aggregate;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.ua.sdk.UaException;
import com.ua.sdk.internal.AbstractGsonParser;
import com.ua.sdk.net.json.GsonFactory;

public class AggregateListJsonParser extends AbstractGsonParser<AggregateList> {
    public AggregateListJsonParser() {
        super(GsonFactory.newAggregateInstance());
    }

    /* access modifiers changed from: protected */
    public AggregateList read(Gson gson, JsonReader reader) throws UaException {
        return (AggregateList) gson.fromJson(reader, new TypeToken<AggregateList>() {
        }.getType());
    }
}
