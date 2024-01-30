package com.ua.sdk.bodymass;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.ua.sdk.UaException;
import com.ua.sdk.internal.AbstractGsonParser;
import com.ua.sdk.net.json.GsonFactory;
import java.lang.reflect.Type;

public class BodyMassJsonParser extends AbstractGsonParser<BodyMass> {
    public BodyMassJsonParser() {
        super(GsonFactory.newBodyMassInstance());
    }

    /* access modifiers changed from: protected */
    public BodyMass read(Gson gson, JsonReader reader) throws UaException {
        return (BodyMass) gson.fromJson(reader, (Type) BodyMass.class);
    }
}
