package com.nautilus.omni.dependencyinjection.modules;

import android.content.Context;
import com.nautilus.omni.dataaccess.AwardsDaoHelper;
import com.nautilus.omni.databasemanager.DataBaseHelper;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class DataModule_ProvideAwardsDaoHelperFactory implements Factory<AwardsDaoHelper> {
    static final /* synthetic */ boolean $assertionsDisabled = (!DataModule_ProvideAwardsDaoHelperFactory.class.desiredAssertionStatus());
    private final Provider<Context> contextProvider;
    private final Provider<DataBaseHelper> dataBaseHelperProvider;
    private final DataModule module;

    public DataModule_ProvideAwardsDaoHelperFactory(DataModule module2, Provider<Context> contextProvider2, Provider<DataBaseHelper> dataBaseHelperProvider2) {
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

    public AwardsDaoHelper get() {
        return (AwardsDaoHelper) Preconditions.checkNotNull(this.module.provideAwardsDaoHelper(this.contextProvider.get(), this.dataBaseHelperProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<AwardsDaoHelper> create(DataModule module2, Provider<Context> contextProvider2, Provider<DataBaseHelper> dataBaseHelperProvider2) {
        return new DataModule_ProvideAwardsDaoHelperFactory(module2, contextProvider2, dataBaseHelperProvider2);
    }

    public static AwardsDaoHelper proxyProvideAwardsDaoHelper(DataModule instance, Context context, DataBaseHelper dataBaseHelper) {
        return instance.provideAwardsDaoHelper(context, dataBaseHelper);
    }
}
