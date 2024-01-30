package com.ua.sdk.page.association;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.ua.sdk.UaException;
import com.ua.sdk.internal.AbstractGsonParser;
import com.ua.sdk.net.json.GsonFactory;

public class PageAssociationListJsonParser extends AbstractGsonParser<PageAssociationList> {
    public PageAssociationListJsonParser() {
        super(GsonFactory.newPageAssociationInstance());
    }

    /* access modifiers changed from: protected */
    public PageAssociationList read(Gson gson, JsonReader reader) throws UaException {
        return (PageAssociationList) gson.fromJson(reader, new TypeToken<PageAssociationList>() {
        }.getType());
    }
}
