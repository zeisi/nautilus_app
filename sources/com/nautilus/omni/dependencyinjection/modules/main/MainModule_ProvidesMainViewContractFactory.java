package com.nautilus.omni.dependencyinjection.modules.main;

import com.nautilus.omni.appmodules.mainactivity.view.MainViewContract;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class MainModule_ProvidesMainViewContractFactory implements Factory<MainViewContract> {
    static final /* synthetic */ boolean $assertionsDisabled = (!MainModule_ProvidesMainViewContractFactory.class.desiredAssertionStatus());
    private final MainModule module;

    public MainModule_ProvidesMainViewContractFactory(MainModule module2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            return;
        }
        throw new AssertionError();
    }

    public MainViewContract get() {
        return (MainViewContract) Preconditions.checkNotNull(this.module.providesMainViewContract(), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<MainViewContract> create(MainModule module2) {
        return new MainModule_ProvidesMainViewContractFactory(module2);
    }

    public static MainViewContract proxyProvidesMainViewContract(MainModule instance) {
        return instance.providesMainViewContract();
    }
}
