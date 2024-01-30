package com.ua.sdk.group.leaderboard;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

public class GroupLeaderboardAdapter implements JsonDeserializer<GroupLeaderboard>, JsonSerializer<GroupLeaderboard> {
    public GroupLeaderboard deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return (GroupLeaderboard) context.deserialize(json, GroupLeaderboardImpl.class);
    }

    public JsonElement serialize(GroupLeaderboard src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src, src.getClass());
    }
}
