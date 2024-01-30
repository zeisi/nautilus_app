package com.mobsandgeeks.saripaar.adapter;

import android.widget.TextView;
import com.mobsandgeeks.saripaar.exception.ConversionException;

public class TextViewIntegerAdapter implements ViewDataAdapter<TextView, Integer> {
    private static final String REGEX_INTEGER = "\\d+";

    public Integer getData(TextView editText) throws ConversionException {
        String integerString = editText.getText().toString().trim();
        if (integerString.matches(REGEX_INTEGER)) {
            return Integer.valueOf(Integer.parseInt(integerString));
        }
        throw new ConversionException(String.format("Expected an integer, but was %s", new Object[]{integerString}));
    }
}
