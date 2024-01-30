package com.nautilus.omni.dependencyinjection.modules.settings.underarmour;

import android.content.Context;
import com.nautilus.underarmourconnection.database.WorkoutTrackDaoHelper;
import com.nautilus.underarmourconnection.services.workouts.WorkoutService;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class UnderArmourModule_ProvideUnderArmourWorkoutServiceFactory implements Factory<WorkoutService> {
    static final /* synthetic */ boolean $assertionsDisabled = (!UnderArmourModule_ProvideUnderArmourWorkoutServiceFactory.class.desiredAssertionStatus());
    private final Provider<Context> contextProvider;
    private final UnderArmourModule module;
    private final Provider<WorkoutTrackDaoHelper> workoutDaoHelperProvider;

    public UnderArmourModule_ProvideUnderArmourWorkoutServiceFactory(UnderArmourModule module2, Provider<Context> contextProvider2, Provider<WorkoutTrackDaoHelper> workoutDaoHelperProvider2) {
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

    public WorkoutService get() {
        return (WorkoutService) Preconditions.checkNotNull(this.module.provideUnderArmourWorkoutService(this.contextProvider.get(), this.workoutDaoHelperProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<WorkoutService> create(UnderArmourModule module2, Provider<Context> contextProvider2, Provider<WorkoutTrackDaoHelper> workoutDaoHelperProvider2) {
        return new UnderArmourModule_ProvideUnderArmourWorkoutServiceFactory(module2, contextProvider2, workoutDaoHelperProvider2);
    }
}
