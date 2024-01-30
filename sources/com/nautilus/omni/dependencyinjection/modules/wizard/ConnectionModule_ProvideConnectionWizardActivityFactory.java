package com.nautilus.omni.dependencyinjection.modules.wizard;

import com.nautilus.omni.appmodules.connectionwizard.view.ConnectionWizardActivity;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class ConnectionModule_ProvideConnectionWizardActivityFactory implements Factory<ConnectionWizardActivity> {
    static final /* synthetic */ boolean $assertionsDisabled = (!ConnectionModule_ProvideConnectionWizardActivityFactory.class.desiredAssertionStatus());
    private final ConnectionModule module;

    public ConnectionModule_ProvideConnectionWizardActivityFactory(ConnectionModule module2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            return;
        }
        throw new AssertionError();
    }

    public ConnectionWizardActivity get() {
        return (ConnectionWizardActivity) Preconditions.checkNotNull(this.module.provideConnectionWizardActivity(), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<ConnectionWizardActivity> create(ConnectionModule module2) {
        return new ConnectionModule_ProvideConnectionWizardActivityFactory(module2);
    }

    public static ConnectionWizardActivity proxyProvideConnectionWizardActivity(ConnectionModule instance) {
        return instance.provideConnectionWizardActivity();
    }
}
