package com.nautilus.omni.appmodules.awardtypes.interactor;

import com.nautilus.omni.appmodules.awardtypes.interactor.AwardsTypesInteractorContract;
import com.nautilus.omni.dataaccess.AwardsDaoHelper;

public class AwardsTypesInteractor implements AwardsTypesInteractorContract {
    private AwardsDaoHelper mAwardDaoHelper;

    public AwardsTypesInteractor(AwardsDaoHelper awardDaoHelper) {
        this.mAwardDaoHelper = awardDaoHelper;
    }

    public void getAllAvailableAwards(AwardsTypesInteractorContract.OnResult onResult, int unit) {
        onResult.onGetAvailableAwardsSuccess(this.mAwardDaoHelper.getAllAvailableAwards(unit));
    }
}
