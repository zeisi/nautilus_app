package com.mobsandgeeks.saripaar.annotation;

import com.mobsandgeeks.saripaar.rule.CreditCardRule;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@ValidateUsing(CreditCardRule.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface CreditCard {

    public enum Type {
        AMEX,
        DINERS,
        DISCOVER,
        MASTERCARD,
        VISA,
        NONE
    }

    Type[] cardTypes() default {Type.AMEX, Type.DINERS, Type.DISCOVER, Type.MASTERCARD, Type.VISA};

    String message() default "Invalid card";

    int messageResId() default -1;

    int sequence() default -1;
}
