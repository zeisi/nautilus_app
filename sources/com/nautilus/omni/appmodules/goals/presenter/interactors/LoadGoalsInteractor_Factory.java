package com.nautilus.omni.appmodules.goals.presenter.interactors;

import android.content.Context;
import com.nautilus.omni.dataaccess.GoalsDaoHelper;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class LoadGoalsInteractor_Factory implements Factory<LoadGoalsInteractor> {
    static final /* synthetic */ boolean $assertionsDisabled = (!LoadGoalsInteractor_Factory.class.desiredAssertionStatus());
    private final Provider<Context> contextProvider;
    private final Provider<GoalsDaoHelper> goalsDaoHelperProvider;

    public LoadGoalsInteractor_Factory(Provider<Context> contextProvider2, Provider<GoalsDaoHelper> goalsDaoHelperProvider2) {
        if ($assertionsDisabled || contextProvider2 != null) {
            this.contextProvider = contextProvider2;
            if ($assertionsDisabled || goalsDaoHelperProvider2 != null) {
                this.goalsDaoHelperProvider = goalsDaoHelperProvider2;
                return;
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public LoadGoalsInteractor get() {
        return new LoadGoalsInteractor(this.contextProvider.get(), this.goalsDaoHelperProvider.get());
    }

    public static Factory<LoadGoalsInteractor> create(Provider<Context> contextProvider2, Provider<GoalsDaoHelper> goalsDaoHelperProvider2) {
        return new LoadGoalsInteractor_Factory(contextProvider2, goalsDaoHelperProvider2);
    }
}
