package com.mobsandgeeks.saripaar;

import android.content.Context;
import java.lang.annotation.Annotation;

public abstract class AnnotationRule<RULE_ANNOTATION extends Annotation, DATA_TYPE> extends Rule<DATA_TYPE> {
    protected final RULE_ANNOTATION mRuleAnnotation;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    protected AnnotationRule(RULE_ANNOTATION ruleAnnotation) {
        super(ruleAnnotation != null ? ((Integer) Reflector.getAttributeValue(ruleAnnotation, "sequence", Integer.TYPE)).intValue() : -1);
        if (ruleAnnotation == null) {
            throw new IllegalArgumentException("'ruleAnnotation' cannot be null.");
        }
        this.mRuleAnnotation = ruleAnnotation;
    }

    public String getMessage(Context context) {
        int messageResId = ((Integer) Reflector.getAttributeValue(this.mRuleAnnotation, "messageResId", Integer.class)).intValue();
        if (messageResId != -1) {
            return context.getString(messageResId);
        }
        return (String) Reflector.getAttributeValue(this.mRuleAnnotation, "message", String.class);
    }
}
