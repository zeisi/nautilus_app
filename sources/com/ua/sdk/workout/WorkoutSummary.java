package com.ua.sdk.workout;

import com.ua.sdk.Entity;
import com.ua.sdk.actigraphy.ActigraphyAggregatesImpl;
import java.util.Date;

public interface WorkoutSummary extends Entity {
    int getActivityTypeId();

    Date getEndDateTime();

    String getName();

    Date getStartDateTime();

    ActigraphyAggregatesImpl getWorkoutAggregates();
}
