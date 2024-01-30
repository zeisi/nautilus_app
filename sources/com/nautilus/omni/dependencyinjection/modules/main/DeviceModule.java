package com.nautilus.omni.dependencyinjection.modules.main;

import android.content.Context;
import com.nautilus.omni.appmodules.device.presenter.DevicePresenter;
import com.nautilus.omni.appmodules.device.presenter.interactors.DeviceInteractor;
import com.nautilus.omni.appmodules.device.view.DeviceFragment;
import com.nautilus.omni.dataaccess.ProductDaoHelper;
import com.nautilus.omni.databasemanager.DataBaseHelper;
import com.nautilus.omni.dependencyinjection.scopes.ActivityScope;
import dagger.Module;
import dagger.Provides;

@Module
public class DeviceModule {
    DeviceFragment mDeviceView;

    public DeviceModule(DeviceFragment deviceView) {
        this.mDeviceView = deviceView;
    }

    /* access modifiers changed from: package-private */
    @ActivityScope
    @Provides
    public DeviceFragment provideDeviceView() {
        return this.mDeviceView;
    }

    /* access modifiers changed from: package-private */
    @ActivityScope
    @Provides
    public DeviceInteractor provideDeviceInteractor(Context context, DataBaseHelper dataBaseHelper, ProductDaoHelper productDaoHelper) {
        return new DeviceInteractor(context, dataBaseHelper, productDaoHelper);
    }

    /* access modifiers changed from: package-private */
    @ActivityScope
    @Provides
    public DevicePresenter provideDevicePresenter(Context context, DeviceInteractor deviceInteractor) {
        return new DevicePresenter(context, deviceInteractor);
    }
}
