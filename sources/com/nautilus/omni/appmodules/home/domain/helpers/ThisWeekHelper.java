package com.nautilus.omni.appmodules.home.domain.helpers;

import android.content.Context;
import android.util.Log;
import com.nautilus.omni.R;
import com.nautilus.omni.appmodules.home.domain.helpers.IThisWeekHelper;
import com.nautilus.omni.appmodules.journal.presenter.JournalMetricsHelper;
import com.nautilus.omni.model.dto.TrainingGoal;
import com.nautilus.omni.model.dto.Week;
import com.nautilus.omni.model.dto.Workout;
import com.nautilus.omni.util.Util;
import java.util.List;

public class ThisWeekHelper implements IThisWeekHelper {
    private static String TAG = "ThisWeekHelper";
    private Context mContext;

    public ThisWeekHelper(Context context) {
        this.mContext = context;
    }

    public void loadDataComparedToCalorieGoal(TrainingGoal currentWorkoutsPerWeekGoal, TrainingGoal currentCaloriePerWorkoutGoal, Week currentWeek, IThisWeekHelper.OnHelperFinishListener onHelperFinishListener) {
        int workoutPerWeekGoal = 0;
        int caloriePerWorkoutGoal = 0;
        try {
            workoutPerWeekGoal = Integer.parseInt(currentWorkoutsPerWeekGoal.getmGoalValue());
            caloriePerWorkoutGoal = Integer.parseInt(currentCaloriePerWorkoutGoal.getmGoalValue());
        } catch (NumberFormatException e) {
            Log.e(TAG, "Cannot parse string to int.");
        }
        onHelperFinishListener.onLoadDataComparedToCalorieGoalSuccess(workoutPerWeekGoal * caloriePerWorkoutGoal, currentWeek != null ? currentWeek.getmTotalWorkoutsCalories() : 0);
    }

    public void loadDataComparedToWorkoutsPerWeekGoal(TrainingGoal currentWorkoutsPerWeekGoal, Week currentWeek, IThisWeekHelper.OnHelperFinishListener onHelperFinishListener) {
        onHelperFinishListener.onDataComparedToWorkoutsPerWeekGoalSuccess(Integer.parseInt(currentWorkoutsPerWeekGoal.getmGoalValue()), currentWeek != null ? currentWeek.getmTotalWorkouts() : 0);
    }

    public void loadDataComparedToTimeGoal(TrainingGoal currentWorkoutsPerWeekGoal, TrainingGoal currentTimePerWorkoutGoal, Week currentWeek, IThisWeekHelper.OnHelperFinishListener onHelperFinishListener) {
        int goalTotalSeconds = 0;
        int totalTimePerWorkoutGoal = 0;
        try {
            totalTimePerWorkoutGoal = Integer.parseInt(currentWorkoutsPerWeekGoal.getmGoalValue()) * (Util.getSecondsFromTimeGoal(currentTimePerWorkoutGoal.getmGoalValue()) / 60);
            goalTotalSeconds = totalTimePerWorkoutGoal * 60;
        } catch (NumberFormatException e) {
            Log.e(TAG, "Cannot parse string to int.");
        }
        onHelperFinishListener.onDataComparedToTimeGoalSuccess(goalTotalSeconds, currentWeek != null ? currentWeek.getmTotalWorkoutsTime() : 0, totalTimePerWorkoutGoal);
    }

    public void loadInfo(int kind, Week currentWeek, Week previousWeek, Context context, int unit, IThisWeekHelper.OnHelperFinishListener onHelperFinishListener) {
        String txtViewThisWeekNumber;
        String txtViewLastWeekNumber;
        String txtViewDataTypeTitle;
        switch (kind) {
            case 0:
                txtViewThisWeekNumber = getNumberString(currentWeek, 0, context, unit);
                txtViewLastWeekNumber = getNumberString(previousWeek, 0, context, unit);
                txtViewDataTypeTitle = this.mContext.getString(R.string.home_title_total);
                break;
            case 1:
                txtViewThisWeekNumber = getNumberString(currentWeek, 1, context, unit);
                txtViewLastWeekNumber = getNumberString(previousWeek, 1, context, unit);
                txtViewDataTypeTitle = this.mContext.getString(R.string.home_title_avg);
                break;
            case 2:
                txtViewThisWeekNumber = getTimeNumberString(currentWeek, previousWeek);
                txtViewLastWeekNumber = getTimeNumberString(previousWeek, currentWeek);
                txtViewDataTypeTitle = getTimeDataTitle(currentWeek, previousWeek);
                break;
            case 3:
                txtViewThisWeekNumber = getNumberString(currentWeek, 3, context, unit);
                txtViewLastWeekNumber = getNumberString(previousWeek, 3, context, unit);
                txtViewDataTypeTitle = this.mContext.getString(R.string.home_title_avg);
                break;
            case 4:
                txtViewThisWeekNumber = getNumberString(currentWeek, 4, context, unit);
                txtViewLastWeekNumber = getNumberString(previousWeek, 4, context, unit);
                txtViewDataTypeTitle = this.mContext.getString(R.string.home_title_total);
                break;
            case 5:
                txtViewThisWeekNumber = getNumberString(currentWeek, 5, context, unit);
                txtViewLastWeekNumber = getNumberString(previousWeek, 5, context, unit);
                txtViewDataTypeTitle = this.mContext.getString(R.string.home_title_avg);
                break;
            case 6:
                txtViewThisWeekNumber = getNumberString(currentWeek, 6, context, unit);
                txtViewLastWeekNumber = getNumberString(previousWeek, 6, context, unit);
                txtViewDataTypeTitle = this.mContext.getString(R.string.home_title_number);
                break;
            case 7:
                txtViewThisWeekNumber = getNumberString(currentWeek, 7, context, unit);
                txtViewLastWeekNumber = getNumberString(previousWeek, 7, context, unit);
                txtViewDataTypeTitle = this.mContext.getString(R.string.home_title_avg);
                break;
            default:
                txtViewThisWeekNumber = "0.0";
                txtViewLastWeekNumber = "0.0";
                txtViewDataTypeTitle = Util.convertSecondsToTimeFormatInMinutesAndSeconds(0);
                break;
        }
        onHelperFinishListener.onLoadInfoComparedWithPreviousWeek(txtViewThisWeekNumber, txtViewLastWeekNumber, txtViewDataTypeTitle);
    }

    private String getNumberString(Week week, int attribute, Context context, int unit) {
        switch (attribute) {
            case 0:
                return week != null ? String.valueOf(week.getmTotalWorkoutsCalories()) : "0";
            case 1:
                if (week == null) {
                    return Util.convertValueToDecimal(0);
                }
                return String.format(context.getString(R.string.oneDecimal), new Object[]{Float.valueOf(week.getmTotalWorkoutsAvgCalBurnRate() / ((float) week.getmTotalWorkouts()))});
            case 2:
                return week != null ? getTime(week.getmTotalWorkoutsTime()) : Util.convertSecondsToTimeFormatInMinutesAndSeconds(0);
            case 4:
                return week != null ? getDistance(week, unit) : Util.convertValueToDecimal(0);
            case 5:
                return week != null ? getSpeed(week, unit) : Util.convertValueToDecimal(0);
            case 6:
                return week != null ? String.valueOf(week.getmTotalWorkouts()) : "0";
            case 7:
                return week != null ? String.valueOf(week.getmTotalWorkoutsPower()) : "0";
            default:
                return "0.0";
        }
    }

    private String getTimeNumberString(Week weekToGetTimeValue, Week weekToCompare) {
        if (weekToGetTimeValue == null) {
            return Util.convertSecondsToTimeFormatInMinutesAndSeconds(0);
        }
        if (shouldShowWeekTimeInHoursAndMinutes(weekToGetTimeValue, weekToCompare)) {
            return Util.convertSecondsToDuration((long) weekToGetTimeValue.getmTotalWorkoutsTime(), false);
        }
        return Util.convertSecondsToTimeFormatInMinutesAndSeconds(weekToGetTimeValue.getmTotalWorkoutsTime());
    }

    private boolean shouldShowWeekTimeInHoursAndMinutes(Week weekToGetTimeValue, Week weekToCompare) {
        return weekToGetTimeValue.getmTotalWorkoutsTime() > 3600 || (weekToCompare != null && weekToCompare.getmTotalWorkoutsTime() > 3600);
    }

    private String getTime(int totalCurrentWeekSeconds) {
        if (totalCurrentWeekSeconds > 3600) {
            return Util.convertSecondsToDuration((long) totalCurrentWeekSeconds, false);
        }
        return Util.convertSecondsToTimeFormatInMinutesAndSeconds(totalCurrentWeekSeconds);
    }

    private String getTimeDataTitle(Week currentWeek, Week previousWeek) {
        if (currentWeek != null && previousWeek != null) {
            return getTimeTitleFromWeek(currentWeek.getmTotalWorkoutsTime(), previousWeek.getmTotalWorkoutsTime());
        }
        if (currentWeek != null) {
            return getTimeTitleFromWeek(currentWeek.getmTotalWorkoutsTime(), 0);
        }
        if (previousWeek != null) {
            return getTimeTitleFromWeek(previousWeek.getmTotalWorkoutsTime(), 0);
        }
        return this.mContext.getString(R.string.home_title_minsec);
    }

    private String getTimeTitleFromWeek(int weekTotalSeconds, int previousWeekTotalSeconds) {
        if (shouldShowTitleInHoursAndMinutes(weekTotalSeconds, previousWeekTotalSeconds)) {
            return this.mContext.getString(R.string.home_title_hourmin);
        }
        return this.mContext.getString(R.string.home_title_minsec);
    }

    private boolean shouldShowTitleInHoursAndMinutes(int weekTotalSeconds, int previousWeekTotalSeconds) {
        return weekTotalSeconds > 3600 || previousWeekTotalSeconds > 3600;
    }

    public void loadHeartRateInfo(Context context, List<Workout> currentWeekWorkouts, List<Workout> previousWeekWorkouts, IThisWeekHelper.OnHelperFinishListener onHelperFinishListener) {
        String txtViewThisWeekNumber;
        String txtViewLastWeekNumber;
        double currentHeartAvg = JournalMetricsHelper.getWorkoutsTotalAvgHeartRate(currentWeekWorkouts);
        double previousHeartAvg = JournalMetricsHelper.getWorkoutsTotalAvgHeartRate(previousWeekWorkouts);
        if (currentHeartAvg > 0.0d) {
            txtViewThisWeekNumber = String.format(context.getString(R.string.oneDecimal), new Object[]{Double.valueOf(currentHeartAvg)});
        } else {
            txtViewThisWeekNumber = Util.convertValueToDecimal(0);
        }
        if (previousHeartAvg > 0.0d) {
            txtViewLastWeekNumber = String.format(context.getString(R.string.oneDecimal), new Object[]{Double.valueOf(previousHeartAvg)});
        } else {
            txtViewLastWeekNumber = Util.convertValueToDecimal(0);
        }
        onHelperFinishListener.onLoadInfoComparedWithPreviousWeek(txtViewThisWeekNumber, txtViewLastWeekNumber, this.mContext.getString(R.string.home_title_avg));
    }

    public void loadWattsInfo(Context context, List<Workout> currentWeekWorkouts, List<Workout> previousWeekWorkouts, IThisWeekHelper.OnHelperFinishListener onHelperFinishListener) {
        String txtViewThisWeekNumber;
        String txtViewLastWeekNumber;
        double currentPowerAvg = JournalMetricsHelper.getWorkoutsTotalAvgPower(currentWeekWorkouts);
        double previousPowerAvg = JournalMetricsHelper.getWorkoutsTotalAvgPower(previousWeekWorkouts);
        if (currentPowerAvg > 0.0d) {
            txtViewThisWeekNumber = String.format(context.getString(R.string.oneDecimal), new Object[]{Double.valueOf(currentPowerAvg)});
        } else {
            txtViewThisWeekNumber = Util.convertValueToDecimal(0);
        }
        if (previousPowerAvg > 0.0d) {
            txtViewLastWeekNumber = String.format(context.getString(R.string.oneDecimal), new Object[]{Double.valueOf(previousPowerAvg)});
        } else {
            txtViewLastWeekNumber = Util.convertValueToDecimal(0);
        }
        onHelperFinishListener.onLoadInfoComparedWithPreviousWeek(txtViewThisWeekNumber, txtViewLastWeekNumber, this.mContext.getString(R.string.home_title_avg));
    }

    private String getSpeed(Week week, int unit) {
        if (unit == 0) {
            return Util.getWeekAvgSpeedInMilesAsStringOneDecimal(week);
        }
        return Util.getWeekAvgSpeedInKmAsStringOneDecimal(week);
    }

    private String getDistance(Week week, int unit) {
        if (unit == 0) {
            return Util.getWeekDistanceInMilesAsStringOneDecimal(week);
        }
        return Util.getWeekDistanceInKmAsStringOneDecimal(week);
    }
}
