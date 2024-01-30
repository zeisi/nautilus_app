package commons.validator.routines.checkdigit;

public final class ISBN10CheckDigit extends ModulusCheckDigit {
    public static final CheckDigit ISBN10_CHECK_DIGIT = new ISBN10CheckDigit();
    private static final long serialVersionUID = 8000855044504864964L;

    public ISBN10CheckDigit() {
        super(11);
    }

    /* access modifiers changed from: protected */
    public int weightedValue(int charValue, int leftPos, int rightPos) {
        return charValue * rightPos;
    }

    /* access modifiers changed from: protected */
    public int toInt(char character, int leftPos, int rightPos) throws CheckDigitException {
        if (rightPos == 1 && character == 'X') {
            return 10;
        }
        return super.toInt(character, leftPos, rightPos);
    }

    /* access modifiers changed from: protected */
    public String toCheckDigit(int charValue) throws CheckDigitException {
        if (charValue == 10) {
            return "X";
        }
        return super.toCheckDigit(charValue);
    }
}
