package com.mobsandgeeks.saripaar.rule;

import com.mobsandgeeks.saripaar.ValidationContext;
import com.mobsandgeeks.saripaar.annotation.ConfirmEmail;
import com.mobsandgeeks.saripaar.annotation.Email;

public class ConfirmEmailRule extends SameValueContextualRule<ConfirmEmail, Email, String> {
    protected ConfirmEmailRule(ValidationContext validationContext, ConfirmEmail confirmEmail) {
        super(validationContext, confirmEmail, Email.class);
    }

    public boolean isValid(String confirmValue) {
        return super.isValid(confirmValue);
    }
}
