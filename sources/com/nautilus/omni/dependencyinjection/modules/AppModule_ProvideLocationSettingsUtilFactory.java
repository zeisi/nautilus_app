package com.nautilus.omni.dependencyinjection.modules;

import com.nautilus.omni.util.LocationSettingsUtil;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class AppModule_ProvideLocationSettingsUtilFactory implements Factory<LocationSettingsUtil> {
    static final /* synthetic */ boolean $assertionsDisabled = (!AppModule_ProvideLocationSettingsUtilFactory.class.desiredAssertionStatus());
    private final AppModule module;

    public AppModule_ProvideLocationSettingsUtilFactory(AppModule module2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            return;
        }
        throw new AssertionError();
    }

    public LocationSettingsUtil get() {
        return (LocationSettingsUtil) Preconditions.checkNotNull(this.module.provideLocationSettingsUtil(), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<LocationSettingsUtil> create(AppModule module2) {
        return new AppModule_ProvideLocationSettingsUtilFactory(module2);
    }

    public static LocationSettingsUtil proxyProvideLocationSettingsUtil(AppModule instance) {
        return instance.provideLocationSettingsUtil();
    }
}
