package com.nautilus.omni.appmodules.journal.presenter;

import com.nautilus.omni.model.dto.Workout;
import com.nautilus.omni.util.Util;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

public class JournalMetricsHelper {
    public static double getWorkoutsTotalElapsedTime(List<Workout> workoutsList) {
        double totalElapsedTime = 0.0d;
        for (Workout workout : workoutsList) {
            totalElapsedTime += (double) workout.getmElapsedTime();
        }
        return totalElapsedTime;
    }

    public static double getWorkoutsTotalCalories(List<Workout> workoutsList) {
        double totalCalories = 0.0d;
        for (Workout workout : workoutsList) {
            totalCalories += (double) workout.getmCalories();
        }
        return totalCalories;
    }

    public static double getWorkoutsTotalAvgHeartRate(List<Workout> workoutsList) {
        double totalAvgHeartRate = 0.0d;
        int workoutsWithAvgHeartRate = getWorkoutsNumberWithAvgHeartRate(workoutsList);
        for (Workout workout : workoutsList) {
            totalAvgHeartRate += (double) workout.getmAvgHeartRate();
        }
        if (workoutsWithAvgHeartRate > 0) {
            return totalAvgHeartRate / ((double) workoutsWithAvgHeartRate);
        }
        return totalAvgHeartRate;
    }

    public static double getWorkoutsTotalAvgPower(List<Workout> workoutsList) {
        double totalAvgPower = 0.0d;
        int workoutsWithAvgPower = getWorkoutsNumberWithAvgPower(workoutsList);
        for (Workout workout : workoutsList) {
            totalAvgPower += Util.getWorkoutPower(workout);
        }
        if (workoutsWithAvgPower > 0) {
            return totalAvgPower / ((double) workoutsWithAvgPower);
        }
        return totalAvgPower;
    }

    private static int getWorkoutsNumberWithAvgHeartRate(List<Workout> workoutsList) {
        int workoutsWithHeartRateCounter = 0;
        for (Workout workout : workoutsList) {
            if (workout.getmAvgHeartRate() > 0) {
                workoutsWithHeartRateCounter++;
            }
        }
        return workoutsWithHeartRateCounter;
    }

    private static int getWorkoutsNumberWithAvgPower(List<Workout> workoutsList) {
        int workoutsWithPowerCounter = 0;
        for (Workout workout : workoutsList) {
            if (workout.getmAvgPower() > 0) {
                workoutsWithPowerCounter++;
            }
        }
        return workoutsWithPowerCounter;
    }

    public static double getWorkoutsTotalDistanceInMiles(List<Workout> workoutsList) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##", new DecimalFormatSymbols(Locale.US));
        double totalDistance = 0.0d;
        for (Workout workout : workoutsList) {
            totalDistance += Util.getWorkoutDistanceInMiles(workout);
        }
        return Double.valueOf(decimalFormat.format(totalDistance)).doubleValue();
    }

    public static double getWorkoutsTotalDistanceInKilometers(List<Workout> workoutsList) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##", new DecimalFormatSymbols(Locale.US));
        double totalDistance = 0.0d;
        for (Workout workout : workoutsList) {
            totalDistance += Util.getWorkoutDistanceInKilometers(workout);
        }
        return Double.valueOf(decimalFormat.format(totalDistance)).doubleValue();
    }

    public static double getWorkoutsTotalAvgSpeedInMPH(List<Workout> workoutsList) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##", new DecimalFormatSymbols(Locale.US));
        double totalAvgSpeed = 0.0d;
        for (Workout workout : workoutsList) {
            totalAvgSpeed += Util.getWorkoutSpeedInMiles(workout);
        }
        if (workoutsList.size() > 0) {
            totalAvgSpeed /= (double) workoutsList.size();
        }
        return Double.valueOf(decimalFormat.format(totalAvgSpeed)).doubleValue();
    }

    public static double getWorkoutsTotalAvgSpeedInKPH(List<Workout> workoutsList) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##", new DecimalFormatSymbols(Locale.US));
        double totalAvgSpeed = 0.0d;
        for (Workout workout : workoutsList) {
            totalAvgSpeed += Util.getWorkoutSpeedInKilometers(workout);
        }
        if (workoutsList.size() > 0) {
            totalAvgSpeed /= (double) workoutsList.size();
        }
        return Double.valueOf(decimalFormat.format(totalAvgSpeed)).doubleValue();
    }
}
