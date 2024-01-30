package com.ua.sdk.gear;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.ua.sdk.UaException;
import com.ua.sdk.internal.AbstractGsonParser;
import com.ua.sdk.net.json.GsonFactory;

public class GearJsonParser extends AbstractGsonParser<Gear> {
    public GearJsonParser() {
        super(GsonFactory.newGearInstance());
    }

    /* access modifiers changed from: protected */
    public Gear read(Gson gson, JsonReader reader) throws UaException {
        return (Gear) gson.fromJson(reader, new TypeToken<Gear>() {
        }.getType());
    }
}
