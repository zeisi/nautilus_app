package com.nautilus.omni.appmodules.settings.mainsection.view;

public interface SettingsViewContract {
    void bluetoothItemChangeSyncState();

    void changeItemBluetoothIcon(int i);

    void displayMenuItemSync(boolean z);

    void displayToastIfFromWizardConnection();

    void enableMenuItemSync(boolean z);

    void executeNormalSettingsResume();

    void initUnitsSpinner(int i);

    void openMyFitnessPalConnectionActivity();

    void openMyFitnessPalDisconnectionActivity();

    void openUnderArmourConnectionActivity();

    void openUnderArmourDisconnectionActivity();

    void refreshBluetoothMenuItem();

    void setAppVersion(String str);

    void setBluetoothItemNullAction();

    void setGoogleFitStatus(String str);

    void setMFPDownloadStatus(boolean z);

    void showDeviceScreen();

    void showHomeScreen();

    void showMFPStatus(String str);

    void showSuccessfulSyncToast();

    void showSyncErrorToast();

    void showUnderArmourStatus(String str);

    void showWorkoutSyncSuccessfulToast(String str);

    void startConfigGoogleFitActivity();

    void startConnectionGoogleFitActivity();

    void updateBluetoothIconToErrorState();
}
