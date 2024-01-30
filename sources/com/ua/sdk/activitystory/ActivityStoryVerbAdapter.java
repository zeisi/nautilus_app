package com.ua.sdk.activitystory;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

public class ActivityStoryVerbAdapter implements JsonSerializer<ActivityStoryVerb>, JsonDeserializer<ActivityStoryVerb> {
    public ActivityStoryVerb deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String val = json.getAsString();
        if (val == null) {
            return null;
        }
        try {
            return ActivityStoryVerb.valueOf(val.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public JsonElement serialize(ActivityStoryVerb src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src.toString().toLowerCase(), String.class);
    }
}
