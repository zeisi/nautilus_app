package com.nautilus.omni.networking.myfitnesspalcommunication;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.android.volley.VolleyError;
import com.nautilus.omni.R;
import com.nautilus.omni.app.Preferences;
import com.nautilus.omni.model.dto.FitServicesWorkout;
import com.nautilus.omni.model.dto.OmniMachineType;
import com.nautilus.omni.syncservices.syncserviceshelpers.FitServicesSyncDataHelper;
import com.nautilus.omni.util.Constants;
import com.nautilus.omni.util.Util;
import java.util.List;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.json.JSONException;
import org.json.JSONObject;

public class MyFitnessPalCommunication {
    public static final String TAG = MyFitnessPalCommunication.class.getSimpleName();
    private Context mContext;
    private FitServicesSyncDataHelper mFitServicesWorkoutDao;
    private OmniMachineType mOmniMachineType;
    /* access modifiers changed from: private */
    public SharedPreferences mSettings = this.mContext.getSharedPreferences(Preferences.OMNI_TRAINER_PREFERENCES, 0);
    private int mWorkoutListCounter;
    private List<FitServicesWorkout> mWorkoutsList;

    public MyFitnessPalCommunication(Context context, List<FitServicesWorkout> workoutsList, FitServicesSyncDataHelper fitServicesWorkoutDao, OmniMachineType omniMachineType) {
        this.mContext = context;
        this.mFitServicesWorkoutDao = fitServicesWorkoutDao;
        this.mWorkoutsList = workoutsList;
        this.mWorkoutListCounter = 0;
        this.mOmniMachineType = omniMachineType;
    }

    public void syncWorkoutsToMyFitnessPal() {
        if (this.mWorkoutsList.size() > this.mWorkoutListCounter) {
            executeSyncService(this.mWorkoutsList.get(this.mWorkoutListCounter), buildMFPWorkoutObject(this.mWorkoutsList.get(this.mWorkoutListCounter)));
            return;
        }
        this.mSettings.edit().putBoolean(Preferences.IS_MFP_SYNC_IN_PROGRESS, false).commit();
    }

    private void executeSyncService(final FitServicesWorkout workout, JSONObject data) {
        new PostMyFitnessPalWorkoutInfoService(this.mContext, new PostMyFitnessPalWorkoutListener() {
            public void onSuccess(String myFitnessPalWorkoutId) {
                MyFitnessPalCommunication.this.executeMFPSuccessfulSyncWorkoutAction(myFitnessPalWorkoutId, workout);
            }

            public void onFailure(VolleyError error) {
                MyFitnessPalCommunication.this.mSettings.edit().putBoolean(Preferences.IS_MFP_SYNC_IN_PROGRESS, false).commit();
                Log.d(MyFitnessPalCommunication.TAG, "DEBUG - an error occured, nex workouts NOT be synced with MFP- " + error.toString());
            }

            public void onParsingObjectFailure(String errorMessage) {
                MyFitnessPalCommunication.this.mSettings.edit().putBoolean(Preferences.IS_MFP_SYNC_IN_PROGRESS, false).commit();
                Log.d(MyFitnessPalCommunication.TAG, "DEBUG - an error occured, nex workouts NOT be synced with MFP- " + errorMessage);
            }
        }).postWorkoutInfoToMyFitnessPal(data);
    }

    /* access modifiers changed from: private */
    public void executeMFPSuccessfulSyncWorkoutAction(String myFitnessPalWorkoutId, FitServicesWorkout workout) {
        if (myFitnessPalWorkoutId.length() > 0) {
            this.mFitServicesWorkoutDao.markWorkoutsAsSyncedWithMyFitnessPal(workout, myFitnessPalWorkoutId);
            this.mWorkoutListCounter++;
            syncWorkoutsToMyFitnessPal();
            Log.d(TAG, "DEBUG - workout synced with MFP");
            return;
        }
        this.mSettings.edit().putBoolean(Preferences.IS_MFP_SYNC_IN_PROGRESS, false).commit();
        Log.d(TAG, "DEBUG - an error occured, new workouts NOT be synced with MFP");
    }

    private JSONObject buildMFPWorkoutObject(FitServicesWorkout FitServiceWorkout) {
        JSONObject data = new JSONObject();
        try {
            data.put("action", MyFitnessPalConstants.MFP_ACTION_VALUE);
            data.put("access_token", this.mSettings.getString(Preferences.MY_FITNESS_PAL_TOKEN, Constants.EMPTY_SPACE));
            data.put(MyFitnessPalConstants.MFP_EXERCISE_ID_KEY, MyFitnessPalConstants.getMFPExerciseID(this.mOmniMachineType));
            data.put(MyFitnessPalConstants.MFP_DURATION_KEY, FitServiceWorkout.getmWorkout().getmElapsedTime() * 1000);
            data.put("energy_expended", FitServiceWorkout.getmWorkout().getmCalories());
            data.put(MyFitnessPalConstants.MFP_AVG_HEART_RATE_KEY, FitServiceWorkout.getmWorkout().getmAvgHeartRate());
            data.put(MyFitnessPalConstants.MFP_START_TIME_KEY, new DateTime(Util.buildWorkoutStartTime(FitServiceWorkout.getmWorkout())).withZone(DateTimeZone.UTC).toString(DateTimeFormat.forPattern(this.mContext.getString(R.string.mfp_date_format))));
            data.put(MyFitnessPalConstants.MFP_STATUS_UPDATE_MSG_KEY, this.mContext.getString(R.string.mfp_workout_synced));
            data.put("units", MyFitnessPalConstants.MFP_UNITS_VALUE);
        } catch (JSONException e) {
            Log.d(TAG, "DEBUG - an error occured when building JSON object, new workouts NOT be synced with MFP");
            this.mSettings.edit().putBoolean(Preferences.IS_MFP_SYNC_IN_PROGRESS, false).commit();
            e.printStackTrace();
        }
        return data;
    }
}
