package com.ua.sdk.suggestedfriends;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.ua.sdk.UaException;
import com.ua.sdk.internal.AbstractGsonParser;

public class SuggestedFriendsListJsonParser extends AbstractGsonParser<SuggestedFriendsListImpl> {
    public SuggestedFriendsListJsonParser(Gson gson) {
        super(gson);
    }

    /* access modifiers changed from: protected */
    public SuggestedFriendsListImpl read(Gson gson, JsonReader reader) throws UaException {
        return (SuggestedFriendsListImpl) gson.fromJson(reader, new TypeToken<SuggestedFriendsListImpl>() {
        }.getType());
    }
}
