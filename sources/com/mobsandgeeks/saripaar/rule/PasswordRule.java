package com.mobsandgeeks.saripaar.rule;

import com.mobsandgeeks.saripaar.AnnotationRule;
import com.mobsandgeeks.saripaar.annotation.Password;
import java.util.HashMap;
import java.util.Map;

public class PasswordRule extends AnnotationRule<Password, String> {
    private final Map<Password.Scheme, String> SCHEME_PATTERNS = new HashMap<Password.Scheme, String>() {
        {
            put(Password.Scheme.ANY, ".+");
            put(Password.Scheme.ALPHA, "\\w+");
            put(Password.Scheme.ALPHA_MIXED_CASE, "(?=.*[a-z])(?=.*[A-Z]).+");
            put(Password.Scheme.NUMERIC, "\\d+");
            put(Password.Scheme.ALPHA_NUMERIC, "(?=.*[a-zA-Z])(?=.*[\\d]).+");
            put(Password.Scheme.ALPHA_NUMERIC_MIXED_CASE, "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d]).+");
            put(Password.Scheme.ALPHA_NUMERIC_SYMBOLS, "(?=.*[a-zA-Z])(?=.*[\\d])(?=.*([^\\w])).+");
            put(Password.Scheme.ALPHA_NUMERIC_MIXED_CASE_SYMBOLS, "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*([^\\w])).+");
        }
    };

    protected PasswordRule(Password password) {
        super(password);
    }

    public boolean isValid(String password) {
        boolean hasMinChars;
        if (password.length() >= ((Password) this.mRuleAnnotation).min()) {
            hasMinChars = true;
        } else {
            hasMinChars = false;
        }
        return hasMinChars && password.matches(this.SCHEME_PATTERNS.get(((Password) this.mRuleAnnotation).scheme()));
    }
}
