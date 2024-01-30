package com.ua.sdk.recorder;

import com.google.gson.Gson;
import com.ua.sdk.UaException;
import com.ua.sdk.internal.AbstractGsonWriter;
import com.ua.sdk.net.json.GsonFactory;
import java.io.OutputStreamWriter;

public class DataSourceConfigurationListWriter extends AbstractGsonWriter {
    public DataSourceConfigurationListWriter() {
        super(GsonFactory.newRecorderConfigurationInstance());
    }

    /* access modifiers changed from: protected */
    public void write(Object entity, Gson gson, OutputStreamWriter writer) throws UaException {
        gson.toJson(entity, (Appendable) writer);
    }
}
