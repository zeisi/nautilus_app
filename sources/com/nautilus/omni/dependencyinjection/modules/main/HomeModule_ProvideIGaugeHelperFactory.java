package com.nautilus.omni.dependencyinjection.modules.main;

import com.nautilus.omni.appmodules.home.domain.helpers.IGaugeHelper;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class HomeModule_ProvideIGaugeHelperFactory implements Factory<IGaugeHelper> {
    static final /* synthetic */ boolean $assertionsDisabled = (!HomeModule_ProvideIGaugeHelperFactory.class.desiredAssertionStatus());
    private final HomeModule module;

    public HomeModule_ProvideIGaugeHelperFactory(HomeModule module2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            return;
        }
        throw new AssertionError();
    }

    public IGaugeHelper get() {
        return (IGaugeHelper) Preconditions.checkNotNull(this.module.provideIGaugeHelper(), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<IGaugeHelper> create(HomeModule module2) {
        return new HomeModule_ProvideIGaugeHelperFactory(module2);
    }

    public static IGaugeHelper proxyProvideIGaugeHelper(HomeModule instance) {
        return instance.provideIGaugeHelper();
    }
}
