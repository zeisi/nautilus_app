package com.nautilus.omni.model.dto;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.io.Serializable;
import org.joda.time.DateTime;

@DatabaseTable(tableName = "achievement")
public class Achievement implements Serializable {
    public static final String GOAL_ACHIEVED_VALUE = "goal_achieved_value";
    public static final String GOAL_ACHIEVEMENT_DATE = "goal_achievement_date";
    public static final String GOAL_TYPE = "goal_type";
    public static final String ID_FIELD_NAME = "id";
    public static final String USER = "user";
    @DatabaseField(columnName = "goal_achieved_value")
    private String mGoalAchievedValue;
    @DatabaseField(columnName = "goal_achievement_date")
    private DateTime mGoalAchievementDate;
    @DatabaseField(columnName = "goal_type")
    private GoalType mGoalType;
    @DatabaseField(columnName = "id", generatedId = true)
    private int mId;
    @DatabaseField(columnName = "user", foreign = true)
    private User mUser;

    public int getmId() {
        return this.mId;
    }

    public GoalType getmGoalType() {
        return this.mGoalType;
    }

    public String getmGoalAchievedValue() {
        return this.mGoalAchievedValue;
    }

    public DateTime getmGoalAchievementDate() {
        return this.mGoalAchievementDate;
    }

    public User getmUser() {
        return this.mUser;
    }

    public void setmId(int mId2) {
        this.mId = mId2;
    }

    public void setmGoalType(GoalType mGoalType2) {
        this.mGoalType = mGoalType2;
    }

    public void setmGoalAchievedValue(String mGoalAchievedValue2) {
        this.mGoalAchievedValue = mGoalAchievedValue2;
    }

    public void setmGoalAchievementDate(DateTime mGoalAchievementDate2) {
        this.mGoalAchievementDate = mGoalAchievementDate2;
    }

    public void setmUser(User mUser2) {
        this.mUser = mUser2;
    }
}
