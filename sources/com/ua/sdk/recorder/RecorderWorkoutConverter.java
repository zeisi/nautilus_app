package com.ua.sdk.recorder;

import com.ua.sdk.workout.Workout;
import com.ua.sdk.workout.WorkoutNameGenerator;

public interface RecorderWorkoutConverter {
    Workout generateWorkout(WorkoutNameGenerator workoutNameGenerator);

    Workout generateWorkout(String str);
}
