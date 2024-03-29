package com.ua.sdk.datapoint;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class BaseDataTypes {
    public static final DataType[] ALL_BASE_TYPE = {TYPE_LOCATION, TYPE_ELEVATION, TYPE_HEART_RATE, TYPE_HEIGHT, TYPE_BODY_MASS, TYPE_SPEED, TYPE_DISTANCE, TYPE_ENERGY_EXPENDED, TYPE_ENERGY_CONSUMED, TYPE_STEPS, TYPE_WILLPOWER, TYPE_DISTANCE_SUMMARY, TYPE_ACTIVE_TIME_SUMMARY, TYPE_ENERGY_EXPENDED_SUMMARY, TYPE_ENERGY_CONSUMED_SUMMARY, TYPE_BODY_MASS_SUMMARY, TYPE_STEPS_SUMMARY, TYPE_HEART_BEAT, TYPE_CYCLING_CADENCE, TYPE_STRIDE_LENGTH, TYPE_AIR_TEMPERATURE, TYPE_RUN_CADENCE, TYPE_CYCLING_POWER, TYPE_CYCLING_POWER_BALANCE, TYPE_ACCUMULATED_TORQUE, TYPE_WHEEL_REVOLUTIONS, TYPE_CRANK_REVOLUTIONS, TYPE_EXTREME_FORCES, TYPE_EXTREME_TORQUE, TYPE_EXTREME_ANGLES, TYPE_TOP_DEAD_SPOT_ANGLE, TYPE_BOTTOM_DEAD_SPOT_ANGLE, TYPE_POSTURE, TYPE_BEARING, TYPE_ELEVATION_SUMMARY, TYPE_HEART_RATE_SUMMARY, TYPE_RUN_CADENCE_SUMMARY, TYPE_CYCLING_POWER_SUMMARY, TYPE_SESSIONS_SUMMARY, TYPE_SLEEP_SUMMARY};
    public static final Map<String, DataType> ALL_BASE_TYPE_MAP = new HashMap<String, DataType>() {
        {
            for (DataType dataType : BaseDataTypes.ALL_BASE_TYPE) {
                put(dataType.getId(), dataType);
            }
        }
    };
    public static final DataUnits[] ALL_BASE_UNITS = {UNITS_METERS, UNITS_DEGREES, UNITS_KILOGRAMS, UNITS_JOULES, UNITS_METERS_PER_SECOND, UNITS_BEATS_PER_MINUTE, UNITS_REVOLUTIONS_PER_MINUTE, UNITS_CELSIUS, UNITS_WATT, UNITS_PERCENTAGE, UNITS_NEWTON, UNITS_NEWTON_METERS, UNITS_SECONDS};
    private static final String DATA_TYPE_FLOAT = "float";
    private static final String DATA_TYPE_INT = "int";
    public static final DataField FIELD_ACCUMULATED_TORQUE = new DataFieldImpl(ID_ACCUMULATED_TORQUE, DATA_TYPE_FLOAT, UNITS_NEWTON);
    public static final DataField FIELD_ACTIVITY_TIME_SUM = new DataFieldImpl("activity_time_sum", DATA_TYPE_FLOAT, UNITS_SECONDS);
    public static final DataField FIELD_AIR_TEMPERATURE = new DataFieldImpl(ID_AIR_TEMPERATURE, DATA_TYPE_FLOAT, UNITS_CELSIUS);
    public static final DataField FIELD_BEARING = new DataFieldImpl(ID_BEARING, DATA_TYPE_FLOAT, UNITS_DEGREES);
    public static final DataField FIELD_BODY_MASS = new DataFieldImpl(ID_BODY_MASS, DATA_TYPE_FLOAT, UNITS_METERS);
    public static final DataField FIELD_BOTTOM_DEAD_SPOT_ANGLE = new DataFieldImpl(ID_BOTTOM_DEAD_SPOT_ANGLE, DATA_TYPE_FLOAT, UNITS_DEGREES);
    public static final DataField FIELD_CRANK_REVOLUTIONS = new DataFieldImpl(ID_CRANK_REVOLUTIONS, DATA_TYPE_INT, (DataUnits) null);
    public static final DataField FIELD_CYCLING_CADENCE = new DataFieldImpl(ID_CYCLING_CADENCE, DATA_TYPE_FLOAT, UNITS_REVOLUTIONS_PER_MINUTE);
    public static final DataField FIELD_CYCLING_POWER = new DataFieldImpl(ID_CYCLING_POWER, DATA_TYPE_FLOAT, UNITS_WATT);
    public static final DataField FIELD_CYCLING_POWER_AVG = new DataFieldImpl("cycling_power_avg", DATA_TYPE_FLOAT, UNITS_WATT);
    public static final DataField FIELD_CYCLING_POWER_BALANCE = new DataFieldImpl(ID_CYCLING_POWER_BALANCE, DATA_TYPE_FLOAT, UNITS_PERCENTAGE);
    public static final DataField FIELD_CYCLING_POWER_MAX = new DataFieldImpl("cycling_power_max", DATA_TYPE_FLOAT, UNITS_WATT);
    public static final DataField FIELD_CYCLING_POWER_MIN = new DataFieldImpl("cycling_power_min", DATA_TYPE_FLOAT, UNITS_WATT);
    public static final DataField FIELD_DISTANCE = new DataFieldImpl("distance", DATA_TYPE_FLOAT, UNITS_METERS);
    public static final DataField FIELD_DISTANCE_SUM = new DataFieldImpl("distance_sum", DATA_TYPE_FLOAT, UNITS_METERS);
    public static final DataField FIELD_ELEVATION = new DataFieldImpl(ID_ELEVATION, DATA_TYPE_FLOAT, UNITS_METERS);
    public static final DataField FIELD_ELEVATION_AVG = new DataFieldImpl("elevation_avg", DATA_TYPE_FLOAT, UNITS_METERS);
    public static final DataField FIELD_ELEVATION_GAIN = new DataFieldImpl("elevation_gain", DATA_TYPE_FLOAT, UNITS_METERS);
    public static final DataField FIELD_ELEVATION_MAX = new DataFieldImpl("elevation_max", DATA_TYPE_FLOAT, UNITS_METERS);
    public static final DataField FIELD_ELEVATION_MIN = new DataFieldImpl("elevation_min", DATA_TYPE_FLOAT, UNITS_METERS);
    public static final DataField FIELD_ENERGY_CONSUMED = new DataFieldImpl(ID_ENERGY_CONSUMED, DATA_TYPE_FLOAT, UNITS_JOULES);
    public static final DataField FIELD_ENERGY_CONSUMED_SUM = new DataFieldImpl("energy_consumed_sum", DATA_TYPE_FLOAT, UNITS_JOULES);
    public static final DataField FIELD_ENERGY_EXPENDED = new DataFieldImpl("energy_expended", DATA_TYPE_FLOAT, UNITS_JOULES);
    public static final DataField FIELD_ENERGY_EXPENDED_SUM = new DataFieldImpl("energy_expended_sum", DATA_TYPE_FLOAT, UNITS_JOULES);
    public static final DataField FIELD_FAT_MASS_AVG = new DataFieldImpl("fat_mass_avg", DATA_TYPE_FLOAT, UNITS_KILOGRAMS);
    public static final DataField FIELD_FAT_MASS_END = new DataFieldImpl("fat_mass_end", DATA_TYPE_FLOAT, UNITS_KILOGRAMS);
    public static final DataField FIELD_FAT_MASS_MAX = new DataFieldImpl("fat_mass_max", DATA_TYPE_FLOAT, UNITS_KILOGRAMS);
    public static final DataField FIELD_FAT_MASS_MIN = new DataFieldImpl("fat_mass_min", DATA_TYPE_FLOAT, UNITS_KILOGRAMS);
    public static final DataField FIELD_FAT_MASS_START = new DataFieldImpl("fat_mass_start", DATA_TYPE_FLOAT, UNITS_KILOGRAMS);
    public static final DataField FIELD_HEART_BEAT = new DataFieldImpl(ID_HEART_BEAT, DATA_TYPE_INT, (DataUnits) null);
    public static final DataField FIELD_HEART_RATE = new DataFieldImpl(ID_HEART_RATE, DATA_TYPE_FLOAT, UNITS_BEATS_PER_MINUTE);
    public static final DataField FIELD_HEART_RATE_AVG = new DataFieldImpl("heart_rate_avg", DATA_TYPE_FLOAT, UNITS_BEATS_PER_MINUTE);
    public static final DataField FIELD_HEART_RATE_MAX = new DataFieldImpl("heart_rate_max", DATA_TYPE_FLOAT, UNITS_BEATS_PER_MINUTE);
    public static final DataField FIELD_HEART_RATE_MIN = new DataFieldImpl("heart_rate_min", DATA_TYPE_FLOAT, UNITS_BEATS_PER_MINUTE);
    public static final DataField FIELD_HEIGHT = new DataFieldImpl(ID_HEIGHT, DATA_TYPE_FLOAT, UNITS_METERS);
    public static final DataField FIELD_HORIZONTAL_ACCURACY = new DataFieldImpl("horizontal_accuracy", DATA_TYPE_FLOAT, UNITS_METERS);
    public static final DataField FIELD_INTENSITY = new DataFieldImpl(ID_INTENSITY, DATA_TYPE_FLOAT, UNITS_PERCENTAGE);
    public static final DataField FIELD_LATITUDE = new DataFieldImpl("latitude", DATA_TYPE_FLOAT, UNITS_DEGREES);
    public static final DataField FIELD_LEAN_MASS_AVG = new DataFieldImpl("lean_mass_avg", DATA_TYPE_FLOAT, UNITS_KILOGRAMS);
    public static final DataField FIELD_LEAN_MASS_END = new DataFieldImpl("lean_mass_end", DATA_TYPE_FLOAT, UNITS_KILOGRAMS);
    public static final DataField FIELD_LEAN_MASS_MAX = new DataFieldImpl("lean_mass_max", DATA_TYPE_FLOAT, UNITS_KILOGRAMS);
    public static final DataField FIELD_LEAN_MASS_MIN = new DataFieldImpl("lean_mass_min", DATA_TYPE_FLOAT, UNITS_KILOGRAMS);
    public static final DataField FIELD_LEAN_MASS_START = new DataFieldImpl("lean_mass_start", DATA_TYPE_FLOAT, UNITS_KILOGRAMS);
    public static final DataField FIELD_LONGITUDE = new DataFieldImpl("longitude", DATA_TYPE_FLOAT, UNITS_DEGREES);
    public static final DataField FIELD_MASS_AVG = new DataFieldImpl("mass_avg", DATA_TYPE_FLOAT, UNITS_KILOGRAMS);
    public static final DataField FIELD_MASS_CHANGE = new DataFieldImpl("mass_change", DATA_TYPE_FLOAT, UNITS_KILOGRAMS);
    public static final DataField FIELD_MASS_END = new DataFieldImpl("mass_end", DATA_TYPE_FLOAT, UNITS_KILOGRAMS);
    public static final DataField FIELD_MASS_MAX = new DataFieldImpl("mass_max", DATA_TYPE_FLOAT, UNITS_KILOGRAMS);
    public static final DataField FIELD_MASS_MIN = new DataFieldImpl("mass_min", DATA_TYPE_FLOAT, UNITS_KILOGRAMS);
    public static final DataField FIELD_MASS_PERCENT_CHANGE = new DataFieldImpl("mass_percent_change", DATA_TYPE_FLOAT, (DataUnits) null);
    public static final DataField FIELD_MASS_START = new DataFieldImpl("mass_start", DATA_TYPE_FLOAT, UNITS_KILOGRAMS);
    public static final DataField FIELD_MAX_ANGLE = new DataFieldImpl("max_angle", DATA_TYPE_FLOAT, UNITS_DEGREES);
    public static final DataField FIELD_MAX_FORCE = new DataFieldImpl("max_force", DATA_TYPE_FLOAT, UNITS_NEWTON);
    public static final DataField FIELD_MAX_TORQUE = new DataFieldImpl("max_torque", DATA_TYPE_FLOAT, UNITS_NEWTON_METERS);
    public static final DataField FIELD_MIN_ANGLE = new DataFieldImpl("min_angle", DATA_TYPE_FLOAT, UNITS_DEGREES);
    public static final DataField FIELD_MIN_FORCE = new DataFieldImpl("min_force", DATA_TYPE_FLOAT, UNITS_NEWTON);
    public static final DataField FIELD_MIN_TORQUE = new DataFieldImpl("min_torque", DATA_TYPE_FLOAT, UNITS_NEWTON_METERS);
    public static final DataField FIELD_POSTURE = new DataFieldImpl(ID_POSTURE, DATA_TYPE_FLOAT, (DataUnits) null);
    public static final DataField FIELD_RUN_CADENCE = new DataFieldImpl(ID_RUN_CADENCE, DATA_TYPE_FLOAT, UNITS_REVOLUTIONS_PER_MINUTE);
    public static final DataField FIELD_RUN_CADENCE_AVG = new DataFieldImpl("run_cadence_avg", DATA_TYPE_FLOAT, UNITS_REVOLUTIONS_PER_MINUTE);
    public static final DataField FIELD_RUN_CADENCE_MAX = new DataFieldImpl("run_cadence_max", DATA_TYPE_FLOAT, UNITS_REVOLUTIONS_PER_MINUTE);
    public static final DataField FIELD_RUN_CADENCE_MIN = new DataFieldImpl("run_cadence_min", DATA_TYPE_FLOAT, UNITS_REVOLUTIONS_PER_MINUTE);
    public static final DataField FIELD_SESSIONS_DISTANCE_SUM = new DataFieldImpl("sessions_distance_sum", DATA_TYPE_FLOAT, UNITS_METERS);
    public static final DataField FIELD_SESSIONS_DURATION_SUM = new DataFieldImpl("sessions_duration_sum", DATA_TYPE_INT, UNITS_SECONDS);
    public static final DataField FIELD_SESSIONS_SUM = new DataFieldImpl("sessions_sum", DATA_TYPE_INT, (DataUnits) null);
    public static final DataField FIELD_SLEEP_TIME_AVG = new DataFieldImpl("sleep_time_avg", DATA_TYPE_FLOAT, UNITS_SECONDS);
    public static final DataField FIELD_SLEEP_TIME_SUM = new DataFieldImpl("sleep_time_sum", DATA_TYPE_INT, UNITS_SECONDS);
    public static final DataField FIELD_SPEED = new DataFieldImpl(ID_SPEED, DATA_TYPE_FLOAT, UNITS_METERS_PER_SECOND);
    public static final DataField FIELD_SPEED_AVG = new DataFieldImpl("speed_avg", DATA_TYPE_FLOAT, UNITS_METERS_PER_SECOND);
    public static final DataField FIELD_SPEED_MAX = new DataFieldImpl("speed_max", DATA_TYPE_FLOAT, UNITS_METERS_PER_SECOND);
    public static final DataField FIELD_SPEED_MIN = new DataFieldImpl("speed_min", DATA_TYPE_FLOAT, UNITS_METERS_PER_SECOND);
    public static final DataField FIELD_STEPS = new DataFieldImpl(ID_STEPS, DATA_TYPE_INT, (DataUnits) null);
    public static final DataField FIELD_STEPS_SUM = new DataFieldImpl("steps_sum", DATA_TYPE_INT, (DataUnits) null);
    public static final DataField FIELD_STRIDE_LENGTH = new DataFieldImpl(ID_STRIDE_LENGTH, DATA_TYPE_FLOAT, UNITS_METERS);
    public static final DataField FIELD_TOP_DEAD_SPOT_ANGLE = new DataFieldImpl(ID_TOP_DEAD_SPOT_ANGLE, DATA_TYPE_FLOAT, UNITS_DEGREES);
    public static final DataField FIELD_VERTICAL_ACCURACY = new DataFieldImpl("vertical_accuracy", DATA_TYPE_FLOAT, UNITS_METERS);
    public static final DataField FIELD_WHEEL_REVOLUTIONS = new DataFieldImpl(ID_WHEEL_REVOLUTIONS, DATA_TYPE_INT, (DataUnits) null);
    public static final DataField FIELD_WILLPOWER = new DataFieldImpl(ID_WILLPOWER, DATA_TYPE_FLOAT, (DataUnits) null);
    public static final String ID_ACCUMULATED_TORQUE = "accumulated_torque";
    public static final String ID_ACTIVE_TIME_SUMMARY = "active_time_summary";
    public static final String ID_AIR_TEMPERATURE = "air_temperature";
    public static final String ID_BEARING = "bearing";
    public static final String ID_BODY_MASS = "body_mass";
    public static final String ID_BODY_MASS_SUMMARY = "body_mass_summary";
    public static final String ID_BOTTOM_DEAD_SPOT_ANGLE = "bottom_dead_spot_angle";
    public static final String ID_CRANK_REVOLUTIONS = "crank_revolutions";
    public static final String ID_CYCLING_CADENCE = "cycling_cadence";
    public static final String ID_CYCLING_POWER = "cycling_power";
    public static final String ID_CYCLING_POWER_BALANCE = "cycling_power_balance";
    public static final String ID_CYCLING_POWER_SUMMARY = "cycling_power_summary";
    public static final String ID_DISTANCE = "distance";
    public static final String ID_DISTANCE_SUMMARY = "distance_summary";
    public static final String ID_ELEVATION = "elevation";
    public static final String ID_ELEVATION_SUMMARY = "elevation_summary";
    public static final String ID_ENERGY_CONSUMED = "energy_consumed";
    public static final String ID_ENERGY_CONSUMED_SUMMARY = "energy_consumed_summary";
    public static final String ID_ENERGY_EXPENDED = "energy_expended";
    public static final String ID_ENERGY_EXPENDED_SUMMARY = "energy_expended_summary";
    public static final String ID_EXTREME_ANGLES = "extreme_angles";
    public static final String ID_EXTREME_FORCES = "extreme_forces";
    public static final String ID_EXTREME_TORQUE = "extreme_torque";
    public static final String ID_HEART_BEAT = "heart_beat";
    public static final String ID_HEART_RATE = "heart_rate";
    public static final String ID_HEART_RATE_SUMMARY = "heart_rate_summary";
    public static final String ID_HEIGHT = "height";
    public static final String ID_INTENSITY = "intensity";
    public static final String ID_LOCATION = "location";
    public static final String ID_POSTURE = "posture";
    public static final String ID_RUN_CADENCE = "run_cadence";
    public static final String ID_RUN_CADENCE_SUMMARY = "run_cadence_summary";
    public static final String ID_SESSIONS_SUMMARY = "sessions_summary";
    public static final String ID_SLEEP_SUMMARY = "sleep_summary";
    public static final String ID_SPEED = "speed";
    public static final String ID_SPEED_SUMMARY = "speed_summary";
    public static final String ID_STEPS = "steps";
    public static final String ID_STEPS_SUMMARY = "steps_summary";
    public static final String ID_STRIDE_LENGTH = "stride_length";
    public static final String ID_TOP_DEAD_SPOT_ANGLE = "top_dead_spot_angle";
    public static final String ID_WHEEL_REVOLUTIONS = "wheel_revolutions";
    public static final String ID_WILLPOWER = "willpower";
    public static final DataType TYPE_ACCUMULATED_TORQUE = new DataTypeImpl(ID_ACCUMULATED_TORQUE, DataPeriod.INTERVAL, "The user's accumulated torque over a period of time", Arrays.asList(new DataField[]{FIELD_ACCUMULATED_TORQUE}));
    public static final DataType TYPE_ACTIVE_TIME_SUMMARY = new DataTypeImpl(ID_ACTIVE_TIME_SUMMARY, DataPeriod.INTERVAL, "A summary of a user's active time over a period of time", Arrays.asList(new DataField[]{FIELD_ACTIVITY_TIME_SUM}));
    public static final DataType TYPE_AIR_TEMPERATURE = new DataTypeImpl(ID_AIR_TEMPERATURE, DataPeriod.INSTANT, "The current air temperature", Arrays.asList(new DataField[]{FIELD_AIR_TEMPERATURE}));
    public static final DataType TYPE_BEARING = new DataTypeImpl(ID_BEARING, DataPeriod.INSTANT, "The user's bearing", Arrays.asList(new DataField[]{FIELD_BEARING}));
    public static final DataType TYPE_BODY_MASS = new DataTypeImpl(ID_BODY_MASS, DataPeriod.INSTANT, "The user's body mass", Arrays.asList(new DataField[]{FIELD_BODY_MASS}));
    public static final DataType TYPE_BODY_MASS_SUMMARY = new DataTypeImpl(ID_BODY_MASS_SUMMARY, DataPeriod.INTERVAL, "A summary of a user's body mass over a period of time", Arrays.asList(new DataField[]{FIELD_MASS_MAX, FIELD_MASS_MIN, FIELD_MASS_AVG, FIELD_MASS_START, FIELD_MASS_END, FIELD_LEAN_MASS_MAX, FIELD_LEAN_MASS_MIN, FIELD_LEAN_MASS_AVG, FIELD_LEAN_MASS_START, FIELD_LEAN_MASS_END, FIELD_FAT_MASS_MAX, FIELD_FAT_MASS_MIN, FIELD_FAT_MASS_AVG, FIELD_FAT_MASS_START, FIELD_FAT_MASS_END, FIELD_MASS_PERCENT_CHANGE, FIELD_MASS_CHANGE}));
    public static final DataType TYPE_BOTTOM_DEAD_SPOT_ANGLE = new DataTypeImpl(ID_BOTTOM_DEAD_SPOT_ANGLE, DataPeriod.INSTANT, "The angle of the bottom dead spot of a pedal stroke", Arrays.asList(new DataField[]{FIELD_BOTTOM_DEAD_SPOT_ANGLE}));
    public static final DataType TYPE_CRANK_REVOLUTIONS = new DataTypeImpl(ID_CRANK_REVOLUTIONS, DataPeriod.INTERVAL, "The user's count of crank revolutions over a period of time", Arrays.asList(new DataField[]{FIELD_CRANK_REVOLUTIONS}));
    public static final DataType TYPE_CYCLING_CADENCE = new DataTypeImpl(ID_CYCLING_CADENCE, DataPeriod.INSTANT, "The user's cycling cadence", Arrays.asList(new DataField[]{FIELD_CYCLING_CADENCE}));
    public static final DataType TYPE_CYCLING_POWER = new DataTypeImpl(ID_CYCLING_POWER, DataPeriod.INSTANT, "The user's cycling power", Arrays.asList(new DataField[]{FIELD_CYCLING_POWER}));
    public static final DataType TYPE_CYCLING_POWER_BALANCE = new DataTypeImpl(ID_CYCLING_POWER_BALANCE, DataPeriod.INSTANT, "The user's cycling power balance", Arrays.asList(new DataField[]{FIELD_CYCLING_POWER_BALANCE}));
    public static final DataType TYPE_CYCLING_POWER_SUMMARY = new DataTypeImpl(ID_CYCLING_POWER_SUMMARY, DataPeriod.INTERVAL, "The user's cycling power summary", Arrays.asList(new DataField[]{FIELD_CYCLING_POWER_MAX, FIELD_CYCLING_POWER_MIN, FIELD_CYCLING_POWER_AVG}));
    public static final DataType TYPE_DISTANCE = new DataTypeImpl("distance", DataPeriod.INTERVAL, "The distance traveled by the user over a period of time", Arrays.asList(new DataField[]{FIELD_DISTANCE}));
    public static final DataType TYPE_DISTANCE_SUMMARY = new DataTypeImpl(ID_DISTANCE_SUMMARY, DataPeriod.INTERVAL, "A summary of the distance a user has traveled over a period of time", Arrays.asList(new DataField[]{FIELD_DISTANCE_SUM}));
    public static final DataType TYPE_ELEVATION = new DataTypeImpl(ID_ELEVATION, DataPeriod.INSTANT, "The user's elevation", Arrays.asList(new DataField[]{FIELD_ELEVATION, FIELD_VERTICAL_ACCURACY}));
    public static final DataType TYPE_ELEVATION_SUMMARY = new DataTypeImpl(ID_ELEVATION_SUMMARY, DataPeriod.INTERVAL, "The user's elevation summary", Arrays.asList(new DataField[]{FIELD_ELEVATION_MAX, FIELD_ELEVATION_MIN, FIELD_ELEVATION_AVG, FIELD_ELEVATION_GAIN}));
    public static final DataType TYPE_ENERGY_CONSUMED = new DataTypeImpl(ID_ENERGY_CONSUMED, DataPeriod.INTERVAL, "The energy that a user has consumed over a period of time", Arrays.asList(new DataField[]{FIELD_ENERGY_CONSUMED}));
    public static final DataType TYPE_ENERGY_CONSUMED_SUMMARY = new DataTypeImpl(ID_ENERGY_CONSUMED_SUMMARY, DataPeriod.INTERVAL, "A summary of a user's energy consumed over a period of time", Arrays.asList(new DataField[]{FIELD_ENERGY_CONSUMED_SUM}));
    public static final DataType TYPE_ENERGY_EXPENDED = new DataTypeImpl("energy_expended", DataPeriod.INTERVAL, "The energy that a user has expended over a period of time", Arrays.asList(new DataField[]{FIELD_ENERGY_EXPENDED}));
    public static final DataType TYPE_ENERGY_EXPENDED_SUMMARY = new DataTypeImpl(ID_ENERGY_EXPENDED_SUMMARY, DataPeriod.INTERVAL, "A summary of a user's energy expended over a period of time", Arrays.asList(new DataField[]{FIELD_ENERGY_EXPENDED_SUM}));
    public static final DataType TYPE_EXTREME_ANGLES = new DataTypeImpl(ID_EXTREME_ANGLES, DataPeriod.INTERVAL, "The user's minimum and maximum angle over a period of time", Arrays.asList(new DataField[]{FIELD_MAX_ANGLE, FIELD_MIN_ANGLE}));
    public static final DataType TYPE_EXTREME_FORCES = new DataTypeImpl(ID_EXTREME_FORCES, DataPeriod.INTERVAL, "The user's minimum and maximum force over a period of time", Arrays.asList(new DataField[]{FIELD_MAX_FORCE, FIELD_MIN_FORCE}));
    public static final DataType TYPE_EXTREME_TORQUE = new DataTypeImpl(ID_EXTREME_TORQUE, DataPeriod.INTERVAL, "The user's minimum and maximum torque over a period of time", Arrays.asList(new DataField[]{FIELD_MAX_TORQUE, FIELD_MIN_TORQUE}));
    public static final DataType TYPE_HEART_BEAT = new DataTypeImpl(ID_HEART_BEAT, DataPeriod.INSTANT, "The single heart beat of a user", Arrays.asList(new DataField[]{FIELD_HEART_BEAT}));
    public static final DataType TYPE_HEART_RATE = new DataTypeImpl(ID_HEART_RATE, DataPeriod.INSTANT, "The user's heart rate", Arrays.asList(new DataField[]{FIELD_HEART_RATE}));
    public static final DataType TYPE_HEART_RATE_SUMMARY = new DataTypeImpl(ID_HEART_RATE_SUMMARY, DataPeriod.INTERVAL, "The user's heart rate summary", Arrays.asList(new DataField[]{FIELD_HEART_RATE_MAX, FIELD_HEART_RATE_MIN, FIELD_HEART_RATE_AVG}));
    public static final DataType TYPE_HEIGHT = new DataTypeImpl(ID_HEIGHT, DataPeriod.INSTANT, "The user's height", Arrays.asList(new DataField[]{FIELD_HEIGHT}));
    public static final DataType TYPE_INTENSITY = new DataTypeImpl(ID_INTENSITY, DataPeriod.INSTANT, "The user's current intensity", Arrays.asList(new DataField[]{FIELD_INTENSITY}));
    public static final DataType TYPE_LOCATION = new DataTypeImpl(ID_LOCATION, DataPeriod.INSTANT, "The user's location", Arrays.asList(new DataField[]{FIELD_LATITUDE, FIELD_LONGITUDE, FIELD_HORIZONTAL_ACCURACY}));
    public static final DataType TYPE_POSTURE = new DataTypeImpl(ID_POSTURE, DataPeriod.INSTANT, "The user's current posture", Arrays.asList(new DataField[]{FIELD_POSTURE}));
    public static final DataType TYPE_RUN_CADENCE = new DataTypeImpl(ID_RUN_CADENCE, DataPeriod.INSTANT, "The user's run cadence", Arrays.asList(new DataField[]{FIELD_RUN_CADENCE}));
    public static final DataType TYPE_RUN_CADENCE_SUMMARY = new DataTypeImpl(ID_RUN_CADENCE_SUMMARY, DataPeriod.INTERVAL, "The user's run cadence summary", Arrays.asList(new DataField[]{FIELD_RUN_CADENCE_MAX, FIELD_RUN_CADENCE_MIN, FIELD_RUN_CADENCE_AVG}));
    public static final DataType TYPE_SESSIONS_SUMMARY = new DataTypeImpl(ID_SESSIONS_SUMMARY, DataPeriod.INTERVAL, "A summary of a user's workout sessions over a period of time", Arrays.asList(new DataField[]{FIELD_SESSIONS_SUM, FIELD_SESSIONS_DURATION_SUM, FIELD_SESSIONS_DISTANCE_SUM}));
    public static final DataType TYPE_SLEEP_SUMMARY = new DataTypeImpl(ID_SLEEP_SUMMARY, DataPeriod.INTERVAL, "A summary of a user's sleep over a period of time", Arrays.asList(new DataField[]{FIELD_SLEEP_TIME_SUM, FIELD_SLEEP_TIME_AVG}));
    public static final DataType TYPE_SPEED = new DataTypeImpl(ID_SPEED, DataPeriod.INSTANT, "The user's speed", Arrays.asList(new DataField[]{FIELD_SPEED}));
    public static final DataType TYPE_SPEED_SUMMARY = new DataTypeImpl(ID_SPEED_SUMMARY, DataPeriod.INTERVAL, "The user's speed summary", Arrays.asList(new DataField[]{FIELD_SPEED_MAX, FIELD_SPEED_MIN, FIELD_SPEED_AVG}));
    public static final DataType TYPE_STEPS = new DataTypeImpl(ID_STEPS, DataPeriod.INTERVAL, "The number of steps taken by the user over a period of time", Arrays.asList(new DataField[]{FIELD_STEPS}));
    public static final DataType TYPE_STEPS_SUMMARY = new DataTypeImpl(ID_STEPS_SUMMARY, DataPeriod.INTERVAL, "A summary of the user's steps over a period of time", Arrays.asList(new DataField[]{FIELD_STEPS_SUM}));
    public static final DataType TYPE_STRIDE_LENGTH = new DataTypeImpl(ID_STRIDE_LENGTH, DataPeriod.INSTANT, "The user's stride length", Arrays.asList(new DataField[]{FIELD_STRIDE_LENGTH}));
    public static final DataType TYPE_TOP_DEAD_SPOT_ANGLE = new DataTypeImpl(ID_TOP_DEAD_SPOT_ANGLE, DataPeriod.INSTANT, "The angle of the top dead spot of a pedal stroke", Arrays.asList(new DataField[]{FIELD_TOP_DEAD_SPOT_ANGLE}));
    public static final DataType TYPE_WHEEL_REVOLUTIONS = new DataTypeImpl(ID_WHEEL_REVOLUTIONS, DataPeriod.INTERVAL, "The user's count of wheel revolutions over a period of time", Arrays.asList(new DataField[]{FIELD_WHEEL_REVOLUTIONS}));
    public static final DataType TYPE_WILLPOWER = new DataTypeImpl(ID_WILLPOWER, DataPeriod.INTERVAL, "The user's WILLpower™", Arrays.asList(new DataField[]{FIELD_WILLPOWER}));
    public static final DataUnits UNITS_BEATS_PER_MINUTE = new DataUnitsImpl("beats_per_minute", "bpm");
    public static final DataUnits UNITS_CELSIUS = new DataUnitsImpl("degrees_celsius", "C");
    public static final DataUnits UNITS_DEGREES = new DataUnitsImpl("degrees", "");
    public static final DataUnits UNITS_JOULES = new DataUnitsImpl("joules", "J");
    public static final DataUnits UNITS_KILOGRAMS = new DataUnitsImpl("kilograms", "kg");
    public static final DataUnits UNITS_METERS = new DataUnitsImpl("meters", "m");
    public static final DataUnits UNITS_METERS_PER_SECOND = new DataUnitsImpl("meters_per_second", "m/s");
    public static final DataUnits UNITS_NEWTON = new DataUnitsImpl("newton", "N");
    public static final DataUnits UNITS_NEWTON_METERS = new DataUnitsImpl("newton_meters", "N*m");
    public static final DataUnits UNITS_PERCENTAGE = new DataUnitsImpl("percent", "%");
    public static final DataUnits UNITS_REVOLUTIONS_PER_MINUTE = new DataUnitsImpl("revolutions_per_minute", "rpm");
    public static final DataUnits UNITS_SECONDS = new DataUnitsImpl("seconds", "s");
    public static final DataUnits UNITS_WATT = new DataUnitsImpl("watts", "W");

    public static DataField findDataField(String id, DataType dataType) {
        if (dataType == null) {
            return null;
        }
        for (DataField dataField : dataType.getFields()) {
            if (dataField.getId().equalsIgnoreCase(id)) {
                return dataField;
            }
        }
        return null;
    }
}
