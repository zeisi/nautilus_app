package com.nautilus.omni.dependencyinjection.components;

import android.content.Context;
import android.content.SharedPreferences;
import com.nautilus.omni.appmodules.splash.presenter.SplashPresenterContract;
import com.nautilus.omni.appmodules.splash.presenter.interactor.SplashInteractorContract;
import com.nautilus.omni.appmodules.splash.view.SplashActivity;
import com.nautilus.omni.appmodules.splash.view.SplashActivity_MembersInjector;
import com.nautilus.omni.appmodules.splash.view.SplashViewContract;
import com.nautilus.omni.dataaccess.awards.MainAwardsDaoHelper;
import com.nautilus.omni.dependencyinjection.modules.splash.SplashModule;
import com.nautilus.omni.dependencyinjection.modules.splash.SplashModule_ProvideSplashActivityFactory;
import com.nautilus.omni.dependencyinjection.modules.splash.SplashModule_ProvideSplashInteractorFactory;
import com.nautilus.omni.dependencyinjection.modules.splash.SplashModule_SplashPresenterFactory;
import com.nautilus.omni.util.LocationSettingsUtil_Factory;
import dagger.MembersInjector;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class DaggerSplashComponent implements SplashComponent {
    static final /* synthetic */ boolean $assertionsDisabled = (!DaggerSplashComponent.class.desiredAssertionStatus());
    private Provider<Context> contextProvider;
    private Provider<MainAwardsDaoHelper> getMainAwardsDaoHelperProvider;
    private Provider<SharedPreferences> getSharedPreferencesProvider;
    private Provider<SplashViewContract> provideSplashActivityProvider;
    private Provider<SplashInteractorContract> provideSplashInteractorProvider;
    private MembersInjector<SplashActivity> splashActivityMembersInjector;
    private Provider<SplashPresenterContract> splashPresenterProvider;

    private DaggerSplashComponent(Builder builder) {
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
        this.provideSplashActivityProvider = DoubleCheck.provider(SplashModule_ProvideSplashActivityFactory.create(builder.splashModule));
        this.getMainAwardsDaoHelperProvider = new Factory<MainAwardsDaoHelper>() {
            private final AppComponent appComponent = builder.appComponent;

            public MainAwardsDaoHelper get() {
                return (MainAwardsDaoHelper) Preconditions.checkNotNull(this.appComponent.getMainAwardsDaoHelper(), "Cannot return null from a non-@Nullable component method");
            }
        };
        this.provideSplashInteractorProvider = DoubleCheck.provider(SplashModule_ProvideSplashInteractorFactory.create(builder.splashModule, this.getMainAwardsDaoHelperProvider));
        this.splashPresenterProvider = DoubleCheck.provider(SplashModule_SplashPresenterFactory.create(builder.splashModule, this.contextProvider, this.provideSplashActivityProvider, this.provideSplashInteractorProvider));
        this.splashActivityMembersInjector = SplashActivity_MembersInjector.create(this.getSharedPreferencesProvider, LocationSettingsUtil_Factory.create(), this.splashPresenterProvider);
    }

    public void inject(SplashActivity splashActivity) {
        this.splashActivityMembersInjector.injectMembers(splashActivity);
    }

    public static final class Builder {
        /* access modifiers changed from: private */
        public AppComponent appComponent;
        /* access modifiers changed from: private */
        public SplashModule splashModule;

        private Builder() {
        }

        public SplashComponent build() {
            if (this.splashModule == null) {
                throw new IllegalStateException(SplashModule.class.getCanonicalName() + " must be set");
            } else if (this.appComponent != null) {
                return new DaggerSplashComponent(this);
            } else {
                throw new IllegalStateException(AppComponent.class.getCanonicalName() + " must be set");
            }
        }

        public Builder splashModule(SplashModule splashModule2) {
            this.splashModule = (SplashModule) Preconditions.checkNotNull(splashModule2);
            return this;
        }

        public Builder appComponent(AppComponent appComponent2) {
            this.appComponent = (AppComponent) Preconditions.checkNotNull(appComponent2);
            return this;
        }
    }
}
