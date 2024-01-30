package com.nautilus.omni.appmodules.settings.myfitnesspal.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import com.nautilus.omni.app.BasePresenter;
import com.nautilus.omni.app.Preferences;
import com.nautilus.omni.appmodules.settings.myfitnesspal.view.MyFitnessPalConnectionViewContract;
import com.nautilus.omni.networking.myfitnesspalcommunication.MyFitnessPalConstants;
import javax.inject.Inject;

public class MyFitnessPalPresenter extends BasePresenter implements MyFitnessPalPresenterContract {
    MyFitnessPalConnectionViewContract iMyFitnessPalConnectionActivity;

    @Inject
    public MyFitnessPalPresenter(Context context) {
        super(context);
    }

    public void setiMyFitnessPalConnectionActivity(MyFitnessPalConnectionViewContract iMyFitnessPalConnectionActivity2) {
        this.iMyFitnessPalConnectionActivity = iMyFitnessPalConnectionActivity2;
    }

    public void startSync() {
        if (this.mPreferences.getBoolean(Preferences.IS_MY_FITNESS_PAL_CONNECTED, false) && !this.mPreferences.getBoolean(Preferences.IS_MFP_SYNC_IN_PROGRESS, false)) {
            this.mPreferences.edit().putBoolean(Preferences.IS_MFP_SYNC_IN_PROGRESS, true).apply();
            this.iMyFitnessPalConnectionActivity.startSync();
        }
    }

    public void saveMyFitnessPalCredentials(String accessToken, String refreshToken, String clientId) {
        SharedPreferences.Editor edit = this.mPreferences.edit();
        edit.putBoolean(Preferences.IS_MY_FITNESS_PAL_CONNECTED, true);
        edit.putString(Preferences.MY_FITNESS_PAL_TOKEN, accessToken);
        edit.putString(Preferences.MY_FITNESS_PAL_REFRESH_TOKEN, refreshToken);
        edit.putString(Preferences.MY_FITNESS_USER_ID, clientId);
        edit.apply();
    }

    public void updateMyFitnessPalDownloadPreference(boolean value) {
        this.mPreferences.edit().putBoolean(Preferences.MY_FITNESS_PAL_DOWNLOAD, value).apply();
    }

    public void checkMFPStatus() {
        if (!this.mPreferences.getBoolean(Preferences.MY_FITNESS_PAL_DOWNLOAD, false)) {
            return;
        }
        if (isAppInstalled(MyFitnessPalConstants.MFP_PACKAGE)) {
            this.iMyFitnessPalConnectionActivity.showDialogRemember();
        } else {
            updateMyFitnessPalDownloadPreference(false);
        }
    }

    private boolean isAppInstalled(String packageName) {
        try {
            this.mContext.getPackageManager().getInstallerPackageName(packageName);
            return Boolean.TRUE.booleanValue();
        } catch (Exception e) {
            return Boolean.FALSE.booleanValue();
        }
    }

    public void disconnectMyFitnessPalApp() {
        SharedPreferences.Editor edit = this.mPreferences.edit();
        edit.putBoolean(Preferences.IS_MY_FITNESS_PAL_CONNECTED, false);
        edit.putBoolean(Preferences.IS_MFP_SYNC_IN_PROGRESS, false);
        edit.apply();
    }
}
