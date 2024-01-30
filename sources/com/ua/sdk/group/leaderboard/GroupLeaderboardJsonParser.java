package com.ua.sdk.group.leaderboard;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.ua.sdk.UaException;
import com.ua.sdk.internal.AbstractGsonParser;
import com.ua.sdk.net.json.GsonFactory;
import java.lang.reflect.Type;

public class GroupLeaderboardJsonParser extends AbstractGsonParser<GroupLeaderboard> {
    public GroupLeaderboardJsonParser() {
        super(GsonFactory.newGroupInstance());
    }

    /* access modifiers changed from: protected */
    public GroupLeaderboard read(Gson gson, JsonReader reader) throws UaException {
        return (GroupLeaderboard) gson.fromJson(reader, (Type) GroupLeaderboard.class);
    }
}
