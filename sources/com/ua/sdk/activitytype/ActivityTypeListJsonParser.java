package com.ua.sdk.activitytype;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.ua.sdk.UaException;
import com.ua.sdk.internal.AbstractGsonParser;
import com.ua.sdk.net.json.GsonFactory;
import java.lang.reflect.Type;

public class ActivityTypeListJsonParser extends AbstractGsonParser<ActivityTypeList> {
    public ActivityTypeListJsonParser() {
        super(GsonFactory.newActivityTypeInstance());
    }

    /* access modifiers changed from: protected */
    public ActivityTypeList read(Gson gson, JsonReader reader) throws UaException {
        return (ActivityTypeList) gson.fromJson(reader, (Type) ActivityTypeList.class);
    }
}
