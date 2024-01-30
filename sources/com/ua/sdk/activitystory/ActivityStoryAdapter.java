package com.ua.sdk.activitystory;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.ua.sdk.UaLog;
import java.lang.reflect.Type;

public class ActivityStoryAdapter implements JsonSerializer<ActivityStory>, JsonDeserializer<ActivityStory> {
    public ActivityStory deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            ActivityStory story = (ActivityStory) context.deserialize(json, ActivityStoryImpl.class);
            ActivityStoryTemplateImpl template = (ActivityStoryTemplateImpl) story.getTemplate();
            if (template == null) {
                return story;
            }
            template.fillTemplateArgs(json.getAsJsonObject());
            return story;
        } catch (JsonParseException e) {
            UaLog.error("Unable to parse ActivityStory=" + json, (Throwable) e);
            return new ActivityStoryImpl();
        }
    }

    public JsonElement serialize(ActivityStory src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src, src.getClass());
    }
}
