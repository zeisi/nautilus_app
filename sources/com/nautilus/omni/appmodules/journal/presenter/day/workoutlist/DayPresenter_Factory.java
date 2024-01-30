package com.nautilus.omni.appmodules.journal.presenter.day.workoutlist;

import android.content.Context;
import dagger.MembersInjector;
import dagger.internal.Factory;
import dagger.internal.MembersInjectors;
import javax.inject.Provider;

public final class DayPresenter_Factory implements Factory<DayPresenter> {
    static final /* synthetic */ boolean $assertionsDisabled = (!DayPresenter_Factory.class.desiredAssertionStatus());
    private final Provider<Context> contextProvider;
    private final Provider<DayInteractor> dayInteractorProvider;
    private final MembersInjector<DayPresenter> dayPresenterMembersInjector;

    public DayPresenter_Factory(MembersInjector<DayPresenter> dayPresenterMembersInjector2, Provider<Context> contextProvider2, Provider<DayInteractor> dayInteractorProvider2) {
        if ($assertionsDisabled || dayPresenterMembersInjector2 != null) {
            this.dayPresenterMembersInjector = dayPresenterMembersInjector2;
            if ($assertionsDisabled || contextProvider2 != null) {
                this.contextProvider = contextProvider2;
                if ($assertionsDisabled || dayInteractorProvider2 != null) {
                    this.dayInteractorProvider = dayInteractorProvider2;
                    return;
                }
                throw new AssertionError();
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public DayPresenter get() {
        return (DayPresenter) MembersInjectors.injectMembers(this.dayPresenterMembersInjector, new DayPresenter(this.contextProvider.get(), this.dayInteractorProvider.get()));
    }

    public static Factory<DayPresenter> create(MembersInjector<DayPresenter> dayPresenterMembersInjector2, Provider<Context> contextProvider2, Provider<DayInteractor> dayInteractorProvider2) {
        return new DayPresenter_Factory(dayPresenterMembersInjector2, contextProvider2, dayInteractorProvider2);
    }
}
