package com.ua.sdk.user;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

public enum Gender {
    FEMALE,
    MALE;

    public static class GenderAdapter implements JsonSerializer<Gender>, JsonDeserializer<Gender> {
        public Gender deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
            String value = json.getAsString();
            if (value.equals("M") || value.equals("m")) {
                return Gender.MALE;
            }
            if (value.equals("F") || value.equals("f")) {
                return Gender.FEMALE;
            }
            return null;
        }

        public JsonElement serialize(Gender obj, Type type, JsonSerializationContext context) {
            switch (obj) {
                case FEMALE:
                    return new JsonPrimitive("F");
                case MALE:
                    return new JsonPrimitive("M");
                default:
                    return null;
            }
        }
    }
}
