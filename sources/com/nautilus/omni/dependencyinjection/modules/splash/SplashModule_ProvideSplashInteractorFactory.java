package com.nautilus.omni.dependencyinjection.modules.splash;

import com.nautilus.omni.appmodules.splash.presenter.interactor.SplashInteractorContract;
import com.nautilus.omni.dataaccess.awards.MainAwardsDaoHelper;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class SplashModule_ProvideSplashInteractorFactory implements Factory<SplashInteractorContract> {
    static final /* synthetic */ boolean $assertionsDisabled = (!SplashModule_ProvideSplashInteractorFactory.class.desiredAssertionStatus());
    private final Provider<MainAwardsDaoHelper> mainAwardsDaoHelperProvider;
    private final SplashModule module;

    public SplashModule_ProvideSplashInteractorFactory(SplashModule module2, Provider<MainAwardsDaoHelper> mainAwardsDaoHelperProvider2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            if ($assertionsDisabled || mainAwardsDaoHelperProvider2 != null) {
                this.mainAwardsDaoHelperProvider = mainAwardsDaoHelperProvider2;
                return;
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public SplashInteractorContract get() {
        return (SplashInteractorContract) Preconditions.checkNotNull(this.module.provideSplashInteractor(this.mainAwardsDaoHelperProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<SplashInteractorContract> create(SplashModule module2, Provider<MainAwardsDaoHelper> mainAwardsDaoHelperProvider2) {
        return new SplashModule_ProvideSplashInteractorFactory(module2, mainAwardsDaoHelperProvider2);
    }

    public static SplashInteractorContract proxyProvideSplashInteractor(SplashModule instance, MainAwardsDaoHelper mainAwardsDaoHelper) {
        return instance.provideSplashInteractor(mainAwardsDaoHelper);
    }
}
