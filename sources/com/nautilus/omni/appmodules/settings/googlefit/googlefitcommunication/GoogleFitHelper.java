package com.nautilus.omni.appmodules.settings.googlefit.googlefitcommunication;

import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessActivities;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.data.Session;
import com.google.android.gms.fitness.request.SessionInsertRequest;
import com.nautilus.omni.R;
import com.nautilus.omni.app.Preferences;
import com.nautilus.omni.appmodules.settings.googlefit.googlefitcommunication.GoogleFitHelperContract;
import com.nautilus.omni.model.dto.FitServicesWorkout;
import com.nautilus.omni.model.dto.OmniData;
import com.nautilus.omni.model.dto.OmniMachineType;
import com.nautilus.omni.model.dto.Workout;
import com.nautilus.omni.syncservices.syncserviceshelpers.FitServicesSyncDataHelper;
import com.nautilus.omni.util.Util;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class GoogleFitHelper implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    public static final String AUTH_PENDING = "auth_state_pending";
    public static final int REQUEST_OAUTH = 1;
    public static final String TAG = GoogleFitHelper.class.getName();
    private Activity mActivity;
    /* access modifiers changed from: private */
    public final GoogleApiClient mApiClient;
    private boolean mAuthInProgress = false;
    /* access modifiers changed from: private */
    public Context mContext;
    /* access modifiers changed from: private */
    public FitServicesSyncDataHelper mFitServicesSyncDataHelper;
    private OmniData mOmniData;
    /* access modifiers changed from: private */
    public GoogleFitHelperContract.OnUserFitnessServiceConnect mOnUserFitnessServiceConnect;
    SharedPreferences mSharedPreferences;
    /* access modifiers changed from: private */
    public List<FitServicesWorkout> mWorkoutsToSyncList;

    public GoogleFitHelper(Context context) {
        this.mContext = context;
        this.mApiClient = new GoogleApiClient.Builder(context).addApi(Fitness.SENSORS_API).addApi(Fitness.RECORDING_API).addApi(Fitness.HISTORY_API).addApi(Fitness.SESSIONS_API).addApi(Fitness.CONFIG_API).addScope(new Scope(Scopes.FITNESS_ACTIVITY_READ_WRITE)).addScope(new Scope(Scopes.FITNESS_BODY_READ_WRITE)).addScope(new Scope(Scopes.FITNESS_LOCATION_READ_WRITE)).addConnectionCallbacks(this).addOnConnectionFailedListener(this).build();
    }

    public void initSharedPreferences() {
        this.mSharedPreferences = this.mContext.getSharedPreferences(Preferences.OMNI_TRAINER_PREFERENCES, 0);
    }

    public void setFitServicesSyncDataHelper(FitServicesSyncDataHelper mFitServicesSyncDataHelper2) {
        this.mFitServicesSyncDataHelper = mFitServicesSyncDataHelper2;
    }

    public void setAuthInProgress(boolean mAuthInProgress2) {
        this.mAuthInProgress = mAuthInProgress2;
    }

    public void setOnUserFitnessServiceConnect(GoogleFitHelperContract.OnUserFitnessServiceConnect mOnUserFitnessServiceConnect2) {
        this.mOnUserFitnessServiceConnect = mOnUserFitnessServiceConnect2;
    }

    public void setOmniData(OmniData mOmniData2) {
        this.mOmniData = mOmniData2;
    }

    public void connectWithGoogleFit(Activity activity) {
        this.mActivity = activity;
        this.mApiClient.connect();
    }

    public void disconnectFitnessService() {
        if (this.mApiClient.isConnected()) {
            Fitness.ConfigApi.disableFit(this.mApiClient).setResultCallback(new ResultCallback<Status>() {
                public void onResult(@NonNull Status status) {
                    if (status.isSuccess()) {
                        Log.i(GoogleFitHelper.TAG, "DEBUG - (GoogleFit) Disconnected");
                        GoogleFitHelper.this.mOnUserFitnessServiceConnect.OnDisconnectedSuccess();
                        GoogleFitHelper.this.mApiClient.disconnect();
                        return;
                    }
                    GoogleFitHelper.this.mOnUserFitnessServiceConnect.OnDisconnectionError();
                }
            });
        }
    }

    public void onConnected(@Nullable Bundle bundle) {
        Log.d(TAG, "DEBUG - (GoogleFit)Success Connection");
        if (this.mOnUserFitnessServiceConnect != null) {
            this.mOnUserFitnessServiceConnect.OnConnectionSuccessListener();
        }
    }

    public void onConnectionSuspended(int i) {
        if (i == 2) {
            Log.d(TAG, "DEBUG - (GoogleFit)Connection lost.  Cause: Network Lost.");
        } else if (i == 1) {
            Log.d(TAG, "DEBUG - (GoogleFit)Connection lost.  Reason: Service Disconnected");
        }
    }

    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        checkStatus(connectionResult);
    }

    private void checkStatus(@NonNull ConnectionResult connectionResult) {
        if (!this.mAuthInProgress) {
            sendResponse(connectionResult);
        } else {
            Log.d("GoogleFit", "mAuthInProgress");
        }
    }

    private void sendResponse(@NonNull ConnectionResult connectionResult) {
        try {
            this.mAuthInProgress = true;
            if (this.mActivity == null) {
                failConnection(connectionResult.getErrorCode());
            } else if (connectionResult.getErrorCode() == 4) {
                connectionResult.startResolutionForResult(this.mActivity, 1);
            } else {
                failConnection(connectionResult.getErrorCode());
            }
        } catch (IntentSender.SendIntentException e) {
            Log.e("GoogleFit", "IntentSender Error" + e.getMessage());
        }
    }

    private void failConnection(int errorCode) {
        this.mAuthInProgress = false;
        if (this.mOnUserFitnessServiceConnect != null) {
            this.mOnUserFitnessServiceConnect.OnConnectionFailedListener(errorCode, 1);
        }
    }

    public void googleFitIsConnected(Activity activity) {
        this.mActivity = activity;
        if (!this.mApiClient.isConnecting() && !this.mApiClient.isConnected()) {
            this.mApiClient.connect();
        }
    }

    public void syncWorkoutDataWithGoogleFit(List<FitServicesWorkout> workoutsList) {
        this.mWorkoutsToSyncList = workoutsList;
        new InsertTask().execute(new Void[0]);
    }

    public boolean isConnected() {
        return this.mApiClient.isConnected();
    }

    private class InsertTask extends AsyncTask<Void, Void, Void> {
        private InsertTask() {
        }

        /* access modifiers changed from: protected */
        public Void doInBackground(Void... params) {
            String fitnessActivites = GoogleFitHelper.this.getFitnessActivities();
            for (FitServicesWorkout fitServicesWorkout : GoogleFitHelper.this.mWorkoutsToSyncList) {
                try {
                    long endTime = GoogleFitHelper.this.buildWorkoutEndTime(fitServicesWorkout.getmWorkout());
                    long startTime = GoogleFitHelper.this.buildWorkoutStartTime(endTime, fitServicesWorkout.getmWorkout());
                    if (endTime > startTime) {
                        DataSet dataSetCalories = GoogleFitHelper.this.insertFitnessData(startTime, endTime, DataType.TYPE_CALORIES_EXPENDED, 0, Field.FIELD_CALORIES, fitServicesWorkout.getmWorkout().getmCalories());
                        DataSet dataSetHRA = GoogleFitHelper.this.insertFitnessData(startTime, endTime, DataType.TYPE_HEART_RATE_BPM, 0, Field.FIELD_BPM, fitServicesWorkout.getmWorkout().getmAvgHeartRate());
                        DataSet dataSetRPM = GoogleFitHelper.this.insertFitnessData(startTime, endTime, DataType.TYPE_CYCLING_WHEEL_RPM, 0, Field.FIELD_RPM, fitServicesWorkout.getmWorkout().getmAvgRPM());
                        DataSet dataSetDistance = GoogleFitHelper.this.insertFitnessData(startTime, endTime, DataType.TYPE_DISTANCE_DELTA, 0, Field.FIELD_DISTANCE, Util.getWorkoutDistanceInMeters(fitServicesWorkout.getmWorkout()));
                        SessionInsertRequest.Builder insertRequestBuilder = new SessionInsertRequest.Builder().setSession(GoogleFitHelper.this.createSession(startTime, endTime, fitnessActivites));
                        if (GoogleFitHelper.this.mSharedPreferences.getBoolean(Preferences.GOOGLE_FIT_CALORIES, false)) {
                            insertRequestBuilder.addDataSet(dataSetCalories);
                        }
                        if (GoogleFitHelper.this.mSharedPreferences.getBoolean(Preferences.GOOGLE_FIT_AVG_HEART_RATE, false)) {
                            insertRequestBuilder.addDataSet(dataSetHRA);
                        }
                        if (GoogleFitHelper.this.mSharedPreferences.getBoolean(Preferences.GOOGLE_FIT_RPM, false)) {
                            insertRequestBuilder.addDataSet(dataSetRPM);
                        }
                        if (GoogleFitHelper.this.mSharedPreferences.getBoolean(Preferences.GOOGLE_FIT_DISTANCE, false)) {
                            insertRequestBuilder.addDataSet(dataSetDistance);
                        }
                        SessionInsertRequest insertRequest = insertRequestBuilder.build();
                        Log.i(GoogleFitHelper.TAG, "Inserting the dataset in the History API");
                        try {
                            Log.i(GoogleFitHelper.TAG, "Inserting the session in the History API");
                            Status insertStatus = Fitness.SessionsApi.insertSession(GoogleFitHelper.this.mApiClient, insertRequest).await(1, TimeUnit.MINUTES);
                            if (!insertStatus.isSuccess()) {
                                Log.i(GoogleFitHelper.TAG, "There was a problem inserting the session: " + insertStatus.getStatusMessage());
                                return null;
                            }
                            Log.i(GoogleFitHelper.TAG, "Session insert was successful!");
                            GoogleFitHelper.this.mFitServicesSyncDataHelper.markWorkoutsAsSyncedWithGoogleFit(fitServicesWorkout);
                            if (GoogleFitHelper.this.mContext instanceof Activity) {
                                ((Activity) GoogleFitHelper.this.mContext).finish();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        continue;
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            return null;
        }
    }

    /* access modifiers changed from: private */
    public long buildWorkoutEndTime(Workout workout) {
        return workout.getmFinishTime().getMillis();
    }

    /* access modifiers changed from: private */
    public long buildWorkoutStartTime(long endTimeInMillis, Workout workout) {
        return endTimeInMillis - ((long) (workout.getmElapsedTime() * 1000));
    }

    /* access modifiers changed from: private */
    public Session createSession(long startTime, long endTime, String fitnessActivities) {
        return new Session.Builder().setName(this.mContext.getString(R.string.google_fit_session_name)).setDescription(this.mContext.getString(R.string.google_fit_session_description)).setIdentifier(UUID.randomUUID().toString().replaceAll("-", "")).setActivity(fitnessActivities).setStartTime(startTime, TimeUnit.MILLISECONDS).setEndTime(endTime, TimeUnit.MILLISECONDS).build();
    }

    /* access modifiers changed from: private */
    public String getFitnessActivities() {
        if (this.mOmniData == null) {
            return FitnessActivities.WALKING_TREADMILL;
        }
        OmniMachineType omniMachineType = this.mOmniData.getmOmniMachineType();
        if (omniMachineType.equals(OmniMachineType.MY14_BIKE_ELLIPTICAL) || omniMachineType.equals(OmniMachineType.MY16_BIKE_ELLIPTICAL) || omniMachineType.equals(OmniMachineType.ELLIPTICAL_E628_INTERNATIONAL) || omniMachineType.equals(OmniMachineType.MY17_ELLIPTICAL_E618) || omniMachineType.equals(OmniMachineType.MY17_ELLIPTICAL_E616)) {
            return FitnessActivities.ELLIPTICAL;
        }
        if (omniMachineType.equals(OmniMachineType.MY14_TREADMILL) || omniMachineType.equals(OmniMachineType.MY16_TREADMILL) || omniMachineType.equals(OmniMachineType.MY16_INTERNATIONAL_TREADMILL) || omniMachineType.equals(OmniMachineType.MY17_TREADMILL_T618) || omniMachineType.equals(OmniMachineType.MY17_TREADMILL_T616)) {
            return FitnessActivities.WALKING_TREADMILL;
        }
        return FitnessActivities.BIKING;
    }

    /* access modifiers changed from: private */
    public DataSet insertFitnessData(long startTime, long endTime, DataType dataType, int type, Field field, int value) {
        Log.i(TAG, "Creating a new data insert request");
        DataSet dataSet = DataSet.create(new DataSource.Builder().setAppPackageName(this.mContext).setDataType(dataType).setName(TAG + " - step count").setType(type).build());
        DataPoint dataPoint = dataSet.createDataPoint().setTimeInterval(startTime, endTime, TimeUnit.MILLISECONDS);
        dataPoint.getValue(field).setFloat((float) value);
        dataSet.add(dataPoint);
        return dataSet;
    }

    /* access modifiers changed from: private */
    public DataSet insertFitnessData(long startTime, long endTime, DataType dataType, int type, Field field, float value) {
        Log.i(TAG, "Creating a new data insert request");
        DataSet dataSet = DataSet.create(new DataSource.Builder().setAppPackageName(this.mContext).setDataType(dataType).setName(TAG + " - step count").setType(type).build());
        DataPoint dataPoint = dataSet.createDataPoint().setTimeInterval(startTime, endTime, TimeUnit.MILLISECONDS);
        dataPoint.getValue(field).setFloat(value);
        dataSet.add(dataPoint);
        return dataSet;
    }
}
