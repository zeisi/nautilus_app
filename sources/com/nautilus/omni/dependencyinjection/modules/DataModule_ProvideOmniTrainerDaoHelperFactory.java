package com.nautilus.omni.dependencyinjection.modules;

import com.nautilus.omni.dataaccess.OmniTrainerDaoHelper;
import com.nautilus.omni.databasemanager.DataBaseHelper;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class DataModule_ProvideOmniTrainerDaoHelperFactory implements Factory<OmniTrainerDaoHelper> {
    static final /* synthetic */ boolean $assertionsDisabled = (!DataModule_ProvideOmniTrainerDaoHelperFactory.class.desiredAssertionStatus());
    private final Provider<DataBaseHelper> dataBaseHelperProvider;
    private final DataModule module;

    public DataModule_ProvideOmniTrainerDaoHelperFactory(DataModule module2, Provider<DataBaseHelper> dataBaseHelperProvider2) {
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

    public OmniTrainerDaoHelper get() {
        return (OmniTrainerDaoHelper) Preconditions.checkNotNull(this.module.provideOmniTrainerDaoHelper(this.dataBaseHelperProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<OmniTrainerDaoHelper> create(DataModule module2, Provider<DataBaseHelper> dataBaseHelperProvider2) {
        return new DataModule_ProvideOmniTrainerDaoHelperFactory(module2, dataBaseHelperProvider2);
    }

    public static OmniTrainerDaoHelper proxyProvideOmniTrainerDaoHelper(DataModule instance, DataBaseHelper dataBaseHelper) {
        return instance.provideOmniTrainerDaoHelper(dataBaseHelper);
    }
}
