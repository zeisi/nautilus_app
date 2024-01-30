package com.nautilus.omni.syncservices;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import com.nautilus.omni.app.OmniApplication;
import com.nautilus.omni.app.Preferences;
import com.nautilus.omni.appmodules.settings.googlefit.googlefitcommunication.GoogleFitHelper;
import com.nautilus.omni.dataaccess.OmniTrainerDaoHelper;
import com.nautilus.omni.dependencyinjection.components.AppComponent;
import com.nautilus.omni.model.dto.FitServicesWorkout;
import com.nautilus.omni.model.dto.OmniData;
import com.nautilus.omni.networking.myfitnesspalcommunication.MyFitnessPalCommunication;
import com.nautilus.omni.syncservices.syncserviceshelpers.FitServicesSyncDataHelper;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FitAppsSyncDataService extends IntentService {
    private static final String ACTION_SYNC_WITH_GOOGLE_FIT = "com.nautilus.omni.syncservices.action.SYNCGOOGLEFIT";
    private static final String ACTION_SYNC_WITH_MY_FITNESS_PAL = "com.nautilus.omni.syncservices.action.SYNCMYFITNESSPAL";
    public static final String TAG = FitAppsSyncDataService.class.getSimpleName();
    private FitServicesSyncDataHelper mFitServicesSyncDataHelper;
    private GoogleFitHelper mGoogleFitHelper;
    private OmniTrainerDaoHelper mOmniTrainerDaoHelper;
    private SharedPreferences mSettings;
    private List<FitServicesWorkout> mWorkoutsToSyncWithGoogleFit;
    private List<FitServicesWorkout> mWorkoutsToSyncWithMFP;

    public static void startSyncWithGoogleFitAction(Context context) {
        Log.d(TAG, "DEBUG - STARTED GOOGLE FIT SYNC DATA SERVICE");
        Intent intent = new Intent(context, FitAppsSyncDataService.class);
        intent.setAction(ACTION_SYNC_WITH_GOOGLE_FIT);
        context.startService(intent);
    }

    public static void startSyncWithMyFitnessPalAction(Context context) {
        Log.d(TAG, "DEBUG - STARTED MFP FIT SYNC DATA SERVICE");
        Intent intent = new Intent(context, FitAppsSyncDataService.class);
        intent.setAction(ACTION_SYNC_WITH_MY_FITNESS_PAL);
        context.startService(intent);
    }

    public FitAppsSyncDataService() {
        super(TAG);
    }

    /* access modifiers changed from: protected */
    public void onHandleIntent(Intent intent) {
        if (intent != null) {
            handleIntentAction(intent);
        }
    }

    private void handleIntentAction(Intent intent) {
        String action = intent.getAction();
        if (ACTION_SYNC_WITH_GOOGLE_FIT.equals(action)) {
            executeSyncWithGoogleFit();
        } else if (ACTION_SYNC_WITH_MY_FITNESS_PAL.equals(action)) {
            executeSyncWithMyFitnessPal();
        }
    }

    private void executeSyncWithGoogleFit() {
        this.mSettings = getSharedPreferences(Preferences.OMNI_TRAINER_PREFERENCES, 0);
        initDataBaseElements();
        getWorkoutsListToSyncWithGoogleFit();
        handleSyncGoogleFitAction();
    }

    private void executeSyncWithMyFitnessPal() {
        this.mSettings = getSharedPreferences(Preferences.OMNI_TRAINER_PREFERENCES, 0);
        initDataBaseElements();
        getWorkoutsListToSyncWithMyFitnessPal();
        handleSyncMyFitnessPalAction();
    }

    private void initDataBaseElements() {
        AppComponent appComponent = ((OmniApplication) getApplicationContext()).getAppComponent();
        this.mOmniTrainerDaoHelper = appComponent.getOmniTrainerDaoHelper();
        this.mFitServicesSyncDataHelper = appComponent.getFitServicesDataHelper();
        this.mGoogleFitHelper = appComponent.getGoogleFitHelper();
    }

    private void getWorkoutsListToSyncWithGoogleFit() {
        try {
            this.mWorkoutsToSyncWithGoogleFit = this.mFitServicesSyncDataHelper.getWorkoutsListToSyncWithGoogleFit();
        } catch (SQLException e) {
            this.mSettings.edit().putBoolean(Preferences.IS_GOOGLE_FIT_SYNC_IN_PROGRESS, false).commit();
            this.mWorkoutsToSyncWithGoogleFit = new ArrayList();
            e.printStackTrace();
        }
    }

    private void getWorkoutsListToSyncWithMyFitnessPal() {
        try {
            this.mWorkoutsToSyncWithMFP = this.mFitServicesSyncDataHelper.getWorkoutsListToSyncWithMyFitnessPal();
        } catch (SQLException e) {
            this.mSettings.edit().putBoolean(Preferences.IS_MFP_SYNC_IN_PROGRESS, false).commit();
            this.mWorkoutsToSyncWithMFP = new ArrayList();
            e.printStackTrace();
        }
    }

    private void handleSyncGoogleFitAction() {
        if (this.mWorkoutsToSyncWithGoogleFit == null || this.mWorkoutsToSyncWithGoogleFit.size() <= 0) {
            this.mSettings.edit().putBoolean(Preferences.IS_GOOGLE_FIT_SYNC_IN_PROGRESS, false).commit();
            return;
        }
        Log.d(TAG, "DEBUG - SENDING NEW WORKOUTS TO GOOGLE FIT");
        OmniData omniData = this.mOmniTrainerDaoHelper.getFirstOmniData();
        this.mGoogleFitHelper.setFitServicesSyncDataHelper(this.mFitServicesSyncDataHelper);
        this.mGoogleFitHelper.setOmniData(omniData);
        this.mGoogleFitHelper.syncWorkoutDataWithGoogleFit(this.mWorkoutsToSyncWithGoogleFit);
        this.mSettings.edit().putBoolean(Preferences.IS_GOOGLE_FIT_SYNC_IN_PROGRESS, false).commit();
    }

    private void handleSyncMyFitnessPalAction() {
        if (this.mWorkoutsToSyncWithMFP == null || this.mWorkoutsToSyncWithMFP.size() <= 0) {
            this.mSettings.edit().putBoolean(Preferences.IS_MFP_SYNC_IN_PROGRESS, false).commit();
            return;
        }
        Log.d(TAG, "DEBUG - SENDING NEW WORKOUTS TO MYFITNESSPAL FIT");
        new MyFitnessPalCommunication(getApplicationContext(), this.mWorkoutsToSyncWithMFP, this.mFitServicesSyncDataHelper, this.mOmniTrainerDaoHelper.getFirstOmniData().getmOmniMachineType()).syncWorkoutsToMyFitnessPal();
    }
}
