package com.nautilus.omni.appmodules.home.presenter;

import android.content.Context;
import com.nautilus.omni.R;
import com.nautilus.omni.app.BasePresenter;
import com.nautilus.omni.app.Preferences;
import com.nautilus.omni.appmodules.home.domain.helpers.IGaugeHelper;
import com.nautilus.omni.appmodules.home.domain.interactors.GaugeInteractorContract;
import com.nautilus.omni.appmodules.home.view.GaugeFragmentContract;
import com.nautilus.omni.model.dto.TrainingGoal;
import com.nautilus.omni.model.dto.User;
import com.nautilus.omni.model.dto.Workout;

public class GaugePresenter extends BasePresenter implements GaugePresenterContract, IGaugeHelper.OnWorkoutListener, GaugeInteractorContract.OnTextDataListener {
    public static final int AVG_CALORIES = 1;
    public static final int DISTANCE = 4;
    public static final int FREQUENCY = 7;
    public static final int HEART_RATE = 3;
    public static final int SPEED = 5;
    public static final int TIME = 2;
    public static final int TOTAL_CALORIES = 0;
    public static final int WATTS = 6;
    GaugeFragmentContract iGaugeFragment;
    IGaugeHelper mIGaugeHelper;
    GaugeInteractorContract mIGaugeInteractor;

    public GaugePresenter(Context context, GaugeInteractorContract iGaugeInteractor, IGaugeHelper iGaugeHelper) {
        super(context);
        this.mIGaugeInteractor = iGaugeInteractor;
        this.mIGaugeHelper = iGaugeHelper;
    }

    public void setGauceFragment(GaugeFragmentContract iGaugeFragment2) {
        this.iGaugeFragment = iGaugeFragment2;
    }

    public void loadTextData(int kind) {
        updateUnits();
        this.mIGaugeInteractor.loadTextData(kind, this.mPreferences.getBoolean(Preferences.GOALS_ENABLED, false), this.mPreferences.getInt(Preferences.USER_INDEX, -1), this);
    }

    public void loadData(int kind, Workout lastWorkout, Workout previousWorkout, User currentUser) {
        if (lastWorkout == null || kind == 3) {
            this.iGaugeFragment.hideImageViewNeedle();
        }
        if (lastWorkout == null) {
            showEmptyData();
            if (this.mPreferences.getBoolean(Preferences.GOALS_ENABLED, false)) {
                this.mIGaugeInteractor.loadGoalsConfigData(kind, currentUser, this);
            }
        } else if (this.mPreferences.getBoolean(Preferences.GOALS_ENABLED, false)) {
            this.mIGaugeInteractor.loadDataFromGoals(kind, lastWorkout, currentUser, this);
        } else if (previousWorkout == null) {
            showDataWithoutComparison(kind, lastWorkout);
        } else {
            showDataComparedToPreviousWorkout(kind, lastWorkout, previousWorkout);
        }
    }

    public void showEmptyData() {
        this.iGaugeFragment.showEmptyData();
    }

    public void showDataComparedToCurrentGoals(int kind, Workout lastWorkout, User currentUser, TrainingGoal caloriesGoals, TrainingGoal timeGoal) {
        switch (kind) {
            case 0:
                this.mIGaugeHelper.loadCaloriesComparedWithCurrentGoal(this, lastWorkout, currentUser, caloriesGoals);
                return;
            case 1:
                this.mIGaugeHelper.loadAvgCaloriesBurnedWithoutComparison(this, lastWorkout);
                return;
            case 2:
                this.mIGaugeHelper.loadTimeComparedWithCurrentGoal(this, lastWorkout, currentUser, timeGoal);
                return;
            case 3:
                this.mIGaugeHelper.loadHeartRateWithoutComparison(this, lastWorkout);
                return;
            case 4:
                this.mIGaugeHelper.loadDistanceWithoutComparison(this.mUnit, this, lastWorkout);
                return;
            case 5:
                this.mIGaugeHelper.loadSpeedWithoutComparison(this.mUnit, this, lastWorkout);
                return;
            case 6:
                this.mIGaugeHelper.loadWattsWithoutComparison(this, lastWorkout);
                return;
            default:
                return;
        }
    }

    public void loadGoalsConfigDataSuccess(int kind, TrainingGoal currentWorkoutsPerWeekGoal, TrainingGoal currentCaloriePerWorkoutGoal, TrainingGoal currentTimePerWorkoutGoal) {
        switch (kind) {
            case 0:
                this.mIGaugeHelper.loadTotalValuesPerWeekCalorieGoals(currentWorkoutsPerWeekGoal, currentCaloriePerWorkoutGoal, this);
                return;
            case 2:
                this.mIGaugeHelper.loadDataComparedToTimeGoal(currentWorkoutsPerWeekGoal, currentTimePerWorkoutGoal, this);
                return;
            default:
                return;
        }
    }

    public void showDataWithoutComparison(int kind, Workout lastWorkout) {
        this.iGaugeFragment.showImageViewNeedle();
        switch (kind) {
            case 0:
                this.mIGaugeHelper.loadCaloriesWithoutComparison(this, lastWorkout);
                return;
            case 1:
                this.mIGaugeHelper.loadAvgCaloriesBurnedWithoutComparison(this, lastWorkout);
                return;
            case 2:
                this.mIGaugeHelper.loadTimeWithoutComparison(this, lastWorkout);
                return;
            case 3:
                this.mIGaugeHelper.loadHeartRateWithoutComparison(this, lastWorkout);
                return;
            case 4:
                this.mIGaugeHelper.loadDistanceWithoutComparison(this.mUnit, this, lastWorkout);
                return;
            case 5:
                this.mIGaugeHelper.loadSpeedWithoutComparison(this.mUnit, this, lastWorkout);
                return;
            case 6:
                this.mIGaugeHelper.loadWattsWithoutComparison(this, lastWorkout);
                return;
            default:
                return;
        }
    }

    public void showDataComparedToPreviousWorkout(int kind, Workout lastWorkout, Workout preWorkout) {
        this.iGaugeFragment.showImageViewNeedle();
        switch (kind) {
            case 0:
                this.mIGaugeHelper.loadCaloriesComparedToPreviousWorkout(this, lastWorkout, preWorkout);
                return;
            case 1:
                this.mIGaugeHelper.loadAvgCalorieBurnRateComparedToPreviousWorkout(this, lastWorkout, preWorkout);
                return;
            case 2:
                this.mIGaugeHelper.compareTimeToPreviousWorkout(this, lastWorkout, preWorkout);
                return;
            case 3:
                this.mIGaugeHelper.loadHeartRateWithoutComparison(this, lastWorkout);
                return;
            case 4:
                this.mIGaugeHelper.loadDistanceComparedToPreviousWorkout(this.mUnit, this, lastWorkout, preWorkout);
                return;
            case 5:
                this.mIGaugeHelper.loadSpeedComparedToPreviousWorkout(this.mUnit, this, lastWorkout, preWorkout);
                return;
            case 6:
                this.mIGaugeHelper.loadWattsComparedToPreviousWorkout(this, lastWorkout, preWorkout);
                return;
            default:
                return;
        }
    }

    public void loadSpeedBasedOnCurrentUnit() {
        if (this.mUnit == 0) {
            this.iGaugeFragment.showDistanceLabelBasedOnCurrentUnit(R.string.home_title_speed_mph);
        } else {
            this.iGaugeFragment.showDistanceLabelBasedOnCurrentUnit(R.string.home_title_speed_kph);
        }
    }

    public void loadDistanceBasedOnCurrentUnit() {
        if (this.mUnit == 0) {
            this.iGaugeFragment.showDistanceLabelBasedOnCurrentUnit(R.string.home_title_distance_miles);
        } else {
            this.iGaugeFragment.showDistanceLabelBasedOnCurrentUnit(R.string.home_title_distance_km);
        }
    }

    public void checkData() {
    }

    public void onDistanceComparedToPreviousWorkoutSuccess(int rotation, int realPercent, String data) {
        this.iGaugeFragment.showDistanceComparedToPreviousWorkout(rotation, realPercent, data);
    }

    public void onSpeedComparedToPreviousWorkoutSuccess(int rotation, int realPercent, String data) {
        this.iGaugeFragment.showSpeedComparedToPreviousWorkout(rotation, realPercent, data);
    }

    public void onCaloriesComparedWithCurrentGoalSuccess(int rotation, int lastWorkoutCaloriesData, String goalValue, int realPercent) {
        this.iGaugeFragment.showCaloriesComparedWithCurrentGoal(rotation, String.valueOf(lastWorkoutCaloriesData), goalValue, realPercent);
    }

    public void onAvgCaloriesBurnedWithoutComparisonSuccess(String value) {
        this.iGaugeFragment.showAvgCaloriesBurnedWithoutComparison(value);
    }

    public void onWattsWithoutComparison(String value) {
        this.iGaugeFragment.showWattsWithoutComparison(value);
    }

    public void onSpeedWithoutComparison(String speed) {
        this.iGaugeFragment.showSpeedWithoutComparison(speed);
    }

    public void onDistanceWithoutComparison(String distance) {
        this.iGaugeFragment.showDistanceWithoutComparison(distance);
    }

    public void onHeartRateWithoutComparison(int heartRate) {
        this.iGaugeFragment.showHeartRateWithoutComparison(String.valueOf(heartRate));
    }

    public void onTimeComparedWithCurrentGoalSuccess(int rotation, int elapsedTime, String goalTimeValue, int realPrecent) {
        this.iGaugeFragment.showTimeComparedWithCurrentGoal(rotation, elapsedTime, goalTimeValue, realPrecent);
    }

    public void onCaloriesWithoutComparisonSuccess(int calories) {
        this.iGaugeFragment.showCaloriesWithoutComparison(calories);
    }

    public void onTimeWithoutComparisonSuccess(int time) {
        this.iGaugeFragment.showTimeWithoutComparison(time);
    }

    public void onCaloriesComparedToPreviousWorkoutSuccess(int rotation, int calories, int realPercent) {
        this.iGaugeFragment.showCaloriesComparedToPreviousWorkout(rotation, String.valueOf(calories), realPercent);
    }

    public void onWattsComparedToPreviousWorkoutSuccess(int rotation, String watts, int realPercent) {
        this.iGaugeFragment.showWattsComparedToPreviousWorkout(rotation, watts, realPercent);
    }

    public void onAvgCalorieBurnRateComparedToPreviousWorkoutSuccess(int rotation, String burnRate, int realPercent) {
        this.iGaugeFragment.showAvgCalorieBurnRateComparedToPreviousWorkout(rotation, burnRate, realPercent);
    }

    public void onCompareTimeToPreviousWorkout(int rotation, int lastTime, int realPercent) {
        this.iGaugeFragment.compareTimeToPreviousWorkout(rotation, lastTime, realPercent);
    }

    public void onLoadDataWhenGoalsEnable(int value, int totalSeconds) {
        this.iGaugeFragment.loadTxtEnableGoalButNoData(value, totalSeconds);
    }
}
