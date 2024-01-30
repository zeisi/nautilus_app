package com.mobsandgeeks.saripaar.rule;

import com.mobsandgeeks.saripaar.AnnotationRule;
import com.mobsandgeeks.saripaar.annotation.AssertFalse;

public class AssertFalseRule extends AnnotationRule<AssertFalse, Boolean> {
    protected AssertFalseRule(AssertFalse assertFalse) {
        super(assertFalse);
    }

    public boolean isValid(Boolean value) {
        if (value != null) {
            return !value.booleanValue();
        }
        throw new IllegalArgumentException("'data' cannot be null.");
    }
}
