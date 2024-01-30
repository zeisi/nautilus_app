package com.ua.sdk.workout;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.ua.sdk.UaException;
import com.ua.sdk.internal.AbstractGsonParser;
import com.ua.sdk.net.json.GsonFactory;
import java.lang.reflect.Type;

public class WorkoutJsonParser extends AbstractGsonParser<Workout> {
    public WorkoutJsonParser() {
        super(GsonFactory.newWorkoutInstance());
    }

    /* access modifiers changed from: protected */
    public Workout read(Gson gson, JsonReader reader) throws UaException {
        return (Workout) gson.fromJson(reader, (Type) WorkoutImpl.class);
    }
}
