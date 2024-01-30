package com.nautilus.omni.appmodules.journal.view.week;

import java.util.ArrayList;

public interface WeekViewContract {
    void disableButtonWeekAfter();

    void disableButtonWeekBefore();

    void enableButtonWeekAfter();

    void enableButtonWeekBefore();

    void showGraphicInfoAccordingWithMetricSelected(ArrayList<Double> arrayList);

    void updateWeekDateTextView(String str);
}
