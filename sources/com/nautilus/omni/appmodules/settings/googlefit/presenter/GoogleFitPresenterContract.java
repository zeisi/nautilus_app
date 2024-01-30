package com.nautilus.omni.appmodules.settings.googlefit.presenter;

import android.app.Activity;
import android.content.Context;
import com.nautilus.omni.appmodules.settings.googlefit.view.ConfigGoogleFitContract;
import com.nautilus.omni.appmodules.settings.googlefit.view.ConnectionGoogleFitViewContract;
import com.nautilus.omni.model.dto.FitServicesWorkout;
import com.nautilus.omni.model.dto.OmniData;
import com.nautilus.omni.syncservices.syncserviceshelpers.FitServicesSyncDataHelper;
import java.util.List;

public interface GoogleFitPresenterContract {
    void cleanGoogleFitPrefs();

    void connectToFitnessService(Activity activity);

    void disconnectFitnessService(Context context);

    void loadSwitches();

    void retryConnection(Activity activity);

    void saveCurrentSwitchesStateInPreferences(boolean z, boolean z2, boolean z3, boolean z4, boolean z5);

    void setConfigurationViewView(ConfigGoogleFitContract configGoogleFitContract);

    void setConnectionView(ConnectionGoogleFitViewContract connectionGoogleFitViewContract);

    void startSync();

    void syncFitnessData(Activity activity, FitServicesSyncDataHelper fitServicesSyncDataHelper, OmniData omniData, List<FitServicesWorkout> list);

    void updateAuthProgressValue(boolean z);
}
