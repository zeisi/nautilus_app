package com.ua.sdk.datasourceidentifier;

import com.ua.sdk.Entity;
import com.ua.sdk.device.Device;

public interface DataSourceIdentifier extends Entity {
    Device getDevice();

    String getName();
}
