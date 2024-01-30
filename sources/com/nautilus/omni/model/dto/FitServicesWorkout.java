package com.nautilus.omni.model.dto;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.joda.time.DateTime;

@DatabaseTable(tableName = "fit_services_workout")
public class FitServicesWorkout {
    public static final String GOOGLE_FIT_ID = "google_fit_id";
    public static final String ID_FIELD_NAME = "id";
    public static final String IS_SYNCED_GOOGLE_FIT = "is_synced_google_fit";
    public static final String IS_SYNCED_MY_FITNESS_PAL = "is_synced_my_fitness_pal";
    public static final String IS_SYNCED_NAUTILUS_CONNECT = "is_synced_nautilus_connect";
    public static final String MY_FITNESS_PAL_ID = "my_fitness_pal_id";
    public static final String NAUTILUS_CONNECT_ID = "nautilus_connect_id";
    public static final String OMNI_USER_NUMBER = "omni_user_number";
    public static final String ORIGINAL_DAY = "original_day";
    public static final String ORIGINAL_HOUR = "original_hour";
    public static final String ORIGINAL_MINUTE = "original_minute";
    public static final String ORIGINAL_MONTH = "original_month";
    public static final String ORIGINAL_SECOND = "original_second";
    public static final String ORIGINAL_YEAR = "original_year";
    public static final String WORKOUT = "workout";
    public static final String WORKOUT_DATE = "workout_date";
    public static final String WORKOUT_RECORD_ID = "workout_record_id";
    @DatabaseField(canBeNull = true, columnName = "google_fit_id")
    private String mGoogleFitId;
    @DatabaseField(columnName = "id", generatedId = true)
    private int mId;
    @DatabaseField(columnName = "is_synced_google_fit")
    private boolean mIsSyncedWithGoogleFit;
    @DatabaseField(columnName = "is_synced_my_fitness_pal")
    private boolean mIsSyncedWithMyFitnessPal;
    @DatabaseField(columnName = "is_synced_nautilus_connect")
    private boolean mIsSyncedWithNautilusConnect;
    @DatabaseField(canBeNull = true, columnName = "my_fitness_pal_id")
    private String mMyFitnessPalId;
    @DatabaseField(canBeNull = true, columnName = "nautilus_connect_id")
    private int mNautilusConnectId;
    @DatabaseField(columnName = "omni_user_number")
    private int mOmniTrainerUserNumber;
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
    @DatabaseField(canBeNull = true, columnName = "workout", foreign = true, foreignAutoRefresh = true)
    private Workout mWorkout;
    @DatabaseField(columnName = "workout_date")
    private DateTime mWorkoutDate;
    @DatabaseField(columnName = "workout_record_id")
    private int mWorkoutRecordId;

    public int getmId() {
        return this.mId;
    }

    public DateTime getmWorkoutDate() {
        return this.mWorkoutDate;
    }

    public int getmWorkoutRecordId() {
        return this.mWorkoutRecordId;
    }

    public int getmOmniTrainerUserNumber() {
        return this.mOmniTrainerUserNumber;
    }

    public Workout getmWorkout() {
        return this.mWorkout;
    }

    public boolean ismIsSyncedWithMyFitnessPal() {
        return this.mIsSyncedWithMyFitnessPal;
    }

    public boolean ismIsSyncedWithGoogleFit() {
        return this.mIsSyncedWithGoogleFit;
    }

    public boolean ismIsSyncedWithNautilusConnect() {
        return this.mIsSyncedWithNautilusConnect;
    }

    public int getmNautilusConnectId() {
        return this.mNautilusConnectId;
    }

    public String getmMyFitnessPalId() {
        return this.mMyFitnessPalId;
    }

    public String getmGoogleFitId() {
        return this.mGoogleFitId;
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

    public void setmId(int mId2) {
        this.mId = mId2;
    }

    public void setmWorkoutDate(DateTime mWorkoutDate2) {
        this.mWorkoutDate = mWorkoutDate2;
    }

    public void setmWorkoutRecordId(int mWorkoutRecordId2) {
        this.mWorkoutRecordId = mWorkoutRecordId2;
    }

    public void setmOmniTrainerUserNumber(int mOmniTrainerUserNumber2) {
        this.mOmniTrainerUserNumber = mOmniTrainerUserNumber2;
    }

    public void setmWorkout(Workout mWorkout2) {
        this.mWorkout = mWorkout2;
    }

    public void setmIsSyncedWithMyFitnessPal(boolean mIsSyncedWithMyFitnessPal2) {
        this.mIsSyncedWithMyFitnessPal = mIsSyncedWithMyFitnessPal2;
    }

    public void setmIsSyncedWithGoogleFit(boolean mIsSyncedWithGoogleFit2) {
        this.mIsSyncedWithGoogleFit = mIsSyncedWithGoogleFit2;
    }

    public void setmMyFitnessPalId(String mMyFitnessPalId2) {
        this.mMyFitnessPalId = mMyFitnessPalId2;
    }

    public void setmIsSyncedWithNautilusConnect(boolean mIsSyncedWithNautilusConnect2) {
        this.mIsSyncedWithNautilusConnect = mIsSyncedWithNautilusConnect2;
    }

    public void setmNautilusConnectId(int mNautilusConnectId2) {
        this.mNautilusConnectId = mNautilusConnectId2;
    }

    public void setmGoogleFitId(String mGoogleFitId2) {
        this.mGoogleFitId = mGoogleFitId2;
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
}
