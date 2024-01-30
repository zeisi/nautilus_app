package com.ua.sdk.page;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.stream.JsonReader;
import com.ua.sdk.UaException;
import com.ua.sdk.internal.JsonParser;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

public class PageJsonParser implements JsonParser<Page> {
    private Gson gson;

    public PageJsonParser(Gson gson2) {
        this.gson = gson2;
    }

    public Page parse(InputStream inputStream) throws UaException {
        try {
            return PageTO.toPageImpl((PageTO) this.gson.fromJson(new JsonReader(new InputStreamReader(inputStream)), (Type) PageTO.class));
        } catch (JsonParseException e) {
            throw new UaException((Throwable) e);
        }
    }
}
