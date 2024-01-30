package com.nautilus.omni.permissions;

import dagger.internal.Factory;
import javax.inject.Provider;

public final class PermissionPresenter_Factory implements Factory<PermissionPresenter> {
    static final /* synthetic */ boolean $assertionsDisabled = (!PermissionPresenter_Factory.class.desiredAssertionStatus());
    private final Provider<PermissionAction> permissionActionProvider;
    private final Provider<PermissionCallbacks> permissionCallbacksProvider;

    public PermissionPresenter_Factory(Provider<PermissionAction> permissionActionProvider2, Provider<PermissionCallbacks> permissionCallbacksProvider2) {
        if ($assertionsDisabled || permissionActionProvider2 != null) {
            this.permissionActionProvider = permissionActionProvider2;
            if ($assertionsDisabled || permissionCallbacksProvider2 != null) {
                this.permissionCallbacksProvider = permissionCallbacksProvider2;
                return;
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public PermissionPresenter get() {
        return new PermissionPresenter(this.permissionActionProvider.get(), this.permissionCallbacksProvider.get());
    }

    public static Factory<PermissionPresenter> create(Provider<PermissionAction> permissionActionProvider2, Provider<PermissionCallbacks> permissionCallbacksProvider2) {
        return new PermissionPresenter_Factory(permissionActionProvider2, permissionCallbacksProvider2);
    }
}
