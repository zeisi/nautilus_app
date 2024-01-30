package com.mobsandgeeks.saripaar.rule;

import com.mobsandgeeks.saripaar.AnnotationRule;
import com.mobsandgeeks.saripaar.annotation.DecimalMin;
import commons.validator.routines.DoubleValidator;

public class DecimalMinRule extends AnnotationRule<DecimalMin, Double> {
    protected DecimalMinRule(DecimalMin decimalMin) {
        super(decimalMin);
    }

    public boolean isValid(Double value) {
        if (value == null) {
            throw new IllegalArgumentException("'Double' cannot be null.");
        }
        return DoubleValidator.getInstance().minValue(value, ((DecimalMin) this.mRuleAnnotation).value());
    }
}
