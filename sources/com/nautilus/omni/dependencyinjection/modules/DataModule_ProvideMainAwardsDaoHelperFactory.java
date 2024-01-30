package com.nautilus.omni.dependencyinjection.modules;

import android.content.Context;
import com.nautilus.omni.dataaccess.awards.MainAwardsDaoHelper;
import com.nautilus.omni.databasemanager.DataBaseHelper;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class DataModule_ProvideMainAwardsDaoHelperFactory implements Factory<MainAwardsDaoHelper> {
    static final /* synthetic */ boolean $assertionsDisabled = (!DataModule_ProvideMainAwardsDaoHelperFactory.class.desiredAssertionStatus());
    private final Provider<Context> contextProvider;
    private final Provider<DataBaseHelper> dataBaseHelperProvider;
    private final DataModule module;

    public DataModule_ProvideMainAwardsDaoHelperFactory(DataModule module2, Provider<Context> contextProvider2, Provider<DataBaseHelper> dataBaseHelperProvider2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            if ($assertionsDisabled || contextProvider2 != null) {
                this.contextProvider = contextProvider2;
                if ($assertionsDisabled || dataBaseHelperProvider2 != null) {
                    this.dataBaseHelperProvider = dataBaseHelperProvider2;
                    return;
                }
                throw new AssertionError();
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public MainAwardsDaoHelper get() {
        return (MainAwardsDaoHelper) Preconditions.checkNotNull(this.module.provideMainAwardsDaoHelper(this.contextProvider.get(), this.dataBaseHelperProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<MainAwardsDaoHelper> create(DataModule module2, Provider<Context> contextProvider2, Provider<DataBaseHelper> dataBaseHelperProvider2) {
        return new DataModule_ProvideMainAwardsDaoHelperFactory(module2, contextProvider2, dataBaseHelperProvider2);
    }

    public static MainAwardsDaoHelper proxyProvideMainAwardsDaoHelper(DataModule instance, Context context, DataBaseHelper dataBaseHelper) {
        return instance.provideMainAwardsDaoHelper(context, dataBaseHelper);
    }
}
