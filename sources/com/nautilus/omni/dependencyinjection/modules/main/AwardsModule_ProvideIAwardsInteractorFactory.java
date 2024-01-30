package com.nautilus.omni.dependencyinjection.modules.main;

import com.nautilus.omni.appmodules.awards.interactor.AwardsInteractorContract;
import com.nautilus.omni.dataaccess.AwardsDaoHelper;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class AwardsModule_ProvideIAwardsInteractorFactory implements Factory<AwardsInteractorContract> {
    static final /* synthetic */ boolean $assertionsDisabled = (!AwardsModule_ProvideIAwardsInteractorFactory.class.desiredAssertionStatus());
    private final Provider<AwardsDaoHelper> awardsDaoHelperProvider;
    private final AwardsModule module;

    public AwardsModule_ProvideIAwardsInteractorFactory(AwardsModule module2, Provider<AwardsDaoHelper> awardsDaoHelperProvider2) {
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

    public AwardsInteractorContract get() {
        return (AwardsInteractorContract) Preconditions.checkNotNull(this.module.provideIAwardsInteractor(this.awardsDaoHelperProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<AwardsInteractorContract> create(AwardsModule module2, Provider<AwardsDaoHelper> awardsDaoHelperProvider2) {
        return new AwardsModule_ProvideIAwardsInteractorFactory(module2, awardsDaoHelperProvider2);
    }

    public static AwardsInteractorContract proxyProvideIAwardsInteractor(AwardsModule instance, AwardsDaoHelper awardsDaoHelper) {
        return instance.provideIAwardsInteractor(awardsDaoHelper);
    }
}
