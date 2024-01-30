package com.nautilus.omni.appmodules.awardtypes.presenter;

import android.content.Context;
import com.nautilus.omni.appmodules.awardtypes.interactor.AwardsTypesInteractorContract;
import com.nautilus.omni.appmodules.awardtypes.view.AwardTypesActivityContract;
import dagger.MembersInjector;
import dagger.internal.Factory;
import dagger.internal.MembersInjectors;
import javax.inject.Provider;

public final class AwardTypesPresenter_Factory implements Factory<AwardTypesPresenter> {
    static final /* synthetic */ boolean $assertionsDisabled = (!AwardTypesPresenter_Factory.class.desiredAssertionStatus());
    private final MembersInjector<AwardTypesPresenter> awardTypesPresenterMembersInjector;
    private final Provider<Context> contextProvider;
    private final Provider<AwardTypesActivityContract> iAwardTypesActivityProvider;
    private final Provider<AwardsTypesInteractorContract> iAwardsInteractorProvider;

    public AwardTypesPresenter_Factory(MembersInjector<AwardTypesPresenter> awardTypesPresenterMembersInjector2, Provider<Context> contextProvider2, Provider<AwardsTypesInteractorContract> iAwardsInteractorProvider2, Provider<AwardTypesActivityContract> iAwardTypesActivityProvider2) {
        if ($assertionsDisabled || awardTypesPresenterMembersInjector2 != null) {
            this.awardTypesPresenterMembersInjector = awardTypesPresenterMembersInjector2;
            if ($assertionsDisabled || contextProvider2 != null) {
                this.contextProvider = contextProvider2;
                if ($assertionsDisabled || iAwardsInteractorProvider2 != null) {
                    this.iAwardsInteractorProvider = iAwardsInteractorProvider2;
                    if ($assertionsDisabled || iAwardTypesActivityProvider2 != null) {
                        this.iAwardTypesActivityProvider = iAwardTypesActivityProvider2;
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

    public AwardTypesPresenter get() {
        return (AwardTypesPresenter) MembersInjectors.injectMembers(this.awardTypesPresenterMembersInjector, new AwardTypesPresenter(this.contextProvider.get(), this.iAwardsInteractorProvider.get(), this.iAwardTypesActivityProvider.get()));
    }

    public static Factory<AwardTypesPresenter> create(MembersInjector<AwardTypesPresenter> awardTypesPresenterMembersInjector2, Provider<Context> contextProvider2, Provider<AwardsTypesInteractorContract> iAwardsInteractorProvider2, Provider<AwardTypesActivityContract> iAwardTypesActivityProvider2) {
        return new AwardTypesPresenter_Factory(awardTypesPresenterMembersInjector2, contextProvider2, iAwardsInteractorProvider2, iAwardTypesActivityProvider2);
    }
}
