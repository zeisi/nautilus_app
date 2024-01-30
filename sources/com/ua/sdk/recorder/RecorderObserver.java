package com.ua.sdk.recorder;

import com.ua.sdk.datapoint.DataFrame;

public interface RecorderObserver {
    void onSegmentStateUpdated(DataFrame dataFrame);

    void onTimeUpdated(double d, double d2);
}
