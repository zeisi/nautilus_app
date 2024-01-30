package com.nautilus.omni.dependencyinjection.modules.workoutdetail;

import com.nautilus.omni.appmodules.journal.view.day.workoutdetail.WorkoutDetailViewContract;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class WorkoutDetailModule_ProvidesWorkoutDetailViewFactory implements Factory<WorkoutDetailViewContract> {
    static final /* synthetic */ boolean $assertionsDisabled = (!WorkoutDetailModule_ProvidesWorkoutDetailViewFactory.class.desiredAssertionStatus());
    private final WorkoutDetailModule module;

    public WorkoutDetailModule_ProvidesWorkoutDetailViewFactory(WorkoutDetailModule module2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            return;
        }
        throw new AssertionError();
    }

    public WorkoutDetailViewContract get() {
        return (WorkoutDetailViewContract) Preconditions.checkNotNull(this.module.providesWorkoutDetailView(), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<WorkoutDetailViewContract> create(WorkoutDetailModule module2) {
        return new WorkoutDetailModule_ProvidesWorkoutDetailViewFactory(module2);
    }

    public static WorkoutDetailViewContract proxyProvidesWorkoutDetailView(WorkoutDetailModule instance) {
        return instance.providesWorkoutDetailView();
    }
}
