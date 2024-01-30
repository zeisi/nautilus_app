package com.nautilus.omni.dependencyinjection.modules;

import android.app.Application;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class AppModule_ProvidesApplicationContextFactory implements Factory<Application> {
    static final /* synthetic */ boolean $assertionsDisabled = (!AppModule_ProvidesApplicationContextFactory.class.desiredAssertionStatus());
    private final AppModule module;

    public AppModule_ProvidesApplicationContextFactory(AppModule module2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            return;
        }
        throw new AssertionError();
    }

    public Application get() {
        return (Application) Preconditions.checkNotNull(this.module.providesApplicationContext(), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<Application> create(AppModule module2) {
        return new AppModule_ProvidesApplicationContextFactory(module2);
    }

    public static Application proxyProvidesApplicationContext(AppModule instance) {
        return instance.providesApplicationContext();
    }
}
