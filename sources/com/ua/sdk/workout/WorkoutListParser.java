package com.ua.sdk.workout;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.ua.sdk.UaException;
import com.ua.sdk.internal.AbstractGsonParser;
import com.ua.sdk.net.json.GsonFactory;
import java.lang.reflect.Type;

public class WorkoutListParser extends AbstractGsonParser<WorkoutList> {
    public WorkoutListParser() {
        super(GsonFactory.newWorkoutInstance());
    }

    /* access modifiers changed from: protected */
    public WorkoutList read(Gson gson, JsonReader reader) throws UaException {
        return (WorkoutList) gson.fromJson(reader, (Type) WorkoutListImpl.class);
    }
}
