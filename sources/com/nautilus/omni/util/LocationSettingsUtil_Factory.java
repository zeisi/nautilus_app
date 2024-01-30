package com.nautilus.omni.util;

import dagger.internal.Factory;

public final class LocationSettingsUtil_Factory implements Factory<LocationSettingsUtil> {
    private static final LocationSettingsUtil_Factory INSTANCE = new LocationSettingsUtil_Factory();

    public LocationSettingsUtil get() {
        return new LocationSettingsUtil();
    }

    public static Factory<LocationSettingsUtil> create() {
        return INSTANCE;
    }
}
