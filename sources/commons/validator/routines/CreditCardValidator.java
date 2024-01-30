package commons.validator.routines;

import commons.validator.routines.checkdigit.CheckDigit;
import commons.validator.routines.checkdigit.LuhnCheckDigit;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CreditCardValidator implements Serializable {
    public static final long AMEX = 1;
    public static final CodeValidator AMEX_VALIDATOR = new CodeValidator("^(3[47]\\d{13})$", LUHN_VALIDATOR);
    public static final long DINERS = 16;
    public static final CodeValidator DINERS_VALIDATOR = new CodeValidator("^(30[0-5]\\d{11}|3095\\d{10}|36\\d{12}|3[8-9]\\d{12})$", LUHN_VALIDATOR);
    public static final long DISCOVER = 8;
    private static final RegexValidator DISCOVER_REGEX = new RegexValidator(new String[]{"^(6011\\d{12})$", "^(64[4-9]\\d{13})$", "^(65\\d{14})$"});
    public static final CodeValidator DISCOVER_VALIDATOR = new CodeValidator(DISCOVER_REGEX, LUHN_VALIDATOR);
    private static final CheckDigit LUHN_VALIDATOR = LuhnCheckDigit.LUHN_CHECK_DIGIT;
    public static final long MASTERCARD = 4;
    public static final CodeValidator MASTERCARD_VALIDATOR = new CodeValidator("^(5[1-5]\\d{14})$", LUHN_VALIDATOR);
    public static final long NONE = 0;
    public static final long VISA = 2;
    public static final CodeValidator VISA_VALIDATOR = new CodeValidator("^(4)(\\d{12}|\\d{15})$", LUHN_VALIDATOR);
    private static final long serialVersionUID = 5955978921148959496L;
    private final List cardTypes;

    public CreditCardValidator() {
        this(15);
    }

    public CreditCardValidator(long options) {
        this.cardTypes = new ArrayList();
        if (isOn(options, 2)) {
            this.cardTypes.add(VISA_VALIDATOR);
        }
        if (isOn(options, 1)) {
            this.cardTypes.add(AMEX_VALIDATOR);
        }
        if (isOn(options, 4)) {
            this.cardTypes.add(MASTERCARD_VALIDATOR);
        }
        if (isOn(options, 8)) {
            this.cardTypes.add(DISCOVER_VALIDATOR);
        }
        if (isOn(options, 16)) {
            this.cardTypes.add(DINERS_VALIDATOR);
        }
    }

    public CreditCardValidator(CodeValidator[] creditCardValidators) {
        this.cardTypes = new ArrayList();
        if (creditCardValidators == null) {
            throw new IllegalArgumentException("Card validators are missing");
        }
        for (CodeValidator add : creditCardValidators) {
            this.cardTypes.add(add);
        }
    }

    public boolean isValid(String card) {
        if (card == null || card.length() == 0) {
            return false;
        }
        for (int i = 0; i < this.cardTypes.size(); i++) {
            if (((CodeValidator) this.cardTypes.get(i)).isValid(card)) {
                return true;
            }
        }
        return false;
    }

    public Object validate(String card) {
        if (card == null || card.length() == 0) {
            return null;
        }
        for (int i = 0; i < this.cardTypes.size(); i++) {
            Object result = ((CodeValidator) this.cardTypes.get(i)).validate(card);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    private boolean isOn(long options, long flag) {
        return (options & flag) > 0;
    }
}
