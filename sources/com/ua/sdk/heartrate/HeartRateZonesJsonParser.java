package com.ua.sdk.heartrate;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.ua.sdk.UaException;
import com.ua.sdk.internal.AbstractGsonParser;
import com.ua.sdk.net.json.GsonFactory;
import java.lang.reflect.Type;

public class HeartRateZonesJsonParser extends AbstractGsonParser<HeartRateZones> {
    public HeartRateZonesJsonParser() {
        super(GsonFactory.newHeartRateInstance());
    }

    /* access modifiers changed from: protected */
    public HeartRateZones read(Gson gson, JsonReader reader) throws UaException {
        return (HeartRateZones) gson.fromJson(reader, (Type) HeartRateZones.class);
    }
}
