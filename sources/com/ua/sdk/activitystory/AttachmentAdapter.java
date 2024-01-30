package com.ua.sdk.activitystory;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

public class AttachmentAdapter implements JsonSerializer<Attachment>, JsonDeserializer<Attachment> {
    public Attachment deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonElement typeJson;
        JsonElement objectJson = json.getAsJsonObject().get("object");
        if (!(objectJson == null || (typeJson = objectJson.getAsJsonObject().get("type")) == null)) {
            String type = typeJson.getAsString();
            if ("photo".equals(type)) {
                return (Attachment) context.deserialize(json, PhotoAttachmentImpl.class);
            }
            if ("video".equals(type)) {
                return (Attachment) context.deserialize(json, VideoAttachmentImpl.class);
            }
        }
        return null;
    }

    public JsonElement serialize(Attachment src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src, src.getClass());
    }
}
