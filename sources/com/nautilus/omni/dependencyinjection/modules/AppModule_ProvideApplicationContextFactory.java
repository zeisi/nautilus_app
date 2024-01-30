package com.nautilus.omni.dependencyinjection.modules;

import android.content.Context;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class AppModule_ProvideApplicationContextFactory implements Factory<Context> {
    static final /* synthetic */ boolean $assertionsDisabled = (!AppModule_ProvideApplicationContextFactory.class.desiredAssertionStatus());
    private final AppModule module;

    public AppModule_ProvideApplicationContextFactory(AppModule module2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            return;
        }
        throw new AssertionError();
    }

    public Context get() {
        return (Context) Preconditions.checkNotNull(this.module.provideApplicationContext(), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<Context> create(AppModule module2) {
        return new AppModule_ProvideApplicationContextFactory(module2);
    }
}
