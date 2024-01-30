package com.nautilus.omni.appmodules.splash.presenter;

import android.content.SharedPreferences;
import com.nautilus.omni.app.Preferences;
import com.nautilus.omni.dataaccess.AchievementsDaoHelper;
import com.nautilus.omni.dataaccess.GoalsDaoHelper;
import com.nautilus.omni.dataaccess.WorkoutDaoHelper;
import com.nautilus.omni.model.dto.GoalType;
import com.nautilus.omni.model.dto.TrainingGoal;
import com.nautilus.omni.model.dto.User;
import com.nautilus.omni.model.dto.Workout;
import com.nautilus.omni.syncservices.syncserviceshelpers.GoalsSyncVerifier;
import com.nautilus.omni.util.Util;
import java.sql.SQLException;
import org.joda.time.DateTime;

public class GoalsUpdater {
    private AchievementsDaoHelper mAchievementsDaoHelper;
    private User mCurrentUser;
    private DateTime mCurrentWeekDate = new DateTime();
    private GoalsDaoHelper mGoalsDaoHelper;
    private GoalsSyncVerifier mGoalsSyncVerifier;
    private SharedPreferences mPreferences;
    private WorkoutDaoHelper mWorkoutDaoHelper;

    public GoalsUpdater(boolean goalsEnabled, WorkoutDaoHelper workoutDaoHelper, GoalsDaoHelper goalsDaoHelper, AchievementsDaoHelper achievementsDaoHelper, User user, SharedPreferences preferences) {
        this.mWorkoutDaoHelper = workoutDaoHelper;
        this.mGoalsDaoHelper = goalsDaoHelper;
        this.mAchievementsDaoHelper = achievementsDaoHelper;
        this.mCurrentUser = user;
        this.mPreferences = preferences;
        this.mGoalsSyncVerifier = new GoalsSyncVerifier(this.mAchievementsDaoHelper, this.mGoalsDaoHelper, this.mCurrentUser, goalsEnabled);
    }

    public void updateTimeGoalValues() {
        updateCalorieGoalOfCurrentWeek();
        updateWorkoutsPerWeekGoalOfCurrentWeek();
        updateTimeGoalCurrentMostWeek();
        deleteAchievements();
        reevaluateWorkoutsFromCurrentWeek();
    }

    private void updateTimeGoalCurrentMostWeek() {
        TrainingGoal timeGoal = this.mGoalsDaoHelper.getMostRecentGoalByType(this.mCurrentUser, GoalType.TIME_PER_WORKOUT);
        if (timeGoal != null) {
            timeGoal.setmGoalValue(buildNewTimeGoalValue(timeGoal.getmGoalValue()));
            timeGoal.setmAccumulatedGoalValue(0);
            timeGoal.setmAchieved(false);
            this.mGoalsDaoHelper.updateGoal(timeGoal);
        }
    }

    private void updateWorkoutsPerWeekGoalOfCurrentWeek() {
        TrainingGoal workoutPerWeekGoal = this.mGoalsDaoHelper.getGoalFromSpecificWeek(this.mCurrentUser, this.mCurrentWeekDate, GoalType.WORKOUTS_PER_WEEK);
        if (workoutPerWeekGoal != null) {
            workoutPerWeekGoal.setmAccumulatedGoalValue(0);
            workoutPerWeekGoal.setmAchieved(false);
            this.mGoalsDaoHelper.updateGoal(workoutPerWeekGoal);
        }
    }

    private void updateCalorieGoalOfCurrentWeek() {
        TrainingGoal calorieGoal = this.mGoalsDaoHelper.getGoalFromSpecificWeek(this.mCurrentUser, this.mCurrentWeekDate, GoalType.CALORIES_PER_WORKOUT);
        if (calorieGoal != null) {
            calorieGoal.setmAccumulatedGoalValue(0);
            calorieGoal.setmAchieved(false);
            this.mGoalsDaoHelper.updateGoal(calorieGoal);
        }
    }

    private String buildNewTimeGoalValue(String currentTimeGoalValue) {
        int selectedMinutes = Integer.parseInt(currentTimeGoalValue.substring(0, 2));
        while (selectedMinutes % 5 != 0) {
            selectedMinutes++;
        }
        return Util.convertSecondsToTimeFormatInMinutesAndSeconds(selectedMinutes * 60);
    }

    private void deleteAchievements() {
        this.mAchievementsDaoHelper.deleteAchievements(this.mCurrentWeekDate);
    }

    private void reevaluateWorkoutsFromCurrentWeek() {
        for (Workout workout : this.mWorkoutDaoHelper.getWorkoutsFromSpecificWeek(this.mCurrentWeekDate, true)) {
            try {
                this.mGoalsSyncVerifier.verifyGoals(workout);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        setTimeGoalUpdateFlagToTrue();
    }

    private void setTimeGoalUpdateFlagToTrue() {
        this.mPreferences.edit().putBoolean(Preferences.TIME_GOAL_VALUES_ALREADY_UPDATED, true).commit();
        this.mPreferences.edit().putBoolean(Preferences.SHOW_TIME_GOAL_UPDATED_DIALOG, true).commit();
    }
}
