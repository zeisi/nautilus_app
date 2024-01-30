package com.nautilus.omni.appmodules.splash.presenter.interactor;

import com.nautilus.omni.appmodules.splash.presenter.interactor.SplashInteractorContract;
import com.nautilus.omni.dataaccess.awards.AwardsUpdater;
import com.nautilus.omni.dataaccess.awards.MainAwardsDaoHelper;

public class SplashInteractor implements SplashInteractorContract {
    AwardsUpdater awardsUpdater;
    MainAwardsDaoHelper mainAwardsDaoHelper;

    public SplashInteractor(MainAwardsDaoHelper mainAwardsDaoHelper2) {
        this.mainAwardsDaoHelper = mainAwardsDaoHelper2;
    }

    public void createAwardTypesInDatabase() {
        this.mainAwardsDaoHelper.createAllAwardTypes();
    }

    public void updateAwardTypes(int oldAwardTypesVersion, SplashInteractorContract.OnSplashResult onSplashResult) {
        if (1 > oldAwardTypesVersion) {
            this.awardsUpdater.executeAwardUpdates();
            onSplashResult.onUpdateAwardTypesSuccess();
        }
    }
}
