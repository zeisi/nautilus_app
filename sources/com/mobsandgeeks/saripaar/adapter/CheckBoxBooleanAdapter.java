package com.mobsandgeeks.saripaar.adapter;

import android.widget.CheckBox;

public class CheckBoxBooleanAdapter implements ViewDataAdapter<CheckBox, Boolean> {
    public Boolean getData(CheckBox checkBox) {
        return Boolean.valueOf(checkBox.isChecked());
    }
}
