package com.nautilus.omni.appmodules.mainactivity.view;

import android.content.SharedPreferences;
import com.nautilus.omni.app.BaseActivity_MembersInjector;
import com.nautilus.omni.appmodules.mainactivity.presenter.MainPresenter;
import com.nautilus.omni.permissions.PermissionPresenter;
import com.nautilus.omni.util.LocationSettingsUtil;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class MainActivity_MembersInjector implements MembersInjector<MainActivity> {
    static final /* synthetic */ boolean $assertionsDisabled = (!MainActivity_MembersInjector.class.desiredAssertionStatus());
    private final Provider<LocationSettingsUtil> mLocationSettingsUtilProvider;
    private final Provider<MainPresenter> mMainPresenterProvider;
    private final Provider<PermissionPresenter> mPermissionPresenterProvider;
    private final Provider<SharedPreferences> mSettingsProvider;

    public MainActivity_MembersInjector(Provider<SharedPreferences> mSettingsProvider2, Provider<LocationSettingsUtil> mLocationSettingsUtilProvider2, Provider<MainPresenter> mMainPresenterProvider2, Provider<PermissionPresenter> mPermissionPresenterProvider2) {
        if ($assertionsDisabled || mSettingsProvider2 != null) {
            this.mSettingsProvider = mSettingsProvider2;
            if ($assertionsDisabled || mLocationSettingsUtilProvider2 != null) {
                this.mLocationSettingsUtilProvider = mLocationSettingsUtilProvider2;
                if ($assertionsDisabled || mMainPresenterProvider2 != null) {
                    this.mMainPresenterProvider = mMainPresenterProvider2;
                    if ($assertionsDisabled || mPermissionPresenterProvider2 != null) {
                        this.mPermissionPresenterProvider = mPermissionPresenterProvider2;
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

    public static MembersInjector<MainActivity> create(Provider<SharedPreferences> mSettingsProvider2, Provider<LocationSettingsUtil> mLocationSettingsUtilProvider2, Provider<MainPresenter> mMainPresenterProvider2, Provider<PermissionPresenter> mPermissionPresenterProvider2) {
        return new MainActivity_MembersInjector(mSettingsProvider2, mLocationSettingsUtilProvider2, mMainPresenterProvider2, mPermissionPresenterProvider2);
    }

    public void injectMembers(MainActivity instance) {
        if (instance == null) {
            throw new NullPointerException("Cannot inject members into a null reference");
        }
        instance.mSettings = this.mSettingsProvider.get();
        BaseActivity_MembersInjector.injectMLocationSettingsUtil(instance, this.mLocationSettingsUtilProvider);
        instance.mMainPresenter = this.mMainPresenterProvider.get();
        instance.mPermissionPresenter = this.mPermissionPresenterProvider.get();
    }

    public static void injectMMainPresenter(MainActivity instance, Provider<MainPresenter> mMainPresenterProvider2) {
        instance.mMainPresenter = mMainPresenterProvider2.get();
    }

    public static void injectMPermissionPresenter(MainActivity instance, Provider<PermissionPresenter> mPermissionPresenterProvider2) {
        instance.mPermissionPresenter = mPermissionPresenterProvider2.get();
    }
}
