package com.ua.sdk.bodymass;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.ua.sdk.UaException;
import com.ua.sdk.internal.AbstractGsonParser;
import com.ua.sdk.net.json.GsonFactory;

public class BodyMassListJsonParser extends AbstractGsonParser<BodyMassList> {
    public BodyMassListJsonParser() {
        super(GsonFactory.newBodyMassInstance());
    }

    /* access modifiers changed from: protected */
    public BodyMassList read(Gson gson, JsonReader reader) throws UaException {
        return (BodyMassList) gson.fromJson(reader, new TypeToken<BodyMassList>() {
        }.getType());
    }
}
