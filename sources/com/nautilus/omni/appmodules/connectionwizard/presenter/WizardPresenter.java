package com.nautilus.omni.appmodules.connectionwizard.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;
import com.nautilus.omni.app.BasePresenter;
import com.nautilus.omni.app.Preferences;
import com.nautilus.omni.appmodules.connectionwizard.domain.interactors.ConnectionWizardInteractorContract;
import com.nautilus.omni.appmodules.connectionwizard.domain.receivers.IWizardReceiversResponse;
import com.nautilus.omni.appmodules.connectionwizard.domain.receivers.WizardReceivers;
import com.nautilus.omni.appmodules.connectionwizard.view.ConnectionWizardActivity;
import com.nautilus.omni.appmodules.connectionwizard.view.ConnectionWizardViewContract;
import com.nautilus.omni.model.dto.OmniData;
import java.util.ArrayList;
import java.util.Iterator;
import javax.inject.Inject;

public class WizardPresenter extends BasePresenter implements WizardPresenterContract, IWizardReceiversResponse {
    private static final int ANIMATION_DURATION = 2000;
    public static final String TAG = WizardPresenter.class.getSimpleName();
    /* access modifiers changed from: private */
    public ConnectionWizardViewContract connectionWizardActivity;
    private ConnectionWizardInteractorContract connectionWizardInteractor;
    private boolean mConnectionSuccess;
    /* access modifiers changed from: private */
    public Handler mHandler = new Handler();
    private boolean mIsScanningForMachines;
    private ArrayList<OmniData> mOmniDataArray;
    private boolean mOmniTrainerErrorHappened;
    private boolean mUnexpectedDisconnectionHappened;
    private final WizardReceivers wizardReceivers;

    @Inject
    public WizardPresenter(Context context, ConnectionWizardActivity connectionWizardActivity2, ConnectionWizardInteractorContract connectionWizardInteractor2, WizardReceivers pWizardReceivers) {
        super(context);
        this.connectionWizardActivity = connectionWizardActivity2;
        this.connectionWizardInteractor = connectionWizardInteractor2;
        this.mOmniDataArray = new ArrayList<>();
        this.mIsScanningForMachines = false;
        this.wizardReceivers = pWizardReceivers;
        this.wizardReceivers.setWizardReceiversResponse(this);
    }

    public void updateSettingFirstTimeAppExecutes() {
        SharedPreferences.Editor editor = this.mPreferences.edit();
        editor.putBoolean(Preferences.FIRST_RUN, false);
        editor.apply();
    }

    public void resume() {
        this.mOmniDataArray.clear();
        initConnectionFlags();
        this.wizardReceivers.registerBroadcastReceivers();
    }

    public void pause() {
        this.wizardReceivers.unregisterBroadcastReceivers();
    }

    public void destroy() {
    }

    public void saveOmniTrainerInDataBase(OmniData omniData) {
        this.connectionWizardInteractor.saveOmniTrainerInDataBase(omniData);
    }

    public void onAddOmniDeviceResponse(OmniData omniData) {
        addOmniData(omniData);
    }

    public void onUpdateReceiverResponse(OmniData omniData) {
        updateOmniTrainerList(omniData);
    }

    public void onSuccessfulConnectionReceiverResponse() {
        this.mConnectionSuccess = true;
        showConnectionResult();
        Log.d(TAG, "DEBUG - CONNECTION WIZARD - SUCCESSFUL CONNECTION...");
    }

    public void onShowErrorConnectingDialogReceiverResponse() {
        this.mOmniDataArray = new ArrayList<>();
        this.mOmniTrainerErrorHappened = true;
        if (!this.mConnectionSuccess) {
            showConnectionResult();
        }
    }

    public void onUnexpectedDisconnProcessReceiverResponse() {
        this.mUnexpectedDisconnectionHappened = true;
        if (!this.mConnectionSuccess) {
            showConnectionResult();
        }
        Log.d(TAG, "DEBUG - CONNECTION WIZARD - UNEXPECTED DISCONNECTION FROM OMNI TRAINER...");
    }

    private void addOmniData(OmniData omniData) {
        if (!this.mOmniDataArray.contains(omniData)) {
            this.mOmniDataArray.add(omniData);
        }
    }

    private void updateOmniTrainerList(OmniData newOmniData) {
        Iterator<OmniData> it = this.mOmniDataArray.iterator();
        while (it.hasNext()) {
            OmniData omniData = it.next();
            if (omniData.equals(newOmniData)) {
                omniData.setIsBusy(newOmniData.ismIsBusy());
            }
        }
    }

    private void showConnectionResult() {
        final Runnable executeConnectionResultActionRunnable = new Runnable() {
            public void run() {
                WizardPresenter.this.executeConnectionActionAccordingWithResult();
            }
        };
        this.mHandler.postDelayed(new Runnable() {
            public void run() {
                WizardPresenter.this.connectionWizardActivity.showConnectingDeviceToastOutAnimation();
                WizardPresenter.this.mHandler.postDelayed(executeConnectionResultActionRunnable, 2000);
            }
        }, 2000);
    }

    /* access modifiers changed from: private */
    public void executeConnectionActionAccordingWithResult() {
        if (this.mUnexpectedDisconnectionHappened) {
            showUnexpectedDisconnectionState();
        } else if (this.mOmniTrainerErrorHappened) {
            this.connectionWizardActivity.showErrorConnectingDialog();
        } else {
            startSelectUserNumberActivity();
        }
        initConnectionFlags();
    }

    private void initConnectionFlags() {
        this.mOmniTrainerErrorHappened = false;
        this.mConnectionSuccess = false;
        this.mUnexpectedDisconnectionHappened = false;
    }

    private void showUnexpectedDisconnectionState() {
        this.mOmniDataArray = new ArrayList<>();
        this.connectionWizardActivity.showSyncFailedDataToast();
        this.connectionWizardActivity.showConnectButton();
        this.connectionWizardActivity.enableTxtViewSkip();
        this.mUnexpectedDisconnectionHappened = false;
    }

    private void startSelectUserNumberActivity() {
        if (this.mOmniDataArray.size() > 0) {
            this.connectionWizardActivity.goToSelectUserNumberActivity();
            return;
        }
        this.mOmniDataArray = new ArrayList<>();
        this.connectionWizardActivity.showErrorConnectingDialog();
    }

    public void validateScanningProcess() {
        if (omniTrainersFound()) {
            checkOmniTrainersAvailable();
        } else {
            this.connectionWizardActivity.showNoOmniTrainerFoundState();
        }
    }

    public void executeSkipAction() {
        if (this.mIsScanningForMachines) {
            this.connectionWizardActivity.stopScanServices();
        }
        this.mIsScanningForMachines = false;
        resetCurrentUserPreference();
        this.mPreferences.edit().putBoolean(Preferences.SYNC_IN_PROGRESS, false).apply();
        this.connectionWizardActivity.goToMainWithSkipAction();
    }

    public void startScan() {
        this.mIsScanningForMachines = true;
    }

    private void resetCurrentUserPreference() {
        SharedPreferences.Editor editor = this.mPreferences.edit();
        editor.putInt(Preferences.USER_INDEX, -1);
        editor.apply();
    }

    private boolean omniTrainersFound() {
        return this.mOmniDataArray.size() > 0;
    }

    private void checkOmniTrainersAvailable() {
        if (this.mOmniDataArray.size() == 1) {
            connectToOmniTrainerAvailable(this.mOmniDataArray.get(0));
        } else {
            this.connectionWizardActivity.callOmniTrainerListActivity(this.mOmniDataArray);
        }
    }

    private void connectToOmniTrainerAvailable(OmniData omniData) {
        if (!omniData.ismIsBusy()) {
            this.connectionWizardActivity.showConnectingDeviceToast();
            saveOmniTrainerInDataBase(omniData);
            this.connectionWizardActivity.sendConnectToOmniTrainerBroadcast(omniData);
            return;
        }
        this.mOmniDataArray = new ArrayList<>();
        this.connectionWizardActivity.showOmniTrainerBusyDialog();
    }
}
