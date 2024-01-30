package com.nautilus.omni.dependencyinjection.modules;

import com.nautilus.omni.dataaccess.AchievementsDaoHelper;
import com.nautilus.omni.databasemanager.DataBaseHelper;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class DataModule_ProvideAchievementsDaoHelperFactory implements Factory<AchievementsDaoHelper> {
    static final /* synthetic */ boolean $assertionsDisabled = (!DataModule_ProvideAchievementsDaoHelperFactory.class.desiredAssertionStatus());
    private final Provider<DataBaseHelper> dataBaseHelperProvider;
    private final DataModule module;

    public DataModule_ProvideAchievementsDaoHelperFactory(DataModule module2, Provider<DataBaseHelper> dataBaseHelperProvider2) {
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

    public AchievementsDaoHelper get() {
        return (AchievementsDaoHelper) Preconditions.checkNotNull(this.module.provideAchievementsDaoHelper(this.dataBaseHelperProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<AchievementsDaoHelper> create(DataModule module2, Provider<DataBaseHelper> dataBaseHelperProvider2) {
        return new DataModule_ProvideAchievementsDaoHelperFactory(module2, dataBaseHelperProvider2);
    }

    public static AchievementsDaoHelper proxyProvideAchievementsDaoHelper(DataModule instance, DataBaseHelper dataBaseHelper) {
        return instance.provideAchievementsDaoHelper(dataBaseHelper);
    }
}
