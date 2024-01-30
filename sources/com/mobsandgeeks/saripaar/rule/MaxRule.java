package com.mobsandgeeks.saripaar.rule;

import com.mobsandgeeks.saripaar.AnnotationRule;
import com.mobsandgeeks.saripaar.annotation.Max;
import commons.validator.routines.IntegerValidator;

public class MaxRule extends AnnotationRule<Max, Integer> {
    protected MaxRule(Max max) {
        super(max);
    }

    public boolean isValid(Integer value) {
        if (value == null) {
            throw new IllegalArgumentException("'Integer' cannot be null.");
        }
        return IntegerValidator.getInstance().maxValue(value, ((Max) this.mRuleAnnotation).value());
    }
}
