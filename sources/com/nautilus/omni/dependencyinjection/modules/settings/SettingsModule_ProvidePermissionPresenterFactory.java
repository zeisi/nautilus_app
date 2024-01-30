package com.nautilus.omni.dependencyinjection.modules.settings;

import com.nautilus.omni.appmodules.settings.mainsection.view.SettingsActivity;
import com.nautilus.omni.permissions.PermissionAction;
import com.nautilus.omni.permissions.PermissionPresenter;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class SettingsModule_ProvidePermissionPresenterFactory implements Factory<PermissionPresenter> {
    static final /* synthetic */ boolean $assertionsDisabled = (!SettingsModule_ProvidePermissionPresenterFactory.class.desiredAssertionStatus());
    private final Provider<SettingsActivity> awardTypesActivityProvider;
    private final SettingsModule module;
    private final Provider<PermissionAction> permissionActionProvider;

    public SettingsModule_ProvidePermissionPresenterFactory(SettingsModule module2, Provider<PermissionAction> permissionActionProvider2, Provider<SettingsActivity> awardTypesActivityProvider2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            if ($assertionsDisabled || permissionActionProvider2 != null) {
                this.permissionActionProvider = permissionActionProvider2;
                if ($assertionsDisabled || awardTypesActivityProvider2 != null) {
                    this.awardTypesActivityProvider = awardTypesActivityProvider2;
                    return;
                }
                throw new AssertionError();
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public PermissionPresenter get() {
        return (PermissionPresenter) Preconditions.checkNotNull(this.module.providePermissionPresenter(this.permissionActionProvider.get(), this.awardTypesActivityProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<PermissionPresenter> create(SettingsModule module2, Provider<PermissionAction> permissionActionProvider2, Provider<SettingsActivity> awardTypesActivityProvider2) {
        return new SettingsModule_ProvidePermissionPresenterFactory(module2, permissionActionProvider2, awardTypesActivityProvider2);
    }

    public static PermissionPresenter proxyProvidePermissionPresenter(SettingsModule instance, PermissionAction permissionAction, SettingsActivity awardTypesActivity) {
        return instance.providePermissionPresenter(permissionAction, awardTypesActivity);
    }
}
