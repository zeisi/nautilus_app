package com.nautilus.omni.appmodules.settings.googlefit.view;

import com.nautilus.omni.model.dto.FitServicesWorkout;
import com.nautilus.omni.model.dto.OmniData;
import java.util.List;

public interface ConfigGoogleFitContract {
    void close();

    void initSwitches(boolean z, boolean z2, boolean z3, boolean z4, boolean z5);

    void showDisconnectionError(String str, String str2);

    void startSync(List<FitServicesWorkout> list, OmniData omniData);
}
