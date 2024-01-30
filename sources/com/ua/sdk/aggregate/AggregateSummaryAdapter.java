package com.ua.sdk.aggregate;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

public class AggregateSummaryAdapter implements JsonDeserializer<AggregateSummary>, JsonSerializer<AggregateSummary> {
    public AggregateSummary deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return (AggregateSummary) context.deserialize(json, AggregateSummaryImpl.class);
    }

    public JsonElement serialize(AggregateSummary src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src, src.getClass());
    }
}
