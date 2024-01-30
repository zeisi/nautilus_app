package com.ua.sdk.friendship;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.stream.JsonReader;
import com.ua.sdk.UaException;
import com.ua.sdk.internal.JsonParser;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

public class FriendshipJsonParser implements JsonParser<Friendship> {
    private Gson gson;

    public FriendshipJsonParser(Gson gson2) {
        this.gson = gson2;
    }

    public Friendship parse(InputStream inputStream) throws UaException {
        try {
            return FriendshipTransferObject.toFriendship((FriendshipTransferObject) this.gson.fromJson(new JsonReader(new InputStreamReader(inputStream)), (Type) FriendshipTransferObject.class));
        } catch (JsonParseException e) {
            throw new UaException((Throwable) e);
        }
    }
}
