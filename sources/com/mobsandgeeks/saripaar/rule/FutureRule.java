package com.mobsandgeeks.saripaar.rule;

import android.content.Context;
import com.mobsandgeeks.saripaar.ContextualAnnotationRule;
import com.mobsandgeeks.saripaar.ValidationContext;
import com.mobsandgeeks.saripaar.annotation.Future;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FutureRule extends ContextualAnnotationRule<Future, String> {
    protected FutureRule(ValidationContext validationContext, Future future) {
        super(validationContext, future);
    }

    public boolean isValid(String dateString) {
        Date parsedDate = null;
        try {
            parsedDate = getDateFormat().parse(dateString);
        } catch (ParseException e) {
        }
        return parsedDate != null && parsedDate.after(new Date());
    }

    private DateFormat getDateFormat() {
        Context context = this.mValidationContext.getContext();
        int dateFormatResId = ((Future) this.mRuleAnnotation).dateFormatResId();
        return new SimpleDateFormat(dateFormatResId != -1 ? context.getString(dateFormatResId) : ((Future) this.mRuleAnnotation).dateFormat());
    }
}
