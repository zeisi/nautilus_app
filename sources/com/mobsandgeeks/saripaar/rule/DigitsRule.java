package com.mobsandgeeks.saripaar.rule;

import com.mobsandgeeks.saripaar.AnnotationRule;
import com.mobsandgeeks.saripaar.annotation.Digits;

public class DigitsRule extends AnnotationRule<Digits, String> {
    protected DigitsRule(Digits digits) {
        super(digits);
    }

    public boolean isValid(String digits) {
        return digits.matches(String.format("(\\d{0,%d})(\\.\\d{1,%d})?", new Object[]{Integer.valueOf(((Digits) this.mRuleAnnotation).integer()), Integer.valueOf(((Digits) this.mRuleAnnotation).fraction())}));
    }
}
