package com.nautilus.omni.appmodules.connectionwizard.view;

import com.nautilus.omni.model.dto.OmniData;

public interface OmniTrainerListActivityContract {
    void changeTxtViewSkipEnableDisable(boolean z);

    void restartConnectionWizard(boolean z);

    void showConnectingDeviceToastOutAnimation();

    void showUnableToConnectDialog();

    void startSelectUserNumberActivity();

    void updateRecycleViewItems(OmniData omniData);
}
