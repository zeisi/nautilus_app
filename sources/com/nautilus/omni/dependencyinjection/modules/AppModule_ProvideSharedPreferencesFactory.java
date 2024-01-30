package com.nautilus.omni.dependencyinjection.modules;

import android.app.Application;
import android.content.SharedPreferences;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class AppModule_ProvideSharedPreferencesFactory implements Factory<SharedPreferences> {
    static final /* synthetic */ boolean $assertionsDisabled = (!AppModule_ProvideSharedPreferencesFactory.class.desiredAssertionStatus());
    private final Provider<Application> applicationProvider;
    private final AppModule module;

    public AppModule_ProvideSharedPreferencesFactory(AppModule module2, Provider<Application> applicationProvider2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            if ($assertionsDisabled || applicationProvider2 != null) {
                this.applicationProvider = applicationProvider2;
                return;
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public SharedPreferences get() {
        return (SharedPreferences) Preconditions.checkNotNull(this.module.provideSharedPreferences(this.applicationProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<SharedPreferences> create(AppModule module2, Provider<Application> applicationProvider2) {
        return new AppModule_ProvideSharedPreferencesFactory(module2, applicationProvider2);
    }

    public static SharedPreferences proxyProvideSharedPreferences(AppModule instance, Application application) {
        return instance.provideSharedPreferences(application);
    }
}
