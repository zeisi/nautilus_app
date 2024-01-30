package commons.validator.routines;

import java.text.Format;
import java.util.Locale;

public class IntegerValidator extends AbstractNumberValidator {
    private static final IntegerValidator VALIDATOR = new IntegerValidator();
    private static final long serialVersionUID = 422081746310306596L;

    public static IntegerValidator getInstance() {
        return VALIDATOR;
    }

    public IntegerValidator() {
        this(true, 0);
    }

    public IntegerValidator(boolean strict, int formatType) {
        super(strict, formatType, false);
    }

    public Integer validate(String value) {
        return (Integer) parse(value, (String) null, (Locale) null);
    }

    public Integer validate(String value, String pattern) {
        return (Integer) parse(value, pattern, (Locale) null);
    }

    public Integer validate(String value, Locale locale) {
        return (Integer) parse(value, (String) null, locale);
    }

    public Integer validate(String value, String pattern, Locale locale) {
        return (Integer) parse(value, pattern, locale);
    }

    public boolean isInRange(int value, int min, int max) {
        return value >= min && value <= max;
    }

    public boolean isInRange(Integer value, int min, int max) {
        return isInRange(value.intValue(), min, max);
    }

    public boolean minValue(int value, int min) {
        return value >= min;
    }

    public boolean minValue(Integer value, int min) {
        return minValue(value.intValue(), min);
    }

    public boolean maxValue(int value, int max) {
        return value <= max;
    }

    public boolean maxValue(Integer value, int max) {
        return maxValue(value.intValue(), max);
    }

    /* access modifiers changed from: protected */
    public Object processParsedValue(Object value, Format formatter) {
        long longValue = ((Number) value).longValue();
        if (longValue < -2147483648L || longValue > 2147483647L) {
            return null;
        }
        return new Integer((int) longValue);
    }
}
