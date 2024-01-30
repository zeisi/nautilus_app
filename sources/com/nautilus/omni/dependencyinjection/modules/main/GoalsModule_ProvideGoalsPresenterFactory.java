package com.nautilus.omni.dependencyinjection.modules.main;

import android.content.Context;
import com.nautilus.omni.appmodules.goals.presenter.GoalsPresenter;
import com.nautilus.omni.appmodules.goals.presenter.interactors.LoadGoalsInteractor;
import com.nautilus.omni.appmodules.goals.presenter.interactors.SaveGoalsInteractor;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class GoalsModule_ProvideGoalsPresenterFactory implements Factory<GoalsPresenter> {
    static final /* synthetic */ boolean $assertionsDisabled = (!GoalsModule_ProvideGoalsPresenterFactory.class.desiredAssertionStatus());
    private final Provider<Context> contextProvider;
    private final Provider<LoadGoalsInteractor> goalsInteractorProvider;
    private final GoalsModule module;
    private final Provider<SaveGoalsInteractor> saveGoalsInteractorProvider;

    public GoalsModule_ProvideGoalsPresenterFactory(GoalsModule module2, Provider<Context> contextProvider2, Provider<LoadGoalsInteractor> goalsInteractorProvider2, Provider<SaveGoalsInteractor> saveGoalsInteractorProvider2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
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
        return (GoalsPresenter) Preconditions.checkNotNull(this.module.provideGoalsPresenter(this.contextProvider.get(), this.goalsInteractorProvider.get(), this.saveGoalsInteractorProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<GoalsPresenter> create(GoalsModule module2, Provider<Context> contextProvider2, Provider<LoadGoalsInteractor> goalsInteractorProvider2, Provider<SaveGoalsInteractor> saveGoalsInteractorProvider2) {
        return new GoalsModule_ProvideGoalsPresenterFactory(module2, contextProvider2, goalsInteractorProvider2, saveGoalsInteractorProvider2);
    }

    public static GoalsPresenter proxyProvideGoalsPresenter(GoalsModule instance, Context context, LoadGoalsInteractor goalsInteractor, SaveGoalsInteractor saveGoalsInteractor) {
        return instance.provideGoalsPresenter(context, goalsInteractor, saveGoalsInteractor);
    }
}
