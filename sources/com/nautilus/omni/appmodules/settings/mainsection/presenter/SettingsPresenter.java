package com.nautilus.omni.appmodules.settings.mainsection.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import com.nautilus.omni.R;
import com.nautilus.omni.app.BasePresenter;
import com.nautilus.omni.app.OmniApplication;
import com.nautilus.omni.app.Preferences;
import com.nautilus.omni.appmodules.settings.googlefit.googlefitcommunication.GoogleFitHelperContract;
import com.nautilus.omni.appmodules.settings.mainsection.interactor.SettingsInteractorContract;
import com.nautilus.omni.appmodules.settings.mainsection.view.SettingsViewContract;
import com.nautilus.omni.appmodules.settings.underarmour.util.UnderArmourConstants;
import com.nautilus.omni.model.dto.Product;
import com.nautilus.omni.model.dto.SyncStatus;
import com.nautilus.omni.util.Constants;
import com.nautilus.omni.util.SendEmailHelper;
import com.nautilus.underarmourconnection.services.authentication.AuthenticationService;
import javax.inject.Inject;

public class SettingsPresenter extends BasePresenter implements SettingsPresenterContract, GoogleFitHelperContract.OnUserFitnessServiceConnect {
    public static final int BLUETOOTH_DELAY = 1000;
    private static final int MILES = 0;
    public static final int SYNC_ICON_DELAY = 2000;
    private final Handler mHandler = new Handler();
    SettingsInteractorContract mSettingsInteractor;
    AuthenticationService mUnderArmourAuthenticationService;
    SettingsViewContract mView;

    @Inject
    public SettingsPresenter(Context context, SettingsViewContract iSettingsActivity, SettingsInteractorContract pSettingsInteractor) {
        super(context);
        this.mView = iSettingsActivity;
        this.mSettingsInteractor = pSettingsInteractor;
    }

    public void updateUnits(int positionSelected) {
        if (positionSelected == 0) {
            this.mUnit = 0;
        } else {
            this.mUnit = 1;
        }
        SharedPreferences.Editor editor = this.mPreferences.edit();
        editor.putInt(Preferences.UNITS_SETTINGS, this.mUnit);
        editor.apply();
    }

    public void loadAppVersion() {
        this.mView.setAppVersion(((OmniApplication) this.mContext).getAppVersion());
    }

    public void loadUnitsForSpinner() {
        this.mView.initUnitsSpinner(this.mUnit);
    }

    public void hasNautilusDeviceSet() {
        boolean hasMaxTrainerDeviceSet = this.mPreferences.getBoolean(Preferences.HAS_OMNI_TRAINER_DEVICE_SET, false);
        boolean syncInProgress = this.mPreferences.getBoolean(Preferences.SYNC_IN_PROGRESS, false);
        this.mView.displayMenuItemSync(hasMaxTrainerDeviceSet);
        this.mView.enableMenuItemSync(syncInProgress);
    }

    public void sendEmail(Context activity) {
        new SendEmailHelper(activity, this.mSettingsInteractor.getProduct(), this.mSettingsInteractor.getCurrentUser(this.mPreferences.getInt(Preferences.USER_INDEX, -1))).sendEmail();
    }

    public void getToastMessageIfFromWizard() {
        if (this.mPreferences.getBoolean("SYNC_FROM_CONNECTION_WIZARD", false)) {
            this.mView.displayToastIfFromWizardConnection();
        }
    }

    public void getSuccessSyncMessage() {
        Product product = this.mSettingsInteractor.getProduct();
        boolean syncFromWizard = this.mPreferences.getBoolean("SYNC_FROM_CONNECTION_WIZARD", false);
        if (product.getmLastSyncStatus().equals(SyncStatus.SUCCESSFUL_NO_WORKOUTS)) {
            this.mView.showWorkoutSyncSuccessfulToast(this.mContext.getString(R.string.connection_no_new_workouts_found));
        } else if (product.getmLastSyncStatus().equals(SyncStatus.SUCCESSFUL) && !syncFromWizard) {
            this.mView.showWorkoutSyncSuccessfulToast(this.mContext.getString(R.string.connection_workouts_synchronized));
        }
    }

    public void updatePreferenceSyncToastShowed() {
        this.mPreferences.edit().putBoolean(Preferences.SYNC_TOASTS_SHOWED, true).apply();
    }

    public void updatePreferenceNavigationFromSettings(boolean value) {
        this.mPreferences.edit().putBoolean("NAVIGATION_FROM_SETTINGS", value).apply();
    }

    public void checkActivityCalled() {
        boolean fromHelp = this.mPreferences.getBoolean(Preferences.SHOW_DEVICE_SCREEN_FROM_HELP, false);
        boolean fromHome = this.mPreferences.getBoolean(Preferences.SHOW_HOME_SCREEN_FROM_HELP, false);
        if (fromHelp) {
            goBackToDeviceScreen();
        } else if (fromHome) {
            goBackToHomeScreen();
        } else {
            this.mView.executeNormalSettingsResume();
        }
    }

    public void updatePreferenceShowDeviceView(boolean value) {
        this.mPreferences.edit().putBoolean(Preferences.SHOW_DEVICE_VIEW, value).apply();
    }

    public void updatePreferenceShowHomeScreen(boolean value) {
        this.mPreferences.edit().putBoolean(Preferences.SHOW_HOME_SCREEN, value).apply();
    }

    private void goBackToDeviceScreen() {
        this.mPreferences.edit().putBoolean(Preferences.SHOW_DEVICE_SCREEN_FROM_HELP, false).apply();
        this.mView.showDeviceScreen();
    }

    private void goBackToHomeScreen() {
        this.mPreferences.edit().putBoolean(Preferences.SHOW_HOME_SCREEN_FROM_HELP, false).apply();
        this.mView.showHomeScreen();
    }

    public void updateBluetoothIcon(boolean isSlowIconTransitionUpdate) {
        this.mView.refreshBluetoothMenuItem();
        Runnable changeBluetoothToActive = new Runnable() {
            public void run() {
                if (SettingsPresenter.this.mPreferences.getBoolean(Preferences.SYNC_IN_PROGRESS, false)) {
                    SettingsPresenter.this.mView.changeItemBluetoothIcon(R.drawable.ic_bluetooth_active);
                } else if (SettingsPresenter.this.mPreferences.getBoolean(Preferences.IS_SYNC_IN_ERROR_STATE, false)) {
                    SettingsPresenter.this.mView.changeItemBluetoothIcon(R.drawable.ic_bluetooth_sync_issue);
                } else {
                    SettingsPresenter.this.mView.changeItemBluetoothIcon(R.drawable.ic_bluetooth_inactive);
                }
            }
        };
        Runnable changeBluetoothToSyncing = new Runnable() {
            public void run() {
                if (SettingsPresenter.this.mPreferences.getBoolean(Preferences.SYNC_IN_PROGRESS, false)) {
                    SettingsPresenter.this.mView.bluetoothItemChangeSyncState();
                } else {
                    SettingsPresenter.this.mView.setBluetoothItemNullAction();
                }
            }
        };
        if (isSlowIconTransitionUpdate) {
            this.mHandler.postDelayed(changeBluetoothToActive, 1000);
            this.mHandler.postDelayed(changeBluetoothToSyncing, 2000);
            return;
        }
        this.mHandler.postDelayed(changeBluetoothToActive, 0);
        this.mHandler.postDelayed(changeBluetoothToSyncing, 0);
    }

    public void checkGoogleFitStatus() {
        this.mGoogleFitHelper.setOnUserFitnessServiceConnect(this);
        if (!this.mGoogleFitHelper.isConnected()) {
            attemptConnectionWithGoogleFit();
        } else {
            this.mView.setGoogleFitStatus(this.mContext.getString(R.string.google_fit_connected));
        }
    }

    private void attemptConnectionWithGoogleFit() {
        this.mView.setGoogleFitStatus(Constants.EMPTY_SPACE);
        this.mGoogleFitHelper.connectWithGoogleFit((Activity) null);
    }

    public void checkMFPStatus() {
        if (!this.mPreferences.getBoolean(Preferences.IS_MY_FITNESS_PAL_CONNECTED, false)) {
            this.mView.showMFPStatus(Constants.EMPTY_SPACE);
        } else {
            this.mView.showMFPStatus(this.mContext.getString(R.string.mfp_is_connected));
        }
    }

    public void checkUnderArmourStatus() {
        this.mUnderArmourAuthenticationService = new AuthenticationService(this.mContext, UnderArmourConstants.CLIENT_KEY, UnderArmourConstants.CLIENT_SECRET);
        if (this.mUnderArmourAuthenticationService.isConnectedToUnderArmour()) {
            this.mView.showUnderArmourStatus(this.mContext.getString(R.string.under_armour_connected));
        } else {
            this.mView.showUnderArmourStatus(Constants.EMPTY_SPACE);
        }
    }

    public void checkMFPDownloadStatus() {
        this.mView.setMFPDownloadStatus(this.mPreferences.getBoolean(Preferences.MY_FITNESS_PAL_DOWNLOAD, false));
    }

    public void updateMyFitnessPalDownloadPreference(boolean value) {
        this.mPreferences.edit().putBoolean(Preferences.MY_FITNESS_PAL_DOWNLOAD, value).apply();
    }

    public void callMFPActivity() {
        if (this.mPreferences.getBoolean(Preferences.IS_MY_FITNESS_PAL_CONNECTED, false)) {
            this.mView.openMyFitnessPalDisconnectionActivity();
        } else {
            this.mView.openMyFitnessPalConnectionActivity();
        }
    }

    public void callUnderArmourActivity() {
        if (this.mUnderArmourAuthenticationService.isConnectedToUnderArmour()) {
            this.mView.openUnderArmourDisconnectionActivity();
        } else {
            this.mView.openUnderArmourConnectionActivity();
        }
    }

    public void updateIsSyncingErrorStatePreference(boolean value) {
        this.mPreferences.edit().putBoolean(Preferences.IS_SYNC_IN_ERROR_STATE, value).apply();
    }

    public void showSyncErrorToast() {
        if (!this.mPreferences.getBoolean(Preferences.SYNC_TOASTS_SHOWED, false)) {
            this.mPreferences.edit().putBoolean(Preferences.SYNC_TOASTS_SHOWED, true).apply();
            this.mView.showSyncErrorToast();
        }
    }

    public void showSuccessfulSyncToast() {
        if (!this.mPreferences.getBoolean(Preferences.SYNC_TOASTS_SHOWED, true)) {
            this.mView.showSuccessfulSyncToast();
        }
    }

    public void callGoogleFitActivity() {
        if (this.mPreferences == null || !this.mGoogleFitHelper.isConnected()) {
            this.mView.startConnectionGoogleFitActivity();
        } else {
            this.mView.startConfigGoogleFitActivity();
        }
    }

    public void updateBluetoothIconToErrorState() {
        this.mHandler.postDelayed(new Runnable() {
            public void run() {
                SettingsPresenter.this.mView.updateBluetoothIconToErrorState();
            }
        }, 1000);
    }

    public void OnConnectionSuccessListener() {
        this.mView.setGoogleFitStatus(this.mContext.getString(R.string.google_fit_connected));
    }

    public void OnConnectionFailedListener(int errorCode, int requestCode) {
        this.mView.setGoogleFitStatus(Constants.EMPTY_SPACE);
    }

    public void OnDisconnectedSuccess() {
    }

    public void OnDisconnectionError() {
    }
}
