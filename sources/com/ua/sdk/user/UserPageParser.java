package com.ua.sdk.user;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.stream.JsonReader;
import com.ua.sdk.EntityList;
import com.ua.sdk.UaException;
import com.ua.sdk.internal.JsonParser;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

public class UserPageParser implements JsonParser<EntityList<User>> {
    private Gson gson;

    public UserPageParser(Gson gson2) {
        this.gson = gson2;
    }

    public EntityList<User> parse(InputStream inputStream) throws UaException {
        try {
            return UserPageTransferObject.toPage((UserPageTransferObject) this.gson.fromJson(new JsonReader(new InputStreamReader(inputStream)), (Type) UserPageTransferObject.class));
        } catch (JsonParseException e) {
            throw new UaException((Throwable) e);
        }
    }
}
