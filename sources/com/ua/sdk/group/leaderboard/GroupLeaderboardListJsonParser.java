package com.ua.sdk.group.leaderboard;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.ua.sdk.UaException;
import com.ua.sdk.internal.AbstractGsonParser;
import com.ua.sdk.net.json.GsonFactory;

public class GroupLeaderboardListJsonParser extends AbstractGsonParser<GroupLeaderboardList> {
    public GroupLeaderboardListJsonParser() {
        super(GsonFactory.newGroupInstance());
    }

    /* access modifiers changed from: protected */
    public GroupLeaderboardList read(Gson gson, JsonReader reader) throws UaException {
        return (GroupLeaderboardList) gson.fromJson(reader, new TypeToken<GroupLeaderboardList>() {
        }.getType());
    }
}
