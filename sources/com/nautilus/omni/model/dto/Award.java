package com.nautilus.omni.model.dto;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.io.Serializable;
import org.joda.time.DateTime;

@DatabaseTable(tableName = "awards")
public class Award implements Serializable {
    public static final String AWARD_TYPE = "award_type";
    public static final String DATE = "date";
    public static final String ID_FIELD_NAME = "id";
    public static final String VALUE = "value";
    public static final String WORKOUT = "workout";
    @DatabaseField(columnName = "award_type", foreign = true, foreignAutoRefresh = true)
    private AwardType mAwardType;
    @DatabaseField(columnName = "date")
    private DateTime mDate;
    @DatabaseField(columnName = "id", generatedId = true)
    private int mId;
    @DatabaseField(columnName = "value")
    private String mValue;
    @DatabaseField(columnName = "workout", foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true)
    private Workout mWorkout;

    public int getmId() {
        return this.mId;
    }

    public String getmValue() {
        return this.mValue;
    }

    public DateTime getmDate() {
        return this.mDate;
    }

    public AwardType getmAwardType() {
        return this.mAwardType;
    }

    public Workout getmWorkout() {
        return this.mWorkout;
    }

    public void setmId(int mId2) {
        this.mId = mId2;
    }

    public void setmValue(String mValue2) {
        this.mValue = mValue2;
    }

    public void setmDate(DateTime mDate2) {
        this.mDate = mDate2;
    }

    public void setmAwardType(AwardType mAwardType2) {
        this.mAwardType = mAwardType2;
    }

    public void setmWorkout(Workout mWorkout2) {
        this.mWorkout = mWorkout2;
    }
}
