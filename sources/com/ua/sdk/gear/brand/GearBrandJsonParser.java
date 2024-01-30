package com.ua.sdk.gear.brand;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.ua.sdk.UaException;
import com.ua.sdk.internal.AbstractGsonParser;
import com.ua.sdk.net.json.GsonFactory;

public class GearBrandJsonParser extends AbstractGsonParser<GearBrand> {
    public GearBrandJsonParser() {
        super(GsonFactory.newGearInstance());
    }

    /* access modifiers changed from: protected */
    public GearBrand read(Gson gson, JsonReader reader) throws UaException {
        return (GearBrand) gson.fromJson(reader, new TypeToken<GearBrand>() {
        }.getType());
    }
}
