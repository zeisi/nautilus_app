package com.nautilus.omni.appmodules.journal.presenter.year;

import android.content.Context;
import com.nautilus.omni.dataaccess.WorkoutDaoHelper;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class YearInteractor_Factory implements Factory<YearInteractor> {
    static final /* synthetic */ boolean $assertionsDisabled = (!YearInteractor_Factory.class.desiredAssertionStatus());
    private final Provider<Context> contextProvider;
    private final Provider<WorkoutDaoHelper> workoutDaoHelperProvider;

    public YearInteractor_Factory(Provider<Context> contextProvider2, Provider<WorkoutDaoHelper> workoutDaoHelperProvider2) {
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

    public YearInteractor get() {
        return new YearInteractor(this.contextProvider.get(), this.workoutDaoHelperProvider.get());
    }

    public static Factory<YearInteractor> create(Provider<Context> contextProvider2, Provider<WorkoutDaoHelper> workoutDaoHelperProvider2) {
        return new YearInteractor_Factory(contextProvider2, workoutDaoHelperProvider2);
    }
}
