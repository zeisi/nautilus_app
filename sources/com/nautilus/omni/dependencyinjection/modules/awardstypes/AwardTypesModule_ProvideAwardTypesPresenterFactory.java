package com.nautilus.omni.dependencyinjection.modules.awardstypes;

import android.content.Context;
import com.nautilus.omni.appmodules.awardtypes.interactor.AwardsTypesInteractorContract;
import com.nautilus.omni.appmodules.awardtypes.presenter.AwardTypesPresenterContract;
import com.nautilus.omni.appmodules.awardtypes.view.AwardTypesActivityContract;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class AwardTypesModule_ProvideAwardTypesPresenterFactory implements Factory<AwardTypesPresenterContract> {
    static final /* synthetic */ boolean $assertionsDisabled = (!AwardTypesModule_ProvideAwardTypesPresenterFactory.class.desiredAssertionStatus());
    private final Provider<AwardTypesActivityContract> awardTypesActivityContractProvider;
    private final Provider<AwardsTypesInteractorContract> awardsInteractorContractProvider;
    private final Provider<Context> contextProvider;
    private final AwardTypesModule module;

    public AwardTypesModule_ProvideAwardTypesPresenterFactory(AwardTypesModule module2, Provider<Context> contextProvider2, Provider<AwardsTypesInteractorContract> awardsInteractorContractProvider2, Provider<AwardTypesActivityContract> awardTypesActivityContractProvider2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            if ($assertionsDisabled || contextProvider2 != null) {
                this.contextProvider = contextProvider2;
                if ($assertionsDisabled || awardsInteractorContractProvider2 != null) {
                    this.awardsInteractorContractProvider = awardsInteractorContractProvider2;
                    if ($assertionsDisabled || awardTypesActivityContractProvider2 != null) {
                        this.awardTypesActivityContractProvider = awardTypesActivityContractProvider2;
                        return;
                    }
                    throw new AssertionError();
                }
                throw new AssertionError();
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public AwardTypesPresenterContract get() {
        return (AwardTypesPresenterContract) Preconditions.checkNotNull(this.module.provideAwardTypesPresenter(this.contextProvider.get(), this.awardsInteractorContractProvider.get(), this.awardTypesActivityContractProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<AwardTypesPresenterContract> create(AwardTypesModule module2, Provider<Context> contextProvider2, Provider<AwardsTypesInteractorContract> awardsInteractorContractProvider2, Provider<AwardTypesActivityContract> awardTypesActivityContractProvider2) {
        return new AwardTypesModule_ProvideAwardTypesPresenterFactory(module2, contextProvider2, awardsInteractorContractProvider2, awardTypesActivityContractProvider2);
    }

    public static AwardTypesPresenterContract proxyProvideAwardTypesPresenter(AwardTypesModule instance, Context context, AwardsTypesInteractorContract awardsInteractorContract, AwardTypesActivityContract awardTypesActivityContract) {
        return instance.provideAwardTypesPresenter(context, awardsInteractorContract, awardTypesActivityContract);
    }
}
