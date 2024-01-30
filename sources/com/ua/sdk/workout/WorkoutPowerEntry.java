package com.ua.sdk.workout;

public interface WorkoutPowerEntry extends BaseTimeSeriesEntry<WorkoutPowerEntry> {
    double getInstantaneousPower();
}
