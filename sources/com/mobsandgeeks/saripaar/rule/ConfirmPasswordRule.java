package com.mobsandgeeks.saripaar.rule;

import com.mobsandgeeks.saripaar.ValidationContext;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Password;

public class ConfirmPasswordRule extends SameValueContextualRule<ConfirmPassword, Password, String> {
    protected ConfirmPasswordRule(ValidationContext validationContext, ConfirmPassword confirmPassword) {
        super(validationContext, confirmPassword, Password.class);
    }

    public boolean isValid(String confirmValue) {
        return super.isValid(confirmValue);
    }
}
