package com.nautilus.omni.appmodules.awardtypes.view;

import android.content.SharedPreferences;
import com.nautilus.omni.app.BaseActivity_MembersInjector;
import com.nautilus.omni.appmodules.awards.AwardValueBuilder;
import com.nautilus.omni.appmodules.awardtypes.presenter.AwardTypesPresenterContract;
import com.nautilus.omni.permissions.PermissionPresenter;
import com.nautilus.omni.util.LocationSettingsUtil;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class AwardTypesActivity_MembersInjector implements MembersInjector<AwardTypesActivity> {
    static final /* synthetic */ boolean $assertionsDisabled = (!AwardTypesActivity_MembersInjector.class.desiredAssertionStatus());
    private final Provider<AwardTypesPresenterContract> awardTypesPresenterProvider;
    private final Provider<AwardValueBuilder> mAwardValueBuilderProvider;
    private final Provider<LocationSettingsUtil> mLocationSettingsUtilProvider;
    private final Provider<PermissionPresenter> mPermissionPresenterProvider;
    private final Provider<SharedPreferences> mSettingsProvider;

    public AwardTypesActivity_MembersInjector(Provider<SharedPreferences> mSettingsProvider2, Provider<LocationSettingsUtil> mLocationSettingsUtilProvider2, Provider<AwardTypesPresenterContract> awardTypesPresenterProvider2, Provider<PermissionPresenter> mPermissionPresenterProvider2, Provider<AwardValueBuilder> mAwardValueBuilderProvider2) {
        if ($assertionsDisabled || mSettingsProvider2 != null) {
            this.mSettingsProvider = mSettingsProvider2;
            if ($assertionsDisabled || mLocationSettingsUtilProvider2 != null) {
                this.mLocationSettingsUtilProvider = mLocationSettingsUtilProvider2;
                if ($assertionsDisabled || awardTypesPresenterProvider2 != null) {
                    this.awardTypesPresenterProvider = awardTypesPresenterProvider2;
                    if ($assertionsDisabled || mPermissionPresenterProvider2 != null) {
                        this.mPermissionPresenterProvider = mPermissionPresenterProvider2;
                        if ($assertionsDisabled || mAwardValueBuilderProvider2 != null) {
                            this.mAwardValueBuilderProvider = mAwardValueBuilderProvider2;
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
        throw new AssertionError();
    }

    public static MembersInjector<AwardTypesActivity> create(Provider<SharedPreferences> mSettingsProvider2, Provider<LocationSettingsUtil> mLocationSettingsUtilProvider2, Provider<AwardTypesPresenterContract> awardTypesPresenterProvider2, Provider<PermissionPresenter> mPermissionPresenterProvider2, Provider<AwardValueBuilder> mAwardValueBuilderProvider2) {
        return new AwardTypesActivity_MembersInjector(mSettingsProvider2, mLocationSettingsUtilProvider2, awardTypesPresenterProvider2, mPermissionPresenterProvider2, mAwardValueBuilderProvider2);
    }

    public void injectMembers(AwardTypesActivity instance) {
        if (instance == null) {
            throw new NullPointerException("Cannot inject members into a null reference");
        }
        instance.mSettings = this.mSettingsProvider.get();
        BaseActivity_MembersInjector.injectMLocationSettingsUtil(instance, this.mLocationSettingsUtilProvider);
        instance.awardTypesPresenter = this.awardTypesPresenterProvider.get();
        instance.mPermissionPresenter = this.mPermissionPresenterProvider.get();
        instance.mAwardValueBuilder = this.mAwardValueBuilderProvider.get();
    }

    public static void injectAwardTypesPresenter(AwardTypesActivity instance, Provider<AwardTypesPresenterContract> awardTypesPresenterProvider2) {
        instance.awardTypesPresenter = awardTypesPresenterProvider2.get();
    }

    public static void injectMPermissionPresenter(AwardTypesActivity instance, Provider<PermissionPresenter> mPermissionPresenterProvider2) {
        instance.mPermissionPresenter = mPermissionPresenterProvider2.get();
    }

    public static void injectMAwardValueBuilder(AwardTypesActivity instance, Provider<AwardValueBuilder> mAwardValueBuilderProvider2) {
        instance.mAwardValueBuilder = mAwardValueBuilderProvider2.get();
    }
}
