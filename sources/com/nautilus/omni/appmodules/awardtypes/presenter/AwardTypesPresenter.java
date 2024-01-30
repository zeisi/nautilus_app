package com.nautilus.omni.appmodules.awardtypes.presenter;

import android.content.Context;
import com.nautilus.omni.app.BasePresenter;
import com.nautilus.omni.appmodules.awardtypes.interactor.AwardsTypesInteractorContract;
import com.nautilus.omni.appmodules.awardtypes.view.AwardTypesActivityContract;
import java.util.List;
import javax.inject.Inject;

public class AwardTypesPresenter extends BasePresenter implements AwardTypesPresenterContract, AwardsTypesInteractorContract.OnResult {
    final AwardTypesActivityContract iAwardTypesActivity;
    final AwardsTypesInteractorContract iAwardsInteractor;

    @Inject
    public AwardTypesPresenter(Context context, AwardsTypesInteractorContract iAwardsInteractor2, AwardTypesActivityContract iAwardTypesActivity2) {
        super(context);
        this.iAwardsInteractor = iAwardsInteractor2;
        this.iAwardTypesActivity = iAwardTypesActivity2;
    }

    public void loadAvailableAwards() {
        this.iAwardsInteractor.getAllAvailableAwards(this, this.mUnit);
    }

    public void onGetAvailableAwardsSuccess(List<Object> awards) {
        this.iAwardTypesActivity.setAvailableAwardsAndUnit(awards, this.mUnit);
    }
}
