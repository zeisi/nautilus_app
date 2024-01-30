package com.nautilus.omni.dependencyinjection.modules.main;

import android.content.Context;
import com.nautilus.omni.appmodules.device.presenter.DevicePresenter;
import com.nautilus.omni.appmodules.device.presenter.interactors.DeviceInteractor;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class DeviceModule_ProvideDevicePresenterFactory implements Factory<DevicePresenter> {
    static final /* synthetic */ boolean $assertionsDisabled = (!DeviceModule_ProvideDevicePresenterFactory.class.desiredAssertionStatus());
    private final Provider<Context> contextProvider;
    private final Provider<DeviceInteractor> deviceInteractorProvider;
    private final DeviceModule module;

    public DeviceModule_ProvideDevicePresenterFactory(DeviceModule module2, Provider<Context> contextProvider2, Provider<DeviceInteractor> deviceInteractorProvider2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            if ($assertionsDisabled || contextProvider2 != null) {
                this.contextProvider = contextProvider2;
                if ($assertionsDisabled || deviceInteractorProvider2 != null) {
                    this.deviceInteractorProvider = deviceInteractorProvider2;
                    return;
                }
                throw new AssertionError();
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public DevicePresenter get() {
        return (DevicePresenter) Preconditions.checkNotNull(this.module.provideDevicePresenter(this.contextProvider.get(), this.deviceInteractorProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<DevicePresenter> create(DeviceModule module2, Provider<Context> contextProvider2, Provider<DeviceInteractor> deviceInteractorProvider2) {
        return new DeviceModule_ProvideDevicePresenterFactory(module2, contextProvider2, deviceInteractorProvider2);
    }

    public static DevicePresenter proxyProvideDevicePresenter(DeviceModule instance, Context context, DeviceInteractor deviceInteractor) {
        return instance.provideDevicePresenter(context, deviceInteractor);
    }
}
