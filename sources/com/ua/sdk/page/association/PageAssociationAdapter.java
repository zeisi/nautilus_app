package com.ua.sdk.page.association;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

public class PageAssociationAdapter implements JsonSerializer<PageAssociation>, JsonDeserializer<PageAssociation> {
    public PageAssociation deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return PageAssociationTransferObject.toPageAssocaitionImpl((PageAssociationTransferObject) context.deserialize(json, PageAssociationTransferObject.class));
    }

    public JsonElement serialize(PageAssociation src, Type typeOfSrc, JsonSerializationContext context) {
        PageAssociationTransferObject to = PageAssociationTransferObject.fromPageAssocaition(src);
        return context.serialize(to, to.getClass());
    }
}
