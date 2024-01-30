package com.ua.sdk.activitystory;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.ua.sdk.activitystory.Attachment;
import com.ua.sdk.activitystory.object.ActivityStoryHighlightImpl;
import java.lang.reflect.Type;

public class ActivityStoryAttachmentTypeAdapter implements JsonSerializer<Attachment.Type>, JsonDeserializer<Attachment.Type> {
    public Attachment.Type deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return (Attachment.Type) context.deserialize(json, ActivityStoryHighlightImpl.class);
    }

    public JsonElement serialize(Attachment.Type src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src, src.getClass());
    }
}
