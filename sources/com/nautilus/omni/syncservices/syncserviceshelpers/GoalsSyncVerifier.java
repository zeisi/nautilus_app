package com.nautilus.omni.syncservices.syncserviceshelpers;

import com.nautilus.omni.dataaccess.AchievementsDaoHelper;
import com.nautilus.omni.dataaccess.GoalsDaoHelper;
import com.nautilus.omni.model.dto.TrainingGoal;
import com.nautilus.omni.model.dto.User;
import com.nautilus.omni.model.dto.Workout;
import java.sql.SQLException;
import java.util.List;

public class GoalsSyncVerifier {
    private AchievementsDaoHelper mAchievementDaoHelper;
    private User mCurrentUser;
    private GoalsDaoHelper mGoalsDaoHelper;
    private boolean mGoalsEnabled;

    public GoalsSyncVerifier(AchievementsDaoHelper achievementsDaoHelper, GoalsDaoHelper goalsDaoHelper, User user, boolean goalsEnabled) {
        this.mAchievementDaoHelper = achievementsDaoHelper;
        this.mGoalsEnabled = goalsEnabled;
        this.mCurrentUser = user;
        this.mGoalsDaoHelper = goalsDaoHelper;
    }

    public void verifyGoals(Workout workout) throws SQLException {
        if (this.mGoalsEnabled) {
            checkIfWorkoutAchievedGoal(workout);
        }
    }

    private void checkIfWorkoutAchievedGoal(Workout workout) throws SQLException {
        List<TrainingGoal> currentGoalsList = this.mGoalsDaoHelper.getTrainingGoalsFromSpecificWeek(this.mCurrentUser, workout.getmWorkoutDate());
        if (currentGoalsList.size() > 0) {
            checkIfCaloriesPerWorkoutGoalWasAchieved(workout, currentGoalsList.get(0));
            checkIfWorkoutsPerWeekGoalWasAchieved(workout, currentGoalsList.get(1));
            checkIfTimePerWorkoutGoalWasAchieved(workout, currentGoalsList.get(2));
        }
    }

    private void checkIfCaloriesPerWorkoutGoalWasAchieved(Workout workout, TrainingGoal calorieGoal) throws SQLException {
        if (Integer.valueOf(calorieGoal.getmGoalValue()).intValue() <= workout.getmCalories()) {
            calorieGoal.setmAchieved(true);
            this.mAchievementDaoHelper.createNewGoalAchievement(workout, this.mCurrentUser, calorieGoal, calorieGoal.getmGoalValue());
        }
        calorieGoal.setmAccumulatedGoalValue(calorieGoal.getmAccumulatedGoalValue() + workout.getmCalories());
        this.mGoalsDaoHelper.updateGoal(calorieGoal);
    }

    private void checkIfWorkoutsPerWeekGoalWasAchieved(Workout workout, TrainingGoal workoutPerWeekGoal) throws SQLException {
        if (!workoutPerWeekGoal.ismAchieved()) {
            if (Integer.valueOf(workoutPerWeekGoal.getmGoalValue()).intValue() <= workoutPerWeekGoal.getmAccumulatedGoalValue() + 1) {
                workoutPerWeekGoal.setmAchieved(true);
                this.mAchievementDaoHelper.createNewGoalAchievement(workout, this.mCurrentUser, workoutPerWeekGoal, workoutPerWeekGoal.getmGoalValue());
            }
            workoutPerWeekGoal.setmAccumulatedGoalValue(workoutPerWeekGoal.getmAccumulatedGoalValue() + 1);
            this.mGoalsDaoHelper.updateGoal(workoutPerWeekGoal);
        }
    }

    private void checkIfTimePerWorkoutGoalWasAchieved(Workout workout, TrainingGoal timeGoal) throws SQLException {
        int selectedMinutes = Integer.parseInt(timeGoal.getmGoalValue().substring(0, 2));
        updateTimeGoal(workout, (selectedMinutes * 60) + Integer.parseInt(timeGoal.getmGoalValue().substring(3, 5)), timeGoal);
    }

    private void updateTimeGoal(Workout currentWorkout, int totalSelectedSeconds, TrainingGoal timeGoal) throws SQLException {
        if (currentWorkout.getmElapsedTime() >= totalSelectedSeconds) {
            this.mAchievementDaoHelper.createNewGoalAchievement(currentWorkout, this.mCurrentUser, timeGoal, timeGoal.getmGoalValue());
            timeGoal.setmAchieved(true);
            this.mGoalsDaoHelper.updateGoal(timeGoal);
        }
    }
}
