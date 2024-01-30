package com.ua.sdk;

import com.ua.sdk.heartrate.HeartRateZones;

public interface IntensityCalculator {
    double calculateCurrentIntensity(HeartRateZones heartRateZones, double d);
}
