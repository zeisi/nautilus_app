package com.nautilus.omni.appmodules.splash.presenter.interactor;

public interface SplashInteractorContract {

    public interface OnSplashResult {
        void onUpdateAwardTypesSuccess();
    }

    void createAwardTypesInDatabase();

    void updateAwardTypes(int i, OnSplashResult onSplashResult);
}
