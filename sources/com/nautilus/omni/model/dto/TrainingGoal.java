package com.nautilus.omni.model.dto;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.io.Serializable;
import org.joda.time.DateTime;

@DatabaseTable(tableName = "new_training_goal")
public class TrainingGoal implements Serializable {
    public static final String ACCUMULATED_GOAL_VALUE = "accumulated_goal_value";
    public static final String ACHIEVED = "achieved";
    public static final String END_DATE = "end_date";
    public static final String GOAL_TYPE = "goal_type";
    public static final String GOAL_VALUE = "goal_value";
    public static final String ID_FIELD_NAME = "id";
    public static final String START_DATE = "start_date";
    public static final String USER = "user";
    @DatabaseField(columnName = "accumulated_goal_value")
    private int mAccumulatedGoalValue;
    @DatabaseField(columnName = "achieved")
    private boolean mAchieved;
    @DatabaseField(columnName = "end_date")
    private DateTime mEndDate;
    @DatabaseField(columnName = "goal_type")
    private GoalType mGoalType;
    @DatabaseField(columnName = "goal_value")
    private String mGoalValue;
    @DatabaseField(columnName = "id", generatedId = true)
    private int mId;
    @DatabaseField(columnName = "start_date")
    private DateTime mStartDate;
    @DatabaseField(columnName = "user", foreign = true)
    private User mUser;

    public int getmId() {
        return this.mId;
    }

    public DateTime getmStartDate() {
        return this.mStartDate;
    }

    public DateTime getmEndDate() {
        return this.mEndDate;
    }

    public User getmUser() {
        return this.mUser;
    }

    public GoalType getmGoalType() {
        return this.mGoalType;
    }

    public String getmGoalValue() {
        return this.mGoalValue;
    }

    public boolean ismAchieved() {
        return this.mAchieved;
    }

    public int getmAccumulatedGoalValue() {
        return this.mAccumulatedGoalValue;
    }

    public void setmId(int mId2) {
        this.mId = mId2;
    }

    public void setmStartDate(DateTime mStartDate2) {
        this.mStartDate = mStartDate2;
    }

    public void setmEndDate(DateTime mEndDate2) {
        this.mEndDate = mEndDate2;
    }

    public void setmAchieved(boolean mAchieved2) {
        this.mAchieved = mAchieved2;
    }

    public void setmUser(User mUser2) {
        this.mUser = mUser2;
    }

    public void setmGoalType(GoalType mGoalType2) {
        this.mGoalType = mGoalType2;
    }

    public void setmGoalValue(String mGoalValue2) {
        this.mGoalValue = mGoalValue2;
    }

    public void setmAccumulatedGoalValue(int mAccumulatedGoalValue2) {
        this.mAccumulatedGoalValue = mAccumulatedGoalValue2;
    }
}
