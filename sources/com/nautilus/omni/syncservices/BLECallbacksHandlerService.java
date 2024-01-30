package com.nautilus.omni.syncservices;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
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
import android.util.SparseArray;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.nautilus.omni.app.OmniApplication;
import com.nautilus.omni.app.Preferences;
import com.nautilus.omni.bleservices.OmniBLEService;
import com.nautilus.omni.bleservices.OmniCallbacks;
import com.nautilus.omni.bleservices.ble.OmniDevice;
import com.nautilus.omni.bleservices.ble.omnitrainer.OmniConnectionState;
import com.nautilus.omni.bleservices.ble.omnitrainer.OmniDictionaryKeys;
import com.nautilus.omni.bleservices.ble.omnitrainer.OmniErrorTypes;
import com.nautilus.omni.dataaccess.WorkoutDaoHelper;
import com.nautilus.omni.databasemanager.DataBaseHelper;
import com.nautilus.omni.model.dto.OmniConsoleType;
import com.nautilus.omni.model.dto.OmniData;
import com.nautilus.omni.model.dto.OmniMachineType;
import com.nautilus.omni.model.dto.Product;
import com.nautilus.omni.model.dto.SyncStatus;
import com.nautilus.omni.model.dto.User;
import com.nautilus.omni.model.dto.Workout;
import com.nautilus.omni.util.BroadcastKeys;
import com.nautilus.omni.util.Constants;
import com.nautilus.omni.util.DateUtil;
import com.nautilus.omni.util.Util;
import java.nio.ByteBuffer;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;

public class BLECallbacksHandlerService extends Service {
    public static final String BLE_SERVICE = "BLE_SERVICE";
    private static final int DEFAULT_USER = 1;
    private static final int END_PAIRING_DELAY = 2000;
    public static final int MOST_RECENT_WORKOUT_INDEX = 0;
    private static final int SIXTY_SECONDS = 60;
    public static final String TAG = BLECallbacksHandlerService.class.getSimpleName();
    /* access modifiers changed from: private */
    public boolean checkingAvailableWorkouts;
    private BroadcastReceiver connectToOmniDataReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            OmniData selectedOmniData = (OmniData) bundle.getParcelable(Constants.OMNI_TRAINER);
            boolean unused = BLECallbacksHandlerService.this.mIsConnectionFromOmniDataList = bundle.getBoolean(Constants.CONNECTION_FROM_OMNI_TRAINER_LIST);
            boolean unused2 = BLECallbacksHandlerService.this.mIsManualSync = bundle.getBoolean(Constants.IS_MANUAL_SYNC, false);
            boolean unused3 = BLECallbacksHandlerService.this.mIsConnectionFromPairingScreen = bundle.getBoolean(Constants.CONNECTION_FROM_PAIRING_SCREEN);
            ((OmniApplication) BLECallbacksHandlerService.this.getApplication()).setCurrentOmniData(selectedOmniData);
            BLECallbacksHandlerService.this.connectToSelectedOmniTrainer(selectedOmniData);
        }
    };
    private BroadcastReceiver disconnectOmniDataReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            boolean unused = BLECallbacksHandlerService.this.mIsDisconnectionForOmniDataError = false;
            if (intent.getBooleanExtra(Constants.HAVE_BLE, true)) {
                Log.d(BLECallbacksHandlerService.TAG, "DEBUG - CALLING RELEASE BLE FOR DISCONNECTION...");
                BLECallbacksHandlerService.this.mOmniBLEService.releaseBLE();
                boolean unused2 = BLECallbacksHandlerService.this.mIsReleaseBleForDisconnection = true;
                return;
            }
            BLECallbacksHandlerService.this.executeMachineDisconnection();
        }
    };
    private BroadcastReceiver endPairingProcessReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            Log.d(BLECallbacksHandlerService.TAG, "DEBUG - ENDING PAIRING PROCESS...");
            BLECallbacksHandlerService.this.mOmniBLEService.endPairingProcess();
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    boolean unused = BLECallbacksHandlerService.this.mIsReleaseBleFromPairingScreen = true;
                    Log.d(BLECallbacksHandlerService.TAG, "DEBUG - CALLING RELEASE BLE ON PAIRING PROCESS...");
                    BLECallbacksHandlerService.this.mOmniBLEService.releaseBLE();
                }
            }, 2000);
        }
    };
    private BroadcastReceiver getBLELockReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            Log.d(BLECallbacksHandlerService.TAG, "DEBUG - LOCKING BLE");
            boolean unused = BLECallbacksHandlerService.this.mIsDisconnectionForOmniDataError = true;
            boolean success = false;
            int count = 0;
            while (!success) {
                int count2 = count + 1;
                if (count < 100) {
                    success = BLECallbacksHandlerService.this.mOmniBLEService.getBLE();
                    try {
                        Thread.sleep(10);
                        count = count2;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        count = count2;
                    }
                } else {
                    return;
                }
            }
            int i = count;
        }
    };
    private BroadcastReceiver getOmniDataProductDataReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            BLECallbacksHandlerService.this.mOmniBLEService.getProductData();
        }
    };
    private BroadcastReceiver getOmniDataStatusReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            Log.d(BLECallbacksHandlerService.TAG, "DEBUG - GET STATUS EXECUTED...");
            BLECallbacksHandlerService.this.mOmniBLEService.getStatus();
        }
    };
    private BroadcastReceiver getUserDataReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            int userIndex = intent.getIntExtra(Preferences.USER_INDEX, -1);
            if (userIndex != -1) {
                BLECallbacksHandlerService.this.mOmniBLEService.getUserData(userIndex);
            }
        }
    };
    private BroadcastReceiver getWorkoutDataReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            BLECallbacksHandlerService.this.getCurrentUser(intent);
            if (BLECallbacksHandlerService.this.mConnectedConsoleType != null && BLECallbacksHandlerService.this.mConnectedConsoleType != OmniConsoleType.MY14) {
                BLECallbacksHandlerService.this.mOmniBLEService.clearNVMFlags();
            } else if (BLECallbacksHandlerService.this.mConnectedConsoleType != null) {
                BLECallbacksHandlerService.this.getNewWorkoutsIfAvailable();
            }
        }
    };
    /* access modifiers changed from: private */
    public ArrayList<Workout> mArrayOfNewWorkouts;
    private BluetoothAdapter mBluetoothAdaptor;
    /* access modifiers changed from: private */
    public OmniConsoleType mConnectedConsoleType;
    private User mCurrentUser;
    private DataBaseHelper mDataBaseHelper;
    private Workout mHighestRecordIdWorkout;
    /* access modifiers changed from: private */
    public boolean mIsConnectionFromOmniDataList;
    /* access modifiers changed from: private */
    public boolean mIsConnectionFromPairingScreen;
    /* access modifiers changed from: private */
    public boolean mIsDisconnectionForOmniDataError;
    /* access modifiers changed from: private */
    public boolean mIsManualSync;
    /* access modifiers changed from: private */
    public boolean mIsReleaseBleForDisconnection;
    /* access modifiers changed from: private */
    public boolean mIsReleaseBleFromPairingScreen;
    /* access modifiers changed from: private */
    public OmniBLEService mOmniBLEService;
    private ServiceHandler mServiceHandler;
    private Looper mServiceLooper;
    private SharedPreferences mSettings;
    private int mTotalOfWorkoutsToGet;
    private Dao<User, Integer> mUserDao;
    private Dao<Workout, Integer> mWorkoutDao;
    private WorkoutDaoHelper mWorkoutDaoHelper;
    /* access modifiers changed from: private */
    public int mWorkoutsCounter;
    private BroadcastReceiver startPairingProcessReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            Log.d(BLECallbacksHandlerService.TAG, "DEBUG - PAIRING PROCESS STARTED...");
            BLECallbacksHandlerService.this.mOmniBLEService.startPairingProcess();
        }
    };
    private BroadcastReceiver startScanReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            Log.d(BLECallbacksHandlerService.TAG, "DEBUG - SCANNING...");
            BLECallbacksHandlerService.this.mOmniBLEService.startOmniTrainerScan();
        }
    };
    private BroadcastReceiver stopOmniDataScanReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            Log.d(BLECallbacksHandlerService.TAG, "DEBUG - STOP SCAN CALLED...");
            BLECallbacksHandlerService.this.mOmniBLEService.stopOmniTrainerScan();
        }
    };
    private OmniCallbacks tc200CallbackHandler = new OmniCallbacks() {
        public void onFoundOmni(OmniDevice foundOmni, String uniqueID, String name, int signalStrength) {
            super.onFoundOmni(foundOmni, uniqueID, name, signalStrength);
            Log.d(TAG, "DEBUG - TEST FOUND MY14");
            BLECallbacksHandlerService.this.sendAddOmniDataToListBroadcast(BLECallbacksHandlerService.this.buildOmniDataObject(foundOmni, BLECallbacksHandlerService.this.buildOmniIdFromMacAddress(uniqueID), name, signalStrength, 1, OmniConsoleType.MY14, true));
        }

        public void omniSignalUpdate(OmniDevice foundOmni, String uniqueID, int signalStrength) {
            super.omniSignalUpdate(foundOmni, uniqueID, signalStrength);
            Log.d(TAG, "DEBUG - TEST UPDATE MY14 ");
            BLECallbacksHandlerService.this.sendOmniDataUpdateBroadcast(BLECallbacksHandlerService.this.buildOmniDataObject(foundOmni, BLECallbacksHandlerService.this.buildOmniIdFromMacAddress(uniqueID), "", signalStrength, 1, OmniConsoleType.MY14, true));
        }

        public void onFoundMY16(OmniDevice foundOmni, ByteBuffer uniqueID, String name, int signalStrength, int consoleState, int userNum, boolean isConsoleConnectable) {
            super.onFoundMY16(foundOmni, uniqueID, name, signalStrength, consoleState, userNum, isConsoleConnectable);
            Log.d(TAG, "DEBUG - TEST FOUND MY16 ");
            BLECallbacksHandlerService.this.sendAddOmniDataToListBroadcast(BLECallbacksHandlerService.this.buildOmniDataObject(foundOmni, BLECallbacksHandlerService.this.buildMY16IdFromByteBuffer(uniqueID), name, signalStrength, 1, OmniConsoleType.MY16, isConsoleConnectable));
        }

        public void mY16SignalUpdate(OmniDevice foundOmni, ByteBuffer uniqueID, int signalStrength, int consoleState, int userNum, boolean isConsoleConnectable) {
            super.mY16SignalUpdate(foundOmni, uniqueID, signalStrength, consoleState, userNum, isConsoleConnectable);
            Log.d(TAG, "DEBUG - TEST UPDATE MY16 ");
            BLECallbacksHandlerService.this.sendOmniDataUpdateBroadcast(BLECallbacksHandlerService.this.buildOmniDataObject(foundOmni, BLECallbacksHandlerService.this.buildMY16IdFromByteBuffer(uniqueID), "", signalStrength, 1, OmniConsoleType.MY16, isConsoleConnectable));
        }

        public void nautilusDeviceConnectionStateChange(OmniConnectionState newConnectionState) {
            super.nautilusDeviceConnectionStateChange(newConnectionState);
            Log.d(TAG, "DEBUG - CONNECTION STATE:" + newConnectionState.toString());
            if (BLECallbacksHandlerService.this.mIsManualSync) {
                BLECallbacksHandlerService.this.handleManualSync(newConnectionState);
            } else {
                BLECallbacksHandlerService.this.handleConnectionWizardSync(newConnectionState);
            }
        }

        public void nautilusDeviceError(OmniErrorTypes omniError) {
            super.nautilusDeviceError(omniError);
            Log.d(TAG, "DEBUG - OMNI TRAINER ERROR: " + omniError.toString());
            BLECallbacksHandlerService.this.handleErrorInOmniData(omniError);
        }

        public void nautilusDeviceStatus(SparseArray statusData) {
            super.nautilusDeviceStatus(statusData);
            Log.d(TAG, "Status Data: " + statusData);
            if (workoutsWereModifiedDuringSync(((Integer) statusData.get(OmniDictionaryKeys.WorkoutRecordDataModified)).intValue())) {
                Log.d(TAG, "DEBUG - WORKOUTS WERE MODIFIED... RETRIEVING AGAIN");
                BLECallbacksHandlerService.this.mArrayOfNewWorkouts.clear();
                int unused = BLECallbacksHandlerService.this.mWorkoutsCounter = 0;
                BLECallbacksHandlerService.this.mOmniBLEService.clearNVMFlags();
                return;
            }
            Log.d(TAG, "DEBUG - WORKOUTS OK, STARTING SAVING IN DATABASE PROCESS");
            BLECallbacksHandlerService.this.sendWorkoutsListBroadcast();
        }

        private boolean workoutsWereModifiedDuringSync(int workoutModified) {
            return workoutModified == 1;
        }

        public void nvmClearFlagsStatus(boolean success) {
            super.nvmClearFlagsStatus(success);
            Log.d(TAG, "DEBUG - GOT NVM CLEAR STATUS");
            BLECallbacksHandlerService.this.getNewWorkoutsIfAvailable();
        }

        public void nautilusDeviceSystemData(int systemOmniUsers, int systemNumPrograms) {
            super.nautilusDeviceSystemData(systemOmniUsers, systemNumPrograms);
        }

        public void nautilusDeviceUserData(SparseArray userData) {
            super.nautilusDeviceUserData(userData);
            BLECallbacksHandlerService.this.sendUserDataBroadcast(userData);
        }

        public void nautilusDeviceProductData(SparseArray productData) {
            super.nautilusDeviceProductData(productData);
            BLECallbacksHandlerService.this.sendOmniDataProductData(productData);
        }

        public void nautilusDeviceUserWorkoutData(SparseArray userWorkoutData) {
            super.nautilusDeviceUserWorkoutData(userWorkoutData);
            if (BLECallbacksHandlerService.this.checkingAvailableWorkouts) {
                boolean unused = BLECallbacksHandlerService.this.checkingAvailableWorkouts = false;
                BLECallbacksHandlerService.this.checkIfWorkoutsAvailable(((Integer) userWorkoutData.get(OmniDictionaryKeys.WorkoutRecordID)).intValue());
                return;
            }
            BLECallbacksHandlerService.access$2308(BLECallbacksHandlerService.this);
            BLECallbacksHandlerService.this.addNewWorkoutToList(userWorkoutData);
        }

        public void nautilusDeviceUserNumberSelected(int userNumber) {
            super.nautilusDeviceUserNumberSelected(userNumber);
            BLECallbacksHandlerService.this.sendUserNumberSelectedBroadcast(userNumber);
        }

        public void nautilusDeviceConsoleDisconnectWarning(int currentUser) {
            super.nautilusDeviceConsoleDisconnectWarning(currentUser);
        }
    };

    static /* synthetic */ int access$2308(BLECallbacksHandlerService x0) {
        int i = x0.mWorkoutsCounter;
        x0.mWorkoutsCounter = i + 1;
        return i;
    }

    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
            BLECallbacksHandlerService.this.enableBluetooth();
            BLECallbacksHandlerService.this.startBLEService();
        }
    }

    public void onCreate() {
        initMiscellaneousData();
        this.mSettings = getSharedPreferences(Preferences.OMNI_TRAINER_PREFERENCES, 0);
        initDataBaseElements();
        registerBroadcastReceivers();
        HandlerThread workerThread = new HandlerThread(BLE_SERVICE, 10);
        workerThread.start();
        this.mServiceLooper = workerThread.getLooper();
        this.mServiceHandler = new ServiceHandler(this.mServiceLooper);
    }

    private void initMiscellaneousData() {
        this.mIsConnectionFromOmniDataList = false;
        this.mCurrentUser = new User();
        this.mArrayOfNewWorkouts = new ArrayList<>();
        this.mWorkoutsCounter = 0;
        this.mIsManualSync = false;
        this.mIsDisconnectionForOmniDataError = true;
        this.mIsConnectionFromPairingScreen = false;
        this.mIsReleaseBleFromPairingScreen = false;
        this.mIsReleaseBleForDisconnection = false;
    }

    private void initDataBaseElements() {
        this.mDataBaseHelper = ((OmniApplication) getApplication()).getAppComponent().getDatabaseHelper();
        try {
            this.mUserDao = this.mDataBaseHelper.getUserDao();
            this.mWorkoutDao = this.mDataBaseHelper.getWorkoutDao();
            this.mWorkoutDaoHelper = new WorkoutDaoHelper(this.mWorkoutDao);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void registerBroadcastReceivers() {
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(this.startScanReceiver, new IntentFilter(BroadcastKeys.START_OMNI_SCAN));
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(this.stopOmniDataScanReceiver, new IntentFilter(BroadcastKeys.STOP_OMNI_SCAN));
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(this.connectToOmniDataReceiver, new IntentFilter(BroadcastKeys.CONNECT_TO_OMNI_DEVICE));
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(this.getBLELockReceiver, new IntentFilter(BroadcastKeys.GET_BLE_LOCK));
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(this.getOmniDataStatusReceiver, new IntentFilter(BroadcastKeys.GET_OMNI_STATUS));
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(this.getOmniDataProductDataReceiver, new IntentFilter(BroadcastKeys.GET_OMNI_PRODUCT_DATA));
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(this.getUserDataReceiver, new IntentFilter(BroadcastKeys.GET_USER_DATA));
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(this.getWorkoutDataReceiver, new IntentFilter(BroadcastKeys.GET_WORKOUT_DATA));
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(this.disconnectOmniDataReceiver, new IntentFilter(BroadcastKeys.DISCONNECT_OMNI_TRAINER));
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(this.startPairingProcessReceiver, new IntentFilter(BroadcastKeys.START_PAIRING_PROCESS));
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(this.endPairingProcessReceiver, new IntentFilter(BroadcastKeys.END_PAIRING_PROCESS));
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Message message = this.mServiceHandler.obtainMessage();
        message.arg1 = startId;
        this.mServiceHandler.sendMessage(message);
        return 2;
    }

    /* access modifiers changed from: private */
    public void enableBluetooth() {
        BluetoothManager btManager = (BluetoothManager) getSystemService("bluetooth");
        if (btManager != null) {
            this.mBluetoothAdaptor = btManager.getAdapter();
        }
    }

    /* access modifiers changed from: private */
    public void startBLEService() {
        this.mOmniBLEService = new OmniBLEService();
        if (this.mOmniBLEService != null) {
            this.mOmniBLEService.omniInitBLEService(this.mBluetoothAdaptor, this.tc200CallbackHandler);
        }
    }

    /* access modifiers changed from: private */
    public void connectToSelectedOmniTrainer(OmniData omniTrainer) {
        Log.d(TAG, "DEBUG - STOP SCAN CALLED BEFORE CALLING CONNECT...");
        this.mOmniBLEService.stopOmniTrainerScan();
        this.mConnectedConsoleType = omniTrainer.getmOmniConsoleType();
        Log.d(TAG, "DEBUG - CONNECTING TO OMNI TRAINER...");
        this.mOmniBLEService.connectToOmniTrainer(omniTrainer.getmOmniTrainerBluetoothDevice());
    }

    /* access modifiers changed from: private */
    public void handleManualSync(OmniConnectionState newConnectionState) {
        if (newConnectionState == OmniConnectionState.CONNECTED_NO_BLE && this.mIsReleaseBleForDisconnection) {
            executeMachineDisconnection();
        } else if (newConnectionState == OmniConnectionState.CONNECTED_NO_BLE) {
            sendSuccessfulConnectionBroadcast();
        } else if (newConnectionState == OmniConnectionState.CONNECTED_WITH_BLE) {
            sendSuccessfulBLELockBroadcast();
        } else if (newConnectionState == OmniConnectionState.ERROR) {
            sendConnectionErrorBroadcast(false);
        } else if (newConnectionState == OmniConnectionState.DISCONNECTED && this.mIsDisconnectionForOmniDataError) {
            sendUnexpectedDisconnectionBroadcast();
        }
    }

    /* access modifiers changed from: private */
    public void handleConnectionWizardSync(OmniConnectionState newConnectionState) {
        if (newConnectionState == OmniConnectionState.CONNECTED_NO_BLE && this.mIsConnectionFromPairingScreen) {
            sendSuccessfulConnectionFromPairingScreenBroadcast();
        } else if (newConnectionState == OmniConnectionState.CONNECTED_NO_BLE && this.mIsReleaseBleFromPairingScreen) {
            sendSuccessfulEndPairingBroadcast();
        } else if (newConnectionState == OmniConnectionState.CONNECTED_NO_BLE && this.mIsReleaseBleForDisconnection) {
            executeMachineDisconnection();
        } else if (newConnectionState == OmniConnectionState.CONNECTED_NO_BLE && this.mIsConnectionFromOmniDataList) {
            sendSuccessfulConnectionBroadcast();
        }
        if (newConnectionState == OmniConnectionState.CONNECTED_NO_BLE && !this.mIsConnectionFromOmniDataList) {
            sendHideConnectingDeviceToastBroadcast();
        } else if (newConnectionState == OmniConnectionState.CONNECTED_WITH_BLE) {
            sendSuccessfulBLELockBroadcast();
        } else if (newConnectionState == OmniConnectionState.ERROR && (this.mIsConnectionFromOmniDataList || this.mIsConnectionFromPairingScreen)) {
            sendConnectionErrorBroadcast(false);
        } else if (newConnectionState == OmniConnectionState.ERROR && !this.mIsConnectionFromOmniDataList) {
            sendShowErrorConnectingDialogBroadcast();
        } else if (newConnectionState == OmniConnectionState.DISCONNECTED && this.mIsDisconnectionForOmniDataError) {
            sendUnexpectedDisconnectionBroadcast();
        }
    }

    /* access modifiers changed from: private */
    public void executeMachineDisconnection() {
        this.mConnectedConsoleType = null;
        this.mIsReleaseBleForDisconnection = false;
        Log.d(TAG, "DEBUG - CALLING DISCONNECT FROM OMNI TRAINER...");
        this.mOmniBLEService.disconnectOmniTrainer();
        Log.d(TAG, "DEBUG - BLE CALLBACKS SERVICE STOPPED");
        stopSelf();
    }

    /* access modifiers changed from: private */
    public void handleErrorInOmniData(OmniErrorTypes omniErrorTypes) {
        if (omniErrorTypes == OmniErrorTypes.NO_WORKOUT_RECORD) {
            sendWorkoutsListBroadcast();
        } else if (omniErrorTypes == OmniErrorTypes.CONSOLE_NOT_RESPONSIVE) {
            sendConsoleNotResponsiveErrorBroadcast();
        } else if (omniErrorTypes == OmniErrorTypes.UNABLE_TO_GET_BLE || omniErrorTypes == OmniErrorTypes.CONSOLE_IS_BUSY) {
            sendConnectionErrorBroadcast(true);
        } else if (omniErrorTypes != OmniErrorTypes.UNABLE_TO_CONNECT && omniErrorTypes != OmniErrorTypes.UNABLE_TO_RELEASE_BLE) {
        } else {
            if (this.mIsConnectionFromOmniDataList) {
                sendConnectionErrorBroadcast(true);
            } else {
                sendShowErrorConnectingDialogBroadcast();
            }
        }
    }

    private void sendConsoleNotResponsiveErrorBroadcast() {
        this.mConnectedConsoleType = null;
        Intent intent = new Intent();
        intent.setAction(BroadcastKeys.CONSOLE_NOT_RESPONSIVE_ERROR);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }

    /* access modifiers changed from: private */
    public void sendUserNumberSelectedBroadcast(int userNumber) {
        Intent intent = new Intent();
        intent.setAction(BroadcastKeys.USER_NUMBER_SELECTED_RECEIVER);
        intent.putExtra(Constants.USER_NUMBER_SELECTED, userNumber);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }

    /* access modifiers changed from: private */
    public OmniData buildOmniDataObject(OmniDevice foundOmniData, String uniqueID, String name, int signalStrength, int userNumber, OmniConsoleType omniMachineType, boolean isConsoleConnectable) {
        boolean z = true;
        OmniData omniMachine = new OmniData();
        omniMachine.setmUniqueID(uniqueID);
        omniMachine.setmName(name);
        omniMachine.setmUserNumber(userNumber);
        omniMachine.setmSignalStrength(signalStrength);
        omniMachine.setmOmniBluetoothDevice(foundOmniData);
        omniMachine.setmUpdateTime(new DateTime());
        omniMachine.setmIsEnabled(true);
        omniMachine.setmOmniConsoleType(omniMachineType);
        if (isConsoleConnectable) {
            z = false;
        }
        omniMachine.setmIsBusy(z);
        omniMachine.setmOmniMachineType(OmniMachineType.valueOf(foundOmniData.getDeviceType().name()));
        return omniMachine;
    }

    /* access modifiers changed from: private */
    public String buildOmniIdFromMacAddress(String macAddress) {
        String[] valueArray = macAddress.split(Constants.COLON_SEPARATOR);
        return valueArray[valueArray.length - 3] + valueArray[valueArray.length - 2] + valueArray[valueArray.length - 1];
    }

    /* access modifiers changed from: private */
    public String buildMY16IdFromByteBuffer(ByteBuffer uniqueID) {
        return String.format("%02x%02x%02x", new Object[]{Byte.valueOf(uniqueID.get(3)), Byte.valueOf(uniqueID.get(4)), Byte.valueOf(uniqueID.get(5))});
    }

    /* access modifiers changed from: private */
    public void sendAddOmniDataToListBroadcast(OmniData omniTrainer) {
        Intent intent = new Intent();
        intent.setAction(BroadcastKeys.ADD_OMNI_DEVICE);
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.OMNI_TRAINER, omniTrainer);
        intent.putExtras(bundle);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }

    /* access modifiers changed from: private */
    public void sendOmniDataUpdateBroadcast(OmniData omniTrainer) {
        Intent intent = new Intent();
        intent.setAction(BroadcastKeys.OMNI_UPDATE);
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.OMNI_TRAINER, omniTrainer);
        intent.putExtras(bundle);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }

    private void sendSuccessfulConnectionBroadcast() {
        Intent intent = new Intent();
        intent.setAction(BroadcastKeys.OMNI_SUCCESSFUL_CONNECTION);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }

    private void sendConnectionErrorBroadcast(boolean unableToGetBle) {
        this.mConnectedConsoleType = null;
        Intent intent = new Intent();
        intent.setAction(BroadcastKeys.OMNI_CONNECTION_ERROR);
        intent.putExtra(Constants.UNABLE_TO_GET_BLE, unableToGetBle);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }

    private void sendSuccessfulConnectionFromPairingScreenBroadcast() {
        this.mIsConnectionFromPairingScreen = false;
        Intent intent = new Intent();
        intent.setAction(BroadcastKeys.SUCCESSFUL_CONNECTION_FROM_PAIRING_SCREEN);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }

    private void sendHideConnectingDeviceToastBroadcast() {
        Intent intent = new Intent();
        intent.setAction(BroadcastKeys.HIDE_CONNECTING_DEVICE_TOAST);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }

    private void sendShowErrorConnectingDialogBroadcast() {
        this.mConnectedConsoleType = null;
        Intent intent = new Intent();
        intent.setAction(BroadcastKeys.SHOW_ERROR_CONNECTING_DIALOG);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }

    private void sendSuccessfulBLELockBroadcast() {
        Intent intent = new Intent();
        intent.setAction(BroadcastKeys.SUCCESSFUL_BLE_LOCK);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }

    private void sendOmniDataStatusBroadcast(int activeUserIndex, int activeProgramID) {
        Log.d(TAG, "DEBUG - STATUS RECEIVED, SENDING STATUS...");
        Intent intent = new Intent();
        intent.setAction(BroadcastKeys.SEND_OMNI_STATUS);
        intent.putExtra(Constants.ACTIVE_USER_INDEX, activeUserIndex);
        intent.putExtra(Constants.ACTIVE_PROGRAM_ID, activeProgramID);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }

    /* access modifiers changed from: private */
    public void sendOmniDataProductData(SparseArray productData) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        Product product = new Product();
        product.setmConsumerVariant(String.valueOf(productData.get(200)));
        product.setmHardwareVariant(String.valueOf(productData.get(OmniDictionaryKeys.ProductHWVarient)));
        product.setmProductModelNumber("");
        product.setmManufacturer(String.valueOf(productData.get(OmniDictionaryKeys.ProductMfgName)));
        product.setmProductModelName(String.valueOf(productData.get(OmniDictionaryKeys.ProductModelName)));
        product.setmFirmwareVersion(String.valueOf(productData.get(OmniDictionaryKeys.ProductFWVersion)));
        product.setmSoftwareVersion(String.valueOf(productData.get(OmniDictionaryKeys.ProductSWVer)));
        product.setmLastSync(new DateTime());
        product.setmLastSyncStatus(SyncStatus.EMPTY);
        bundle.putParcelable(Constants.PRODUCT, product);
        intent.putExtras(bundle);
        intent.setAction(BroadcastKeys.SEND_OMNI_PRODUCT_DATA);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }

    /* access modifiers changed from: private */
    public void sendUserDataBroadcast(SparseArray userData) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        User user = new User();
        user.setmUserMachineIndex(((Integer) userData.get(OmniDictionaryKeys.UserIndex)).intValue());
        user.setWeight((double) ((Integer) userData.get(OmniDictionaryKeys.UserWeight)).intValue());
        user.setmLastSync(new DateTime());
        user.setmUnits(((Integer) userData.get(OmniDictionaryKeys.UserUnits)).intValue());
        user.setmNumberOfWorkoutRecords(((Integer) userData.get(OmniDictionaryKeys.UserNumWorkoutRecords)).intValue());
        bundle.putParcelable(User.USER, user);
        intent.putExtras(bundle);
        intent.setAction(BroadcastKeys.SEND_USER_DATA);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }

    /* access modifiers changed from: private */
    public void getCurrentUser(Intent intent) {
        try {
            List<User> users = this.mUserDao.queryBuilder().where().eq(User.USER_INDEX, Integer.valueOf(intent.getIntExtra(Preferences.USER_INDEX, -1))).query();
            if (users.size() > 0) {
                this.mCurrentUser = users.get(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public void getNewWorkoutsIfAvailable() {
        this.checkingAvailableWorkouts = true;
        Log.d(TAG, "DEBUG - CALLING GET WORKOUT DATA...");
        this.mOmniBLEService.getWorkoutData(this.mCurrentUser.getUserOmniTrainerIndex(), 0);
    }

    /* access modifiers changed from: private */
    public void checkIfWorkoutsAvailable(int mostRecentWorkoutRecordId) {
        if (isFirstSync() || areNewRecordsAvailable(mostRecentWorkoutRecordId)) {
            getNewWorkouts(mostRecentWorkoutRecordId);
            return;
        }
        this.mArrayOfNewWorkouts = null;
        sendWorkoutsListBroadcast();
    }

    private boolean isFirstSync() {
        try {
            return this.mWorkoutDao.countOf() == 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean areNewRecordsAvailable(int mostRecentWorkoutRecordId) {
        this.mHighestRecordIdWorkout = this.mWorkoutDaoHelper.getHighestRecordIdWorkout();
        if (mostRecentWorkoutRecordId > this.mHighestRecordIdWorkout.getmRecordId()) {
            return true;
        }
        return false;
    }

    private void getNewWorkouts(int mostRecentWorkoutRecordId) {
        try {
            if (this.mWorkoutDao.countOf() == 0) {
                this.mTotalOfWorkoutsToGet = this.mCurrentUser.getmNumberOfWorkoutRecords();
            } else {
                this.mTotalOfWorkoutsToGet = mostRecentWorkoutRecordId - this.mHighestRecordIdWorkout.getmRecordId();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.mOmniBLEService.getWorkoutData(this.mCurrentUser.getUserOmniTrainerIndex(), this.mWorkoutsCounter);
    }

    /* access modifiers changed from: private */
    public void addNewWorkoutToList(SparseArray userWorkoutData) {
        Workout workout = new Workout();
        workout.setmUser(this.mCurrentUser);
        workout.setmRecordId(((Integer) userWorkoutData.get(OmniDictionaryKeys.WorkoutRecordID)).intValue());
        workout.setmProgramId(((Integer) userWorkoutData.get(OmniDictionaryKeys.ProgramID)).intValue());
        workout.setmAvgIncline(((Integer) userWorkoutData.get(OmniDictionaryKeys.WorkoutAvgIncline)).intValue());
        workout.setmAvgLapTime(((Integer) userWorkoutData.get(OmniDictionaryKeys.WorkoutAvgLapTime)).intValue());
        workout.setmWorkoutFitnessScore(((Integer) userWorkoutData.get(OmniDictionaryKeys.WorkoutFitnessScore)).intValue());
        workout.setmWorkoutFitnessScoreQualified(((Integer) userWorkoutData.get(OmniDictionaryKeys.WorkoutFitnessScoreQualified)).intValue());
        workout.setmCalories(((Integer) userWorkoutData.get(OmniDictionaryKeys.WorkoutCalories)).intValue());
        workout.setmWorkoutLapsCompleted(((Integer) userWorkoutData.get(OmniDictionaryKeys.WorkoutLapsCompleted)).intValue());
        workout.setmAvgHeartRate(((Integer) userWorkoutData.get(OmniDictionaryKeys.WorkoutAvgHR)).intValue());
        workout.setmAvgResistance(((Integer) userWorkoutData.get(OmniDictionaryKeys.WorkoutAvgResistance)).intValue());
        workout.setmAvgRPM(((Integer) userWorkoutData.get(OmniDictionaryKeys.WorkoutAvgRPM)).intValue());
        workout.setmAvgPower(((Integer) userWorkoutData.get(OmniDictionaryKeys.WorkoutAvgPower)).intValue());
        workout.setmElapsedTime(((Integer) userWorkoutData.get(OmniDictionaryKeys.WorkoutElapsedTime)).intValue());
        workout.setmOriginalDay(((Integer) userWorkoutData.get(OmniDictionaryKeys.WorkoutDay)).intValue());
        workout.setmOriginalMonth(((Integer) userWorkoutData.get(OmniDictionaryKeys.WorkoutMonth)).intValue());
        workout.setmOriginalYear(((Integer) userWorkoutData.get(OmniDictionaryKeys.WorkoutYear)).intValue());
        workout.setmDistance(((Integer) userWorkoutData.get(OmniDictionaryKeys.WorkoutDistance)).intValue());
        workout.setmAvgSpeed(((Integer) userWorkoutData.get(OmniDictionaryKeys.WorkoutAvgSpeed)).intValue());
        workout.setmAvgCalorieBurnRate(Util.getAvgCalorieBurnRatePerMinute(workout));
        DateTime workoutDate = buildWorkoutTime(workout, buildWorkoutDate(userWorkoutData), userWorkoutData);
        workout.setmHeartRateMeasuredTime(((Integer) userWorkoutData.get(OmniDictionaryKeys.WorkoutAvgHRMeasureTime)).intValue());
        workout.setmFinishTime(workoutDate.plusSeconds(workout.getmElapsedTime()));
        workout.setmWorkoutDate(workoutDate);
        if (workout.getmCalories() > 0 || workout.getmElapsedTime() >= 60) {
            this.mArrayOfNewWorkouts.add(workout);
        }
        if (this.mWorkoutsCounter == this.mTotalOfWorkoutsToGet && this.mConnectedConsoleType == OmniConsoleType.MY14) {
            sendWorkoutsListBroadcast();
        } else if (this.mWorkoutsCounter == this.mTotalOfWorkoutsToGet) {
            checkConsoleStatus();
        } else {
            this.mOmniBLEService.getWorkoutData(this.mCurrentUser.getUserOmniTrainerIndex(), this.mWorkoutsCounter);
        }
    }

    private void checkConsoleStatus() {
        this.mOmniBLEService.getStatus();
    }

    private DateTime buildWorkoutDate(SparseArray userWorkoutData) {
        int day = ((Integer) userWorkoutData.get(OmniDictionaryKeys.WorkoutDay)).intValue();
        return DateUtil.buildWorkoutValidDate(((Integer) userWorkoutData.get(OmniDictionaryKeys.WorkoutYear)).intValue(), ((Integer) userWorkoutData.get(OmniDictionaryKeys.WorkoutMonth)).intValue(), day);
    }

    private DateTime buildWorkoutTime(Workout workout, DateTime workoutDate, SparseArray userWorkoutData) {
        int i;
        int i2;
        int i3;
        setOriginalTimeDataToM7Workout(workout, userWorkoutData);
        if (userWorkoutData.get(OmniDictionaryKeys.WorkoutFinishTimeHour) != null) {
            i = ((Integer) userWorkoutData.get(OmniDictionaryKeys.WorkoutFinishTimeHour)).intValue();
        } else {
            i = 0;
        }
        int hour = DateUtil.getValidHour(i);
        if (userWorkoutData.get(OmniDictionaryKeys.WorkoutFinishTimeMin) != null) {
            i2 = ((Integer) userWorkoutData.get(OmniDictionaryKeys.WorkoutFinishTimeMin)).intValue();
        } else {
            i2 = 0;
        }
        int minute = DateUtil.getValidMinute(i2);
        if (userWorkoutData.get(OmniDictionaryKeys.WorkoutFinishTimeSec) != null) {
            i3 = ((Integer) userWorkoutData.get(OmniDictionaryKeys.WorkoutFinishTimeSec)).intValue();
        } else {
            i3 = 0;
        }
        return workoutDate.withHourOfDay(hour).withMinuteOfHour(minute).withSecondOfMinute(DateUtil.getValidSecond(i3)).withMillisOfSecond(0);
    }

    private void setOriginalTimeDataToM7Workout(Workout workout, SparseArray userWorkoutData) {
        if (userWorkoutData.get(OmniDictionaryKeys.WorkoutFinishTimeSec) != null) {
            workout.setmOriginalSecond(((Integer) userWorkoutData.get(OmniDictionaryKeys.WorkoutFinishTimeSec)).intValue());
        } else {
            workout.setmOriginalSecond(0);
        }
        if (userWorkoutData.get(OmniDictionaryKeys.WorkoutFinishTimeMin) != null) {
            workout.setmOriginalMinute(((Integer) userWorkoutData.get(OmniDictionaryKeys.WorkoutFinishTimeMin)).intValue());
        } else {
            workout.setmOriginalMinute(0);
        }
        if (userWorkoutData.get(OmniDictionaryKeys.WorkoutFinishTimeHour) != null) {
            workout.setmOriginalHour(((Integer) userWorkoutData.get(OmniDictionaryKeys.WorkoutFinishTimeHour)).intValue());
        } else {
            workout.setmOriginalHour(0);
        }
    }

    /* access modifiers changed from: private */
    public void sendWorkoutsListBroadcast() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("workout", this.mArrayOfNewWorkouts);
        intent.putExtras(bundle);
        intent.setAction(BroadcastKeys.SEND_WORKOUT_DATA);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }

    private void sendSuccessfulEndPairingBroadcast() {
        this.mIsReleaseBleFromPairingScreen = false;
        Log.d(TAG, "DEBUG - RELEASE BLE FROM PAIRING SUCCESSFUL... END PAIRING SUCCESSFUL...");
        Intent intent = new Intent();
        intent.setAction(BroadcastKeys.SUCCESSFUL_END_PAIRING);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }

    private void sendUnexpectedDisconnectionBroadcast() {
        Log.d(TAG, "DEBUG - SENDING UNEXPECTED DISCONNECTION NOTIFICATION...");
        this.mConnectedConsoleType = null;
        Intent intent = new Intent();
        intent.setAction(BroadcastKeys.UNEXPECTED_DISCONNECTION);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onDestroy() {
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(this.startScanReceiver);
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(this.connectToOmniDataReceiver);
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(this.stopOmniDataScanReceiver);
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(this.getOmniDataStatusReceiver);
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(this.getBLELockReceiver);
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(this.getOmniDataProductDataReceiver);
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(this.getUserDataReceiver);
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(this.getWorkoutDataReceiver);
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(this.disconnectOmniDataReceiver);
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(this.startPairingProcessReceiver);
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(this.endPairingProcessReceiver);
        if (this.mDataBaseHelper != null) {
            OpenHelperManager.releaseHelper();
            this.mDataBaseHelper = null;
        }
    }
}
