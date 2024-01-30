package com.nautilus.omni.appmodules.awards.presenter;

import android.content.Context;
import com.nautilus.omni.app.BasePresenter;
import com.nautilus.omni.appmodules.awards.AwardTypeEnum;
import com.nautilus.omni.appmodules.awards.interactor.AwardsInteractorContract;
import com.nautilus.omni.appmodules.awards.view.AwardsFragmentContract;
import com.nautilus.omni.model.dto.Award;
import com.nautilus.omni.util.Util;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class AwardsPresenter extends BasePresenter implements AwardsPresenterContract, AwardsInteractorContract.OnResult {
    AwardsFragmentContract mAwardsFragment;
    AwardsInteractorContract mAwardsInteractor;

    @Inject
    public AwardsPresenter(Context context, AwardsInteractorContract iAwardsInteractor) {
        super(context);
        this.mAwardsInteractor = iAwardsInteractor;
    }

    public void setmAwardsFragment(AwardsFragmentContract mAwardsFragment2) {
        this.mAwardsFragment = mAwardsFragment2;
    }

    public void refreshData() {
        updateUnits();
        loadAwards();
    }

    public void loadAwards() {
        this.mAwardsInteractor.getAllAwards(this);
    }

    public void loadAwardDetailInfo(Award award) {
        if (award.getmAwardType().getmName().equals(AwardTypeEnum.BEST_WORKOUT.toString())) {
            ArrayList<Award> mAwardDetailList = new ArrayList<>();
            mAwardDetailList.add(award);
            onGetAwardsByTypeSuccess(mAwardDetailList);
            return;
        }
        this.mAwardsInteractor.getAllAwardsByType(award.getmAwardType().getmName(), this);
    }

    public void onGetAwardsSuccess(List<Award> awards) {
        awards.addAll(Util.createEmptyAwards(this.mContext));
        updateUnits();
        this.mAwardsFragment.setAwards(awards, this.mUnit);
    }

    public void onGetAwardsByTypeSuccess(List<Award> awards) {
        this.mAwardsFragment.loadAwardDetailInfo(awards, this.mUnit);
    }
}
