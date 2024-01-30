package com.nautilus.omni.appmodules.settings.mainsection.presenter;

import android.content.Context;

public interface SettingsPresenterContract {
    void callGoogleFitActivity();

    void callMFPActivity();

    void callUnderArmourActivity();

    void checkActivityCalled();

    void checkGoogleFitStatus();

    void checkMFPDownloadStatus();

    void checkMFPStatus();

    void checkUnderArmourStatus();

    void getSuccessSyncMessage();

    void getToastMessageIfFromWizard();

    void hasNautilusDeviceSet();

    void loadAppVersion();

    void loadUnitsForSpinner();

    void sendEmail(Context context);

    void showSuccessfulSyncToast();

    void showSyncErrorToast();

    void updateBluetoothIcon(boolean z);

    void updateBluetoothIconToErrorState();

    void updateIsSyncingErrorStatePreference(boolean z);

    void updateMyFitnessPalDownloadPreference(boolean z);

    void updatePreferenceNavigationFromSettings(boolean z);

    void updatePreferenceShowDeviceView(boolean z);

    void updatePreferenceShowHomeScreen(boolean z);

    void updatePreferenceSyncToastShowed();

    void updateUnits(int i);
}
