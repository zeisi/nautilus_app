package com.nautilus.omni.appmodules.splash.view;

import android.content.SharedPreferences;
import com.nautilus.omni.app.BaseActivity_MembersInjector;
import com.nautilus.omni.appmodules.splash.presenter.SplashPresenterContract;
import com.nautilus.omni.util.LocationSettingsUtil;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class SplashActivity_MembersInjector implements MembersInjector<SplashActivity> {
    static final /* synthetic */ boolean $assertionsDisabled = (!SplashActivity_MembersInjector.class.desiredAssertionStatus());
    private final Provider<LocationSettingsUtil> mLocationSettingsUtilProvider;
    private final Provider<SharedPreferences> mSettingsProvider;
    private final Provider<SplashPresenterContract> splashPresenterProvider;

    public SplashActivity_MembersInjector(Provider<SharedPreferences> mSettingsProvider2, Provider<LocationSettingsUtil> mLocationSettingsUtilProvider2, Provider<SplashPresenterContract> splashPresenterProvider2) {
        if ($assertionsDisabled || mSettingsProvider2 != null) {
            this.mSettingsProvider = mSettingsProvider2;
            if ($assertionsDisabled || mLocationSettingsUtilProvider2 != null) {
                this.mLocationSettingsUtilProvider = mLocationSettingsUtilProvider2;
                if ($assertionsDisabled || splashPresenterProvider2 != null) {
                    this.splashPresenterProvider = splashPresenterProvider2;
                    return;
                }
                throw new AssertionError();
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public static MembersInjector<SplashActivity> create(Provider<SharedPreferences> mSettingsProvider2, Provider<LocationSettingsUtil> mLocationSettingsUtilProvider2, Provider<SplashPresenterContract> splashPresenterProvider2) {
        return new SplashActivity_MembersInjector(mSettingsProvider2, mLocationSettingsUtilProvider2, splashPresenterProvider2);
    }

    public void injectMembers(SplashActivity instance) {
        if (instance == null) {
            throw new NullPointerException("Cannot inject members into a null reference");
        }
        instance.mSettings = this.mSettingsProvider.get();
        BaseActivity_MembersInjector.injectMLocationSettingsUtil(instance, this.mLocationSettingsUtilProvider);
        instance.splashPresenter = this.splashPresenterProvider.get();
    }

    public static void injectSplashPresenter(SplashActivity instance, Provider<SplashPresenterContract> splashPresenterProvider2) {
        instance.splashPresenter = splashPresenterProvider2.get();
    }
}
