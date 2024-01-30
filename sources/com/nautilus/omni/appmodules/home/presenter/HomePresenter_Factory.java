package com.nautilus.omni.appmodules.home.presenter;

import android.content.Context;
import com.nautilus.omni.appmodules.awards.AwardValueBuilder;
import com.nautilus.omni.appmodules.home.domain.interactors.HomeInteractorContract;
import dagger.MembersInjector;
import dagger.internal.Factory;
import dagger.internal.MembersInjectors;
import javax.inject.Provider;

public final class HomePresenter_Factory implements Factory<HomePresenter> {
    static final /* synthetic */ boolean $assertionsDisabled = (!HomePresenter_Factory.class.desiredAssertionStatus());
    private final Provider<AwardValueBuilder> awardValueBuilderProvider;
    private final Provider<Context> contextProvider;
    private final Provider<HomeInteractorContract> homeInteractorContractProvider;
    private final MembersInjector<HomePresenter> homePresenterMembersInjector;

    public HomePresenter_Factory(MembersInjector<HomePresenter> homePresenterMembersInjector2, Provider<Context> contextProvider2, Provider<HomeInteractorContract> homeInteractorContractProvider2, Provider<AwardValueBuilder> awardValueBuilderProvider2) {
        if ($assertionsDisabled || homePresenterMembersInjector2 != null) {
            this.homePresenterMembersInjector = homePresenterMembersInjector2;
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

    public HomePresenter get() {
        return (HomePresenter) MembersInjectors.injectMembers(this.homePresenterMembersInjector, new HomePresenter(this.contextProvider.get(), this.homeInteractorContractProvider.get(), this.awardValueBuilderProvider.get()));
    }

    public static Factory<HomePresenter> create(MembersInjector<HomePresenter> homePresenterMembersInjector2, Provider<Context> contextProvider2, Provider<HomeInteractorContract> homeInteractorContractProvider2, Provider<AwardValueBuilder> awardValueBuilderProvider2) {
        return new HomePresenter_Factory(homePresenterMembersInjector2, contextProvider2, homeInteractorContractProvider2, awardValueBuilderProvider2);
    }
}
