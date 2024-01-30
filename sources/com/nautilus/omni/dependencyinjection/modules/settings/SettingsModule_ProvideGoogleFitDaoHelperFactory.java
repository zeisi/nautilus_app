package com.nautilus.omni.dependencyinjection.modules.settings;

import com.nautilus.omni.dataaccess.GoogleFitDaoHelper;
import com.nautilus.omni.databasemanager.DataBaseHelper;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class SettingsModule_ProvideGoogleFitDaoHelperFactory implements Factory<GoogleFitDaoHelper> {
    static final /* synthetic */ boolean $assertionsDisabled = (!SettingsModule_ProvideGoogleFitDaoHelperFactory.class.desiredAssertionStatus());
    private final Provider<DataBaseHelper> dataBaseHelperProvider;
    private final SettingsModule module;

    public SettingsModule_ProvideGoogleFitDaoHelperFactory(SettingsModule module2, Provider<DataBaseHelper> dataBaseHelperProvider2) {
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

    public GoogleFitDaoHelper get() {
        return (GoogleFitDaoHelper) Preconditions.checkNotNull(this.module.provideGoogleFitDaoHelper(this.dataBaseHelperProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<GoogleFitDaoHelper> create(SettingsModule module2, Provider<DataBaseHelper> dataBaseHelperProvider2) {
        return new SettingsModule_ProvideGoogleFitDaoHelperFactory(module2, dataBaseHelperProvider2);
    }

    public static GoogleFitDaoHelper proxyProvideGoogleFitDaoHelper(SettingsModule instance, DataBaseHelper dataBaseHelper) {
        return instance.provideGoogleFitDaoHelper(dataBaseHelper);
    }
}
