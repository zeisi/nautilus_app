package com.nautilus.omni.dependencyinjection.modules.settings.googlefit;

import com.nautilus.omni.appmodules.settings.googlefit.view.ConnectionGoogleFitActivity;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class GoogleFitModule_ProvideConnectionGoogleFitActivityFactory implements Factory<ConnectionGoogleFitActivity> {
    static final /* synthetic */ boolean $assertionsDisabled = (!GoogleFitModule_ProvideConnectionGoogleFitActivityFactory.class.desiredAssertionStatus());
    private final GoogleFitModule module;

    public GoogleFitModule_ProvideConnectionGoogleFitActivityFactory(GoogleFitModule module2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            return;
        }
        throw new AssertionError();
    }

    public ConnectionGoogleFitActivity get() {
        return (ConnectionGoogleFitActivity) Preconditions.checkNotNull(this.module.provideConnectionGoogleFitActivity(), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<ConnectionGoogleFitActivity> create(GoogleFitModule module2) {
        return new GoogleFitModule_ProvideConnectionGoogleFitActivityFactory(module2);
    }

    public static ConnectionGoogleFitActivity proxyProvideConnectionGoogleFitActivity(GoogleFitModule instance) {
        return instance.provideConnectionGoogleFitActivity();
    }
}
