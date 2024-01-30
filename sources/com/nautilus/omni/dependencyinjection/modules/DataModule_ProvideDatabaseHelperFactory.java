package com.nautilus.omni.dependencyinjection.modules;

import android.content.Context;
import com.nautilus.omni.databasemanager.DataBaseHelper;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class DataModule_ProvideDatabaseHelperFactory implements Factory<DataBaseHelper> {
    static final /* synthetic */ boolean $assertionsDisabled = (!DataModule_ProvideDatabaseHelperFactory.class.desiredAssertionStatus());
    private final Provider<Context> contextProvider;
    private final DataModule module;

    public DataModule_ProvideDatabaseHelperFactory(DataModule module2, Provider<Context> contextProvider2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            if ($assertionsDisabled || contextProvider2 != null) {
                this.contextProvider = contextProvider2;
                return;
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public DataBaseHelper get() {
        return (DataBaseHelper) Preconditions.checkNotNull(this.module.provideDatabaseHelper(this.contextProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<DataBaseHelper> create(DataModule module2, Provider<Context> contextProvider2) {
        return new DataModule_ProvideDatabaseHelperFactory(module2, contextProvider2);
    }

    public static DataBaseHelper proxyProvideDatabaseHelper(DataModule instance, Context context) {
        return instance.provideDatabaseHelper(context);
    }
}
