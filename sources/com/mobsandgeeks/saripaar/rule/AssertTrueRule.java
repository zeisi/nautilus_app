package com.mobsandgeeks.saripaar.rule;

import com.mobsandgeeks.saripaar.AnnotationRule;
import com.mobsandgeeks.saripaar.annotation.AssertTrue;

public class AssertTrueRule extends AnnotationRule<AssertTrue, Boolean> {
    protected AssertTrueRule(AssertTrue assertTrue) {
        super(assertTrue);
    }

    public boolean isValid(Boolean value) {
        if (value != null) {
            return value.booleanValue();
        }
        throw new IllegalArgumentException("'data' cannot be null.");
    }
}
