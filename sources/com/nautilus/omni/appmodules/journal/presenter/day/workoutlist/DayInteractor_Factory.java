package com.nautilus.omni.appmodules.journal.presenter.day.workoutlist;

import android.content.Context;
import com.nautilus.omni.dataaccess.AchievementsDaoHelper;
import com.nautilus.omni.dataaccess.WorkoutDaoHelper;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class DayInteractor_Factory implements Factory<DayInteractor> {
    static final /* synthetic */ boolean $assertionsDisabled = (!DayInteractor_Factory.class.desiredAssertionStatus());
    private final Provider<AchievementsDaoHelper> achievementsDaoHelperProvider;
    private final Provider<Context> contextProvider;
    private final Provider<WorkoutDaoHelper> workoutDaoHelperProvider;

    public DayInteractor_Factory(Provider<Context> contextProvider2, Provider<WorkoutDaoHelper> workoutDaoHelperProvider2, Provider<AchievementsDaoHelper> achievementsDaoHelperProvider2) {
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

    public DayInteractor get() {
        return new DayInteractor(this.contextProvider.get(), this.workoutDaoHelperProvider.get(), this.achievementsDaoHelperProvider.get());
    }

    public static Factory<DayInteractor> create(Provider<Context> contextProvider2, Provider<WorkoutDaoHelper> workoutDaoHelperProvider2, Provider<AchievementsDaoHelper> achievementsDaoHelperProvider2) {
        return new DayInteractor_Factory(contextProvider2, workoutDaoHelperProvider2, achievementsDaoHelperProvider2);
    }
}
