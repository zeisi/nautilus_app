package com.ua.sdk.location;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

public class LocationAdapter implements JsonSerializer<Location>, JsonDeserializer<Location> {
    public Location deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (!json.isJsonObject()) {
            return null;
        }
        JsonObject jsonObject = json.getAsJsonObject();
        return new LocationImpl(getString(jsonObject, "country"), getString(jsonObject, "region"), getString(jsonObject, "locality"), getString(jsonObject, "address"));
    }

    public JsonElement serialize(Location src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("country", src.getCountry());
        jsonObject.addProperty("region", src.getRegion());
        jsonObject.addProperty("locality", src.getLocality());
        jsonObject.addProperty("address", src.getAddress());
        return jsonObject;
    }

    private String getString(JsonObject json, String key) {
        JsonElement element = json.get(key);
        if (element != null) {
            return element.getAsString();
        }
        return null;
    }
}
