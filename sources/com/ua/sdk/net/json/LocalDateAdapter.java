package com.ua.sdk.net.json;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.ua.sdk.LocalDate;
import java.lang.reflect.Type;

public class LocalDateAdapter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {
    public LocalDate deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        return LocalDate.fromString(json.getAsString());
    }

    public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.toString());
    }
}
