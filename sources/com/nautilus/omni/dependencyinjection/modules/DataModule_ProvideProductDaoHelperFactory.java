package com.nautilus.omni.dependencyinjection.modules;

import com.nautilus.omni.dataaccess.ProductDaoHelper;
import com.nautilus.omni.databasemanager.DataBaseHelper;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class DataModule_ProvideProductDaoHelperFactory implements Factory<ProductDaoHelper> {
    static final /* synthetic */ boolean $assertionsDisabled = (!DataModule_ProvideProductDaoHelperFactory.class.desiredAssertionStatus());
    private final Provider<DataBaseHelper> dataBaseHelperProvider;
    private final DataModule module;

    public DataModule_ProvideProductDaoHelperFactory(DataModule module2, Provider<DataBaseHelper> dataBaseHelperProvider2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            if ($assertionsDisabled || dataBaseHelperProvider2 != null) {
                this.dataBaseHelperProvider = dataBaseHelperProvider2;
                return;
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public ProductDaoHelper get() {
        return (ProductDaoHelper) Preconditions.checkNotNull(this.module.provideProductDaoHelper(this.dataBaseHelperProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<ProductDaoHelper> create(DataModule module2, Provider<DataBaseHelper> dataBaseHelperProvider2) {
        return new DataModule_ProvideProductDaoHelperFactory(module2, dataBaseHelperProvider2);
    }

    public static ProductDaoHelper proxyProvideProductDaoHelper(DataModule instance, DataBaseHelper dataBaseHelper) {
        return instance.provideProductDaoHelper(dataBaseHelper);
    }
}
