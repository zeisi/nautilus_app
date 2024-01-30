package com.nautilus.omni.dependencyinjection.modules.wizard;

import com.nautilus.omni.appmodules.connectionwizard.view.ConnectionWizardActivity;
import com.nautilus.omni.permissions.PermissionAction;
import com.nautilus.omni.permissions.PermissionPresenter;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class ConnectionModule_ProvidePermissionPresenterFactory implements Factory<PermissionPresenter> {
    static final /* synthetic */ boolean $assertionsDisabled = (!ConnectionModule_ProvidePermissionPresenterFactory.class.desiredAssertionStatus());
    private final Provider<ConnectionWizardActivity> connectionWizardActivityProvider;
    private final ConnectionModule module;
    private final Provider<PermissionAction> permissionActionProvider;

    public ConnectionModule_ProvidePermissionPresenterFactory(ConnectionModule module2, Provider<PermissionAction> permissionActionProvider2, Provider<ConnectionWizardActivity> connectionWizardActivityProvider2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            if ($assertionsDisabled || permissionActionProvider2 != null) {
                this.permissionActionProvider = permissionActionProvider2;
                if ($assertionsDisabled || connectionWizardActivityProvider2 != null) {
                    this.connectionWizardActivityProvider = connectionWizardActivityProvider2;
                    return;
                }
                throw new AssertionError();
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public PermissionPresenter get() {
        return (PermissionPresenter) Preconditions.checkNotNull(this.module.providePermissionPresenter(this.permissionActionProvider.get(), this.connectionWizardActivityProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<PermissionPresenter> create(ConnectionModule module2, Provider<PermissionAction> permissionActionProvider2, Provider<ConnectionWizardActivity> connectionWizardActivityProvider2) {
        return new ConnectionModule_ProvidePermissionPresenterFactory(module2, permissionActionProvider2, connectionWizardActivityProvider2);
    }

    public static PermissionPresenter proxyProvidePermissionPresenter(ConnectionModule instance, PermissionAction permissionAction, ConnectionWizardActivity connectionWizardActivity) {
        return instance.providePermissionPresenter(permissionAction, connectionWizardActivity);
    }
}
