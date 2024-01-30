package com.ua.sdk.page.follow;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.stream.JsonReader;
import com.ua.sdk.EntityList;
import com.ua.sdk.UaException;
import com.ua.sdk.internal.JsonParser;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

public class PageFollowPageJsonParser implements JsonParser<EntityList<PageFollow>> {
    private Gson gson;

    public PageFollowPageJsonParser(Gson gson2) {
        this.gson = gson2;
    }

    public EntityList<PageFollow> parse(InputStream inputStream) throws UaException {
        try {
            return PageFollowPageTransferObject.toPage((PageFollowPageTransferObject) this.gson.fromJson(new JsonReader(new InputStreamReader(inputStream)), (Type) PageFollowPageTransferObject.class));
        } catch (JsonParseException e) {
            throw new UaException((Throwable) e);
        }
    }
}
