package com.nautilus.omni.appmodules.settings.googlefit.view;

import android.content.SharedPreferences;
import com.nautilus.omni.app.BaseActivity_MembersInjector;
import com.nautilus.omni.appmodules.settings.googlefit.presenter.GoogleFitPresenterContract;
import com.nautilus.omni.syncservices.syncserviceshelpers.FitServicesSyncDataHelper;
import com.nautilus.omni.util.LocationSettingsUtil;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class ConfigGoogleFitActivity_MembersInjector implements MembersInjector<ConfigGoogleFitActivity> {
    static final /* synthetic */ boolean $assertionsDisabled = (!ConfigGoogleFitActivity_MembersInjector.class.desiredAssertionStatus());
    private final Provider<FitServicesSyncDataHelper> mFitServicesSyncDataHelperProvider;
    private final Provider<GoogleFitPresenterContract> mGoogleFitPresenterProvider;
    private final Provider<LocationSettingsUtil> mLocationSettingsUtilProvider;
    private final Provider<SharedPreferences> mSettingsProvider;

    public ConfigGoogleFitActivity_MembersInjector(Provider<SharedPreferences> mSettingsProvider2, Provider<LocationSettingsUtil> mLocationSettingsUtilProvider2, Provider<GoogleFitPresenterContract> mGoogleFitPresenterProvider2, Provider<FitServicesSyncDataHelper> mFitServicesSyncDataHelperProvider2) {
        if ($assertionsDisabled || mSettingsProvider2 != null) {
            this.mSettingsProvider = mSettingsProvider2;
            if ($assertionsDisabled || mLocationSettingsUtilProvider2 != null) {
                this.mLocationSettingsUtilProvider = mLocationSettingsUtilProvider2;
                if ($assertionsDisabled || mGoogleFitPresenterProvider2 != null) {
                    this.mGoogleFitPresenterProvider = mGoogleFitPresenterProvider2;
                    if ($assertionsDisabled || mFitServicesSyncDataHelperProvider2 != null) {
                        this.mFitServicesSyncDataHelperProvider = mFitServicesSyncDataHelperProvider2;
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

    public static MembersInjector<ConfigGoogleFitActivity> create(Provider<SharedPreferences> mSettingsProvider2, Provider<LocationSettingsUtil> mLocationSettingsUtilProvider2, Provider<GoogleFitPresenterContract> mGoogleFitPresenterProvider2, Provider<FitServicesSyncDataHelper> mFitServicesSyncDataHelperProvider2) {
        return new ConfigGoogleFitActivity_MembersInjector(mSettingsProvider2, mLocationSettingsUtilProvider2, mGoogleFitPresenterProvider2, mFitServicesSyncDataHelperProvider2);
    }

    public void injectMembers(ConfigGoogleFitActivity instance) {
        if (instance == null) {
            throw new NullPointerException("Cannot inject members into a null reference");
        }
        instance.mSettings = this.mSettingsProvider.get();
        BaseActivity_MembersInjector.injectMLocationSettingsUtil(instance, this.mLocationSettingsUtilProvider);
        instance.mGoogleFitPresenter = this.mGoogleFitPresenterProvider.get();
        instance.mFitServicesSyncDataHelper = this.mFitServicesSyncDataHelperProvider.get();
    }

    public static void injectMGoogleFitPresenter(ConfigGoogleFitActivity instance, Provider<GoogleFitPresenterContract> mGoogleFitPresenterProvider2) {
        instance.mGoogleFitPresenter = mGoogleFitPresenterProvider2.get();
    }

    public static void injectMFitServicesSyncDataHelper(ConfigGoogleFitActivity instance, Provider<FitServicesSyncDataHelper> mFitServicesSyncDataHelperProvider2) {
        instance.mFitServicesSyncDataHelper = mFitServicesSyncDataHelperProvider2.get();
    }
}
