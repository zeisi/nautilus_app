package com.mobsandgeeks.saripaar;

import android.view.View;

public abstract class QuickRule<VIEW extends View> extends Rule<VIEW> {
    public abstract boolean isValid(VIEW view);

    protected QuickRule() {
        super(-1);
    }

    protected QuickRule(int sequence) {
        super(sequence);
        if (sequence < 0) {
            throw new IllegalArgumentException("'sequence' should be a non-negative integer.");
        }
    }
}
