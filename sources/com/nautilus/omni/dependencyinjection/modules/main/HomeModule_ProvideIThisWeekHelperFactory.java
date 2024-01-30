package com.nautilus.omni.dependencyinjection.modules.main;

import android.content.Context;
import com.nautilus.omni.appmodules.home.domain.helpers.IThisWeekHelper;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class HomeModule_ProvideIThisWeekHelperFactory implements Factory<IThisWeekHelper> {
    static final /* synthetic */ boolean $assertionsDisabled = (!HomeModule_ProvideIThisWeekHelperFactory.class.desiredAssertionStatus());
    private final Provider<Context> contextProvider;
    private final HomeModule module;

    public HomeModule_ProvideIThisWeekHelperFactory(HomeModule module2, Provider<Context> contextProvider2) {
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

    public IThisWeekHelper get() {
        return (IThisWeekHelper) Preconditions.checkNotNull(this.module.provideIThisWeekHelper(this.contextProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<IThisWeekHelper> create(HomeModule module2, Provider<Context> contextProvider2) {
        return new HomeModule_ProvideIThisWeekHelperFactory(module2, contextProvider2);
    }

    public static IThisWeekHelper proxyProvideIThisWeekHelper(HomeModule instance, Context context) {
        return instance.provideIThisWeekHelper(context);
    }
}
