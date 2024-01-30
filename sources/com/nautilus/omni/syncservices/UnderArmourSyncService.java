package com.nautilus.omni.syncservices;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import com.nautilus.omni.app.OmniApplication;
import com.nautilus.omni.app.Preferences;
import com.nautilus.omni.appmodules.settings.underarmour.util.UnderArmourUtil;
import com.nautilus.omni.dataaccess.OmniTrainerDaoHelper;
import com.nautilus.omni.dataaccess.WorkoutDaoHelper;
import com.nautilus.omni.dependencyinjection.components.AppComponent;
import com.nautilus.omni.model.dto.OmniData;
import com.nautilus.omni.model.dto.Workout;
import com.nautilus.omni.util.Util;
import com.nautilus.underarmourconnection.database.WorkoutTrack;
import com.nautilus.underarmourconnection.errorhandling.UnderArmourError;
import com.nautilus.underarmourconnection.services.workouts.WorkoutCallback;
import com.nautilus.underarmourconnection.services.workouts.WorkoutKeys;
import com.nautilus.underarmourconnection.services.workouts.WorkoutService;
import com.nautilus.underarmourconnection.services.workouts.WorkoutUnits;
import java.util.ArrayList;
import java.util.SimpleTimeZone;
import org.json.JSONException;
import org.json.JSONObject;

public class UnderArmourSyncService extends IntentService implements WorkoutCallback {
    private static final String ACTION_SYNC_WITH_UNDER_ARMOUR = "com.nautilus.omni.syncservices.action.SYNC_WITH_UNDER_ARMOUR";
    public static final String TAG = UnderArmourSyncService.class.getSimpleName();
    private OmniTrainerDaoHelper mConsoleDaoHelper;
    private OmniData mConsoleData;
    private SharedPreferences mPreferences;
    private WorkoutService mUnderArmourWorkoutService;
    private WorkoutDaoHelper mWorkoutDaoHelper;
    private int mWorkoutsCounter = 0;
    private ArrayList<Workout> mWorkoutsList;

    public UnderArmourSyncService() {
        super("UnderArmourSyncService");
    }

    public static void startSyncWithUnderArmourAction(Context context) {
        Log.d(TAG, "DEBUG - STARTED UNDER ARMOUR SYNC DATA SERVICE");
        Intent intent = new Intent(context, UnderArmourSyncService.class);
        intent.setAction(ACTION_SYNC_WITH_UNDER_ARMOUR);
        context.startService(intent);
    }

    /* access modifiers changed from: protected */
    public void onHandleIntent(Intent intent) {
        if (intent != null && ACTION_SYNC_WITH_UNDER_ARMOUR.equals(intent.getAction())) {
            handleSyncWorkoutsAction();
        }
    }

    private void handleSyncWorkoutsAction() {
        initDataBaseElements();
        if (this.mConsoleData != null) {
            getWorkoutsToSyncWithUnderArmour();
        } else {
            this.mPreferences.edit().putBoolean(Preferences.IS_UNDER_ARMOUR_SYNC_IN_PROGRESS, false).apply();
        }
    }

    private void initDataBaseElements() {
        AppComponent appComponent = ((OmniApplication) getApplicationContext()).getAppComponent();
        this.mPreferences = appComponent.getSharedPreferences();
        this.mConsoleDaoHelper = appComponent.getOmniTrainerDaoHelper();
        this.mWorkoutDaoHelper = appComponent.getWorkoutDaoHelper();
        this.mUnderArmourWorkoutService = appComponent.getUnderArmourWorkoutService();
        this.mConsoleData = this.mConsoleDaoHelper.getFirstOmniData();
        this.mPreferences.edit().putBoolean(Preferences.IS_UNDER_ARMOUR_SYNC_IN_PROGRESS, true).apply();
    }

    private void getWorkoutsToSyncWithUnderArmour() {
        WorkoutTrack lastSyncedWorkoutTrack = this.mUnderArmourWorkoutService.getLastSyncedWorkoutRecord(this.mConsoleData.getmOmniMachineType().getType(), this.mConsoleData.getmUserNumber());
        if (lastSyncedWorkoutTrack == null) {
            getAllRecordsFromDatabase();
        } else {
            checkIfWorkoutRecordExists(lastSyncedWorkoutTrack);
        }
    }

    private void checkIfWorkoutRecordExists(WorkoutTrack lastSyncedWorkoutTrack) {
        Workout workout = this.mWorkoutDaoHelper.getWorkout(lastSyncedWorkoutTrack);
        if (workout == null) {
            getAllRecordsFromDatabase();
        } else {
            getRecordsHigherThanLastSyncedRecordId(workout.getmRecordId());
        }
    }

    private void getAllRecordsFromDatabase() {
        this.mWorkoutsList = new ArrayList<>(this.mWorkoutDaoHelper.getAllWorkouts(false));
        syncWorkouts(this.mWorkoutsCounter);
    }

    private void getRecordsHigherThanLastSyncedRecordId(int recordId) {
        this.mWorkoutsList = new ArrayList<>(this.mWorkoutDaoHelper.getWorkoutsWithRecordIdHigherThan(recordId, false));
        syncWorkouts(this.mWorkoutsCounter);
    }

    private void syncWorkouts(int position) {
        if (position < this.mWorkoutsList.size()) {
            this.mUnderArmourWorkoutService.addWorkoutToUnderArmour(buildJSONObject(this.mWorkoutsList.get(position)), this);
            return;
        }
        Log.d(TAG, "DONE SYNCING UNDER ARMOUR WORKOUTS...");
        this.mPreferences.edit().putBoolean(Preferences.IS_UNDER_ARMOUR_SYNC_IN_PROGRESS, false).commit();
    }

    private JSONObject buildJSONObject(Workout workout) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(WorkoutKeys.RECORD_ID, workout.getmRecordId());
            jsonObject.put(WorkoutKeys.ORIGINAL_SECOND, workout.getmOriginalSecond());
            jsonObject.put(WorkoutKeys.ORIGINAL_MINUTE, workout.getmOriginalMinute());
            jsonObject.put(WorkoutKeys.ORIGINAL_HOUR, workout.getmOriginalHour());
            jsonObject.put(WorkoutKeys.ORIGINAL_DAY, workout.getmOriginalDay());
            jsonObject.put(WorkoutKeys.ORIGINAL_MONTH, workout.getmOriginalMonth());
            jsonObject.put(WorkoutKeys.ORIGINAL_YEAR, workout.getmOriginalYear());
            jsonObject.put(WorkoutKeys.MACHINE_TYPE, this.mConsoleData.getmOmniMachineType().getType());
            jsonObject.put(WorkoutKeys.CONSOLE_USER_NUMBER, this.mConsoleData.getmUserNumber());
            jsonObject.put(WorkoutKeys.NAME, UnderArmourUtil.getWorkoutName(this.mConsoleData.getmOmniMachineType()));
            jsonObject.put(WorkoutKeys.ACTIVITY_TYPE, UnderArmourUtil.getActivityType(this.mConsoleData.getmOmniMachineType()));
            jsonObject.put(WorkoutKeys.TIME_ZONE, SimpleTimeZone.getDefault());
            jsonObject.put(WorkoutKeys.START_TIME, workout.getmWorkoutDate().getMillis());
            jsonObject.put(WorkoutKeys.ELAPSED_TIME_UNIT, WorkoutUnits.ELAPSED_TIME_SECONDS);
            jsonObject.put(WorkoutKeys.ELAPSED_TIME, Double.valueOf((double) workout.getmElapsedTime()));
            jsonObject.put(WorkoutKeys.SPEED_UNIT, WorkoutUnits.SPEED_MPH);
            jsonObject.put(WorkoutKeys.SPEED_AVG, Util.getWorkoutSpeedInMiles(workout));
            jsonObject.put(WorkoutKeys.DISTANCE_UNIT, WorkoutUnits.DISTANCE_MILES);
            jsonObject.put(WorkoutKeys.DISTANCE, Util.getWorkoutDistanceInMiles(workout));
            jsonObject.put(WorkoutKeys.METABOLIC_ENERGY_TOTAL_UNIT, WorkoutUnits.METABOLIC_ENERGY_CALORIES);
            jsonObject.put(WorkoutKeys.METABOLIC_ENERGY_TOTAL, Double.valueOf((double) workout.getmCalories()));
            jsonObject.put(WorkoutKeys.HEART_RATE_AVG, workout.getmAvgHeartRate());
            jsonObject.put(WorkoutKeys.POWER_AVG, Util.getWorkoutPower(workout));
        } catch (JSONException e) {
            e.printStackTrace();
            this.mPreferences.edit().putBoolean(Preferences.IS_UNDER_ARMOUR_SYNC_IN_PROGRESS, false).commit();
        }
        return jsonObject;
    }

    public void onWorkoutSaveSuccess(String workoutName) {
        Log.d(TAG, "WORKOUT SYNCED WITH UNDER ARMOUR: " + workoutName);
        this.mWorkoutsCounter++;
        syncWorkouts(this.mWorkoutsCounter);
    }

    public void onWorkoutSaveError(UnderArmourError underArmourError) {
        this.mPreferences.edit().putBoolean(Preferences.IS_UNDER_ARMOUR_SYNC_IN_PROGRESS, false).commit();
        Log.d(TAG, "ERROR SYNCING WORKOUT WITH UNDER ARMOUR: " + underArmourError.getErrorRecommendation());
    }
}
