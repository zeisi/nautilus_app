package com.mobsandgeeks.saripaar.adapter;

import android.view.View;
import com.mobsandgeeks.saripaar.exception.ConversionException;

public interface ViewDataAdapter<VIEW extends View, DATA> {
    DATA getData(VIEW view) throws ConversionException;
}
