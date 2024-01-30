package com.ua.sdk.aggregate;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.ua.sdk.UaException;
import com.ua.sdk.internal.AbstractGsonParser;
import com.ua.sdk.net.json.GsonFactory;
import java.lang.reflect.Type;

public class AggregateJsonParser extends AbstractGsonParser<Aggregate> {
    public AggregateJsonParser() {
        super(GsonFactory.newAggregateInstance());
    }

    /* access modifiers changed from: protected */
    public Aggregate read(Gson gson, JsonReader reader) throws UaException {
        return (Aggregate) gson.fromJson(reader, (Type) Aggregate.class);
    }
}
