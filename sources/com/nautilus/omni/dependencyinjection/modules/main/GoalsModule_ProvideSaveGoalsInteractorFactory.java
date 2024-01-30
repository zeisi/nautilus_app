package com.nautilus.omni.dependencyinjection.modules.main;

import com.nautilus.omni.appmodules.goals.presenter.interactors.SaveGoalsInteractor;
import com.nautilus.omni.dataaccess.AchievementsDaoHelper;
import com.nautilus.omni.dataaccess.GoalsDaoHelper;
import com.nautilus.omni.dataaccess.WorkoutDaoHelper;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class GoalsModule_ProvideSaveGoalsInteractorFactory implements Factory<SaveGoalsInteractor> {
    static final /* synthetic */ boolean $assertionsDisabled = (!GoalsModule_ProvideSaveGoalsInteractorFactory.class.desiredAssertionStatus());
    private final Provider<AchievementsDaoHelper> achievementsDaoHelperProvider;
    private final Provider<GoalsDaoHelper> goalsDaoHelperProvider;
    private final GoalsModule module;
    private final Provider<WorkoutDaoHelper> workoutDaoHelperProvider;

    public GoalsModule_ProvideSaveGoalsInteractorFactory(GoalsModule module2, Provider<AchievementsDaoHelper> achievementsDaoHelperProvider2, Provider<GoalsDaoHelper> goalsDaoHelperProvider2, Provider<WorkoutDaoHelper> workoutDaoHelperProvider2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            if ($assertionsDisabled || achievementsDaoHelperProvider2 != null) {
                this.achievementsDaoHelperProvider = achievementsDaoHelperProvider2;
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
        throw new AssertionError();
    }

    public SaveGoalsInteractor get() {
        return (SaveGoalsInteractor) Preconditions.checkNotNull(this.module.provideSaveGoalsInteractor(this.achievementsDaoHelperProvider.get(), this.goalsDaoHelperProvider.get(), this.workoutDaoHelperProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<SaveGoalsInteractor> create(GoalsModule module2, Provider<AchievementsDaoHelper> achievementsDaoHelperProvider2, Provider<GoalsDaoHelper> goalsDaoHelperProvider2, Provider<WorkoutDaoHelper> workoutDaoHelperProvider2) {
        return new GoalsModule_ProvideSaveGoalsInteractorFactory(module2, achievementsDaoHelperProvider2, goalsDaoHelperProvider2, workoutDaoHelperProvider2);
    }

    public static SaveGoalsInteractor proxyProvideSaveGoalsInteractor(GoalsModule instance, AchievementsDaoHelper achievementsDaoHelper, GoalsDaoHelper goalsDaoHelper, WorkoutDaoHelper workoutDaoHelper) {
        return instance.provideSaveGoalsInteractor(achievementsDaoHelper, goalsDaoHelper, workoutDaoHelper);
    }
}
