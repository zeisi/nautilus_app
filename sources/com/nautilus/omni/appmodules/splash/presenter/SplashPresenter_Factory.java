package com.nautilus.omni.appmodules.splash.presenter;

import android.content.Context;
import com.nautilus.omni.appmodules.splash.presenter.interactor.SplashInteractorContract;
import com.nautilus.omni.appmodules.splash.view.SplashViewContract;
import dagger.MembersInjector;
import dagger.internal.Factory;
import dagger.internal.MembersInjectors;
import javax.inject.Provider;

public final class SplashPresenter_Factory implements Factory<SplashPresenter> {
    static final /* synthetic */ boolean $assertionsDisabled = (!SplashPresenter_Factory.class.desiredAssertionStatus());
    private final Provider<Context> contextProvider;
    private final Provider<SplashViewContract> splashActivityProvider;
    private final Provider<SplashInteractorContract> splashInteractorProvider;
    private final MembersInjector<SplashPresenter> splashPresenterMembersInjector;

    public SplashPresenter_Factory(MembersInjector<SplashPresenter> splashPresenterMembersInjector2, Provider<Context> contextProvider2, Provider<SplashInteractorContract> splashInteractorProvider2, Provider<SplashViewContract> splashActivityProvider2) {
        if ($assertionsDisabled || splashPresenterMembersInjector2 != null) {
            this.splashPresenterMembersInjector = splashPresenterMembersInjector2;
            if ($assertionsDisabled || contextProvider2 != null) {
                this.contextProvider = contextProvider2;
                if ($assertionsDisabled || splashInteractorProvider2 != null) {
                    this.splashInteractorProvider = splashInteractorProvider2;
                    if ($assertionsDisabled || splashActivityProvider2 != null) {
                        this.splashActivityProvider = splashActivityProvider2;
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

    public SplashPresenter get() {
        return (SplashPresenter) MembersInjectors.injectMembers(this.splashPresenterMembersInjector, new SplashPresenter(this.contextProvider.get(), this.splashInteractorProvider.get(), this.splashActivityProvider.get()));
    }

    public static Factory<SplashPresenter> create(MembersInjector<SplashPresenter> splashPresenterMembersInjector2, Provider<Context> contextProvider2, Provider<SplashInteractorContract> splashInteractorProvider2, Provider<SplashViewContract> splashActivityProvider2) {
        return new SplashPresenter_Factory(splashPresenterMembersInjector2, contextProvider2, splashInteractorProvider2, splashActivityProvider2);
    }
}
