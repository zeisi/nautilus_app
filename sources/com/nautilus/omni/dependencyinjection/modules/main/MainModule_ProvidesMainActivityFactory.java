package com.nautilus.omni.dependencyinjection.modules.main;

import com.nautilus.omni.appmodules.mainactivity.view.MainActivity;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class MainModule_ProvidesMainActivityFactory implements Factory<MainActivity> {
    static final /* synthetic */ boolean $assertionsDisabled = (!MainModule_ProvidesMainActivityFactory.class.desiredAssertionStatus());
    private final MainModule module;

    public MainModule_ProvidesMainActivityFactory(MainModule module2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            return;
        }
        throw new AssertionError();
    }

    public MainActivity get() {
        return (MainActivity) Preconditions.checkNotNull(this.module.providesMainActivity(), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<MainActivity> create(MainModule module2) {
        return new MainModule_ProvidesMainActivityFactory(module2);
    }

    public static MainActivity proxyProvidesMainActivity(MainModule instance) {
        return instance.providesMainActivity();
    }
}
