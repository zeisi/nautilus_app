package com.mobsandgeeks.saripaar.adapter;

import android.widget.TextView;

public class TextViewStringAdapter implements ViewDataAdapter<TextView, String> {
    public String getData(TextView editText) {
        return editText.getText().toString();
    }
}
