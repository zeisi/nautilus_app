package com.nautilus.omni.appmodules.home.view;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.nautilus.omni.R;
import com.nautilus.omni.app.BaseFragment;
import com.nautilus.omni.appmodules.awards.AwardValueBuilder;
import com.nautilus.omni.appmodules.home.adapters.AchievementsAdapter;
import com.nautilus.omni.appmodules.home.adapters.RecordsAdapter;
import com.nautilus.omni.appmodules.home.presenter.AchievementsPresenterContract;
import com.nautilus.omni.dependencyinjection.components.MainOmniComponent;
import com.nautilus.omni.model.dto.Achievement;
import com.nautilus.omni.model.dto.Award;
import java.util.List;
import javax.inject.Inject;

public class AchievementsFragment extends BaseFragment implements AchievementFragmentContract {
    static final String KIND = "KIND_ACHIEVEMENTS";
    @Inject
    AchievementsPresenterContract iAchievementsPresenter;
    @BindView(2131755317)
    ListView listView;
    private AchievementsAdapter mAchievementsAdapter;
    @Inject
    AwardValueBuilder mAwardValueBuilder;
    int mKind = 0;
    private RecordsAdapter mRecordsAdapter;
    int mUnit;

    public void setKind(int kind) {
        this.mKind = kind;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_achievements, container, false);
        ButterKnife.bind((Object) this, view);
        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null) {
            this.mKind = savedInstanceState.getInt(KIND);
        }
    }

    public void onResume() {
        super.onResume();
        if (this.iAchievementsPresenter != null) {
            this.mUnit = this.iAchievementsPresenter.refreshUnit();
            refreshCurrentUnitOnAchievementsAdapter();
            refreshCurrentUnitOnRecordsAdapter();
        }
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((MainOmniComponent) getComponent(MainOmniComponent.class)).inject(this);
        this.iAchievementsPresenter.setiAchievementFragment(this);
        this.mUnit = this.iAchievementsPresenter.refreshUnit();
        if (this.mKind == 0) {
            this.mAchievementsAdapter = new AchievementsAdapter(getActivity().getApplicationContext(), this.mAwardValueBuilder);
            this.listView.setAdapter(this.mAchievementsAdapter);
            this.listView.setDividerHeight(0);
            this.listView.setDivider((Drawable) null);
            this.iAchievementsPresenter.getAllAwardsAndAchievements();
            return;
        }
        this.mRecordsAdapter = new RecordsAdapter(getActivity().getApplicationContext(), this.mUnit, this.iAchievementsPresenter);
        this.listView.setAdapter(this.mRecordsAdapter);
        this.listView.setDividerHeight(0);
        this.listView.setDivider((Drawable) null);
    }

    public void updateAwards() {
        if (this.iAchievementsPresenter != null) {
            this.mUnit = this.iAchievementsPresenter.refreshUnit();
            if (this.mKind == 0) {
                this.mAchievementsAdapter = new AchievementsAdapter(getActivity().getApplicationContext(), this.mAwardValueBuilder);
                this.listView.setAdapter(this.mAchievementsAdapter);
                this.listView.setDividerHeight(0);
                this.listView.setDivider((Drawable) null);
                this.iAchievementsPresenter.getAllAwardsAndAchievements();
                return;
            }
            this.mRecordsAdapter = new RecordsAdapter(getActivity().getApplicationContext(), this.mUnit, this.iAchievementsPresenter);
            this.listView.setAdapter(this.mRecordsAdapter);
            this.listView.setDividerHeight(0);
            this.listView.setDivider((Drawable) null);
        }
    }

    private void refreshCurrentUnitOnRecordsAdapter() {
        if (this.mRecordsAdapter != null) {
            this.mRecordsAdapter.setmUnit(this.mUnit);
        }
    }

    private void refreshCurrentUnitOnAchievementsAdapter() {
        if (this.mAchievementsAdapter != null) {
            this.mAchievementsAdapter.setmUnit(this.mUnit);
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KIND, this.mKind);
    }

    public void setAwardsAndAchievements(List<Award> pAwards, List<Achievement> pAchievements, int pUnit) {
        this.mAchievementsAdapter.setAchievementsAndAwards(pAwards, pAchievements, pUnit);
    }

    public void setCaloriesBurnedPerWorkout(String value) {
    }
}
