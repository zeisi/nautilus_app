package com.nautilus.omni.dependencyinjection.modules.main;

import android.content.Context;
import com.nautilus.omni.appmodules.device.presenter.interactors.DeviceInteractor;
import com.nautilus.omni.dataaccess.ProductDaoHelper;
import com.nautilus.omni.databasemanager.DataBaseHelper;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class DeviceModule_ProvideDeviceInteractorFactory implements Factory<DeviceInteractor> {
    static final /* synthetic */ boolean $assertionsDisabled = (!DeviceModule_ProvideDeviceInteractorFactory.class.desiredAssertionStatus());
    private final Provider<Context> contextProvider;
    private final Provider<DataBaseHelper> dataBaseHelperProvider;
    private final DeviceModule module;
    private final Provider<ProductDaoHelper> productDaoHelperProvider;

    public DeviceModule_ProvideDeviceInteractorFactory(DeviceModule module2, Provider<Context> contextProvider2, Provider<DataBaseHelper> dataBaseHelperProvider2, Provider<ProductDaoHelper> productDaoHelperProvider2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
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
        throw new AssertionError();
    }

    public DeviceInteractor get() {
        return (DeviceInteractor) Preconditions.checkNotNull(this.module.provideDeviceInteractor(this.contextProvider.get(), this.dataBaseHelperProvider.get(), this.productDaoHelperProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<DeviceInteractor> create(DeviceModule module2, Provider<Context> contextProvider2, Provider<DataBaseHelper> dataBaseHelperProvider2, Provider<ProductDaoHelper> productDaoHelperProvider2) {
        return new DeviceModule_ProvideDeviceInteractorFactory(module2, contextProvider2, dataBaseHelperProvider2, productDaoHelperProvider2);
    }

    public static DeviceInteractor proxyProvideDeviceInteractor(DeviceModule instance, Context context, DataBaseHelper dataBaseHelper, ProductDaoHelper productDaoHelper) {
        return instance.provideDeviceInteractor(context, dataBaseHelper, productDaoHelper);
    }
}
