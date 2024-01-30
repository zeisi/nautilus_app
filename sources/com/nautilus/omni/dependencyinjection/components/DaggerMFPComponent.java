package com.nautilus.omni.dependencyinjection.components;

import android.content.Context;
import android.content.SharedPreferences;
import com.nautilus.omni.appmodules.settings.myfitnesspal.presenter.MyFitnessPalPresenterContract;
import com.nautilus.omni.appmodules.settings.myfitnesspal.view.MyFitnessPalConnectionActivity;
import com.nautilus.omni.appmodules.settings.myfitnesspal.view.MyFitnessPalConnectionActivity_MembersInjector;
import com.nautilus.omni.appmodules.settings.myfitnesspal.view.MyFitnessPalDisconnectionActivity;
import com.nautilus.omni.appmodules.settings.myfitnesspal.view.MyFitnessPalDisconnectionActivity_MembersInjector;
import com.nautilus.omni.dependencyinjection.modules.settings.MyFitnessPal.MFPModule;
import com.nautilus.omni.dependencyinjection.modules.settings.MyFitnessPal.MFPModule_ProvideMyFitnessPalPresenterFactory;
import com.nautilus.omni.util.LocationSettingsUtil_Factory;
import dagger.MembersInjector;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class DaggerMFPComponent implements MFPComponent {
    static final /* synthetic */ boolean $assertionsDisabled = (!DaggerMFPComponent.class.desiredAssertionStatus());
    private Provider<Context> contextProvider;
    private Provider<SharedPreferences> getSharedPreferencesProvider;
    private MembersInjector<MyFitnessPalConnectionActivity> myFitnessPalConnectionActivityMembersInjector;
    private MembersInjector<MyFitnessPalDisconnectionActivity> myFitnessPalDisconnectionActivityMembersInjector;
    private Provider<MyFitnessPalPresenterContract> provideMyFitnessPalPresenterProvider;

    private DaggerMFPComponent(Builder builder) {
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
        this.provideMyFitnessPalPresenterProvider = DoubleCheck.provider(MFPModule_ProvideMyFitnessPalPresenterFactory.create(builder.mFPModule, this.contextProvider));
        this.myFitnessPalConnectionActivityMembersInjector = MyFitnessPalConnectionActivity_MembersInjector.create(this.getSharedPreferencesProvider, LocationSettingsUtil_Factory.create(), this.provideMyFitnessPalPresenterProvider);
        this.myFitnessPalDisconnectionActivityMembersInjector = MyFitnessPalDisconnectionActivity_MembersInjector.create(this.getSharedPreferencesProvider, LocationSettingsUtil_Factory.create(), this.provideMyFitnessPalPresenterProvider);
    }

    public void inject(MyFitnessPalConnectionActivity myFitnessPalConnectionActivity) {
        this.myFitnessPalConnectionActivityMembersInjector.injectMembers(myFitnessPalConnectionActivity);
    }

    public void inject(MyFitnessPalDisconnectionActivity myFitnessPalDisconnectionActivity) {
        this.myFitnessPalDisconnectionActivityMembersInjector.injectMembers(myFitnessPalDisconnectionActivity);
    }

    public static final class Builder {
        /* access modifiers changed from: private */
        public AppComponent appComponent;
        /* access modifiers changed from: private */
        public MFPModule mFPModule;

        private Builder() {
        }

        public MFPComponent build() {
            if (this.mFPModule == null) {
                this.mFPModule = new MFPModule();
            }
            if (this.appComponent != null) {
                return new DaggerMFPComponent(this);
            }
            throw new IllegalStateException(AppComponent.class.getCanonicalName() + " must be set");
        }

        public Builder mFPModule(MFPModule mFPModule2) {
            this.mFPModule = (MFPModule) Preconditions.checkNotNull(mFPModule2);
            return this;
        }

        public Builder appComponent(AppComponent appComponent2) {
            this.appComponent = (AppComponent) Preconditions.checkNotNull(appComponent2);
            return this;
        }
    }
}
