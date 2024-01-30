package com.nautilus.omni.appmodules.home.presenter;

import android.content.Context;
import com.nautilus.omni.R;
import com.nautilus.omni.app.BasePresenter;
import com.nautilus.omni.app.Preferences;
import com.nautilus.omni.appmodules.awards.AwardValueBuilder;
import com.nautilus.omni.appmodules.awards.adapters.detail.AwardHeaderBuilder;
import com.nautilus.omni.appmodules.home.domain.interactors.HomeInteractorContract;
import com.nautilus.omni.appmodules.home.view.HomeFragmentContract;
import com.nautilus.omni.model.dto.Achievement;
import com.nautilus.omni.model.dto.Award;
import com.nautilus.omni.model.dto.Workout;
import com.nautilus.omni.util.Constants;
import javax.inject.Inject;
import org.joda.time.ReadableInstant;
import org.joda.time.format.DateTimeFormat;

public class HomePresenter extends BasePresenter implements HomePresenterContract, HomeInteractorContract.OnLastAwardExistValidatedListener, HomeInteractorContract.OnUpdatedAchievementsSectionListener, HomeInteractorContract.OnWorkoutsForPagerAdaptersListener {
    private AwardHeaderBuilder mAwardHeaderBuilder;
    private AwardValueBuilder mAwardValueBuilder;
    private Context mContext;
    HomeFragmentContract mHomeFragment;
    HomeInteractorContract mHomeInteractor;

    @Inject
    public HomePresenter(Context context, HomeInteractorContract homeInteractorContract, AwardValueBuilder awardValueBuilder) {
        super(context);
        this.mContext = context;
        this.mHomeInteractor = homeInteractorContract;
        this.mAwardValueBuilder = awardValueBuilder;
    }

    public void setHomeFragment(HomeFragmentContract mHomeFragment2) {
        this.mHomeFragment = mHomeFragment2;
    }

    public void onResume() {
        initAwardHeaderBuilder();
        checkIfUnitHasChanged();
    }

    private void checkIfUnitHasChanged() {
        if (unitHasChanged(this.mPreferences.getInt(Preferences.UNITS_SETTINGS, 0))) {
            this.mHomeFragment.refreshData();
            updateUnits();
            updateUnitInAwardValueBuilders();
        }
    }

    private void updateUnitInAwardValueBuilders() {
        if (this.mAwardHeaderBuilder != null && this.mAwardValueBuilder != null) {
            this.mAwardValueBuilder.setUnit(this.mUnit);
            this.mAwardHeaderBuilder.setUnit(this.mUnit);
        }
    }

    public void checkIsAvailableAchievements() {
        this.mHomeInteractor.validateLastAward(0, this);
    }

    public void loadPagerAdapters() {
        this.mHomeInteractor.loadWorkoutsForPagerAdapters(this);
    }

    public boolean isGoalsEnable() {
        return this.mPreferences.getBoolean(Preferences.GOALS_ENABLED, false);
    }

    public int getThisWeekLabel() {
        return this.mPreferences.getInt(Constants.THIS_WEEK_LABEL, 1);
    }

    public void updateThisWeekLabel() {
        if (this.mPreferences.getBoolean(Preferences.GOALS_ENABLED, false) && this.mPreferences.getInt(Constants.THIS_WEEK_LABEL, 1) < 4) {
            this.mPreferences.edit().putInt(Constants.THIS_WEEK_LABEL, this.mPreferences.getInt(Constants.THIS_WEEK_LABEL, 1) + 1).apply();
        }
    }

    public void loadAchievementsSection() {
        this.mHomeInteractor.loadLastAchievementAndAwardForAchievementSection(this);
    }

    public void onAwardExist() {
        this.mHomeFragment.showDetailView();
    }

    public void onAwardNoExist() {
    }

    public void onAchievementsAndAwardsNoAvailable() {
        this.mHomeFragment.achievementsSectionLastAwardAndAchievementNoAvailable();
    }

    public void onAchievementIsAvailable(Achievement achievement) {
        this.mHomeFragment.achievementsSectionLastAchievementIsAvailable(achievement);
    }

    public void onAwardIsAvailable(Award award) {
        updateUnits();
        this.mHomeFragment.achievementsSectionLastAwardIsAvailable(award.getmAwardType().getmMediumImage(), buildAwardValue(award), DateTimeFormat.forPattern(this.mContext.getResources().getString(R.string.home_date_achievements)).print((ReadableInstant) award.getmDate()));
    }

    private String buildAwardValue(Award award) {
        initAwardHeaderBuilder();
        StringBuilder stringBuilder = new StringBuilder();
        updateUnitInAwardValueBuilders();
        return stringBuilder.append(this.mAwardHeaderBuilder.getAwardHeaderValue(award)).toString().toUpperCase();
    }

    private void initAwardHeaderBuilder() {
        if (this.mAwardHeaderBuilder == null) {
            this.mAwardHeaderBuilder = new AwardHeaderBuilder(this.mContext, this.mAwardValueBuilder, this.mUnit);
        }
    }

    public void onWorkoutsLoadSuccess(Workout lastWorkout) {
        this.mHomeFragment.setPagerAdapter(lastWorkout);
    }
}
