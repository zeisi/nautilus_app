package com.ua.sdk.recorder;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.ua.sdk.UaException;
import com.ua.sdk.internal.AbstractGsonParser;
import com.ua.sdk.net.json.GsonFactory;
import java.lang.reflect.Type;

public class DataSourceConfigurationListParser extends AbstractGsonParser<DataSourceConfigurationList> {
    public DataSourceConfigurationListParser() {
        super(GsonFactory.newRecorderConfigurationInstance());
    }

    /* access modifiers changed from: protected */
    public DataSourceConfigurationList read(Gson gson, JsonReader reader) throws UaException {
        return (DataSourceConfigurationList) gson.fromJson(reader, (Type) DataSourceConfigurationList.class);
    }
}
