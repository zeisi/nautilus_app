package com.nautilus.omni.appmodules.journal.presenter.year;

import com.nautilus.omni.appmodules.journal.presenter.JournalMetrics;
import java.util.ArrayList;
import org.joda.time.DateTime;

public interface YearInteractorContract {

    public interface OnCalculateWorkoutsInfoByMetricListener {
        void onWorkoutInfoByMetricTypeCalculated(ArrayList<Double> arrayList, String str);
    }

    public interface OnCurrentYearDateUpdatedListener {
        void onCurrentYearDateUpdated(DateTime dateTime);
    }

    public interface OnLoadWorkoutsAnnualInfoListener {
        void onWorkoutsAnnualInfoLoaded(ArrayList<Double> arrayList, String str);
    }

    boolean areWorkoutsAvailableForNextYears();

    boolean areWorkoutsAvailableForPreviousYears();

    void getMostRecentWorkoutDate(OnCurrentYearDateUpdatedListener onCurrentYearDateUpdatedListener);

    String[] loadMetricsSpinnerInfo();

    void loadWorkoutsAnnualInfoSortedByDay(OnLoadWorkoutsAnnualInfoListener onLoadWorkoutsAnnualInfoListener, int i, JournalMetrics journalMetrics);

    void loadWorkoutsInfoByMetricType(JournalMetrics journalMetrics, OnCalculateWorkoutsInfoByMetricListener onCalculateWorkoutsInfoByMetricListener);

    ArrayList<String> loadYearMonthsList();
}
