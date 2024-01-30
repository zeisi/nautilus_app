package com.nautilus.omni.dependencyinjection.modules.main;

import com.nautilus.omni.appmodules.goals.view.GoalsFragment;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class GoalsModule_ProvideGoalsViewFactory implements Factory<GoalsFragment> {
    static final /* synthetic */ boolean $assertionsDisabled = (!GoalsModule_ProvideGoalsViewFactory.class.desiredAssertionStatus());
    private final GoalsModule module;

    public GoalsModule_ProvideGoalsViewFactory(GoalsModule module2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            return;
        }
        throw new AssertionError();
    }

    public GoalsFragment get() {
        return (GoalsFragment) Preconditions.checkNotNull(this.module.provideGoalsView(), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<GoalsFragment> create(GoalsModule module2) {
        return new GoalsModule_ProvideGoalsViewFactory(module2);
    }

    public static GoalsFragment proxyProvideGoalsView(GoalsModule instance) {
        return instance.provideGoalsView();
    }
}
