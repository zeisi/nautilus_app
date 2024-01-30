package com.nautilus.omni.syncservices;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.nautilus.omni.app.OmniApplication;
import com.nautilus.omni.app.Preferences;
import com.nautilus.omni.databasemanager.DataBaseHelper;
import com.nautilus.omni.model.dto.Product;
import com.nautilus.omni.model.dto.SyncStatus;
import com.nautilus.omni.model.dto.User;
import com.nautilus.omni.model.dto.Workout;
import com.nautilus.omni.syncservices.syncserviceshelpers.WorkoutSyncDataLoaderHelper;
import com.nautilus.omni.util.BroadcastKeys;
import com.nautilus.omni.util.Constants;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;

public class SyncOmniDataService extends Service {
    public static final String SYNC_DATA_SERVICE = "SYNC_DATA_SERVICE";
    public static final String TAG = SyncOmniDataService.class.getSimpleName();
    public BroadcastReceiver bleStatusReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            Log.d(SyncOmniDataService.TAG, "DEBUG - SAVING BLE STATUS...");
        }
    };
    public BroadcastReceiver connectionErrorReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            SyncOmniDataService.this.sendSyncFailedBroadcast();
        }
    };
    public BroadcastReceiver errorDialogReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            SyncOmniDataService.this.sendSyncFailedBroadcast();
        }
    };
    /* access modifiers changed from: private */
    public User mCurrentUser;
    private DataBaseHelper mDataBaseHelper;
    /* access modifiers changed from: private */
    public boolean mGotBleFromMachine;
    private boolean mNewWorkoutsLoaded;
    private Dao<Product, Integer> mProductDao;
    private ServiceHandler mServiceHandler;
    private Looper mServiceLooper;
    private SharedPreferences mSettings;
    private Dao<User, Integer> mUserDao;
    private int mUserIndex;
    public BroadcastReceiver omniProductDataReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            Log.d(SyncOmniDataService.TAG, "DEBUG - SAVING BLE PRODUCT...");
            SyncOmniDataService.this.saveMaxTrainerProductDataInDataBase((Product) intent.getExtras().getParcelable(Constants.PRODUCT));
            SyncOmniDataService.this.getUserDataFromMaxTrainer();
        }
    };
    public BroadcastReceiver stopSyncServiceReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            SyncOmniDataService.this.stopSelf();
            Log.d(SyncOmniDataService.TAG, "DEBUG - SYNC DATA SERVICE STOPPED");
        }
    };
    private BroadcastReceiver successfulBLELockReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            boolean unused = SyncOmniDataService.this.mGotBleFromMachine = true;
            SyncOmniDataService.this.getMaxTrainerProductData();
        }
    };
    public BroadcastReceiver userDataReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            Log.d(SyncOmniDataService.TAG, "DEBUG - SAVING BLE USER DATA...");
            User user = (User) intent.getExtras().getParcelable(User.USER);
            User unused = SyncOmniDataService.this.mCurrentUser = user;
            SyncOmniDataService.this.saveUserDataInDataBase(user);
            SyncOmniDataService.this.getMaxTrainerWorkoutData();
        }
    };
    /* access modifiers changed from: private */
    public WorkoutSyncDataLoaderHelper workoutDataLoaderHelper;
    public BroadcastReceiver workoutDataReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            Log.d(SyncOmniDataService.TAG, "DEBUG - SAVING WORKOUT DATA...");
            ArrayList<Workout> workoutsList = intent.getExtras().getParcelableArrayList("workout");
            if (workoutsList == null || workoutsList.size() <= 0) {
                SyncOmniDataService.this.sendSyncFinishedBroadcast(false);
            } else {
                SyncOmniDataService.this.saveWorkoutData(workoutsList);
            }
        }
    };

    public void onCreate() {
        Log.d(TAG, "DEBUG -------------------------- ON CREATE SYNC DATA SERVICE");
        getSharedPreferences();
        this.mNewWorkoutsLoaded = false;
        this.mGotBleFromMachine = false;
        initDataBaseElements();
        registerBroadcastReceivers();
        HandlerThread workerThread = new HandlerThread(SYNC_DATA_SERVICE, 10);
        workerThread.start();
        this.mServiceLooper = workerThread.getLooper();
        this.mServiceHandler = new ServiceHandler(this.mServiceLooper);
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Message message = this.mServiceHandler.obtainMessage();
        message.arg1 = startId;
        this.mServiceHandler.sendMessage(message);
        return 2;
    }

    private void getSharedPreferences() {
        this.mSettings = getSharedPreferences(Preferences.OMNI_TRAINER_PREFERENCES, 0);
        this.mUserIndex = this.mSettings.getInt(Preferences.USER_INDEX, -1);
    }

    private void registerBroadcastReceivers() {
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(this.successfulBLELockReceiver, new IntentFilter(BroadcastKeys.SUCCESSFUL_BLE_LOCK));
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(this.connectionErrorReceiver, new IntentFilter(BroadcastKeys.OMNI_CONNECTION_ERROR));
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(this.errorDialogReceiver, new IntentFilter(BroadcastKeys.SHOW_ERROR_CONNECTING_DIALOG));
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(this.bleStatusReceiver, new IntentFilter(BroadcastKeys.SEND_OMNI_STATUS));
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(this.omniProductDataReceiver, new IntentFilter(BroadcastKeys.SEND_OMNI_PRODUCT_DATA));
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(this.userDataReceiver, new IntentFilter(BroadcastKeys.SEND_USER_DATA));
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(this.workoutDataReceiver, new IntentFilter(BroadcastKeys.SEND_WORKOUT_DATA));
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(this.stopSyncServiceReceiver, new IntentFilter(BroadcastKeys.STOP_SYNC_SERVICE));
    }

    private void initDataBaseElements() {
        this.mDataBaseHelper = ((OmniApplication) getApplication()).getAppComponent().getDatabaseHelper();
        try {
            this.mProductDao = this.mDataBaseHelper.getProductDao();
            this.mUserDao = this.mDataBaseHelper.getUserDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
            SyncOmniDataService.this.saveSyncInProgressPreference();
            SyncOmniDataService.this.lockBLE();
        }
    }

    /* access modifiers changed from: private */
    public void saveSyncInProgressPreference() {
        SharedPreferences.Editor editor = this.mSettings.edit();
        editor.putBoolean(Preferences.SYNC_IN_PROGRESS, true);
        editor.commit();
    }

    /* access modifiers changed from: private */
    public void lockBLE() {
        Log.d(TAG, "DEBUG - LOCK BLE");
        Intent intent = new Intent();
        intent.setAction(BroadcastKeys.GET_BLE_LOCK);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }

    /* access modifiers changed from: private */
    public void getMaxTrainerProductData() {
        Intent intent = new Intent();
        intent.setAction(BroadcastKeys.GET_OMNI_PRODUCT_DATA);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }

    /* access modifiers changed from: private */
    public void saveMaxTrainerProductDataInDataBase(Product product) {
        try {
            if (this.mProductDao.countOf() == 0) {
                this.mProductDao.create(product);
                return;
            }
            product.setmId(this.mProductDao.queryBuilder().queryForFirst().getmId());
            this.mProductDao.update(product);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public void getUserDataFromMaxTrainer() {
        Intent intent = new Intent();
        intent.setAction(BroadcastKeys.GET_USER_DATA);
        intent.putExtra(Preferences.USER_INDEX, this.mUserIndex);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }

    /* access modifiers changed from: private */
    public void saveUserDataInDataBase(User user) {
        try {
            List<User> usersList = this.mUserDao.queryBuilder().where().eq(User.USER_INDEX, Integer.valueOf(user.getUserOmniTrainerIndex())).query();
            if (usersList.size() == 0) {
                this.mUserDao.create(user);
                return;
            }
            user.setId(usersList.get(0).getId());
            this.mUserDao.update(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public void getMaxTrainerWorkoutData() {
        Intent intent = new Intent();
        intent.putExtra(Preferences.USER_INDEX, this.mUserIndex);
        intent.setAction(BroadcastKeys.GET_WORKOUT_DATA);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }

    /* access modifiers changed from: private */
    public void saveWorkoutData(ArrayList<Workout> workoutsList) {
        this.mNewWorkoutsLoaded = true;
        this.workoutDataLoaderHelper = new WorkoutSyncDataLoaderHelper(getApplicationContext(), this.mCurrentUser, workoutsList, this.mSettings.getBoolean(Preferences.GOALS_ENABLED, false));
        new WorkoutsSaveProcess().execute(new Void[0]);
    }

    private class WorkoutsSaveProcess extends AsyncTask<Void, Void, Boolean> {
        private WorkoutsSaveProcess() {
        }

        /* access modifiers changed from: protected */
        public Boolean doInBackground(Void... params) {
            return Boolean.valueOf(SyncOmniDataService.this.workoutDataLoaderHelper.saveWorkouts());
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if (result.booleanValue()) {
                SyncOmniDataService.this.finishSyncProcess();
            } else {
                SyncOmniDataService.this.sendSyncFailedBroadcast();
            }
        }
    }

    public void finishSyncProcess() {
        Log.d(TAG, "DEBUG - WORKOUTS SAVED...");
        sendSyncFinishedBroadcast(true);
    }

    /* access modifiers changed from: private */
    public void sendSyncFinishedBroadcast(boolean newWorkoutsLoaded) {
        Log.d(TAG, "DEBUG - SENDING FINISH SYNC BROADCAST...");
        this.mSettings.edit().putBoolean(Preferences.SYNC_IN_PROGRESS, false).commit();
        Intent intent = new Intent();
        intent.putExtra(BroadcastKeys.NEW_WORKOUTS_LOADED, newWorkoutsLoaded);
        intent.setAction(BroadcastKeys.SYNC_FINISHED);
        updateSyncState(true, newWorkoutsLoaded);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
        sendDisconnectTreadClimberBroadcast();
        this.mGotBleFromMachine = false;
    }

    /* access modifiers changed from: private */
    public void sendSyncFailedBroadcast() {
        Log.d(TAG, "DEBUG - SENDING SYNC FAILED BROADCAST...");
        this.mSettings.edit().putBoolean(Preferences.IS_SYNC_IN_ERROR_STATE, true).commit();
        this.mSettings.edit().putBoolean(Preferences.SYNC_IN_PROGRESS, false).commit();
        Intent intent = new Intent();
        intent.setAction(BroadcastKeys.SYNC_FAILED);
        intent.putExtra(Constants.HAVE_BLE, this.mGotBleFromMachine);
        updateSyncState(false, false);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
        this.mGotBleFromMachine = false;
    }

    private void updateSyncState(boolean isSyncSuccessful, boolean newWorkoutsLoaded) {
        try {
            DateTime currentSyncDate = new DateTime();
            Product currentProduct = this.mProductDao.queryBuilder().queryForFirst();
            if (isSyncSuccessful) {
                currentProduct.setmLastSync(currentSyncDate);
                setSuccessfulSyncState(currentProduct, newWorkoutsLoaded);
            } else {
                currentProduct = updateProductStateWhenSyncFailed(currentProduct, currentSyncDate);
            }
            this.mProductDao.update(currentProduct);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Product updateProductStateWhenSyncFailed(Product currentProduct, DateTime currentSyncDate) {
        if (currentProduct == null) {
            return createEmptyProduct(currentSyncDate);
        }
        currentProduct.setmLastSync(currentSyncDate);
        currentProduct.setmLastSyncStatus(SyncStatus.FAILED);
        return currentProduct;
    }

    private Product createEmptyProduct(DateTime currentSyncDate) {
        Product product = new Product();
        product.setmLastSync(currentSyncDate);
        product.setmLastSyncStatus(SyncStatus.FAILED);
        product.setmManufacturer("");
        product.setmConsumerVariant("");
        product.setmFirmwareVersion("");
        product.setmHardwareVariant("");
        product.setmProductModelName("");
        product.setmProductModelNumber("");
        product.setmSoftwareVersion("");
        return product;
    }

    private void setSuccessfulSyncState(Product currentProduct, boolean newWorkoutsLoaded) {
        if (newWorkoutsLoaded) {
            currentProduct.setmLastSyncStatus(SyncStatus.SUCCESSFUL);
        } else {
            currentProduct.setmLastSyncStatus(SyncStatus.SUCCESSFUL_NO_WORKOUTS);
        }
    }

    private void sendDisconnectTreadClimberBroadcast() {
        Intent intent = new Intent();
        intent.setAction(BroadcastKeys.DISCONNECT_OMNI_TRAINER);
        intent.putExtra(Constants.HAVE_BLE, this.mGotBleFromMachine);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onDestroy() {
        Log.d(TAG, "DEBUG --------------------------ON DESTROY SYNC DATA SERVICE");
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(this.successfulBLELockReceiver);
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(this.connectionErrorReceiver);
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(this.errorDialogReceiver);
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(this.bleStatusReceiver);
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(this.omniProductDataReceiver);
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(this.userDataReceiver);
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(this.workoutDataReceiver);
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(this.stopSyncServiceReceiver);
        if (this.mDataBaseHelper != null) {
            OpenHelperManager.releaseHelper();
            this.mDataBaseHelper = null;
        }
        super.onDestroy();
    }
}
