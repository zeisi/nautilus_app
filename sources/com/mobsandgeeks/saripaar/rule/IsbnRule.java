package com.mobsandgeeks.saripaar.rule;

import com.mobsandgeeks.saripaar.AnnotationRule;
import com.mobsandgeeks.saripaar.annotation.Isbn;
import commons.validator.routines.ISBNValidator;

public class IsbnRule extends AnnotationRule<Isbn, String> {
    protected IsbnRule(Isbn isbn) {
        super(isbn);
    }

    public boolean isValid(String isbn) {
        return ISBNValidator.getInstance().isValid(isbn);
    }
}
