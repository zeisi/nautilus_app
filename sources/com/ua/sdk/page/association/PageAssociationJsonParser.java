package com.ua.sdk.page.association;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.ua.sdk.UaException;
import com.ua.sdk.internal.AbstractGsonParser;
import com.ua.sdk.net.json.GsonFactory;
import java.lang.reflect.Type;

public class PageAssociationJsonParser extends AbstractGsonParser<PageAssociation> {
    public PageAssociationJsonParser() {
        super(GsonFactory.newPageAssociationInstance());
    }

    /* access modifiers changed from: protected */
    public PageAssociation read(Gson gson, JsonReader reader) throws UaException {
        return (PageAssociation) gson.fromJson(reader, (Type) PageAssociationImpl.class);
    }
}
