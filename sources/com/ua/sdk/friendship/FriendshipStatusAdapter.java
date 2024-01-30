package com.ua.sdk.friendship;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

public class FriendshipStatusAdapter implements JsonSerializer<FriendshipStatus>, JsonDeserializer<FriendshipStatus> {
    public FriendshipStatus deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return FriendshipStatus.getStatusFromString(json.getAsString());
    }

    public JsonElement serialize(FriendshipStatus src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.getValue());
    }
}
