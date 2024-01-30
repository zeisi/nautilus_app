package com.nautilus.underarmourconnection.services.workouts;

import com.nautilus.underarmourconnection.database.WorkoutTrack;
import org.json.JSONObject;

public interface WorkoutServiceInterface {
    void addWorkoutToUnderArmour(JSONObject jSONObject, WorkoutCallback workoutCallback);

    WorkoutTrack getLastSyncedWorkoutRecord(String str, int i);
}
