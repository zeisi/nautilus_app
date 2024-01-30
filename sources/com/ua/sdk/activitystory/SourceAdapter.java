package com.ua.sdk.activitystory;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.ua.sdk.Source;
import java.lang.reflect.Type;

public class SourceAdapter implements JsonSerializer<Source>, JsonDeserializer<Source> {
    public Source deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return (Source) context.deserialize(json, SourceImpl.class);
    }

    public JsonElement serialize(Source src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src, src.getClass());
    }
}
