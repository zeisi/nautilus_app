package com.nautilus.omni.dependencyinjection.modules.splash;

import android.content.Context;
import com.nautilus.omni.appmodules.splash.presenter.SplashPresenterContract;
import com.nautilus.omni.appmodules.splash.presenter.interactor.SplashInteractorContract;
import com.nautilus.omni.appmodules.splash.view.SplashViewContract;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class SplashModule_SplashPresenterFactory implements Factory<SplashPresenterContract> {
    static final /* synthetic */ boolean $assertionsDisabled = (!SplashModule_SplashPresenterFactory.class.desiredAssertionStatus());
    private final Provider<Context> contextProvider;
    private final Provider<SplashInteractorContract> iSplashInteractorProvider;
    private final SplashModule module;
    private final Provider<SplashViewContract> splashActivityProvider;

    public SplashModule_SplashPresenterFactory(SplashModule module2, Provider<Context> contextProvider2, Provider<SplashViewContract> splashActivityProvider2, Provider<SplashInteractorContract> iSplashInteractorProvider2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            if ($assertionsDisabled || contextProvider2 != null) {
                this.contextProvider = contextProvider2;
                if ($assertionsDisabled || splashActivityProvider2 != null) {
                    this.splashActivityProvider = splashActivityProvider2;
                    if ($assertionsDisabled || iSplashInteractorProvider2 != null) {
                        this.iSplashInteractorProvider = iSplashInteractorProvider2;
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

    public SplashPresenterContract get() {
        return (SplashPresenterContract) Preconditions.checkNotNull(this.module.splashPresenter(this.contextProvider.get(), this.splashActivityProvider.get(), this.iSplashInteractorProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<SplashPresenterContract> create(SplashModule module2, Provider<Context> contextProvider2, Provider<SplashViewContract> splashActivityProvider2, Provider<SplashInteractorContract> iSplashInteractorProvider2) {
        return new SplashModule_SplashPresenterFactory(module2, contextProvider2, splashActivityProvider2, iSplashInteractorProvider2);
    }

    public static SplashPresenterContract proxySplashPresenter(SplashModule instance, Context context, SplashViewContract splashActivity, SplashInteractorContract iSplashInteractor) {
        return instance.splashPresenter(context, splashActivity, iSplashInteractor);
    }
}
