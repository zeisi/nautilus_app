package com.nautilus.omni.app;

import android.content.Context;
import android.content.SharedPreferences;
import com.nautilus.omni.appmodules.settings.googlefit.googlefitcommunication.GoogleFitHelper;
import com.nautilus.omni.dataaccess.UserDaoHelper;
import com.nautilus.omni.dependencyinjection.components.AppComponent;
import com.nautilus.omni.syncservices.ObservableObject;
import javax.inject.Inject;

public class BasePresenter {
    protected Context mContext;
    protected final GoogleFitHelper mGoogleFitHelper;
    protected final SharedPreferences mPreferences;
    protected int mUnit = this.mPreferences.getInt(Preferences.UNITS_SETTINGS, 0);
    protected final UserDaoHelper mUserDaoHelper;
    protected final ObservableObject observableObject;

    @Inject
    public BasePresenter(Context context) {
        this.mContext = context;
        AppComponent appComponent = ((OmniApplication) context).getAppComponent();
        this.mUserDaoHelper = appComponent.getUserDaoHelper();
        this.mPreferences = appComponent.getSharedPreferences();
        this.observableObject = appComponent.getObservableObject();
        this.mGoogleFitHelper = appComponent.getGoogleFitHelper();
    }

    /* access modifiers changed from: protected */
    public boolean unitHasChanged(int unit) {
        return unit != this.mUnit;
    }

    /* access modifiers changed from: protected */
    public void updateUnits() {
        this.mUnit = this.mPreferences.getInt(Preferences.UNITS_SETTINGS, 0);
    }
}
