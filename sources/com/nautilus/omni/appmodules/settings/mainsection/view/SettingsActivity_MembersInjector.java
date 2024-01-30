package com.nautilus.omni.appmodules.settings.mainsection.view;

import android.content.SharedPreferences;
import com.nautilus.omni.app.BaseActivity_MembersInjector;
import com.nautilus.omni.appmodules.settings.mainsection.presenter.SettingsPresenterContract;
import com.nautilus.omni.permissions.PermissionPresenter;
import com.nautilus.omni.util.LocationSettingsUtil;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class SettingsActivity_MembersInjector implements MembersInjector<SettingsActivity> {
    static final /* synthetic */ boolean $assertionsDisabled = (!SettingsActivity_MembersInjector.class.desiredAssertionStatus());
    private final Provider<LocationSettingsUtil> mLocationSettingsUtilProvider;
    private final Provider<PermissionPresenter> mPermissionPresenterProvider;
    private final Provider<SettingsPresenterContract> mSettingsPresenterProvider;
    private final Provider<SharedPreferences> mSettingsProvider;

    public SettingsActivity_MembersInjector(Provider<SharedPreferences> mSettingsProvider2, Provider<LocationSettingsUtil> mLocationSettingsUtilProvider2, Provider<SettingsPresenterContract> mSettingsPresenterProvider2, Provider<PermissionPresenter> mPermissionPresenterProvider2) {
        if ($assertionsDisabled || mSettingsProvider2 != null) {
            this.mSettingsProvider = mSettingsProvider2;
            if ($assertionsDisabled || mLocationSettingsUtilProvider2 != null) {
                this.mLocationSettingsUtilProvider = mLocationSettingsUtilProvider2;
                if ($assertionsDisabled || mSettingsPresenterProvider2 != null) {
                    this.mSettingsPresenterProvider = mSettingsPresenterProvider2;
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

    public static MembersInjector<SettingsActivity> create(Provider<SharedPreferences> mSettingsProvider2, Provider<LocationSettingsUtil> mLocationSettingsUtilProvider2, Provider<SettingsPresenterContract> mSettingsPresenterProvider2, Provider<PermissionPresenter> mPermissionPresenterProvider2) {
        return new SettingsActivity_MembersInjector(mSettingsProvider2, mLocationSettingsUtilProvider2, mSettingsPresenterProvider2, mPermissionPresenterProvider2);
    }

    public void injectMembers(SettingsActivity instance) {
        if (instance == null) {
            throw new NullPointerException("Cannot inject members into a null reference");
        }
        instance.mSettings = this.mSettingsProvider.get();
        BaseActivity_MembersInjector.injectMLocationSettingsUtil(instance, this.mLocationSettingsUtilProvider);
        instance.mSettingsPresenter = this.mSettingsPresenterProvider.get();
        instance.mPermissionPresenter = this.mPermissionPresenterProvider.get();
    }

    public static void injectMSettingsPresenter(SettingsActivity instance, Provider<SettingsPresenterContract> mSettingsPresenterProvider2) {
        instance.mSettingsPresenter = mSettingsPresenterProvider2.get();
    }

    public static void injectMPermissionPresenter(SettingsActivity instance, Provider<PermissionPresenter> mPermissionPresenterProvider2) {
        instance.mPermissionPresenter = mPermissionPresenterProvider2.get();
    }
}
