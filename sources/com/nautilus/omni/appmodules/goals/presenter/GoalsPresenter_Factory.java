package com.nautilus.omni.appmodules.goals.presenter;

import android.content.Context;
import com.nautilus.omni.appmodules.goals.presenter.interactors.LoadGoalsInteractor;
import com.nautilus.omni.appmodules.goals.presenter.interactors.SaveGoalsInteractor;
import dagger.MembersInjector;
import dagger.internal.Factory;
import dagger.internal.MembersInjectors;
import javax.inject.Provider;

public final class GoalsPresenter_Factory implements Factory<GoalsPresenter> {
    static final /* synthetic */ boolean $assertionsDisabled = (!GoalsPresenter_Factory.class.desiredAssertionStatus());
    private final Provider<Context> contextProvider;
    private final Provider<LoadGoalsInteractor> goalsInteractorProvider;
    private final MembersInjector<GoalsPresenter> goalsPresenterMembersInjector;
    private final Provider<SaveGoalsInteractor> saveGoalsInteractorProvider;

    public GoalsPresenter_Factory(MembersInjector<GoalsPresenter> goalsPresenterMembersInjector2, Provider<Context> contextProvider2, Provider<LoadGoalsInteractor> goalsInteractorProvider2, Provider<SaveGoalsInteractor> saveGoalsInteractorProvider2) {
        if ($assertionsDisabled || goalsPresenterMembersInjector2 != null) {
            this.goalsPresenterMembersInjector = goalsPresenterMembersInjector2;
            if ($assertionsDisabled || contextProvider2 != null) {
                this.contextProvider = contextProvider2;
                if ($assertionsDisabled || goalsInteractorProvider2 != null) {
                    this.goalsInteractorProvider = goalsInteractorProvider2;
                    if ($assertionsDisabled || saveGoalsInteractorProvider2 != null) {
                        this.saveGoalsInteractorProvider = saveGoalsInteractorProvider2;
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

    public GoalsPresenter get() {
        return (GoalsPresenter) MembersInjectors.injectMembers(this.goalsPresenterMembersInjector, new GoalsPresenter(this.contextProvider.get(), this.goalsInteractorProvider.get(), this.saveGoalsInteractorProvider.get()));
    }

    public static Factory<GoalsPresenter> create(MembersInjector<GoalsPresenter> goalsPresenterMembersInjector2, Provider<Context> contextProvider2, Provider<LoadGoalsInteractor> goalsInteractorProvider2, Provider<SaveGoalsInteractor> saveGoalsInteractorProvider2) {
        return new GoalsPresenter_Factory(goalsPresenterMembersInjector2, contextProvider2, goalsInteractorProvider2, saveGoalsInteractorProvider2);
    }
}
