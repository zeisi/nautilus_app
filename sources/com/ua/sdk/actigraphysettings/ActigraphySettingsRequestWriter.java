package com.ua.sdk.actigraphysettings;

import com.google.gson.Gson;
import com.ua.sdk.UaException;
import com.ua.sdk.internal.AbstractGsonWriter;
import com.ua.sdk.net.json.GsonFactory;
import java.io.OutputStreamWriter;

public class ActigraphySettingsRequestWriter extends AbstractGsonWriter<ActigraphySettings> {
    public ActigraphySettingsRequestWriter() {
        super(GsonFactory.newInstance());
    }

    /* access modifiers changed from: protected */
    public void write(ActigraphySettings entity, Gson gson, OutputStreamWriter writer) throws UaException {
        gson.toJson((Object) ActigraphySettingsRequestTO.toTransferObject(entity), (Appendable) writer);
    }
}
