package com.nautilus.omni.appmodules.journal.presenter.week;

import com.nautilus.omni.appmodules.journal.presenter.JournalMetrics;
import java.util.ArrayList;
import org.joda.time.DateTime;

public interface WeekInteractorContract {

    public interface OnCalculateWorkoutsInfoByMetricListener {
        void onWorkoutInfoByMetricTypeCalculated(ArrayList<Double> arrayList, String str);
    }

    public interface OnCurrentWeekDateUpdatedListener {
        void onCurrentWeekDateUpdated(DateTime dateTime);
    }

    public interface OnLoadWorkoutsWeeklyInfoListener {
        void onWorkoutsWeeklyInfoLoaded(ArrayList<Double> arrayList, String str);
    }

    boolean areWorkoutsAvailableForNextWeeks();

    boolean areWorkoutsAvailableForPreviousWeeks();

    void getMostRecentWorkoutDate(OnCurrentWeekDateUpdatedListener onCurrentWeekDateUpdatedListener);

    String[] loadMetricsSpinnerInfo();

    ArrayList<String> loadWeekDaysList();

    void loadWorkoutsInfoByMetricType(JournalMetrics journalMetrics, OnCalculateWorkoutsInfoByMetricListener onCalculateWorkoutsInfoByMetricListener);

    void loadWorkoutsWeeklyInfoSortedByDay(OnLoadWorkoutsWeeklyInfoListener onLoadWorkoutsWeeklyInfoListener, int i, JournalMetrics journalMetrics);
}
