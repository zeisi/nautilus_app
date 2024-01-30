package commons.validator.routines.checkdigit;

public final class LuhnCheckDigit extends ModulusCheckDigit {
    public static final CheckDigit LUHN_CHECK_DIGIT = new LuhnCheckDigit();
    private static final int[] POSITION_WEIGHT = {2, 1};
    private static final long serialVersionUID = -2976900113942875999L;

    public LuhnCheckDigit() {
        super(10);
    }

    /* access modifiers changed from: protected */
    public int weightedValue(int charValue, int leftPos, int rightPos) {
        int weightedValue = charValue * POSITION_WEIGHT[rightPos % 2];
        return weightedValue > 9 ? weightedValue - 9 : weightedValue;
    }
}
