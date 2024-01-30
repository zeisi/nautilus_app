package com.nautilus.omni.appmodules.journal.presenter.week;

import com.nautilus.omni.appmodules.journal.presenter.JournalMetrics;
import java.util.ArrayList;

public interface WeekPresenterContract {
    void checkIfAreWorkoutsAvailableForNextWeeks();

    void checkIfAreWorkoutsAvailableForPreviousWeeks();

    String[] loadMetricsSpinnerInfo();

    ArrayList<String> loadWeekDaysList();

    void loadWorkoutsAfterSyncFinished(int i, JournalMetrics journalMetrics);

    void loadWorkoutsInfoByMetricType(JournalMetrics journalMetrics);

    void loadWorkoutsWeeklyInfo(int i, JournalMetrics journalMetrics);
}
