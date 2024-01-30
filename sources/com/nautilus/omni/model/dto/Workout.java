package com.nautilus.omni.model.dto;

import android.os.Parcel;
import android.os.Parcelable;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import java.io.Serializable;
import java.util.Collection;
import org.joda.time.DateTime;

@DatabaseTable(tableName = "workout")
public class Workout implements Serializable, Parcelable {
    public static final String AVERAGE_CALORIE_BURN_RATE = "average_calorie_burn_rate";
    public static final String AVERAGE_HEART_RATE = "average_heart_rate";
    public static final String AVERAGE_HEART_RATE_MEASURED_TIME = "average_heart_rate_measured_time";
    public static final String AVG_INCLINE = "avg_incline";
    public static final String AVG_LAP_TIME = "avg_lap_time";
    public static final String AVG_POWER = "avg_power";
    public static final String AVG_RESISTANCE = "avg_resistance";
    public static final String AVG_RPM = "avg_rpm";
    public static final String AVG_SPEED = "avg_speed";
    public static final String AWARDS = "awards";
    public static final String CALORIES = "calories";
    public static final Parcelable.Creator<Workout> CREATOR = new Parcelable.Creator<Workout>() {
        public Workout createFromParcel(Parcel in) {
            return new Workout(in);
        }

        public Workout[] newArray(int size) {
            return new Workout[size];
        }
    };
    public static final String DISTANCE = "distance";
    public static final String ELAPSED_TIME = "elapsed_time";
    public static final String FINISH_TIME = "finish_time";
    public static final String ID_FIELD_NAME = "id";
    public static final String ORIGINAL_DAY = "original_day";
    public static final String ORIGINAL_HOUR = "original_hour";
    public static final String ORIGINAL_MINUTE = "original_minute";
    public static final String ORIGINAL_MONTH = "original_month";
    public static final String ORIGINAL_SECOND = "original_second";
    public static final String ORIGINAL_YEAR = "original_year";
    public static final String PROGRAM_ID = "program_id";
    public static final String RECORD_ID = "record_id";
    public static final String USER = "user";
    public static final String WORKOUT = "workout";
    public static final String WORKOUT_DATE = "workout_date";
    public static final String WORKOUT_FITNESS_SCORE = "workout_fitness_score";
    public static final String WORKOUT_FITNESS_SCORE_QUALIFIED = "workout_fitness_score_qualified";
    public static final String WORKOUT_LAPS_COMPLETED = "workout_completed";
    @DatabaseField(columnName = "average_calorie_burn_rate")
    private float mAvgCalorieBurnRate;
    @DatabaseField(columnName = "average_heart_rate")
    private int mAvgHeartRate;
    @DatabaseField(columnName = "avg_incline")
    private int mAvgIncline;
    @DatabaseField(columnName = "avg_lap_time")
    private int mAvgLapTime;
    @DatabaseField(columnName = "avg_power")
    private int mAvgPower;
    @DatabaseField(columnName = "avg_rpm")
    private int mAvgRPM;
    @DatabaseField(columnName = "avg_resistance")
    private int mAvgResistance;
    @DatabaseField(columnName = "avg_speed")
    private int mAvgSpeed;
    @ForeignCollectionField(columnName = "awards", eager = false, maxEagerLevel = 3)
    private Collection<Award> mAwards;
    @DatabaseField(columnName = "calories")
    private int mCalories;
    @DatabaseField(columnName = "distance")
    private int mDistance;
    @DatabaseField(columnName = "elapsed_time")
    private int mElapsedTime;
    @DatabaseField(columnName = "finish_time")
    private DateTime mFinishTime;
    @DatabaseField(columnName = "average_heart_rate_measured_time")
    private int mHeartRateMeasuredTime;
    @DatabaseField(columnName = "id", generatedId = true)
    private int mId;
    @DatabaseField(columnName = "original_day")
    private int mOriginalDay;
    @DatabaseField(columnName = "original_hour")
    private int mOriginalHour;
    @DatabaseField(columnName = "original_minute")
    private int mOriginalMinute;
    @DatabaseField(columnName = "original_month")
    private int mOriginalMonth;
    @DatabaseField(columnName = "original_second")
    private int mOriginalSecond;
    @DatabaseField(columnName = "original_year")
    private int mOriginalYear;
    @DatabaseField(columnName = "program_id")
    private int mProgramId;
    @DatabaseField(columnName = "record_id")
    private int mRecordId;
    @DatabaseField(columnName = "user", foreign = true)
    private User mUser;
    @DatabaseField(columnName = "workout_date")
    private DateTime mWorkoutDate;
    @DatabaseField(columnName = "workout_fitness_score")
    private int mWorkoutFitnessScore;
    @DatabaseField(columnName = "workout_fitness_score_qualified")
    private int mWorkoutFitnessScoreQualified;
    @DatabaseField(columnName = "workout_completed")
    private int mWorkoutLapsCompleted;

    public Workout() {
    }

    protected Workout(Parcel in) {
        this.mId = in.readInt();
        this.mRecordId = in.readInt();
        this.mWorkoutDate = (DateTime) in.readSerializable();
        this.mProgramId = in.readInt();
        this.mElapsedTime = in.readInt();
        this.mCalories = in.readInt();
        this.mAvgHeartRate = in.readInt();
        this.mHeartRateMeasuredTime = in.readInt();
        this.mAvgCalorieBurnRate = in.readFloat();
        this.mFinishTime = new DateTime(in.readLong());
        this.mAvgPower = in.readInt();
        this.mAvgResistance = in.readInt();
        this.mAvgRPM = in.readInt();
        this.mWorkoutLapsCompleted = in.readInt();
        this.mAvgIncline = in.readInt();
        this.mAvgLapTime = in.readInt();
        this.mWorkoutFitnessScore = in.readInt();
        this.mWorkoutFitnessScoreQualified = in.readInt();
        this.mOriginalSecond = in.readInt();
        this.mOriginalMinute = in.readInt();
        this.mOriginalHour = in.readInt();
        this.mOriginalDay = in.readInt();
        this.mOriginalMonth = in.readInt();
        this.mOriginalYear = in.readInt();
        this.mDistance = in.readInt();
        this.mAvgSpeed = in.readInt();
        this.mAwards = null;
    }

    public int describeContents() {
        return 0;
    }

    public int getmDistance() {
        return this.mDistance;
    }

    public void setmDistance(int mDistance2) {
        this.mDistance = mDistance2;
    }

    public int getmAvgSpeed() {
        return this.mAvgSpeed;
    }

    public void setmAvgSpeed(int mAvgSpeed2) {
        this.mAvgSpeed = mAvgSpeed2;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mId);
        dest.writeInt(this.mRecordId);
        dest.writeSerializable(this.mWorkoutDate);
        dest.writeInt(this.mProgramId);
        dest.writeInt(this.mElapsedTime);
        dest.writeInt(this.mCalories);
        dest.writeInt(this.mAvgHeartRate);
        dest.writeInt(this.mHeartRateMeasuredTime);
        dest.writeFloat(this.mAvgCalorieBurnRate);
        dest.writeLong(this.mFinishTime.getMillis());
        dest.writeInt(this.mAvgPower);
        dest.writeInt(this.mAvgResistance);
        dest.writeInt(this.mAvgRPM);
        dest.writeInt(this.mWorkoutLapsCompleted);
        dest.writeInt(this.mAvgIncline);
        dest.writeInt(this.mAvgLapTime);
        dest.writeInt(this.mWorkoutFitnessScore);
        dest.writeInt(this.mWorkoutFitnessScoreQualified);
        dest.writeInt(this.mOriginalSecond);
        dest.writeInt(this.mOriginalMinute);
        dest.writeInt(this.mOriginalHour);
        dest.writeInt(this.mOriginalDay);
        dest.writeInt(this.mOriginalMonth);
        dest.writeInt(this.mOriginalYear);
        dest.writeInt(this.mDistance);
        dest.writeInt(this.mAvgSpeed);
    }

    public int getmId() {
        return this.mId;
    }

    public int getmRecordId() {
        return this.mRecordId;
    }

    public int getmProgramId() {
        return this.mProgramId;
    }

    public DateTime getmWorkoutDate() {
        return this.mWorkoutDate;
    }

    public DateTime getmFinishTime() {
        return this.mFinishTime;
    }

    public int getmElapsedTime() {
        return this.mElapsedTime;
    }

    public int getmCalories() {
        return this.mCalories;
    }

    public int getmAvgHeartRate() {
        return this.mAvgHeartRate;
    }

    public int getmHeartRateMeasuredTime() {
        return this.mHeartRateMeasuredTime;
    }

    public float getmAvgCalorieBurnRate() {
        return this.mAvgCalorieBurnRate;
    }

    public int getmAvgPower() {
        return this.mAvgPower;
    }

    public int getmAvgResistance() {
        return this.mAvgResistance;
    }

    public int getmAvgRPM() {
        return this.mAvgRPM;
    }

    public int getmWorkoutLapsCompleted() {
        return this.mWorkoutLapsCompleted;
    }

    public int getmOriginalSecond() {
        return this.mOriginalSecond;
    }

    public int getmOriginalMinute() {
        return this.mOriginalMinute;
    }

    public int getmOriginalHour() {
        return this.mOriginalHour;
    }

    public int getmOriginalDay() {
        return this.mOriginalDay;
    }

    public int getmOriginalMonth() {
        return this.mOriginalMonth;
    }

    public int getmOriginalYear() {
        return this.mOriginalYear;
    }

    public User getmUser() {
        return this.mUser;
    }

    public Collection<Award> getmAwards() {
        return this.mAwards;
    }

    public int getmAvgIncline() {
        return this.mAvgIncline;
    }

    public int getmAvgLapTime() {
        return this.mAvgLapTime;
    }

    public int getmWorkoutFitnessScore() {
        return this.mWorkoutFitnessScore;
    }

    public int getmWorkoutFitnessScoreQualified() {
        return this.mWorkoutFitnessScoreQualified;
    }

    public void setmId(int mId2) {
        this.mId = mId2;
    }

    public void setmRecordId(int mRecordId2) {
        this.mRecordId = mRecordId2;
    }

    public void setmProgramId(int mProgramId2) {
        this.mProgramId = mProgramId2;
    }

    public void setmWorkoutDate(DateTime mWorkoutDate2) {
        this.mWorkoutDate = mWorkoutDate2;
    }

    public void setmFinishTime(DateTime mFinishTime2) {
        this.mFinishTime = mFinishTime2;
    }

    public void setmElapsedTime(int mElapsedTime2) {
        this.mElapsedTime = mElapsedTime2;
    }

    public void setmCalories(int mCalories2) {
        this.mCalories = mCalories2;
    }

    public void setmAvgHeartRate(int mAvgHeartRate2) {
        this.mAvgHeartRate = mAvgHeartRate2;
    }

    public void setmHeartRateMeasuredTime(int mHeartRateMeasuredTime2) {
        this.mHeartRateMeasuredTime = mHeartRateMeasuredTime2;
    }

    public void setmAvgCalorieBurnRate(float mAvgCalorieBurnRate2) {
        this.mAvgCalorieBurnRate = mAvgCalorieBurnRate2;
    }

    public void setmAvgPower(int mAvgPower2) {
        this.mAvgPower = mAvgPower2;
    }

    public void setmAvgResistance(int mAvgResistance2) {
        this.mAvgResistance = mAvgResistance2;
    }

    public void setmAvgRPM(int mAvgRPM2) {
        this.mAvgRPM = mAvgRPM2;
    }

    public void setmWorkoutLapsCompleted(int mWorkoutLapsCompleted2) {
        this.mWorkoutLapsCompleted = mWorkoutLapsCompleted2;
    }

    public void setmOriginalSecond(int mOriginalSecond2) {
        this.mOriginalSecond = mOriginalSecond2;
    }

    public void setmOriginalMinute(int mOriginalMinute2) {
        this.mOriginalMinute = mOriginalMinute2;
    }

    public void setmOriginalHour(int mOriginalHour2) {
        this.mOriginalHour = mOriginalHour2;
    }

    public void setmOriginalDay(int mOriginalDay2) {
        this.mOriginalDay = mOriginalDay2;
    }

    public void setmOriginalMonth(int mOriginalMonth2) {
        this.mOriginalMonth = mOriginalMonth2;
    }

    public void setmOriginalYear(int mOriginalYear2) {
        this.mOriginalYear = mOriginalYear2;
    }

    public void setmUser(User mUser2) {
        this.mUser = mUser2;
    }

    public void setmAwards(Collection<Award> mAwards2) {
        this.mAwards = mAwards2;
    }

    public void setmAvgIncline(int mAvgIncline2) {
        this.mAvgIncline = mAvgIncline2;
    }

    public void setmAvgLapTime(int mAvgLapTime2) {
        this.mAvgLapTime = mAvgLapTime2;
    }

    public void setmWorkoutFitnessScore(int mWorkoutFitnessScore2) {
        this.mWorkoutFitnessScore = mWorkoutFitnessScore2;
    }

    public void setmWorkoutFitnessScoreQualified(int mWorkoutFitnessScoreQualified2) {
        this.mWorkoutFitnessScoreQualified = mWorkoutFitnessScoreQualified2;
    }
}
