package com.mobsandgeeks.saripaar;

import android.content.Context;
import android.view.View;
import java.util.List;

public class ValidationError {
    private final List<Rule> failedRules;
    private final View view;

    ValidationError(View view2, List<Rule> failedRules2) {
        this.view = view2;
        this.failedRules = failedRules2;
    }

    public View getView() {
        return this.view;
    }

    public List<Rule> getFailedRules() {
        return this.failedRules;
    }

    public String getCollatedErrorMessage(Context context) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Rule failedRule : this.failedRules) {
            String message = failedRule.getMessage(context).trim();
            if (message.length() > 0) {
                stringBuilder.append(message).append(10);
            }
        }
        return stringBuilder.toString().trim();
    }

    public String toString() {
        return "ValidationError{view=" + this.view + ", failedRules=" + this.failedRules + '}';
    }
}
