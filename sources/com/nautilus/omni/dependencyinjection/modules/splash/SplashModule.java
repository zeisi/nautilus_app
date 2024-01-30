package com.nautilus.omni.dependencyinjection.modules.splash;

import android.content.Context;
import com.nautilus.omni.appmodules.splash.presenter.SplashPresenter;
import com.nautilus.omni.appmodules.splash.presenter.SplashPresenterContract;
import com.nautilus.omni.appmodules.splash.presenter.interactor.SplashInteractor;
import com.nautilus.omni.appmodules.splash.presenter.interactor.SplashInteractorContract;
import com.nautilus.omni.appmodules.splash.view.SplashViewContract;
import com.nautilus.omni.dataaccess.awards.MainAwardsDaoHelper;
import com.nautilus.omni.dependencyinjection.scopes.ActivityScope;
import dagger.Module;
import dagger.Provides;

@Module
public class SplashModule {
    SplashViewContract splashActivity;

    public SplashModule(SplashViewContract splashActivity2) {
        this.splashActivity = splashActivity2;
    }

    /* access modifiers changed from: package-private */
    @ActivityScope
    @Provides
    public SplashViewContract provideSplashActivity() {
        return this.splashActivity;
    }

    /* access modifiers changed from: package-private */
    @ActivityScope
    @Provides
    public SplashInteractorContract provideSplashInteractor(MainAwardsDaoHelper mainAwardsDaoHelper) {
        return new SplashInteractor(mainAwardsDaoHelper);
    }

    /* access modifiers changed from: package-private */
    @ActivityScope
    @Provides
    public SplashPresenterContract splashPresenter(Context context, SplashViewContract splashActivity2, SplashInteractorContract iSplashInteractor) {
        return new SplashPresenter(context, iSplashInteractor, splashActivity2);
    }
}
