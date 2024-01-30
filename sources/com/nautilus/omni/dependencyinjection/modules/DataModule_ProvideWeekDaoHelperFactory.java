package com.nautilus.omni.dependencyinjection.modules;

import com.nautilus.omni.dataaccess.WeekDaoHelper;
import com.nautilus.omni.databasemanager.DataBaseHelper;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class DataModule_ProvideWeekDaoHelperFactory implements Factory<WeekDaoHelper> {
    static final /* synthetic */ boolean $assertionsDisabled = (!DataModule_ProvideWeekDaoHelperFactory.class.desiredAssertionStatus());
    private final Provider<DataBaseHelper> dataBaseHelperProvider;
    private final DataModule module;

    public DataModule_ProvideWeekDaoHelperFactory(DataModule module2, Provider<DataBaseHelper> dataBaseHelperProvider2) {
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

    public WeekDaoHelper get() {
        return (WeekDaoHelper) Preconditions.checkNotNull(this.module.provideWeekDaoHelper(this.dataBaseHelperProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<WeekDaoHelper> create(DataModule module2, Provider<DataBaseHelper> dataBaseHelperProvider2) {
        return new DataModule_ProvideWeekDaoHelperFactory(module2, dataBaseHelperProvider2);
    }

    public static WeekDaoHelper proxyProvideWeekDaoHelper(DataModule instance, DataBaseHelper dataBaseHelper) {
        return instance.provideWeekDaoHelper(dataBaseHelper);
    }
}
