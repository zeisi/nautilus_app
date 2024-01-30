package com.mobsandgeeks.saripaar.rule;

import android.content.Context;
import com.mobsandgeeks.saripaar.ContextualAnnotationRule;
import com.mobsandgeeks.saripaar.ValidationContext;
import com.mobsandgeeks.saripaar.annotation.Past;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PastRule extends ContextualAnnotationRule<Past, String> {
    protected PastRule(ValidationContext validationContext, Past past) {
        super(validationContext, past);
    }

    public boolean isValid(String dateString) {
        Date parsedDate = null;
        try {
            parsedDate = getDateFormat().parse(dateString);
        } catch (ParseException e) {
        }
        return parsedDate != null && parsedDate.before(new Date());
    }

    private DateFormat getDateFormat() {
        Context context = this.mValidationContext.getContext();
        int dateFormatResId = ((Past) this.mRuleAnnotation).dateFormatResId();
        return new SimpleDateFormat(dateFormatResId != -1 ? context.getString(dateFormatResId) : ((Past) this.mRuleAnnotation).dateFormat());
    }
}
