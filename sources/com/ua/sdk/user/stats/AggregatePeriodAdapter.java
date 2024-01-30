package com.ua.sdk.user.stats;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

public class AggregatePeriodAdapter implements JsonDeserializer<AggregatePeriod>, JsonSerializer<AggregatePeriod> {
    public AggregatePeriod deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return (AggregatePeriod) context.deserialize(json, AggregatePeriodImpl.class);
    }

    public JsonElement serialize(AggregatePeriod src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src, src.getClass());
    }
}
