package com.nautilus.omni.appmodules.home.domain.helpers;

import android.util.Log;
import com.nautilus.omni.appmodules.home.domain.helpers.IGaugeHelper;
import com.nautilus.omni.model.dto.TrainingGoal;
import com.nautilus.omni.model.dto.User;
import com.nautilus.omni.model.dto.Workout;
import com.nautilus.omni.util.Util;

public class GaugeHelper implements IGaugeHelper {
    private static final int MAX_GAUGE_ROTATION = 276;
    private static final String TAG = "IGaugeHelper";

    public int getPercentageGauge(int last, int previous) {
        if (previous == 0) {
            return 27600;
        }
        return ((float) last) / ((float) previous) > 1.0f ? MAX_GAUGE_ROTATION : (last * MAX_GAUGE_ROTATION) / previous;
    }

    public int getPercentageGauge(float last, float previous) {
        float f = 276.0f;
        if (previous == 0.0f) {
            return 27600;
        }
        if (last / previous <= 1.0f) {
            f = (276.0f * last) / previous;
        }
        return (int) f;
    }

    public int getRealPercentage(int last, int previous) {
        if (previous == 0) {
            return 100;
        }
        return (last * 100) / previous;
    }

    public int getRealPercentage(float last, float previous) {
        if (previous == 0.0f) {
            return 100;
        }
        return (int) ((100.0f * last) / previous);
    }

    public void loadDistanceComparedToPreviousWorkout(int unit, IGaugeHelper.OnWorkoutListener onWorkoutListener, Workout lastWorkout, Workout previousWorkout) {
        String data;
        int rotation = getPercentageGauge(lastWorkout.getmDistance(), previousWorkout.getmDistance());
        int realPercent = getRealPercentage(lastWorkout.getmDistance(), previousWorkout.getmDistance());
        if (unit == 0) {
            data = Util.getWorkoutDistanceInMilesAsString(lastWorkout);
        } else {
            data = Util.getWorkoutDistanceInKilometersAsString(lastWorkout);
        }
        onWorkoutListener.onDistanceComparedToPreviousWorkoutSuccess(rotation, realPercent, data);
    }

    public void loadSpeedComparedToPreviousWorkout(int unit, IGaugeHelper.OnWorkoutListener onWorkoutListener, Workout lastWorkout, Workout previousWorkou) {
        String data;
        int rotation = getPercentageGauge(lastWorkout.getmAvgSpeed(), previousWorkou.getmAvgSpeed());
        int realPercent = getRealPercentage(lastWorkout.getmAvgSpeed(), previousWorkou.getmAvgSpeed());
        if (unit == 0) {
            data = Util.getWorkoutSpeedInMilesAsString(lastWorkout);
        } else {
            data = Util.getWorkoutSpeedInKilometersAsString(lastWorkout);
        }
        onWorkoutListener.onSpeedComparedToPreviousWorkoutSuccess(rotation, realPercent, data);
    }

    public void loadCaloriesComparedWithCurrentGoal(IGaugeHelper.OnWorkoutListener onWorkoutListener, Workout lastWorkout, User currentUser, TrainingGoal calorieGoal) {
        onWorkoutListener.onCaloriesComparedWithCurrentGoalSuccess(getPercentageGauge(lastWorkout.getmCalories(), Integer.valueOf(calorieGoal.getmGoalValue()).intValue()), lastWorkout.getmCalories(), calorieGoal.getmGoalValue(), getRealPercentage(lastWorkout.getmCalories(), Integer.valueOf(calorieGoal.getmGoalValue()).intValue()));
    }

    public void loadAvgCaloriesBurnedWithoutComparison(IGaugeHelper.OnWorkoutListener onWorkoutListener, Workout lastWorkout) {
        onWorkoutListener.onAvgCaloriesBurnedWithoutComparisonSuccess(Util.getAvgCalorieBurnRateAsStringTwoDecimal(lastWorkout));
    }

    public void loadWattsWithoutComparison(IGaugeHelper.OnWorkoutListener onWorkoutListener, Workout lastWorkout) {
        onWorkoutListener.onWattsWithoutComparison(Util.getWorkoutPowerAsStringTwoDecimals(lastWorkout));
    }

    public void loadSpeedWithoutComparison(int unit, IGaugeHelper.OnWorkoutListener onWorkoutListener, Workout lastWorkout) {
        if (unit == 0) {
            onWorkoutListener.onSpeedWithoutComparison(Util.getWorkoutSpeedInMilesAsString(lastWorkout));
        } else {
            onWorkoutListener.onSpeedWithoutComparison(Util.getWorkoutSpeedInKilometersAsString(lastWorkout));
        }
    }

    public void loadDistanceWithoutComparison(int unit, IGaugeHelper.OnWorkoutListener onWorkoutListener, Workout lastWorkout) {
        if (unit == 0) {
            onWorkoutListener.onDistanceWithoutComparison(Util.getWorkoutDistanceInMilesAsString(lastWorkout));
        } else {
            onWorkoutListener.onDistanceWithoutComparison(Util.getWorkoutDistanceInKilometersAsString(lastWorkout));
        }
    }

    public void loadHeartRateWithoutComparison(IGaugeHelper.OnWorkoutListener onWorkoutListener, Workout lastWorkout) {
        onWorkoutListener.onHeartRateWithoutComparison(lastWorkout.getmAvgHeartRate());
    }

    public void loadTimeComparedWithCurrentGoal(IGaugeHelper.OnWorkoutListener onWorkoutListener, Workout lastWorkout, User currentUser, TrainingGoal timeGoal) {
        onWorkoutListener.onTimeComparedWithCurrentGoalSuccess(getPercentageGauge(lastWorkout.getmElapsedTime(), Util.getSecondsFromTimeGoal(timeGoal.getmGoalValue())), lastWorkout.getmElapsedTime(), timeGoal.getmGoalValue(), getRealPercentage(lastWorkout.getmElapsedTime(), Util.getSecondsFromTimeGoal(timeGoal.getmGoalValue())));
    }

    public void loadCaloriesWithoutComparison(IGaugeHelper.OnWorkoutListener onWorkoutListener, Workout lastWorkout) {
        onWorkoutListener.onCaloriesWithoutComparisonSuccess(lastWorkout.getmCalories());
    }

    public void loadTimeWithoutComparison(IGaugeHelper.OnWorkoutListener onWorkoutListener, Workout lastWorkout) {
        onWorkoutListener.onTimeWithoutComparisonSuccess(lastWorkout.getmElapsedTime());
    }

    public void loadCaloriesComparedToPreviousWorkout(IGaugeHelper.OnWorkoutListener onWorkoutListener, Workout lastWorkout, Workout previousWorkout) {
        onWorkoutListener.onCaloriesComparedToPreviousWorkoutSuccess(getPercentageGauge(lastWorkout.getmCalories(), previousWorkout.getmCalories()), lastWorkout.getmCalories(), getRealPercentage(lastWorkout.getmCalories(), previousWorkout.getmCalories()));
    }

    public void loadWattsComparedToPreviousWorkout(IGaugeHelper.OnWorkoutListener onWorkoutListener, Workout lastWorkout, Workout previousWorkout) {
        onWorkoutListener.onWattsComparedToPreviousWorkoutSuccess(getPercentageGauge(lastWorkout.getmAvgPower(), previousWorkout.getmAvgPower()), Util.getWorkoutPowerAsStringTwoDecimals(lastWorkout), getRealPercentage(lastWorkout.getmAvgPower(), previousWorkout.getmAvgPower()));
    }

    public void loadAvgCalorieBurnRateComparedToPreviousWorkout(IGaugeHelper.OnWorkoutListener onWorkoutListener, Workout lastWorkout, Workout previousWorkout) {
        onWorkoutListener.onAvgCalorieBurnRateComparedToPreviousWorkoutSuccess(getPercentageGauge(lastWorkout.getmAvgCalorieBurnRate(), previousWorkout.getmAvgCalorieBurnRate()), Util.getAvgCalorieBurnRateAsStringTwoDecimal(lastWorkout), getRealPercentage(lastWorkout.getmAvgCalorieBurnRate(), previousWorkout.getmAvgCalorieBurnRate()));
    }

    public void compareTimeToPreviousWorkout(IGaugeHelper.OnWorkoutListener onWorkoutListener, Workout lastWorkout, Workout previousWorkout) {
        onWorkoutListener.onCompareTimeToPreviousWorkout(getPercentageGauge(lastWorkout.getmElapsedTime(), previousWorkout.getmElapsedTime()), lastWorkout.getmElapsedTime(), getRealPercentage(lastWorkout.getmElapsedTime(), previousWorkout.getmElapsedTime()));
    }

    public void loadTotalValuesPerWeekCalorieGoals(TrainingGoal currentWorkoutsPerWeekGoal, TrainingGoal currentCaloriePerWorkoutGoal, IGaugeHelper.OnWorkoutListener onWorkoutLister) {
        int workoutPerWeekGoal = 0;
        int caloriePerWorkoutGoal = 0;
        try {
            workoutPerWeekGoal = Integer.parseInt(currentWorkoutsPerWeekGoal.getmGoalValue());
            caloriePerWorkoutGoal = Integer.parseInt(currentCaloriePerWorkoutGoal.getmGoalValue());
        } catch (NumberFormatException e) {
            Log.e(TAG, "Cannot parse string to int.");
        }
        onWorkoutLister.onLoadDataWhenGoalsEnable(workoutPerWeekGoal * caloriePerWorkoutGoal, 0);
    }

    public void loadDataComparedToTimeGoal(TrainingGoal currentWorkoutsPerWeekGoal, TrainingGoal currentTimePerWorkoutGoal, IGaugeHelper.OnWorkoutListener onWorkoutLister) {
        int totalSeconds = 0;
        int totalTimePerWorkoutGoal = 0;
        try {
            totalTimePerWorkoutGoal = Integer.parseInt(currentWorkoutsPerWeekGoal.getmGoalValue()) * (Util.getSecondsFromTimeGoal(currentTimePerWorkoutGoal.getmGoalValue()) / 60);
            totalSeconds = totalTimePerWorkoutGoal * 60;
        } catch (NumberFormatException e) {
            Log.e(TAG, "Cannot parse string to int.");
        }
        onWorkoutLister.onLoadDataWhenGoalsEnable(totalTimePerWorkoutGoal, totalSeconds);
    }
}
