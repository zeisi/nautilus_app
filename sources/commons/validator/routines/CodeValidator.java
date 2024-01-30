package commons.validator.routines;

import commons.validator.routines.checkdigit.CheckDigit;
import java.io.Serializable;

public final class CodeValidator implements Serializable {
    private static final long serialVersionUID = 446960910870938233L;
    private final CheckDigit checkdigit;
    private final int maxLength;
    private final int minLength;
    private final RegexValidator regexValidator;

    public CodeValidator(String regex, CheckDigit checkdigit2) {
        this(regex, -1, -1, checkdigit2);
    }

    public CodeValidator(String regex, int length, CheckDigit checkdigit2) {
        this(regex, length, length, checkdigit2);
    }

    public CodeValidator(String regex, int minLength2, int maxLength2, CheckDigit checkdigit2) {
        if (regex == null || regex.length() <= 0) {
            this.regexValidator = null;
        } else {
            this.regexValidator = new RegexValidator(regex);
        }
        this.minLength = minLength2;
        this.maxLength = maxLength2;
        this.checkdigit = checkdigit2;
    }

    public CodeValidator(RegexValidator regexValidator2, CheckDigit checkdigit2) {
        this(regexValidator2, -1, -1, checkdigit2);
    }

    public CodeValidator(RegexValidator regexValidator2, int length, CheckDigit checkdigit2) {
        this(regexValidator2, length, length, checkdigit2);
    }

    public CodeValidator(RegexValidator regexValidator2, int minLength2, int maxLength2, CheckDigit checkdigit2) {
        this.regexValidator = regexValidator2;
        this.minLength = minLength2;
        this.maxLength = maxLength2;
        this.checkdigit = checkdigit2;
    }

    public CheckDigit getCheckDigit() {
        return this.checkdigit;
    }

    public int getMinLength() {
        return this.minLength;
    }

    public int getMaxLength() {
        return this.maxLength;
    }

    public RegexValidator getRegexValidator() {
        return this.regexValidator;
    }

    public boolean isValid(String input) {
        return validate(input) != null;
    }

    public Object validate(String input) {
        if (input == null) {
            return null;
        }
        String code = input.trim();
        if (code.length() == 0) {
            return null;
        }
        if (this.regexValidator != null && (code = this.regexValidator.validate(code)) == null) {
            return null;
        }
        if ((this.minLength >= 0 && code.length() < this.minLength) || (this.maxLength >= 0 && code.length() > this.maxLength)) {
            return null;
        }
        if (this.checkdigit == null || this.checkdigit.isValid(code)) {
            return code;
        }
        return null;
    }
}
