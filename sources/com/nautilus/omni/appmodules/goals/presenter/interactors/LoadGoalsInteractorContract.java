package com.nautilus.omni.appmodules.goals.presenter.interactors;

import com.nautilus.omni.model.dto.TrainingGoal;

public interface LoadGoalsInteractorContract {

    public interface OnGoalPickerValuesListener {
        void onCalorieGoalPickerValuesLoaded(String[] strArr, String str);

        void onTimeGoalPickerValuesLoaded(String[] strArr, String str);

        void onWorkoutsPerWeekPickerValuesLoaded(String[] strArr, String str);
    }

    public interface OnGoalsLoadedListener {
        void onCaloriePerWorkoutGoalLoaded(TrainingGoal trainingGoal);

        void onTimePerWorkoutGoalLoaded(TrainingGoal trainingGoal);

        void onWorkoutsPerWeekGoalLoaded(TrainingGoal trainingGoal);
    }

    void loadCurrentGoals(OnGoalsLoadedListener onGoalsLoadedListener);

    void loadGoalPickerValues(OnGoalPickerValuesListener onGoalPickerValuesListener);
}
