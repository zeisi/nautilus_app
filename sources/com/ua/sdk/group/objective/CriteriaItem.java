package com.ua.sdk.group.objective;

import android.os.Parcelable;

public interface CriteriaItem<T> extends Parcelable {
    String getName();

    T getValue();

    void setValue(T t);
}
