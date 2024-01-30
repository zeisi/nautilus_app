package com.ua.sdk.bodymass;

import com.google.gson.Gson;
import com.ua.sdk.UaException;
import com.ua.sdk.internal.AbstractGsonWriter;
import com.ua.sdk.net.json.GsonFactory;
import java.io.OutputStreamWriter;

public class BodyMassJsonWriter extends AbstractGsonWriter<BodyMass> {
    public BodyMassJsonWriter() {
        super(GsonFactory.newBodyMassInstance());
    }

    /* access modifiers changed from: protected */
    public void write(BodyMass entity, Gson gson, OutputStreamWriter writer) throws UaException {
        gson.toJson((Object) entity, (Appendable) writer);
    }
}
