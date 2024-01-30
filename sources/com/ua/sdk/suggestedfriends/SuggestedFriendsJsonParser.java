package com.ua.sdk.suggestedfriends;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.ua.sdk.UaException;
import com.ua.sdk.internal.AbstractGsonParser;
import java.lang.reflect.Type;

public class SuggestedFriendsJsonParser extends AbstractGsonParser<SuggestedFriends> {
    public SuggestedFriendsJsonParser(Gson gson) {
        super(gson);
    }

    /* access modifiers changed from: protected */
    public SuggestedFriends read(Gson gson, JsonReader reader) throws UaException {
        return (SuggestedFriends) gson.fromJson(reader, (Type) SuggestedFriendsImpl.class);
    }
}
