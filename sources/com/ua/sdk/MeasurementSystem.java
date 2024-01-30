package com.ua.sdk;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

public enum MeasurementSystem {
    METRIC,
    IMPERIAL,
    HYBRID;

    /* access modifiers changed from: private */
    public static String toJson(MeasurementSystem measurementSystem) {
        switch (measurementSystem) {
            case METRIC:
                return "metric";
            case IMPERIAL:
                return "imperial";
            case HYBRID:
                return "hybrid";
            default:
                return null;
        }
    }

    /* access modifiers changed from: private */
    public static MeasurementSystem fromJson(String string) {
        if ("metric".equals(string)) {
            return METRIC;
        }
        if ("imperial".equals(string)) {
            return IMPERIAL;
        }
        if ("hybrid".equals(string)) {
            return HYBRID;
        }
        return null;
    }

    public static class MeasurementSystemAdapter implements JsonSerializer<MeasurementSystem>, JsonDeserializer<MeasurementSystem> {
        public MeasurementSystem deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return MeasurementSystem.fromJson(json.getAsString());
        }

        public JsonElement serialize(MeasurementSystem src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(MeasurementSystem.toJson(src));
        }
    }
}
