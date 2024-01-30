package com.nautilus.omni.dependencyinjection.modules.wizard;

import com.nautilus.omni.appmodules.connectionwizard.view.ConnectionWizardActivity;
import com.nautilus.omni.permissions.PermissionAction;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class ConnectionModule_ProvidePermissionActionFactory implements Factory<PermissionAction> {
    static final /* synthetic */ boolean $assertionsDisabled = (!ConnectionModule_ProvidePermissionActionFactory.class.desiredAssertionStatus());
    private final Provider<ConnectionWizardActivity> connectionWizardActivityProvider;
    private final ConnectionModule module;

    public ConnectionModule_ProvidePermissionActionFactory(ConnectionModule module2, Provider<ConnectionWizardActivity> connectionWizardActivityProvider2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            if ($assertionsDisabled || connectionWizardActivityProvider2 != null) {
                this.connectionWizardActivityProvider = connectionWizardActivityProvider2;
                return;
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public PermissionAction get() {
        return (PermissionAction) Preconditions.checkNotNull(this.module.providePermissionAction(this.connectionWizardActivityProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<PermissionAction> create(ConnectionModule module2, Provider<ConnectionWizardActivity> connectionWizardActivityProvider2) {
        return new ConnectionModule_ProvidePermissionActionFactory(module2, connectionWizardActivityProvider2);
    }

    public static PermissionAction proxyProvidePermissionAction(ConnectionModule instance, ConnectionWizardActivity connectionWizardActivity) {
        return instance.providePermissionAction(connectionWizardActivity);
    }
}
