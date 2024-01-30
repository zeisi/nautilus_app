package com.mobsandgeeks.saripaar.adapter;

import android.widget.TextView;
import com.mobsandgeeks.saripaar.exception.ConversionException;

public class TextViewFloatAdapter implements ViewDataAdapter<TextView, Float> {
    private static final String REGEX_DECIMAL = "[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?";

    public Float getData(TextView editText) throws ConversionException {
        String floatString = editText.getText().toString().trim();
        if (floatString.matches(REGEX_DECIMAL)) {
            return Float.valueOf(Float.parseFloat(floatString));
        }
        throw new ConversionException(String.format("Expected a floating point number, but was %s", new Object[]{floatString}));
    }
}
