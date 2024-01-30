package com.mobsandgeeks.saripaar.rule;

import com.mobsandgeeks.saripaar.AnnotationRule;
import com.mobsandgeeks.saripaar.annotation.Email;
import commons.validator.routines.EmailValidator;

public class EmailRule extends AnnotationRule<Email, String> {
    protected EmailRule(Email email) {
        super(email);
    }

    public boolean isValid(String email) {
        return EmailValidator.getInstance(((Email) this.mRuleAnnotation).allowLocal()).isValid(email);
    }
}
