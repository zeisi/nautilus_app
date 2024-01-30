package com.nautilus.underarmourconnection.database;

import com.j256.ormlite.dao.Dao;
import java.sql.SQLException;
import org.joda.time.DateTime;

public class WorkoutTrackDaoHelper {
    private Dao<WorkoutTrack, Integer> mWorkoutDao;

    public WorkoutTrackDaoHelper(Dao<WorkoutTrack, Integer> workoutDao) {
        this.mWorkoutDao = workoutDao;
    }

    public WorkoutTrack getLastSyncedWorkoutTrack(String machineType, int consoleUserNumber) {
        try {
            return this.mWorkoutDao.queryBuilder().orderBy("id", false).where().eq(WorkoutTrack.MACHINE_TYPE, machineType).and().eq(WorkoutTrack.CONSOLE_USER_NUMBER, Integer.valueOf(consoleUserNumber)).queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean createWorkout(String workoutUnderArmourId, int recordId, String machineType, int consoleUserNumber, long syncDate, int originalSecond, int originalMinute, int originalHour, int originalDay, int originalMonth, int originalYear) {
        try {
            this.mWorkoutDao.createOrUpdate(new WorkoutTrack(workoutUnderArmourId, recordId, consoleUserNumber, machineType, new DateTime(syncDate), originalSecond, originalMinute, originalHour, originalDay, originalMonth, originalYear));
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isWorkoutInDatabase(int recordId, String machineType, int consoleUserNumber, int originalSecond, int originalMinute, int originalHour, int originalDay, int originalMonth, int originalYear) {
        try {
            if (this.mWorkoutDao.queryBuilder().where().eq(WorkoutTrack.MACHINE_TYPE, machineType).and().eq(WorkoutTrack.CONSOLE_USER_NUMBER, Integer.valueOf(consoleUserNumber)).and().eq("record_id", Integer.valueOf(recordId)).and().eq("original_second", Integer.valueOf(originalSecond)).and().eq("original_minute", Integer.valueOf(originalMinute)).and().eq("original_hour", Integer.valueOf(originalHour)).and().eq("original_day", Integer.valueOf(originalDay)).and().eq("original_month", Integer.valueOf(originalMonth)).and().eq("original_year", Integer.valueOf(originalYear)).queryForFirst() != null) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
