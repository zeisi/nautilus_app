package com.nautilus.omni.appmodules.goals.presenter.interactors;

import android.content.Context;
import com.nautilus.omni.R;
import com.nautilus.omni.appmodules.goals.presenter.interactors.LoadGoalsInteractorContract;
import com.nautilus.omni.dataaccess.GoalsDaoHelper;
import com.nautilus.omni.model.dto.GoalType;
import com.nautilus.omni.model.dto.TrainingGoal;
import com.nautilus.omni.model.dto.User;
import com.nautilus.omni.util.DateUtil;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;
import org.joda.time.DateTime;

public class LoadGoalsInteractor implements LoadGoalsInteractorContract {
    private Context mContext;
    private TrainingGoal mCurrentCaloriePerWorkoutGoal;
    private DateTime mCurrentDate = new DateTime();
    private TrainingGoal mCurrentTimePerWorkoutGoal;
    private User mCurrentUser;
    private TrainingGoal mCurrentWorkoutsPerWeekGoal;
    private GoalsDaoHelper mGoalsDaoHelper;
    private LoadGoalsInteractorContract.OnGoalPickerValuesListener mOnGoalPickerValuesListener;
    private LoadGoalsInteractorContract.OnGoalsLoadedListener mOnGoalsLoadListener;

    @Inject
    public LoadGoalsInteractor(Context context, GoalsDaoHelper goalsDaoHelper) {
        this.mGoalsDaoHelper = goalsDaoHelper;
        this.mContext = context;
    }

    public void loadGoalPickerValues(LoadGoalsInteractorContract.OnGoalPickerValuesListener onGoalPickerValuesListener) {
        this.mOnGoalPickerValuesListener = onGoalPickerValuesListener;
        loadGoalCalorieValues();
        loadTimeGoalValues();
        loadWorkoutsNumberGoalValues();
    }

    private void loadGoalCalorieValues() {
        String caloriesDefaultValue = this.mContext.getString(R.string.goals_calories_burned_default_value);
        this.mOnGoalPickerValuesListener.onCalorieGoalPickerValuesLoaded(this.mContext.getResources().getStringArray(R.array.goals_calories_burned_array), caloriesDefaultValue);
    }

    private void loadTimeGoalValues() {
        String timeDefaultValue = this.mContext.getString(R.string.goals_workout_time_default_value);
        this.mOnGoalPickerValuesListener.onTimeGoalPickerValuesLoaded(this.mContext.getResources().getStringArray(R.array.goals_workout_time_array), timeDefaultValue);
    }

    private void loadWorkoutsNumberGoalValues() {
        String numberWorkoutsDefaultValue = this.mContext.getString(R.string.goals_workout_number_default_value);
        this.mOnGoalPickerValuesListener.onWorkoutsPerWeekPickerValuesLoaded(this.mContext.getResources().getStringArray(R.array.goals_workout_number_array), numberWorkoutsDefaultValue);
    }

    public void loadCurrentGoals(LoadGoalsInteractorContract.OnGoalsLoadedListener onGoalsLoadListener) {
        this.mOnGoalsLoadListener = onGoalsLoadListener;
        loadGoalsValues();
    }

    private void loadGoalsValues() {
        List<TrainingGoal> goalsFromCurrentWeek = this.mGoalsDaoHelper.getTrainingGoalsFromSpecificWeek(this.mCurrentUser, this.mCurrentDate);
        if (haveGoals(goalsFromCurrentWeek)) {
            getCurrentGoals(goalsFromCurrentWeek);
        }
    }

    private boolean haveGoals(List<TrainingGoal> goalsList) {
        return goalsList != null && goalsList.size() > 0;
    }

    private void getCurrentGoals(Collection<TrainingGoal> trainingGoalsCollection) {
        for (TrainingGoal element : trainingGoalsCollection) {
            setCurrentCalorieGoal(element);
            setCurrentWorkoutNumberGoal(element);
            setCurrentWorkoutTimeGoal(element);
        }
    }

    private void setCurrentCalorieGoal(TrainingGoal element) {
        if (element.getmGoalType().equals(GoalType.CALORIES_PER_WORKOUT) && DateUtil.isDateBetween(element.getmStartDate(), this.mCurrentDate, element.getmEndDate())) {
            this.mCurrentCaloriePerWorkoutGoal = element;
            this.mOnGoalsLoadListener.onCaloriePerWorkoutGoalLoaded(this.mCurrentCaloriePerWorkoutGoal);
        }
    }

    private void setCurrentWorkoutNumberGoal(TrainingGoal element) {
        if (element.getmGoalType().equals(GoalType.WORKOUTS_PER_WEEK) && DateUtil.isDateBetween(element.getmStartDate(), this.mCurrentDate, element.getmEndDate())) {
            this.mCurrentWorkoutsPerWeekGoal = element;
            this.mOnGoalsLoadListener.onWorkoutsPerWeekGoalLoaded(this.mCurrentWorkoutsPerWeekGoal);
        }
    }

    private void setCurrentWorkoutTimeGoal(TrainingGoal element) {
        if (element.getmGoalType().equals(GoalType.TIME_PER_WORKOUT) && DateUtil.isDateBetween(element.getmStartDate(), this.mCurrentDate, element.getmEndDate())) {
            this.mCurrentTimePerWorkoutGoal = element;
            this.mOnGoalsLoadListener.onTimePerWorkoutGoalLoaded(this.mCurrentTimePerWorkoutGoal);
        }
    }

    public void setCurrentUser(User mCurrentUser2) {
        this.mCurrentUser = mCurrentUser2;
    }
}
