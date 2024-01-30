package com.nautilus.omni.dependencyinjection.modules.main;

import android.content.Context;
import com.nautilus.omni.appmodules.awards.interactor.AwardsInteractorContract;
import com.nautilus.omni.appmodules.awards.presenter.AwardsPresenterContract;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class AwardsModule_ProvideIAwardsPresenterFactory implements Factory<AwardsPresenterContract> {
    static final /* synthetic */ boolean $assertionsDisabled = (!AwardsModule_ProvideIAwardsPresenterFactory.class.desiredAssertionStatus());
    private final Provider<AwardsInteractorContract> awardsInteractorContractProvider;
    private final Provider<Context> contextProvider;
    private final AwardsModule module;

    public AwardsModule_ProvideIAwardsPresenterFactory(AwardsModule module2, Provider<Context> contextProvider2, Provider<AwardsInteractorContract> awardsInteractorContractProvider2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            if ($assertionsDisabled || contextProvider2 != null) {
                this.contextProvider = contextProvider2;
                if ($assertionsDisabled || awardsInteractorContractProvider2 != null) {
                    this.awardsInteractorContractProvider = awardsInteractorContractProvider2;
                    return;
                }
                throw new AssertionError();
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public AwardsPresenterContract get() {
        return (AwardsPresenterContract) Preconditions.checkNotNull(this.module.provideIAwardsPresenter(this.contextProvider.get(), this.awardsInteractorContractProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<AwardsPresenterContract> create(AwardsModule module2, Provider<Context> contextProvider2, Provider<AwardsInteractorContract> awardsInteractorContractProvider2) {
        return new AwardsModule_ProvideIAwardsPresenterFactory(module2, contextProvider2, awardsInteractorContractProvider2);
    }

    public static AwardsPresenterContract proxyProvideIAwardsPresenter(AwardsModule instance, Context context, AwardsInteractorContract awardsInteractorContract) {
        return instance.provideIAwardsPresenter(context, awardsInteractorContract);
    }
}
