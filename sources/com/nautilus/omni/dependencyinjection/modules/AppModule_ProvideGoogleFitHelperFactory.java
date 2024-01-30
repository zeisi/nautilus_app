package com.nautilus.omni.dependencyinjection.modules;

import android.content.Context;
import com.nautilus.omni.appmodules.settings.googlefit.googlefitcommunication.GoogleFitHelper;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class AppModule_ProvideGoogleFitHelperFactory implements Factory<GoogleFitHelper> {
    static final /* synthetic */ boolean $assertionsDisabled = (!AppModule_ProvideGoogleFitHelperFactory.class.desiredAssertionStatus());
    private final Provider<Context> contextProvider;
    private final AppModule module;

    public AppModule_ProvideGoogleFitHelperFactory(AppModule module2, Provider<Context> contextProvider2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            if ($assertionsDisabled || contextProvider2 != null) {
                this.contextProvider = contextProvider2;
                return;
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public GoogleFitHelper get() {
        return (GoogleFitHelper) Preconditions.checkNotNull(this.module.provideGoogleFitHelper(this.contextProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<GoogleFitHelper> create(AppModule module2, Provider<Context> contextProvider2) {
        return new AppModule_ProvideGoogleFitHelperFactory(module2, contextProvider2);
    }

    public static GoogleFitHelper proxyProvideGoogleFitHelper(AppModule instance, Context context) {
        return instance.provideGoogleFitHelper(context);
    }
}
