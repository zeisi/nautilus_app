package com.mobsandgeeks.saripaar.rule;

import com.mobsandgeeks.saripaar.AnnotationRule;
import com.mobsandgeeks.saripaar.annotation.DecimalMax;
import commons.validator.routines.DoubleValidator;

public class DecimalMaxRule extends AnnotationRule<DecimalMax, Double> {
    protected DecimalMaxRule(DecimalMax decimalMax) {
        super(decimalMax);
    }

    public boolean isValid(Double value) {
        if (value == null) {
            throw new IllegalArgumentException("'Double' cannot be null.");
        }
        return DoubleValidator.getInstance().maxValue(value, ((DecimalMax) this.mRuleAnnotation).value());
    }
}
