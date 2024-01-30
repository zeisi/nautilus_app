package com.ua.sdk.workout;

public interface WorkoutStepsEntry extends BaseTimeSeriesEntry<WorkoutStepsEntry> {
    int getInstantaneousSteps();
}
