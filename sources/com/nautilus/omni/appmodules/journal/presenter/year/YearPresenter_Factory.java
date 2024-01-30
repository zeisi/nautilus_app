package com.nautilus.omni.appmodules.journal.presenter.year;

import android.content.Context;
import dagger.MembersInjector;
import dagger.internal.Factory;
import dagger.internal.MembersInjectors;
import javax.inject.Provider;

public final class YearPresenter_Factory implements Factory<YearPresenter> {
    static final /* synthetic */ boolean $assertionsDisabled = (!YearPresenter_Factory.class.desiredAssertionStatus());
    private final Provider<Context> contextProvider;
    private final Provider<YearInteractor> yearInteractorProvider;
    private final MembersInjector<YearPresenter> yearPresenterMembersInjector;

    public YearPresenter_Factory(MembersInjector<YearPresenter> yearPresenterMembersInjector2, Provider<Context> contextProvider2, Provider<YearInteractor> yearInteractorProvider2) {
        if ($assertionsDisabled || yearPresenterMembersInjector2 != null) {
            this.yearPresenterMembersInjector = yearPresenterMembersInjector2;
            if ($assertionsDisabled || contextProvider2 != null) {
                this.contextProvider = contextProvider2;
                if ($assertionsDisabled || yearInteractorProvider2 != null) {
                    this.yearInteractorProvider = yearInteractorProvider2;
                    return;
                }
                throw new AssertionError();
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public YearPresenter get() {
        return (YearPresenter) MembersInjectors.injectMembers(this.yearPresenterMembersInjector, new YearPresenter(this.contextProvider.get(), this.yearInteractorProvider.get()));
    }

    public static Factory<YearPresenter> create(MembersInjector<YearPresenter> yearPresenterMembersInjector2, Provider<Context> contextProvider2, Provider<YearInteractor> yearInteractorProvider2) {
        return new YearPresenter_Factory(yearPresenterMembersInjector2, contextProvider2, yearInteractorProvider2);
    }
}
