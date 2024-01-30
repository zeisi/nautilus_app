package com.ua.sdk.datapoint;

import android.os.Parcelable;

public interface DataField extends Parcelable {
    String getId();

    String getType();

    DataUnits getUnits();
}
