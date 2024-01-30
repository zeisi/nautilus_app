package com.nautilus.omni.appmodules.home.view;

import com.nautilus.omni.appmodules.home.presenter.ThisWeekPresenterContract;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class ThisWeekSectionFragment_MembersInjector implements MembersInjector<ThisWeekSectionFragment> {
    static final /* synthetic */ boolean $assertionsDisabled = (!ThisWeekSectionFragment_MembersInjector.class.desiredAssertionStatus());
    private final Provider<ThisWeekPresenterContract> iThisWeekPresenterProvider;

    public ThisWeekSectionFragment_MembersInjector(Provider<ThisWeekPresenterContract> iThisWeekPresenterProvider2) {
        if ($assertionsDisabled || iThisWeekPresenterProvider2 != null) {
            this.iThisWeekPresenterProvider = iThisWeekPresenterProvider2;
            return;
        }
        throw new AssertionError();
    }

    public static MembersInjector<ThisWeekSectionFragment> create(Provider<ThisWeekPresenterContract> iThisWeekPresenterProvider2) {
        return new ThisWeekSectionFragment_MembersInjector(iThisWeekPresenterProvider2);
    }

    public void injectMembers(ThisWeekSectionFragment instance) {
        if (instance == null) {
            throw new NullPointerException("Cannot inject members into a null reference");
        }
        instance.iThisWeekPresenter = this.iThisWeekPresenterProvider.get();
    }

    public static void injectIThisWeekPresenter(ThisWeekSectionFragment instance, Provider<ThisWeekPresenterContract> iThisWeekPresenterProvider2) {
        instance.iThisWeekPresenter = iThisWeekPresenterProvider2.get();
    }
}
