package com.nautilus.omni.appmodules.journal.presenter.week;

import android.content.Context;
import dagger.MembersInjector;
import dagger.internal.Factory;
import dagger.internal.MembersInjectors;
import javax.inject.Provider;

public final class WeekPresenter_Factory implements Factory<WeekPresenter> {
    static final /* synthetic */ boolean $assertionsDisabled = (!WeekPresenter_Factory.class.desiredAssertionStatus());
    private final Provider<Context> contextProvider;
    private final Provider<WeekInteractor> weekInteractorProvider;
    private final MembersInjector<WeekPresenter> weekPresenterMembersInjector;

    public WeekPresenter_Factory(MembersInjector<WeekPresenter> weekPresenterMembersInjector2, Provider<Context> contextProvider2, Provider<WeekInteractor> weekInteractorProvider2) {
        if ($assertionsDisabled || weekPresenterMembersInjector2 != null) {
            this.weekPresenterMembersInjector = weekPresenterMembersInjector2;
            if ($assertionsDisabled || contextProvider2 != null) {
                this.contextProvider = contextProvider2;
                if ($assertionsDisabled || weekInteractorProvider2 != null) {
                    this.weekInteractorProvider = weekInteractorProvider2;
                    return;
                }
                throw new AssertionError();
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public WeekPresenter get() {
        return (WeekPresenter) MembersInjectors.injectMembers(this.weekPresenterMembersInjector, new WeekPresenter(this.contextProvider.get(), this.weekInteractorProvider.get()));
    }

    public static Factory<WeekPresenter> create(MembersInjector<WeekPresenter> weekPresenterMembersInjector2, Provider<Context> contextProvider2, Provider<WeekInteractor> weekInteractorProvider2) {
        return new WeekPresenter_Factory(weekPresenterMembersInjector2, contextProvider2, weekInteractorProvider2);
    }
}
