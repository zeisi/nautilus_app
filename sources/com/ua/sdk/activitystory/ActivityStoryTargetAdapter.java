package com.ua.sdk.activitystory;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.ua.sdk.activitystory.target.ActivityStoryGroupTarget;
import com.ua.sdk.activitystory.target.ActivityStoryUnknownTarget;
import java.lang.reflect.Type;

public class ActivityStoryTargetAdapter implements JsonSerializer<ActivityStoryTarget>, JsonDeserializer<ActivityStoryTarget> {
    public ActivityStoryTarget deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonElement typeElement = json.getAsJsonObject().get("type");
        if (typeElement == null || !"group".equals(typeElement.getAsString())) {
            return (ActivityStoryTarget) context.deserialize(json, ActivityStoryUnknownTarget.class);
        }
        return (ActivityStoryTarget) context.deserialize(json, ActivityStoryGroupTarget.class);
    }

    public JsonElement serialize(ActivityStoryTarget src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src, src.getClass());
    }
}
