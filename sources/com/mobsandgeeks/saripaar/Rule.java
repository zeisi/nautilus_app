package com.mobsandgeeks.saripaar;

import android.content.Context;

public abstract class Rule<VALIDATABLE> {
    protected final int mSequence;

    public abstract String getMessage(Context context);

    public abstract boolean isValid(VALIDATABLE validatable);

    protected Rule(int sequence) {
        this.mSequence = sequence;
    }

    public final int getSequence() {
        return this.mSequence;
    }
}
