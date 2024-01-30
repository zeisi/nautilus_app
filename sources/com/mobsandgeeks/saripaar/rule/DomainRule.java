package com.mobsandgeeks.saripaar.rule;

import com.mobsandgeeks.saripaar.AnnotationRule;
import com.mobsandgeeks.saripaar.annotation.Domain;
import commons.validator.routines.DomainValidator;

public class DomainRule extends AnnotationRule<Domain, String> {
    protected DomainRule(Domain domain) {
        super(domain);
    }

    public boolean isValid(String domain) {
        return DomainValidator.getInstance(((Domain) this.mRuleAnnotation).allowLocal()).isValid(domain);
    }
}
