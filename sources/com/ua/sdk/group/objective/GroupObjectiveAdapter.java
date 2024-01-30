package com.ua.sdk.group.objective;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

public class GroupObjectiveAdapter implements JsonDeserializer<GroupObjective>, JsonSerializer<GroupObjective> {
    public GroupObjective deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return (GroupObjective) context.deserialize(json, GroupObjectiveImpl.class);
    }

    public JsonElement serialize(GroupObjective src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src, src.getClass());
    }
}
