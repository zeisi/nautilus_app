package commons.validator.routines;

import java.io.Serializable;
import java.text.Format;
import java.text.ParsePosition;
import java.util.Locale;

public abstract class AbstractFormatValidator implements Serializable {
    private static final long serialVersionUID = -4690687565200568258L;
    private final boolean strict;

    /* access modifiers changed from: protected */
    public abstract Format getFormat(String str, Locale locale);

    public abstract boolean isValid(String str, String str2, Locale locale);

    /* access modifiers changed from: protected */
    public abstract Object processParsedValue(Object obj, Format format);

    public AbstractFormatValidator(boolean strict2) {
        this.strict = strict2;
    }

    public boolean isStrict() {
        return this.strict;
    }

    public boolean isValid(String value) {
        return isValid(value, (String) null, (Locale) null);
    }

    public boolean isValid(String value, String pattern) {
        return isValid(value, pattern, (Locale) null);
    }

    public boolean isValid(String value, Locale locale) {
        return isValid(value, (String) null, locale);
    }

    public String format(Object value) {
        return format(value, (String) null, (Locale) null);
    }

    public String format(Object value, String pattern) {
        return format(value, pattern, (Locale) null);
    }

    public String format(Object value, Locale locale) {
        return format(value, (String) null, locale);
    }

    public String format(Object value, String pattern, Locale locale) {
        return format(value, getFormat(pattern, locale));
    }

    /* access modifiers changed from: protected */
    public String format(Object value, Format formatter) {
        return formatter.format(value);
    }

    /* access modifiers changed from: protected */
    public Object parse(String value, Format formatter) {
        ParsePosition pos = new ParsePosition(0);
        Object parsedValue = formatter.parseObject(value, pos);
        if (pos.getErrorIndex() > -1) {
            return null;
        }
        if (isStrict() && pos.getIndex() < value.length()) {
            return null;
        }
        if (parsedValue != null) {
            parsedValue = processParsedValue(parsedValue, formatter);
        }
        return parsedValue;
    }
}
