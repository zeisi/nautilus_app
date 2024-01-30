package com.ua.sdk.net.json;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.ua.oss.org.codehaus.jackson.map.util.Iso8601DateFormat;
import java.lang.reflect.Type;
import java.util.Date;

public class DateAdapter implements JsonSerializer<Date>, JsonDeserializer<Date> {
    public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(Iso8601DateFormat.format(src, true));
    }

    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return Iso8601DateFormat.parse(json.getAsString());
    }
}
