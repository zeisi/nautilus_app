package com.ua.sdk.gear.user;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.ua.sdk.EntityRef;
import com.ua.sdk.activitytype.ActivityType;
import com.ua.sdk.cache.EntityDatabase;
import java.lang.reflect.Type;
import java.util.List;

public class UserGearAdapter implements JsonDeserializer<UserGear>, JsonSerializer<UserGear> {
    public UserGear deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        JsonObject embedded = jsonObject.getAsJsonObject("_embedded");
        if (embedded != null) {
            JsonObject gear = embedded.getAsJsonObject("gear");
            gear.add(EntityDatabase.LINKS.TABLE_SUFFIX, embedded.getAsJsonObject(EntityDatabase.LINKS.TABLE_SUFFIX));
            jsonObject.add("gear", gear);
        }
        return (UserGear) context.deserialize(jsonObject, UserGearImpl.class);
    }

    public JsonElement serialize(UserGear src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = context.serialize(src, src.getClass()).getAsJsonObject();
        if (!(src.getGear() == null || src.getGear().getRef() == null)) {
            jsonObject.add("gear", context.serialize(src.getGear().getRef().getHref()));
        }
        if (src.getDefaultActivities() != null) {
            jsonObject.remove(EntityDatabase.LINKS.TABLE_SUFFIX);
            List<EntityRef<ActivityType>> activityTypes = src.getDefaultActivities();
            JsonArray jsonArray = new JsonArray();
            for (EntityRef<ActivityType> activityType : activityTypes) {
                jsonArray.add(context.serialize(activityType.getHref()));
            }
            jsonObject.add("default_activities", jsonArray);
        }
        return jsonObject;
    }
}
