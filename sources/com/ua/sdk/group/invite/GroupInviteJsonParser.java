package com.ua.sdk.group.invite;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.ua.sdk.UaException;
import com.ua.sdk.internal.AbstractGsonParser;
import com.ua.sdk.net.json.GsonFactory;
import java.lang.reflect.Type;

public class GroupInviteJsonParser extends AbstractGsonParser<GroupInvite> {
    public GroupInviteJsonParser() {
        super(GsonFactory.newGroupInstance());
    }

    /* access modifiers changed from: protected */
    public GroupInvite read(Gson gson, JsonReader reader) throws UaException {
        return (GroupInvite) gson.fromJson(reader, (Type) GroupInvite.class);
    }
}
