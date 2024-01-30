package com.ua.sdk.activitystory;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

public class ActivityStoryGroupLeaderboardAdapter implements JsonDeserializer<ActivityStoryGroupLeaderboard>, JsonSerializer<ActivityStoryGroupLeaderboard> {
    public ActivityStoryGroupLeaderboard deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return (ActivityStoryGroupLeaderboard) context.deserialize(json, ActivityStoryGroupLeaderboardImpl.class);
    }

    public JsonElement serialize(ActivityStoryGroupLeaderboard src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src, src.getClass());
    }
}
