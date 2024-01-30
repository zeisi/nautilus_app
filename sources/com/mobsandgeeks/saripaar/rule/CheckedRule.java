package com.mobsandgeeks.saripaar.rule;

import com.mobsandgeeks.saripaar.AnnotationRule;
import com.mobsandgeeks.saripaar.annotation.Checked;

public class CheckedRule extends AnnotationRule<Checked, Boolean> {
    protected CheckedRule(Checked checked) {
        super(checked);
    }

    public boolean isValid(Boolean value) {
        if (value != null) {
            return ((Checked) this.mRuleAnnotation).value() == value.booleanValue();
        }
        throw new IllegalArgumentException("'data' cannot be null.");
    }
}
