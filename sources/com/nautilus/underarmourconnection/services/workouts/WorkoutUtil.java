package com.nautilus.underarmourconnection.services.workouts;

import com.nautilus.underarmourconnection.errorhandling.ExceptionUtil;
import com.nautilus.underarmourconnection.errorhandling.UnitTypeValidator;
import java.util.Date;
import java.util.TimeZone;
import org.json.JSONException;
import org.json.JSONObject;

public class WorkoutUtil {
    public static final double JOULES_IN_CALORIE = 4184.0d;
    public static final double METERS_PER_KM = 1000.0d;
    public static final double METERS_PER_MILE = 1609.34d;
    public static final double SECONDS_IN_HOUR = 3600.0d;
    public static final double SECONDS_IN_MINUTE = 60.0d;

    public static Integer getIntegerValue(JSONObject jsonObject, String key) throws JSONException {
        ExceptionUtil.validateIfParametersMissing(true, jsonObject, key);
        return (Integer) ExceptionUtil.validateGenericObject(jsonObject.get(key), Integer.class);
    }

    public static Long getLongValue(JSONObject jsonObject, String key) throws JSONException {
        ExceptionUtil.validateIfParametersMissing(true, jsonObject, key);
        return (Long) ExceptionUtil.validateGenericObject(jsonObject.get(key), Long.class);
    }

    public static String getStringValue(JSONObject jsonObject, String key) throws JSONException {
        ExceptionUtil.validateIfParametersMissing(true, jsonObject, key);
        return (String) ExceptionUtil.validateGenericObject(jsonObject.get(key), String.class);
    }

    public static TimeZone getTimeZone(JSONObject jsonObject) throws JSONException {
        ExceptionUtil.validateIfParametersMissing(true, jsonObject, WorkoutKeys.TIME_ZONE);
        return (TimeZone) ExceptionUtil.validateGenericObject(jsonObject.get(WorkoutKeys.TIME_ZONE), TimeZone.class);
    }

    public static Date getStartTime(JSONObject jsonObject) throws JSONException {
        ExceptionUtil.validateIfParametersMissing(true, jsonObject, WorkoutKeys.START_TIME);
        return new Date(((Long) ExceptionUtil.validateGenericObject(jsonObject.get(WorkoutKeys.START_TIME), Long.class)).longValue());
    }

    public static Date getCreateTime() throws JSONException {
        return new Date();
    }

    public static Integer getAvgHeartRate(JSONObject jsonObject) throws JSONException {
        ExceptionUtil.validateIfParametersMissing(false, jsonObject, WorkoutKeys.HEART_RATE_AVG);
        try {
            return (Integer) ExceptionUtil.validateGenericObject(jsonObject.get(WorkoutKeys.HEART_RATE_AVG), Integer.class);
        } catch (JSONException exception) {
            return (Integer) ExceptionUtil.getException(exception, true);
        }
    }

    public static Double getAvgPower(JSONObject jsonObject) throws JSONException {
        ExceptionUtil.validateIfParametersMissing(false, jsonObject, WorkoutKeys.POWER_AVG);
        try {
            return (Double) ExceptionUtil.validateGenericObject(jsonObject.get(WorkoutKeys.POWER_AVG), Double.class);
        } catch (JSONException exception) {
            return (Double) ExceptionUtil.getException(exception, false);
        }
    }

    public static Double getElapsedTimePreparedForUnderArmourApi(JSONObject jsonObject) throws JSONException {
        boolean z;
        ExceptionUtil.validateIfParametersMissing(jsonObject, WorkoutKeys.ELAPSED_TIME, WorkoutKeys.ELAPSED_TIME_UNIT);
        try {
            Double elapsedTimeValue = (Double) ExceptionUtil.validateGenericObject(jsonObject.get(WorkoutKeys.ELAPSED_TIME), Double.class);
            if (UnitTypeValidator.validateElapsedTimeUnit(jsonObject.get(WorkoutKeys.ELAPSED_TIME_UNIT)).equals(WorkoutUnits.ELAPSED_TIME_MINUTES)) {
                z = true;
            } else {
                z = false;
            }
            return convertElapsedTimeToSeconds(elapsedTimeValue, z);
        } catch (JSONException exception) {
            return (Double) ExceptionUtil.getException(exception, false);
        }
    }

    public static Double getSpeedPreparedForUnderArmourApi(JSONObject jsonObject) throws JSONException {
        boolean z;
        ExceptionUtil.validateIfParametersMissing(jsonObject, WorkoutKeys.SPEED_AVG, WorkoutKeys.SPEED_UNIT);
        try {
            Double speedValue = (Double) ExceptionUtil.validateGenericObject(Double.valueOf(jsonObject.getDouble(WorkoutKeys.SPEED_AVG)), Double.class);
            if (UnitTypeValidator.validateSpeedUnit(jsonObject.getString(WorkoutKeys.SPEED_UNIT)).equals(WorkoutUnits.SPEED_MPH)) {
                z = true;
            } else {
                z = false;
            }
            return convertSpeedToMetersPerSecond(speedValue, z);
        } catch (JSONException exception) {
            return (Double) ExceptionUtil.getException(exception, false);
        }
    }

    public static Double getDistancePreparedForUnderArmourApi(JSONObject jsonObject) throws JSONException {
        boolean z;
        ExceptionUtil.validateIfParametersMissing(jsonObject, WorkoutKeys.DISTANCE, WorkoutKeys.DISTANCE_UNIT);
        try {
            Double elapsedTimeValue = (Double) ExceptionUtil.validateGenericObject(Double.valueOf(jsonObject.getDouble(WorkoutKeys.DISTANCE)), Double.class);
            if (UnitTypeValidator.validateDistanceUnit(jsonObject.getString(WorkoutKeys.DISTANCE_UNIT)).equals(WorkoutUnits.DISTANCE_MILES)) {
                z = true;
            } else {
                z = false;
            }
            return convertDistanceToMeters(elapsedTimeValue, z);
        } catch (JSONException exception) {
            return (Double) ExceptionUtil.getException(exception, false);
        }
    }

    public static Double getMetabolicEnergyPreparedForUnderArmourApi(JSONObject jsonObject) throws JSONException {
        boolean z;
        ExceptionUtil.validateIfParametersMissing(jsonObject, WorkoutKeys.METABOLIC_ENERGY_TOTAL, WorkoutKeys.METABOLIC_ENERGY_TOTAL_UNIT);
        try {
            Double energyValue = (Double) ExceptionUtil.validateGenericObject(Double.valueOf(jsonObject.getDouble(WorkoutKeys.METABOLIC_ENERGY_TOTAL)), Double.class);
            if (UnitTypeValidator.validateMetbolicEnergyUnit(jsonObject.getString(WorkoutKeys.METABOLIC_ENERGY_TOTAL_UNIT)).equals(WorkoutUnits.METABOLIC_ENERGY_CALORIES)) {
                z = true;
            } else {
                z = false;
            }
            return convertMetabolicEnergyToJules(energyValue, z);
        } catch (JSONException exception) {
            return (Double) ExceptionUtil.getException(exception, false);
        }
    }

    private static Double convertElapsedTimeToSeconds(Double elapsedTimeValue, boolean isInMinutes) {
        return Double.valueOf(isInMinutes ? elapsedTimeValue.doubleValue() * 60.0d : elapsedTimeValue.doubleValue());
    }

    private static Double convertSpeedToMetersPerSecond(Double speedValue, boolean isInMPH) {
        double doubleValue;
        if (isInMPH) {
            doubleValue = (speedValue.doubleValue() * 1609.34d) / 3600.0d;
        } else {
            doubleValue = (speedValue.doubleValue() * 1000.0d) / 3600.0d;
        }
        return Double.valueOf(doubleValue);
    }

    private static Double convertDistanceToMeters(Double distanceValue, boolean isInMiles) {
        return Double.valueOf(isInMiles ? distanceValue.doubleValue() * 1609.34d : distanceValue.doubleValue() * 1000.0d);
    }

    private static Double convertMetabolicEnergyToJules(Double metabolicEnergyValue, boolean isInCalories) {
        return Double.valueOf(isInCalories ? metabolicEnergyValue.doubleValue() * 4184.0d : metabolicEnergyValue.doubleValue());
    }
}
