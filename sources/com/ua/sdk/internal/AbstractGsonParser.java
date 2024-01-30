package com.ua.sdk.internal;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.stream.JsonReader;
import com.ua.sdk.UaException;
import java.io.InputStream;
import java.io.InputStreamReader;

public abstract class AbstractGsonParser<T> implements JsonParser<T> {
    protected Gson mGson;

    /* access modifiers changed from: protected */
    public abstract T read(Gson gson, JsonReader jsonReader) throws UaException;

    public AbstractGsonParser(Gson gson) {
        Precondition.isNotNull(gson);
        this.mGson = gson;
    }

    public T parse(InputStream inputStream) throws UaException {
        try {
            return read(this.mGson, new JsonReader(new InputStreamReader(inputStream)));
        } catch (JsonParseException e) {
            throw new UaException((Throwable) e);
        }
    }
}
