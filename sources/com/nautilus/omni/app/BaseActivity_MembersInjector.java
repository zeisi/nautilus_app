package com.nautilus.omni.app;

import android.content.SharedPreferences;
import com.nautilus.omni.util.LocationSettingsUtil;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class BaseActivity_MembersInjector implements MembersInjector<BaseActivity> {
    static final /* synthetic */ boolean $assertionsDisabled = (!BaseActivity_MembersInjector.class.desiredAssertionStatus());
    private final Provider<LocationSettingsUtil> mLocationSettingsUtilProvider;
    private final Provider<SharedPreferences> mSettingsProvider;

    public BaseActivity_MembersInjector(Provider<SharedPreferences> mSettingsProvider2, Provider<LocationSettingsUtil> mLocationSettingsUtilProvider2) {
        if ($assertionsDisabled || mSettingsProvider2 != null) {
            this.mSettingsProvider = mSettingsProvider2;
            if ($assertionsDisabled || mLocationSettingsUtilProvider2 != null) {
                this.mLocationSettingsUtilProvider = mLocationSettingsUtilProvider2;
                return;
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public static MembersInjector<BaseActivity> create(Provider<SharedPreferences> mSettingsProvider2, Provider<LocationSettingsUtil> mLocationSettingsUtilProvider2) {
        return new BaseActivity_MembersInjector(mSettingsProvider2, mLocationSettingsUtilProvider2);
    }

    public void injectMembers(BaseActivity instance) {
        if (instance == null) {
            throw new NullPointerException("Cannot inject members into a null reference");
        }
        instance.mSettings = this.mSettingsProvider.get();
        instance.mLocationSettingsUtil = this.mLocationSettingsUtilProvider.get();
    }

    public static void injectMLocationSettingsUtil(BaseActivity instance, Provider<LocationSettingsUtil> mLocationSettingsUtilProvider2) {
        instance.mLocationSettingsUtil = mLocationSettingsUtilProvider2.get();
    }
}
