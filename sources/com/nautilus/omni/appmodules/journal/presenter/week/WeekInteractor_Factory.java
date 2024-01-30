package com.nautilus.omni.appmodules.journal.presenter.week;

import android.content.Context;
import com.nautilus.omni.dataaccess.WorkoutDaoHelper;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class WeekInteractor_Factory implements Factory<WeekInteractor> {
    static final /* synthetic */ boolean $assertionsDisabled = (!WeekInteractor_Factory.class.desiredAssertionStatus());
    private final Provider<Context> contextProvider;
    private final Provider<WorkoutDaoHelper> workoutDaoHelperProvider;

    public WeekInteractor_Factory(Provider<Context> contextProvider2, Provider<WorkoutDaoHelper> workoutDaoHelperProvider2) {
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

    public WeekInteractor get() {
        return new WeekInteractor(this.contextProvider.get(), this.workoutDaoHelperProvider.get());
    }

    public static Factory<WeekInteractor> create(Provider<Context> contextProvider2, Provider<WorkoutDaoHelper> workoutDaoHelperProvider2) {
        return new WeekInteractor_Factory(contextProvider2, workoutDaoHelperProvider2);
    }
}
