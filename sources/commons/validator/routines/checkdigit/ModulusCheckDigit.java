package commons.validator.routines.checkdigit;

import java.io.Serializable;

public abstract class ModulusCheckDigit implements CheckDigit, Serializable {
    private static final long serialVersionUID = 2948962251251528941L;
    private final int modulus;

    /* access modifiers changed from: protected */
    public abstract int weightedValue(int i, int i2, int i3) throws CheckDigitException;

    public ModulusCheckDigit(int modulus2) {
        this.modulus = modulus2;
    }

    public int getModulus() {
        return this.modulus;
    }

    public boolean isValid(String code) {
        boolean z = true;
        if (code == null || code.length() == 0) {
            return false;
        }
        try {
            if (calculateModulus(code, true) != 0) {
                z = false;
            }
            return z;
        } catch (CheckDigitException e) {
            return false;
        }
    }

    public String calculate(String code) throws CheckDigitException {
        if (code == null || code.length() == 0) {
            throw new CheckDigitException("Code is missing");
        }
        return toCheckDigit((this.modulus - calculateModulus(code, false)) % this.modulus);
    }

    /* access modifiers changed from: protected */
    public int calculateModulus(String code, boolean includesCheckDigit) throws CheckDigitException {
        int total = 0;
        for (int i = 0; i < code.length(); i++) {
            int leftPos = i + 1;
            int rightPos = (code.length() + (includesCheckDigit ? 0 : 1)) - i;
            total += weightedValue(toInt(code.charAt(i), leftPos, rightPos), leftPos, rightPos);
        }
        if (total != 0) {
            return total % this.modulus;
        }
        throw new CheckDigitException("Invalid code, sum is zero");
    }

    /* access modifiers changed from: protected */
    public int toInt(char character, int leftPos, int rightPos) throws CheckDigitException {
        if (Character.isDigit(character)) {
            return Character.getNumericValue(character);
        }
        throw new CheckDigitException("Invalid Character[" + leftPos + "] = '" + character + "'");
    }

    /* access modifiers changed from: protected */
    public String toCheckDigit(int charValue) throws CheckDigitException {
        if (charValue >= 0 && charValue <= 9) {
            return Integer.toString(charValue);
        }
        throw new CheckDigitException("Invalid Check Digit Value =" + charValue);
    }

    public static int sumDigits(int number) {
        int total = 0;
        for (int todo = number; todo > 0; todo /= 10) {
            total += todo % 10;
        }
        return total;
    }
}
