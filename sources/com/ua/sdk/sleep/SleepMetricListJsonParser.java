package com.ua.sdk.sleep;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.ua.sdk.UaException;
import com.ua.sdk.internal.AbstractGsonParser;
import com.ua.sdk.net.json.GsonFactory;
import java.lang.reflect.Type;

public class SleepMetricListJsonParser extends AbstractGsonParser<SleepMetricList> {
    public SleepMetricListJsonParser() {
        super(GsonFactory.newSleepInstance());
    }

    /* access modifiers changed from: protected */
    public SleepMetricList read(Gson gson, JsonReader reader) throws UaException {
        return (SleepMetricList) gson.fromJson(reader, (Type) SleepMetricList.class);
    }
}
