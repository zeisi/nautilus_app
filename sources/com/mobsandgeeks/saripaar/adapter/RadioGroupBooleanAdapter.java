package com.mobsandgeeks.saripaar.adapter;

import android.widget.RadioGroup;
import com.mobsandgeeks.saripaar.exception.ConversionException;

public class RadioGroupBooleanAdapter implements ViewDataAdapter<RadioGroup, Boolean> {
    public Boolean getData(RadioGroup radioGroup) throws ConversionException {
        return Boolean.valueOf(radioGroup.getCheckedRadioButtonId() != -1);
    }
}
