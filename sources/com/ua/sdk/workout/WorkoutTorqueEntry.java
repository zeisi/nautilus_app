package com.ua.sdk.workout;

public interface WorkoutTorqueEntry extends BaseTimeSeriesEntry<WorkoutTorqueEntry> {
    double getInstantaneousTorque();
}
