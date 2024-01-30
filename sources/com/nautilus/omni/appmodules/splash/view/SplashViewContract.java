package com.nautilus.omni.appmodules.splash.view;

public interface SplashViewContract {
    void showUpdatingDatabaseToast(String str);

    void startBLECallbacksHandlerService();

    void startConnectionWizard();

    void startHomeActivity();

    void startTutorialActivity();
}
