package com.nautilus.omni.dependencyinjection.modules.main;

import com.nautilus.omni.appmodules.awards.view.AwardsFragment;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class AwardsModule_ProvideAwardsViewFactory implements Factory<AwardsFragment> {
    static final /* synthetic */ boolean $assertionsDisabled = (!AwardsModule_ProvideAwardsViewFactory.class.desiredAssertionStatus());
    private final AwardsModule module;

    public AwardsModule_ProvideAwardsViewFactory(AwardsModule module2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            return;
        }
        throw new AssertionError();
    }

    public AwardsFragment get() {
        return (AwardsFragment) Preconditions.checkNotNull(this.module.provideAwardsView(), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<AwardsFragment> create(AwardsModule module2) {
        return new AwardsModule_ProvideAwardsViewFactory(module2);
    }

    public static AwardsFragment proxyProvideAwardsView(AwardsModule instance) {
        return instance.provideAwardsView();
    }
}
