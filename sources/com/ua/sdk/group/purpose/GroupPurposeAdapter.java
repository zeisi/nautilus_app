package com.ua.sdk.group.purpose;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

public class GroupPurposeAdapter implements JsonDeserializer<GroupPurpose>, JsonSerializer<GroupPurpose> {
    public GroupPurpose deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return (GroupPurpose) context.deserialize(json, GroupPurposeImpl.class);
    }

    public JsonElement serialize(GroupPurpose src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src, src.getClass());
    }
}
