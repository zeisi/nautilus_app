package com.nautilus.omni.appmodules.home.view;

public interface GaugeFragmentContract {
    void compareTimeToPreviousWorkout(int i, int i2, int i3);

    void hideImageViewNeedle();

    void loadTxtEnableGoalButNoData(int i, int i2);

    void showAvgCalorieBurnRateComparedToPreviousWorkout(int i, String str, int i2);

    void showAvgCaloriesBurnedWithoutComparison(String str);

    void showCaloriesComparedToPreviousWorkout(int i, String str, int i2);

    void showCaloriesComparedWithCurrentGoal(int i, String str, String str2, int i2);

    void showCaloriesWithoutComparison(int i);

    void showDistanceComparedToPreviousWorkout(int i, int i2, String str);

    void showDistanceLabelBasedOnCurrentUnit(int i);

    void showDistanceWithoutComparison(String str);

    void showEmptyData();

    void showHeartRateWithoutComparison(String str);

    void showImageViewNeedle();

    void showSpeedComparedToPreviousWorkout(int i, int i2, String str);

    void showSpeedLabelBasedOnCurrentUnit(int i);

    void showSpeedWithoutComparison(String str);

    void showTimeComparedWithCurrentGoal(int i, int i2, String str, int i3);

    void showTimeWithoutComparison(int i);

    void showWattsComparedToPreviousWorkout(int i, String str, int i2);

    void showWattsWithoutComparison(String str);
}
