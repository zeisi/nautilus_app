package com.nautilus.omni.dependencyinjection.modules;

import com.nautilus.omni.databasemanager.DataBaseHelper;
import com.nautilus.omni.syncservices.syncserviceshelpers.FitServicesSyncDataHelper;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class DataModule_ProvideFitServicesDaoHelperFactory implements Factory<FitServicesSyncDataHelper> {
    static final /* synthetic */ boolean $assertionsDisabled = (!DataModule_ProvideFitServicesDaoHelperFactory.class.desiredAssertionStatus());
    private final Provider<DataBaseHelper> dataBaseHelperProvider;
    private final DataModule module;

    public DataModule_ProvideFitServicesDaoHelperFactory(DataModule module2, Provider<DataBaseHelper> dataBaseHelperProvider2) {
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

    public FitServicesSyncDataHelper get() {
        return (FitServicesSyncDataHelper) Preconditions.checkNotNull(this.module.provideFitServicesDaoHelper(this.dataBaseHelperProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<FitServicesSyncDataHelper> create(DataModule module2, Provider<DataBaseHelper> dataBaseHelperProvider2) {
        return new DataModule_ProvideFitServicesDaoHelperFactory(module2, dataBaseHelperProvider2);
    }

    public static FitServicesSyncDataHelper proxyProvideFitServicesDaoHelper(DataModule instance, DataBaseHelper dataBaseHelper) {
        return instance.provideFitServicesDaoHelper(dataBaseHelper);
    }
}
