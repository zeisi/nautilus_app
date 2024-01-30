package com.nautilus.omni.dependencyinjection.components;

import android.content.Context;
import android.content.SharedPreferences;
import com.nautilus.omni.appmodules.settings.googlefit.interactor.GoogleFitInteractorContract;
import com.nautilus.omni.appmodules.settings.googlefit.presenter.GoogleFitPresenterContract;
import com.nautilus.omni.appmodules.settings.googlefit.view.ConfigGoogleFitActivity;
import com.nautilus.omni.appmodules.settings.googlefit.view.ConfigGoogleFitActivity_MembersInjector;
import com.nautilus.omni.appmodules.settings.googlefit.view.ConnectionGoogleFitActivity;
import com.nautilus.omni.appmodules.settings.googlefit.view.ConnectionGoogleFitActivity_MembersInjector;
import com.nautilus.omni.dataaccess.GoogleFitDaoHelper;
import com.nautilus.omni.dataaccess.OmniTrainerDaoHelper;
import com.nautilus.omni.databasemanager.DataBaseHelper;
import com.nautilus.omni.dependencyinjection.modules.settings.googlefit.GoogleFitModule;
import com.nautilus.omni.dependencyinjection.modules.settings.googlefit.GoogleFitModule_ProvideGoogleFitDaoHelperFactory;
import com.nautilus.omni.dependencyinjection.modules.settings.googlefit.GoogleFitModule_ProvideGoogleFitPresenterFactory;
import com.nautilus.omni.dependencyinjection.modules.settings.googlefit.GoogleFitModule_ProvidegGoogleFitInteractorFactory;
import com.nautilus.omni.syncservices.syncserviceshelpers.FitServicesSyncDataHelper;
import com.nautilus.omni.util.LocationSettingsUtil_Factory;
import dagger.MembersInjector;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class DaggerGoogleFitComponent implements GoogleFitComponent {
    static final /* synthetic */ boolean $assertionsDisabled = (!DaggerGoogleFitComponent.class.desiredAssertionStatus());
    private MembersInjector<ConfigGoogleFitActivity> configGoogleFitActivityMembersInjector;
    private MembersInjector<ConnectionGoogleFitActivity> connectionGoogleFitActivityMembersInjector;
    private Provider<Context> contextProvider;
    private Provider<DataBaseHelper> getDatabaseHelperProvider;
    private Provider<FitServicesSyncDataHelper> getFitServicesDataHelperProvider;
    private Provider<OmniTrainerDaoHelper> getOmniTrainerDaoHelperProvider;
    private Provider<SharedPreferences> getSharedPreferencesProvider;
    private Provider<GoogleFitDaoHelper> provideGoogleFitDaoHelperProvider;
    private Provider<GoogleFitPresenterContract> provideGoogleFitPresenterProvider;
    private Provider<GoogleFitInteractorContract> providegGoogleFitInteractorProvider;

    private DaggerGoogleFitComponent(Builder builder) {
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
        this.getDatabaseHelperProvider = new Factory<DataBaseHelper>() {
            private final AppComponent appComponent = builder.appComponent;

            public DataBaseHelper get() {
                return (DataBaseHelper) Preconditions.checkNotNull(this.appComponent.getDatabaseHelper(), "Cannot return null from a non-@Nullable component method");
            }
        };
        this.provideGoogleFitDaoHelperProvider = DoubleCheck.provider(GoogleFitModule_ProvideGoogleFitDaoHelperFactory.create(builder.googleFitModule, this.getDatabaseHelperProvider));
        this.getOmniTrainerDaoHelperProvider = new Factory<OmniTrainerDaoHelper>() {
            private final AppComponent appComponent = builder.appComponent;

            public OmniTrainerDaoHelper get() {
                return (OmniTrainerDaoHelper) Preconditions.checkNotNull(this.appComponent.getOmniTrainerDaoHelper(), "Cannot return null from a non-@Nullable component method");
            }
        };
        this.providegGoogleFitInteractorProvider = DoubleCheck.provider(GoogleFitModule_ProvidegGoogleFitInteractorFactory.create(builder.googleFitModule, this.provideGoogleFitDaoHelperProvider, this.getOmniTrainerDaoHelperProvider));
        this.provideGoogleFitPresenterProvider = DoubleCheck.provider(GoogleFitModule_ProvideGoogleFitPresenterFactory.create(builder.googleFitModule, this.contextProvider, this.providegGoogleFitInteractorProvider));
        this.getFitServicesDataHelperProvider = new Factory<FitServicesSyncDataHelper>() {
            private final AppComponent appComponent = builder.appComponent;

            public FitServicesSyncDataHelper get() {
                return (FitServicesSyncDataHelper) Preconditions.checkNotNull(this.appComponent.getFitServicesDataHelper(), "Cannot return null from a non-@Nullable component method");
            }
        };
        this.configGoogleFitActivityMembersInjector = ConfigGoogleFitActivity_MembersInjector.create(this.getSharedPreferencesProvider, LocationSettingsUtil_Factory.create(), this.provideGoogleFitPresenterProvider, this.getFitServicesDataHelperProvider);
        this.connectionGoogleFitActivityMembersInjector = ConnectionGoogleFitActivity_MembersInjector.create(this.getSharedPreferencesProvider, LocationSettingsUtil_Factory.create(), this.provideGoogleFitPresenterProvider);
    }

    public void inject(ConfigGoogleFitActivity configGoogleFitActivity) {
        this.configGoogleFitActivityMembersInjector.injectMembers(configGoogleFitActivity);
    }

    public void inject(ConnectionGoogleFitActivity connectionGoogleFitActivity) {
        this.connectionGoogleFitActivityMembersInjector.injectMembers(connectionGoogleFitActivity);
    }

    public static final class Builder {
        /* access modifiers changed from: private */
        public AppComponent appComponent;
        /* access modifiers changed from: private */
        public GoogleFitModule googleFitModule;

        private Builder() {
        }

        public GoogleFitComponent build() {
            if (this.googleFitModule == null) {
                throw new IllegalStateException(GoogleFitModule.class.getCanonicalName() + " must be set");
            } else if (this.appComponent != null) {
                return new DaggerGoogleFitComponent(this);
            } else {
                throw new IllegalStateException(AppComponent.class.getCanonicalName() + " must be set");
            }
        }

        public Builder googleFitModule(GoogleFitModule googleFitModule2) {
            this.googleFitModule = (GoogleFitModule) Preconditions.checkNotNull(googleFitModule2);
            return this;
        }

        public Builder appComponent(AppComponent appComponent2) {
            this.appComponent = (AppComponent) Preconditions.checkNotNull(appComponent2);
            return this;
        }
    }
}
