package com.ua.sdk.suggestedfriends;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

public class SuggestedFriendsAdapter implements JsonSerializer<SuggestedFriends>, JsonDeserializer<SuggestedFriends> {
    public SuggestedFriends deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return (SuggestedFriends) context.deserialize(json, SuggestedFriendsImpl.class);
    }

    public JsonElement serialize(SuggestedFriends src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src, src.getClass());
    }
}
