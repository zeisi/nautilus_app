package com.ua.sdk.activitytimeseries;

import com.google.gson.Gson;
import com.ua.sdk.UaException;
import com.ua.sdk.internal.AbstractGsonWriter;
import com.ua.sdk.net.json.GsonFactory;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;

public class ActivityTimeSeriesJsonWriter extends AbstractGsonWriter<ActivityTimeSeries> {
    public ActivityTimeSeriesJsonWriter() {
        super(GsonFactory.newActivityTimeSeriesInstance());
    }

    /* access modifiers changed from: protected */
    public void write(ActivityTimeSeries entity, Gson gson, OutputStreamWriter writer) throws UaException {
        gson.toJson((Object) entity, (Type) entity.getClass(), (Appendable) writer);
    }
}
