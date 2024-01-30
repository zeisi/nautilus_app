package com.nautilus.omni.appmodules.connectionwizard.view;

import android.content.SharedPreferences;
import com.nautilus.omni.app.BaseActivity_MembersInjector;
import com.nautilus.omni.appmodules.connectionwizard.presenter.DevicesListPresenterContract;
import com.nautilus.omni.util.LocationSettingsUtil;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class OmniTrainerListActivity_MembersInjector implements MembersInjector<OmniTrainerListActivity> {
    static final /* synthetic */ boolean $assertionsDisabled = (!OmniTrainerListActivity_MembersInjector.class.desiredAssertionStatus());
    private final Provider<DevicesListPresenterContract> devicesListPresenterProvider;
    private final Provider<LocationSettingsUtil> mLocationSettingsUtilProvider;
    private final Provider<SharedPreferences> mSettingsProvider;

    public OmniTrainerListActivity_MembersInjector(Provider<SharedPreferences> mSettingsProvider2, Provider<LocationSettingsUtil> mLocationSettingsUtilProvider2, Provider<DevicesListPresenterContract> devicesListPresenterProvider2) {
        if ($assertionsDisabled || mSettingsProvider2 != null) {
            this.mSettingsProvider = mSettingsProvider2;
            if ($assertionsDisabled || mLocationSettingsUtilProvider2 != null) {
                this.mLocationSettingsUtilProvider = mLocationSettingsUtilProvider2;
                if ($assertionsDisabled || devicesListPresenterProvider2 != null) {
                    this.devicesListPresenterProvider = devicesListPresenterProvider2;
                    return;
                }
                throw new AssertionError();
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public static MembersInjector<OmniTrainerListActivity> create(Provider<SharedPreferences> mSettingsProvider2, Provider<LocationSettingsUtil> mLocationSettingsUtilProvider2, Provider<DevicesListPresenterContract> devicesListPresenterProvider2) {
        return new OmniTrainerListActivity_MembersInjector(mSettingsProvider2, mLocationSettingsUtilProvider2, devicesListPresenterProvider2);
    }

    public void injectMembers(OmniTrainerListActivity instance) {
        if (instance == null) {
            throw new NullPointerException("Cannot inject members into a null reference");
        }
        instance.mSettings = this.mSettingsProvider.get();
        BaseActivity_MembersInjector.injectMLocationSettingsUtil(instance, this.mLocationSettingsUtilProvider);
        instance.devicesListPresenter = this.devicesListPresenterProvider.get();
    }

    public static void injectDevicesListPresenter(OmniTrainerListActivity instance, Provider<DevicesListPresenterContract> devicesListPresenterProvider2) {
        instance.devicesListPresenter = devicesListPresenterProvider2.get();
    }
}
