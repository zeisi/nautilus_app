package com.nautilus.omni.appmodules.goals.presenter.interactors;

import com.nautilus.omni.model.dto.TrainingGoal;

public interface SaveGoalsInteractorContract {

    public interface OnGoalsSavedListener {
        void onGoalsSaved(TrainingGoal trainingGoal, TrainingGoal trainingGoal2, TrainingGoal trainingGoal3);
    }

    void disableGoals();

    void saveGoals(OnGoalsSavedListener onGoalsSavedListener, String str, String str2, String str3);
}
