package com.ua.sdk.gear.brand;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.ua.sdk.EntityList;
import com.ua.sdk.UaException;
import com.ua.sdk.internal.AbstractGsonParser;
import com.ua.sdk.net.json.GsonFactory;

public class GearBrandListJsonParser extends AbstractGsonParser<EntityList<GearBrand>> {
    public GearBrandListJsonParser() {
        super(GsonFactory.newGearInstance());
    }

    /* access modifiers changed from: protected */
    public EntityList<GearBrand> read(Gson gson, JsonReader reader) throws UaException {
        return (EntityList) gson.fromJson(reader, new TypeToken<GearBrandList>() {
        }.getType());
    }
}
