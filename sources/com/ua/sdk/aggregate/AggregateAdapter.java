package com.ua.sdk.aggregate;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

public class AggregateAdapter implements JsonDeserializer<Aggregate>, JsonSerializer<Aggregate> {
    public Aggregate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return (Aggregate) context.deserialize(json, AggregateImpl.class);
    }

    public JsonElement serialize(Aggregate src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src, src.getClass());
    }
}
