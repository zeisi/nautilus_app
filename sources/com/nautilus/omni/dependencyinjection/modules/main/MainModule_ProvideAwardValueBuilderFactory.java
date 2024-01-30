package com.nautilus.omni.dependencyinjection.modules.main;

import android.content.Context;
import com.nautilus.omni.appmodules.awards.AwardValueBuilder;
import com.nautilus.omni.dataaccess.WeekDaoHelper;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class MainModule_ProvideAwardValueBuilderFactory implements Factory<AwardValueBuilder> {
    static final /* synthetic */ boolean $assertionsDisabled = (!MainModule_ProvideAwardValueBuilderFactory.class.desiredAssertionStatus());
    private final Provider<Context> contextProvider;
    private final MainModule module;
    private final Provider<WeekDaoHelper> weekDaoHelperProvider;

    public MainModule_ProvideAwardValueBuilderFactory(MainModule module2, Provider<Context> contextProvider2, Provider<WeekDaoHelper> weekDaoHelperProvider2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            if ($assertionsDisabled || contextProvider2 != null) {
                this.contextProvider = contextProvider2;
                if ($assertionsDisabled || weekDaoHelperProvider2 != null) {
                    this.weekDaoHelperProvider = weekDaoHelperProvider2;
                    return;
                }
                throw new AssertionError();
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public AwardValueBuilder get() {
        return (AwardValueBuilder) Preconditions.checkNotNull(this.module.provideAwardValueBuilder(this.contextProvider.get(), this.weekDaoHelperProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<AwardValueBuilder> create(MainModule module2, Provider<Context> contextProvider2, Provider<WeekDaoHelper> weekDaoHelperProvider2) {
        return new MainModule_ProvideAwardValueBuilderFactory(module2, contextProvider2, weekDaoHelperProvider2);
    }

    public static AwardValueBuilder proxyProvideAwardValueBuilder(MainModule instance, Context context, WeekDaoHelper weekDaoHelper) {
        return instance.provideAwardValueBuilder(context, weekDaoHelper);
    }
}
