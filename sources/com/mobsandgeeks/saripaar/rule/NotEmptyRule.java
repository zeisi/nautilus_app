package com.mobsandgeeks.saripaar.rule;

import android.content.Context;
import com.mobsandgeeks.saripaar.ContextualAnnotationRule;
import com.mobsandgeeks.saripaar.ValidationContext;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

public class NotEmptyRule extends ContextualAnnotationRule<NotEmpty, String> {
    protected NotEmptyRule(ValidationContext validationContext, NotEmpty notEmpty) {
        super(validationContext, notEmpty);
    }

    public boolean isValid(String data) {
        String emptyText;
        boolean isEmpty = false;
        if (data != null) {
            String text = ((NotEmpty) this.mRuleAnnotation).trim() ? data.trim() : data;
            Context context = this.mValidationContext.getContext();
            if (((NotEmpty) this.mRuleAnnotation).emptyTextResId() != -1) {
                emptyText = context.getString(((NotEmpty) this.mRuleAnnotation).emptyTextResId());
            } else {
                emptyText = ((NotEmpty) this.mRuleAnnotation).emptyText();
            }
            if (emptyText.equals(text) || "".equals(text)) {
                isEmpty = true;
            } else {
                isEmpty = false;
            }
        }
        if (!isEmpty) {
            return true;
        }
        return false;
    }
}
