package com.nautilus.underarmourconnection.services.workouts;

import com.nautilus.underarmourconnection.errorhandling.UnderArmourError;

public interface WorkoutCallback {
    void onWorkoutSaveError(UnderArmourError underArmourError);

    void onWorkoutSaveSuccess(String str);
}
