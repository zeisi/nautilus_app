package com.ua.sdk.sleep;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.ua.sdk.UaException;
import com.ua.sdk.internal.AbstractGsonParser;
import com.ua.sdk.net.json.GsonFactory;
import java.lang.reflect.Type;

public class SleepMetricJsonParser extends AbstractGsonParser<SleepMetric> {
    public SleepMetricJsonParser() {
        super(GsonFactory.newSleepInstance());
    }

    /* access modifiers changed from: protected */
    public SleepMetric read(Gson gson, JsonReader reader) throws UaException {
        return (SleepMetric) gson.fromJson(reader, (Type) SleepMetricImpl.class);
    }
}
