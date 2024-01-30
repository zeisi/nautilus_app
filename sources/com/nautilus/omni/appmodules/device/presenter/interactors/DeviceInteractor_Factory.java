package com.nautilus.omni.appmodules.device.presenter.interactors;

import android.content.Context;
import com.nautilus.omni.dataaccess.ProductDaoHelper;
import com.nautilus.omni.databasemanager.DataBaseHelper;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class DeviceInteractor_Factory implements Factory<DeviceInteractor> {
    static final /* synthetic */ boolean $assertionsDisabled = (!DeviceInteractor_Factory.class.desiredAssertionStatus());
    private final Provider<Context> contextProvider;
    private final Provider<DataBaseHelper> dataBaseHelperProvider;
    private final Provider<ProductDaoHelper> productDaoHelperProvider;

    public DeviceInteractor_Factory(Provider<Context> contextProvider2, Provider<DataBaseHelper> dataBaseHelperProvider2, Provider<ProductDaoHelper> productDaoHelperProvider2) {
        if ($assertionsDisabled || contextProvider2 != null) {
            this.contextProvider = contextProvider2;
            if ($assertionsDisabled || dataBaseHelperProvider2 != null) {
                this.dataBaseHelperProvider = dataBaseHelperProvider2;
                if ($assertionsDisabled || productDaoHelperProvider2 != null) {
                    this.productDaoHelperProvider = productDaoHelperProvider2;
                    return;
                }
                throw new AssertionError();
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public DeviceInteractor get() {
        return new DeviceInteractor(this.contextProvider.get(), this.dataBaseHelperProvider.get(), this.productDaoHelperProvider.get());
    }

    public static Factory<DeviceInteractor> create(Provider<Context> contextProvider2, Provider<DataBaseHelper> dataBaseHelperProvider2, Provider<ProductDaoHelper> productDaoHelperProvider2) {
        return new DeviceInteractor_Factory(contextProvider2, dataBaseHelperProvider2, productDaoHelperProvider2);
    }
}
