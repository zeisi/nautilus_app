package com.nautilus.underarmourconnection.errorhandling;

import com.nautilus.underarmourconnection.services.workouts.WorkoutUnits;
import org.json.JSONException;

public class UnitTypeValidator {
    public static String validateDistanceUnit(Object objectValue) throws JSONException {
        String distanceUnitValue = (String) ExceptionUtil.validateGenericObject(objectValue, String.class);
        if (distanceUnitValue.equals(WorkoutUnits.DISTANCE_KM) || distanceUnitValue.equals(WorkoutUnits.DISTANCE_MILES)) {
            return distanceUnitValue;
        }
        throw new JSONException(UnderArmourErrorHandler.WRONG_UNIT_TYPE);
    }

    public static String validateSpeedUnit(Object objectValue) throws JSONException {
        String speedUnitValue = (String) ExceptionUtil.validateGenericObject(objectValue, String.class);
        if (speedUnitValue.equals(WorkoutUnits.SPEED_KPH) || speedUnitValue.equals(WorkoutUnits.SPEED_MPH)) {
            return speedUnitValue;
        }
        throw new JSONException(UnderArmourErrorHandler.WRONG_UNIT_TYPE);
    }

    public static String validateElapsedTimeUnit(Object objectValue) throws JSONException {
        String elapsedTimeUnitValue = (String) ExceptionUtil.validateGenericObject(objectValue, String.class);
        if (elapsedTimeUnitValue.equals(WorkoutUnits.ELAPSED_TIME_MINUTES) || elapsedTimeUnitValue.equals(WorkoutUnits.ELAPSED_TIME_SECONDS)) {
            return elapsedTimeUnitValue;
        }
        throw new JSONException(UnderArmourErrorHandler.WRONG_UNIT_TYPE);
    }

    public static String validateMetbolicEnergyUnit(Object objectValue) throws JSONException {
        String metabolicEnergyUnitValue = (String) ExceptionUtil.validateGenericObject(objectValue, String.class);
        if (metabolicEnergyUnitValue.equals(WorkoutUnits.METABOLIC_ENERGY_CALORIES) || metabolicEnergyUnitValue.equals(WorkoutUnits.METABOLIC_ENERGY_JULES)) {
            return metabolicEnergyUnitValue;
        }
        throw new JSONException(UnderArmourErrorHandler.WRONG_UNIT_TYPE);
    }
}
