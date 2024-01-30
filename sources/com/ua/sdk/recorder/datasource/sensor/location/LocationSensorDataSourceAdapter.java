package com.ua.sdk.recorder.datasource.sensor.location;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.ua.sdk.recorder.LocationSensorDataSourceConfiguration;
import java.lang.reflect.Type;

public class LocationSensorDataSourceAdapter implements JsonSerializer<LocationSensorDataSourceConfiguration>, JsonDeserializer<LocationSensorDataSourceConfiguration> {
    public LocationSensorDataSourceConfiguration deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return (LocationSensorDataSourceConfiguration) context.deserialize(json, LocationSensorSensorDataSourceConfigurationImpl.class);
    }

    public JsonElement serialize(LocationSensorDataSourceConfiguration src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src, src.getClass());
    }
}
