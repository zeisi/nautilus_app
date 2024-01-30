package com.nautilus.omni.dependencyinjection.modules.awardstypes;

import com.nautilus.omni.appmodules.awardtypes.view.AwardTypesActivityContract;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class AwardTypesModule_ProvideIAwardsViewFactory implements Factory<AwardTypesActivityContract> {
    static final /* synthetic */ boolean $assertionsDisabled = (!AwardTypesModule_ProvideIAwardsViewFactory.class.desiredAssertionStatus());
    private final AwardTypesModule module;

    public AwardTypesModule_ProvideIAwardsViewFactory(AwardTypesModule module2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            return;
        }
        throw new AssertionError();
    }

    public AwardTypesActivityContract get() {
        return (AwardTypesActivityContract) Preconditions.checkNotNull(this.module.provideIAwardsView(), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<AwardTypesActivityContract> create(AwardTypesModule module2) {
        return new AwardTypesModule_ProvideIAwardsViewFactory(module2);
    }

    public static AwardTypesActivityContract proxyProvideIAwardsView(AwardTypesModule instance) {
        return instance.provideIAwardsView();
    }
}
