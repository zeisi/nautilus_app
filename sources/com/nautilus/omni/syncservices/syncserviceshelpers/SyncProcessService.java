package com.nautilus.omni.syncservices.syncserviceshelpers;

import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import com.j256.ormlite.dao.Dao;
import com.nautilus.omni.app.OmniApplication;
import com.nautilus.omni.app.Preferences;
import com.nautilus.omni.appmodules.settings.googlefit.googlefitcommunication.GoogleFitHelper;
import com.nautilus.omni.appmodules.settings.googlefit.googlefitcommunication.GoogleFitHelperContract;
import com.nautilus.omni.appmodules.settings.underarmour.util.UnderArmourConstants;
import com.nautilus.omni.databasemanager.DataBaseHelper;
import com.nautilus.omni.dependencyinjection.components.AppComponent;
import com.nautilus.omni.model.dto.OmniData;
import com.nautilus.omni.model.dto.Product;
import com.nautilus.omni.model.dto.SyncStatus;
import com.nautilus.omni.syncservices.BLECallbacksHandlerService;
import com.nautilus.omni.syncservices.FitAppsSyncDataService;
import com.nautilus.omni.syncservices.SyncOmniDataService;
import com.nautilus.omni.syncservices.UnderArmourSyncService;
import com.nautilus.omni.util.BroadcastKeys;
import com.nautilus.omni.util.Constants;
import com.nautilus.underarmourconnection.services.authentication.AuthenticationService;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import org.joda.time.DateTime;

public class SyncProcessService extends Service implements GoogleFitHelperContract.OnUserFitnessServiceConnect {
    public static final String AUTO_SYNC = "AUTO_SYNC";
    public static final int BLE_CALLBACKS_HANDLER_INIT_DELAY = 2000;
    public static final String MANUAL_SYNC = "MANUAL_SYNC";
    public static final int SCANNING_DURATION_ON_AUTOMATIC_SYNC = 6000;
    public static final int SCANNING_DURATION_ON_MANUAL_SYNC = 8000;
    public static final String SYNC_FROM_CONNECTION_WIZARD = "SYNC_FROM_CONNECTION_WIZARD";
    public static final String SYNC_PROCESS_SERVICE = "SYNC_PROCESS_SERVICE";
    public static final String SYNC_TYPE = "SYNC_TYPE";
    public static final String TAG = SyncProcessService.class.getSimpleName();
    public BroadcastReceiver addOmniTrainerToListReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            OmniData omniData = (OmniData) intent.getExtras().getParcelable(Constants.OMNI_TRAINER);
            if (!SyncProcessService.this.mOmniDataArray.contains(omniData)) {
                SyncProcessService.this.mOmniDataArray.add(omniData);
            }
        }
    };
    public BroadcastReceiver connectionErrorReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            SyncProcessService.this.updateSyncStatusWhenFailed(false, false, false);
            Log.d(SyncProcessService.TAG, "DEBUG - ERROR WHEN TRYING TO CONNECT");
        }
    };
    public BroadcastReceiver consoleBusy = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            SyncProcessService.this.updateSyncStatusWhenFailed(true, true, false);
            Log.d(SyncProcessService.TAG, "DEBUG - CONSOLE MY16 BUSY STATE...");
        }
    };
    public BroadcastReceiver consoleNotResponsiveErrorReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            SyncProcessService.this.updateSyncStatusWhenFailed(true, false, true);
            Log.d(SyncProcessService.TAG, "DEBUG - CONSOLE MY16 NOT RESPONSIVE...");
        }
    };
    private DataBaseHelper mDataBaseHelper;
    private OmniData mDefaultOmniData;
    private GoogleFitHelper mGoogleFitHelper;
    /* access modifiers changed from: private */
    public boolean mGotMachineBle;
    private Handler mHandler;
    private boolean mIsDeviceNotFoundOrBusy;
    /* access modifiers changed from: private */
    public ArrayList<OmniData> mOmniDataArray;
    private Dao<OmniData, Integer> mOmniTrainerDao;
    private Dao<Product, Integer> mProductDao;
    private ServiceHandler mServiceHandler;
    private Looper mServiceLooper;
    private SharedPreferences mSettings;
    private String mSyncType;
    private AuthenticationService mUnderArmourAuthenticationService;
    public BroadcastReceiver successfulConnectionReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            SyncProcessService.this.startSyncDataService();
            Log.d(SyncProcessService.TAG, "DEBUG - SUCCESSFUL CONNECTION... START SYNC SERVICE");
        }
    };
    public BroadcastReceiver syncDataFailedReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            boolean unused = SyncProcessService.this.mGotMachineBle = intent.getBooleanExtra(Constants.HAVE_BLE, false);
            SyncProcessService.this.updateSyncStatusWhenFailed(false, false, false);
        }
    };
    public BroadcastReceiver syncDataFinishedReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            boolean booleanExtra = intent.getBooleanExtra(BroadcastKeys.NEW_WORKOUTS_LOADED, false);
            SyncProcessService.this.updateSyncStatusWhenFinished();
            Log.d(SyncProcessService.TAG, "DEBUG - SYNC FINISHED...");
            SyncProcessService.this.startSyncingDataWithGoogleFit();
            SyncProcessService.this.startSyncingDataWithMyFitnessPal();
            SyncProcessService.this.startSyncingDataWithUnderArmour();
        }
    };
    public BroadcastReceiver unexpectedDisconnectionProcessReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            SyncProcessService.this.updateSyncStatusWhenFailed(false, false, false);
            Log.d(SyncProcessService.TAG, "DEBUG - UNEXPECTED DISCONNECTION FROM OMNI TRAINER...");
        }
    };

    public void onCreate() {
        super.onCreate();
        getSharedPreferences();
        initDataBaseElements();
        registerBroadcastReceivers();
        this.mUnderArmourAuthenticationService = new AuthenticationService(getApplicationContext(), UnderArmourConstants.CLIENT_KEY, UnderArmourConstants.CLIENT_SECRET);
        this.mHandler = new Handler();
        this.mIsDeviceNotFoundOrBusy = false;
        this.mGotMachineBle = false;
        this.mOmniDataArray = new ArrayList<>();
        HandlerThread workerThread = new HandlerThread(SYNC_PROCESS_SERVICE, 10);
        workerThread.start();
        this.mServiceLooper = workerThread.getLooper();
        this.mServiceHandler = new ServiceHandler(this.mServiceLooper);
    }

    private void getSharedPreferences() {
        this.mSettings = getSharedPreferences(Preferences.OMNI_TRAINER_PREFERENCES, 0);
    }

    private void initDataBaseElements() {
        AppComponent appComponent = ((OmniApplication) getApplicationContext()).getAppComponent();
        this.mGoogleFitHelper = appComponent.getGoogleFitHelper();
        this.mGoogleFitHelper.setOnUserFitnessServiceConnect(this);
        this.mGoogleFitHelper.initSharedPreferences();
        this.mDataBaseHelper = appComponent.getDatabaseHelper();
        try {
            this.mProductDao = this.mDataBaseHelper.getProductDao();
            this.mOmniTrainerDao = this.mDataBaseHelper.getOmniDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        this.mSyncType = intent.getStringExtra(SYNC_TYPE);
        Message message = this.mServiceHandler.obtainMessage();
        message.arg1 = startId;
        this.mServiceHandler.sendMessage(message);
        this.mSettings.edit().putBoolean(Preferences.SYNC_TOASTS_SHOWED, false).commit();
        if (this.mSyncType.equals("SYNC_FROM_CONNECTION_WIZARD")) {
            startSyncFromConnectionWizard();
            return 2;
        } else if (this.mSyncType.equals(AUTO_SYNC)) {
            startAutomaticSyncProcess();
            return 2;
        } else {
            startManualSyncProcess();
            return 2;
        }
    }

    private void registerBroadcastReceivers() {
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(this.addOmniTrainerToListReceiver, new IntentFilter(BroadcastKeys.ADD_OMNI_DEVICE));
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(this.successfulConnectionReceiver, new IntentFilter(BroadcastKeys.OMNI_SUCCESSFUL_CONNECTION));
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(this.connectionErrorReceiver, new IntentFilter(BroadcastKeys.OMNI_CONNECTION_ERROR));
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(this.consoleBusy, new IntentFilter(BroadcastKeys.OMNI_CONSOLE_BUSY));
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(this.syncDataFinishedReceiver, new IntentFilter(BroadcastKeys.SYNC_FINISHED));
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(this.syncDataFailedReceiver, new IntentFilter(BroadcastKeys.SYNC_FAILED));
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(this.unexpectedDisconnectionProcessReceiver, new IntentFilter(BroadcastKeys.UNEXPECTED_DISCONNECTION));
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(this.consoleNotResponsiveErrorReceiver, new IntentFilter(BroadcastKeys.CONSOLE_NOT_RESPONSIVE_ERROR));
    }

    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }
    }

    private void startSyncFromConnectionWizard() {
        this.mSettings.edit().putBoolean("SYNC_FROM_CONNECTION_WIZARD", true).commit();
        startSyncDataService();
    }

    public void startAutomaticSyncProcess() {
        Log.d(TAG, "DEBUG - STARTING AUTO SYNC");
        this.mSettings.edit().putBoolean("SYNC_FROM_CONNECTION_WIZARD", false).commit();
        saveSyncInProgressPreference();
        sendScanForOmniTrainerBroadcast(0);
        this.mHandler.postDelayed(new Runnable() {
            public void run() {
                SyncProcessService.this.validateScanningProcess();
            }
        }, 6000);
    }

    public void startManualSyncProcess() {
        Log.d(TAG, "DEBUG - STARTING MANUAL SYNC");
        this.mSettings.edit().putBoolean("SYNC_FROM_CONNECTION_WIZARD", false).commit();
        saveSyncInProgressPreference();
        this.mOmniDataArray.clear();
        startBLECallbacksHandlerService();
        sendScanForOmniTrainerBroadcast(2000);
        this.mHandler.postDelayed(new Runnable() {
            public void run() {
                SyncProcessService.this.validateScanningProcess();
            }
        }, 8000);
    }

    private void saveSyncInProgressPreference() {
        SharedPreferences.Editor editor = this.mSettings.edit();
        editor.putBoolean(Preferences.SYNC_IN_PROGRESS, true);
        editor.commit();
    }

    private void sendScanForOmniTrainerBroadcast(int bleCallbacksServiceDelay) {
        this.mHandler.postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent();
                intent.setAction(BroadcastKeys.START_OMNI_SCAN);
                LocalBroadcastManager.getInstance(SyncProcessService.this.getApplicationContext()).sendBroadcast(intent);
            }
        }, (long) bleCallbacksServiceDelay);
    }

    /* access modifiers changed from: private */
    public void validateScanningProcess() {
        if (currentDefaultOmniTrainerFound()) {
            checkDefaultOmniTrainerState();
        } else {
            updateSyncStatusWhenFailed(true, false, false);
        }
    }

    private boolean currentDefaultOmniTrainerFound() {
        try {
            this.mDefaultOmniData = this.mOmniTrainerDao.queryBuilder().queryForFirst();
            OmniData foundOmniData = getOmniTrainer(this.mDefaultOmniData != null ? this.mDefaultOmniData.getmUniqueID() : "");
            if (!(this.mDefaultOmniData == null || foundOmniData == null)) {
                this.mDefaultOmniData = foundOmniData;
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void checkDefaultOmniTrainerState() {
        if (!this.mDefaultOmniData.ismIsBusy()) {
            sendConnectToOmniTrainerBroadcast(this.mDefaultOmniData, 0);
            return;
        }
        this.mIsDeviceNotFoundOrBusy = true;
        updateSyncStatusWhenFailed(false, true, false);
    }

    private OmniData getOmniTrainer(String omniTrainerId) {
        Iterator<OmniData> it = this.mOmniDataArray.iterator();
        while (it.hasNext()) {
            OmniData omniData = it.next();
            if (omniData.getmUniqueID().equals(omniTrainerId)) {
                return omniData;
            }
        }
        this.mIsDeviceNotFoundOrBusy = true;
        return null;
    }

    private void startBLECallbacksHandlerService() {
        startService(new Intent(getApplicationContext(), BLECallbacksHandlerService.class));
    }

    private void sendConnectToOmniTrainerBroadcast(final OmniData defaultOmniData, int initDelay) {
        this.mHandler.postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constants.OMNI_TRAINER, defaultOmniData);
                bundle.putBoolean(Constants.CONNECTION_FROM_OMNI_TRAINER_LIST, false);
                bundle.putBoolean(Constants.IS_MANUAL_SYNC, true);
                intent.setAction(BroadcastKeys.CONNECT_TO_OMNI_DEVICE);
                intent.putExtras(bundle);
                LocalBroadcastManager.getInstance(SyncProcessService.this.getApplicationContext()).sendBroadcast(intent);
            }
        }, (long) initDelay);
    }

    /* access modifiers changed from: private */
    public void startSyncDataService() {
        startService(new Intent(getApplicationContext(), SyncOmniDataService.class));
    }

    /* access modifiers changed from: private */
    public void updateSyncStatusWhenFinished() {
        sendStopSyncDataServiceBroadcast();
        saveSyncFinishedPreference();
        sendBroadcastUpdateStatusToSuccessSycFinishOnMainScreen();
        stopSelf();
    }

    private void sendBroadcastUpdateStatusToSuccessSycFinishOnMainScreen() {
        Intent intent = new Intent();
        intent.setAction(BroadcastKeys.UPDATE_MAIN_ACTIVITY_WHEN_FINISHED);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }

    public void updateSyncStatusWhenFailed(boolean isAppUnableToConnectWithOmniTrainer, boolean isConsoleInBusyState, boolean isConsoleNotResponsive) {
        updateProductDataAccordingWithSyncResult(isAppUnableToConnectWithOmniTrainer, isConsoleInBusyState);
        this.mSettings.edit().putBoolean(Preferences.IS_SYNC_IN_ERROR_STATE, true).commit();
        sendStopSyncDataServiceBroadcast();
        stopOmniTrainerProcess();
        saveSyncFinishedPreference();
        sendStopSyncAnimationOnRemainingScreens();
        sendBroadcastUpdateStatusToFailedOnMainScreen(isConsoleNotResponsive, isConsoleInBusyState);
        stopSelf();
        Log.d(TAG, "DEBUG - SYNC FAILED...");
    }

    private void sendBroadcastUpdateStatusToFailedOnMainScreen(boolean isConsoleNotResponsive, boolean isConsoleInBusyState) {
        Intent intent = new Intent();
        intent.setAction(BroadcastKeys.UPDATE_MAIN_ACTIVITY_WHEN_FAILED);
        intent.putExtra(Constants.CONSOLE_NOT_RESPONSIVE, isConsoleNotResponsive);
        intent.putExtra(Constants.CONSOLE_BUSY, isConsoleInBusyState);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }

    /* access modifiers changed from: private */
    public void startSyncingDataWithGoogleFit() {
        if (!this.mGoogleFitHelper.isConnected() || this.mSettings.getBoolean(Preferences.IS_GOOGLE_FIT_SYNC_IN_PROGRESS, false)) {
            this.mGoogleFitHelper.connectWithGoogleFit((Activity) null);
            return;
        }
        this.mSettings.edit().putBoolean(Preferences.IS_GOOGLE_FIT_SYNC_IN_PROGRESS, true).commit();
        FitAppsSyncDataService.startSyncWithGoogleFitAction(getApplicationContext());
    }

    public void OnConnectionSuccessListener() {
        this.mSettings.edit().putBoolean(Preferences.IS_GOOGLE_FIT_SYNC_IN_PROGRESS, true).commit();
        FitAppsSyncDataService.startSyncWithGoogleFitAction(getApplicationContext());
    }

    public void OnConnectionFailedListener(int errorCode, int requestCode) {
    }

    public void OnDisconnectedSuccess() {
    }

    public void OnDisconnectionError() {
    }

    /* access modifiers changed from: private */
    public void startSyncingDataWithMyFitnessPal() {
        if (this.mSettings.getBoolean(Preferences.IS_MY_FITNESS_PAL_CONNECTED, false) && !this.mSettings.getBoolean(Preferences.IS_MFP_SYNC_IN_PROGRESS, false)) {
            this.mSettings.edit().putBoolean(Preferences.IS_MFP_SYNC_IN_PROGRESS, true).commit();
            FitAppsSyncDataService.startSyncWithMyFitnessPalAction(getApplicationContext());
        }
    }

    /* access modifiers changed from: private */
    public void startSyncingDataWithUnderArmour() {
        if (this.mUnderArmourAuthenticationService.isConnectedToUnderArmour() && !this.mSettings.getBoolean(Preferences.IS_UNDER_ARMOUR_SYNC_IN_PROGRESS, false)) {
            UnderArmourSyncService.startSyncWithUnderArmourAction(getApplicationContext());
        }
    }

    private void updateProductDataAccordingWithSyncResult(boolean isAppUnableToConnectWithOmniTrainer, boolean isConsoleInBusyState) {
        try {
            sendUnableToConnectBroadcast();
            DateTime currentSyncDate = new DateTime();
            Product currentProduct = this.mProductDao.queryBuilder().queryForFirst();
            if (currentProduct != null) {
                updateSyncState(currentProduct, currentSyncDate, isAppUnableToConnectWithOmniTrainer, isConsoleInBusyState);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateSyncState(Product currentProduct, DateTime currentSyncDate, boolean isAppUnableToConnectWithOmniTrainer, boolean isConsoleInBusyState) throws SQLException {
        currentProduct.setmLastSync(currentSyncDate);
        if (isAppUnableToConnectWithOmniTrainer) {
            currentProduct.setmLastSyncStatus(SyncStatus.UNABLE_TO_CONNECT);
        } else if (isConsoleInBusyState) {
            currentProduct.setmLastSyncStatus(SyncStatus.BUSY_STATE);
        } else {
            currentProduct.setmLastSyncStatus(SyncStatus.FAILED);
        }
        this.mProductDao.update(currentProduct);
    }

    private void sendUnableToConnectBroadcast() {
        Intent syncStartedIntent = new Intent();
        syncStartedIntent.setAction(BroadcastKeys.UNABLE_TO_CONNECT);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(syncStartedIntent);
    }

    private void saveSyncFinishedPreference() {
        SharedPreferences.Editor editor = this.mSettings.edit();
        editor.putBoolean(Preferences.SYNC_IN_PROGRESS, false);
        editor.commit();
    }

    private void sendStopSyncAnimationOnRemainingScreens() {
        Intent intent = new Intent();
        intent.setAction(BroadcastKeys.STOP_SYNC_STATE_ON_OTHER_SCREENS);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }

    private void sendStopSyncDataServiceBroadcast() {
        Intent intent = new Intent();
        intent.setAction(BroadcastKeys.STOP_SYNC_SERVICE);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }

    private void stopOmniTrainerProcess() {
        if (this.mIsDeviceNotFoundOrBusy) {
            sendStopOmniTrainerScanBroadcast();
            this.mIsDeviceNotFoundOrBusy = false;
        }
        sendDisconnectOmniTrainerBroadcast();
    }

    private void sendDisconnectOmniTrainerBroadcast() {
        Log.d(TAG, "DEBUG - SENDING OMNI TRAINER DISCONNECTION...");
        Intent intent = new Intent();
        intent.setAction(BroadcastKeys.DISCONNECT_OMNI_TRAINER);
        intent.putExtra(Constants.HAVE_BLE, false);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }

    private void sendStopOmniTrainerScanBroadcast() {
        Log.d(TAG, "DEBUG - STOPPING OMNI TRAINER SCANNING...");
        Intent intent = new Intent();
        intent.setAction(BroadcastKeys.STOP_OMNI_SCAN);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }

    public void onDestroy() {
        super.onDestroy();
        unregisterBroadcastReceivers();
    }

    private void unregisterBroadcastReceivers() {
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(this.addOmniTrainerToListReceiver);
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(this.successfulConnectionReceiver);
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(this.connectionErrorReceiver);
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(this.syncDataFinishedReceiver);
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(this.consoleBusy);
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(this.syncDataFailedReceiver);
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(this.unexpectedDisconnectionProcessReceiver);
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(this.consoleNotResponsiveErrorReceiver);
    }

    public IBinder onBind(Intent intent) {
        return null;
    }
}
