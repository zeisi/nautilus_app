package com.nautilus.omni.appmodules.home.domain.interactors;

import com.nautilus.omni.model.dto.TrainingGoal;
import com.nautilus.omni.model.dto.User;
import com.nautilus.omni.model.dto.Workout;

public interface GaugeInteractorContract {

    public interface OnTextDataListener {
        void loadData(int i, Workout workout, Workout workout2, User user);

        void loadGoalsConfigDataSuccess(int i, TrainingGoal trainingGoal, TrainingGoal trainingGoal2, TrainingGoal trainingGoal3);

        void showDataComparedToCurrentGoals(int i, Workout workout, User user, TrainingGoal trainingGoal, TrainingGoal trainingGoal2);
    }

    void loadDataFromGoals(int i, Workout workout, User user, OnTextDataListener onTextDataListener);

    void loadGoalsConfigData(int i, User user, OnTextDataListener onTextDataListener);

    void loadTextData(int i, boolean z, int i2, OnTextDataListener onTextDataListener);
}
