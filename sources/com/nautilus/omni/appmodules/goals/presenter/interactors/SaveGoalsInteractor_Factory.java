package com.nautilus.omni.appmodules.goals.presenter.interactors;

import com.nautilus.omni.dataaccess.AchievementsDaoHelper;
import com.nautilus.omni.dataaccess.GoalsDaoHelper;
import com.nautilus.omni.dataaccess.WorkoutDaoHelper;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class SaveGoalsInteractor_Factory implements Factory<SaveGoalsInteractor> {
    static final /* synthetic */ boolean $assertionsDisabled = (!SaveGoalsInteractor_Factory.class.desiredAssertionStatus());
    private final Provider<AchievementsDaoHelper> achievementDaoHelperProvider;
    private final Provider<GoalsDaoHelper> goalsDaoHelperProvider;
    private final Provider<WorkoutDaoHelper> workoutDaoHelperProvider;

    public SaveGoalsInteractor_Factory(Provider<AchievementsDaoHelper> achievementDaoHelperProvider2, Provider<GoalsDaoHelper> goalsDaoHelperProvider2, Provider<WorkoutDaoHelper> workoutDaoHelperProvider2) {
        if ($assertionsDisabled || achievementDaoHelperProvider2 != null) {
            this.achievementDaoHelperProvider = achievementDaoHelperProvider2;
            if ($assertionsDisabled || goalsDaoHelperProvider2 != null) {
                this.goalsDaoHelperProvider = goalsDaoHelperProvider2;
                if ($assertionsDisabled || workoutDaoHelperProvider2 != null) {
                    this.workoutDaoHelperProvider = workoutDaoHelperProvider2;
                    return;
                }
                throw new AssertionError();
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public SaveGoalsInteractor get() {
        return new SaveGoalsInteractor(this.achievementDaoHelperProvider.get(), this.goalsDaoHelperProvider.get(), this.workoutDaoHelperProvider.get());
    }

    public static Factory<SaveGoalsInteractor> create(Provider<AchievementsDaoHelper> achievementDaoHelperProvider2, Provider<GoalsDaoHelper> goalsDaoHelperProvider2, Provider<WorkoutDaoHelper> workoutDaoHelperProvider2) {
        return new SaveGoalsInteractor_Factory(achievementDaoHelperProvider2, goalsDaoHelperProvider2, workoutDaoHelperProvider2);
    }
}
