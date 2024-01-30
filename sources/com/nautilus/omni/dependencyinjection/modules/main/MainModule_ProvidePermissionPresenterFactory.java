package com.nautilus.omni.dependencyinjection.modules.main;

import com.nautilus.omni.appmodules.mainactivity.view.MainActivity;
import com.nautilus.omni.permissions.PermissionAction;
import com.nautilus.omni.permissions.PermissionPresenter;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class MainModule_ProvidePermissionPresenterFactory implements Factory<PermissionPresenter> {
    static final /* synthetic */ boolean $assertionsDisabled = (!MainModule_ProvidePermissionPresenterFactory.class.desiredAssertionStatus());
    private final Provider<MainActivity> mainActivityProvider;
    private final MainModule module;
    private final Provider<PermissionAction> permissionActionProvider;

    public MainModule_ProvidePermissionPresenterFactory(MainModule module2, Provider<PermissionAction> permissionActionProvider2, Provider<MainActivity> mainActivityProvider2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            if ($assertionsDisabled || permissionActionProvider2 != null) {
                this.permissionActionProvider = permissionActionProvider2;
                if ($assertionsDisabled || mainActivityProvider2 != null) {
                    this.mainActivityProvider = mainActivityProvider2;
                    return;
                }
                throw new AssertionError();
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public PermissionPresenter get() {
        return (PermissionPresenter) Preconditions.checkNotNull(this.module.providePermissionPresenter(this.permissionActionProvider.get(), this.mainActivityProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<PermissionPresenter> create(MainModule module2, Provider<PermissionAction> permissionActionProvider2, Provider<MainActivity> mainActivityProvider2) {
        return new MainModule_ProvidePermissionPresenterFactory(module2, permissionActionProvider2, mainActivityProvider2);
    }

    public static PermissionPresenter proxyProvidePermissionPresenter(MainModule instance, PermissionAction permissionAction, MainActivity mainActivity) {
        return instance.providePermissionPresenter(permissionAction, mainActivity);
    }
}
