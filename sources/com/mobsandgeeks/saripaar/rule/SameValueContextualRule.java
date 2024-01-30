package com.mobsandgeeks.saripaar.rule;

import android.view.View;
import com.mobsandgeeks.saripaar.ContextualAnnotationRule;
import com.mobsandgeeks.saripaar.ValidationContext;
import java.lang.annotation.Annotation;
import java.util.List;

class SameValueContextualRule<CONFIRM extends Annotation, SOURCE extends Annotation, DATA_TYPE> extends ContextualAnnotationRule<CONFIRM, DATA_TYPE> {
    private Class<CONFIRM> mConfirmClass;
    private Class<SOURCE> mSourceClass;

    protected SameValueContextualRule(ValidationContext validationContext, CONFIRM confirmAnnotation, Class<SOURCE> sourceClass) {
        super(validationContext, confirmAnnotation);
        this.mSourceClass = sourceClass;
        this.mConfirmClass = confirmAnnotation.annotationType();
    }

    public boolean isValid(DATA_TYPE confirmValue) {
        List<View> sourceViews = this.mValidationContext.getAnnotatedViews(this.mSourceClass);
        int nSourceViews = sourceViews.size();
        if (nSourceViews == 0) {
            throw new IllegalStateException(String.format("You should have a view annotated with '%s' to use '%s'.", new Object[]{this.mSourceClass.getName(), this.mConfirmClass.getName()}));
        } else if (nSourceViews > 1) {
            throw new IllegalStateException(String.format("More than 1 field annotated with '%s'.", new Object[]{this.mSourceClass.getName()}));
        } else {
            return confirmValue.equals(this.mValidationContext.getData(sourceViews.get(0), this.mSourceClass));
        }
    }
}
