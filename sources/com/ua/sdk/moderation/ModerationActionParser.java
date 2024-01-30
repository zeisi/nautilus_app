package com.ua.sdk.moderation;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.ua.sdk.UaException;
import com.ua.sdk.internal.AbstractGsonParser;
import com.ua.sdk.net.json.GsonFactory;

public class ModerationActionParser extends AbstractGsonParser<ModerationAction> {
    public ModerationActionParser() {
        super(GsonFactory.newModerationInstance());
    }

    /* access modifiers changed from: protected */
    public ModerationAction read(Gson gson, JsonReader reader) throws UaException {
        return (ModerationAction) gson.fromJson(reader, new TypeToken<ModerationAction>() {
        }.getType());
    }
}
