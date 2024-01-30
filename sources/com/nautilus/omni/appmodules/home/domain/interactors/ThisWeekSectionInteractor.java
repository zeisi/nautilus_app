package com.nautilus.omni.appmodules.home.domain.interactors;

import com.nautilus.omni.appmodules.home.domain.interactors.ThisWeekSectionInteractorContract;
import com.nautilus.omni.dataaccess.GoalsDaoHelper;
import com.nautilus.omni.dataaccess.UserDaoHelper;
import com.nautilus.omni.dataaccess.WeekDaoHelper;
import com.nautilus.omni.dataaccess.WorkoutDaoHelper;
import com.nautilus.omni.model.dto.GoalType;
import com.nautilus.omni.model.dto.TrainingGoal;
import com.nautilus.omni.model.dto.Week;
import java.util.List;
import org.joda.time.DateTime;

public class ThisWeekSectionInteractor implements ThisWeekSectionInteractorContract {
    private final GoalsDaoHelper mGoalsDaoHelper;
    private final UserDaoHelper mUserDaoHelper;
    private final WeekDaoHelper mWeekDaoHelper;
    private final WorkoutDaoHelper mWorkoutDaoHelper;

    public ThisWeekSectionInteractor(GoalsDaoHelper mGoalsDaoHelper2, WeekDaoHelper mWeekDaoHelper2, UserDaoHelper userDaoHelper, WorkoutDaoHelper mWorkoutDaoHelper2) {
        this.mGoalsDaoHelper = mGoalsDaoHelper2;
        this.mWeekDaoHelper = mWeekDaoHelper2;
        this.mUserDaoHelper = userDaoHelper;
        this.mWorkoutDaoHelper = mWorkoutDaoHelper2;
    }

    public void loadData(int currentUserIndex, int kind, ThisWeekSectionInteractorContract.OnLoadData onLoadData) {
        Week mCurrentWeek = this.mWeekDaoHelper.getWeek(new DateTime());
        Week mPreviousWeek = this.mWeekDaoHelper.getWeek(new DateTime().minusWeeks(1));
        TrainingGoal currentCaloriePerWorkoutGoal = null;
        TrainingGoal currentWorkoutsPerWeekGoal = null;
        TrainingGoal currentTimePerWorkoutGoal = null;
        List<TrainingGoal> goalsFromCurrentWeek = this.mGoalsDaoHelper.getTrainingGoalsFromSpecificWeek(this.mUserDaoHelper.getCurrentUser(currentUserIndex), new DateTime());
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
        onLoadData.onLoadDataSuccess(kind, currentWorkoutsPerWeekGoal, currentCaloriePerWorkoutGoal, currentTimePerWorkoutGoal, mCurrentWeek, mPreviousWeek);
    }

    public void loadAvgRateData(ThisWeekSectionInteractorContract.OnLoadData onLoadData) {
        onLoadData.onLoadAvgRateDataSuccess(this.mWorkoutDaoHelper.getWorkoutsFromSpecificWeek(new DateTime(), true), this.mWorkoutDaoHelper.getWorkoutsFromSpecificWeek(new DateTime().minusWeeks(1), true));
    }

    public void loadAvgWattsData(ThisWeekSectionInteractorContract.OnLoadData onLoadData) {
        onLoadData.onLoadAvgWattsDataSuccess(this.mWorkoutDaoHelper.getWorkoutsFromSpecificWeek(new DateTime(), true), this.mWorkoutDaoHelper.getWorkoutsFromSpecificWeek(new DateTime().minusWeeks(1), true));
    }

    private boolean haveGoals(List<TrainingGoal> goalsList) {
        return goalsList != null && goalsList.size() > 0;
    }
}
