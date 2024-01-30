package com.ua.sdk.remoteconnection;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.ua.sdk.UaException;
import com.ua.sdk.internal.JsonParser;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

public class RemoteConnectionJsonParser implements JsonParser<RemoteConnection> {
    private Gson gson;

    public RemoteConnectionJsonParser(Gson gson2) {
        this.gson = gson2;
    }

    public RemoteConnection parse(InputStream inputStream) throws UaException {
        return RemoteConnectionTransferObject.toRemoteConnectionImpl((RemoteConnectionTransferObject) this.gson.fromJson(new JsonReader(new InputStreamReader(inputStream)), (Type) RemoteConnectionTransferObject.class));
    }
}
