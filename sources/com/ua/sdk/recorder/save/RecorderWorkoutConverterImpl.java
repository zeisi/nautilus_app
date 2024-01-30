package com.ua.sdk.recorder.save;

import com.ua.sdk.datapoint.DataFrame;
import com.ua.sdk.internal.Precondition;
import com.ua.sdk.recorder.RecorderContext;
import com.ua.sdk.recorder.RecorderWorkoutConverter;
import com.ua.sdk.workout.Workout;
import com.ua.sdk.workout.WorkoutNameGenerator;
import java.util.List;

public class RecorderWorkoutConverterImpl implements RecorderWorkoutConverter {
    private RecorderContext context;
    private List<DataFrame> recordData;

    public RecorderWorkoutConverterImpl(List<DataFrame> recordData2, RecorderContext context2) {
        this.recordData = (List) Precondition.isNotNull(recordData2, "record data");
        this.context = (RecorderContext) Precondition.isNotNull(context2, "recorder context");
    }

    public Workout generateWorkout(String workoutName) {
        return new RecordWorkoutV7Converter(this.recordData, this.context).buildWorkout(workoutName);
    }

    public Workout generateWorkout(WorkoutNameGenerator workoutNameGenerator) {
        return new RecordWorkoutV7Converter(this.recordData, this.context).buildWorkout(workoutNameGenerator);
    }
}
