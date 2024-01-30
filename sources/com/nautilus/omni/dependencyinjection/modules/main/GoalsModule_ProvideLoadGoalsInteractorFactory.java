package com.nautilus.omni.dependencyinjection.modules.main;

import android.content.Context;
import com.nautilus.omni.appmodules.goals.presenter.interactors.LoadGoalsInteractor;
import com.nautilus.omni.dataaccess.GoalsDaoHelper;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class GoalsModule_ProvideLoadGoalsInteractorFactory implements Factory<LoadGoalsInteractor> {
    static final /* synthetic */ boolean $assertionsDisabled = (!GoalsModule_ProvideLoadGoalsInteractorFactory.class.desiredAssertionStatus());
    private final Provider<Context> contextProvider;
    private final Provider<GoalsDaoHelper> goalsDaoHelperProvider;
    private final GoalsModule module;

    public GoalsModule_ProvideLoadGoalsInteractorFactory(GoalsModule module2, Provider<Context> contextProvider2, Provider<GoalsDaoHelper> goalsDaoHelperProvider2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
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
        throw new AssertionError();
    }

    public LoadGoalsInteractor get() {
        return (LoadGoalsInteractor) Preconditions.checkNotNull(this.module.provideLoadGoalsInteractor(this.contextProvider.get(), this.goalsDaoHelperProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<LoadGoalsInteractor> create(GoalsModule module2, Provider<Context> contextProvider2, Provider<GoalsDaoHelper> goalsDaoHelperProvider2) {
        return new GoalsModule_ProvideLoadGoalsInteractorFactory(module2, contextProvider2, goalsDaoHelperProvider2);
    }

    public static LoadGoalsInteractor proxyProvideLoadGoalsInteractor(GoalsModule instance, Context context, GoalsDaoHelper goalsDaoHelper) {
        return instance.provideLoadGoalsInteractor(context, goalsDaoHelper);
    }
}
