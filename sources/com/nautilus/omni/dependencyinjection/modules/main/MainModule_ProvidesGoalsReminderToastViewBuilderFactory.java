package com.nautilus.omni.dependencyinjection.modules.main;

import com.nautilus.omni.appmodules.mainactivity.presenter.GoalsReminderToastViewBuilder;
import com.nautilus.omni.appmodules.mainactivity.view.MainActivity;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class MainModule_ProvidesGoalsReminderToastViewBuilderFactory implements Factory<GoalsReminderToastViewBuilder> {
    static final /* synthetic */ boolean $assertionsDisabled = (!MainModule_ProvidesGoalsReminderToastViewBuilderFactory.class.desiredAssertionStatus());
    private final Provider<MainActivity> mainActivityProvider;
    private final MainModule module;

    public MainModule_ProvidesGoalsReminderToastViewBuilderFactory(MainModule module2, Provider<MainActivity> mainActivityProvider2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            if ($assertionsDisabled || mainActivityProvider2 != null) {
                this.mainActivityProvider = mainActivityProvider2;
                return;
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public GoalsReminderToastViewBuilder get() {
        return (GoalsReminderToastViewBuilder) Preconditions.checkNotNull(this.module.providesGoalsReminderToastViewBuilder(this.mainActivityProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<GoalsReminderToastViewBuilder> create(MainModule module2, Provider<MainActivity> mainActivityProvider2) {
        return new MainModule_ProvidesGoalsReminderToastViewBuilderFactory(module2, mainActivityProvider2);
    }

    public static GoalsReminderToastViewBuilder proxyProvidesGoalsReminderToastViewBuilder(MainModule instance, MainActivity mainActivity) {
        return instance.providesGoalsReminderToastViewBuilder(mainActivity);
    }
}
