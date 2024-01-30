package com.ua.sdk.activitytimeseries;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.ua.sdk.UaException;
import com.ua.sdk.internal.AbstractGsonParser;
import com.ua.sdk.net.json.GsonFactory;
import java.lang.reflect.Type;

public class ActivityTimeSeriesJsonParser extends AbstractGsonParser<ActivityTimeSeries> {
    public ActivityTimeSeriesJsonParser() {
        super(GsonFactory.newActivityTimeSeriesInstance());
    }

    /* access modifiers changed from: protected */
    public ActivityTimeSeries read(Gson gson, JsonReader reader) throws UaException {
        return (ActivityTimeSeries) gson.fromJson(reader, (Type) ActivityTimeSeriesImpl.class);
    }
}
