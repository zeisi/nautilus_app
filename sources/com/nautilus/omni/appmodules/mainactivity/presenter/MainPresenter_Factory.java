package com.nautilus.omni.appmodules.mainactivity.presenter;

import android.content.Context;
import com.nautilus.omni.appmodules.mainactivity.view.MainViewContract;
import dagger.MembersInjector;
import dagger.internal.Factory;
import dagger.internal.MembersInjectors;
import javax.inject.Provider;

public final class MainPresenter_Factory implements Factory<MainPresenter> {
    static final /* synthetic */ boolean $assertionsDisabled = (!MainPresenter_Factory.class.desiredAssertionStatus());
    private final Provider<Context> contextProvider;
    private final Provider<GoalsReminderToastViewBuilder> goalsReminderToastViewBuilderProvider;
    private final Provider<MainGoalsHelperInteractor> mainGoalsHelperInteractorProvider;
    private final MembersInjector<MainPresenter> mainPresenterMembersInjector;
    private final Provider<MainViewContract> mainViewProvider;

    public MainPresenter_Factory(MembersInjector<MainPresenter> mainPresenterMembersInjector2, Provider<Context> contextProvider2, Provider<MainViewContract> mainViewProvider2, Provider<MainGoalsHelperInteractor> mainGoalsHelperInteractorProvider2, Provider<GoalsReminderToastViewBuilder> goalsReminderToastViewBuilderProvider2) {
        if ($assertionsDisabled || mainPresenterMembersInjector2 != null) {
            this.mainPresenterMembersInjector = mainPresenterMembersInjector2;
            if ($assertionsDisabled || contextProvider2 != null) {
                this.contextProvider = contextProvider2;
                if ($assertionsDisabled || mainViewProvider2 != null) {
                    this.mainViewProvider = mainViewProvider2;
                    if ($assertionsDisabled || mainGoalsHelperInteractorProvider2 != null) {
                        this.mainGoalsHelperInteractorProvider = mainGoalsHelperInteractorProvider2;
                        if ($assertionsDisabled || goalsReminderToastViewBuilderProvider2 != null) {
                            this.goalsReminderToastViewBuilderProvider = goalsReminderToastViewBuilderProvider2;
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
        throw new AssertionError();
    }

    public MainPresenter get() {
        return (MainPresenter) MembersInjectors.injectMembers(this.mainPresenterMembersInjector, new MainPresenter(this.contextProvider.get(), this.mainViewProvider.get(), this.mainGoalsHelperInteractorProvider.get(), this.goalsReminderToastViewBuilderProvider.get()));
    }

    public static Factory<MainPresenter> create(MembersInjector<MainPresenter> mainPresenterMembersInjector2, Provider<Context> contextProvider2, Provider<MainViewContract> mainViewProvider2, Provider<MainGoalsHelperInteractor> mainGoalsHelperInteractorProvider2, Provider<GoalsReminderToastViewBuilder> goalsReminderToastViewBuilderProvider2) {
        return new MainPresenter_Factory(mainPresenterMembersInjector2, contextProvider2, mainViewProvider2, mainGoalsHelperInteractorProvider2, goalsReminderToastViewBuilderProvider2);
    }
}
