package com.nautilus.omni.dependencyinjection.modules.main;

import android.content.Context;
import com.nautilus.omni.appmodules.journal.presenter.week.WeekInteractor;
import com.nautilus.omni.dataaccess.WorkoutDaoHelper;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class JournalModule_ProvideWeekInteractorFactory implements Factory<WeekInteractor> {
    static final /* synthetic */ boolean $assertionsDisabled = (!JournalModule_ProvideWeekInteractorFactory.class.desiredAssertionStatus());
    private final Provider<Context> contextProvider;
    private final JournalModule module;
    private final Provider<WorkoutDaoHelper> workoutDaoHelperProvider;

    public JournalModule_ProvideWeekInteractorFactory(JournalModule module2, Provider<Context> contextProvider2, Provider<WorkoutDaoHelper> workoutDaoHelperProvider2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            if ($assertionsDisabled || contextProvider2 != null) {
                this.contextProvider = contextProvider2;
                if ($assertionsDisabled || workoutDaoHelperProvider2 != null) {
                    this.workoutDaoHelperProvider = workoutDaoHelperProvider2;
                    return;
                }
                throw new AssertionError();
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public WeekInteractor get() {
        return (WeekInteractor) Preconditions.checkNotNull(this.module.provideWeekInteractor(this.contextProvider.get(), this.workoutDaoHelperProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<WeekInteractor> create(JournalModule module2, Provider<Context> contextProvider2, Provider<WorkoutDaoHelper> workoutDaoHelperProvider2) {
        return new JournalModule_ProvideWeekInteractorFactory(module2, contextProvider2, workoutDaoHelperProvider2);
    }

    public static WeekInteractor proxyProvideWeekInteractor(JournalModule instance, Context context, WorkoutDaoHelper workoutDaoHelper) {
        return instance.provideWeekInteractor(context, workoutDaoHelper);
    }
}
