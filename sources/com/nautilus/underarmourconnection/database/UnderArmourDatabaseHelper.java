package com.nautilus.underarmourconnection.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;

public class UnderArmourDatabaseHelper extends OrmLiteSqliteOpenHelper {
    public static final String DATABASE_NAME = "underarmourDB.db";
    public static final int DATABASE_VERSION = 1;
    private static UnderArmourDatabaseHelper mUnderArmourDatabaseHelper;
    private Context mContext;
    private Dao<WorkoutTrack, Integer> mWorkoutTrackDao = null;

    public UnderArmourDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, (SQLiteDatabase.CursorFactory) null, 1);
        this.mContext = context;
    }

    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            Log.d("TAG", "CREATING TRACK WORKOUT TABLE...........");
            TableUtils.createTable(connectionSource, WorkoutTrack.class);
        } catch (SQLException e) {
        }
    }

    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
    }

    public Dao<WorkoutTrack, Integer> getWorkoutTrackDao() throws SQLException {
        if (this.mWorkoutTrackDao == null) {
            this.mWorkoutTrackDao = getDao(WorkoutTrack.class);
        }
        return this.mWorkoutTrackDao;
    }

    public static synchronized UnderArmourDatabaseHelper getHelper(Context context) {
        UnderArmourDatabaseHelper underArmourDatabaseHelper;
        synchronized (UnderArmourDatabaseHelper.class) {
            if (mUnderArmourDatabaseHelper == null) {
                mUnderArmourDatabaseHelper = new UnderArmourDatabaseHelper(context);
            }
            underArmourDatabaseHelper = mUnderArmourDatabaseHelper;
        }
        return underArmourDatabaseHelper;
    }

    public void close() {
        super.close();
        this.mWorkoutTrackDao = null;
    }
}
