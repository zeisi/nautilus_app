package com.nautilus.omni.appmodules.device.presenter.interactors;

import com.nautilus.omni.model.dto.Product;

public interface DeviceInteractorContract {

    public interface OnDeviceInfoLoadedListener {
        void onDeviceInfoLoaded(Product product);
    }

    void forgetDevice();

    void loadDeviceInfo(OnDeviceInfoLoadedListener onDeviceInfoLoadedListener);

    void startSyncProcess();
}
