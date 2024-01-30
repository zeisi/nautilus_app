package com.mobsandgeeks.saripaar.rule;

import com.mobsandgeeks.saripaar.AnnotationRule;
import com.mobsandgeeks.saripaar.annotation.Url;
import commons.validator.routines.UrlValidator;

public class UrlRule extends AnnotationRule<Url, String> {
    protected UrlRule(Url url) {
        super(url);
    }

    public boolean isValid(String url) {
        UrlValidator urlValidator;
        String[] schemes = ((Url) this.mRuleAnnotation).schemes();
        long options = ((Url) this.mRuleAnnotation).allowFragments() ? 0 : 4;
        if (schemes == null || schemes.length <= 0) {
            urlValidator = UrlValidator.getInstance();
        } else {
            urlValidator = new UrlValidator(schemes, options);
        }
        return urlValidator.isValid(url);
    }
}
