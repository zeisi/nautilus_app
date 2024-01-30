package com.nautilus.omni.appmodules.settings.googlefit.interactor;

import com.nautilus.omni.model.dto.FitServicesWorkout;
import com.nautilus.omni.model.dto.OmniData;
import java.util.List;

public interface GoogleFitInteractorContract {

    public interface OnResult {
        void setWorkoutList(List<FitServicesWorkout> list, OmniData omniData);
    }

    void getWorkouts(OnResult onResult);
}
