package com.mobsandgeeks.saripaar.rule;

import com.mobsandgeeks.saripaar.AnnotationRule;
import com.mobsandgeeks.saripaar.annotation.Length;

public class LengthRule extends AnnotationRule<Length, String> {
    protected LengthRule(Length length) {
        super(length);
    }

    public boolean isValid(String text) {
        if (text == null) {
            throw new IllegalArgumentException("'text' cannot be null.");
        }
        int ruleMin = ((Length) this.mRuleAnnotation).min();
        int ruleMax = ((Length) this.mRuleAnnotation).max();
        assertMinMax(ruleMin, ruleMax);
        int length = ((Length) this.mRuleAnnotation).trim() ? text.trim().length() : text.length();
        boolean minIsValid = true;
        if (ruleMin != Integer.MIN_VALUE) {
            if (length >= ruleMin) {
                minIsValid = true;
            } else {
                minIsValid = false;
            }
        }
        boolean maxIsValid = true;
        if (ruleMax != Integer.MAX_VALUE) {
            if (length <= ruleMax) {
                maxIsValid = true;
            } else {
                maxIsValid = false;
            }
        }
        if (!minIsValid || !maxIsValid) {
            return false;
        }
        return true;
    }

    private void assertMinMax(int min, int max) {
        if (min > max) {
            throw new IllegalStateException(String.format("'min' (%d) should be less than or equal to 'max' (%d).", new Object[]{Integer.valueOf(min), Integer.valueOf(max)}));
        }
    }
}
