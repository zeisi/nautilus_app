package com.nautilus.omni.appmodules.device.presenter.interactors;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import com.nautilus.omni.appmodules.device.presenter.interactors.DeviceInteractorContract;
import com.nautilus.omni.dataaccess.ProductDaoHelper;
import com.nautilus.omni.databasemanager.DataBaseHelper;
import com.nautilus.omni.util.BroadcastKeys;
import javax.inject.Inject;

public class DeviceInteractor implements DeviceInteractorContract {
    private Context mContext;
    private DataBaseHelper mDatabaseHelper;
    private ProductDaoHelper mProductDaoHelper;

    @Inject
    public DeviceInteractor(Context context, DataBaseHelper dataBaseHelper, ProductDaoHelper productDaoHelper) {
        this.mContext = context;
        this.mDatabaseHelper = dataBaseHelper;
        this.mProductDaoHelper = productDaoHelper;
    }

    public void loadDeviceInfo(DeviceInteractorContract.OnDeviceInfoLoadedListener onDeviceInfoLoadedListener) {
        onDeviceInfoLoadedListener.onDeviceInfoLoaded(this.mProductDaoHelper.getProduct());
    }

    public void startSyncProcess() {
        sendStartSyncProcessBroadcast();
    }

    private void sendStartSyncProcessBroadcast() {
        Intent syncStartedIntent = new Intent();
        syncStartedIntent.setAction(BroadcastKeys.START_SYNC_PROCESS);
        LocalBroadcastManager.getInstance(this.mContext).sendBroadcast(syncStartedIntent);
    }

    public void forgetDevice() {
        eraseDatabaseData();
    }

    private void eraseDatabaseData() {
        this.mDatabaseHelper.dropDatabaseData();
    }
}
