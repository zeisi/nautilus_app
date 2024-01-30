package com.nautilus.omni.dependencyinjection.modules.main;

import com.nautilus.omni.appmodules.home.view.HomeFragment;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class HomeModule_ProvideHomeViewFactory implements Factory<HomeFragment> {
    static final /* synthetic */ boolean $assertionsDisabled = (!HomeModule_ProvideHomeViewFactory.class.desiredAssertionStatus());
    private final HomeModule module;

    public HomeModule_ProvideHomeViewFactory(HomeModule module2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            return;
        }
        throw new AssertionError();
    }

    public HomeFragment get() {
        return (HomeFragment) Preconditions.checkNotNull(this.module.provideHomeView(), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<HomeFragment> create(HomeModule module2) {
        return new HomeModule_ProvideHomeViewFactory(module2);
    }

    public static HomeFragment proxyProvideHomeView(HomeModule instance) {
        return instance.provideHomeView();
    }
}
