package com.mobsandgeeks.saripaar.adapter;

import android.widget.TextView;
import com.mobsandgeeks.saripaar.exception.ConversionException;

public class TextViewDoubleAdapter implements ViewDataAdapter<TextView, Double> {
    private static final String REGEX_DECIMAL = "[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?";

    public Double getData(TextView editText) throws ConversionException {
        String doubleString = editText.getText().toString().trim();
        if (doubleString.matches(REGEX_DECIMAL)) {
            return Double.valueOf(Double.parseDouble(doubleString));
        }
        throw new ConversionException(String.format("Expected a floating point number, but was %s", new Object[]{doubleString}));
    }
}
