package com.nautilus.omni.appmodules.journal.view.year;

import java.util.ArrayList;

public interface YearViewContract {
    void disableButtonYearAfter();

    void disableButtonYearBefore();

    void enableButtonYearAfter();

    void enableButtonYearBefore();

    void showGraphicInfoAccordingWithMetricSelected(ArrayList<Double> arrayList);

    void updateYearTextView(String str);
}
