package com.nautilus.omni.appmodules.awards.presenter;

import com.nautilus.omni.appmodules.awards.view.AwardsFragmentContract;
import com.nautilus.omni.model.dto.Award;

public interface AwardsPresenterContract {
    void loadAwardDetailInfo(Award award);

    void loadAwards();

    void refreshData();

    void setmAwardsFragment(AwardsFragmentContract awardsFragmentContract);
}
