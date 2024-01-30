package com.nautilus.omni.dependencyinjection.modules.main;

import android.content.Context;
import com.nautilus.omni.appmodules.journal.presenter.year.YearInteractor;
import com.nautilus.omni.dataaccess.WorkoutDaoHelper;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class JournalModule_ProvideYearInteractorFactory implements Factory<YearInteractor> {
    static final /* synthetic */ boolean $assertionsDisabled = (!JournalModule_ProvideYearInteractorFactory.class.desiredAssertionStatus());
    private final Provider<Context> contextProvider;
    private final JournalModule module;
    private final Provider<WorkoutDaoHelper> workoutDaoHelperProvider;

    public JournalModule_ProvideYearInteractorFactory(JournalModule module2, Provider<Context> contextProvider2, Provider<WorkoutDaoHelper> workoutDaoHelperProvider2) {
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

    public YearInteractor get() {
        return (YearInteractor) Preconditions.checkNotNull(this.module.provideYearInteractor(this.contextProvider.get(), this.workoutDaoHelperProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<YearInteractor> create(JournalModule module2, Provider<Context> contextProvider2, Provider<WorkoutDaoHelper> workoutDaoHelperProvider2) {
        return new JournalModule_ProvideYearInteractorFactory(module2, contextProvider2, workoutDaoHelperProvider2);
    }

    public static YearInteractor proxyProvideYearInteractor(JournalModule instance, Context context, WorkoutDaoHelper workoutDaoHelper) {
        return instance.provideYearInteractor(context, workoutDaoHelper);
    }
}
