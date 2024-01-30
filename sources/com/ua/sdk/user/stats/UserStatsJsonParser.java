package com.ua.sdk.user.stats;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.ua.sdk.UaException;
import com.ua.sdk.internal.AbstractGsonParser;
import com.ua.sdk.net.json.GsonFactory;

public class UserStatsJsonParser extends AbstractGsonParser<UserStats> {
    public UserStatsJsonParser() {
        super(GsonFactory.newUserStatsInstance());
    }

    /* access modifiers changed from: protected */
    public UserStats read(Gson gson, JsonReader reader) throws UaException {
        return (UserStats) gson.fromJson(reader, new TypeToken<UserStats>() {
        }.getType());
    }
}
