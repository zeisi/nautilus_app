package com.nautilus.omni.dependencyinjection.components.wizard;

import android.content.Context;
import android.content.SharedPreferences;
import com.nautilus.omni.appmodules.connectionwizard.domain.interactors.ConnectionWizardInteractorContract;
import com.nautilus.omni.appmodules.connectionwizard.presenter.DevicesListPresenterContract;
import com.nautilus.omni.appmodules.connectionwizard.view.OmniTrainerListActivity;
import com.nautilus.omni.appmodules.connectionwizard.view.OmniTrainerListActivityContract;
import com.nautilus.omni.appmodules.connectionwizard.view.OmniTrainerListActivity_MembersInjector;
import com.nautilus.omni.dataaccess.OmniTrainerDaoHelper;
import com.nautilus.omni.dependencyinjection.components.AppComponent;
import com.nautilus.omni.dependencyinjection.modules.wizard.DevicesListModule;
import com.nautilus.omni.dependencyinjection.modules.wizard.DevicesListModule_ProvideDevicesListPresenterFactory;
import com.nautilus.omni.dependencyinjection.modules.wizard.DevicesListModule_ProvideOmniTrainerListActivityFactory;
import com.nautilus.omni.dependencyinjection.modules.wizard.WizardDataModule;
import com.nautilus.omni.dependencyinjection.modules.wizard.WizardDataModule_ProvideConnectionWizardInteractorFactory;
import com.nautilus.omni.util.LocationSettingsUtil_Factory;
import dagger.MembersInjector;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class DaggerDevicesListComponent implements DevicesListComponent {
    static final /* synthetic */ boolean $assertionsDisabled = (!DaggerDevicesListComponent.class.desiredAssertionStatus());
    private Provider<Context> contextProvider;
    private Provider<OmniTrainerDaoHelper> getOmniTrainerDaoHelperProvider;
    private Provider<SharedPreferences> getSharedPreferencesProvider;
    private MembersInjector<OmniTrainerListActivity> omniTrainerListActivityMembersInjector;
    private Provider<ConnectionWizardInteractorContract> provideConnectionWizardInteractorProvider;
    private Provider<DevicesListPresenterContract> provideDevicesListPresenterProvider;
    private Provider<OmniTrainerListActivityContract> provideOmniTrainerListActivityProvider;

    private DaggerDevicesListComponent(Builder builder) {
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
        this.getOmniTrainerDaoHelperProvider = new Factory<OmniTrainerDaoHelper>() {
            private final AppComponent appComponent = builder.appComponent;

            public OmniTrainerDaoHelper get() {
                return (OmniTrainerDaoHelper) Preconditions.checkNotNull(this.appComponent.getOmniTrainerDaoHelper(), "Cannot return null from a non-@Nullable component method");
            }
        };
        this.provideConnectionWizardInteractorProvider = DoubleCheck.provider(WizardDataModule_ProvideConnectionWizardInteractorFactory.create(builder.wizardDataModule, this.getOmniTrainerDaoHelperProvider));
        this.provideOmniTrainerListActivityProvider = DoubleCheck.provider(DevicesListModule_ProvideOmniTrainerListActivityFactory.create(builder.devicesListModule));
        this.provideDevicesListPresenterProvider = DoubleCheck.provider(DevicesListModule_ProvideDevicesListPresenterFactory.create(builder.devicesListModule, this.contextProvider, this.provideConnectionWizardInteractorProvider, this.provideOmniTrainerListActivityProvider));
        this.omniTrainerListActivityMembersInjector = OmniTrainerListActivity_MembersInjector.create(this.getSharedPreferencesProvider, LocationSettingsUtil_Factory.create(), this.provideDevicesListPresenterProvider);
    }

    public void inject(OmniTrainerListActivity omniTrainerListActivity) {
        this.omniTrainerListActivityMembersInjector.injectMembers(omniTrainerListActivity);
    }

    public static final class Builder {
        /* access modifiers changed from: private */
        public AppComponent appComponent;
        /* access modifiers changed from: private */
        public DevicesListModule devicesListModule;
        /* access modifiers changed from: private */
        public WizardDataModule wizardDataModule;

        private Builder() {
        }

        public DevicesListComponent build() {
            if (this.wizardDataModule == null) {
                this.wizardDataModule = new WizardDataModule();
            }
            if (this.devicesListModule == null) {
                throw new IllegalStateException(DevicesListModule.class.getCanonicalName() + " must be set");
            } else if (this.appComponent != null) {
                return new DaggerDevicesListComponent(this);
            } else {
                throw new IllegalStateException(AppComponent.class.getCanonicalName() + " must be set");
            }
        }

        public Builder wizardDataModule(WizardDataModule wizardDataModule2) {
            this.wizardDataModule = (WizardDataModule) Preconditions.checkNotNull(wizardDataModule2);
            return this;
        }

        public Builder devicesListModule(DevicesListModule devicesListModule2) {
            this.devicesListModule = (DevicesListModule) Preconditions.checkNotNull(devicesListModule2);
            return this;
        }

        public Builder appComponent(AppComponent appComponent2) {
            this.appComponent = (AppComponent) Preconditions.checkNotNull(appComponent2);
            return this;
        }
    }
}
