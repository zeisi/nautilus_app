package com.nautilus.omni.dependencyinjection.modules.main;

import com.nautilus.omni.appmodules.home.domain.interactors.AchievementsContract;
import com.nautilus.omni.dataaccess.AchievementsDaoHelper;
import com.nautilus.omni.dataaccess.AwardsDaoHelper;
import com.nautilus.omni.dataaccess.WeekDaoHelper;
import com.nautilus.omni.dataaccess.WorkoutDaoHelper;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class HomeModule_ProvideAchievementsInteractorFactory implements Factory<AchievementsContract> {
    static final /* synthetic */ boolean $assertionsDisabled = (!HomeModule_ProvideAchievementsInteractorFactory.class.desiredAssertionStatus());
    private final Provider<AchievementsDaoHelper> mAchievementsDaoHelperProvider;
    private final Provider<AwardsDaoHelper> mAwardsDaoHelperProvider;
    private final Provider<WeekDaoHelper> mWeekDaoHelperProvider;
    private final Provider<WorkoutDaoHelper> mWorkoutDaoHelperProvider;
    private final HomeModule module;

    public HomeModule_ProvideAchievementsInteractorFactory(HomeModule module2, Provider<WorkoutDaoHelper> mWorkoutDaoHelperProvider2, Provider<AwardsDaoHelper> mAwardsDaoHelperProvider2, Provider<AchievementsDaoHelper> mAchievementsDaoHelperProvider2, Provider<WeekDaoHelper> mWeekDaoHelperProvider2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            if ($assertionsDisabled || mWorkoutDaoHelperProvider2 != null) {
                this.mWorkoutDaoHelperProvider = mWorkoutDaoHelperProvider2;
                if ($assertionsDisabled || mAwardsDaoHelperProvider2 != null) {
                    this.mAwardsDaoHelperProvider = mAwardsDaoHelperProvider2;
                    if ($assertionsDisabled || mAchievementsDaoHelperProvider2 != null) {
                        this.mAchievementsDaoHelperProvider = mAchievementsDaoHelperProvider2;
                        if ($assertionsDisabled || mWeekDaoHelperProvider2 != null) {
                            this.mWeekDaoHelperProvider = mWeekDaoHelperProvider2;
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

    public AchievementsContract get() {
        return (AchievementsContract) Preconditions.checkNotNull(this.module.provideAchievementsInteractor(this.mWorkoutDaoHelperProvider.get(), this.mAwardsDaoHelperProvider.get(), this.mAchievementsDaoHelperProvider.get(), this.mWeekDaoHelperProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<AchievementsContract> create(HomeModule module2, Provider<WorkoutDaoHelper> mWorkoutDaoHelperProvider2, Provider<AwardsDaoHelper> mAwardsDaoHelperProvider2, Provider<AchievementsDaoHelper> mAchievementsDaoHelperProvider2, Provider<WeekDaoHelper> mWeekDaoHelperProvider2) {
        return new HomeModule_ProvideAchievementsInteractorFactory(module2, mWorkoutDaoHelperProvider2, mAwardsDaoHelperProvider2, mAchievementsDaoHelperProvider2, mWeekDaoHelperProvider2);
    }

    public static AchievementsContract proxyProvideAchievementsInteractor(HomeModule instance, WorkoutDaoHelper mWorkoutDaoHelper, AwardsDaoHelper mAwardsDaoHelper, AchievementsDaoHelper mAchievementsDaoHelper, WeekDaoHelper mWeekDaoHelper) {
        return instance.provideAchievementsInteractor(mWorkoutDaoHelper, mAwardsDaoHelper, mAchievementsDaoHelper, mWeekDaoHelper);
    }
}
