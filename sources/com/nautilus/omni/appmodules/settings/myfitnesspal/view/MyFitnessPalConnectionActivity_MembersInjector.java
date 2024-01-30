package com.nautilus.omni.appmodules.settings.myfitnesspal.view;

import android.content.SharedPreferences;
import com.nautilus.omni.app.BaseActivity_MembersInjector;
import com.nautilus.omni.appmodules.settings.myfitnesspal.presenter.MyFitnessPalPresenterContract;
import com.nautilus.omni.util.LocationSettingsUtil;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class MyFitnessPalConnectionActivity_MembersInjector implements MembersInjector<MyFitnessPalConnectionActivity> {
    static final /* synthetic */ boolean $assertionsDisabled = (!MyFitnessPalConnectionActivity_MembersInjector.class.desiredAssertionStatus());
    private final Provider<LocationSettingsUtil> mLocationSettingsUtilProvider;
    private final Provider<SharedPreferences> mSettingsProvider;
    private final Provider<MyFitnessPalPresenterContract> myFitnessPalPresenterProvider;

    public MyFitnessPalConnectionActivity_MembersInjector(Provider<SharedPreferences> mSettingsProvider2, Provider<LocationSettingsUtil> mLocationSettingsUtilProvider2, Provider<MyFitnessPalPresenterContract> myFitnessPalPresenterProvider2) {
        if ($assertionsDisabled || mSettingsProvider2 != null) {
            this.mSettingsProvider = mSettingsProvider2;
            if ($assertionsDisabled || mLocationSettingsUtilProvider2 != null) {
                this.mLocationSettingsUtilProvider = mLocationSettingsUtilProvider2;
                if ($assertionsDisabled || myFitnessPalPresenterProvider2 != null) {
                    this.myFitnessPalPresenterProvider = myFitnessPalPresenterProvider2;
                    return;
                }
                throw new AssertionError();
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public static MembersInjector<MyFitnessPalConnectionActivity> create(Provider<SharedPreferences> mSettingsProvider2, Provider<LocationSettingsUtil> mLocationSettingsUtilProvider2, Provider<MyFitnessPalPresenterContract> myFitnessPalPresenterProvider2) {
        return new MyFitnessPalConnectionActivity_MembersInjector(mSettingsProvider2, mLocationSettingsUtilProvider2, myFitnessPalPresenterProvider2);
    }

    public void injectMembers(MyFitnessPalConnectionActivity instance) {
        if (instance == null) {
            throw new NullPointerException("Cannot inject members into a null reference");
        }
        instance.mSettings = this.mSettingsProvider.get();
        BaseActivity_MembersInjector.injectMLocationSettingsUtil(instance, this.mLocationSettingsUtilProvider);
        instance.myFitnessPalPresenter = this.myFitnessPalPresenterProvider.get();
    }

    public static void injectMyFitnessPalPresenter(MyFitnessPalConnectionActivity instance, Provider<MyFitnessPalPresenterContract> myFitnessPalPresenterProvider2) {
        instance.myFitnessPalPresenter = myFitnessPalPresenterProvider2.get();
    }
}
