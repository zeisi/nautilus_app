package com.ua.sdk.group.user;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

public class GroupUserAdapter implements JsonDeserializer<GroupUser>, JsonSerializer<GroupUser> {
    public GroupUser deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return (GroupUser) context.deserialize(json, GroupUserImpl.class);
    }

    public JsonElement serialize(GroupUser src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src, src.getClass());
    }
}
