package com.ua.sdk.gear.brand;

import android.os.Parcelable;
import com.ua.sdk.Entity;

public interface GearBrand extends Entity, Parcelable {
    String getBrandName();

    String getGearTypeId();

    Boolean isPopular();
}
