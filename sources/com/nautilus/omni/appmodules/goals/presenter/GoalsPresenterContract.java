package com.nautilus.omni.appmodules.goals.presenter;

public interface GoalsPresenterContract {
    void disableGoals();

    void loadCurrentGoals();

    void loadGoalPickerValues();

    void onDestroy();

    void saveGoals(String str, String str2, String str3);
}
