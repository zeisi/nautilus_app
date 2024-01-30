package com.nautilus.omni.dependencyinjection.modules.main;

import com.nautilus.omni.appmodules.home.domain.interactors.GaugeInteractorContract;
import com.nautilus.omni.dataaccess.GoalsDaoHelper;
import com.nautilus.omni.dataaccess.UserDaoHelper;
import com.nautilus.omni.dataaccess.WorkoutDaoHelper;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class HomeModule_ProvideIGaugeInteractorFactory implements Factory<GaugeInteractorContract> {
    static final /* synthetic */ boolean $assertionsDisabled = (!HomeModule_ProvideIGaugeInteractorFactory.class.desiredAssertionStatus());
    private final Provider<GoalsDaoHelper> goalsDaoHelpeProvider;
    private final HomeModule module;
    private final Provider<UserDaoHelper> userDaoHelperProvider;
    private final Provider<WorkoutDaoHelper> workoutDaoHelperProvider;

    public HomeModule_ProvideIGaugeInteractorFactory(HomeModule module2, Provider<WorkoutDaoHelper> workoutDaoHelperProvider2, Provider<UserDaoHelper> userDaoHelperProvider2, Provider<GoalsDaoHelper> goalsDaoHelpeProvider2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            if ($assertionsDisabled || workoutDaoHelperProvider2 != null) {
                this.workoutDaoHelperProvider = workoutDaoHelperProvider2;
                if ($assertionsDisabled || userDaoHelperProvider2 != null) {
                    this.userDaoHelperProvider = userDaoHelperProvider2;
                    if ($assertionsDisabled || goalsDaoHelpeProvider2 != null) {
                        this.goalsDaoHelpeProvider = goalsDaoHelpeProvider2;
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

    public GaugeInteractorContract get() {
        return (GaugeInteractorContract) Preconditions.checkNotNull(this.module.provideIGaugeInteractor(this.workoutDaoHelperProvider.get(), this.userDaoHelperProvider.get(), this.goalsDaoHelpeProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<GaugeInteractorContract> create(HomeModule module2, Provider<WorkoutDaoHelper> workoutDaoHelperProvider2, Provider<UserDaoHelper> userDaoHelperProvider2, Provider<GoalsDaoHelper> goalsDaoHelpeProvider2) {
        return new HomeModule_ProvideIGaugeInteractorFactory(module2, workoutDaoHelperProvider2, userDaoHelperProvider2, goalsDaoHelpeProvider2);
    }

    public static GaugeInteractorContract proxyProvideIGaugeInteractor(HomeModule instance, WorkoutDaoHelper workoutDaoHelper, UserDaoHelper userDaoHelper, GoalsDaoHelper goalsDaoHelpe) {
        return instance.provideIGaugeInteractor(workoutDaoHelper, userDaoHelper, goalsDaoHelpe);
    }
}
