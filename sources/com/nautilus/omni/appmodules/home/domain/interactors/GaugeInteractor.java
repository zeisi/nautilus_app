package com.nautilus.omni.appmodules.home.domain.interactors;

import com.nautilus.omni.appmodules.home.domain.interactors.GaugeInteractorContract;
import com.nautilus.omni.dataaccess.GoalsDaoHelper;
import com.nautilus.omni.dataaccess.UserDaoHelper;
import com.nautilus.omni.dataaccess.WorkoutDaoHelper;
import com.nautilus.omni.model.dto.GoalType;
import com.nautilus.omni.model.dto.TrainingGoal;
import com.nautilus.omni.model.dto.User;
import com.nautilus.omni.model.dto.Workout;
import java.util.List;
import org.joda.time.DateTime;

public class GaugeInteractor implements GaugeInteractorContract {
    GoalsDaoHelper mGoalsDaoHelper;
    UserDaoHelper mUserDaoHelper;
    WorkoutDaoHelper mWorkoutDaoHelper;

    public GaugeInteractor(WorkoutDaoHelper workoutDaoHelper, UserDaoHelper userDaoHelper, GoalsDaoHelper goalsDaoHelper) {
        this.mWorkoutDaoHelper = workoutDaoHelper;
        this.mUserDaoHelper = userDaoHelper;
        this.mGoalsDaoHelper = goalsDaoHelper;
    }

    public void loadTextData(int kind, boolean enableGoals, int currentUserIndex, GaugeInteractorContract.OnTextDataListener onTextDataListener) {
        onTextDataListener.loadData(kind, this.mWorkoutDaoHelper.getLastWorkout(), this.mWorkoutDaoHelper.getPreviousWorkout(), this.mUserDaoHelper.getCurrentUser(currentUserIndex));
    }

    public void loadDataFromGoals(int kind, Workout lastWorkout, User currentUser, GaugeInteractorContract.OnTextDataListener onTextDataListener) {
        TrainingGoal calorieGoal = this.mGoalsDaoHelper.getGoalFromSpecificWeek(currentUser, new DateTime(), GoalType.CALORIES_PER_WORKOUT);
        TrainingGoal timeGoal = this.mGoalsDaoHelper.getGoalFromSpecificWeek(currentUser, new DateTime(), GoalType.TIME_PER_WORKOUT);
        if (lastWorkout != null && calorieGoal != null && timeGoal != null) {
            onTextDataListener.showDataComparedToCurrentGoals(kind, lastWorkout, currentUser, calorieGoal, timeGoal);
        }
    }

    public void loadGoalsConfigData(int kind, User currentUser, GaugeInteractorContract.OnTextDataListener onTextDataListener) {
        TrainingGoal currentCaloriePerWorkoutGoal = null;
        TrainingGoal currentWorkoutsPerWeekGoal = null;
        TrainingGoal currentTimePerWorkoutGoal = null;
        List<TrainingGoal> goalsFromCurrentWeek = this.mGoalsDaoHelper.getTrainingGoalsFromSpecificWeek(currentUser, new DateTime());
        if (haveGoals(goalsFromCurrentWeek)) {
            for (TrainingGoal element : goalsFromCurrentWeek) {
                if (element.getmGoalType().equals(GoalType.CALORIES_PER_WORKOUT)) {
                    currentCaloriePerWorkoutGoal = element;
                } else if (element.getmGoalType().equals(GoalType.WORKOUTS_PER_WEEK)) {
                    currentWorkoutsPerWeekGoal = element;
                } else if (element.getmGoalType().equals(GoalType.TIME_PER_WORKOUT)) {
                    currentTimePerWorkoutGoal = element;
                }
            }
        }
        onTextDataListener.loadGoalsConfigDataSuccess(kind, currentWorkoutsPerWeekGoal, currentCaloriePerWorkoutGoal, currentTimePerWorkoutGoal);
    }

    private boolean haveGoals(List<TrainingGoal> goalsList) {
        return goalsList != null && goalsList.size() > 0;
    }
}
