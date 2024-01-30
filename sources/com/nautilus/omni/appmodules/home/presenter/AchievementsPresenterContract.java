package com.nautilus.omni.appmodules.home.presenter;

import com.nautilus.omni.appmodules.home.view.AchievementFragmentContract;

public interface AchievementsPresenterContract {
    void getAllAwardsAndAchievements();

    String getCaloriesBurnedPerWorkout();

    String getHighestAvgSpeedPerWeek(int i);

    String getHighestAvgSpeedPerWorkout(int i);

    String getHighestBurnRatePerWorkout();

    String getLongestWorkout();

    String getMostCaloriesBurnedPerWeek();

    String getMostDistancePerWeek(int i);

    String getMostDistancePerWorkout(int i);

    String getMostWorkoutTimePerWeek();

    String getMostWorkoutsPerWeek();

    int refreshUnit();

    void setiAchievementFragment(AchievementFragmentContract achievementFragmentContract);
}
