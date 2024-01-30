package com.nautilus.omni.dependencyinjection.modules.awardstypes;

import com.nautilus.omni.appmodules.awardtypes.interactor.AwardsTypesInteractorContract;
import com.nautilus.omni.dataaccess.AwardsDaoHelper;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class AwardTypesModule_ProvideAwardsTypesInteractorFactory implements Factory<AwardsTypesInteractorContract> {
    static final /* synthetic */ boolean $assertionsDisabled = (!AwardTypesModule_ProvideAwardsTypesInteractorFactory.class.desiredAssertionStatus());
    private final Provider<AwardsDaoHelper> awardsDaoHelperProvider;
    private final AwardTypesModule module;

    public AwardTypesModule_ProvideAwardsTypesInteractorFactory(AwardTypesModule module2, Provider<AwardsDaoHelper> awardsDaoHelperProvider2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            if ($assertionsDisabled || awardsDaoHelperProvider2 != null) {
                this.awardsDaoHelperProvider = awardsDaoHelperProvider2;
                return;
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public AwardsTypesInteractorContract get() {
        return (AwardsTypesInteractorContract) Preconditions.checkNotNull(this.module.provideAwardsTypesInteractor(this.awardsDaoHelperProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<AwardsTypesInteractorContract> create(AwardTypesModule module2, Provider<AwardsDaoHelper> awardsDaoHelperProvider2) {
        return new AwardTypesModule_ProvideAwardsTypesInteractorFactory(module2, awardsDaoHelperProvider2);
    }

    public static AwardsTypesInteractorContract proxyProvideAwardsTypesInteractor(AwardTypesModule instance, AwardsDaoHelper awardsDaoHelper) {
        return instance.provideAwardsTypesInteractor(awardsDaoHelper);
    }
}
