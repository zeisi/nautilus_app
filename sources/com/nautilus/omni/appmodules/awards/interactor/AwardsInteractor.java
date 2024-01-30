package com.nautilus.omni.appmodules.awards.interactor;

import com.nautilus.omni.appmodules.awards.interactor.AwardsInteractorContract;
import com.nautilus.omni.dataaccess.AwardsDaoHelper;

public class AwardsInteractor implements AwardsInteractorContract {
    private AwardsDaoHelper mAwardDaoHelper;

    public AwardsInteractor(AwardsDaoHelper awardDaoHelper) {
        this.mAwardDaoHelper = awardDaoHelper;
    }

    public void getAllAwards(AwardsInteractorContract.OnResult onResult) {
        onResult.onGetAwardsSuccess(this.mAwardDaoHelper.getAllAwards());
    }

    public void getAllAwardsByType(String typeName, AwardsInteractorContract.OnResult onResult) {
        onResult.onGetAwardsByTypeSuccess(this.mAwardDaoHelper.getAllAwards(typeName));
    }
}
