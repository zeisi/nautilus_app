package com.nautilus.omni.appmodules.mainactivity.presenter;

import android.content.Context;
import com.nautilus.omni.R;
import com.nautilus.omni.appmodules.goals.presenter.interactors.SaveGoalsInteractor;
import com.nautilus.omni.appmodules.mainactivity.presenter.MainGoalsHelperInteractorContract;
import com.nautilus.omni.dataaccess.GoalsDaoHelper;
import com.nautilus.omni.model.dto.GoalType;
import com.nautilus.omni.model.dto.TrainingGoal;
import com.nautilus.omni.model.dto.User;
import java.util.List;
import org.joda.time.DateTime;

public class MainGoalsHelperInteractor implements MainGoalsHelperInteractorContract {
    private Context mContext;
    private DateTime mCurrentDate = new DateTime();
    private MainGoalsHelperInteractorContract.CurrentGoalsCheckListener mCurrentGoalsCheckedListener;
    private User mCurrentUser;
    private GoalsDaoHelper mGoalsDaoHelper;
    private boolean mGoalsEnabled;
    private SaveGoalsInteractor mSaveGoalsInteractor;
    private boolean mShouldShowTimeGoalUpdatedDialog;

    public MainGoalsHelperInteractor(Context context, SaveGoalsInteractor saveGoalsInteractor, GoalsDaoHelper goalsDaoHelper) {
        this.mContext = context;
        this.mGoalsDaoHelper = goalsDaoHelper;
        this.mSaveGoalsInteractor = saveGoalsInteractor;
    }

    public void setCurrentUser(User currentUser) {
        this.mCurrentUser = currentUser;
        this.mSaveGoalsInteractor.setCurrentUser(this.mCurrentUser);
    }

    public void setGoalsEnabled(boolean goalsEnabled) {
        this.mGoalsEnabled = goalsEnabled;
    }

    public void setShouldShowTimeGoalUpdatedDialog(boolean shouldShowTimeGoalUpdatedDialog) {
        this.mShouldShowTimeGoalUpdatedDialog = shouldShowTimeGoalUpdatedDialog;
    }

    public void checkCurrentGoals(MainGoalsHelperInteractorContract.CurrentGoalsCheckListener currentGoalsCheckedListener) {
        this.mCurrentGoalsCheckedListener = currentGoalsCheckedListener;
        checkIfShouldShowTimeGoalUpdatedDialog();
        if (!userHasGoalsForCurrentWeek() && this.mGoalsEnabled) {
            setDefaultGoals();
            this.mCurrentGoalsCheckedListener.onDefaultGoalsSaved();
        }
    }

    private void checkIfShouldShowTimeGoalUpdatedDialog() {
        if (this.mShouldShowTimeGoalUpdatedDialog) {
            this.mCurrentGoalsCheckedListener.onTimeGoalsUpdated();
        }
    }

    private boolean userHasGoalsForCurrentWeek() {
        List<TrainingGoal> trainingGoals = this.mGoalsDaoHelper.getTrainingGoalsFromSpecificWeek(this.mCurrentUser, this.mCurrentDate);
        if (trainingGoals == null || trainingGoals.size() <= 0) {
            return false;
        }
        return true;
    }

    private void setDefaultGoals() {
        List<TrainingGoal> trainingGoals = this.mGoalsDaoHelper.getMostRecentGoals(this.mCurrentUser);
        if (trainingGoals == null || trainingGoals.size() <= 0) {
            this.mSaveGoalsInteractor.saveGoalsData(this.mContext.getString(R.string.goals_calories_burned_default_value), this.mContext.getString(R.string.goals_workout_number_default_value), this.mContext.getString(R.string.goals_workout_time_default_value));
            return;
        }
        this.mSaveGoalsInteractor.saveGoalsData(getGoal(trainingGoals, GoalType.CALORIES_PER_WORKOUT).getmGoalValue(), getGoal(trainingGoals, GoalType.WORKOUTS_PER_WEEK).getmGoalValue(), getGoal(trainingGoals, GoalType.TIME_PER_WORKOUT).getmGoalValue());
    }

    private TrainingGoal getGoal(List<TrainingGoal> trainingGoals, GoalType goalType) {
        for (TrainingGoal goal : trainingGoals) {
            if (goal.getmGoalType() == goalType) {
                return goal;
            }
        }
        return trainingGoals.get(0);
    }
}
