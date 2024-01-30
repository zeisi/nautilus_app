package com.nautilus.omni.appmodules.home.presenter;

import android.content.Context;
import com.nautilus.omni.app.BasePresenter;
import com.nautilus.omni.app.Preferences;
import com.nautilus.omni.appmodules.home.domain.interactors.AchievementsContract;
import com.nautilus.omni.appmodules.home.view.AchievementFragmentContract;
import com.nautilus.omni.model.dto.Achievement;
import com.nautilus.omni.model.dto.Award;
import com.nautilus.omni.model.dto.Week;
import com.nautilus.omni.model.dto.Workout;
import com.nautilus.omni.util.Util;
import java.util.List;
import javax.inject.Inject;

public class AchievementsPresenter extends BasePresenter implements AchievementsPresenterContract, AchievementsContract.OnResult {
    private AchievementFragmentContract iAchievementFragment;
    private AchievementsContract iAchievementsInteractor;

    @Inject
    public AchievementsPresenter(Context context, AchievementsContract iAchievementsInteractor2) {
        super(context);
        this.iAchievementsInteractor = iAchievementsInteractor2;
    }

    public void setiAchievementFragment(AchievementFragmentContract iAchievementFragment2) {
        this.iAchievementFragment = iAchievementFragment2;
    }

    public int refreshUnit() {
        int unit = this.mPreferences.getInt(Preferences.UNITS_SETTINGS, 0);
        this.mUnit = unit;
        return unit;
    }

    public void getAllAwardsAndAchievements() {
        this.iAchievementsInteractor.getAllAchievementsAndAwards(this);
    }

    public String getCaloriesBurnedPerWorkout() {
        Workout workout = this.iAchievementsInteractor.getHighestCalorieWorkout(0);
        return String.valueOf(workout == null ? "0" : String.valueOf(workout.getmCalories()));
    }

    public String getMostCaloriesBurnedPerWeek() {
        Week week = this.iAchievementsInteractor.getWeekWithMostCalories();
        return week == null ? "0" : String.valueOf(week.getmTotalWorkoutsCalories());
    }

    public String getHighestBurnRatePerWorkout() {
        Workout workout = this.iAchievementsInteractor.getHighestAvgCalorieBurnWorkout(0);
        return workout == null ? "0" : Util.convertDoubleToTwoDecimals((double) workout.getmAvgCalorieBurnRate());
    }

    public String getLongestWorkout() {
        Workout workout = this.iAchievementsInteractor.getLongestWorkout(0);
        if (workout == null) {
            return Util.convertSecondsToDuration(0, true);
        }
        return Util.convertSecondsToDuration((long) workout.getmElapsedTime(), true);
    }

    public String getMostWorkoutTimePerWeek() {
        Week week = this.iAchievementsInteractor.getWeekWithMostTime();
        if (week == null) {
            return Util.convertSecondsToDuration(0, true);
        }
        return Util.convertSecondsToDuration((long) week.getmTotalWorkoutsTime(), true);
    }

    public String getMostWorkoutsPerWeek() {
        Week week = this.iAchievementsInteractor.getWeekWithMostWorkouts();
        return week == null ? "0" : String.valueOf(week.getmTotalWorkouts());
    }

    public String getHighestAvgSpeedPerWorkout(int unit) {
        Workout workout = this.iAchievementsInteractor.getHighestAvgSpeedWorkout(0);
        return unit == 0 ? workout == null ? "0" : Util.getWorkoutSpeedInMilesAsString(workout) : workout == null ? "0" : Util.getWorkoutSpeedInKilometersAsString(workout);
    }

    public String getHighestAvgSpeedPerWeek(int unit) {
        Week week = this.iAchievementsInteractor.getWeekWithMostSpeed();
        return unit == 0 ? week == null ? "0" : Util.getWeekAvgSpeedInMilesAsString(week) : week == null ? "0" : Util.getWeekAvgSpeedInKmAsString(week);
    }

    public String getMostDistancePerWorkout(int unit) {
        Workout workout = this.iAchievementsInteractor.getLongestDistanceWorkout(0);
        return unit == 0 ? workout == null ? "0" : Util.getWorkoutDistanceInMilesAsString(workout) : workout == null ? "0" : Util.getWorkoutDistanceInKilometersAsString(workout);
    }

    public String getMostDistancePerWeek(int unit) {
        Week week = this.iAchievementsInteractor.getWeekWithMostDistance();
        return unit == 0 ? week == null ? "0" : Util.getWeekDistanceInMilesAsString(week) : week == null ? "0" : Util.getWeekDistanceInKmAsString(week);
    }

    public void onGetAllAchievementsAndAwardsSuccess(List<Award> awards, List<Achievement> achievements) {
        this.iAchievementFragment.setAwardsAndAchievements(awards, achievements, this.mUnit);
    }
}
