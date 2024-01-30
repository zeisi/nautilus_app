package com.ua.sdk.activitystory;

import com.google.gson.Gson;
import com.ua.sdk.internal.AbstractGsonWriter;
import com.ua.sdk.net.json.GsonFactory;
import java.io.OutputStreamWriter;

public class ActivityStoryJsonWriter extends AbstractGsonWriter<ActivityStory> {
    public ActivityStoryJsonWriter() {
        super(GsonFactory.newActivityStoryInstance());
    }

    /* access modifiers changed from: protected */
    public void write(ActivityStory entity, Gson gson, OutputStreamWriter writer) {
        gson.toJson((Object) entity, (Appendable) writer);
    }
}
