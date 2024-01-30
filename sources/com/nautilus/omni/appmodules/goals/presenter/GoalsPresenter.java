package com.nautilus.omni.appmodules.goals.presenter;

import android.content.Context;
import com.nautilus.omni.app.BasePresenter;
import com.nautilus.omni.app.Preferences;
import com.nautilus.omni.appmodules.goals.presenter.interactors.LoadGoalsInteractor;
import com.nautilus.omni.appmodules.goals.presenter.interactors.LoadGoalsInteractorContract;
import com.nautilus.omni.appmodules.goals.presenter.interactors.SaveGoalsInteractor;
import com.nautilus.omni.appmodules.goals.presenter.interactors.SaveGoalsInteractorContract;
import com.nautilus.omni.appmodules.goals.view.GoalsViewContract;
import com.nautilus.omni.model.dto.TrainingGoal;
import com.nautilus.omni.model.dto.User;
import javax.inject.Inject;

public class GoalsPresenter extends BasePresenter implements GoalsPresenterContract, LoadGoalsInteractorContract.OnGoalsLoadedListener, SaveGoalsInteractorContract.OnGoalsSavedListener, LoadGoalsInteractorContract.OnGoalPickerValuesListener {
    private User mCurrentUser;
    private GoalsViewContract mIGoalsView;
    private LoadGoalsInteractor mLoadGoalsInteractor;
    private SaveGoalsInteractor mSaveGoalsInteractor;
    private int mUserIndex;

    @Inject
    public GoalsPresenter(Context context, LoadGoalsInteractor goalsInteractor, SaveGoalsInteractor saveGoalsInteractor) {
        super(context);
        getCurrentUserFromDatabase();
        this.mLoadGoalsInteractor = goalsInteractor;
        this.mSaveGoalsInteractor = saveGoalsInteractor;
        this.mLoadGoalsInteractor.setCurrentUser(this.mCurrentUser);
        this.mSaveGoalsInteractor.setCurrentUser(this.mCurrentUser);
    }

    public void setIGoalsView(GoalsViewContract goalsView) {
        this.mIGoalsView = goalsView;
    }

    private void getCurrentUserFromDatabase() {
        this.mUserIndex = this.mPreferences.getInt(Preferences.USER_INDEX, -1);
        this.mCurrentUser = this.mUserDaoHelper.getCurrentUser(this.mUserIndex);
    }

    public void loadGoalPickerValues() {
        this.mLoadGoalsInteractor.loadGoalPickerValues(this);
    }

    public void onCalorieGoalPickerValuesLoaded(String[] arrayCalorieValues, String calorieDefaultValue) {
        this.mIGoalsView.loadCaloriePicker(arrayCalorieValues, calorieDefaultValue);
    }

    public void onTimeGoalPickerValuesLoaded(String[] arrayTimeGoalValues, String timeDefaultValue) {
        this.mIGoalsView.loadTimeGoalPicker(arrayTimeGoalValues, timeDefaultValue);
    }

    public void onWorkoutsPerWeekPickerValuesLoaded(String[] arrayNumberOfWorkoutsValues, String workoutNumberDefaultValue) {
        this.mIGoalsView.loadNumberWorkoutsPicker(arrayNumberOfWorkoutsValues, workoutNumberDefaultValue);
    }

    public void loadCurrentGoals() {
        this.mSaveGoalsInteractor.initWorkoutsList();
        this.mLoadGoalsInteractor.loadCurrentGoals(this);
    }

    public void onCaloriePerWorkoutGoalLoaded(TrainingGoal calorieGoal) {
        this.mSaveGoalsInteractor.setmCurrentCaloriePerWorkoutGoal(calorieGoal);
        this.mIGoalsView.loadCurrentCalorieGoal(calorieGoal);
    }

    public void onTimePerWorkoutGoalLoaded(TrainingGoal timeGoal) {
        this.mSaveGoalsInteractor.setmCurrentWorkoutTimePerWorkoutGoal(timeGoal);
        this.mIGoalsView.loadCurrentTimeGoal(timeGoal);
    }

    public void onWorkoutsPerWeekGoalLoaded(TrainingGoal numberWorkoutsGoal) {
        this.mSaveGoalsInteractor.setmCurrentWorkoutsPerWeekGoal(numberWorkoutsGoal);
        this.mIGoalsView.loadCurrentNumberWorkoutsGoal(numberWorkoutsGoal);
    }

    public void saveGoals(String calorieGoalSelectedValue, String numberWorkoutsGoalSelectedValue, String timeGoalSelectedValue) {
        this.mSaveGoalsInteractor.saveGoals(this, calorieGoalSelectedValue, numberWorkoutsGoalSelectedValue, timeGoalSelectedValue);
    }

    public void onGoalsSaved(TrainingGoal calorieGoal, TrainingGoal numberWorkoutsGoal, TrainingGoal timeGoal) {
        this.mIGoalsView.showGoalsSavedToast(calorieGoal, numberWorkoutsGoal, timeGoal);
    }

    public void disableGoals() {
        this.mSaveGoalsInteractor.disableGoals();
    }

    public User getCurrentUser() {
        return this.mCurrentUser;
    }

    public void onDestroy() {
    }
}
