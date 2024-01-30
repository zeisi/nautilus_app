package com.nautilus.omni.databasemanager;

import android.content.SharedPreferences;
import com.j256.ormlite.dao.Dao;
import com.nautilus.omni.model.dto.FitServicesWorkout;
import com.nautilus.omni.model.dto.User;
import com.nautilus.omni.model.dto.Week;
import com.nautilus.omni.model.dto.Workout;

public class DatabaseUpdater {
    private Dao<FitServicesWorkout, Integer> mFitServicesWorkoutDao;
    private SharedPreferences mSettings;
    private Dao<User, Integer> mUserDao;
    private Dao<Week, Integer> mWeekDao;
    private Dao<Workout, Integer> mWorkoutDao;

    public DatabaseUpdater(SharedPreferences settings, Dao<User, Integer> userDao, Dao<Workout, Integer> workoutDao, Dao<FitServicesWorkout, Integer> fitServicesWorkoutDao, Dao<Week, Integer> weekDao) {
        this.mSettings = settings;
        this.mUserDao = userDao;
        this.mWorkoutDao = workoutDao;
        this.mFitServicesWorkoutDao = fitServicesWorkoutDao;
        this.mWeekDao = weekDao;
    }

    public void updateDatabase() {
        addWattsToWeekTable();
    }

    private void addWattsToWeekTable() {
        new DataBaseMigrationVersion2(this.mWeekDao).migrateDataToDatabaseVersion2();
    }
}
