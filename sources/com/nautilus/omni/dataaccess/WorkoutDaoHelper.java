package com.nautilus.omni.dataaccess;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.nautilus.omni.appmodules.awards.AwardTypeEnum;
import com.nautilus.omni.model.dto.Award;
import com.nautilus.omni.model.dto.User;
import com.nautilus.omni.model.dto.Workout;
import com.nautilus.omni.util.DateUtil;
import com.nautilus.omni.util.Util;
import com.nautilus.underarmourconnection.database.WorkoutTrack;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.joda.time.DateTime;
import org.joda.time.ReadableInstant;

public class WorkoutDaoHelper {
    private Dao<Workout, Integer> mWorkoutDao;

    public WorkoutDaoHelper(Dao<Workout, Integer> workoutDao) {
        this.mWorkoutDao = workoutDao;
    }

    public void createWorkout(Workout workout) {
        try {
            this.mWorkoutDao.createOrUpdate(workout);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public long getCountOfWorkoutTable() {
        try {
            return this.mWorkoutDao.countOf();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public Workout getWorkoutById(int workoutId) {
        try {
            return this.mWorkoutDao.queryForId(Integer.valueOf(workoutId));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Workout> getMostRecentWorkouts(DateTime currentMostRecentWorkoutDate) {
        List workoutsList = new ArrayList();
        try {
            return this.mWorkoutDao.queryBuilder().where().gt("workout_date", currentMostRecentWorkoutDate).query();
        } catch (SQLException e) {
            e.printStackTrace();
            return workoutsList;
        }
    }

    public List<Workout> getAllWorkouts(boolean inAscedingOrder) {
        List workoutsList = new ArrayList();
        try {
            return this.mWorkoutDao.queryBuilder().orderBy("workout_date", inAscedingOrder).query();
        } catch (SQLException e) {
            e.printStackTrace();
            return workoutsList;
        }
    }

    public List<Workout> getWorkoutsWithRecordIdHigherThan(int recordId, boolean inAscedingOrder) {
        List workoutsList = new ArrayList();
        try {
            return this.mWorkoutDao.queryBuilder().orderBy("workout_date", inAscedingOrder).where().gt("record_id", Integer.valueOf(recordId)).query();
        } catch (SQLException e) {
            e.printStackTrace();
            return workoutsList;
        }
    }

    public Workout getWorkout(WorkoutTrack workoutTrack) {
        try {
            return this.mWorkoutDao.queryBuilder().where().eq("record_id", Integer.valueOf(workoutTrack.getRecordId())).and().eq("original_second", Integer.valueOf(workoutTrack.getOriginalSecond())).and().eq("original_minute", Integer.valueOf(workoutTrack.getOriginalMinute())).and().eq("original_hour", Integer.valueOf(workoutTrack.getOriginalHour())).and().eq("original_day", Integer.valueOf(workoutTrack.getOriginalDay())).and().eq("original_month", Integer.valueOf(workoutTrack.getOriginalMonth())).and().eq("original_year", Integer.valueOf(workoutTrack.getOriginalYear())).queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Number getHighestWorkoutByColumnType(String workoutColumn, int currentWorkoutId) {
        if (workoutColumn.equals("calories")) {
            return Integer.valueOf(getHighestCalorieWorkout(currentWorkoutId).getmCalories());
        }
        if (workoutColumn.equals(Workout.AVERAGE_CALORIE_BURN_RATE)) {
            return Float.valueOf(Util.getAvgCalorieBurnRatePerMinute(getHighestAvgCalorieBurnWorkout(currentWorkoutId)));
        }
        if (workoutColumn.equals("distance")) {
            return Integer.valueOf(getLongestDistanceWorkout(currentWorkoutId).getmDistance());
        }
        if (workoutColumn.equals(Workout.ELAPSED_TIME)) {
            return Integer.valueOf(getLongestWorkout(currentWorkoutId).getmElapsedTime());
        }
        if (workoutColumn.equals(Workout.AVG_SPEED)) {
            return Integer.valueOf(getHighestAvgSpeedWorkout(currentWorkoutId).getmAvgSpeed());
        }
        return 0;
    }

    public Workout getHighestCalorieWorkout(int currentWorkoutId) {
        try {
            QueryBuilder<Workout, Integer> builder = this.mWorkoutDao.queryBuilder();
            builder.orderBy("calories", false).where().ne("id", Integer.valueOf(currentWorkoutId));
            return this.mWorkoutDao.queryForFirst(builder.prepare());
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Workout getHighestAvgCalorieBurnWorkout(int currentWorkoutId) {
        try {
            QueryBuilder<Workout, Integer> builder = this.mWorkoutDao.queryBuilder();
            builder.orderBy(Workout.AVERAGE_CALORIE_BURN_RATE, false).where().ne("id", Integer.valueOf(currentWorkoutId));
            return this.mWorkoutDao.queryForFirst(builder.prepare());
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Workout getLongestDistanceWorkout(int currentWorkoutId) {
        try {
            QueryBuilder<Workout, Integer> builder = this.mWorkoutDao.queryBuilder();
            builder.orderBy("distance", false).where().ne("id", Integer.valueOf(currentWorkoutId));
            return this.mWorkoutDao.queryForFirst(builder.prepare());
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Workout getLongestWorkout(int currentWorkoutId) {
        try {
            QueryBuilder<Workout, Integer> builder = this.mWorkoutDao.queryBuilder();
            builder.orderBy(Workout.ELAPSED_TIME, false).where().ne("id", Integer.valueOf(currentWorkoutId));
            return this.mWorkoutDao.queryForFirst(builder.prepare());
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getBestWorkoutAwardsAmount(int currentWorkoutId) {
        int biggest = 0;
        try {
            for (Workout workout : this.mWorkoutDao.queryBuilder().where().ne("id", Integer.valueOf(currentWorkoutId)).query()) {
                int currentWorkoutAwardsAmount = workout.getmAwards() != null ? workout.getmAwards().size() : 0;
                if (currentWorkoutAwardsAmount > biggest) {
                    biggest = !wonBestAward(workout.getmAwards()) ? currentWorkoutAwardsAmount : currentWorkoutAwardsAmount - 1;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return biggest;
    }

    public List<Workout> getBestWorkouts(List<Workout> workoutsList) {
        ArrayList<Workout> mostRecentBestWorkoutsList = new ArrayList<>();
        for (Workout workout : workoutsList) {
            if (wonBestAward(workout.getmAwards())) {
                mostRecentBestWorkoutsList.add(workout);
            }
        }
        return mostRecentBestWorkoutsList;
    }

    public boolean wonBestAward(Collection<Award> awardsList) {
        for (Award award : awardsList) {
            if (award.getmAwardType().getmName().equals(AwardTypeEnum.BEST_WORKOUT.toString())) {
                return true;
            }
        }
        return false;
    }

    public Workout getLastWorkout() {
        try {
            QueryBuilder<Workout, Integer> builder = this.mWorkoutDao.queryBuilder();
            builder.orderBy("workout_date", false);
            return this.mWorkoutDao.queryForFirst(builder.prepare());
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Workout getPreviousWorkout() {
        try {
            QueryBuilder<Workout, Integer> builder = this.mWorkoutDao.queryBuilder();
            builder.orderBy("workout_date", false);
            builder.limit((Long) 2L);
            List<Workout> workoutList = this.mWorkoutDao.query(builder.prepare());
            if (workoutList.size() <= 1) {
                return null;
            }
            workoutList.remove(0);
            return workoutList.get(0);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Workout> getWorkoutsFromSpecificWeek(DateTime weekDate, boolean inAscendingOrder) {
        List<Workout> workoutsList = new ArrayList<>();
        try {
            return this.mWorkoutDao.queryBuilder().orderBy("workout_date", inAscendingOrder).where().ge("workout_date", DateUtil.getFirstDayOftWeek(weekDate)).and().le("workout_date", DateUtil.getLastDayOfWeek(weekDate)).query();
        } catch (SQLException e) {
            e.printStackTrace();
            return workoutsList;
        }
    }

    public ArrayList<Workout> getWorkoutsFromSpecificWeek(DateTime weekDate, List<Workout> workoutsList) {
        ArrayList<Workout> weekWorkoutsList = new ArrayList<>();
        for (Workout workout : workoutsList) {
            if (DateUtil.isDateBetween(DateUtil.getFirstDayOftWeek(weekDate), workout.getmWorkoutDate(), DateUtil.getLastDayOfWeek(weekDate))) {
                weekWorkoutsList.add(workout);
            }
        }
        return weekWorkoutsList;
    }

    public Workout getHighestRecordIdWorkout() {
        try {
            return this.mWorkoutDao.queryBuilder().orderBy("record_id", false).limit((Long) 1L).queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Workout getHighestAvgSpeedWorkout(int currentWorkoutId) {
        try {
            QueryBuilder<Workout, Integer> builder = this.mWorkoutDao.queryBuilder();
            builder.orderBy(Workout.AVG_SPEED, false).where().ne("id", Integer.valueOf(currentWorkoutId));
            return this.mWorkoutDao.queryForFirst(builder.prepare());
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public DateTime getMostRecentWorkoutDate(User currentUser) {
        try {
            Workout mostRecentWorkout = this.mWorkoutDao.queryBuilder().limit((Long) 1L).orderBy("workout_date", false).where().eq("user", currentUser).queryForFirst();
            if (mostRecentWorkout != null) {
                return mostRecentWorkout.getmWorkoutDate();
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public DateTime getOldestWorkoutDate(User currentUser) {
        try {
            Workout oldestWorkout = this.mWorkoutDao.queryBuilder().limit((Long) 1L).orderBy("workout_date", true).where().eq("user", currentUser).queryForFirst();
            if (oldestWorkout != null) {
                return oldestWorkout.getmWorkoutDate();
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean areWorkoutsAvailableForOlderWeeks(User currentUser, DateTime currentDate) {
        if (getOldestWorkoutDate(currentUser).isBefore((ReadableInstant) DateUtil.getFirstDayOftWeek(currentDate))) {
            return true;
        }
        return false;
    }

    public boolean areWorkoutsAvailableForNextWeeks(User currentUser, DateTime currentDate) {
        if (getMostRecentWorkoutDate(currentUser).isAfter((ReadableInstant) DateUtil.getLastDayOfWeek(currentDate))) {
            return true;
        }
        return false;
    }

    public boolean areWorkoutsAvailableForNextYears(User currentUser, DateTime currentDate) {
        if (getMostRecentWorkoutDate(currentUser).getYear() > currentDate.getYear()) {
            return true;
        }
        return false;
    }

    public boolean areWorkoutsAvailableForOlderYears(User currentUser, DateTime currentDate) {
        if (getOldestWorkoutDate(currentUser).getYear() < currentDate.getYear()) {
            return true;
        }
        return false;
    }

    public boolean areWorkoutsAvailableForSelectedYear(DateTime yearDate) {
        if (getWorkoutsFromSpecificYear(yearDate, false).size() > 0) {
            return true;
        }
        return false;
    }

    public List<Workout> getWorkoutsFromSpecificYear(DateTime yearDate, boolean inAscendingOrder) {
        List<Workout> workoutsList = new ArrayList<>();
        DateTime firstDayOfYear = DateUtil.getFirstDayOfYear(yearDate);
        try {
            return this.mWorkoutDao.queryBuilder().orderBy("workout_date", inAscendingOrder).where().ge("workout_date", firstDayOfYear).and().le("workout_date", DateUtil.getLastDayOfYear(yearDate)).query();
        } catch (SQLException e) {
            e.printStackTrace();
            return workoutsList;
        }
    }
}
