package com.nautilus.omni.appmodules.home.domain.interactors;

import com.nautilus.omni.model.dto.TrainingGoal;
import com.nautilus.omni.model.dto.Week;
import com.nautilus.omni.model.dto.Workout;
import java.util.List;

public interface ThisWeekSectionInteractorContract {

    public interface OnLoadData {
        void onLoadAvgRateDataSuccess(List<Workout> list, List<Workout> list2);

        void onLoadAvgWattsDataSuccess(List<Workout> list, List<Workout> list2);

        void onLoadDataSuccess(int i, TrainingGoal trainingGoal, TrainingGoal trainingGoal2, TrainingGoal trainingGoal3, Week week, Week week2);
    }

    void loadAvgRateData(OnLoadData onLoadData);

    void loadAvgWattsData(OnLoadData onLoadData);

    void loadData(int i, int i2, OnLoadData onLoadData);
}
