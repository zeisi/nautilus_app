package com.mobsandgeeks.saripaar.adapter;

import android.widget.Spinner;

public class SpinnerIndexAdapter implements ViewDataAdapter<Spinner, Integer> {
    public Integer getData(Spinner spinner) {
        return Integer.valueOf(spinner.getSelectedItemPosition());
    }
}
