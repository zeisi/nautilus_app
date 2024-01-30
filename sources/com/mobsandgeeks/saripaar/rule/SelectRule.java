package com.mobsandgeeks.saripaar.rule;

import com.mobsandgeeks.saripaar.AnnotationRule;
import com.mobsandgeeks.saripaar.annotation.Select;

public class SelectRule extends AnnotationRule<Select, Integer> {
    protected SelectRule(Select select) {
        super(select);
    }

    public boolean isValid(Integer index) {
        if (index != null) {
            return ((Select) this.mRuleAnnotation).defaultSelection() != index.intValue();
        }
        throw new IllegalArgumentException("'index' cannot be null.");
    }
}
