package com.nautilus.omni.appmodules.journal.presenter.day.workoutlist;

public interface DayPresenterContract {
    void getSelectedWorkout(int i);

    void loadNewSyncedData(boolean z);

    void loadOlderData(int i, int i2, int i3);

    void loadWorkoutsList();

    void sendBroadcastToSyncNewData();
}
