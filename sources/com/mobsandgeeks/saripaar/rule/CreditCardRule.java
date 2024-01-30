package com.mobsandgeeks.saripaar.rule;

import com.mobsandgeeks.saripaar.AnnotationRule;
import com.mobsandgeeks.saripaar.annotation.CreditCard;
import com.nautilus.omni.util.Constants;
import commons.validator.routines.CreditCardValidator;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

public class CreditCardRule extends AnnotationRule<CreditCard, String> {
    private static final Map<CreditCard.Type, Long> CARD_TYPE_REGISTRY = new HashMap<CreditCard.Type, Long>() {
        {
            put(CreditCard.Type.AMEX, 1L);
            put(CreditCard.Type.DINERS, 16L);
            put(CreditCard.Type.DISCOVER, 8L);
            put(CreditCard.Type.MASTERCARD, 4L);
            put(CreditCard.Type.VISA, 2L);
        }
    };

    protected CreditCardRule(CreditCard creditCard) {
        super(creditCard);
    }

    public boolean isValid(String creditCardNumber) {
        HashSet<CreditCard.Type> typesSet = new HashSet<>(Arrays.asList(((CreditCard) this.mRuleAnnotation).cardTypes()));
        long options = 0;
        if (!typesSet.contains(CreditCard.Type.NONE)) {
            Iterator<CreditCard.Type> it = typesSet.iterator();
            while (it.hasNext()) {
                options += CARD_TYPE_REGISTRY.get(it.next()).longValue();
            }
        } else {
            options = 0;
        }
        return new CreditCardValidator(options).isValid(creditCardNumber.replaceAll(Constants.EMPTY_SPACE_SEPARATOR, ""));
    }
}
