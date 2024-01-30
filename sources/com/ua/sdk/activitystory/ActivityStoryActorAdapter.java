package com.ua.sdk.activitystory;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.ua.sdk.activitystory.actor.ActivityStoryBrandActorImpl;
import com.ua.sdk.activitystory.actor.ActivityStoryGroupActorImpl;
import com.ua.sdk.activitystory.actor.ActivityStoryPageActorImpl;
import com.ua.sdk.activitystory.actor.ActivityStorySiteActorImpl;
import com.ua.sdk.activitystory.actor.ActivityStoryUnknownActorImpl;
import com.ua.sdk.activitystory.actor.ActivityStoryUserActorImpl;
import java.lang.reflect.Type;

public class ActivityStoryActorAdapter implements JsonSerializer<ActivityStoryActor>, JsonDeserializer<ActivityStoryActor> {
    public ActivityStoryActor deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonElement typeElement = json.getAsJsonObject().get("type");
        if (typeElement != null) {
            String type = typeElement.getAsString();
            if ("user".equals(type)) {
                return (ActivityStoryActor) context.deserialize(json, ActivityStoryUserActorImpl.class);
            }
            if ("brand".equals(type)) {
                return (ActivityStoryActor) context.deserialize(json, ActivityStoryBrandActorImpl.class);
            }
            if ("site".equals(type)) {
                return (ActivityStoryActor) context.deserialize(json, ActivityStorySiteActorImpl.class);
            }
            if ("page".equals(type)) {
                return (ActivityStoryActor) context.deserialize(json, ActivityStoryPageActorImpl.class);
            }
            if ("group".equals(type)) {
                return (ActivityStoryActor) context.deserialize(json, ActivityStoryGroupActorImpl.class);
            }
        }
        return (ActivityStoryActor) context.deserialize(json, ActivityStoryUnknownActorImpl.class);
    }

    public JsonElement serialize(ActivityStoryActor src, Type typeOfSrc, JsonSerializationContext context) {
        JsonElement element = context.serialize(src, src.getClass());
        element.getAsJsonObject().addProperty("type", src.getType().toString().toLowerCase());
        return element;
    }
}
