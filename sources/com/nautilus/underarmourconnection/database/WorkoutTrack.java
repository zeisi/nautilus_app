package com.nautilus.underarmourconnection.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.joda.time.DateTime;

@DatabaseTable(tableName = "workout_track")
public class WorkoutTrack {
    public static final String CONSOLE_USER_NUMBER = "console_user_number";
    public static final String CUSTOM_KEY = "custom_key";
    public static final String ID_FIELD_NAME = "id";
    public static final String MACHINE_TYPE = "machine_type";
    public static final String ORIGINAL_DAY = "original_day";
    public static final String ORIGINAL_HOUR = "original_hour";
    public static final String ORIGINAL_MINUTE = "original_minute";
    public static final String ORIGINAL_MONTH = "original_month";
    public static final String ORIGINAL_SECOND = "original_second";
    public static final String ORIGINAL_YEAR = "original_year";
    public static final String RECORD_ID = "record_id";
    public static final String SYNC_DATE = "sync_date";
    public static final String UNDER_ARMOUR_ID = "under_armour_id";
    @DatabaseField(columnName = "console_user_number")
    private int consoleUserNumber;
    @DatabaseField(columnName = "custom_key")
    private String customKey;
    @DatabaseField(columnName = "id", generatedId = true)
    private int id;
    @DatabaseField(columnName = "machine_type")
    private String machineType;
    @DatabaseField(columnName = "original_day")
    private int originalDay;
    @DatabaseField(columnName = "original_hour")
    private int originalHour;
    @DatabaseField(columnName = "original_minute")
    private int originalMinute;
    @DatabaseField(columnName = "original_month")
    private int originalMonth;
    @DatabaseField(columnName = "original_second")
    private int originalSecond;
    @DatabaseField(columnName = "original_year")
    private int originalYear;
    @DatabaseField(columnName = "record_id")
    private int recordId;
    @DatabaseField(columnName = "sync_date")
    private DateTime syncDate;
    @DatabaseField(columnName = "under_armour_id")
    private String workoutUnderArmourId;

    public WorkoutTrack() {
    }

    public WorkoutTrack(String workoutUnderArmourId2, int recordId2, int consoleUserNumber2, String machineType2, DateTime syncDate2, int originalSecond2, int originalMinute2, int originalHour2, int originalDay2, int originalMonth2, int originalYear2) {
        this.recordId = recordId2;
        this.workoutUnderArmourId = workoutUnderArmourId2;
        this.consoleUserNumber = consoleUserNumber2;
        this.machineType = machineType2;
        this.syncDate = syncDate2;
        this.originalSecond = originalSecond2;
        this.originalMinute = originalMinute2;
        this.originalHour = originalHour2;
        this.originalDay = originalDay2;
        this.originalMonth = originalMonth2;
        this.originalYear = originalYear2;
        this.customKey = this.consoleUserNumber + this.recordId + this.machineType + this.originalSecond + this.originalMinute + this.originalHour + this.originalDay + this.originalMonth + this.originalYear;
    }

    public int getId() {
        return this.id;
    }

    public String getWorkoutUnderArmourId() {
        return this.workoutUnderArmourId;
    }

    public int getRecordId() {
        return this.recordId;
    }

    public int getConsoleUserNumber() {
        return this.consoleUserNumber;
    }

    public String getMachineType() {
        return this.machineType;
    }

    public DateTime getSyncDate() {
        return this.syncDate;
    }

    public int getOriginalSecond() {
        return this.originalSecond;
    }

    public int getOriginalMinute() {
        return this.originalMinute;
    }

    public int getOriginalHour() {
        return this.originalHour;
    }

    public int getOriginalDay() {
        return this.originalDay;
    }

    public int getOriginalMonth() {
        return this.originalMonth;
    }

    public int getOriginalYear() {
        return this.originalYear;
    }

    public String getCustomKey() {
        return this.customKey;
    }

    public void setId(int id2) {
        this.id = id2;
    }

    public void setWorkoutUnderArmourId(String workoutUnderArmourId2) {
        this.workoutUnderArmourId = workoutUnderArmourId2;
    }

    public void setRecordId(int recordId2) {
        this.recordId = recordId2;
    }

    public void setConsoleUserNumber(int consoleUserNumber2) {
        this.consoleUserNumber = consoleUserNumber2;
    }

    public void setMachineType(String machineType2) {
        this.machineType = machineType2;
    }

    public void setSyncDate(DateTime syncDate2) {
        this.syncDate = syncDate2;
    }

    public void setOriginalSecond(int originalSecond2) {
        this.originalSecond = originalSecond2;
    }

    public void setOriginalMinute(int originalMinute2) {
        this.originalMinute = originalMinute2;
    }

    public void setOriginalHour(int originalHour2) {
        this.originalHour = originalHour2;
    }

    public void setOriginalDay(int originalDay2) {
        this.originalDay = originalDay2;
    }

    public void setOriginalMonth(int originalMonth2) {
        this.originalMonth = originalMonth2;
    }

    public void setOriginalYear(int originalYear2) {
        this.originalYear = originalYear2;
    }

    public void setCustomKey(String customKey2) {
        this.customKey = customKey2;
    }
}
