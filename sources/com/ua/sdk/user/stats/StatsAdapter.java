package com.ua.sdk.user.stats;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

public class StatsAdapter implements JsonDeserializer<Stats>, JsonSerializer<Stats> {
    public Stats deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return (Stats) context.deserialize(json, StatsImpl.class);
    }

    public JsonElement serialize(Stats src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src, src.getClass());
    }
}
