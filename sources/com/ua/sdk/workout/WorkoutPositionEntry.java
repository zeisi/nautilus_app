package com.ua.sdk.workout;

public interface WorkoutPositionEntry extends BaseTimeSeriesEntry<WorkoutPositionEntry> {
    Double getElevation();

    Double getLatitude();

    Double getLongitude();
}
