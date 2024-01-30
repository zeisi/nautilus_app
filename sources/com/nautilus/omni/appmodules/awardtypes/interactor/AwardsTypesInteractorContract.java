package com.nautilus.omni.appmodules.awardtypes.interactor;

import java.util.List;

public interface AwardsTypesInteractorContract {

    public interface OnResult {
        void onGetAvailableAwardsSuccess(List<Object> list);
    }

    void getAllAvailableAwards(OnResult onResult, int i);
}
