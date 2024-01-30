package com.nautilus.omni.dependencyinjection.components.wizard;

import android.content.Context;
import android.content.SharedPreferences;
import com.nautilus.omni.appmodules.connectionwizard.domain.interactors.ConnectionWizardInteractorContract;
import com.nautilus.omni.appmodules.connectionwizard.domain.receivers.WizardReceivers;
import com.nautilus.omni.appmodules.connectionwizard.presenter.UserSelectPresenterContract;
import com.nautilus.omni.appmodules.connectionwizard.view.SelectUserNumberActivity;
import com.nautilus.omni.appmodules.connectionwizard.view.SelectUserNumberActivityContract;
import com.nautilus.omni.appmodules.connectionwizard.view.SelectUserNumberActivity_MembersInjector;
import com.nautilus.omni.dataaccess.OmniTrainerDaoHelper;
import com.nautilus.omni.dependencyinjection.components.AppComponent;
import com.nautilus.omni.dependencyinjection.modules.wizard.UserSelectModule;
import com.nautilus.omni.dependencyinjection.modules.wizard.UserSelectModule_ProvideISelectUserNumberActivityFactory;
import com.nautilus.omni.dependencyinjection.modules.wizard.UserSelectModule_ProvideWizardPresenterFactory;
import com.nautilus.omni.dependencyinjection.modules.wizard.WizardDataModule;
import com.nautilus.omni.dependencyinjection.modules.wizard.WizardDataModule_ProvideConnectionWizardInteractorFactory;
import com.nautilus.omni.dependencyinjection.modules.wizard.WizardDataModule_ProvideReceiversFactory;
import com.nautilus.omni.util.LocationSettingsUtil_Factory;
import dagger.MembersInjector;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class DaggerUserSelectComponent implements UserSelectComponent {
    static final /* synthetic */ boolean $assertionsDisabled = (!DaggerUserSelectComponent.class.desiredAssertionStatus());
    private Provider<Context> contextProvider;
    private Provider<OmniTrainerDaoHelper> getOmniTrainerDaoHelperProvider;
    private Provider<SharedPreferences> getSharedPreferencesProvider;
    private Provider<ConnectionWizardInteractorContract> provideConnectionWizardInteractorProvider;
    private Provider<SelectUserNumberActivityContract> provideISelectUserNumberActivityProvider;
    private Provider<WizardReceivers> provideReceiversProvider;
    private Provider<UserSelectPresenterContract> provideWizardPresenterProvider;
    private MembersInjector<SelectUserNumberActivity> selectUserNumberActivityMembersInjector;

    private DaggerUserSelectComponent(Builder builder) {
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
        this.provideISelectUserNumberActivityProvider = DoubleCheck.provider(UserSelectModule_ProvideISelectUserNumberActivityFactory.create(builder.userSelectModule));
        this.getOmniTrainerDaoHelperProvider = new Factory<OmniTrainerDaoHelper>() {
            private final AppComponent appComponent = builder.appComponent;

            public OmniTrainerDaoHelper get() {
                return (OmniTrainerDaoHelper) Preconditions.checkNotNull(this.appComponent.getOmniTrainerDaoHelper(), "Cannot return null from a non-@Nullable component method");
            }
        };
        this.provideConnectionWizardInteractorProvider = DoubleCheck.provider(WizardDataModule_ProvideConnectionWizardInteractorFactory.create(builder.wizardDataModule, this.getOmniTrainerDaoHelperProvider));
        this.provideReceiversProvider = DoubleCheck.provider(WizardDataModule_ProvideReceiversFactory.create(builder.wizardDataModule, this.contextProvider));
        this.provideWizardPresenterProvider = DoubleCheck.provider(UserSelectModule_ProvideWizardPresenterFactory.create(builder.userSelectModule, this.contextProvider, this.provideISelectUserNumberActivityProvider, this.provideConnectionWizardInteractorProvider, this.provideReceiversProvider));
        this.selectUserNumberActivityMembersInjector = SelectUserNumberActivity_MembersInjector.create(this.getSharedPreferencesProvider, LocationSettingsUtil_Factory.create(), this.provideWizardPresenterProvider);
    }

    public void inject(SelectUserNumberActivity selectUserNumberActivity) {
        this.selectUserNumberActivityMembersInjector.injectMembers(selectUserNumberActivity);
    }

    public static final class Builder {
        /* access modifiers changed from: private */
        public AppComponent appComponent;
        /* access modifiers changed from: private */
        public UserSelectModule userSelectModule;
        /* access modifiers changed from: private */
        public WizardDataModule wizardDataModule;

        private Builder() {
        }

        public UserSelectComponent build() {
            if (this.userSelectModule == null) {
                throw new IllegalStateException(UserSelectModule.class.getCanonicalName() + " must be set");
            }
            if (this.wizardDataModule == null) {
                this.wizardDataModule = new WizardDataModule();
            }
            if (this.appComponent != null) {
                return new DaggerUserSelectComponent(this);
            }
            throw new IllegalStateException(AppComponent.class.getCanonicalName() + " must be set");
        }

        public Builder wizardDataModule(WizardDataModule wizardDataModule2) {
            this.wizardDataModule = (WizardDataModule) Preconditions.checkNotNull(wizardDataModule2);
            return this;
        }

        public Builder userSelectModule(UserSelectModule userSelectModule2) {
            this.userSelectModule = (UserSelectModule) Preconditions.checkNotNull(userSelectModule2);
            return this;
        }

        public Builder appComponent(AppComponent appComponent2) {
            this.appComponent = (AppComponent) Preconditions.checkNotNull(appComponent2);
            return this;
        }
    }
}
