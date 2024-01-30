package com.ua.sdk.remoteconnection;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.stream.JsonReader;
import com.ua.sdk.EntityList;
import com.ua.sdk.UaException;
import com.ua.sdk.internal.JsonParser;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

public class RemoteConnectionPageJsonParser implements JsonParser<EntityList<RemoteConnection>> {
    private final Gson gson;

    public RemoteConnectionPageJsonParser(Gson gson2) {
        this.gson = gson2;
    }

    public EntityList<RemoteConnection> parse(InputStream inputStream) throws UaException {
        try {
            return RemoteConnectionPageTO.toPage((RemoteConnectionPageTO) this.gson.fromJson(new JsonReader(new InputStreamReader(inputStream)), (Type) RemoteConnectionPageTO.class));
        } catch (JsonParseException e) {
            throw new UaException((Throwable) e);
        }
    }
}
