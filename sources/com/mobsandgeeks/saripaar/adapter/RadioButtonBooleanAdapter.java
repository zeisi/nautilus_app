package com.mobsandgeeks.saripaar.adapter;

import android.widget.RadioButton;

public class RadioButtonBooleanAdapter implements ViewDataAdapter<RadioButton, Boolean> {
    public Boolean getData(RadioButton radioButton) {
        return Boolean.valueOf(radioButton.isChecked());
    }
}
