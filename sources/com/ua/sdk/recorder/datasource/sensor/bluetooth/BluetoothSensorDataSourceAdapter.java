package com.ua.sdk.recorder.datasource.sensor.bluetooth;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.ua.sdk.recorder.BluetoothSensorDataSourceConfiguration;
import java.lang.reflect.Type;

public class BluetoothSensorDataSourceAdapter implements JsonSerializer<BluetoothSensorDataSourceConfiguration>, JsonDeserializer<BluetoothSensorDataSourceConfiguration> {
    public BluetoothSensorDataSourceConfiguration deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return (BluetoothSensorDataSourceConfiguration) context.deserialize(json, BluetoothSensorDataSourceConfigurationImpl.class);
    }

    public JsonElement serialize(BluetoothSensorDataSourceConfiguration src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src, src.getClass());
    }
}
