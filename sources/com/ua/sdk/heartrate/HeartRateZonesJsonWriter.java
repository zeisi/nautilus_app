package com.ua.sdk.heartrate;

import com.google.gson.Gson;
import com.ua.sdk.UaException;
import com.ua.sdk.internal.AbstractGsonWriter;
import com.ua.sdk.net.json.GsonFactory;
import java.io.OutputStreamWriter;

public class HeartRateZonesJsonWriter extends AbstractGsonWriter<HeartRateZones> {
    public HeartRateZonesJsonWriter() {
        super(GsonFactory.newHeartRateInstance());
    }

    /* access modifiers changed from: protected */
    public void write(HeartRateZones entity, Gson gson, OutputStreamWriter writer) throws UaException {
        gson.toJson((Object) entity, (Appendable) writer);
    }
}
