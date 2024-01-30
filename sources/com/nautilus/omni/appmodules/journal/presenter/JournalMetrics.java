package com.nautilus.omni.appmodules.journal.presenter;

public enum JournalMetrics {
    ELAPSED_TIME(0),
    TOTAL_CALORIES(1),
    AVG_HEART_RATE(2),
    DISTANCE(3),
    POWER(4),
    SPEED(5);
    
    private final int code;

    private JournalMetrics(int code2) {
        this.code = code2;
    }

    public int toInt() {
        return this.code;
    }
}
