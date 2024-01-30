package com.ua.sdk.activitytype;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.ua.sdk.internal.JsonParser;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

public class ActivityTypeParser implements JsonParser<ActivityType> {
    private Gson gson;

    public ActivityTypeParser(Gson gson2) {
        this.gson = gson2;
    }

    public ActivityType parse(InputStream inputStream) {
        return ActivityTypeTransferObject.toImpl((ActivityTypeTransferObject) this.gson.fromJson(new JsonReader(new InputStreamReader(inputStream)), (Type) ActivityTypeTransferObject.class));
    }
}
