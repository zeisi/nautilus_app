package com.nautilus.omni.appmodules.journal.presenter.year;

import com.nautilus.omni.appmodules.journal.presenter.JournalMetrics;
import java.util.ArrayList;

public interface YearPresenterContract {
    void checkIfAreWorkoutsAvailableForNextYears();

    void checkIfAreWorkoutsAvailableForPreviousYears();

    String[] loadMetricsSpinnerInfo();

    void loadWorkoutsAfterSyncFinished(int i, JournalMetrics journalMetrics);

    void loadWorkoutsAnnualInfo(int i, JournalMetrics journalMetrics);

    void loadWorkoutsInfoByMetricType(JournalMetrics journalMetrics);

    ArrayList<String> loadYearMonthsList();
}
