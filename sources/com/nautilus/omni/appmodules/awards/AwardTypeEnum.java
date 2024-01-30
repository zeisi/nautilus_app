package com.nautilus.omni.appmodules.awards;

public enum AwardTypeEnum {
    MOST_WORKOUTS_WEEK("most_workouts_week_award_name"),
    THREE_OR_MORE_WORKOUTS_IN_A_WEEK("three_workouts_in_week_award_name"),
    MOST_CALORIES_WORKOUT("most_calories_award_name"),
    HIGHEST_AVG_CALORIE("highest_avg_calories_award_name"),
    HIGHEST_AVG_SPEED("highest_speed_award_name"),
    LONGEST_DISTANCE("longest_distance_award_name"),
    LONGEST_WORKOUT("longest_workout_award_name"),
    MOST_DISTANCE_PER_WEEK("most_distance_award_name"),
    FIRST_WORKOUT("first_workout_award_name"),
    BEST_WORKOUT("best_workout_award_name");
    
    private final String mName;

    private AwardTypeEnum(String name) {
        this.mName = name;
    }

    public boolean equalsName(String otherName) {
        if (otherName == null) {
            return false;
        }
        return this.mName.equals(otherName);
    }

    public String toString() {
        return this.mName;
    }
}
