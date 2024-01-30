package com.nautilus.omni.appmodules.device.presenter;

public interface DevicePresenterContract {
    void forgetDeviceInfo();

    void loadDeviceInfo();

    void onDestroy();

    void startSyncProcess();
}
