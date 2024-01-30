package com.ua.sdk.activitytype;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;

public class ActivityTypeAdapter implements JsonDeserializer<ActivityTypeImpl> {
    public ActivityTypeImpl deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        ActivityTypeTransferObject to = (ActivityTypeTransferObject) context.deserialize(json, ActivityTypeTransferObject.class);
        if (to != null) {
            return ActivityTypeTransferObject.toImpl(to);
        }
        return null;
    }
}
