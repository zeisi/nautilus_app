package com.nautilus.omni.dataaccess;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.nautilus.omni.model.dto.Week;
import com.nautilus.omni.model.dto.Workout;
import com.nautilus.omni.util.DateUtil;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;

public class WeekDaoHelper {
    private Dao<Week, Integer> mWeekDao;

    public WeekDaoHelper(Dao<Week, Integer> weekDao) {
        this.mWeekDao = weekDao;
    }

    public void createOrUpdateRecord(Workout currentWorkout) {
        try {
            List<Week> weekList = this.mWeekDao.queryBuilder().where().le(Week.INITIAL_DATE, currentWorkout.getmWorkoutDate()).and().ge("end_date", currentWorkout.getmWorkoutDate()).query();
            if (weekList == null || weekList.size() == 0) {
                createWeek(currentWorkout);
            } else {
                updateWeek(weekList.get(0), currentWorkout);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createWeek(Workout currentWorkout) {
        Week week = new Week();
        week.setmInitialDate(DateUtil.getFirstDayOftWeek(currentWorkout.getmWorkoutDate()));
        week.setmEndDate(DateUtil.getLastDayOfWeek(currentWorkout.getmWorkoutDate()));
        week.setmLastWorkout(currentWorkout);
        week.setmTotalWorkouts(1);
        week.setmIsAwardedWithMostWorkoutsPerWeek(false);
        week.setmIsAwardedWithThreeOrMoreWorkoutsPerWeek(false);
        week.setmIsAwardedWithMostDistancePerWeek(false);
        week.setmTotalWorkoutsCalories(currentWorkout.getmCalories());
        week.setmTotalWorkoutsPower(currentWorkout.getmAvgPower());
        week.setmTotalWorkoutsTime(currentWorkout.getmElapsedTime());
        week.setmTotalWorkoutsDistance(currentWorkout.getmDistance());
        week.setmTotalWorkoutsSpeed(currentWorkout.getmAvgSpeed());
        week.setmTotalWorkoutsHeartRate(currentWorkout.getmAvgHeartRate());
        week.setmTotalWorkoutsAvgCalBurnRate(currentWorkout.getmAvgCalorieBurnRate());
        try {
            this.mWeekDao.create(week);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateWeek(Week week, Workout currentWorkout) {
        week.setmTotalWorkouts(week.getmTotalWorkouts() + 1);
        week.setmTotalWorkoutsCalories(week.getmTotalWorkoutsCalories() + currentWorkout.getmCalories());
        week.setmTotalWorkoutsTime(week.getmTotalWorkoutsTime() + currentWorkout.getmElapsedTime());
        week.setmTotalWorkoutsDistance(week.getmTotalWorkoutsDistance() + currentWorkout.getmDistance());
        week.setmTotalWorkoutsSpeed(week.getmTotalWorkoutsSpeed() + currentWorkout.getmAvgSpeed());
        week.setmTotalWorkoutsAvgCalBurnRate(week.getmTotalWorkoutsAvgCalBurnRate() + currentWorkout.getmAvgCalorieBurnRate());
        week.setmTotalWorkoutsHeartRate(week.getmTotalWorkoutsHeartRate() + currentWorkout.getmAvgHeartRate());
        week.setmTotalWorkoutsPower(week.getmTotalWorkoutsPower() + currentWorkout.getmAvgPower());
        week.setmLastWorkout(currentWorkout);
        try {
            this.mWeekDao.update(week);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createEmptyWeekRecord() {
        DateTime currentDate = new DateTime();
        if (getWeek(currentDate) == null) {
            Week week = new Week();
            week.setmInitialDate(DateUtil.getFirstDayOftWeek(currentDate));
            week.setmEndDate(DateUtil.getLastDayOfWeek(currentDate));
            week.setmLastWorkout((Workout) null);
            week.setmTotalWorkouts(0);
            week.setmIsAwardedWithMostWorkoutsPerWeek(false);
            week.setmIsAwardedWithThreeOrMoreWorkoutsPerWeek(false);
            week.setmTotalWorkoutsDistance(0);
            week.setmTotalWorkoutsCalories(0);
            week.setmTotalWorkoutsTime(0);
            week.setmTotalWorkoutsPower(0);
            try {
                this.mWeekDao.create(week);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateWeek(Week week) {
        try {
            this.mWeekDao.update(week);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public long getCountOfWeekTable() {
        try {
            return this.mWeekDao.countOf();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public Week getWeek(DateTime currentDate) {
        try {
            return this.mWeekDao.queryBuilder().where().le(Week.INITIAL_DATE, currentDate).and().ge("end_date", currentDate).queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Week getWeekWithMostWorkouts() {
        try {
            QueryBuilder<Week, Integer> builder = this.mWeekDao.queryBuilder();
            builder.orderBy(Week.TOTAL_WORKOUTS, false).query();
            return this.mWeekDao.queryForFirst(builder.prepare());
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Week getWeekWithMostDistance() {
        try {
            QueryBuilder<Week, Integer> builder = this.mWeekDao.queryBuilder();
            builder.orderBy(Week.TOTAL_WORKOUTS_DISTANCE, false).query();
            return this.mWeekDao.queryForFirst(builder.prepare());
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Week getWeekWithMostCalories() {
        try {
            QueryBuilder<Week, Integer> builder = this.mWeekDao.queryBuilder();
            builder.orderBy(Week.TOTAL_WORKOUTS_CALORIES, false).query();
            return this.mWeekDao.queryForFirst(builder.prepare());
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Week getWeekWithMostTime() {
        try {
            QueryBuilder<Week, Integer> builder = this.mWeekDao.queryBuilder();
            builder.orderBy(Week.TOTAL_WORKOUTS_TIME, false).query();
            return this.mWeekDao.queryForFirst(builder.prepare());
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Week getWeekWithMostSpeed() {
        try {
            QueryBuilder<Week, Integer> builder = this.mWeekDao.queryBuilder();
            builder.orderBy(Week.TOTAL_WORKOUTS_SPEED, false).query();
            return this.mWeekDao.queryForFirst(builder.prepare());
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Week> getMostRecentWeeks(int numberOfWeeks) {
        List<Week> weekList = new ArrayList<>();
        DateTime weekDate = DateUtil.getFirstDayOfCurrentWeek();
        try {
            List<Week> temporalWeekList = this.mWeekDao.queryBuilder().orderBy(Week.INITIAL_DATE, false).limit(Long.valueOf((long) numberOfWeeks)).query();
            for (int currentWeek = 0; currentWeek < numberOfWeeks; currentWeek++) {
                weekList.add(checkWeek(temporalWeekList, weekDate));
                weekDate = weekDate.minusWeeks(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return weekList;
    }

    private Week checkWeek(List<Week> temporalWeekList, DateTime currentWeekDate) {
        for (Week week : temporalWeekList) {
            if (week.getmInitialDate().getDayOfYear() == currentWeekDate.getDayOfYear()) {
                return week;
            }
        }
        Week week2 = new Week();
        week2.setmInitialDate(currentWeekDate);
        week2.setmTotalWorkouts(0);
        week2.setmTotalWorkoutsCalories(0);
        week2.setmTotalWorkoutsDistance(0);
        week2.setmTotalWorkoutsPower(0);
        return week2;
    }
}
