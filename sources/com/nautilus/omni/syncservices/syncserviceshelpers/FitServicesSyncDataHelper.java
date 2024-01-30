package com.nautilus.omni.syncservices.syncserviceshelpers;

import android.util.Log;
import com.j256.ormlite.dao.Dao;
import com.nautilus.omni.model.dto.FitServicesWorkout;
import com.nautilus.omni.model.dto.Workout;
import java.sql.SQLException;
import java.util.List;

public class FitServicesSyncDataHelper {
    public static final String TAG = FitServicesSyncDataHelper.class.getSimpleName();
    private int mCurrentUserNumber = -1;
    private Dao<FitServicesWorkout, Integer> mFitServicesWorkoutDao;

    public FitServicesSyncDataHelper(Dao<FitServicesWorkout, Integer> fitServicesWorkoutDao) {
        this.mFitServicesWorkoutDao = fitServicesWorkoutDao;
    }

    public void setCurrentUserNumber(int currentUserNumber) {
        this.mCurrentUserNumber = currentUserNumber;
    }

    public void addWorkoutToFitServicesTable(Workout workout) {
        try {
            FitServicesWorkout fitServicesWorkout = this.mFitServicesWorkoutDao.queryBuilder().where().eq("original_second", Integer.valueOf(workout.getmOriginalSecond())).and().eq("original_minute", Integer.valueOf(workout.getmOriginalMinute())).and().eq("original_hour", Integer.valueOf(workout.getmOriginalHour())).and().eq("original_day", Integer.valueOf(workout.getmOriginalDay())).and().eq("original_month", Integer.valueOf(workout.getmOriginalMonth())).and().eq("original_year", Integer.valueOf(workout.getmOriginalYear())).and().eq(FitServicesWorkout.WORKOUT_RECORD_ID, Integer.valueOf(workout.getmRecordId())).and().eq(FitServicesWorkout.OMNI_USER_NUMBER, Integer.valueOf(this.mCurrentUserNumber)).queryForFirst();
            if (fitServicesWorkout != null) {
                updateExistingFitServicesWorkout(fitServicesWorkout, workout);
            } else if (workout.getmCalories() > 0 && workout.getmElapsedTime() > 0) {
                createNewFitServicesWorkout(workout);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createNewFitServicesWorkout(Workout workout) {
        FitServicesWorkout fitServicesWorkout = new FitServicesWorkout();
        fitServicesWorkout.setmWorkoutDate(workout.getmWorkoutDate());
        fitServicesWorkout.setmWorkoutRecordId(workout.getmRecordId());
        fitServicesWorkout.setmOmniTrainerUserNumber(this.mCurrentUserNumber);
        fitServicesWorkout.setmWorkout(workout);
        fitServicesWorkout.setmIsSyncedWithGoogleFit(false);
        fitServicesWorkout.setmIsSyncedWithMyFitnessPal(false);
        fitServicesWorkout.setmMyFitnessPalId((String) null);
        fitServicesWorkout.setmGoogleFitId((String) null);
        fitServicesWorkout.setmOriginalSecond(workout.getmOriginalSecond());
        fitServicesWorkout.setmOriginalMinute(workout.getmOriginalMinute());
        fitServicesWorkout.setmOriginalHour(workout.getmOriginalHour());
        fitServicesWorkout.setmOriginalDay(workout.getmOriginalDay());
        fitServicesWorkout.setmOriginalMonth(workout.getmOriginalMonth());
        fitServicesWorkout.setmOriginalYear(workout.getmOriginalYear());
        try {
            this.mFitServicesWorkoutDao.create(fitServicesWorkout);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateExistingFitServicesWorkout(FitServicesWorkout fitServicesWorkout, Workout workout) {
        fitServicesWorkout.setmWorkout(workout);
        try {
            this.mFitServicesWorkoutDao.update(fitServicesWorkout);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<FitServicesWorkout> getWorkoutsListToSyncWithGoogleFit() throws SQLException {
        return this.mFitServicesWorkoutDao.queryBuilder().orderBy("workout_date", false).where().eq(FitServicesWorkout.IS_SYNCED_GOOGLE_FIT, false).and().isNotNull("workout").query();
    }

    public List<FitServicesWorkout> getWorkoutsListToSyncWithMyFitnessPal() throws SQLException {
        return this.mFitServicesWorkoutDao.queryBuilder().orderBy("workout_date", false).where().eq(FitServicesWorkout.IS_SYNCED_MY_FITNESS_PAL, false).and().isNotNull("workout").query();
    }

    public List<FitServicesWorkout> getWorkoutsListToSyncWithNautilusConnect() throws SQLException {
        return this.mFitServicesWorkoutDao.queryBuilder().orderBy("workout_date", false).where().eq(FitServicesWorkout.IS_SYNCED_NAUTILUS_CONNECT, false).and().isNotNull("workout").query();
    }

    public void markWorkoutsAsSyncedWithGoogleFit(FitServicesWorkout fitServicesWorkout) {
        Log.d(TAG, "DEBUG - MARKING WORKOUTS SYNCED WITH GOOGLE FIT");
        fitServicesWorkout.setmIsSyncedWithGoogleFit(true);
        try {
            this.mFitServicesWorkoutDao.update(fitServicesWorkout);
        } catch (SQLException e) {
            Log.d(TAG, "DEBUG - workout NOT synce with GF, error updating table");
            e.printStackTrace();
        }
    }

    public void markWorkoutsAsSyncedWithMyFitnessPal(FitServicesWorkout fitServicesWorkout, String myFitnessPalWorkoutId) {
        Log.d(TAG, "DEBUG - MARKING WORKOUTS SYNCED WITH MY FITNESS PAL");
        fitServicesWorkout.setmIsSyncedWithMyFitnessPal(true);
        fitServicesWorkout.setmMyFitnessPalId(myFitnessPalWorkoutId);
        try {
            this.mFitServicesWorkoutDao.update(fitServicesWorkout);
        } catch (SQLException e) {
            Log.d(TAG, "DEBUG - workout NOT synced with MFP, error updating table");
            e.printStackTrace();
        }
    }

    public void markWorkoutsAsSyncedWithNautilusConnect(FitServicesWorkout fitServicesWorkout, int nautilusConnectWorkoutId) {
        fitServicesWorkout.setmIsSyncedWithNautilusConnect(true);
        fitServicesWorkout.setmNautilusConnectId(nautilusConnectWorkoutId);
        Log.d(TAG, "DEBUG - MARKING WORKOUTS SYNCED WITH NAUTILUS CONNECT, NEW WORKOUT ID: " + nautilusConnectWorkoutId);
        try {
            this.mFitServicesWorkoutDao.update(fitServicesWorkout);
            Log.d(TAG, "DEBUG - workout synced with Nautilus Connect");
        } catch (SQLException e) {
            Log.d(TAG, "DEBUG - workout NOT synced with Nautilus Connect, error updating table");
            e.printStackTrace();
        }
    }
}
