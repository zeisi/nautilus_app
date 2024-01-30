package com.nautilus.omni.dependencyinjection.modules.main;

import android.content.Context;
import com.nautilus.omni.appmodules.home.domain.helpers.IGaugeHelper;
import com.nautilus.omni.appmodules.home.domain.interactors.GaugeInteractorContract;
import com.nautilus.omni.appmodules.home.presenter.GaugePresenterContract;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class HomeModule_ProvideIGaugePresenterFactory implements Factory<GaugePresenterContract> {
    static final /* synthetic */ boolean $assertionsDisabled = (!HomeModule_ProvideIGaugePresenterFactory.class.desiredAssertionStatus());
    private final Provider<Context> contextProvider;
    private final Provider<IGaugeHelper> iGaugeHelperProvider;
    private final Provider<GaugeInteractorContract> iGaugeInteractorProvider;
    private final HomeModule module;

    public HomeModule_ProvideIGaugePresenterFactory(HomeModule module2, Provider<Context> contextProvider2, Provider<GaugeInteractorContract> iGaugeInteractorProvider2, Provider<IGaugeHelper> iGaugeHelperProvider2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            if ($assertionsDisabled || contextProvider2 != null) {
                this.contextProvider = contextProvider2;
                if ($assertionsDisabled || iGaugeInteractorProvider2 != null) {
                    this.iGaugeInteractorProvider = iGaugeInteractorProvider2;
                    if ($assertionsDisabled || iGaugeHelperProvider2 != null) {
                        this.iGaugeHelperProvider = iGaugeHelperProvider2;
                        return;
                    }
                    throw new AssertionError();
                }
                throw new AssertionError();
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public GaugePresenterContract get() {
        return (GaugePresenterContract) Preconditions.checkNotNull(this.module.provideIGaugePresenter(this.contextProvider.get(), this.iGaugeInteractorProvider.get(), this.iGaugeHelperProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<GaugePresenterContract> create(HomeModule module2, Provider<Context> contextProvider2, Provider<GaugeInteractorContract> iGaugeInteractorProvider2, Provider<IGaugeHelper> iGaugeHelperProvider2) {
        return new HomeModule_ProvideIGaugePresenterFactory(module2, contextProvider2, iGaugeInteractorProvider2, iGaugeHelperProvider2);
    }

    public static GaugePresenterContract proxyProvideIGaugePresenter(HomeModule instance, Context context, GaugeInteractorContract iGaugeInteractor, IGaugeHelper iGaugeHelper) {
        return instance.provideIGaugePresenter(context, iGaugeInteractor, iGaugeHelper);
    }
}
