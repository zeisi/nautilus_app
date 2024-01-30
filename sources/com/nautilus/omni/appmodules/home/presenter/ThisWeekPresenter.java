package com.nautilus.omni.appmodules.home.presenter;

import android.content.Context;
import com.nautilus.omni.R;
import com.nautilus.omni.app.BasePresenter;
import com.nautilus.omni.app.Preferences;
import com.nautilus.omni.appmodules.home.domain.helpers.IThisWeekHelper;
import com.nautilus.omni.appmodules.home.domain.interactors.ThisWeekSectionInteractorContract;
import com.nautilus.omni.appmodules.home.view.ThisWeekSectionFragmentContract;
import com.nautilus.omni.model.dto.TrainingGoal;
import com.nautilus.omni.model.dto.Week;
import com.nautilus.omni.model.dto.Workout;
import com.nautilus.omni.util.Util;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class ThisWeekPresenter extends BasePresenter implements ThisWeekPresenterContract, IThisWeekHelper.OnHelperFinishListener, ThisWeekSectionInteractorContract.OnLoadData {
    public static final int AVG_CALORIES = 1;
    public static final int DISTANCE = 4;
    public static final int FREQUENCY = 6;
    public static final int HEART_RATE = 3;
    public static final int LABEL = 8;
    public static final int POWER = 7;
    public static final int SPEED = 5;
    public static final int TIME = 2;
    public static final int TOTAL_CALORIES = 0;
    IThisWeekHelper iThisWeekHelper;
    ThisWeekSectionFragmentContract iThisWeekSectionFragment;
    ThisWeekSectionInteractorContract iThisWeekSectionInteractor;
    Context mContext;

    @Inject
    public ThisWeekPresenter(Context context, IThisWeekHelper thisWeekHelper, ThisWeekSectionInteractorContract thisWeekSectionInteractor) {
        super(context);
        this.mContext = context;
        this.iThisWeekHelper = thisWeekHelper;
        this.iThisWeekSectionInteractor = thisWeekSectionInteractor;
    }

    public void setiThisWeekSectionFragment(ThisWeekSectionFragmentContract iThisWeekSectionFragment2) {
        this.iThisWeekSectionFragment = iThisWeekSectionFragment2;
    }

    public void loadFragment(int kind) {
        updateUnits();
        switch (kind) {
            case 3:
                this.iThisWeekSectionInteractor.loadAvgRateData(this);
                return;
            case 7:
                this.iThisWeekSectionInteractor.loadAvgWattsData(this);
                return;
            default:
                this.iThisWeekSectionInteractor.loadData(this.mPreferences.getInt(Preferences.USER_INDEX, -1), kind, this);
                return;
        }
    }

    public void onLoadDataSuccess(int kind, TrainingGoal currentWorkoutsPerWeekGoal, TrainingGoal currentCaloriePerWorkoutGoal, TrainingGoal currentTimePerWorkoutGoal, Week currentWeek, Week previousWeek) {
        this.iThisWeekHelper.loadInfo(kind, currentWeek, previousWeek, this.mContext, this.mUnit, this);
        showDataComparedToGoals(kind, currentWorkoutsPerWeekGoal, currentCaloriePerWorkoutGoal, currentWeek, currentTimePerWorkoutGoal);
    }

    public void showDataComparedToGoals(int kind, TrainingGoal currentWorkoutsPerWeekGoal, TrainingGoal currentCaloriePerWorkoutGoal, Week currentWeek, TrainingGoal currentTimePerWorkoutGoal) {
        if (this.mPreferences.getBoolean(Preferences.GOALS_ENABLED, false)) {
            showDataWhenGoalsEnabled(kind, currentWorkoutsPerWeekGoal, currentCaloriePerWorkoutGoal, currentWeek, currentTimePerWorkoutGoal);
        } else {
            this.iThisWeekSectionFragment.disableGoalGraphic();
        }
    }

    private void showDataWhenGoalsEnabled(int kind, TrainingGoal currentWorkoutsPerWeekGoal, TrainingGoal currentCaloriePerWorkoutGoal, Week currentWeek, TrainingGoal currentTimePerWorkoutGoal) {
        if (kind == 0 && currentCaloriePerWorkoutGoal != null && currentWorkoutsPerWeekGoal != null) {
            this.iThisWeekHelper.loadDataComparedToCalorieGoal(currentWorkoutsPerWeekGoal, currentCaloriePerWorkoutGoal, currentWeek, this);
        } else if (kind == 6 && currentWorkoutsPerWeekGoal != null) {
            this.iThisWeekHelper.loadDataComparedToWorkoutsPerWeekGoal(currentWorkoutsPerWeekGoal, currentWeek, this);
        } else if (kind != 2 || currentTimePerWorkoutGoal == null || currentWorkoutsPerWeekGoal == null) {
            this.iThisWeekSectionFragment.disableGoalGraphic();
        } else {
            this.iThisWeekHelper.loadDataComparedToTimeGoal(currentWorkoutsPerWeekGoal, currentTimePerWorkoutGoal, currentWeek, this);
        }
    }

    public void onLoadDataComparedToCalorieGoalSuccess(int totalCaloriePerWorkoutGoal, int totalWeekCalories) {
        this.iThisWeekSectionFragment.enableGoalGraphic();
        this.iThisWeekSectionFragment.showDataComparedToCalorieGoal(totalCaloriePerWorkoutGoal, totalWeekCalories);
        showHorizontalGraphicData(this.mContext.getResources().getColor(R.color.journal_week_total_calories_bars_color), totalCaloriePerWorkoutGoal, totalWeekCalories);
    }

    public void onDataComparedToWorkoutsPerWeekGoalSuccess(int currentWorkoutsPerWeekGoalValue, int totalWorkouts) {
        this.iThisWeekSectionFragment.enableGoalGraphic();
        this.iThisWeekSectionFragment.showDataComparedToWorkoutsPerWeekGoal(currentWorkoutsPerWeekGoalValue, totalWorkouts);
        showHorizontalGraphicData(this.mContext.getResources().getColor(R.color.award_light_blue_color), currentWorkoutsPerWeekGoalValue, totalWorkouts);
    }

    private void showHorizontalGraphicData(int graphicBarsColor, int totalGoalValue, int totalWeekValue) {
        ArrayList<String> graphicArrayValues = new ArrayList<>();
        graphicArrayValues.add(String.valueOf(totalWeekValue));
        this.iThisWeekSectionFragment.showHorizontalGraphicDataWhenGoalsEnabled(graphicBarsColor, graphicArrayValues, getGraphicHorizontalValues(totalGoalValue, totalWeekValue), totalGoalValue);
    }

    public void onDataComparedToTimeGoalSuccess(int goalTotalSeconds, int weekTotalWorkoutsTimeInSeconds, int totalTimePerWorkoutGoalInMinutes) {
        this.iThisWeekSectionFragment.enableGoalGraphic();
        this.iThisWeekSectionFragment.showDataComparedToTimeGoal(getTimeValue(totalTimePerWorkoutGoalInMinutes, weekTotalWorkoutsTimeInSeconds, goalTotalSeconds), getTimeTitle(totalTimePerWorkoutGoalInMinutes, weekTotalWorkoutsTimeInSeconds), weekTotalWorkoutsTimeInSeconds, goalTotalSeconds);
        showTimeHorizontalGraphicData(totalTimePerWorkoutGoalInMinutes, goalTotalSeconds, weekTotalWorkoutsTimeInSeconds);
    }

    private void showTimeHorizontalGraphicData(int totalTimePerWorkoutGoalInMinutes, int goalTotalSeconds, int weekTotalWorkoutsTimeInSeconds) {
        ArrayList<String> graphicArrayValues = new ArrayList<>();
        graphicArrayValues.add(getTimeChartValue(totalTimePerWorkoutGoalInMinutes, weekTotalWorkoutsTimeInSeconds));
        this.iThisWeekSectionFragment.showHorizontalGraphicDataWhenGoalsEnabled(this.mContext.getResources().getColor(R.color.journal_week_elapsed_time_bars_color), graphicArrayValues, getGraphicHorizontalValues(goalTotalSeconds, weekTotalWorkoutsTimeInSeconds), goalTotalSeconds);
    }

    private ArrayList<Double> getGraphicHorizontalValues(int goal, int value) {
        if (value > goal) {
            value = goal;
        }
        ArrayList<Double> horizontalGraphicValues = new ArrayList<>();
        horizontalGraphicValues.add(Double.valueOf((double) value));
        return horizontalGraphicValues;
    }

    private String getTimeChartValue(int totalTimePerWorkoutGoalInMinutes, int weekTotalWorkoutsTimeInSeconds) {
        if (totalTimePerWorkoutGoalInMinutes > 60 || weekTotalWorkoutsTimeInSeconds > 3600) {
            return Util.convertSecondsToDuration((long) weekTotalWorkoutsTimeInSeconds, false);
        }
        return Util.convertSecondsToTimeFormatInMinutesAndSeconds(weekTotalWorkoutsTimeInSeconds);
    }

    private String getTimeTitle(int totalTimePerWorkoutGoalInMinutes, int weekTotalWorkoutsTimeInSeconds) {
        if (totalTimePerWorkoutGoalInMinutes > 60 || weekTotalWorkoutsTimeInSeconds > 3600) {
            return this.mContext.getString(R.string.home_title_hourmin);
        }
        return this.mContext.getString(R.string.home_title_minsec);
    }

    private String getTimeValue(int totalTimePerWorkoutGoal, int weekTotalWorkoutsTimeInSeconds, int totalSeconds) {
        if (totalTimePerWorkoutGoal > 60 || weekTotalWorkoutsTimeInSeconds > 3600) {
            return this.mContext.getString(R.string.home_title_goal, new Object[]{Util.convertSecondsToDuration((long) totalSeconds, false)});
        }
        return this.mContext.getString(R.string.home_title_goal, new Object[]{Util.convertSecondsToTimeFormatInMinutesAndSeconds(totalSeconds)});
    }

    public void onLoadAvgRateDataSuccess(List<Workout> currentWeekWorkouts, List<Workout> previousWeekWorkouts) {
        this.iThisWeekHelper.loadHeartRateInfo(this.mContext, currentWeekWorkouts, previousWeekWorkouts, this);
    }

    public void onLoadInfoComparedWithPreviousWeek(String txtViewThisWeekNumber, String txtViewLastWeekNumber, String txtViewDataTypeTitle) {
        this.iThisWeekSectionFragment.showDataComparedWithPreviousWeek(txtViewThisWeekNumber, txtViewLastWeekNumber, txtViewDataTypeTitle);
    }

    public void onLoadAvgWattsDataSuccess(List<Workout> currentWeekWorkouts, List<Workout> previousWeekWorkouts) {
        this.iThisWeekHelper.loadWattsInfo(this.mContext, currentWeekWorkouts, previousWeekWorkouts, this);
    }
}
