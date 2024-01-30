package com.ua.sdk.datasourceidentifier;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

public class DataSourceIdentifierAdapter implements JsonSerializer<DataSourceIdentifier>, JsonDeserializer<DataSourceIdentifier> {
    public DataSourceIdentifier deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return (DataSourceIdentifier) context.deserialize(json, DataSourceIdentifierImpl.class);
    }

    public JsonElement serialize(DataSourceIdentifier src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src, src.getClass());
    }
}
