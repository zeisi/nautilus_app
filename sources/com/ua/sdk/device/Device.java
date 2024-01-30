package com.ua.sdk.device;

import com.ua.sdk.Entity;

public interface Device extends Entity {
    String getDescription();

    String getManufacturer();

    String getModel();

    String getName();
}
