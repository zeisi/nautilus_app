package com.nautilus.omni.dependencyinjection.modules.main;

import android.content.Context;
import com.nautilus.omni.appmodules.journal.presenter.day.workoutlist.DayInteractor;
import com.nautilus.omni.dataaccess.AchievementsDaoHelper;
import com.nautilus.omni.dataaccess.WorkoutDaoHelper;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class JournalModule_ProvideDayInteractorFactory implements Factory<DayInteractor> {
    static final /* synthetic */ boolean $assertionsDisabled = (!JournalModule_ProvideDayInteractorFactory.class.desiredAssertionStatus());
    private final Provider<AchievementsDaoHelper> achievementsDaoHelperProvider;
    private final Provider<Context> contextProvider;
    private final JournalModule module;
    private final Provider<WorkoutDaoHelper> workoutDaoHelperProvider;

    public JournalModule_ProvideDayInteractorFactory(JournalModule module2, Provider<Context> contextProvider2, Provider<WorkoutDaoHelper> workoutDaoHelperProvider2, Provider<AchievementsDaoHelper> achievementsDaoHelperProvider2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            if ($assertionsDisabled || contextProvider2 != null) {
                this.contextProvider = contextProvider2;
                if ($assertionsDisabled || workoutDaoHelperProvider2 != null) {
                    this.workoutDaoHelperProvider = workoutDaoHelperProvider2;
                    if ($assertionsDisabled || achievementsDaoHelperProvider2 != null) {
                        this.achievementsDaoHelperProvider = achievementsDaoHelperProvider2;
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

    public DayInteractor get() {
        return (DayInteractor) Preconditions.checkNotNull(this.module.provideDayInteractor(this.contextProvider.get(), this.workoutDaoHelperProvider.get(), this.achievementsDaoHelperProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<DayInteractor> create(JournalModule module2, Provider<Context> contextProvider2, Provider<WorkoutDaoHelper> workoutDaoHelperProvider2, Provider<AchievementsDaoHelper> achievementsDaoHelperProvider2) {
        return new JournalModule_ProvideDayInteractorFactory(module2, contextProvider2, workoutDaoHelperProvider2, achievementsDaoHelperProvider2);
    }

    public static DayInteractor proxyProvideDayInteractor(JournalModule instance, Context context, WorkoutDaoHelper workoutDaoHelper, AchievementsDaoHelper achievementsDaoHelper) {
        return instance.provideDayInteractor(context, workoutDaoHelper, achievementsDaoHelper);
    }
}
