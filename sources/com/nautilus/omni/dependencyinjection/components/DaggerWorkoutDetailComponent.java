package com.nautilus.omni.dependencyinjection.components;

import android.content.Context;
import android.content.SharedPreferences;
import com.nautilus.omni.appmodules.journal.presenter.day.workoutdetail.WorkoutDetailPresenter;
import com.nautilus.omni.appmodules.journal.view.day.workoutdetail.WorkoutDetailActivity;
import com.nautilus.omni.appmodules.journal.view.day.workoutdetail.WorkoutDetailActivity_MembersInjector;
import com.nautilus.omni.appmodules.journal.view.day.workoutdetail.WorkoutDetailViewContract;
import com.nautilus.omni.dependencyinjection.modules.workoutdetail.WorkoutDetailModule;
import com.nautilus.omni.dependencyinjection.modules.workoutdetail.WorkoutDetailModule_ProvidePermissionActionFactory;
import com.nautilus.omni.dependencyinjection.modules.workoutdetail.WorkoutDetailModule_ProvidePermissionPresenterFactory;
import com.nautilus.omni.dependencyinjection.modules.workoutdetail.WorkoutDetailModule_ProvideWorkoutDetailActivityFactory;
import com.nautilus.omni.dependencyinjection.modules.workoutdetail.WorkoutDetailModule_ProvideWorkoutDetailPresenterFactory;
import com.nautilus.omni.dependencyinjection.modules.workoutdetail.WorkoutDetailModule_ProvidesWorkoutDetailViewFactory;
import com.nautilus.omni.permissions.PermissionAction;
import com.nautilus.omni.permissions.PermissionPresenter;
import com.nautilus.omni.util.LocationSettingsUtil_Factory;
import dagger.MembersInjector;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class DaggerWorkoutDetailComponent implements WorkoutDetailComponent {
    static final /* synthetic */ boolean $assertionsDisabled = (!DaggerWorkoutDetailComponent.class.desiredAssertionStatus());
    private Provider<Context> contextProvider;
    private Provider<SharedPreferences> getSharedPreferencesProvider;
    private Provider<PermissionAction> providePermissionActionProvider;
    private Provider<PermissionPresenter> providePermissionPresenterProvider;
    private Provider<WorkoutDetailActivity> provideWorkoutDetailActivityProvider;
    private Provider<WorkoutDetailPresenter> provideWorkoutDetailPresenterProvider;
    private Provider<WorkoutDetailViewContract> providesWorkoutDetailViewProvider;
    private MembersInjector<WorkoutDetailActivity> workoutDetailActivityMembersInjector;

    private DaggerWorkoutDetailComponent(Builder builder) {
        if ($assertionsDisabled || builder != null) {
            initialize(builder);
            return;
        }
        throw new AssertionError();
    }

    public static Builder builder() {
        return new Builder();
    }

    private void initialize(final Builder builder) {
        this.getSharedPreferencesProvider = new Factory<SharedPreferences>() {
            private final AppComponent appComponent = builder.appComponent;

            public SharedPreferences get() {
                return (SharedPreferences) Preconditions.checkNotNull(this.appComponent.getSharedPreferences(), "Cannot return null from a non-@Nullable component method");
            }
        };
        this.contextProvider = new Factory<Context>() {
            private final AppComponent appComponent = builder.appComponent;

            public Context get() {
                return (Context) Preconditions.checkNotNull(this.appComponent.context(), "Cannot return null from a non-@Nullable component method");
            }
        };
        this.providesWorkoutDetailViewProvider = DoubleCheck.provider(WorkoutDetailModule_ProvidesWorkoutDetailViewFactory.create(builder.workoutDetailModule));
        this.provideWorkoutDetailPresenterProvider = DoubleCheck.provider(WorkoutDetailModule_ProvideWorkoutDetailPresenterFactory.create(builder.workoutDetailModule, this.contextProvider, this.providesWorkoutDetailViewProvider));
        this.provideWorkoutDetailActivityProvider = DoubleCheck.provider(WorkoutDetailModule_ProvideWorkoutDetailActivityFactory.create(builder.workoutDetailModule));
        this.providePermissionActionProvider = DoubleCheck.provider(WorkoutDetailModule_ProvidePermissionActionFactory.create(builder.workoutDetailModule, this.provideWorkoutDetailActivityProvider));
        this.providePermissionPresenterProvider = DoubleCheck.provider(WorkoutDetailModule_ProvidePermissionPresenterFactory.create(builder.workoutDetailModule, this.providePermissionActionProvider, this.provideWorkoutDetailActivityProvider));
        this.workoutDetailActivityMembersInjector = WorkoutDetailActivity_MembersInjector.create(this.getSharedPreferencesProvider, LocationSettingsUtil_Factory.create(), this.provideWorkoutDetailPresenterProvider, this.providePermissionPresenterProvider);
    }

    public void inject(WorkoutDetailActivity workoutDetailActivity) {
        this.workoutDetailActivityMembersInjector.injectMembers(workoutDetailActivity);
    }

    public static final class Builder {
        /* access modifiers changed from: private */
        public AppComponent appComponent;
        /* access modifiers changed from: private */
        public WorkoutDetailModule workoutDetailModule;

        private Builder() {
        }

        public WorkoutDetailComponent build() {
            if (this.workoutDetailModule == null) {
                throw new IllegalStateException(WorkoutDetailModule.class.getCanonicalName() + " must be set");
            } else if (this.appComponent != null) {
                return new DaggerWorkoutDetailComponent(this);
            } else {
                throw new IllegalStateException(AppComponent.class.getCanonicalName() + " must be set");
            }
        }

        public Builder workoutDetailModule(WorkoutDetailModule workoutDetailModule2) {
            this.workoutDetailModule = (WorkoutDetailModule) Preconditions.checkNotNull(workoutDetailModule2);
            return this;
        }

        public Builder appComponent(AppComponent appComponent2) {
            this.appComponent = (AppComponent) Preconditions.checkNotNull(appComponent2);
            return this;
        }
    }
}
