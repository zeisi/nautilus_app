package com.nautilus.omni.syncservices.syncserviceshelpers;

import android.content.Context;
import com.nautilus.omni.appmodules.awards.AwardTypeEnum;
import com.nautilus.omni.dataaccess.AwardsDaoHelper;
import com.nautilus.omni.dataaccess.WeekDaoHelper;
import com.nautilus.omni.dataaccess.WorkoutDaoHelper;
import com.nautilus.omni.model.dto.AwardType;
import com.nautilus.omni.model.dto.Week;
import com.nautilus.omni.model.dto.Workout;
import com.nautilus.omni.util.DateUtil;
import com.nautilus.omni.util.Util;
import java.sql.SQLException;
import java.util.List;

public class AwardSyncDataLoaderHelper {
    private static final int BEST_WORKOUT_MINIMUM_AMOUNT = 2;
    private static final int FIRST_WEEK = 1;
    private static final int MINIMUM_AVG_BURN_CAL_AWARD = 1;
    private static final int MINIMUM_AVG_SPEED_WORKOUT_AWARD = 1;
    private static final int MINIMUM_CAL_AWARD = 1;
    private static final int MINIMUM_LONGEST_DISTANCE_WORKOUT_AWARD = 1;
    private static final int MINIMUM_LONGEST_WORKOUT_AWARD = 1;
    private static final int MINIMUM_WEEKS_FOR_MOST_DISTANCE_AWARD = 1;
    private static final int MINIMUM_WEEKS_FOR_MOST_WORKOUTS_AWARD = 1;
    private static final int THREE_WORKOUTS = 3;
    private AwardsDaoHelper mAwardDaoHelper;
    private Context mContext;
    private WeekDaoHelper mWeekDaoHelper;
    private WorkoutDaoHelper mWorkoutDaoHelper;

    public AwardSyncDataLoaderHelper(Context context, WorkoutDaoHelper workoutDaoHelper, AwardsDaoHelper awardsDaoHelper, WeekDaoHelper weekDaoHelper) {
        this.mWorkoutDaoHelper = workoutDaoHelper;
        this.mContext = context;
        this.mAwardDaoHelper = awardsDaoHelper;
        this.mWeekDaoHelper = weekDaoHelper;
    }

    public void updateWeekTable(Workout currentWorkout) {
        this.mWeekDaoHelper.createOrUpdateRecord(currentWorkout);
    }

    public void checkForAwards(Workout currentWorkout) throws SQLException {
        checkIfWorkoutHasWonFirstWorkoutPerformedAward(currentWorkout);
        checkIfWonAward(currentWorkout, 1, AwardTypeEnum.MOST_CALORIES_WORKOUT.toString(), "calories", (double) currentWorkout.getmCalories());
        checkIfWonAward(currentWorkout, 1, AwardTypeEnum.HIGHEST_AVG_CALORIE.toString(), Workout.AVERAGE_CALORIE_BURN_RATE, (double) Util.getAvgCalorieBurnRatePerMinute(currentWorkout));
        checkIfWonAward(currentWorkout, 1, AwardTypeEnum.LONGEST_DISTANCE.toString(), "distance", (double) currentWorkout.getmDistance());
        checkIfWonAward(currentWorkout, 1, AwardTypeEnum.HIGHEST_AVG_SPEED.toString(), Workout.AVG_SPEED, (double) currentWorkout.getmAvgSpeed());
        checkIfWonAward(currentWorkout, 1, AwardTypeEnum.LONGEST_WORKOUT.toString(), Workout.ELAPSED_TIME, (double) currentWorkout.getmElapsedTime());
        checkIfWorkoutHasWonThreeOrMoreWorkoutsPeerWeekAward(currentWorkout, AwardTypeEnum.THREE_OR_MORE_WORKOUTS_IN_A_WEEK);
        checkMostWorkoutsPerWeekAward(currentWorkout);
        checkIfWorkoutHasWonMostDistancePerWeekAward(currentWorkout);
        checkIfWorkoutHasWonBestWorkoutAward(currentWorkout);
    }

    private boolean checkIfWonAward(Workout currentWorkout, int minimumAmountRestriction, String awardTypeName, String awardColumn, double currentWorkoutValue) {
        if (this.mWorkoutDaoHelper.getCountOfWorkoutTable() <= ((long) minimumAmountRestriction)) {
            return false;
        }
        return evaluateAward(this.mAwardDaoHelper.getAwardTypeByName(awardTypeName), currentWorkoutValue, Double.valueOf(this.mWorkoutDaoHelper.getHighestWorkoutByColumnType(awardColumn, currentWorkout.getmId()).doubleValue()), currentWorkout);
    }

    private boolean evaluateAward(AwardType awardType, double currentWorkoutValue, Double existentValue, Workout currentWorkout) {
        if (existentValue.doubleValue() >= currentWorkoutValue) {
            return false;
        }
        this.mAwardDaoHelper.createNewAward(awardType, currentWorkout);
        return true;
    }

    private void checkIfWorkoutHasWonFirstWorkoutPerformedAward(Workout currentWorkout) throws SQLException {
        if (this.mWorkoutDaoHelper.getCountOfWorkoutTable() == 1) {
            saveFirstWorkoutAward(currentWorkout);
        }
    }

    private void saveFirstWorkoutAward(Workout currentWorkout) throws SQLException {
        this.mAwardDaoHelper.createNewAward(this.mAwardDaoHelper.getAwardTypeByName(AwardTypeEnum.FIRST_WORKOUT.toString()), currentWorkout);
    }

    private void checkIfWorkoutHasWonThreeOrMoreWorkoutsPeerWeekAward(Workout currentWorkout, AwardTypeEnum awardType) throws SQLException {
        if (justWonThreeOrMoreDaysPerWeekAward(currentWorkout, awardType)) {
            saveThreeOrMoreWorkoutsPerWeekAward(currentWorkout, awardType);
        }
    }

    private void saveThreeOrMoreWorkoutsPerWeekAward(Workout currentWorkout, AwardTypeEnum awardTypeEnum) throws SQLException {
        this.mAwardDaoHelper.createNewAward(this.mAwardDaoHelper.getAwardTypeByName(awardTypeEnum.toString()), currentWorkout);
    }

    public boolean justWonThreeOrMoreDaysPerWeekAward(Workout currentWorkout, AwardTypeEnum awardType) throws SQLException {
        Week currentWorkoutWeek = this.mWeekDaoHelper.getWeek(currentWorkout.getmWorkoutDate());
        if (currentWorkoutWeek == null) {
            return false;
        }
        if (currentWorkoutWeek.getmTotalWorkouts() == 3) {
            return true;
        }
        if (currentWorkoutWeek.getmTotalWorkouts() <= 3) {
            return false;
        }
        updateBestWorkoutAward(this.mAwardDaoHelper.getMostRecentAward(AwardTypeEnum.THREE_OR_MORE_WORKOUTS_IN_A_WEEK).getmWorkout());
        updateThreeOrMoreWorkoutsPerWeekAward(currentWorkout, awardType, currentWorkoutWeek.getmTotalWorkouts());
        return false;
    }

    private void updateThreeOrMoreWorkoutsPerWeekAward(Workout currentWorkout, AwardTypeEnum awardTypeEnum, int workoutsAmount) {
        this.mAwardDaoHelper.updateAward(currentWorkout, this.mAwardDaoHelper.getMostRecentAward(awardTypeEnum));
    }

    public void checkIfWorkoutHasWonMostDistancePerWeekAward(Workout currentWorkout) throws SQLException {
        boolean weekJustAwardedWithCurrentSync = false;
        if (this.mWeekDaoHelper.getCountOfWeekTable() > 1) {
            Week week = this.mWeekDaoHelper.getWeekWithMostDistance();
            if (wonMostDistancePerWeekAward(week)) {
                saveMostDistancePerWeekAward(week);
                weekJustAwardedWithCurrentSync = true;
            }
            if (isCurrentWeekAndAlreadyWonMostDistanceAward(week, currentWorkout) && !weekJustAwardedWithCurrentSync) {
                updateBestWorkoutAward(this.mAwardDaoHelper.getMostRecentAward(AwardTypeEnum.MOST_DISTANCE_PER_WEEK).getmWorkout());
                updateCurrentMostDistancePerWeekAward(currentWorkout, week);
            }
        }
    }

    private boolean wonMostDistancePerWeekAward(Week week) {
        return week != null && !week.ismIsAwardedWithMostDistancePerWeek() && isNotFirstWeek(week);
    }

    private boolean isCurrentWeekAndAlreadyWonMostDistanceAward(Week week, Workout currentWorkout) {
        if (!DateUtil.isSameDay(week.getmInitialDate(), DateUtil.getFirstDayOftWeek(currentWorkout.getmWorkoutDate())) || !week.ismIsAwardedWithMostDistancePerWeek()) {
            return false;
        }
        return true;
    }

    private void saveMostDistancePerWeekAward(Week week) {
        week.setmIsAwardedWithMostDistancePerWeek(true);
        this.mWeekDaoHelper.updateWeek(week);
        this.mAwardDaoHelper.createNewAward(this.mAwardDaoHelper.getAwardTypeByName(AwardTypeEnum.MOST_DISTANCE_PER_WEEK.toString()), week.getmLastWorkout());
    }

    private void updateCurrentMostDistancePerWeekAward(Workout currentWorkout, Week week) throws SQLException {
        this.mAwardDaoHelper.updateAward(currentWorkout, this.mAwardDaoHelper.getMostRecentAward(AwardTypeEnum.MOST_DISTANCE_PER_WEEK));
        week.setmLastWorkout(currentWorkout);
        this.mWeekDaoHelper.updateWeek(week);
    }

    public void checkMostWorkoutsPerWeekAward(Workout currentWorkout) throws SQLException {
        boolean weekJustAwardedWithCurrentSync = false;
        if (this.mWeekDaoHelper.getCountOfWeekTable() > 1) {
            Week week = this.mWeekDaoHelper.getWeekWithMostWorkouts();
            if (wonMostWorkoutsPerWeekAward(week)) {
                saveMostWorkoutsPerWeekAward(week);
                weekJustAwardedWithCurrentSync = true;
            }
            if (isCurrentWeekAndAlreadyWonMostWorkoutsAward(week, currentWorkout) && !weekJustAwardedWithCurrentSync) {
                updateBestWorkoutAward(this.mAwardDaoHelper.getMostRecentAward(AwardTypeEnum.MOST_WORKOUTS_WEEK).getmWorkout());
                updateCurrentMostWorkoutsPerWeekAward(currentWorkout, week);
            }
        }
    }

    private boolean wonMostWorkoutsPerWeekAward(Week week) {
        return week != null && !week.ismIsAwardedWithMostWorkoutsPerWeek() && isNotFirstWeek(week);
    }

    private boolean isCurrentWeekAndAlreadyWonMostWorkoutsAward(Week week, Workout currentWorkout) {
        if (!DateUtil.isSameDay(week.getmInitialDate(), DateUtil.getFirstDayOftWeek(currentWorkout.getmWorkoutDate())) || !week.ismIsAwardedWithMostWorkoutsPerWeek()) {
            return false;
        }
        return true;
    }

    private void updateCurrentMostWorkoutsPerWeekAward(Workout currentWorkout, Week week) throws SQLException {
        this.mAwardDaoHelper.updateAward(currentWorkout, this.mAwardDaoHelper.getMostRecentAward(AwardTypeEnum.MOST_WORKOUTS_WEEK));
        week.setmLastWorkout(currentWorkout);
        this.mWeekDaoHelper.updateWeek(week);
    }

    private boolean isNotFirstWeek(Week week) {
        return week.getmId() > 1;
    }

    private void saveMostWorkoutsPerWeekAward(Week week) {
        week.setmIsAwardedWithMostWorkoutsPerWeek(true);
        this.mWeekDaoHelper.updateWeek(week);
        this.mAwardDaoHelper.createNewAward(this.mAwardDaoHelper.getAwardTypeByName(AwardTypeEnum.MOST_WORKOUTS_WEEK.toString()), week.getmLastWorkout());
    }

    private void updateBestWorkoutAward(Workout lastWorkout) throws SQLException {
        if (this.mWorkoutDaoHelper.wonBestAward(lastWorkout.getmAwards())) {
            List<Workout> bestWorkoutsList = this.mWorkoutDaoHelper.getBestWorkouts(this.mWorkoutDaoHelper.getAllWorkouts(false));
            if (bestWorkoutsList.size() == 1) {
                checkIfWorkoutIsStillBestWorkout(bestWorkoutsList.get(0));
            } else if (bestWorkoutsList.size() > 1) {
                checkIfWorkoutIsStillBestWorkout(bestWorkoutsList.get(0), bestWorkoutsList.get(1));
            }
        }
    }

    private void checkIfWorkoutIsStillBestWorkout(Workout workout) {
        if (workout.getmAwards().size() - 2 < 2) {
            this.mAwardDaoHelper.deleteBestAward(workout);
        } else {
            updateBestWorkoutAwardValue();
        }
    }

    private void checkIfWorkoutIsStillBestWorkout(Workout mostRecentBestWorkout, Workout secondMostRecentBestWorkout) {
        if (mostRecentBestWorkout.getmAwards().size() - 1 == secondMostRecentBestWorkout.getmAwards().size()) {
            this.mAwardDaoHelper.deleteBestAward(mostRecentBestWorkout);
        } else {
            updateBestWorkoutAwardValue();
        }
    }

    private void updateBestWorkoutAwardValue() {
        this.mAwardDaoHelper.updateAward(this.mAwardDaoHelper.getMostRecentAward(AwardTypeEnum.BEST_WORKOUT));
    }

    private void checkIfWorkoutHasWonBestWorkoutAward(Workout currentWorkout) throws SQLException {
        if (this.mWorkoutDaoHelper.getWorkoutById(currentWorkout.getmId()) != null) {
            currentWorkout = this.mWorkoutDaoHelper.getWorkoutById(currentWorkout.getmId());
        }
        if (this.mWorkoutDaoHelper.getCountOfWorkoutTable() > 1) {
            int bestWorkoutAmount = this.mWorkoutDaoHelper.getBestWorkoutAwardsAmount(currentWorkout.getmId());
            int newWorkoutAwardsAmount = currentWorkout.getmAwards() != null ? currentWorkout.getmAwards().size() : 0;
            if (newWorkoutAwardsAmount >= 2 && newWorkoutAwardsAmount > bestWorkoutAmount) {
                saveBestWorkoutAward(currentWorkout);
            }
        }
    }

    private void saveBestWorkoutAward(Workout currentWorkout) throws SQLException {
        this.mAwardDaoHelper.createNewAward(this.mAwardDaoHelper.getAwardTypeByName(AwardTypeEnum.BEST_WORKOUT.toString()), currentWorkout);
    }
}
