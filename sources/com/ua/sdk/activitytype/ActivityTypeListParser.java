package com.ua.sdk.activitytype;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.ua.sdk.internal.JsonParser;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

public class ActivityTypeListParser implements JsonParser<List<ActivityTypeImpl>> {
    private Gson gson;

    public ActivityTypeListParser(Gson gson2) {
        this.gson = gson2;
    }

    public List<ActivityTypeImpl> parse(InputStream inputStream) {
        return ActivityTypeListTransferObject.toImplList((ActivityTypeListTransferObject) this.gson.fromJson(new JsonReader(new InputStreamReader(inputStream)), (Type) ActivityTypeListTransferObject.class));
    }
}
