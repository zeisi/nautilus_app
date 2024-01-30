package com.nautilus.omni.appmodules.journal.presenter.day.workoutlist;

import com.nautilus.omni.model.dto.Workout;
import java.util.List;

public interface DayInteractorContract {

    public interface OnNewSyncedDataLoaderListener {
        void onNewSyncedDataLoaded(List<Object> list);
    }

    public interface OnOlderWorkoutsListLoaderListener {
        void onOlderWorkoutsLoaded(List<Object> list);
    }

    public interface OnWorkoutSelectedLoaderListener {
        void onWorkoutSelectedLoaded(Workout workout);
    }

    public interface OnWorkoutsListLoaderListener {
        void onWorkoutsListLoaded(List<Object> list);
    }

    void loadNewSyncedData(OnNewSyncedDataLoaderListener onNewSyncedDataLoaderListener, boolean z);

    void loadOlderWorkoutsList(OnOlderWorkoutsListLoaderListener onOlderWorkoutsListLoaderListener);

    void loadSelectedWorkout(int i, OnWorkoutSelectedLoaderListener onWorkoutSelectedLoaderListener);

    void loadWorkoutsList(OnWorkoutsListLoaderListener onWorkoutsListLoaderListener);

    void sendStartSyncBroadcast();
}
