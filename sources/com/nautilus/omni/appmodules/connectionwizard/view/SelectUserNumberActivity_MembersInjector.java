package com.nautilus.omni.appmodules.connectionwizard.view;

import android.content.SharedPreferences;
import com.nautilus.omni.app.BaseActivity_MembersInjector;
import com.nautilus.omni.appmodules.connectionwizard.presenter.UserSelectPresenterContract;
import com.nautilus.omni.util.LocationSettingsUtil;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class SelectUserNumberActivity_MembersInjector implements MembersInjector<SelectUserNumberActivity> {
    static final /* synthetic */ boolean $assertionsDisabled = (!SelectUserNumberActivity_MembersInjector.class.desiredAssertionStatus());
    private final Provider<LocationSettingsUtil> mLocationSettingsUtilProvider;
    private final Provider<SharedPreferences> mSettingsProvider;
    private final Provider<UserSelectPresenterContract> userSelectPresenterProvider;

    public SelectUserNumberActivity_MembersInjector(Provider<SharedPreferences> mSettingsProvider2, Provider<LocationSettingsUtil> mLocationSettingsUtilProvider2, Provider<UserSelectPresenterContract> userSelectPresenterProvider2) {
        if ($assertionsDisabled || mSettingsProvider2 != null) {
            this.mSettingsProvider = mSettingsProvider2;
            if ($assertionsDisabled || mLocationSettingsUtilProvider2 != null) {
                this.mLocationSettingsUtilProvider = mLocationSettingsUtilProvider2;
                if ($assertionsDisabled || userSelectPresenterProvider2 != null) {
                    this.userSelectPresenterProvider = userSelectPresenterProvider2;
                    return;
                }
                throw new AssertionError();
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public static MembersInjector<SelectUserNumberActivity> create(Provider<SharedPreferences> mSettingsProvider2, Provider<LocationSettingsUtil> mLocationSettingsUtilProvider2, Provider<UserSelectPresenterContract> userSelectPresenterProvider2) {
        return new SelectUserNumberActivity_MembersInjector(mSettingsProvider2, mLocationSettingsUtilProvider2, userSelectPresenterProvider2);
    }

    public void injectMembers(SelectUserNumberActivity instance) {
        if (instance == null) {
            throw new NullPointerException("Cannot inject members into a null reference");
        }
        instance.mSettings = this.mSettingsProvider.get();
        BaseActivity_MembersInjector.injectMLocationSettingsUtil(instance, this.mLocationSettingsUtilProvider);
        instance.userSelectPresenter = this.userSelectPresenterProvider.get();
    }

    public static void injectUserSelectPresenter(SelectUserNumberActivity instance, Provider<UserSelectPresenterContract> userSelectPresenterProvider2) {
        instance.userSelectPresenter = userSelectPresenterProvider2.get();
    }
}
