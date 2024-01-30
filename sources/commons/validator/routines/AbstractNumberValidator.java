package commons.validator.routines;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.Format;
import java.text.NumberFormat;
import java.util.Locale;

public abstract class AbstractNumberValidator extends AbstractFormatValidator {
    public static final int CURRENCY_FORMAT = 1;
    public static final int PERCENT_FORMAT = 2;
    public static final int STANDARD_FORMAT = 0;
    private static final long serialVersionUID = -3088817875906765463L;
    private final boolean allowFractions;
    private final int formatType;

    /* access modifiers changed from: protected */
    public abstract Object processParsedValue(Object obj, Format format);

    public AbstractNumberValidator(boolean strict, int formatType2, boolean allowFractions2) {
        super(strict);
        this.allowFractions = allowFractions2;
        this.formatType = formatType2;
    }

    public boolean isAllowFractions() {
        return this.allowFractions;
    }

    public int getFormatType() {
        return this.formatType;
    }

    public boolean isValid(String value, String pattern, Locale locale) {
        return parse(value, pattern, locale) != null;
    }

    public boolean isInRange(Number value, Number min, Number max) {
        return minValue(value, min) && maxValue(value, max);
    }

    public boolean minValue(Number value, Number min) {
        if (isAllowFractions()) {
            if (value.doubleValue() >= min.doubleValue()) {
                return true;
            }
            return false;
        } else if (value.longValue() < min.longValue()) {
            return false;
        } else {
            return true;
        }
    }

    public boolean maxValue(Number value, Number max) {
        if (isAllowFractions()) {
            if (value.doubleValue() <= max.doubleValue()) {
                return true;
            }
            return false;
        } else if (value.longValue() > max.longValue()) {
            return false;
        } else {
            return true;
        }
    }

    /* access modifiers changed from: protected */
    public Object parse(String value, String pattern, Locale locale) {
        String value2 = value == null ? null : value.trim();
        if (value2 == null || value2.length() == 0) {
            return null;
        }
        return parse(value2, getFormat(pattern, locale));
    }

    /* access modifiers changed from: protected */
    public Format getFormat(String pattern, Locale locale) {
        NumberFormat formatter;
        if (!(pattern != null && pattern.length() > 0)) {
            formatter = (NumberFormat) getFormat(locale);
        } else if (locale == null) {
            formatter = new DecimalFormat(pattern);
        } else {
            formatter = new DecimalFormat(pattern, new DecimalFormatSymbols(locale));
        }
        if (determineScale(formatter) == 0) {
            formatter.setParseIntegerOnly(true);
        }
        return formatter;
    }

    /* access modifiers changed from: protected */
    public int determineScale(NumberFormat format) {
        if (!isStrict()) {
            return -1;
        }
        if (!isAllowFractions() || format.isParseIntegerOnly()) {
            return 0;
        }
        int minimumFraction = format.getMinimumFractionDigits();
        if (minimumFraction != format.getMaximumFractionDigits()) {
            return -1;
        }
        int scale = minimumFraction;
        if (format instanceof DecimalFormat) {
            int multiplier = ((DecimalFormat) format).getMultiplier();
            if (multiplier == 100) {
                return scale + 2;
            }
            if (multiplier == 1000) {
                return scale + 3;
            }
            return scale;
        } else if (this.formatType == 2) {
            return scale + 2;
        } else {
            return scale;
        }
    }

    /* access modifiers changed from: protected */
    public Format getFormat(Locale locale) {
        switch (this.formatType) {
            case 1:
                if (locale == null) {
                    return NumberFormat.getCurrencyInstance();
                }
                return NumberFormat.getCurrencyInstance(locale);
            case 2:
                if (locale == null) {
                    return NumberFormat.getPercentInstance();
                }
                return NumberFormat.getPercentInstance(locale);
            default:
                if (locale == null) {
                    return NumberFormat.getInstance();
                }
                return NumberFormat.getInstance(locale);
        }
    }
}
