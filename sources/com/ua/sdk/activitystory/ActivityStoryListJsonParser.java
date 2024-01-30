package com.ua.sdk.activitystory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.ua.sdk.UaException;
import com.ua.sdk.internal.AbstractGsonParser;
import com.ua.sdk.net.json.GsonFactory;

public class ActivityStoryListJsonParser extends AbstractGsonParser<ActivityStoryList> {
    public ActivityStoryListJsonParser() {
        super(GsonFactory.newActivityStoryInstance());
    }

    /* access modifiers changed from: protected */
    public ActivityStoryList read(Gson gson, JsonReader reader) throws UaException {
        return (ActivityStoryList) gson.fromJson(reader, new TypeToken<ActivityStoryList>() {
        }.getType());
    }
}
