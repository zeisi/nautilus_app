package com.nautilus.underarmourconnection.services.workouts;

import android.content.Context;
import com.nautilus.underarmourconnection.database.WorkoutTrack;
import com.nautilus.underarmourconnection.database.WorkoutTrackDaoHelper;
import com.nautilus.underarmourconnection.errorhandling.DatabaseException;
import com.nautilus.underarmourconnection.errorhandling.UnderArmourErrorHandler;
import com.nautilus.underarmourconnection.services.underarmour.UnderArmourService;
import com.ua.sdk.CreateCallback;
import com.ua.sdk.FetchCallback;
import com.ua.sdk.UaException;
import com.ua.sdk.activitytype.ActivityType;
import com.ua.sdk.activitytype.ActivityTypeRef;
import com.ua.sdk.privacy.Privacy;
import com.ua.sdk.workout.Workout;
import com.ua.sdk.workout.WorkoutBuilderImpl;
import org.json.JSONException;
import org.json.JSONObject;

public class WorkoutService extends UnderArmourService implements WorkoutServiceInterface {
    ActivityTypeRef mActivityTypeRef;
    private int mConsoleUserNumber;
    private String mMachineType;
    private int mOriginalDay;
    private int mOriginalHour;
    private int mOriginalMinute;
    private int mOriginalMonth;
    private int mOriginalSecond;
    private int mOriginalYear;
    private WorkoutTrackDaoHelper mWorkoutDaoHelper;
    private int mWorkoutRecordId;
    private long mWorkoutSyncDate;

    public WorkoutService(Context context, String clientKey, String clientSecret, WorkoutTrackDaoHelper workoutDaoHelper) {
        super(context, clientKey, clientSecret);
        this.mWorkoutDaoHelper = workoutDaoHelper;
    }

    public WorkoutTrack getLastSyncedWorkoutRecord(String machineType, int consoleUserNumber) {
        return this.mWorkoutDaoHelper.getLastSyncedWorkoutTrack(machineType, consoleUserNumber);
    }

    public void addWorkoutToUnderArmour(JSONObject jsonObject, WorkoutCallback workoutCallback) {
        if (isNetworkAvailable()) {
            sendWorkoutToUnderArmour(jsonObject, workoutCallback);
        } else {
            workoutCallback.onWorkoutSaveError(UnderArmourErrorHandler.getUnderArmourError(this.mContext, new UaException(UaException.Code.NETWORK)));
        }
    }

    private void sendWorkoutToUnderArmour(JSONObject jsonObject, WorkoutCallback workoutCallback) {
        try {
            getWorkoutActivityType(jsonObject, workoutCallback);
        } catch (JSONException jsonException) {
            workoutCallback.onWorkoutSaveError(UnderArmourErrorHandler.getParametersError(this.mContext, jsonException));
        }
    }

    private void getWorkoutActivityType(final JSONObject jsonObject, final WorkoutCallback workoutCallback) throws JSONException {
        this.mUnderArmourInterface.getActivityTypeManager().fetchActivityType(ActivityTypeRef.getBuilder().setActivityTypeId(WorkoutUtil.getStringValue(jsonObject, WorkoutKeys.ACTIVITY_TYPE)).build(), new FetchCallback<ActivityType>() {
            public void onFetched(ActivityType activityType, UaException exception) {
                if (activityType != null) {
                    WorkoutService.this.mActivityTypeRef = activityType.getRef();
                    WorkoutService.this.syncWorkout(jsonObject, workoutCallback);
                    return;
                }
                workoutCallback.onWorkoutSaveError(UnderArmourErrorHandler.getUnderArmourError(WorkoutService.this.mContext, exception));
            }
        });
    }

    /* access modifiers changed from: private */
    public void syncWorkout(JSONObject jsonObject, final WorkoutCallback workoutCallback) {
        try {
            this.mUnderArmourInterface.getWorkoutManager().createWorkout(buildWorkoutFromJson(jsonObject), new CreateCallback<Workout>() {
                public void onCreated(Workout workout, UaException exception) {
                    if (workout != null) {
                        WorkoutService.this.handleWorkoutSyncedSuccess(workoutCallback, workout.getRef().getId(), workout.getName());
                    } else {
                        workoutCallback.onWorkoutSaveError(UnderArmourErrorHandler.getUnderArmourError(WorkoutService.this.mContext, exception));
                    }
                }
            });
        } catch (JSONException jsonException) {
            workoutCallback.onWorkoutSaveError(UnderArmourErrorHandler.getParametersError(this.mContext, jsonException));
        } catch (UaException UaException) {
            workoutCallback.onWorkoutSaveError(UnderArmourErrorHandler.getUnderArmourError(this.mContext, UaException));
        } catch (DatabaseException databaseException) {
            workoutCallback.onWorkoutSaveError(UnderArmourErrorHandler.getDatabaseError(this.mContext, databaseException.getMessage()));
        }
    }

    /* access modifiers changed from: private */
    public void handleWorkoutSyncedSuccess(WorkoutCallback workoutCallback, String workoutUnderArmourId, String workoutName) {
        if (saveWorkoutInDatabase(workoutUnderArmourId)) {
            workoutCallback.onWorkoutSaveSuccess(workoutName);
        } else {
            workoutCallback.onWorkoutSaveError(UnderArmourErrorHandler.getDatabaseError(this.mContext, UnderArmourErrorHandler.ERROR_SAVING_WORKOUT));
        }
    }

    private Workout buildWorkoutFromJson(JSONObject jsonObject) throws JSONException, UaException, DatabaseException {
        getWorkoutIdInfo(jsonObject);
        WorkoutBuilderImpl workoutBuilder = new WorkoutBuilderImpl();
        workoutBuilder.setPrivacy(Privacy.Level.PUBLIC);
        workoutBuilder.setActivityType(this.mActivityTypeRef);
        workoutBuilder.setName(WorkoutUtil.getStringValue(jsonObject, WorkoutKeys.NAME));
        workoutBuilder.setTimeZone(WorkoutUtil.getTimeZone(jsonObject));
        workoutBuilder.setStartTime(WorkoutUtil.getStartTime(jsonObject));
        workoutBuilder.setCreateTime(WorkoutUtil.getCreateTime());
        Double elapsedTime = WorkoutUtil.getElapsedTimePreparedForUnderArmourApi(jsonObject);
        workoutBuilder.setTotalTime(elapsedTime, elapsedTime);
        Double speed = WorkoutUtil.getSpeedPreparedForUnderArmourApi(jsonObject);
        workoutBuilder.setSpeedAggregates(speed, speed, speed);
        workoutBuilder.setTotalDistance(WorkoutUtil.getDistancePreparedForUnderArmourApi(jsonObject));
        workoutBuilder.setMetabolicEnergyTotal(WorkoutUtil.getMetabolicEnergyPreparedForUnderArmourApi(jsonObject));
        Integer heartRate = WorkoutUtil.getAvgHeartRate(jsonObject);
        workoutBuilder.setHeartRateAggregates(heartRate, heartRate, heartRate);
        Double powerAvg = WorkoutUtil.getAvgPower(jsonObject);
        workoutBuilder.setPowerAggregates(powerAvg, powerAvg, powerAvg);
        return workoutBuilder.build();
    }

    private void getWorkoutIdInfo(JSONObject jsonObject) throws JSONException, DatabaseException {
        this.mWorkoutRecordId = WorkoutUtil.getIntegerValue(jsonObject, WorkoutKeys.RECORD_ID).intValue();
        this.mMachineType = WorkoutUtil.getStringValue(jsonObject, WorkoutKeys.MACHINE_TYPE);
        this.mConsoleUserNumber = WorkoutUtil.getIntegerValue(jsonObject, WorkoutKeys.CONSOLE_USER_NUMBER).intValue();
        this.mOriginalSecond = WorkoutUtil.getIntegerValue(jsonObject, WorkoutKeys.ORIGINAL_SECOND).intValue();
        this.mOriginalMinute = WorkoutUtil.getIntegerValue(jsonObject, WorkoutKeys.ORIGINAL_MINUTE).intValue();
        this.mOriginalHour = WorkoutUtil.getIntegerValue(jsonObject, WorkoutKeys.ORIGINAL_HOUR).intValue();
        this.mOriginalDay = WorkoutUtil.getIntegerValue(jsonObject, WorkoutKeys.ORIGINAL_DAY).intValue();
        this.mOriginalMonth = WorkoutUtil.getIntegerValue(jsonObject, WorkoutKeys.ORIGINAL_MONTH).intValue();
        this.mOriginalYear = WorkoutUtil.getIntegerValue(jsonObject, WorkoutKeys.ORIGINAL_YEAR).intValue();
        this.mWorkoutSyncDate = WorkoutUtil.getLongValue(jsonObject, WorkoutKeys.START_TIME).longValue();
        if (checkIfWorkoutAlreadyExists()) {
            throw new DatabaseException(UnderArmourErrorHandler.WORKOUT_ALREADY_EXISTS);
        }
    }

    private boolean checkIfWorkoutAlreadyExists() {
        return this.mWorkoutDaoHelper.isWorkoutInDatabase(this.mWorkoutRecordId, this.mMachineType, this.mConsoleUserNumber, this.mOriginalSecond, this.mOriginalMinute, this.mOriginalHour, this.mOriginalDay, this.mOriginalMonth, this.mOriginalYear);
    }

    private boolean saveWorkoutInDatabase(String workoutUnderArmourId) {
        return this.mWorkoutDaoHelper.createWorkout(workoutUnderArmourId, this.mWorkoutRecordId, this.mMachineType, this.mConsoleUserNumber, this.mWorkoutSyncDate, this.mOriginalSecond, this.mOriginalMinute, this.mOriginalHour, this.mOriginalDay, this.mOriginalMonth, this.mOriginalYear);
    }
}
