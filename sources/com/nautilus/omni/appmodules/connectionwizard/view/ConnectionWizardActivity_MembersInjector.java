package com.nautilus.omni.appmodules.connectionwizard.view;

import android.content.SharedPreferences;
import com.nautilus.omni.app.BaseActivity_MembersInjector;
import com.nautilus.omni.appmodules.connectionwizard.presenter.WizardPresenter;
import com.nautilus.omni.permissions.PermissionPresenter;
import com.nautilus.omni.util.LocationSettingsUtil;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class ConnectionWizardActivity_MembersInjector implements MembersInjector<ConnectionWizardActivity> {
    static final /* synthetic */ boolean $assertionsDisabled = (!ConnectionWizardActivity_MembersInjector.class.desiredAssertionStatus());
    private final Provider<LocationSettingsUtil> mLocationSettingsUtilProvider;
    private final Provider<PermissionPresenter> mPermissionPresenterProvider;
    private final Provider<SharedPreferences> mSettingsProvider;
    private final Provider<WizardPresenter> mWizardPresenterProvider;

    public ConnectionWizardActivity_MembersInjector(Provider<SharedPreferences> mSettingsProvider2, Provider<LocationSettingsUtil> mLocationSettingsUtilProvider2, Provider<PermissionPresenter> mPermissionPresenterProvider2, Provider<WizardPresenter> mWizardPresenterProvider2) {
        if ($assertionsDisabled || mSettingsProvider2 != null) {
            this.mSettingsProvider = mSettingsProvider2;
            if ($assertionsDisabled || mLocationSettingsUtilProvider2 != null) {
                this.mLocationSettingsUtilProvider = mLocationSettingsUtilProvider2;
                if ($assertionsDisabled || mPermissionPresenterProvider2 != null) {
                    this.mPermissionPresenterProvider = mPermissionPresenterProvider2;
                    if ($assertionsDisabled || mWizardPresenterProvider2 != null) {
                        this.mWizardPresenterProvider = mWizardPresenterProvider2;
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

    public static MembersInjector<ConnectionWizardActivity> create(Provider<SharedPreferences> mSettingsProvider2, Provider<LocationSettingsUtil> mLocationSettingsUtilProvider2, Provider<PermissionPresenter> mPermissionPresenterProvider2, Provider<WizardPresenter> mWizardPresenterProvider2) {
        return new ConnectionWizardActivity_MembersInjector(mSettingsProvider2, mLocationSettingsUtilProvider2, mPermissionPresenterProvider2, mWizardPresenterProvider2);
    }

    public void injectMembers(ConnectionWizardActivity instance) {
        if (instance == null) {
            throw new NullPointerException("Cannot inject members into a null reference");
        }
        instance.mSettings = this.mSettingsProvider.get();
        BaseActivity_MembersInjector.injectMLocationSettingsUtil(instance, this.mLocationSettingsUtilProvider);
        instance.mPermissionPresenter = this.mPermissionPresenterProvider.get();
        instance.mWizardPresenter = this.mWizardPresenterProvider.get();
    }

    public static void injectMPermissionPresenter(ConnectionWizardActivity instance, Provider<PermissionPresenter> mPermissionPresenterProvider2) {
        instance.mPermissionPresenter = mPermissionPresenterProvider2.get();
    }

    public static void injectMWizardPresenter(ConnectionWizardActivity instance, Provider<WizardPresenter> mWizardPresenterProvider2) {
        instance.mWizardPresenter = mWizardPresenterProvider2.get();
    }
}
