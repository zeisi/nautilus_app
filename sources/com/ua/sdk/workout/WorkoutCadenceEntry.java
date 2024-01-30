package com.ua.sdk.workout;

public interface WorkoutCadenceEntry extends BaseTimeSeriesEntry<WorkoutCadenceEntry> {
    int getInstantaneousCadence();
}
