package com.nautilus.omni.appmodules.connectionwizard.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import com.nautilus.omni.app.BasePresenter;
import com.nautilus.omni.app.Preferences;
import com.nautilus.omni.appmodules.connectionwizard.domain.interactors.ConnectionWizardInteractorContract;
import com.nautilus.omni.appmodules.connectionwizard.domain.receivers.ISelectUserReceiversResponse;
import com.nautilus.omni.appmodules.connectionwizard.domain.receivers.WizardReceivers;
import com.nautilus.omni.appmodules.connectionwizard.view.SelectUserNumberActivityContract;
import javax.inject.Inject;

public class UserSelectPresenter extends BasePresenter implements UserSelectPresenterContract, ISelectUserReceiversResponse {
    private static final int ERROR_DIALOG_DELAY = 1000;
    private static final int TOAST_DURATION = 3000;
    private final ConnectionWizardInteractorContract connectionWizardInteractor;
    /* access modifiers changed from: private */
    public boolean mErrorOrUnexpectedDisconnection = false;
    private final Handler mHandler = new Handler();
    /* access modifiers changed from: private */
    public boolean mIsErrorDialogVisible;
    /* access modifiers changed from: private */
    public final SelectUserNumberActivityContract userNumberActivity;
    WizardReceivers wizardReceivers;

    @Inject
    public UserSelectPresenter(Context context, ConnectionWizardInteractorContract connectionWizardInteractor2, SelectUserNumberActivityContract userNumberActivity2, WizardReceivers pWizardReceivers) {
        super(context);
        this.connectionWizardInteractor = connectionWizardInteractor2;
        this.userNumberActivity = userNumberActivity2;
        this.wizardReceivers = pWizardReceivers;
    }

    public void deleteOmnidata() {
        this.connectionWizardInteractor.deleteOmniData();
    }

    public void showSyncingToast() {
        this.userNumberActivity.showSyncingToast();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                if (!UserSelectPresenter.this.mErrorOrUnexpectedDisconnection) {
                    UserSelectPresenter.this.userNumberActivity.startMainActivity();
                }
            }
        }, 3000);
    }

    public void pause() {
        this.wizardReceivers.unregisterUserSelectBroadcastReceivers();
    }

    public void resume() {
        this.wizardReceivers.registerBroadcastReceivers(this);
    }

    public void showUnableToConnectDialog() {
        if (!this.mIsErrorDialogVisible) {
            this.mHandler.postDelayed(new Runnable() {
                public void run() {
                    if (!UserSelectPresenter.this.mIsErrorDialogVisible) {
                        UserSelectPresenter.this.userNumberActivity.showUnableToConnectDialog();
                        boolean unused = UserSelectPresenter.this.mIsErrorDialogVisible = true;
                    }
                }
            }, 1000);
        }
    }

    public void saveCurrentUserIndexInSharedPreferences(int mUserNumberSelected) {
        SharedPreferences.Editor editor = this.mPreferences.edit();
        editor.putInt(Preferences.USER_INDEX, mUserNumberSelected);
        editor.apply();
    }

    public void resetCurrentUserPreference() {
        SharedPreferences.Editor editor = this.mPreferences.edit();
        editor.putInt(Preferences.USER_INDEX, -1);
        editor.apply();
    }

    public void updateSyncPreference(boolean value) {
        this.mPreferences.edit().putBoolean(Preferences.SYNC_IN_PROGRESS, value).apply();
    }

    public void saveDefaultDevicePreference() {
        SharedPreferences.Editor editor = this.mPreferences.edit();
        editor.putBoolean(Preferences.HAS_OMNI_TRAINER_DEVICE_SET, true);
        editor.apply();
    }

    public void onReceiverConnectionErrorResponse() {
        this.mErrorOrUnexpectedDisconnection = true;
        showUnableToConnectDialog();
    }
}
