package com.nautilus.omni.appmodules.home.domain.interactors;

import com.nautilus.omni.model.dto.Achievement;
import com.nautilus.omni.model.dto.Award;
import com.nautilus.omni.model.dto.Workout;

public interface HomeInteractorContract {

    public interface OnLastAwardExistValidatedListener {
        void onAwardExist();

        void onAwardNoExist();
    }

    public interface OnUpdatedAchievementsSectionListener {
        void onAchievementIsAvailable(Achievement achievement);

        void onAchievementsAndAwardsNoAvailable();

        void onAwardIsAvailable(Award award);
    }

    public interface OnWorkoutsForPagerAdaptersListener {
        void onWorkoutsLoadSuccess(Workout workout);
    }

    void loadLastAchievementAndAwardForAchievementSection(OnUpdatedAchievementsSectionListener onUpdatedAchievementsSectionListener);

    void loadWorkoutsForPagerAdapters(OnWorkoutsForPagerAdaptersListener onWorkoutsForPagerAdaptersListener);

    void validateLastAward(int i, OnLastAwardExistValidatedListener onLastAwardExistValidatedListener);
}
