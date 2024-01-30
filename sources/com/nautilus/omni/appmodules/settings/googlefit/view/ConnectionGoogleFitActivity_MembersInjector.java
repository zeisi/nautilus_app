package com.nautilus.omni.appmodules.settings.googlefit.view;

import android.content.SharedPreferences;
import com.nautilus.omni.app.BaseActivity_MembersInjector;
import com.nautilus.omni.appmodules.settings.googlefit.presenter.GoogleFitPresenterContract;
import com.nautilus.omni.util.LocationSettingsUtil;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class ConnectionGoogleFitActivity_MembersInjector implements MembersInjector<ConnectionGoogleFitActivity> {
    static final /* synthetic */ boolean $assertionsDisabled = (!ConnectionGoogleFitActivity_MembersInjector.class.desiredAssertionStatus());
    private final Provider<GoogleFitPresenterContract> mGoogleFitPresenterProvider;
    private final Provider<LocationSettingsUtil> mLocationSettingsUtilProvider;
    private final Provider<SharedPreferences> mSettingsProvider;

    public ConnectionGoogleFitActivity_MembersInjector(Provider<SharedPreferences> mSettingsProvider2, Provider<LocationSettingsUtil> mLocationSettingsUtilProvider2, Provider<GoogleFitPresenterContract> mGoogleFitPresenterProvider2) {
        if ($assertionsDisabled || mSettingsProvider2 != null) {
            this.mSettingsProvider = mSettingsProvider2;
            if ($assertionsDisabled || mLocationSettingsUtilProvider2 != null) {
                this.mLocationSettingsUtilProvider = mLocationSettingsUtilProvider2;
                if ($assertionsDisabled || mGoogleFitPresenterProvider2 != null) {
                    this.mGoogleFitPresenterProvider = mGoogleFitPresenterProvider2;
                    return;
                }
                throw new AssertionError();
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public static MembersInjector<ConnectionGoogleFitActivity> create(Provider<SharedPreferences> mSettingsProvider2, Provider<LocationSettingsUtil> mLocationSettingsUtilProvider2, Provider<GoogleFitPresenterContract> mGoogleFitPresenterProvider2) {
        return new ConnectionGoogleFitActivity_MembersInjector(mSettingsProvider2, mLocationSettingsUtilProvider2, mGoogleFitPresenterProvider2);
    }

    public void injectMembers(ConnectionGoogleFitActivity instance) {
        if (instance == null) {
            throw new NullPointerException("Cannot inject members into a null reference");
        }
        instance.mSettings = this.mSettingsProvider.get();
        BaseActivity_MembersInjector.injectMLocationSettingsUtil(instance, this.mLocationSettingsUtilProvider);
        instance.mGoogleFitPresenter = this.mGoogleFitPresenterProvider.get();
    }

    public static void injectMGoogleFitPresenter(ConnectionGoogleFitActivity instance, Provider<GoogleFitPresenterContract> mGoogleFitPresenterProvider2) {
        instance.mGoogleFitPresenter = mGoogleFitPresenterProvider2.get();
    }
}
