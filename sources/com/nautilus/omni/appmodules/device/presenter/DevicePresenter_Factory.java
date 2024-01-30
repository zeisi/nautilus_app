package com.nautilus.omni.appmodules.device.presenter;

import android.content.Context;
import com.nautilus.omni.appmodules.device.presenter.interactors.DeviceInteractor;
import dagger.MembersInjector;
import dagger.internal.Factory;
import dagger.internal.MembersInjectors;
import javax.inject.Provider;

public final class DevicePresenter_Factory implements Factory<DevicePresenter> {
    static final /* synthetic */ boolean $assertionsDisabled = (!DevicePresenter_Factory.class.desiredAssertionStatus());
    private final Provider<Context> contextProvider;
    private final Provider<DeviceInteractor> deviceInteractorProvider;
    private final MembersInjector<DevicePresenter> devicePresenterMembersInjector;

    public DevicePresenter_Factory(MembersInjector<DevicePresenter> devicePresenterMembersInjector2, Provider<Context> contextProvider2, Provider<DeviceInteractor> deviceInteractorProvider2) {
        if ($assertionsDisabled || devicePresenterMembersInjector2 != null) {
            this.devicePresenterMembersInjector = devicePresenterMembersInjector2;
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
        return (DevicePresenter) MembersInjectors.injectMembers(this.devicePresenterMembersInjector, new DevicePresenter(this.contextProvider.get(), this.deviceInteractorProvider.get()));
    }

    public static Factory<DevicePresenter> create(MembersInjector<DevicePresenter> devicePresenterMembersInjector2, Provider<Context> contextProvider2, Provider<DeviceInteractor> deviceInteractorProvider2) {
        return new DevicePresenter_Factory(devicePresenterMembersInjector2, contextProvider2, deviceInteractorProvider2);
    }
}
