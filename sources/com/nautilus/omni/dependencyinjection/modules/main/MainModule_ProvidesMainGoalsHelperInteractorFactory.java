package com.nautilus.omni.dependencyinjection.modules.main;

import android.content.Context;
import com.nautilus.omni.appmodules.goals.presenter.interactors.SaveGoalsInteractor;
import com.nautilus.omni.appmodules.mainactivity.presenter.MainGoalsHelperInteractor;
import com.nautilus.omni.dataaccess.GoalsDaoHelper;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class MainModule_ProvidesMainGoalsHelperInteractorFactory implements Factory<MainGoalsHelperInteractor> {
    static final /* synthetic */ boolean $assertionsDisabled = (!MainModule_ProvidesMainGoalsHelperInteractorFactory.class.desiredAssertionStatus());
    private final Provider<Context> contextProvider;
    private final Provider<GoalsDaoHelper> goalsDaoHelperProvider;
    private final MainModule module;
    private final Provider<SaveGoalsInteractor> saveGoalsInteractorProvider;

    public MainModule_ProvidesMainGoalsHelperInteractorFactory(MainModule module2, Provider<Context> contextProvider2, Provider<SaveGoalsInteractor> saveGoalsInteractorProvider2, Provider<GoalsDaoHelper> goalsDaoHelperProvider2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            if ($assertionsDisabled || contextProvider2 != null) {
                this.contextProvider = contextProvider2;
                if ($assertionsDisabled || saveGoalsInteractorProvider2 != null) {
                    this.saveGoalsInteractorProvider = saveGoalsInteractorProvider2;
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
        throw new AssertionError();
    }

    public MainGoalsHelperInteractor get() {
        return (MainGoalsHelperInteractor) Preconditions.checkNotNull(this.module.providesMainGoalsHelperInteractor(this.contextProvider.get(), this.saveGoalsInteractorProvider.get(), this.goalsDaoHelperProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<MainGoalsHelperInteractor> create(MainModule module2, Provider<Context> contextProvider2, Provider<SaveGoalsInteractor> saveGoalsInteractorProvider2, Provider<GoalsDaoHelper> goalsDaoHelperProvider2) {
        return new MainModule_ProvidesMainGoalsHelperInteractorFactory(module2, contextProvider2, saveGoalsInteractorProvider2, goalsDaoHelperProvider2);
    }

    public static MainGoalsHelperInteractor proxyProvidesMainGoalsHelperInteractor(MainModule instance, Context context, SaveGoalsInteractor saveGoalsInteractor, GoalsDaoHelper goalsDaoHelper) {
        return instance.providesMainGoalsHelperInteractor(context, saveGoalsInteractor, goalsDaoHelper);
    }
}
