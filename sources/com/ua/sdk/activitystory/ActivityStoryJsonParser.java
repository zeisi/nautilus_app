package com.ua.sdk.activitystory;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.ua.sdk.UaException;
import com.ua.sdk.internal.AbstractGsonParser;
import com.ua.sdk.net.json.GsonFactory;
import java.lang.reflect.Type;

public class ActivityStoryJsonParser extends AbstractGsonParser<ActivityStory> {
    public ActivityStoryJsonParser() {
        super(GsonFactory.newActivityStoryInstance());
    }

    /* access modifiers changed from: protected */
    public ActivityStory read(Gson gson, JsonReader reader) throws UaException {
        return (ActivityStory) gson.fromJson(reader, (Type) ActivityStory.class);
    }
}
