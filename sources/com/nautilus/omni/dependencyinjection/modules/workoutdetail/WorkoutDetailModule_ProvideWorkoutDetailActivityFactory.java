package com.nautilus.omni.dependencyinjection.modules.workoutdetail;

import com.nautilus.omni.appmodules.journal.view.day.workoutdetail.WorkoutDetailActivity;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class WorkoutDetailModule_ProvideWorkoutDetailActivityFactory implements Factory<WorkoutDetailActivity> {
    static final /* synthetic */ boolean $assertionsDisabled = (!WorkoutDetailModule_ProvideWorkoutDetailActivityFactory.class.desiredAssertionStatus());
    private final WorkoutDetailModule module;

    public WorkoutDetailModule_ProvideWorkoutDetailActivityFactory(WorkoutDetailModule module2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            return;
        }
        throw new AssertionError();
    }

    public WorkoutDetailActivity get() {
        return (WorkoutDetailActivity) Preconditions.checkNotNull(this.module.provideWorkoutDetailActivity(), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<WorkoutDetailActivity> create(WorkoutDetailModule module2) {
        return new WorkoutDetailModule_ProvideWorkoutDetailActivityFactory(module2);
    }

    public static WorkoutDetailActivity proxyProvideWorkoutDetailActivity(WorkoutDetailModule instance) {
        return instance.provideWorkoutDetailActivity();
    }
}
