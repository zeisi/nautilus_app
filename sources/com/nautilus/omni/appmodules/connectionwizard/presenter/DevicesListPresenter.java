package com.nautilus.omni.appmodules.connectionwizard.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;
import com.nautilus.omni.app.BasePresenter;
import com.nautilus.omni.app.Preferences;
import com.nautilus.omni.appmodules.connectionwizard.adapters.OmniTrainerListAdapter;
import com.nautilus.omni.appmodules.connectionwizard.domain.interactors.ConnectionWizardInteractorContract;
import com.nautilus.omni.appmodules.connectionwizard.domain.receivers.IOmniDevicesListReceivers;
import com.nautilus.omni.appmodules.connectionwizard.domain.receivers.OmniDevicesListReceivers;
import com.nautilus.omni.appmodules.connectionwizard.view.OmniTrainerListActivityContract;
import com.nautilus.omni.model.dto.OmniData;
import java.util.ArrayList;
import java.util.Iterator;
import org.joda.time.DateTime;

public class DevicesListPresenter extends BasePresenter implements DevicesListPresenterContract, IOmniDevicesListReceivers, ConnectionWizardInteractorContract.OnConnectionWizardInteractorResponse {
    public static final int ANIMATION_DURATION = 2000;
    private static final int CONNECTION_RESULT_DELAY = 2000;
    private static final int MACHINES_TIME_STATUS_DELAY = 5000;
    public static final String TAG = DevicesListPresenter.class.getSimpleName();
    private static final int TRAINER_DISCONNECTED_DELAY = 2000;
    private static final int UPDATE_DELAY = 2000;
    private OmniTrainerListAdapter.AdapterView adapterView;
    private ConnectionWizardInteractorContract connectionWizardInteractor;
    /* access modifiers changed from: private */
    public Handler handler = new Handler();
    private DateTime lastUpdateDateTime;
    /* access modifiers changed from: private */
    public boolean mCheckOmniTrainer;
    /* access modifiers changed from: private */
    public boolean mConnectionSuccess;
    /* access modifiers changed from: private */
    public boolean mErrorDetected;
    /* access modifiers changed from: private */
    public boolean mMakeUpdate;
    private ArrayList<OmniData> mOmniDataList;
    Runnable mStatusChecker = new Runnable() {
        public void run() {
            try {
                DevicesListPresenter.this.checkLastTimeUpdated();
            } finally {
                DevicesListPresenter.this.handler.postDelayed(DevicesListPresenter.this.mStatusChecker, (long) 5000);
            }
        }
    };
    private OmniDevicesListReceivers omniDevicesListReceivers;
    /* access modifiers changed from: private */
    public OmniTrainerListActivityContract omniTrainerListActivity;
    private boolean onItemClick;

    public void setmOmniDataList(ArrayList<OmniData> mOmniDataList2) {
        this.mOmniDataList = mOmniDataList2;
    }

    public void listEmpty() {
        this.omniTrainerListActivity.restartConnectionWizard(true);
    }

    public DevicesListPresenter(Context context, ConnectionWizardInteractorContract pConnectionWizardInteractor, OmniTrainerListActivityContract pOmniTrainerListActivity) {
        super(context);
        this.omniDevicesListReceivers = new OmniDevicesListReceivers(context, this);
        pConnectionWizardInteractor.setResponseInterface(this);
        this.mOmniDataList = new ArrayList<>();
        this.connectionWizardInteractor = pConnectionWizardInteractor;
        this.omniTrainerListActivity = pOmniTrainerListActivity;
        this.mMakeUpdate = true;
        this.mErrorDetected = false;
        this.mCheckOmniTrainer = true;
        this.mConnectionSuccess = false;
        this.onItemClick = false;
    }

    /* access modifiers changed from: package-private */
    public void startCheckingToMakeUpdatesDevicesList() {
        this.mStatusChecker.run();
    }

    /* access modifiers changed from: package-private */
    public void stopCheckingToMakeUpdatesDevicesList() {
        this.handler.removeCallbacks(this.mStatusChecker);
    }

    public void updateSyncInProgressPreference(boolean value) {
        this.mPreferences.edit().putBoolean(Preferences.SYNC_IN_PROGRESS, value).apply();
    }

    public void setAdapterView(OmniTrainerListAdapter.AdapterView adapterView2) {
        this.adapterView = adapterView2;
    }

    public void pause() {
        this.onItemClick = true;
        this.omniDevicesListReceivers.unregisterBroadcastReceivers();
        stopCheckingToMakeUpdatesDevicesList();
    }

    public void resume() {
        this.omniDevicesListReceivers.registerBroadcastReceivers();
        startCheckingToMakeUpdatesDevicesList();
        this.onItemClick = false;
    }

    public void resetCurrentUserPreference() {
        SharedPreferences.Editor editor = this.mPreferences.edit();
        editor.putInt(Preferences.USER_INDEX, -1);
        editor.apply();
    }

    public void updateDefaultMaxTrainer(OmniData newDefaultOmniData) {
        this.onItemClick = true;
        OmniData currentDefaultOmniData = this.connectionWizardInteractor.getFirstIOmniData();
        if (currentDefaultOmniData != null) {
            updateCurrentDefaultMaxTrainer(currentDefaultOmniData, newDefaultOmniData);
        } else {
            this.connectionWizardInteractor.createOmniTrainerOnListAppearNew(newDefaultOmniData);
        }
    }

    private void updateCurrentDefaultMaxTrainer(OmniData currentDefaultOmniData, OmniData newDefaultOmniData) {
        currentDefaultOmniData.setmOmniBluetoothDevice(newDefaultOmniData.getmOmniTrainerBluetoothDevice());
        currentDefaultOmniData.setIsBusy(newDefaultOmniData.ismIsBusy());
        currentDefaultOmniData.setmSignalStrength(newDefaultOmniData.getmSignalStrength());
        currentDefaultOmniData.setmName(newDefaultOmniData.getmName());
        currentDefaultOmniData.setmUniqueID(newDefaultOmniData.getmUniqueID());
        currentDefaultOmniData.setmUserNumber(newDefaultOmniData.getmUserNumber());
        currentDefaultOmniData.setmOmniConsoleType(newDefaultOmniData.getmOmniConsoleType());
        currentDefaultOmniData.setmOmniMachineType(newDefaultOmniData.getmOmniMachineType());
        this.connectionWizardInteractor.updateOmniData(currentDefaultOmniData);
        this.adapterView.onVerifieDefaultOmniDataSuccess(currentDefaultOmniData);
    }

    public void onAddOmniTrainerToListReceiverResponse(OmniData omniData) {
        if (!this.mOmniDataList.contains(omniData)) {
            this.mOmniDataList.add(omniData);
        }
    }

    public void onOmniTrainerUpdatesReceiverResponse(OmniData omniData) {
        this.lastUpdateDateTime = new DateTime();
        makeUpdatesInTemporalOmniTrainerList(omniData);
        if (this.mMakeUpdate && !this.onItemClick) {
            updateRealOmniTrainerList();
        }
        if (this.mCheckOmniTrainer && !this.onItemClick) {
            checkIfOmniTrainerDisconnected();
        }
    }

    public void onSuccessfulConnectionReceiverResponse() {
        this.mConnectionSuccess = true;
        this.handler.postDelayed(new Runnable() {
            public void run() {
                DevicesListPresenter.this.hideConnectingDeviceToast();
            }
        }, 2000);
    }

    public void onConnectionErrorReceiverResponse() {
        this.mErrorDetected = true;
        if (!this.mConnectionSuccess) {
            this.omniTrainerListActivity.showUnableToConnectDialog();
            this.mErrorDetected = false;
            this.mConnectionSuccess = false;
        }
    }

    public void onUnexpectedDisconnectionProcessReceiverResponse() {
        this.mErrorDetected = true;
        if (!this.mConnectionSuccess) {
            this.omniTrainerListActivity.showUnableToConnectDialog();
            this.mErrorDetected = false;
            this.mConnectionSuccess = false;
        }
        Log.d(TAG, "DEBUG - CONNECTION WIZARD - UNEXPECTED DISCONNECTION FROM OMNI TRAINER...");
    }

    private void updateRealOmniTrainerList() {
        Log.d(TAG, "DEBUG - MAKING UPDATE ON LIST, TIME: " + new DateTime());
        Iterator<OmniData> it = this.mOmniDataList.iterator();
        while (it.hasNext()) {
            this.omniTrainerListActivity.updateRecycleViewItems(it.next());
        }
        this.mMakeUpdate = false;
        this.handler.postDelayed(new Runnable() {
            public void run() {
                boolean unused = DevicesListPresenter.this.mMakeUpdate = true;
            }
        }, 2000);
    }

    private void makeUpdatesInTemporalOmniTrainerList(OmniData newOmniData) {
        Iterator<OmniData> it = this.mOmniDataList.iterator();
        while (it.hasNext()) {
            OmniData currentOmniData = it.next();
            if (currentOmniData.getmUniqueID().equals(newOmniData.getmUniqueID())) {
                currentOmniData.setmSignalStrength(newOmniData.getmSignalStrength());
                currentOmniData.setIsBusy(newOmniData.ismIsBusy());
                currentOmniData.setmUpdateTime(new DateTime());
                currentOmniData.setmIsEnabled(true);
            }
        }
    }

    private void checkIfOmniTrainerDisconnected() {
        checkOmniDevicesTime();
        this.mCheckOmniTrainer = false;
        this.handler.postDelayed(new Runnable() {
            public void run() {
                boolean unused = DevicesListPresenter.this.mCheckOmniTrainer = true;
            }
        }, 2000);
    }

    /* access modifiers changed from: private */
    public void checkLastTimeUpdated() {
        if (this.lastUpdateDateTime != null && !this.onItemClick && ((double) (new DateTime().getSecondOfDay() - this.lastUpdateDateTime.getSecondOfDay())) > 5.0d) {
            checkOmniDevicesTime();
            updateRealOmniTrainerList();
        }
    }

    private void checkOmniDevicesTime() {
        Iterator<OmniData> it = this.mOmniDataList.iterator();
        while (it.hasNext()) {
            OmniData currentOmniData = it.next();
            if (new DateTime().getSecondOfDay() - currentOmniData.getmUpdateTime().getSecondOfDay() > 1) {
                currentOmniData.setmIsEnabled(false);
            }
        }
    }

    /* access modifiers changed from: private */
    public void hideConnectingDeviceToast() {
        Runnable startSelectUserActivityRunnable = new Runnable() {
            public void run() {
                DevicesListPresenter.this.omniTrainerListActivity.startSelectUserNumberActivity();
            }
        };
        Runnable showErrorRunable = new Runnable() {
            public void run() {
                OmniTrainerListAdapter.setIsOmniTrainerSelected(false);
                DevicesListPresenter.this.omniTrainerListActivity.showUnableToConnectDialog();
                boolean unused = DevicesListPresenter.this.mErrorDetected = false;
                boolean unused2 = DevicesListPresenter.this.mConnectionSuccess = false;
            }
        };
        this.omniTrainerListActivity.showConnectingDeviceToastOutAnimation();
        if (this.mErrorDetected) {
            this.omniTrainerListActivity.changeTxtViewSkipEnableDisable(true);
            this.handler.postDelayed(showErrorRunable, 2000);
            return;
        }
        this.handler.postDelayed(startSelectUserActivityRunnable, 2000);
    }

    public void onCreateSuccess(OmniData omniData) {
        this.adapterView.onVerifieDefaultOmniDataSuccess(omniData);
    }
}
