package com.ua.sdk.privacy;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

public class PrivacyAdapter implements JsonSerializer<Privacy>, JsonDeserializer<Privacy> {
    public Privacy deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json.isJsonPrimitive()) {
            return PrivacyHelper.getPrivacyFromId(json.getAsJsonPrimitive().getAsNumber().intValue());
        }
        return null;
    }

    public JsonElement serialize(Privacy src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive((Number) Integer.valueOf(src.getLevel().id));
    }
}
