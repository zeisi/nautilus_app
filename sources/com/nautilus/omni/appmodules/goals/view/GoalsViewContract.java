package com.nautilus.omni.appmodules.goals.view;

import com.nautilus.omni.model.dto.TrainingGoal;

public interface GoalsViewContract {
    void loadCaloriePicker(String[] strArr, String str);

    void loadCurrentCalorieGoal(TrainingGoal trainingGoal);

    void loadCurrentNumberWorkoutsGoal(TrainingGoal trainingGoal);

    void loadCurrentTimeGoal(TrainingGoal trainingGoal);

    void loadNumberWorkoutsPicker(String[] strArr, String str);

    void loadTimeGoalPicker(String[] strArr, String str);

    void showGoalsSavedToast(TrainingGoal trainingGoal, TrainingGoal trainingGoal2, TrainingGoal trainingGoal3);
}
