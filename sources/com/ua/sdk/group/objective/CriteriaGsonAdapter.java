package com.ua.sdk.group.objective;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.Map;

public class CriteriaGsonAdapter implements JsonDeserializer<Criteria>, JsonSerializer<Criteria> {
    public Criteria deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        CriteriaItem item;
        JsonObject jsonObject = json.getAsJsonObject();
        Criteria criteria = new CriteriaImpl();
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            String name = entry.getKey();
            if (CriteriaTypes.ACTIVITY_TYPE.equals(name)) {
                CriteriaItem item2 = new ActivityTypeCriteriaItem();
                item2.setValue(Integer.valueOf(entry.getValue().getAsInt()));
                item = item2;
            } else if (CriteriaTypes.SORT.equals(name)) {
                CriteriaItem item3 = new SortCriteriaItem();
                item3.setValue(entry.getValue().getAsString());
                item = item3;
            } else {
                CriteriaItem item4 = new CriteriaItemImpl();
                ((CriteriaItemImpl) item4).setValue(entry.getValue().toString());
                ((CriteriaItemImpl) item4).name = name;
                item4.setValue(entry.getValue().toString());
                item = item4;
            }
            criteria.addCriteriaItem(item);
        }
        return criteria;
    }

    public JsonElement serialize(Criteria src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject json = new JsonObject();
        for (String key : ((CriteriaImpl) src).criteria.keySet()) {
            if (CriteriaTypes.ACTIVITY_TYPE.equals(key)) {
                json.add(key, new JsonPrimitive((Number) (Integer) ((CriteriaImpl) src).criteria.get(key).getValue()));
            } else if (CriteriaTypes.SORT.equals(key)) {
                json.add(key, new JsonPrimitive((String) ((CriteriaImpl) src).criteria.get(key).getValue()));
            } else {
                json.add(key, new JsonPrimitive((String) ((CriteriaImpl) src).criteria.get(key).getValue()));
            }
        }
        return json;
    }
}
