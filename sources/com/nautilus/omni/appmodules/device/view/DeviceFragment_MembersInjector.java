package com.nautilus.omni.appmodules.device.view;

import com.nautilus.omni.appmodules.device.presenter.DevicePresenter;
import com.nautilus.omni.permissions.PermissionPresenter;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class DeviceFragment_MembersInjector implements MembersInjector<DeviceFragment> {
    static final /* synthetic */ boolean $assertionsDisabled = (!DeviceFragment_MembersInjector.class.desiredAssertionStatus());
    private final Provider<DevicePresenter> mDevicePresenterProvider;
    private final Provider<PermissionPresenter> mPermissionPresenterProvider;

    public DeviceFragment_MembersInjector(Provider<DevicePresenter> mDevicePresenterProvider2, Provider<PermissionPresenter> mPermissionPresenterProvider2) {
        if ($assertionsDisabled || mDevicePresenterProvider2 != null) {
            this.mDevicePresenterProvider = mDevicePresenterProvider2;
            if ($assertionsDisabled || mPermissionPresenterProvider2 != null) {
                this.mPermissionPresenterProvider = mPermissionPresenterProvider2;
                return;
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public static MembersInjector<DeviceFragment> create(Provider<DevicePresenter> mDevicePresenterProvider2, Provider<PermissionPresenter> mPermissionPresenterProvider2) {
        return new DeviceFragment_MembersInjector(mDevicePresenterProvider2, mPermissionPresenterProvider2);
    }

    public void injectMembers(DeviceFragment instance) {
        if (instance == null) {
            throw new NullPointerException("Cannot inject members into a null reference");
        }
        instance.mDevicePresenter = this.mDevicePresenterProvider.get();
        instance.mPermissionPresenter = this.mPermissionPresenterProvider.get();
    }

    public static void injectMDevicePresenter(DeviceFragment instance, Provider<DevicePresenter> mDevicePresenterProvider2) {
        instance.mDevicePresenter = mDevicePresenterProvider2.get();
    }

    public static void injectMPermissionPresenter(DeviceFragment instance, Provider<PermissionPresenter> mPermissionPresenterProvider2) {
        instance.mPermissionPresenter = mPermissionPresenterProvider2.get();
    }
}
