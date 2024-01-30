package com.nautilus.omni.dependencyinjection.modules;

import com.nautilus.omni.dataaccess.WorkoutDaoHelper;
import com.nautilus.omni.databasemanager.DataBaseHelper;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class DataModule_ProvideWorkoutDaoHelperFactory implements Factory<WorkoutDaoHelper> {
    static final /* synthetic */ boolean $assertionsDisabled = (!DataModule_ProvideWorkoutDaoHelperFactory.class.desiredAssertionStatus());
    private final Provider<DataBaseHelper> dataBaseHelperProvider;
    private final DataModule module;

    public DataModule_ProvideWorkoutDaoHelperFactory(DataModule module2, Provider<DataBaseHelper> dataBaseHelperProvider2) {
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

    public WorkoutDaoHelper get() {
        return (WorkoutDaoHelper) Preconditions.checkNotNull(this.module.provideWorkoutDaoHelper(this.dataBaseHelperProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<WorkoutDaoHelper> create(DataModule module2, Provider<DataBaseHelper> dataBaseHelperProvider2) {
        return new DataModule_ProvideWorkoutDaoHelperFactory(module2, dataBaseHelperProvider2);
    }

    public static WorkoutDaoHelper proxyProvideWorkoutDaoHelper(DataModule instance, DataBaseHelper dataBaseHelper) {
        return instance.provideWorkoutDaoHelper(dataBaseHelper);
    }
}
