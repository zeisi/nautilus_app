package com.nautilus.omni.dependencyinjection.modules.settings.googlefit;

import com.nautilus.omni.appmodules.settings.googlefit.view.ConnectionGoogleFitActivity;
import com.nautilus.omni.permissions.PermissionAction;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class GoogleFitModule_ProvidePermissionActionFactory implements Factory<PermissionAction> {
    static final /* synthetic */ boolean $assertionsDisabled = (!GoogleFitModule_ProvidePermissionActionFactory.class.desiredAssertionStatus());
    private final Provider<ConnectionGoogleFitActivity> mainActivityProvider;
    private final GoogleFitModule module;

    public GoogleFitModule_ProvidePermissionActionFactory(GoogleFitModule module2, Provider<ConnectionGoogleFitActivity> mainActivityProvider2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            if ($assertionsDisabled || mainActivityProvider2 != null) {
                this.mainActivityProvider = mainActivityProvider2;
                return;
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public PermissionAction get() {
        return (PermissionAction) Preconditions.checkNotNull(this.module.providePermissionAction(this.mainActivityProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<PermissionAction> create(GoogleFitModule module2, Provider<ConnectionGoogleFitActivity> mainActivityProvider2) {
        return new GoogleFitModule_ProvidePermissionActionFactory(module2, mainActivityProvider2);
    }

    public static PermissionAction proxyProvidePermissionAction(GoogleFitModule instance, ConnectionGoogleFitActivity mainActivity) {
        return instance.providePermissionAction(mainActivity);
    }
}
