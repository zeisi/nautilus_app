package commons.validator.routines;

import commons.validator.routines.checkdigit.CheckDigitException;
import commons.validator.routines.checkdigit.EAN13CheckDigit;
import commons.validator.routines.checkdigit.ISBN10CheckDigit;
import java.io.Serializable;

public class ISBNValidator implements Serializable {
    private static final String GROUP = "(\\d{1,5})";
    static final String ISBN10_REGEX = "^(?:(\\d{9}[0-9X])|(?:(\\d{1,5})(?:\\-|\\s)(\\d{1,7})(?:\\-|\\s)(\\d{1,6})(?:\\-|\\s)([0-9X])))$";
    static final String ISBN13_REGEX = "^(978|979)(?:(\\d{10})|(?:(?:\\-|\\s)(\\d{1,5})(?:\\-|\\s)(\\d{1,7})(?:\\-|\\s)(\\d{1,6})(?:\\-|\\s)([0-9])))$";
    private static final ISBNValidator ISBN_VALIDATOR = new ISBNValidator();
    private static final ISBNValidator ISBN_VALIDATOR_NO_CONVERT = new ISBNValidator(false);
    private static final String PUBLISHER = "(\\d{1,7})";
    private static final String SEP = "(?:\\-|\\s)";
    private static final String TITLE = "(\\d{1,6})";
    private static final long serialVersionUID = 4319515687976420405L;
    private final boolean convert;
    private final CodeValidator isbn10Validator;
    private final CodeValidator isbn13Validator;

    public static ISBNValidator getInstance() {
        return ISBN_VALIDATOR;
    }

    public static ISBNValidator getInstance(boolean convert2) {
        return convert2 ? ISBN_VALIDATOR : ISBN_VALIDATOR_NO_CONVERT;
    }

    public ISBNValidator() {
        this(true);
    }

    public ISBNValidator(boolean convert2) {
        this.isbn10Validator = new CodeValidator(ISBN10_REGEX, 10, ISBN10CheckDigit.ISBN10_CHECK_DIGIT);
        this.isbn13Validator = new CodeValidator(ISBN13_REGEX, 13, EAN13CheckDigit.EAN13_CHECK_DIGIT);
        this.convert = convert2;
    }

    public boolean isValid(String code) {
        return isValidISBN13(code) || isValidISBN10(code);
    }

    public boolean isValidISBN10(String code) {
        return this.isbn10Validator.isValid(code);
    }

    public boolean isValidISBN13(String code) {
        return this.isbn13Validator.isValid(code);
    }

    public String validate(String code) {
        String result = validateISBN13(code);
        if (result != null) {
            return result;
        }
        String result2 = validateISBN10(code);
        if (result2 == null || !this.convert) {
            return result2;
        }
        return convertToISBN13(result2);
    }

    public String validateISBN10(String code) {
        Object result = this.isbn10Validator.validate(code);
        if (result == null) {
            return null;
        }
        return result.toString();
    }

    public String validateISBN13(String code) {
        Object result = this.isbn13Validator.validate(code);
        if (result == null) {
            return null;
        }
        return result.toString();
    }

    public String convertToISBN13(String isbn10) {
        if (isbn10 == null) {
            return null;
        }
        String input = isbn10.trim();
        if (input.length() != 10) {
            throw new IllegalArgumentException("Invalid length " + input.length() + " for '" + input + "'");
        }
        String isbn13 = "978" + input.substring(0, 9);
        try {
            return isbn13 + this.isbn13Validator.getCheckDigit().calculate(isbn13);
        } catch (CheckDigitException e) {
            throw new IllegalArgumentException("Check digit error for '" + input + "' - " + e.getMessage());
        }
    }
}
