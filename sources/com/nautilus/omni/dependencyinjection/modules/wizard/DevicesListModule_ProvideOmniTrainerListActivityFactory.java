package com.nautilus.omni.dependencyinjection.modules.wizard;

import com.nautilus.omni.appmodules.connectionwizard.view.OmniTrainerListActivityContract;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class DevicesListModule_ProvideOmniTrainerListActivityFactory implements Factory<OmniTrainerListActivityContract> {
    static final /* synthetic */ boolean $assertionsDisabled = (!DevicesListModule_ProvideOmniTrainerListActivityFactory.class.desiredAssertionStatus());
    private final DevicesListModule module;

    public DevicesListModule_ProvideOmniTrainerListActivityFactory(DevicesListModule module2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            return;
        }
        throw new AssertionError();
    }

    public OmniTrainerListActivityContract get() {
        return (OmniTrainerListActivityContract) Preconditions.checkNotNull(this.module.provideOmniTrainerListActivity(), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<OmniTrainerListActivityContract> create(DevicesListModule module2) {
        return new DevicesListModule_ProvideOmniTrainerListActivityFactory(module2);
    }

    public static OmniTrainerListActivityContract proxyProvideOmniTrainerListActivity(DevicesListModule instance) {
        return instance.provideOmniTrainerListActivity();
    }
}
