package com.nautilus.omni.dependencyinjection.modules.settings;

import com.nautilus.omni.appmodules.settings.mainsection.view.SettingsViewContract;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class SettingsModule_ProvideISettingsActivityFactory implements Factory<SettingsViewContract> {
    static final /* synthetic */ boolean $assertionsDisabled = (!SettingsModule_ProvideISettingsActivityFactory.class.desiredAssertionStatus());
    private final SettingsModule module;

    public SettingsModule_ProvideISettingsActivityFactory(SettingsModule module2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            return;
        }
        throw new AssertionError();
    }

    public SettingsViewContract get() {
        return (SettingsViewContract) Preconditions.checkNotNull(this.module.provideISettingsActivity(), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<SettingsViewContract> create(SettingsModule module2) {
        return new SettingsModule_ProvideISettingsActivityFactory(module2);
    }

    public static SettingsViewContract proxyProvideISettingsActivity(SettingsModule instance) {
        return instance.provideISettingsActivity();
    }
}
