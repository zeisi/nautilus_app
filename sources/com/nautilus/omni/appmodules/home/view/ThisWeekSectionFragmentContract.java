package com.nautilus.omni.appmodules.home.view;

import java.util.ArrayList;

public interface ThisWeekSectionFragmentContract {
    void disableGoalGraphic();

    void enableGoalGraphic();

    void showDataComparedToCalorieGoal(int i, int i2);

    void showDataComparedToTimeGoal(String str, String str2, int i, int i2);

    void showDataComparedToWorkoutsPerWeekGoal(int i, int i2);

    void showDataComparedWithPreviousWeek(String str, String str2, String str3);

    void showHorizontalGraphicDataWhenGoalsEnabled(int i, ArrayList<String> arrayList, ArrayList<Double> arrayList2, int i2);
}
