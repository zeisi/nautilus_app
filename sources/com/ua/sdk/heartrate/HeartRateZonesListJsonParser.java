package com.ua.sdk.heartrate;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.ua.sdk.UaException;
import com.ua.sdk.internal.AbstractGsonParser;
import com.ua.sdk.net.json.GsonFactory;

public class HeartRateZonesListJsonParser extends AbstractGsonParser<HeartRateZonesList> {
    public HeartRateZonesListJsonParser() {
        super(GsonFactory.newHeartRateInstance());
    }

    /* access modifiers changed from: protected */
    public HeartRateZonesList read(Gson gson, JsonReader reader) throws UaException {
        return (HeartRateZonesList) gson.fromJson(reader, new TypeToken<HeartRateZonesList>() {
        }.getType());
    }
}
