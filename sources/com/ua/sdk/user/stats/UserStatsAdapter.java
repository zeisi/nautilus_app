package com.ua.sdk.user.stats;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

public class UserStatsAdapter implements JsonDeserializer<UserStats>, JsonSerializer<UserStats> {
    public UserStats deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return (UserStats) context.deserialize(json, UserStatsImpl.class);
    }

    public JsonElement serialize(UserStats src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src, src.getClass());
    }
}
