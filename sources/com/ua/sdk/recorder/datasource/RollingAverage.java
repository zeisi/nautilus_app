package com.ua.sdk.recorder.datasource;

import java.lang.Number;
import java.util.Iterator;
import java.util.LinkedList;

public class RollingAverage<T extends Number> {
    private double avg = 0.0d;
    private int maxSize = 0;
    private LinkedList<T> values = new LinkedList<>();

    public RollingAverage(int maxSize2) {
        this.maxSize = maxSize2;
    }

    public double getAverage() {
        return this.avg;
    }

    public void addValue(T value) {
        double v = value.doubleValue();
        if (this.maxSize > 0 && !Double.isInfinite(v) && !Double.isNaN(v)) {
            if (this.values.size() == this.maxSize) {
                this.values.removeFirst();
            }
            this.values.add(value);
            this.avg = calculateAvg();
        }
    }

    public void reset() {
        this.values = new LinkedList<>();
        this.avg = 0.0d;
    }

    private double calculateAvg() {
        double sum = 0.0d;
        Iterator i$ = this.values.iterator();
        while (i$.hasNext()) {
            sum += ((Number) i$.next()).doubleValue();
        }
        return sum / ((double) this.values.size());
    }
}
