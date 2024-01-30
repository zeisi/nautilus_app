package com.nautilus.omni.dependencyinjection.components.wizard;

import android.content.Context;
import android.content.SharedPreferences;
import com.nautilus.omni.appmodules.connectionwizard.domain.interactors.ConnectionWizardInteractorContract;
import com.nautilus.omni.appmodules.connectionwizard.domain.receivers.WizardReceivers;
import com.nautilus.omni.appmodules.connectionwizard.presenter.WizardPresenter;
import com.nautilus.omni.appmodules.connectionwizard.presenter.WizardPresenter_Factory;
import com.nautilus.omni.appmodules.connectionwizard.view.ConnectionWizardActivity;
import com.nautilus.omni.appmodules.connectionwizard.view.ConnectionWizardActivity_MembersInjector;
import com.nautilus.omni.dataaccess.OmniTrainerDaoHelper;
import com.nautilus.omni.dependencyinjection.components.AppComponent;
import com.nautilus.omni.dependencyinjection.modules.wizard.ConnectionModule;
import com.nautilus.omni.dependencyinjection.modules.wizard.ConnectionModule_ProvideConnectionWizardActivityFactory;
import com.nautilus.omni.dependencyinjection.modules.wizard.ConnectionModule_ProvidePermissionActionFactory;
import com.nautilus.omni.dependencyinjection.modules.wizard.ConnectionModule_ProvidePermissionPresenterFactory;
import com.nautilus.omni.dependencyinjection.modules.wizard.WizardDataModule;
import com.nautilus.omni.dependencyinjection.modules.wizard.WizardDataModule_ProvideConnectionWizardInteractorFactory;
import com.nautilus.omni.dependencyinjection.modules.wizard.WizardDataModule_ProvideReceiversFactory;
import com.nautilus.omni.permissions.PermissionAction;
import com.nautilus.omni.permissions.PermissionPresenter;
import com.nautilus.omni.util.LocationSettingsUtil_Factory;
import dagger.MembersInjector;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import dagger.internal.MembersInjectors;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class DaggerWizardConnectionComponent implements WizardConnectionComponent {
    static final /* synthetic */ boolean $assertionsDisabled = (!DaggerWizardConnectionComponent.class.desiredAssertionStatus());
    private MembersInjector<ConnectionWizardActivity> connectionWizardActivityMembersInjector;
    private Provider<Context> contextProvider;
    private Provider<OmniTrainerDaoHelper> getOmniTrainerDaoHelperProvider;
    private Provider<SharedPreferences> getSharedPreferencesProvider;
    private Provider<ConnectionWizardActivity> provideConnectionWizardActivityProvider;
    private Provider<ConnectionWizardInteractorContract> provideConnectionWizardInteractorProvider;
    private Provider<PermissionAction> providePermissionActionProvider;
    private Provider<PermissionPresenter> providePermissionPresenterProvider;
    private Provider<WizardReceivers> provideReceiversProvider;
    private Provider<WizardPresenter> wizardPresenterProvider;

    private DaggerWizardConnectionComponent(Builder builder) {
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
        this.provideConnectionWizardActivityProvider = DoubleCheck.provider(ConnectionModule_ProvideConnectionWizardActivityFactory.create(builder.connectionModule));
        this.providePermissionActionProvider = DoubleCheck.provider(ConnectionModule_ProvidePermissionActionFactory.create(builder.connectionModule, this.provideConnectionWizardActivityProvider));
        this.providePermissionPresenterProvider = DoubleCheck.provider(ConnectionModule_ProvidePermissionPresenterFactory.create(builder.connectionModule, this.providePermissionActionProvider, this.provideConnectionWizardActivityProvider));
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
        this.provideReceiversProvider = DoubleCheck.provider(WizardDataModule_ProvideReceiversFactory.create(builder.wizardDataModule, this.contextProvider));
        this.wizardPresenterProvider = WizardPresenter_Factory.create(MembersInjectors.noOp(), this.contextProvider, this.provideConnectionWizardActivityProvider, this.provideConnectionWizardInteractorProvider, this.provideReceiversProvider);
        this.connectionWizardActivityMembersInjector = ConnectionWizardActivity_MembersInjector.create(this.getSharedPreferencesProvider, LocationSettingsUtil_Factory.create(), this.providePermissionPresenterProvider, this.wizardPresenterProvider);
    }

    public void inject(ConnectionWizardActivity connectionWizardActivity) {
        this.connectionWizardActivityMembersInjector.injectMembers(connectionWizardActivity);
    }

    public static final class Builder {
        /* access modifiers changed from: private */
        public AppComponent appComponent;
        /* access modifiers changed from: private */
        public ConnectionModule connectionModule;
        /* access modifiers changed from: private */
        public WizardDataModule wizardDataModule;

        private Builder() {
        }

        public WizardConnectionComponent build() {
            if (this.connectionModule == null) {
                throw new IllegalStateException(ConnectionModule.class.getCanonicalName() + " must be set");
            }
            if (this.wizardDataModule == null) {
                this.wizardDataModule = new WizardDataModule();
            }
            if (this.appComponent != null) {
                return new DaggerWizardConnectionComponent(this);
            }
            throw new IllegalStateException(AppComponent.class.getCanonicalName() + " must be set");
        }

        public Builder wizardDataModule(WizardDataModule wizardDataModule2) {
            this.wizardDataModule = (WizardDataModule) Preconditions.checkNotNull(wizardDataModule2);
            return this;
        }

        public Builder connectionModule(ConnectionModule connectionModule2) {
            this.connectionModule = (ConnectionModule) Preconditions.checkNotNull(connectionModule2);
            return this;
        }

        public Builder appComponent(AppComponent appComponent2) {
            this.appComponent = (AppComponent) Preconditions.checkNotNull(appComponent2);
            return this;
        }
    }
}
