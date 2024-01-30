package com.nautilus.omni.dependencyinjection.modules.workoutdetail;

import com.nautilus.omni.appmodules.journal.view.day.workoutdetail.WorkoutDetailActivity;
import com.nautilus.omni.permissions.PermissionAction;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class WorkoutDetailModule_ProvidePermissionActionFactory implements Factory<PermissionAction> {
    static final /* synthetic */ boolean $assertionsDisabled = (!WorkoutDetailModule_ProvidePermissionActionFactory.class.desiredAssertionStatus());
    private final WorkoutDetailModule module;
    private final Provider<WorkoutDetailActivity> workoutDetailActivityProvider;

    public WorkoutDetailModule_ProvidePermissionActionFactory(WorkoutDetailModule module2, Provider<WorkoutDetailActivity> workoutDetailActivityProvider2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            if ($assertionsDisabled || workoutDetailActivityProvider2 != null) {
                this.workoutDetailActivityProvider = workoutDetailActivityProvider2;
                return;
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public PermissionAction get() {
        return (PermissionAction) Preconditions.checkNotNull(this.module.providePermissionAction(this.workoutDetailActivityProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<PermissionAction> create(WorkoutDetailModule module2, Provider<WorkoutDetailActivity> workoutDetailActivityProvider2) {
        return new WorkoutDetailModule_ProvidePermissionActionFactory(module2, workoutDetailActivityProvider2);
    }

    public static PermissionAction proxyProvidePermissionAction(WorkoutDetailModule instance, WorkoutDetailActivity workoutDetailActivity) {
        return instance.providePermissionAction(workoutDetailActivity);
    }
}
