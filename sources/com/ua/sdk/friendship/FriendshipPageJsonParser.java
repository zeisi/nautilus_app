package com.ua.sdk.friendship;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.stream.JsonReader;
import com.ua.sdk.EntityList;
import com.ua.sdk.UaException;
import com.ua.sdk.internal.JsonParser;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

public class FriendshipPageJsonParser implements JsonParser<EntityList<Friendship>> {
    private Gson gson;

    public FriendshipPageJsonParser(Gson gson2) {
        this.gson = gson2;
    }

    public EntityList<Friendship> parse(InputStream inputStream) throws UaException {
        try {
            return FriendshipPageTransferObject.fromTransferObject((FriendshipPageTransferObject) this.gson.fromJson(new JsonReader(new InputStreamReader(inputStream)), (Type) FriendshipPageTransferObject.class));
        } catch (JsonParseException e) {
            throw new UaException((Throwable) e);
        }
    }
}
