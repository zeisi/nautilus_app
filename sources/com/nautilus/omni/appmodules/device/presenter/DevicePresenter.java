package com.nautilus.omni.appmodules.device.presenter;

import android.content.Context;
import com.nautilus.omni.app.BasePresenter;
import com.nautilus.omni.app.Preferences;
import com.nautilus.omni.appmodules.device.presenter.interactors.DeviceInteractor;
import com.nautilus.omni.appmodules.device.presenter.interactors.DeviceInteractorContract;
import com.nautilus.omni.appmodules.device.view.IDeviceView;
import com.nautilus.omni.model.dto.Product;
import com.nautilus.omni.model.dto.User;
import javax.inject.Inject;

public class DevicePresenter extends BasePresenter implements DevicePresenterContract, DeviceInteractorContract.OnDeviceInfoLoadedListener {
    private User mCurrentUser;
    private DeviceInteractor mDeviceInteractor;
    private IDeviceView mIDeviceView;
    private int mUserIndex;

    @Inject
    public DevicePresenter(Context context, DeviceInteractor deviceInteractor) {
        super(context);
        getCurrentUserFromDatabase();
        this.mDeviceInteractor = deviceInteractor;
    }

    public void setIDeviceView(IDeviceView deviceView) {
        this.mIDeviceView = deviceView;
    }

    private void getCurrentUserFromDatabase() {
        this.mUserIndex = this.mPreferences.getInt(Preferences.USER_INDEX, -1);
        this.mCurrentUser = this.mUserDaoHelper.getCurrentUser(this.mUserIndex);
    }

    public void loadDeviceInfo() {
        this.mDeviceInteractor.loadDeviceInfo(this);
    }

    public void onDeviceInfoLoaded(Product product) {
        this.mIDeviceView.showDeviceInfo(product);
    }

    public void startSyncProcess() {
        this.mDeviceInteractor.startSyncProcess();
    }

    public void forgetDeviceInfo() {
        this.mDeviceInteractor.forgetDevice();
        Preferences.resetPreferences(this.mPreferences);
    }

    public User getCurrentUser() {
        return this.mCurrentUser;
    }

    public void onDestroy() {
    }
}
