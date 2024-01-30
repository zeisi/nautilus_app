package com.nautilus.omni.dependencyinjection.modules.main;

import com.nautilus.omni.appmodules.home.domain.interactors.ThisWeekSectionInteractorContract;
import com.nautilus.omni.dataaccess.GoalsDaoHelper;
import com.nautilus.omni.dataaccess.UserDaoHelper;
import com.nautilus.omni.dataaccess.WeekDaoHelper;
import com.nautilus.omni.dataaccess.WorkoutDaoHelper;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class HomeModule_ProvideIThisWeekSectionInteractorFactory implements Factory<ThisWeekSectionInteractorContract> {
    static final /* synthetic */ boolean $assertionsDisabled = (!HomeModule_ProvideIThisWeekSectionInteractorFactory.class.desiredAssertionStatus());
    private final Provider<GoalsDaoHelper> goalsDaoHelperProvider;
    private final HomeModule module;
    private final Provider<UserDaoHelper> userDaoHelperProvider;
    private final Provider<WeekDaoHelper> weekDaoHelperProvider;
    private final Provider<WorkoutDaoHelper> workoutDaoHelperProvider;

    public HomeModule_ProvideIThisWeekSectionInteractorFactory(HomeModule module2, Provider<GoalsDaoHelper> goalsDaoHelperProvider2, Provider<WeekDaoHelper> weekDaoHelperProvider2, Provider<UserDaoHelper> userDaoHelperProvider2, Provider<WorkoutDaoHelper> workoutDaoHelperProvider2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            if ($assertionsDisabled || goalsDaoHelperProvider2 != null) {
                this.goalsDaoHelperProvider = goalsDaoHelperProvider2;
                if ($assertionsDisabled || weekDaoHelperProvider2 != null) {
                    this.weekDaoHelperProvider = weekDaoHelperProvider2;
                    if ($assertionsDisabled || userDaoHelperProvider2 != null) {
                        this.userDaoHelperProvider = userDaoHelperProvider2;
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
        throw new AssertionError();
    }

    public ThisWeekSectionInteractorContract get() {
        return (ThisWeekSectionInteractorContract) Preconditions.checkNotNull(this.module.provideIThisWeekSectionInteractor(this.goalsDaoHelperProvider.get(), this.weekDaoHelperProvider.get(), this.userDaoHelperProvider.get(), this.workoutDaoHelperProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<ThisWeekSectionInteractorContract> create(HomeModule module2, Provider<GoalsDaoHelper> goalsDaoHelperProvider2, Provider<WeekDaoHelper> weekDaoHelperProvider2, Provider<UserDaoHelper> userDaoHelperProvider2, Provider<WorkoutDaoHelper> workoutDaoHelperProvider2) {
        return new HomeModule_ProvideIThisWeekSectionInteractorFactory(module2, goalsDaoHelperProvider2, weekDaoHelperProvider2, userDaoHelperProvider2, workoutDaoHelperProvider2);
    }

    public static ThisWeekSectionInteractorContract proxyProvideIThisWeekSectionInteractor(HomeModule instance, GoalsDaoHelper goalsDaoHelper, WeekDaoHelper weekDaoHelper, UserDaoHelper userDaoHelper, WorkoutDaoHelper workoutDaoHelper) {
        return instance.provideIThisWeekSectionInteractor(goalsDaoHelper, weekDaoHelper, userDaoHelper, workoutDaoHelper);
    }
}
