package com.nautilus.omni.appmodules.journal.view.day.workoutdetail;

public interface WorkoutDetailViewContract {
    void showWorkoutAvgCaloriesPerMinute(String str);

    void showWorkoutAvgWatts(String str);

    void showWorkoutDate(String str);

    void showWorkoutDistance(String str, String str2);

    void showWorkoutElapsedTime(String str);

    void showWorkoutHeartRate(String str);

    void showWorkoutSpeed(String str, String str2);

    void showWorkoutTotalCalories(String str);
}
