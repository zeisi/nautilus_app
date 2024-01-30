package com.nautilus.omni.dependencyinjection.modules.workoutdetail;

import android.content.Context;
import com.nautilus.omni.appmodules.journal.presenter.day.workoutdetail.WorkoutDetailPresenter;
import com.nautilus.omni.appmodules.journal.view.day.workoutdetail.WorkoutDetailViewContract;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class WorkoutDetailModule_ProvideWorkoutDetailPresenterFactory implements Factory<WorkoutDetailPresenter> {
    static final /* synthetic */ boolean $assertionsDisabled = (!WorkoutDetailModule_ProvideWorkoutDetailPresenterFactory.class.desiredAssertionStatus());
    private final Provider<Context> contextProvider;
    private final WorkoutDetailModule module;
    private final Provider<WorkoutDetailViewContract> workoutDetailViewProvider;

    public WorkoutDetailModule_ProvideWorkoutDetailPresenterFactory(WorkoutDetailModule module2, Provider<Context> contextProvider2, Provider<WorkoutDetailViewContract> workoutDetailViewProvider2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            if ($assertionsDisabled || contextProvider2 != null) {
                this.contextProvider = contextProvider2;
                if ($assertionsDisabled || workoutDetailViewProvider2 != null) {
                    this.workoutDetailViewProvider = workoutDetailViewProvider2;
                    return;
                }
                throw new AssertionError();
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public WorkoutDetailPresenter get() {
        return (WorkoutDetailPresenter) Preconditions.checkNotNull(this.module.provideWorkoutDetailPresenter(this.contextProvider.get(), this.workoutDetailViewProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<WorkoutDetailPresenter> create(WorkoutDetailModule module2, Provider<Context> contextProvider2, Provider<WorkoutDetailViewContract> workoutDetailViewProvider2) {
        return new WorkoutDetailModule_ProvideWorkoutDetailPresenterFactory(module2, contextProvider2, workoutDetailViewProvider2);
    }

    public static WorkoutDetailPresenter proxyProvideWorkoutDetailPresenter(WorkoutDetailModule instance, Context context, WorkoutDetailViewContract workoutDetailView) {
        return instance.provideWorkoutDetailPresenter(context, workoutDetailView);
    }
}
