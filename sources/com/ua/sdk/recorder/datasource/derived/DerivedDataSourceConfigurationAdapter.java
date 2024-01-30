package com.ua.sdk.recorder.datasource.derived;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.ua.sdk.recorder.DerivedDataSourceConfiguration;
import java.lang.reflect.Type;

public class DerivedDataSourceConfigurationAdapter implements JsonSerializer<DerivedDataSourceConfiguration>, JsonDeserializer<DerivedDataSourceConfiguration> {
    public DerivedDataSourceConfiguration deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return (DerivedDataSourceConfiguration) context.deserialize(json, DerivedDataSourceConfigurationImpl.class);
    }

    public JsonElement serialize(DerivedDataSourceConfiguration src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src, src.getClass());
    }
}
