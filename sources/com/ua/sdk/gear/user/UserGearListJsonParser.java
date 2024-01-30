package com.ua.sdk.gear.user;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.ua.sdk.EntityList;
import com.ua.sdk.UaException;
import com.ua.sdk.internal.AbstractGsonParser;
import com.ua.sdk.net.json.GsonFactory;

public class UserGearListJsonParser extends AbstractGsonParser<EntityList<UserGear>> {
    public UserGearListJsonParser() {
        super(GsonFactory.newGearInstance());
    }

    /* access modifiers changed from: protected */
    public EntityList<UserGear> read(Gson gson, JsonReader reader) throws UaException {
        return (EntityList) gson.fromJson(reader, new TypeToken<UserGearList>() {
        }.getType());
    }
}
