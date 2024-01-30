package com.nautilus.omni.appmodules.journal.view.day;

import com.nautilus.omni.model.dto.Workout;
import java.util.List;

public interface DayViewContract {
    void openWorkoutDetailScreen(Workout workout);

    void refreshListWithNewSyncedData(List<Object> list);

    void refreshListWithOlderData(List<Object> list);

    void showWorkoutsList(List<Object> list);
}
