package com.nautilus.omni.dependencyinjection.modules.settings.googlefit;

import com.nautilus.omni.dataaccess.GoogleFitDaoHelper;
import com.nautilus.omni.databasemanager.DataBaseHelper;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class GoogleFitModule_ProvideGoogleFitDaoHelperFactory implements Factory<GoogleFitDaoHelper> {
    static final /* synthetic */ boolean $assertionsDisabled = (!GoogleFitModule_ProvideGoogleFitDaoHelperFactory.class.desiredAssertionStatus());
    private final Provider<DataBaseHelper> dataBaseHelperProvider;
    private final GoogleFitModule module;

    public GoogleFitModule_ProvideGoogleFitDaoHelperFactory(GoogleFitModule module2, Provider<DataBaseHelper> dataBaseHelperProvider2) {
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

    public static Factory<GoogleFitDaoHelper> create(GoogleFitModule module2, Provider<DataBaseHelper> dataBaseHelperProvider2) {
        return new GoogleFitModule_ProvideGoogleFitDaoHelperFactory(module2, dataBaseHelperProvider2);
    }

    public static GoogleFitDaoHelper proxyProvideGoogleFitDaoHelper(GoogleFitModule instance, DataBaseHelper dataBaseHelper) {
        return instance.provideGoogleFitDaoHelper(dataBaseHelper);
    }
}
