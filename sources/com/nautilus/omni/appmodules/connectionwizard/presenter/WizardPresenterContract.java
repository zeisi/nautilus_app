package com.nautilus.omni.appmodules.connectionwizard.presenter;

import com.nautilus.omni.model.dto.OmniData;

public interface WizardPresenterContract {
    void destroy();

    void executeSkipAction();

    void pause();

    void resume();

    void saveOmniTrainerInDataBase(OmniData omniData);

    void startScan();

    void updateSettingFirstTimeAppExecutes();

    void validateScanningProcess();
}
