package com.nautilus.omni.dependencyinjection.modules.settings;

import com.nautilus.omni.appmodules.settings.mainsection.view.SettingsActivity;
import com.nautilus.omni.permissions.PermissionAction;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class SettingsModule_ProvidePermissionActionFactory implements Factory<PermissionAction> {
    static final /* synthetic */ boolean $assertionsDisabled = (!SettingsModule_ProvidePermissionActionFactory.class.desiredAssertionStatus());
    private final Provider<SettingsActivity> awardTypesActivityProvider;
    private final SettingsModule module;

    public SettingsModule_ProvidePermissionActionFactory(SettingsModule module2, Provider<SettingsActivity> awardTypesActivityProvider2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            if ($assertionsDisabled || awardTypesActivityProvider2 != null) {
                this.awardTypesActivityProvider = awardTypesActivityProvider2;
                return;
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public PermissionAction get() {
        return (PermissionAction) Preconditions.checkNotNull(this.module.providePermissionAction(this.awardTypesActivityProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<PermissionAction> create(SettingsModule module2, Provider<SettingsActivity> awardTypesActivityProvider2) {
        return new SettingsModule_ProvidePermissionActionFactory(module2, awardTypesActivityProvider2);
    }

    public static PermissionAction proxyProvidePermissionAction(SettingsModule instance, SettingsActivity awardTypesActivity) {
        return instance.providePermissionAction(awardTypesActivity);
    }
}
