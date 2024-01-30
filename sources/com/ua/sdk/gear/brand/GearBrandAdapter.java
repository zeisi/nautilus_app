package com.ua.sdk.gear.brand;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

public class GearBrandAdapter implements JsonDeserializer<GearBrand>, JsonSerializer<GearBrand> {
    public GearBrand deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return (GearBrand) context.deserialize(json, GearBrandImpl.class);
    }

    public JsonElement serialize(GearBrand src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src, src.getClass());
    }
}
