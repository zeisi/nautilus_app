package com.ua.sdk.actigraphy;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.ua.sdk.EntityList;
import com.ua.sdk.UaException;
import com.ua.sdk.internal.JsonParser;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

public class ActigraphyJsonParser implements JsonParser<EntityList<Actigraphy>> {
    private Gson gson;

    public ActigraphyJsonParser(Gson gson2) {
        this.gson = gson2;
    }

    public EntityList<Actigraphy> parse(InputStream inputStream) throws UaException {
        return ActigraphyTransferObject.toList((ActigraphyTransferObject) this.gson.fromJson(new JsonReader(new InputStreamReader(inputStream)), (Type) ActigraphyTransferObject.class));
    }
}
