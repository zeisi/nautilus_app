package com.ua.sdk.group;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

public class GroupAdapter implements JsonDeserializer<Group>, JsonSerializer<Group> {
    public Group deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return (Group) context.deserialize(json, GroupImpl.class);
    }

    public JsonElement serialize(Group src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src, src.getClass());
    }
}
