package com.nautilus.omni.dependencyinjection.modules.splash;

import com.nautilus.omni.appmodules.splash.view.SplashViewContract;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class SplashModule_ProvideSplashActivityFactory implements Factory<SplashViewContract> {
    static final /* synthetic */ boolean $assertionsDisabled = (!SplashModule_ProvideSplashActivityFactory.class.desiredAssertionStatus());
    private final SplashModule module;

    public SplashModule_ProvideSplashActivityFactory(SplashModule module2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            return;
        }
        throw new AssertionError();
    }

    public SplashViewContract get() {
        return (SplashViewContract) Preconditions.checkNotNull(this.module.provideSplashActivity(), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<SplashViewContract> create(SplashModule module2) {
        return new SplashModule_ProvideSplashActivityFactory(module2);
    }

    public static SplashViewContract proxyProvideSplashActivity(SplashModule instance) {
        return instance.provideSplashActivity();
    }
}
