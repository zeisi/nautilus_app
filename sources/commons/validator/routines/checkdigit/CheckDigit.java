package commons.validator.routines.checkdigit;

public interface CheckDigit {
    String calculate(String str) throws CheckDigitException;

    boolean isValid(String str);
}
