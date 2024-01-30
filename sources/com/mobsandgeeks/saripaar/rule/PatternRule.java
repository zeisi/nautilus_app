package com.mobsandgeeks.saripaar.rule;

import com.mobsandgeeks.saripaar.AnnotationRule;
import com.mobsandgeeks.saripaar.annotation.Pattern;
import commons.validator.routines.RegexValidator;

public class PatternRule extends AnnotationRule<Pattern, String> {
    protected PatternRule(Pattern pattern) {
        super(pattern);
    }

    public boolean isValid(String text) {
        return new RegexValidator(((Pattern) this.mRuleAnnotation).regex(), ((Pattern) this.mRuleAnnotation).caseSensitive()).isValid(text);
    }
}
