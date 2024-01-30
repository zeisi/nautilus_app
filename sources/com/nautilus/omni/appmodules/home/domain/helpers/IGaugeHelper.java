package com.nautilus.omni.appmodules.home.domain.helpers;

import com.nautilus.omni.model.dto.TrainingGoal;
import com.nautilus.omni.model.dto.User;
import com.nautilus.omni.model.dto.Workout;

public interface IGaugeHelper {

    public interface OnWorkoutListener {
        void onAvgCalorieBurnRateComparedToPreviousWorkoutSuccess(int i, String str, int i2);

        void onAvgCaloriesBurnedWithoutComparisonSuccess(String str);

        void onCaloriesComparedToPreviousWorkoutSuccess(int i, int i2, int i3);

        void onCaloriesComparedWithCurrentGoalSuccess(int i, int i2, String str, int i3);

        void onCaloriesWithoutComparisonSuccess(int i);

        void onCompareTimeToPreviousWorkout(int i, int i2, int i3);

        void onDistanceComparedToPreviousWorkoutSuccess(int i, int i2, String str);

        void onDistanceWithoutComparison(String str);

        void onHeartRateWithoutComparison(int i);

        void onLoadDataWhenGoalsEnable(int i, int i2);

        void onSpeedComparedToPreviousWorkoutSuccess(int i, int i2, String str);

        void onSpeedWithoutComparison(String str);

        void onTimeComparedWithCurrentGoalSuccess(int i, int i2, String str, int i3);

        void onTimeWithoutComparisonSuccess(int i);

        void onWattsComparedToPreviousWorkoutSuccess(int i, String str, int i2);

        void onWattsWithoutComparison(String str);
    }

    void compareTimeToPreviousWorkout(OnWorkoutListener onWorkoutListener, Workout workout, Workout workout2);

    void loadAvgCalorieBurnRateComparedToPreviousWorkout(OnWorkoutListener onWorkoutListener, Workout workout, Workout workout2);

    void loadAvgCaloriesBurnedWithoutComparison(OnWorkoutListener onWorkoutListener, Workout workout);

    void loadCaloriesComparedToPreviousWorkout(OnWorkoutListener onWorkoutListener, Workout workout, Workout workout2);

    void loadCaloriesComparedWithCurrentGoal(OnWorkoutListener onWorkoutListener, Workout workout, User user, TrainingGoal trainingGoal);

    void loadCaloriesWithoutComparison(OnWorkoutListener onWorkoutListener, Workout workout);

    void loadDataComparedToTimeGoal(TrainingGoal trainingGoal, TrainingGoal trainingGoal2, OnWorkoutListener onWorkoutListener);

    void loadDistanceComparedToPreviousWorkout(int i, OnWorkoutListener onWorkoutListener, Workout workout, Workout workout2);

    void loadDistanceWithoutComparison(int i, OnWorkoutListener onWorkoutListener, Workout workout);

    void loadHeartRateWithoutComparison(OnWorkoutListener onWorkoutListener, Workout workout);

    void loadSpeedComparedToPreviousWorkout(int i, OnWorkoutListener onWorkoutListener, Workout workout, Workout workout2);

    void loadSpeedWithoutComparison(int i, OnWorkoutListener onWorkoutListener, Workout workout);

    void loadTimeComparedWithCurrentGoal(OnWorkoutListener onWorkoutListener, Workout workout, User user, TrainingGoal trainingGoal);

    void loadTimeWithoutComparison(OnWorkoutListener onWorkoutListener, Workout workout);

    void loadTotalValuesPerWeekCalorieGoals(TrainingGoal trainingGoal, TrainingGoal trainingGoal2, OnWorkoutListener onWorkoutListener);

    void loadWattsComparedToPreviousWorkout(OnWorkoutListener onWorkoutListener, Workout workout, Workout workout2);

    void loadWattsWithoutComparison(OnWorkoutListener onWorkoutListener, Workout workout);
}
