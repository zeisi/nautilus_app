package com.ua.sdk;

import android.os.Parcelable;

public interface ImageUrl extends Parcelable {
    String getCustom(int i, int i2);

    String getLarge();

    String getMedium();

    String getSmall();
}
