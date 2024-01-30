package com.ua.sdk.activitytimeseries;

import com.ua.sdk.util.DoubleList;
import com.ua.sdk.util.IntList;
import com.ua.sdk.util.LongList;
import java.util.UUID;

public class ActivityTimeSeriesBuilderImpl implements ActivityTimeSeriesBuilder {
    public static final int MAX_EVENT_COUNT = 10800;
    public static final int MAX_INTERVAL_SECONDS = 604800;
    LongList calorieEpochs = null;
    DoubleList calorieValues = null;
    LongList distanceEpochs = null;
    DoubleList distanceValues = null;
    String recorderIdentifier = null;
    String recorderTypeKey = null;
    LongList stepEpochs = null;
    IntList stepValues = null;

    public ActivityTimeSeriesBuilderImpl setRecorderTypeKey(String recorderTypeKey2) {
        this.recorderTypeKey = recorderTypeKey2;
        return this;
    }

    public ActivityTimeSeriesBuilderImpl setRecorderIdentifier(String recorderIdentifier2) {
        this.recorderIdentifier = recorderIdentifier2;
        return this;
    }

    public ActivityTimeSeriesBuilderImpl addSteps(long epochSeconds, int steps) {
        if (this.stepEpochs == null) {
            this.stepEpochs = new LongList();
            this.stepValues = new IntList();
        }
        this.stepEpochs.add(epochSeconds);
        this.stepValues.add(steps);
        return this;
    }

    public ActivityTimeSeriesBuilderImpl addCalories(long epochSeconds, double joules) {
        if (this.calorieEpochs == null) {
            this.calorieEpochs = new LongList();
            this.calorieValues = new DoubleList();
        }
        this.calorieEpochs.add(epochSeconds);
        this.calorieValues.add(joules);
        return this;
    }

    public ActivityTimeSeriesBuilderImpl addDistance(long epochSeconds, double meters) {
        if (this.distanceEpochs == null) {
            this.distanceEpochs = new LongList();
            this.distanceValues = new DoubleList();
        }
        this.distanceEpochs.add(epochSeconds);
        this.distanceValues.add(meters);
        return this;
    }

    public ActivityTimeSeriesImpl build() {
        if (this.recorderTypeKey == null) {
            throw new IllegalArgumentException("recorderTypeKey must be set.");
        }
        if (this.recorderIdentifier == null) {
            this.recorderIdentifier = UUID.randomUUID().toString();
        }
        return new ActivityTimeSeriesImpl(this);
    }
}
