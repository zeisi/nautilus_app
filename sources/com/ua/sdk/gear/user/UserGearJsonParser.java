package com.ua.sdk.gear.user;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.ua.sdk.UaException;
import com.ua.sdk.internal.AbstractGsonParser;
import com.ua.sdk.net.json.GsonFactory;

public class UserGearJsonParser extends AbstractGsonParser<UserGear> {
    public UserGearJsonParser() {
        super(GsonFactory.newGearInstance());
    }

    /* access modifiers changed from: protected */
    public UserGear read(Gson gson, JsonReader reader) throws UaException {
        return (UserGear) gson.fromJson(reader, new TypeToken<UserGear>() {
        }.getType());
    }
}
