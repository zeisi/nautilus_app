package com.ua.sdk.user;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.stream.JsonReader;
import com.ua.sdk.UaException;
import com.ua.sdk.internal.JsonParser;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

public class UserJsonParser implements JsonParser<User> {
    private Gson gson;

    public UserJsonParser(Gson gson2) {
        this.gson = gson2;
    }

    public UserImpl parse(InputStream inputStream) throws UaException {
        try {
            return UserTO.fromTransferObject((UserTO) this.gson.fromJson(new JsonReader(new InputStreamReader(inputStream)), (Type) UserTO.class));
        } catch (JsonParseException e) {
            throw new UaException((Throwable) e);
        }
    }
}
