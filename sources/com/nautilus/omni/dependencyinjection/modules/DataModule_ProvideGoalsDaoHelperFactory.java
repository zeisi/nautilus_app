package com.nautilus.omni.dependencyinjection.modules;

import com.nautilus.omni.dataaccess.GoalsDaoHelper;
import com.nautilus.omni.databasemanager.DataBaseHelper;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class DataModule_ProvideGoalsDaoHelperFactory implements Factory<GoalsDaoHelper> {
    static final /* synthetic */ boolean $assertionsDisabled = (!DataModule_ProvideGoalsDaoHelperFactory.class.desiredAssertionStatus());
    private final Provider<DataBaseHelper> dataBaseHelperProvider;
    private final DataModule module;

    public DataModule_ProvideGoalsDaoHelperFactory(DataModule module2, Provider<DataBaseHelper> dataBaseHelperProvider2) {
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

    public GoalsDaoHelper get() {
        return (GoalsDaoHelper) Preconditions.checkNotNull(this.module.provideGoalsDaoHelper(this.dataBaseHelperProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<GoalsDaoHelper> create(DataModule module2, Provider<DataBaseHelper> dataBaseHelperProvider2) {
        return new DataModule_ProvideGoalsDaoHelperFactory(module2, dataBaseHelperProvider2);
    }

    public static GoalsDaoHelper proxyProvideGoalsDaoHelper(DataModule instance, DataBaseHelper dataBaseHelper) {
        return instance.provideGoalsDaoHelper(dataBaseHelper);
    }
}
