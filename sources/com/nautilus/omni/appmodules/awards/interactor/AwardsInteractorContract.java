package com.nautilus.omni.appmodules.awards.interactor;

import com.nautilus.omni.model.dto.Award;
import java.util.List;

public interface AwardsInteractorContract {

    public interface OnResult {
        void onGetAwardsByTypeSuccess(List<Award> list);

        void onGetAwardsSuccess(List<Award> list);
    }

    void getAllAwards(OnResult onResult);

    void getAllAwardsByType(String str, OnResult onResult);
}
