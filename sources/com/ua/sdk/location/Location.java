package com.ua.sdk.location;

import android.os.Parcelable;

public interface Location extends Parcelable {
    String getAddress();

    String getCountry();

    String getLocality();

    String getRegion();

    void setAddress(String str);

    void setCountry(String str);

    void setLocality(String str);

    void setRegion(String str);
}
