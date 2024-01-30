package com.nautilus.omni.dependencyinjection.modules.awardstypes;

import com.nautilus.omni.appmodules.awardtypes.view.AwardTypesActivity;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class AwardTypesModule_ProvideAwardsViewFactory implements Factory<AwardTypesActivity> {
    static final /* synthetic */ boolean $assertionsDisabled = (!AwardTypesModule_ProvideAwardsViewFactory.class.desiredAssertionStatus());
    private final AwardTypesModule module;

    public AwardTypesModule_ProvideAwardsViewFactory(AwardTypesModule module2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            return;
        }
        throw new AssertionError();
    }

    public AwardTypesActivity get() {
        return (AwardTypesActivity) Preconditions.checkNotNull(this.module.provideAwardsView(), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<AwardTypesActivity> create(AwardTypesModule module2) {
        return new AwardTypesModule_ProvideAwardsViewFactory(module2);
    }

    public static AwardTypesActivity proxyProvideAwardsView(AwardTypesModule instance) {
        return instance.provideAwardsView();
    }
}
