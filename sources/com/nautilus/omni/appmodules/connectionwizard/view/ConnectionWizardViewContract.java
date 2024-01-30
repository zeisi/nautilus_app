package com.nautilus.omni.appmodules.connectionwizard.view;

import com.nautilus.omni.model.dto.OmniData;
import java.util.ArrayList;

public interface ConnectionWizardViewContract {
    void callOmniTrainerListActivity(ArrayList<OmniData> arrayList);

    void enableTxtViewSkip();

    void goToMainWithSkipAction();

    void goToSelectUserNumberActivity();

    void sendConnectToOmniTrainerBroadcast(OmniData omniData);

    void showConnectButton();

    void showConnectingDeviceToast();

    void showConnectingDeviceToastOutAnimation();

    void showErrorConnectingDialog();

    void showNoOmniTrainerFoundState();

    void showOmniTrainerBusyDialog();

    void showSyncFailedDataToast();

    void stopScanServices();
}
