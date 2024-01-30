package com.nautilus.omni.appmodules.home.domain.interactors;

import com.nautilus.omni.appmodules.home.domain.interactors.HomeInteractorContract;
import com.nautilus.omni.dataaccess.AchievementsDaoHelper;
import com.nautilus.omni.dataaccess.AwardsDaoHelper;
import com.nautilus.omni.dataaccess.WorkoutDaoHelper;
import com.nautilus.omni.model.dto.Achievement;
import com.nautilus.omni.model.dto.Award;

public class HomeInteractor implements HomeInteractorContract {
    final AchievementsDaoHelper mAchievementsDaoHelper;
    final AwardsDaoHelper mAwardsDaoHelper;
    final WorkoutDaoHelper mWorkoutDaoHelper;

    public HomeInteractor(AwardsDaoHelper awardsDaoHelper, WorkoutDaoHelper workoutDaoHelper, AchievementsDaoHelper achievementsDaoHelper) {
        this.mAwardsDaoHelper = awardsDaoHelper;
        this.mWorkoutDaoHelper = workoutDaoHelper;
        this.mAchievementsDaoHelper = achievementsDaoHelper;
    }

    public void validateLastAward(int unit, HomeInteractorContract.OnLastAwardExistValidatedListener onLastAwardExistValidatedListener) {
        if (this.mAwardsDaoHelper.getLastAward(unit) != null) {
            onLastAwardExistValidatedListener.onAwardExist();
        }
    }

    public void loadLastAchievementAndAwardForAchievementSection(HomeInteractorContract.OnUpdatedAchievementsSectionListener onUpdatedAchievementsSectionListener) {
        Award award = this.mAwardsDaoHelper.getLastAward(0);
        Achievement achievement = this.mAchievementsDaoHelper.getLastAchievement(0);
        if (award == null && achievement == null) {
            onUpdatedAchievementsSectionListener.onAchievementsAndAwardsNoAvailable();
        } else if (achievement != null) {
            onUpdatedAchievementsSectionListener.onAchievementIsAvailable(achievement);
        } else {
            onUpdatedAchievementsSectionListener.onAwardIsAvailable(award);
        }
    }

    public void loadWorkoutsForPagerAdapters(HomeInteractorContract.OnWorkoutsForPagerAdaptersListener onWorkoutsForPagerAdaptersListener) {
        onWorkoutsForPagerAdaptersListener.onWorkoutsLoadSuccess(this.mWorkoutDaoHelper.getLastWorkout());
    }
}
