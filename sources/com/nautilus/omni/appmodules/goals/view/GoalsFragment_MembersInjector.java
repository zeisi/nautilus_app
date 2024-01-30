package com.nautilus.omni.appmodules.goals.view;

import com.nautilus.omni.appmodules.goals.presenter.GoalsPresenter;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class GoalsFragment_MembersInjector implements MembersInjector<GoalsFragment> {
    static final /* synthetic */ boolean $assertionsDisabled = (!GoalsFragment_MembersInjector.class.desiredAssertionStatus());
    private final Provider<GoalsPresenter> mGoalsPresenterProvider;

    public GoalsFragment_MembersInjector(Provider<GoalsPresenter> mGoalsPresenterProvider2) {
        if ($assertionsDisabled || mGoalsPresenterProvider2 != null) {
            this.mGoalsPresenterProvider = mGoalsPresenterProvider2;
            return;
        }
        throw new AssertionError();
    }

    public static MembersInjector<GoalsFragment> create(Provider<GoalsPresenter> mGoalsPresenterProvider2) {
        return new GoalsFragment_MembersInjector(mGoalsPresenterProvider2);
    }

    public void injectMembers(GoalsFragment instance) {
        if (instance == null) {
            throw new NullPointerException("Cannot inject members into a null reference");
        }
        instance.mGoalsPresenter = this.mGoalsPresenterProvider.get();
    }

    public static void injectMGoalsPresenter(GoalsFragment instance, Provider<GoalsPresenter> mGoalsPresenterProvider2) {
        instance.mGoalsPresenter = mGoalsPresenterProvider2.get();
    }
}
