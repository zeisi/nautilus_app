package com.nautilus.omni.model.dto;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.joda.time.DateTime;

@DatabaseTable(tableName = "week")
public class Week {
    public static final String AWARDED_MOST_DISTANCE_PER_WEEK = "awarded_most_distance_per_week";
    public static final String AWARDED_MOST_THREE_OR_MORE_WORKOUTS_PER_WEEK = "awarded_three_or_more_workouts_per_week";
    public static final String AWARDED_MOST_WORKOUTS_PER_WEEK = "awarded_most_workouts_per_week";
    public static final String END_DATE = "end_date";
    public static final String ID_FIELD_NAME = "id";
    public static final String INITIAL_DATE = "initial_date";
    public static final String LAST_WORKOUT = "last_workout";
    public static final String TOTAL_WORKOUTS = "total_workouts";
    public static final String TOTAL_WORKOUTS_CALORIES = "total_workouts_calories";
    public static final String TOTAL_WORKOUTS_CAL_BURN_RATE = "total_workouts_calorie_burn";
    public static final String TOTAL_WORKOUTS_DISTANCE = "total_workouts_distance";
    public static final String TOTAL_WORKOUTS_HEART_RATE = "total_workouts_heart_rate";
    public static final String TOTAL_WORKOUTS_POWER = "total_workouts_power";
    public static final String TOTAL_WORKOUTS_SPEED = "total_workouts_speed";
    public static final String TOTAL_WORKOUTS_TIME = "total_workouts_time";
    @DatabaseField(columnName = "end_date")
    private DateTime mEndDate;
    @DatabaseField(columnName = "id", generatedId = true)
    private int mId;
    @DatabaseField(columnName = "initial_date")
    private DateTime mInitialDate;
    @DatabaseField(columnName = "awarded_most_distance_per_week")
    private boolean mIsAwardedWithMostDistancePerWeek;
    @DatabaseField(columnName = "awarded_most_workouts_per_week")
    private boolean mIsAwardedWithMostWorkoutsPerWeek;
    @DatabaseField(columnName = "awarded_three_or_more_workouts_per_week")
    private boolean mIsAwardedWithThreeOrMoreWorkoutsPerWeek;
    @DatabaseField(columnName = "last_workout", foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true)
    private Workout mLastWorkout;
    @DatabaseField(columnName = "total_workouts")
    private int mTotalWorkouts;
    @DatabaseField(columnName = "total_workouts_calorie_burn")
    private float mTotalWorkoutsAvgCalBurnRate;
    @DatabaseField(columnName = "total_workouts_calories")
    private int mTotalWorkoutsCalories;
    @DatabaseField(columnName = "total_workouts_distance")
    private int mTotalWorkoutsDistance;
    @DatabaseField(columnName = "total_workouts_heart_rate")
    private int mTotalWorkoutsHeartRate;
    @DatabaseField(columnName = "total_workouts_power")
    private int mTotalWorkoutsPower;
    @DatabaseField(columnName = "total_workouts_speed")
    private int mTotalWorkoutsSpeed;
    @DatabaseField(columnName = "total_workouts_time")
    private int mTotalWorkoutsTime;

    public int getmId() {
        return this.mId;
    }

    public DateTime getmInitialDate() {
        return this.mInitialDate;
    }

    public DateTime getmEndDate() {
        return this.mEndDate;
    }

    public int getmTotalWorkouts() {
        return this.mTotalWorkouts;
    }

    public int getmTotalWorkoutsDistance() {
        return this.mTotalWorkoutsDistance;
    }

    public int getmTotalWorkoutsCalories() {
        return this.mTotalWorkoutsCalories;
    }

    public int getmTotalWorkoutsTime() {
        return this.mTotalWorkoutsTime;
    }

    public int getmTotalWorkoutsSpeed() {
        return this.mTotalWorkoutsSpeed;
    }

    public int getmTotalWorkoutsHeartRate() {
        return this.mTotalWorkoutsHeartRate;
    }

    public int getmTotalWorkoutsPower() {
        return this.mTotalWorkoutsPower;
    }

    public float getmTotalWorkoutsAvgCalBurnRate() {
        return this.mTotalWorkoutsAvgCalBurnRate;
    }

    public Workout getmLastWorkout() {
        return this.mLastWorkout;
    }

    public boolean ismIsAwardedWithMostWorkoutsPerWeek() {
        return this.mIsAwardedWithMostWorkoutsPerWeek;
    }

    public boolean ismIsAwardedWithThreeOrMoreWorkoutsPerWeek() {
        return this.mIsAwardedWithThreeOrMoreWorkoutsPerWeek;
    }

    public boolean ismIsAwardedWithMostDistancePerWeek() {
        return this.mIsAwardedWithMostDistancePerWeek;
    }

    public void setmId(int mId2) {
        this.mId = mId2;
    }

    public void setmInitialDate(DateTime mInitialDate2) {
        this.mInitialDate = mInitialDate2;
    }

    public void setmEndDate(DateTime mEndDate2) {
        this.mEndDate = mEndDate2;
    }

    public void setmTotalWorkouts(int mTotalWorkouts2) {
        this.mTotalWorkouts = mTotalWorkouts2;
    }

    public void setmTotalWorkoutsDistance(int mTotalWorkoutsDistance2) {
        this.mTotalWorkoutsDistance = mTotalWorkoutsDistance2;
    }

    public void setmTotalWorkoutsCalories(int mTotalWorkoutsCalories2) {
        this.mTotalWorkoutsCalories = mTotalWorkoutsCalories2;
    }

    public void setmTotalWorkoutsTime(int mTotalWorkoutsTime2) {
        this.mTotalWorkoutsTime = mTotalWorkoutsTime2;
    }

    public void setmTotalWorkoutsSpeed(int mTotalWorkoutsSpeed2) {
        this.mTotalWorkoutsSpeed = mTotalWorkoutsSpeed2;
    }

    public void setmTotalWorkoutsHeartRate(int mTotalWorkoutsHeartRate2) {
        this.mTotalWorkoutsHeartRate = mTotalWorkoutsHeartRate2;
    }

    public void setmTotalWorkoutsPower(int mTotalWorkoutsPower2) {
        this.mTotalWorkoutsPower = mTotalWorkoutsPower2;
    }

    public void setmTotalWorkoutsAvgCalBurnRate(float mTotalWorkoutsAvgCalBurnRate2) {
        this.mTotalWorkoutsAvgCalBurnRate = mTotalWorkoutsAvgCalBurnRate2;
    }

    public void setmLastWorkout(Workout mLastWorkout2) {
        this.mLastWorkout = mLastWorkout2;
    }

    public void setmIsAwardedWithMostWorkoutsPerWeek(boolean mIsAwardedWithMostWorkoutsPerWeek2) {
        this.mIsAwardedWithMostWorkoutsPerWeek = mIsAwardedWithMostWorkoutsPerWeek2;
    }

    public void setmIsAwardedWithThreeOrMoreWorkoutsPerWeek(boolean mIsAwardedWithThreeOrMoreWorkoutsPerWeek2) {
        this.mIsAwardedWithThreeOrMoreWorkoutsPerWeek = mIsAwardedWithThreeOrMoreWorkoutsPerWeek2;
    }

    public void setmIsAwardedWithMostDistancePerWeek(boolean mIsAwardedWithMostDistancePerWeek2) {
        this.mIsAwardedWithMostDistancePerWeek = mIsAwardedWithMostDistancePerWeek2;
    }
}
