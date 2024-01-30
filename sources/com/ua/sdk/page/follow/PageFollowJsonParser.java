package com.ua.sdk.page.follow;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.stream.JsonReader;
import com.ua.sdk.UaException;
import com.ua.sdk.internal.AbstractGsonParser;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

public class PageFollowJsonParser extends AbstractGsonParser<PageFollow> {
    public PageFollowJsonParser(Gson gson) {
        super(gson);
    }

    /* access modifiers changed from: protected */
    public PageFollow read(Gson gson, JsonReader reader) throws UaException {
        return null;
    }

    public PageFollow parse(InputStream inputStream) throws UaException {
        try {
            return PageFollowTransferObject.toPageFollowImpl((PageFollowTransferObject) this.mGson.fromJson(new JsonReader(new InputStreamReader(inputStream)), (Type) PageFollowTransferObject.class));
        } catch (JsonParseException e) {
            throw new UaException((Throwable) e);
        }
    }
}
