package com.nautilus.omni.dependencyinjection.modules.main;

import com.nautilus.omni.appmodules.device.view.DeviceFragment;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class DeviceModule_ProvideDeviceViewFactory implements Factory<DeviceFragment> {
    static final /* synthetic */ boolean $assertionsDisabled = (!DeviceModule_ProvideDeviceViewFactory.class.desiredAssertionStatus());
    private final DeviceModule module;

    public DeviceModule_ProvideDeviceViewFactory(DeviceModule module2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            return;
        }
        throw new AssertionError();
    }

    public DeviceFragment get() {
        return (DeviceFragment) Preconditions.checkNotNull(this.module.provideDeviceView(), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<DeviceFragment> create(DeviceModule module2) {
        return new DeviceModule_ProvideDeviceViewFactory(module2);
    }

    public static DeviceFragment proxyProvideDeviceView(DeviceModule instance) {
        return instance.provideDeviceView();
    }
}
