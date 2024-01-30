package com.mobsandgeeks.saripaar.rule;

import com.mobsandgeeks.saripaar.AnnotationRule;
import com.mobsandgeeks.saripaar.annotation.Min;
import commons.validator.routines.IntegerValidator;

public class MinRule extends AnnotationRule<Min, Integer> {
    protected MinRule(Min min) {
        super(min);
    }

    public boolean isValid(Integer value) {
        if (value == null) {
            throw new IllegalArgumentException("'Integer' cannot be null.");
        }
        return IntegerValidator.getInstance().minValue(value, ((Min) this.mRuleAnnotation).value());
    }
}
