package com.nautilus.omni.dependencyinjection.components;

import android.content.Context;
import android.content.SharedPreferences;
import com.nautilus.omni.appmodules.settings.mainsection.interactor.SettingsInteractorContract;
import com.nautilus.omni.appmodules.settings.mainsection.presenter.SettingsPresenterContract;
import com.nautilus.omni.appmodules.settings.mainsection.view.SettingsActivity;
import com.nautilus.omni.appmodules.settings.mainsection.view.SettingsActivity_MembersInjector;
import com.nautilus.omni.appmodules.settings.mainsection.view.SettingsViewContract;
import com.nautilus.omni.dataaccess.ProductDaoHelper;
import com.nautilus.omni.dataaccess.UserDaoHelper;
import com.nautilus.omni.dependencyinjection.modules.settings.SettingsModule;
import com.nautilus.omni.dependencyinjection.modules.settings.SettingsModule_ProvideISettingsActivityFactory;
import com.nautilus.omni.dependencyinjection.modules.settings.SettingsModule_ProvidePermissionActionFactory;
import com.nautilus.omni.dependencyinjection.modules.settings.SettingsModule_ProvidePermissionPresenterFactory;
import com.nautilus.omni.dependencyinjection.modules.settings.SettingsModule_ProvideSettingsActivityFactory;
import com.nautilus.omni.dependencyinjection.modules.settings.SettingsModule_ProvideSettingsInteractorFactory;
import com.nautilus.omni.dependencyinjection.modules.settings.SettingsModule_ProvideSettingsPresenterFactory;
import com.nautilus.omni.permissions.PermissionAction;
import com.nautilus.omni.permissions.PermissionPresenter;
import com.nautilus.omni.util.LocationSettingsUtil_Factory;
import dagger.MembersInjector;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class DaggerSettingsComponent implements SettingsComponent {
    static final /* synthetic */ boolean $assertionsDisabled = (!DaggerSettingsComponent.class.desiredAssertionStatus());
    private Provider<Context> contextProvider;
    private Provider<ProductDaoHelper> getProductDaoHelperProvider;
    private Provider<SharedPreferences> getSharedPreferencesProvider;
    private Provider<UserDaoHelper> getUserDaoHelperProvider;
    private Provider<SettingsViewContract> provideISettingsActivityProvider;
    private Provider<PermissionAction> providePermissionActionProvider;
    private Provider<PermissionPresenter> providePermissionPresenterProvider;
    private Provider<SettingsActivity> provideSettingsActivityProvider;
    private Provider<SettingsInteractorContract> provideSettingsInteractorProvider;
    private Provider<SettingsPresenterContract> provideSettingsPresenterProvider;
    private MembersInjector<SettingsActivity> settingsActivityMembersInjector;

    private DaggerSettingsComponent(Builder builder) {
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
        this.provideISettingsActivityProvider = DoubleCheck.provider(SettingsModule_ProvideISettingsActivityFactory.create(builder.settingsModule));
        this.getUserDaoHelperProvider = new Factory<UserDaoHelper>() {
            private final AppComponent appComponent = builder.appComponent;

            public UserDaoHelper get() {
                return (UserDaoHelper) Preconditions.checkNotNull(this.appComponent.getUserDaoHelper(), "Cannot return null from a non-@Nullable component method");
            }
        };
        this.getProductDaoHelperProvider = new Factory<ProductDaoHelper>() {
            private final AppComponent appComponent = builder.appComponent;

            public ProductDaoHelper get() {
                return (ProductDaoHelper) Preconditions.checkNotNull(this.appComponent.getProductDaoHelper(), "Cannot return null from a non-@Nullable component method");
            }
        };
        this.provideSettingsInteractorProvider = DoubleCheck.provider(SettingsModule_ProvideSettingsInteractorFactory.create(builder.settingsModule, this.getUserDaoHelperProvider, this.getProductDaoHelperProvider));
        this.provideSettingsPresenterProvider = DoubleCheck.provider(SettingsModule_ProvideSettingsPresenterFactory.create(builder.settingsModule, this.contextProvider, this.provideISettingsActivityProvider, this.provideSettingsInteractorProvider));
        this.provideSettingsActivityProvider = DoubleCheck.provider(SettingsModule_ProvideSettingsActivityFactory.create(builder.settingsModule));
        this.providePermissionActionProvider = DoubleCheck.provider(SettingsModule_ProvidePermissionActionFactory.create(builder.settingsModule, this.provideSettingsActivityProvider));
        this.providePermissionPresenterProvider = DoubleCheck.provider(SettingsModule_ProvidePermissionPresenterFactory.create(builder.settingsModule, this.providePermissionActionProvider, this.provideSettingsActivityProvider));
        this.settingsActivityMembersInjector = SettingsActivity_MembersInjector.create(this.getSharedPreferencesProvider, LocationSettingsUtil_Factory.create(), this.provideSettingsPresenterProvider, this.providePermissionPresenterProvider);
    }

    public void inject(SettingsActivity settingsActivity) {
        this.settingsActivityMembersInjector.injectMembers(settingsActivity);
    }

    public static final class Builder {
        /* access modifiers changed from: private */
        public AppComponent appComponent;
        /* access modifiers changed from: private */
        public SettingsModule settingsModule;

        private Builder() {
        }

        public SettingsComponent build() {
            if (this.settingsModule == null) {
                throw new IllegalStateException(SettingsModule.class.getCanonicalName() + " must be set");
            } else if (this.appComponent != null) {
                return new DaggerSettingsComponent(this);
            } else {
                throw new IllegalStateException(AppComponent.class.getCanonicalName() + " must be set");
            }
        }

        public Builder settingsModule(SettingsModule settingsModule2) {
            this.settingsModule = (SettingsModule) Preconditions.checkNotNull(settingsModule2);
            return this;
        }

        public Builder appComponent(AppComponent appComponent2) {
            this.appComponent = (AppComponent) Preconditions.checkNotNull(appComponent2);
            return this;
        }
    }
}
