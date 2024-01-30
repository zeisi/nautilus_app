package com.nautilus.omni.dependencyinjection.modules.settings;

import com.nautilus.omni.appmodules.settings.mainsection.view.SettingsActivity;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class SettingsModule_ProvideSettingsActivityFactory implements Factory<SettingsActivity> {
    static final /* synthetic */ boolean $assertionsDisabled = (!SettingsModule_ProvideSettingsActivityFactory.class.desiredAssertionStatus());
    private final SettingsModule module;

    public SettingsModule_ProvideSettingsActivityFactory(SettingsModule module2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            return;
        }
        throw new AssertionError();
    }

    public SettingsActivity get() {
        return (SettingsActivity) Preconditions.checkNotNull(this.module.provideSettingsActivity(), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<SettingsActivity> create(SettingsModule module2) {
        return new SettingsModule_ProvideSettingsActivityFactory(module2);
    }

    public static SettingsActivity proxyProvideSettingsActivity(SettingsModule instance) {
        return instance.provideSettingsActivity();
    }
}
