package com.nautilus.omni.dataaccess;

import android.util.Log;
import com.j256.ormlite.dao.Dao;
import com.nautilus.omni.model.dto.FitServicesWorkout;
import java.sql.SQLException;
import java.util.List;

public class GoogleFitDaoHelper {
    public static final String TAG = GoogleFitDaoHelper.class.getSimpleName();
    private final Dao<FitServicesWorkout, Integer> mFitServicesWorkoutDao;

    public GoogleFitDaoHelper(Dao<FitServicesWorkout, Integer> mFitServicesWorkoutDao2) {
        this.mFitServicesWorkoutDao = mFitServicesWorkoutDao2;
    }

    public List<FitServicesWorkout> getFitServicesWorkouts() {
        try {
            return this.mFitServicesWorkoutDao.queryBuilder().where().eq(FitServicesWorkout.IS_SYNCED_GOOGLE_FIT, false).and().isNotNull("workout").query();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void update(FitServicesWorkout fitServicesWorkout) {
        try {
            this.mFitServicesWorkoutDao.update(fitServicesWorkout);
        } catch (SQLException e) {
            Log.d(TAG, "DEBUG - Update table error, google fit workout marked as Not Synced with GF");
            e.printStackTrace();
        }
    }
}
