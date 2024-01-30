package com.nautilus.omni.dependencyinjection.modules.workoutdetail;

import android.content.Context;
import com.nautilus.omni.appmodules.journal.presenter.day.workoutdetail.WorkoutDetailPresenter;
import com.nautilus.omni.appmodules.journal.view.day.workoutdetail.WorkoutDetailActivity;
import com.nautilus.omni.appmodules.journal.view.day.workoutdetail.WorkoutDetailViewContract;
import com.nautilus.omni.dependencyinjection.scopes.ActivityScope;
import com.nautilus.omni.permissions.PermissionAction;
import com.nautilus.omni.permissions.PermissionPresenter;
import com.nautilus.omni.permissions.SupportPermissionActionImpl;
import dagger.Module;
import dagger.Provides;

@Module
public class WorkoutDetailModule {
    WorkoutDetailActivity mIWorkoutDetailView;

    public WorkoutDetailModule(WorkoutDetailActivity workoutDetailView) {
        this.mIWorkoutDetailView = workoutDetailView;
    }

    /* access modifiers changed from: package-private */
    @ActivityScope
    @Provides
    public WorkoutDetailViewContract providesWorkoutDetailView() {
        return this.mIWorkoutDetailView;
    }

    /* access modifiers changed from: package-private */
    @ActivityScope
    @Provides
    public WorkoutDetailActivity provideWorkoutDetailActivity() {
        return this.mIWorkoutDetailView;
    }

    /* access modifiers changed from: package-private */
    @ActivityScope
    @Provides
    public WorkoutDetailPresenter provideWorkoutDetailPresenter(Context context, WorkoutDetailViewContract workoutDetailView) {
        return new WorkoutDetailPresenter(context, workoutDetailView);
    }

    /* access modifiers changed from: package-private */
    @ActivityScope
    @Provides
    public PermissionAction providePermissionAction(WorkoutDetailActivity workoutDetailActivity) {
        return new SupportPermissionActionImpl(workoutDetailActivity);
    }

    /* access modifiers changed from: package-private */
    @ActivityScope
    @Provides
    public PermissionPresenter providePermissionPresenter(PermissionAction permissionAction, WorkoutDetailActivity workoutDetailActivity) {
        return new PermissionPresenter(permissionAction, workoutDetailActivity);
    }
}
