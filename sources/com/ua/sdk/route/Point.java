package com.ua.sdk.route;

import android.os.Parcelable;

public interface Point extends Parcelable {
    Double getDistanceMeters();

    Double getElevation();

    Double getLatitude();

    Double getLongitude();
}
