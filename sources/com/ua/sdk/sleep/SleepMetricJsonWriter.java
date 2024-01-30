package com.ua.sdk.sleep;

import com.google.gson.Gson;
import com.ua.sdk.UaException;
import com.ua.sdk.internal.AbstractGsonWriter;
import com.ua.sdk.net.json.GsonFactory;
import java.io.OutputStreamWriter;

public class SleepMetricJsonWriter extends AbstractGsonWriter<SleepMetric> {
    public SleepMetricJsonWriter() {
        super(GsonFactory.newSleepInstance());
    }

    /* access modifiers changed from: protected */
    public void write(SleepMetric entity, Gson gson, OutputStreamWriter writer) throws UaException {
        gson.toJson((Object) entity, (Appendable) writer);
    }
}
