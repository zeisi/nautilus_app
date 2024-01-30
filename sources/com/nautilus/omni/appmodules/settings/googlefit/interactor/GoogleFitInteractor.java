package com.nautilus.omni.appmodules.settings.googlefit.interactor;

import com.nautilus.omni.appmodules.settings.googlefit.interactor.GoogleFitInteractorContract;
import com.nautilus.omni.dataaccess.GoogleFitDaoHelper;
import com.nautilus.omni.dataaccess.OmniTrainerDaoHelper;

public class GoogleFitInteractor implements GoogleFitInteractorContract {
    public static final String TAG = GoogleFitInteractor.class.getSimpleName();
    private final GoogleFitDaoHelper googleFitDaoHelper;
    private final OmniTrainerDaoHelper mOmniTrainerDaoHelper;

    public GoogleFitInteractor(GoogleFitDaoHelper googleFitDaoHelper2, OmniTrainerDaoHelper omniTrainerDaoHelper) {
        this.googleFitDaoHelper = googleFitDaoHelper2;
        this.mOmniTrainerDaoHelper = omniTrainerDaoHelper;
    }

    public void getWorkouts(GoogleFitInteractorContract.OnResult onResult) {
        onResult.setWorkoutList(this.googleFitDaoHelper.getFitServicesWorkouts(), this.mOmniTrainerDaoHelper.getFirstOmniData());
    }
}
