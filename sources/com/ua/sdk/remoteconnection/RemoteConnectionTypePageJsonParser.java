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

public class RemoteConnectionTypePageJsonParser implements JsonParser<EntityList<RemoteConnectionType>> {
    private final Gson gson;

    public RemoteConnectionTypePageJsonParser(Gson gson2) {
        this.gson = gson2;
    }

    public EntityList<RemoteConnectionType> parse(InputStream inputStream) throws UaException {
        try {
            return RemoteConnectionTypePageTO.fromTransferObject((RemoteConnectionTypePageTO) this.gson.fromJson(new JsonReader(new InputStreamReader(inputStream)), (Type) RemoteConnectionTypePageTO.class));
        } catch (JsonParseException e) {
            throw new UaException((Throwable) e);
        }
    }
}
