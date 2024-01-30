package com.ua.sdk.workout;

import com.google.gson.Gson;
import com.ua.sdk.UaException;
import com.ua.sdk.internal.AbstractGsonWriter;
import com.ua.sdk.net.json.GsonFactory;
import java.io.OutputStreamWriter;

public class WorkoutJsonWriter extends AbstractGsonWriter<Workout> {
    public WorkoutJsonWriter() {
        super(GsonFactory.newWorkoutInstance());
    }

    /* access modifiers changed from: protected */
    public void write(Workout entity, Gson gson, OutputStreamWriter writer) throws UaException {
        gson.toJson((Object) entity, (Appendable) writer);
    }
}
