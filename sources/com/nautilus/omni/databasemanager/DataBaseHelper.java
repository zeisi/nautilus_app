package com.nautilus.omni.databasemanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.nautilus.omni.app.Preferences;
import com.nautilus.omni.model.dto.Achievement;
import com.nautilus.omni.model.dto.Award;
import com.nautilus.omni.model.dto.AwardType;
import com.nautilus.omni.model.dto.FitServicesWorkout;
import com.nautilus.omni.model.dto.OmniData;
import com.nautilus.omni.model.dto.Product;
import com.nautilus.omni.model.dto.TrainingGoal;
import com.nautilus.omni.model.dto.User;
import com.nautilus.omni.model.dto.Week;
import com.nautilus.omni.model.dto.Workout;
import java.sql.SQLException;

public class DataBaseHelper extends OrmLiteSqliteOpenHelper {
    public static final String DATABASE_NAME = "omniDB.db";
    public static final int DATABASE_VERSION = 2;
    private static final String TAG = "DBHelper";
    private static DataBaseHelper mDatabaseHelper;
    private Dao<Achievement, Integer> mAchievementDao = null;
    private Dao<AwardType, Integer> mAwardTypeDao = null;
    private Dao<Award, Integer> mAwardsDao = null;
    private Context mContext;
    private Dao<FitServicesWorkout, Integer> mFitServicesWorkoutDao = null;
    private Dao<OmniData, Integer> mOmniDao = null;
    private Dao<Product, Integer> mProductDao = null;
    private SharedPreferences mSettings;
    private Dao<TrainingGoal, Integer> mTrainingGoalsDao = null;
    private Dao<User, Integer> mUserDao = null;
    private Dao<Week, Integer> mWeek = null;
    private Dao<Workout, Integer> mWorkoutDao = null;

    private DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, (SQLiteDatabase.CursorFactory) null, 2);
        this.mContext = context;
        Context context2 = this.mContext;
        Context context3 = this.mContext;
        this.mSettings = context2.getSharedPreferences(Preferences.OMNI_TRAINER_PREFERENCES, 0);
    }

    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Award.class);
            TableUtils.createTable(connectionSource, AwardType.class);
            TableUtils.createTable(connectionSource, TrainingGoal.class);
            TableUtils.createTable(connectionSource, User.class);
            TableUtils.createTable(connectionSource, Workout.class);
            TableUtils.createTable(connectionSource, Achievement.class);
            TableUtils.createTable(connectionSource, OmniData.class);
            TableUtils.createTable(connectionSource, Product.class);
            TableUtils.createTable(connectionSource, Week.class);
            TableUtils.createTable(connectionSource, FitServicesWorkout.class);
        } catch (SQLException e) {
            Log.e(TAG, "onCreate error", e);
        }
    }

    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i2) {
        try {
            new DatabaseUpdater(this.mSettings, getUserDao(), getWorkoutDao(), getFitServicesWorkoutDao(), getWeekDao()).updateDatabase();
        } catch (SQLException e) {
            Log.e(TAG, "onUpgrade error", e);
        }
    }

    public void dropDatabase() {
        this.mContext.deleteDatabase(DATABASE_NAME);
    }

    public void dropDatabaseData() {
        try {
            TableUtils.dropTable((ConnectionSource) this.connectionSource, Award.class, true);
            TableUtils.dropTable((ConnectionSource) this.connectionSource, TrainingGoal.class, true);
            TableUtils.dropTable((ConnectionSource) this.connectionSource, User.class, true);
            TableUtils.dropTable((ConnectionSource) this.connectionSource, Workout.class, true);
            TableUtils.dropTable((ConnectionSource) this.connectionSource, Achievement.class, true);
            TableUtils.dropTable((ConnectionSource) this.connectionSource, OmniData.class, true);
            TableUtils.dropTable((ConnectionSource) this.connectionSource, Product.class, true);
            TableUtils.dropTable((ConnectionSource) this.connectionSource, Week.class, true);
            TableUtils.createTableIfNotExists((ConnectionSource) this.connectionSource, Award.class);
            TableUtils.createTableIfNotExists((ConnectionSource) this.connectionSource, TrainingGoal.class);
            TableUtils.createTableIfNotExists((ConnectionSource) this.connectionSource, User.class);
            TableUtils.createTableIfNotExists((ConnectionSource) this.connectionSource, Workout.class);
            TableUtils.createTableIfNotExists((ConnectionSource) this.connectionSource, Achievement.class);
            TableUtils.createTableIfNotExists((ConnectionSource) this.connectionSource, OmniData.class);
            TableUtils.createTableIfNotExists((ConnectionSource) this.connectionSource, Product.class);
            TableUtils.createTableIfNotExists((ConnectionSource) this.connectionSource, Week.class);
            dropWorkoutsOnFitServicesTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void resetTablesForInconsistentDateWorkouts() {
        try {
            TableUtils.dropTable((ConnectionSource) this.connectionSource, Award.class, true);
            TableUtils.dropTable((ConnectionSource) this.connectionSource, Achievement.class, true);
            TableUtils.dropTable((ConnectionSource) this.connectionSource, Week.class, true);
            TableUtils.createTableIfNotExists((ConnectionSource) this.connectionSource, Award.class);
            TableUtils.createTableIfNotExists((ConnectionSource) this.connectionSource, Achievement.class);
            TableUtils.createTableIfNotExists((ConnectionSource) this.connectionSource, Week.class);
            dropWorkoutsOnFitServicesTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void dropWorkoutsOnFitServicesTable() {
        try {
            for (FitServicesWorkout fitServicesWorkout : getFitServicesWorkoutDao().queryForAll()) {
                fitServicesWorkout.setmWorkout((Workout) null);
                this.mFitServicesWorkoutDao.update(fitServicesWorkout);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void resetWorkouts() {
        try {
            TableUtils.dropTable((ConnectionSource) this.connectionSource, Workout.class, true);
            TableUtils.createTableIfNotExists((ConnectionSource) this.connectionSource, Workout.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Dao<Award, Integer> getAwardsDao() throws SQLException {
        if (this.mAwardsDao == null) {
            this.mAwardsDao = getDao(Award.class);
        }
        return this.mAwardsDao;
    }

    public Dao<AwardType, Integer> getAwardTypeDao() throws SQLException {
        if (this.mAwardTypeDao == null) {
            this.mAwardTypeDao = getDao(AwardType.class);
        }
        return this.mAwardTypeDao;
    }

    public Dao<TrainingGoal, Integer> getTrainingGoalsDao() throws SQLException {
        if (this.mTrainingGoalsDao == null) {
            this.mTrainingGoalsDao = getDao(TrainingGoal.class);
        }
        return this.mTrainingGoalsDao;
    }

    public Dao<User, Integer> getUserDao() throws SQLException {
        if (this.mUserDao == null) {
            this.mUserDao = getDao(User.class);
        }
        return this.mUserDao;
    }

    public Dao<Workout, Integer> getWorkoutDao() throws SQLException {
        if (this.mWorkoutDao == null) {
            this.mWorkoutDao = getDao(Workout.class);
        }
        return this.mWorkoutDao;
    }

    public Dao<Achievement, Integer> getAchievementDao() throws SQLException {
        if (this.mAchievementDao == null) {
            this.mAchievementDao = getDao(Achievement.class);
        }
        return this.mAchievementDao;
    }

    public Dao<OmniData, Integer> getOmniDao() throws SQLException {
        if (this.mOmniDao == null) {
            this.mOmniDao = getDao(OmniData.class);
        }
        return this.mOmniDao;
    }

    public Dao<Product, Integer> getProductDao() throws SQLException {
        if (this.mProductDao == null) {
            this.mProductDao = getDao(Product.class);
        }
        return this.mProductDao;
    }

    public Dao<Week, Integer> getWeekDao() throws SQLException {
        if (this.mWeek == null) {
            this.mWeek = getDao(Week.class);
        }
        return this.mWeek;
    }

    public Dao<FitServicesWorkout, Integer> getFitServicesWorkoutDao() throws SQLException {
        if (this.mFitServicesWorkoutDao == null) {
            this.mFitServicesWorkoutDao = getDao(FitServicesWorkout.class);
        }
        return this.mFitServicesWorkoutDao;
    }

    public static synchronized DataBaseHelper getHelper(Context context) {
        DataBaseHelper dataBaseHelper;
        synchronized (DataBaseHelper.class) {
            if (mDatabaseHelper == null) {
                mDatabaseHelper = new DataBaseHelper(context);
            }
            dataBaseHelper = mDatabaseHelper;
        }
        return dataBaseHelper;
    }

    public void close() {
        super.close();
        this.mAwardsDao = null;
        this.mAwardTypeDao = null;
        this.mTrainingGoalsDao = null;
        this.mUserDao = null;
        this.mWorkoutDao = null;
        this.mAchievementDao = null;
        this.mOmniDao = null;
        this.mProductDao = null;
        this.mWeek = null;
        this.mFitServicesWorkoutDao = null;
    }
}
