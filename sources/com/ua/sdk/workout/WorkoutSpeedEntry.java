package com.ua.sdk.workout;

public interface WorkoutSpeedEntry extends BaseTimeSeriesEntry<WorkoutSpeedEntry> {
    double getInstantaneousSpeed();
}
