package com.nautilus.omni.appmodules.home.domain.interactors;

import com.nautilus.omni.appmodules.home.domain.interactors.AchievementsContract;
import com.nautilus.omni.dataaccess.AchievementsDaoHelper;
import com.nautilus.omni.dataaccess.AwardsDaoHelper;
import com.nautilus.omni.dataaccess.WeekDaoHelper;
import com.nautilus.omni.dataaccess.WorkoutDaoHelper;
import com.nautilus.omni.model.dto.Week;
import com.nautilus.omni.model.dto.Workout;

public class AchievementsInteractor implements AchievementsContract {
    private final AchievementsDaoHelper mAchievementsDaoHelper;
    private final AwardsDaoHelper mAwardsDaoHelper;
    private final WeekDaoHelper mWeekDaoHelper;
    private final WorkoutDaoHelper mWorkoutDaoHelper;

    public AchievementsInteractor(WorkoutDaoHelper mWorkoutDaoHelper2, AwardsDaoHelper mAwardsDaoHelper2, AchievementsDaoHelper mAchievementsDaoHelper2, WeekDaoHelper mWeekDaoHelper2) {
        this.mWorkoutDaoHelper = mWorkoutDaoHelper2;
        this.mAwardsDaoHelper = mAwardsDaoHelper2;
        this.mAchievementsDaoHelper = mAchievementsDaoHelper2;
        this.mWeekDaoHelper = mWeekDaoHelper2;
    }

    public void getAllAchievementsAndAwards(AchievementsContract.OnResult onResult) {
        onResult.onGetAllAchievementsAndAwardsSuccess(this.mAwardsDaoHelper.getAllAwards(), this.mAchievementsDaoHelper.getAllAchievements());
    }

    public Workout getHighestCalorieWorkout(int workoutId) {
        return this.mWorkoutDaoHelper.getHighestCalorieWorkout(workoutId);
    }

    public Week getWeekWithMostCalories() {
        return this.mWeekDaoHelper.getWeekWithMostCalories();
    }

    public Workout getHighestAvgCalorieBurnWorkout(int workoutId) {
        return this.mWorkoutDaoHelper.getHighestAvgCalorieBurnWorkout(workoutId);
    }

    public Workout getLongestWorkout(int workoutId) {
        return this.mWorkoutDaoHelper.getLongestWorkout(workoutId);
    }

    public Week getWeekWithMostTime() {
        return this.mWeekDaoHelper.getWeekWithMostTime();
    }

    public Week getWeekWithMostWorkouts() {
        return this.mWeekDaoHelper.getWeekWithMostWorkouts();
    }

    public Workout getHighestAvgSpeedWorkout(int workoutId) {
        return this.mWorkoutDaoHelper.getHighestAvgSpeedWorkout(workoutId);
    }

    public Week getWeekWithMostSpeed() {
        return this.mWeekDaoHelper.getWeekWithMostSpeed();
    }

    public Workout getLongestDistanceWorkout(int workoutId) {
        return this.mWorkoutDaoHelper.getLongestDistanceWorkout(0);
    }

    public Week getWeekWithMostDistance() {
        return this.mWeekDaoHelper.getWeekWithMostDistance();
    }
}
