package com.nautilus.omni.dependencyinjection.modules.main;

import android.content.Context;
import com.nautilus.omni.appmodules.awards.AwardValueBuilder;
import com.nautilus.omni.appmodules.home.domain.interactors.HomeInteractorContract;
import com.nautilus.omni.appmodules.home.presenter.HomePresenterContract;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class HomeModule_ProvideIHomePresenterFactory implements Factory<HomePresenterContract> {
    static final /* synthetic */ boolean $assertionsDisabled = (!HomeModule_ProvideIHomePresenterFactory.class.desiredAssertionStatus());
    private final Provider<AwardValueBuilder> awardValueBuilderProvider;
    private final Provider<Context> contextProvider;
    private final Provider<HomeInteractorContract> homeInteractorContractProvider;
    private final HomeModule module;

    public HomeModule_ProvideIHomePresenterFactory(HomeModule module2, Provider<Context> contextProvider2, Provider<HomeInteractorContract> homeInteractorContractProvider2, Provider<AwardValueBuilder> awardValueBuilderProvider2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            if ($assertionsDisabled || contextProvider2 != null) {
                this.contextProvider = contextProvider2;
                if ($assertionsDisabled || homeInteractorContractProvider2 != null) {
                    this.homeInteractorContractProvider = homeInteractorContractProvider2;
                    if ($assertionsDisabled || awardValueBuilderProvider2 != null) {
                        this.awardValueBuilderProvider = awardValueBuilderProvider2;
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

    public HomePresenterContract get() {
        return (HomePresenterContract) Preconditions.checkNotNull(this.module.provideIHomePresenter(this.contextProvider.get(), this.homeInteractorContractProvider.get(), this.awardValueBuilderProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<HomePresenterContract> create(HomeModule module2, Provider<Context> contextProvider2, Provider<HomeInteractorContract> homeInteractorContractProvider2, Provider<AwardValueBuilder> awardValueBuilderProvider2) {
        return new HomeModule_ProvideIHomePresenterFactory(module2, contextProvider2, homeInteractorContractProvider2, awardValueBuilderProvider2);
    }

    public static HomePresenterContract proxyProvideIHomePresenter(HomeModule instance, Context context, HomeInteractorContract homeInteractorContract, AwardValueBuilder awardValueBuilder) {
        return instance.provideIHomePresenter(context, homeInteractorContract, awardValueBuilder);
    }
}
