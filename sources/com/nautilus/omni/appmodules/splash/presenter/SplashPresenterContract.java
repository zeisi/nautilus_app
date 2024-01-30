package com.nautilus.omni.appmodules.splash.presenter;

public interface SplashPresenterContract {
    void checkFirstPreferences();

    void createAwardTypesInDatabase();

    void showUpdatingDatabaseToast();

    void startNextActivity();

    void updateAwardTypes();

    void updateTimeGoalValues();
}
