package com.ua.sdk.device;

import android.os.Parcelable;

public interface DeviceBuilder extends Parcelable {
    Device build();

    DeviceBuilder setDescription(String str);

    DeviceBuilder setManufacturer(String str);

    DeviceBuilder setModel(String str);

    DeviceBuilder setName(String str);
}
