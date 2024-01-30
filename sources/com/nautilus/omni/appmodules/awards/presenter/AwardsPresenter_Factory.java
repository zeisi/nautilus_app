package com.nautilus.omni.appmodules.awards.presenter;

import android.content.Context;
import com.nautilus.omni.appmodules.awards.interactor.AwardsInteractorContract;
import dagger.MembersInjector;
import dagger.internal.Factory;
import dagger.internal.MembersInjectors;
import javax.inject.Provider;

public final class AwardsPresenter_Factory implements Factory<AwardsPresenter> {
    static final /* synthetic */ boolean $assertionsDisabled = (!AwardsPresenter_Factory.class.desiredAssertionStatus());
    private final MembersInjector<AwardsPresenter> awardsPresenterMembersInjector;
    private final Provider<Context> contextProvider;
    private final Provider<AwardsInteractorContract> iAwardsInteractorProvider;

    public AwardsPresenter_Factory(MembersInjector<AwardsPresenter> awardsPresenterMembersInjector2, Provider<Context> contextProvider2, Provider<AwardsInteractorContract> iAwardsInteractorProvider2) {
        if ($assertionsDisabled || awardsPresenterMembersInjector2 != null) {
            this.awardsPresenterMembersInjector = awardsPresenterMembersInjector2;
            if ($assertionsDisabled || contextProvider2 != null) {
                this.contextProvider = contextProvider2;
                if ($assertionsDisabled || iAwardsInteractorProvider2 != null) {
                    this.iAwardsInteractorProvider = iAwardsInteractorProvider2;
                    return;
                }
                throw new AssertionError();
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public AwardsPresenter get() {
        return (AwardsPresenter) MembersInjectors.injectMembers(this.awardsPresenterMembersInjector, new AwardsPresenter(this.contextProvider.get(), this.iAwardsInteractorProvider.get()));
    }

    public static Factory<AwardsPresenter> create(MembersInjector<AwardsPresenter> awardsPresenterMembersInjector2, Provider<Context> contextProvider2, Provider<AwardsInteractorContract> iAwardsInteractorProvider2) {
        return new AwardsPresenter_Factory(awardsPresenterMembersInjector2, contextProvider2, iAwardsInteractorProvider2);
    }
}
