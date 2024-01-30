package com.nautilus.omni.dependencyinjection.modules.settings.underarmour;

import android.content.Context;
import com.nautilus.underarmourconnection.database.UnderArmourDatabaseHelper;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class UnderArmourModule_ProvidesUnderArmourDatabaseHelperFactory implements Factory<UnderArmourDatabaseHelper> {
    static final /* synthetic */ boolean $assertionsDisabled = (!UnderArmourModule_ProvidesUnderArmourDatabaseHelperFactory.class.desiredAssertionStatus());
    private final Provider<Context> contextProvider;
    private final UnderArmourModule module;

    public UnderArmourModule_ProvidesUnderArmourDatabaseHelperFactory(UnderArmourModule module2, Provider<Context> contextProvider2) {
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

    public UnderArmourDatabaseHelper get() {
        return (UnderArmourDatabaseHelper) Preconditions.checkNotNull(this.module.providesUnderArmourDatabaseHelper(this.contextProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<UnderArmourDatabaseHelper> create(UnderArmourModule module2, Provider<Context> contextProvider2) {
        return new UnderArmourModule_ProvidesUnderArmourDatabaseHelperFactory(module2, contextProvider2);
    }
}
