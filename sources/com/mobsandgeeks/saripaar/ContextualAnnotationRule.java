package com.mobsandgeeks.saripaar;

import java.lang.annotation.Annotation;

public abstract class ContextualAnnotationRule<RULE_ANNOTATION extends Annotation, DATA_TYPE> extends AnnotationRule<RULE_ANNOTATION, DATA_TYPE> {
    protected ValidationContext mValidationContext;

    protected ContextualAnnotationRule(ValidationContext validationContext, RULE_ANNOTATION ruleAnnotation) {
        super(ruleAnnotation);
        if (validationContext == null) {
            throw new IllegalArgumentException("'validationContext' cannot be null.");
        }
        this.mValidationContext = validationContext;
    }
}
