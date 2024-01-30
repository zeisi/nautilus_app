package com.ua.sdk.remoteconnection;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.ua.sdk.UaException;
import com.ua.sdk.internal.JsonParser;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

public class RemoteConnectionTypeJsonParser implements JsonParser<RemoteConnectionType> {
    private Gson gson;

    public RemoteConnectionTypeJsonParser(Gson gson2) {
        this.gson = gson2;
    }

    public RemoteConnectionType parse(InputStream inputStream) throws UaException {
        return RemoteConnectionTypeTransferObject.toRemoteConnectionTypeImpl((RemoteConnectionTypeTransferObject) this.gson.fromJson(new JsonReader(new InputStreamReader(inputStream)), (Type) RemoteConnectionTypeTransferObject.class));
    }
}
