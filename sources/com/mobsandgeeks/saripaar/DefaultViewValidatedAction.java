package com.mobsandgeeks.saripaar;

import android.view.View;
import android.widget.TextView;
import com.mobsandgeeks.saripaar.Validator;

public class DefaultViewValidatedAction implements Validator.ViewValidatedAction {
    public void onAllRulesPassed(View view) {
        if (view instanceof TextView) {
            ((TextView) view).setError((CharSequence) null);
        }
    }
}
