package com.nautilus.omni.dependencyinjection.components;

import android.content.Context;
import android.content.SharedPreferences;
import com.nautilus.omni.appmodules.awards.AwardValueBuilder;
import com.nautilus.omni.appmodules.awardtypes.interactor.AwardsTypesInteractorContract;
import com.nautilus.omni.appmodules.awardtypes.presenter.AwardTypesPresenterContract;
import com.nautilus.omni.appmodules.awardtypes.view.AwardTypesActivity;
import com.nautilus.omni.appmodules.awardtypes.view.AwardTypesActivityContract;
import com.nautilus.omni.appmodules.awardtypes.view.AwardTypesActivity_MembersInjector;
import com.nautilus.omni.dataaccess.AwardsDaoHelper;
import com.nautilus.omni.dataaccess.WeekDaoHelper;
import com.nautilus.omni.dependencyinjection.modules.awardstypes.AwardTypesModule;
import com.nautilus.omni.dependencyinjection.modules.awardstypes.AwardTypesModule_ProvideAwardTypesPresenterFactory;
import com.nautilus.omni.dependencyinjection.modules.awardstypes.AwardTypesModule_ProvideAwardValueBuilderFactory;
import com.nautilus.omni.dependencyinjection.modules.awardstypes.AwardTypesModule_ProvideAwardsTypesInteractorFactory;
import com.nautilus.omni.dependencyinjection.modules.awardstypes.AwardTypesModule_ProvideAwardsViewFactory;
import com.nautilus.omni.dependencyinjection.modules.awardstypes.AwardTypesModule_ProvideIAwardsViewFactory;
import com.nautilus.omni.dependencyinjection.modules.awardstypes.AwardTypesModule_ProvidePermissionActionFactory;
import com.nautilus.omni.dependencyinjection.modules.awardstypes.AwardTypesModule_ProvidePermissionPresenterFactory;
import com.nautilus.omni.permissions.PermissionAction;
import com.nautilus.omni.permissions.PermissionPresenter;
import com.nautilus.omni.util.LocationSettingsUtil_Factory;
import dagger.MembersInjector;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class DaggerAwardTypesComponent implements AwardTypesComponent {
    static final /* synthetic */ boolean $assertionsDisabled = (!DaggerAwardTypesComponent.class.desiredAssertionStatus());
    private MembersInjector<AwardTypesActivity> awardTypesActivityMembersInjector;
    private Provider<Context> contextProvider;
    private Provider<AwardsDaoHelper> getAwardsDaoHelperProvider;
    private Provider<SharedPreferences> getSharedPreferencesProvider;
    private Provider<WeekDaoHelper> getWeekDaoHelperProvider;
    private Provider<AwardTypesPresenterContract> provideAwardTypesPresenterProvider;
    private Provider<AwardValueBuilder> provideAwardValueBuilderProvider;
    private Provider<AwardsTypesInteractorContract> provideAwardsTypesInteractorProvider;
    private Provider<AwardTypesActivity> provideAwardsViewProvider;
    private Provider<AwardTypesActivityContract> provideIAwardsViewProvider;
    private Provider<PermissionAction> providePermissionActionProvider;
    private Provider<PermissionPresenter> providePermissionPresenterProvider;

    private DaggerAwardTypesComponent(Builder builder) {
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
        this.getAwardsDaoHelperProvider = new Factory<AwardsDaoHelper>() {
            private final AppComponent appComponent = builder.appComponent;

            public AwardsDaoHelper get() {
                return (AwardsDaoHelper) Preconditions.checkNotNull(this.appComponent.getAwardsDaoHelper(), "Cannot return null from a non-@Nullable component method");
            }
        };
        this.provideAwardsTypesInteractorProvider = DoubleCheck.provider(AwardTypesModule_ProvideAwardsTypesInteractorFactory.create(builder.awardTypesModule, this.getAwardsDaoHelperProvider));
        this.provideIAwardsViewProvider = DoubleCheck.provider(AwardTypesModule_ProvideIAwardsViewFactory.create(builder.awardTypesModule));
        this.provideAwardTypesPresenterProvider = DoubleCheck.provider(AwardTypesModule_ProvideAwardTypesPresenterFactory.create(builder.awardTypesModule, this.contextProvider, this.provideAwardsTypesInteractorProvider, this.provideIAwardsViewProvider));
        this.provideAwardsViewProvider = DoubleCheck.provider(AwardTypesModule_ProvideAwardsViewFactory.create(builder.awardTypesModule));
        this.providePermissionActionProvider = DoubleCheck.provider(AwardTypesModule_ProvidePermissionActionFactory.create(builder.awardTypesModule, this.provideAwardsViewProvider));
        this.providePermissionPresenterProvider = DoubleCheck.provider(AwardTypesModule_ProvidePermissionPresenterFactory.create(builder.awardTypesModule, this.providePermissionActionProvider, this.provideAwardsViewProvider));
        this.getWeekDaoHelperProvider = new Factory<WeekDaoHelper>() {
            private final AppComponent appComponent = builder.appComponent;

            public WeekDaoHelper get() {
                return (WeekDaoHelper) Preconditions.checkNotNull(this.appComponent.getWeekDaoHelper(), "Cannot return null from a non-@Nullable component method");
            }
        };
        this.provideAwardValueBuilderProvider = DoubleCheck.provider(AwardTypesModule_ProvideAwardValueBuilderFactory.create(builder.awardTypesModule, this.contextProvider, this.getWeekDaoHelperProvider));
        this.awardTypesActivityMembersInjector = AwardTypesActivity_MembersInjector.create(this.getSharedPreferencesProvider, LocationSettingsUtil_Factory.create(), this.provideAwardTypesPresenterProvider, this.providePermissionPresenterProvider, this.provideAwardValueBuilderProvider);
    }

    public void inject(AwardTypesActivity awardTypesActivity) {
        this.awardTypesActivityMembersInjector.injectMembers(awardTypesActivity);
    }

    public static final class Builder {
        /* access modifiers changed from: private */
        public AppComponent appComponent;
        /* access modifiers changed from: private */
        public AwardTypesModule awardTypesModule;

        private Builder() {
        }

        public AwardTypesComponent build() {
            if (this.awardTypesModule == null) {
                throw new IllegalStateException(AwardTypesModule.class.getCanonicalName() + " must be set");
            } else if (this.appComponent != null) {
                return new DaggerAwardTypesComponent(this);
            } else {
                throw new IllegalStateException(AppComponent.class.getCanonicalName() + " must be set");
            }
        }

        public Builder awardTypesModule(AwardTypesModule awardTypesModule2) {
            this.awardTypesModule = (AwardTypesModule) Preconditions.checkNotNull(awardTypesModule2);
            return this;
        }

        public Builder appComponent(AppComponent appComponent2) {
            this.appComponent = (AppComponent) Preconditions.checkNotNull(appComponent2);
            return this;
        }
    }
}
