package com.nautilus.omni.dependencyinjection.modules;

import com.nautilus.omni.dataaccess.UserDaoHelper;
import com.nautilus.omni.databasemanager.DataBaseHelper;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class DataModule_ProvideUserDaoHelperFactory implements Factory<UserDaoHelper> {
    static final /* synthetic */ boolean $assertionsDisabled = (!DataModule_ProvideUserDaoHelperFactory.class.desiredAssertionStatus());
    private final Provider<DataBaseHelper> dataBaseHelperProvider;
    private final DataModule module;

    public DataModule_ProvideUserDaoHelperFactory(DataModule module2, Provider<DataBaseHelper> dataBaseHelperProvider2) {
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

    public UserDaoHelper get() {
        return (UserDaoHelper) Preconditions.checkNotNull(this.module.provideUserDaoHelper(this.dataBaseHelperProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<UserDaoHelper> create(DataModule module2, Provider<DataBaseHelper> dataBaseHelperProvider2) {
        return new DataModule_ProvideUserDaoHelperFactory(module2, dataBaseHelperProvider2);
    }

    public static UserDaoHelper proxyProvideUserDaoHelper(DataModule instance, DataBaseHelper dataBaseHelper) {
        return instance.provideUserDaoHelper(dataBaseHelper);
    }
}
