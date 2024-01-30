package com.ua.sdk.gear;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

public class GearAdapter implements JsonDeserializer<Gear>, JsonSerializer<Gear> {
    public Gear deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return (Gear) context.deserialize(json, GearImpl.class);
    }

    public JsonElement serialize(Gear src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src, src.getClass());
    }
}
