package com.nautilus.omni.dependencyinjection.modules.main;

import com.nautilus.omni.appmodules.home.domain.interactors.HomeInteractorContract;
import com.nautilus.omni.dataaccess.AchievementsDaoHelper;
import com.nautilus.omni.dataaccess.AwardsDaoHelper;
import com.nautilus.omni.dataaccess.WorkoutDaoHelper;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class HomeModule_ProvideIHomeInteractorFactory implements Factory<HomeInteractorContract> {
    static final /* synthetic */ boolean $assertionsDisabled = (!HomeModule_ProvideIHomeInteractorFactory.class.desiredAssertionStatus());
    private final Provider<AchievementsDaoHelper> achievementsDaoHelperProvider;
    private final Provider<AwardsDaoHelper> awardsDaoHelperProvider;
    private final HomeModule module;
    private final Provider<WorkoutDaoHelper> workoutDaoHelperProvider;

    public HomeModule_ProvideIHomeInteractorFactory(HomeModule module2, Provider<AwardsDaoHelper> awardsDaoHelperProvider2, Provider<WorkoutDaoHelper> workoutDaoHelperProvider2, Provider<AchievementsDaoHelper> achievementsDaoHelperProvider2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            if ($assertionsDisabled || awardsDaoHelperProvider2 != null) {
                this.awardsDaoHelperProvider = awardsDaoHelperProvider2;
                if ($assertionsDisabled || workoutDaoHelperProvider2 != null) {
                    this.workoutDaoHelperProvider = workoutDaoHelperProvider2;
                    if ($assertionsDisabled || achievementsDaoHelperProvider2 != null) {
                        this.achievementsDaoHelperProvider = achievementsDaoHelperProvider2;
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

    public HomeInteractorContract get() {
        return (HomeInteractorContract) Preconditions.checkNotNull(this.module.provideIHomeInteractor(this.awardsDaoHelperProvider.get(), this.workoutDaoHelperProvider.get(), this.achievementsDaoHelperProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<HomeInteractorContract> create(HomeModule module2, Provider<AwardsDaoHelper> awardsDaoHelperProvider2, Provider<WorkoutDaoHelper> workoutDaoHelperProvider2, Provider<AchievementsDaoHelper> achievementsDaoHelperProvider2) {
        return new HomeModule_ProvideIHomeInteractorFactory(module2, awardsDaoHelperProvider2, workoutDaoHelperProvider2, achievementsDaoHelperProvider2);
    }

    public static HomeInteractorContract proxyProvideIHomeInteractor(HomeModule instance, AwardsDaoHelper awardsDaoHelper, WorkoutDaoHelper workoutDaoHelper, AchievementsDaoHelper achievementsDaoHelper) {
        return instance.provideIHomeInteractor(awardsDaoHelper, workoutDaoHelper, achievementsDaoHelper);
    }
}
