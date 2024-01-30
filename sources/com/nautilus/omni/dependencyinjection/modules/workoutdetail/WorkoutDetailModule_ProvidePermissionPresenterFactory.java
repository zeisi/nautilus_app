package com.nautilus.omni.dependencyinjection.modules.workoutdetail;

import com.nautilus.omni.appmodules.journal.view.day.workoutdetail.WorkoutDetailActivity;
import com.nautilus.omni.permissions.PermissionAction;
import com.nautilus.omni.permissions.PermissionPresenter;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class WorkoutDetailModule_ProvidePermissionPresenterFactory implements Factory<PermissionPresenter> {
    static final /* synthetic */ boolean $assertionsDisabled = (!WorkoutDetailModule_ProvidePermissionPresenterFactory.class.desiredAssertionStatus());
    private final WorkoutDetailModule module;
    private final Provider<PermissionAction> permissionActionProvider;
    private final Provider<WorkoutDetailActivity> workoutDetailActivityProvider;

    public WorkoutDetailModule_ProvidePermissionPresenterFactory(WorkoutDetailModule module2, Provider<PermissionAction> permissionActionProvider2, Provider<WorkoutDetailActivity> workoutDetailActivityProvider2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            if ($assertionsDisabled || permissionActionProvider2 != null) {
                this.permissionActionProvider = permissionActionProvider2;
                if ($assertionsDisabled || workoutDetailActivityProvider2 != null) {
                    this.workoutDetailActivityProvider = workoutDetailActivityProvider2;
                    return;
                }
                throw new AssertionError();
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public PermissionPresenter get() {
        return (PermissionPresenter) Preconditions.checkNotNull(this.module.providePermissionPresenter(this.permissionActionProvider.get(), this.workoutDetailActivityProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<PermissionPresenter> create(WorkoutDetailModule module2, Provider<PermissionAction> permissionActionProvider2, Provider<WorkoutDetailActivity> workoutDetailActivityProvider2) {
        return new WorkoutDetailModule_ProvidePermissionPresenterFactory(module2, permissionActionProvider2, workoutDetailActivityProvider2);
    }

    public static PermissionPresenter proxyProvidePermissionPresenter(WorkoutDetailModule instance, PermissionAction permissionAction, WorkoutDetailActivity workoutDetailActivity) {
        return instance.providePermissionPresenter(permissionAction, workoutDetailActivity);
    }
}
