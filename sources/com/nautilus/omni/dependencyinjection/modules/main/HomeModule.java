package com.nautilus.omni.dependencyinjection.modules.main;

import android.content.Context;
import com.nautilus.omni.appmodules.awards.AwardValueBuilder;
import com.nautilus.omni.appmodules.home.domain.helpers.GaugeHelper;
import com.nautilus.omni.appmodules.home.domain.helpers.IGaugeHelper;
import com.nautilus.omni.appmodules.home.domain.helpers.IThisWeekHelper;
import com.nautilus.omni.appmodules.home.domain.helpers.ThisWeekHelper;
import com.nautilus.omni.appmodules.home.domain.interactors.AchievementsContract;
import com.nautilus.omni.appmodules.home.domain.interactors.AchievementsInteractor;
import com.nautilus.omni.appmodules.home.domain.interactors.GaugeInteractor;
import com.nautilus.omni.appmodules.home.domain.interactors.GaugeInteractorContract;
import com.nautilus.omni.appmodules.home.domain.interactors.HomeInteractor;
import com.nautilus.omni.appmodules.home.domain.interactors.HomeInteractorContract;
import com.nautilus.omni.appmodules.home.domain.interactors.ThisWeekSectionInteractor;
import com.nautilus.omni.appmodules.home.domain.interactors.ThisWeekSectionInteractorContract;
import com.nautilus.omni.appmodules.home.presenter.AchievementsPresenter;
import com.nautilus.omni.appmodules.home.presenter.AchievementsPresenterContract;
import com.nautilus.omni.appmodules.home.presenter.GaugePresenter;
import com.nautilus.omni.appmodules.home.presenter.GaugePresenterContract;
import com.nautilus.omni.appmodules.home.presenter.HomePresenter;
import com.nautilus.omni.appmodules.home.presenter.HomePresenterContract;
import com.nautilus.omni.appmodules.home.presenter.ThisWeekPresenter;
import com.nautilus.omni.appmodules.home.presenter.ThisWeekPresenterContract;
import com.nautilus.omni.appmodules.home.view.HomeFragment;
import com.nautilus.omni.dataaccess.AchievementsDaoHelper;
import com.nautilus.omni.dataaccess.AwardsDaoHelper;
import com.nautilus.omni.dataaccess.GoalsDaoHelper;
import com.nautilus.omni.dataaccess.UserDaoHelper;
import com.nautilus.omni.dataaccess.WeekDaoHelper;
import com.nautilus.omni.dataaccess.WorkoutDaoHelper;
import com.nautilus.omni.dependencyinjection.scopes.ActivityScope;
import dagger.Module;
import dagger.Provides;

@Module
public class HomeModule {
    HomeFragment mHomeView;

    public HomeModule(HomeFragment homeView) {
        this.mHomeView = homeView;
    }

    /* access modifiers changed from: package-private */
    @ActivityScope
    @Provides
    public HomeInteractorContract provideIHomeInteractor(AwardsDaoHelper awardsDaoHelper, WorkoutDaoHelper workoutDaoHelper, AchievementsDaoHelper achievementsDaoHelper) {
        return new HomeInteractor(awardsDaoHelper, workoutDaoHelper, achievementsDaoHelper);
    }

    /* access modifiers changed from: package-private */
    @ActivityScope
    @Provides
    public HomeFragment provideHomeView() {
        return this.mHomeView;
    }

    /* access modifiers changed from: package-private */
    @ActivityScope
    @Provides
    public HomePresenterContract provideIHomePresenter(Context context, HomeInteractorContract homeInteractorContract, AwardValueBuilder awardValueBuilder) {
        return new HomePresenter(context, homeInteractorContract, awardValueBuilder);
    }

    /* access modifiers changed from: package-private */
    @ActivityScope
    @Provides
    public AchievementsContract provideAchievementsInteractor(WorkoutDaoHelper mWorkoutDaoHelper, AwardsDaoHelper mAwardsDaoHelper, AchievementsDaoHelper mAchievementsDaoHelper, WeekDaoHelper mWeekDaoHelper) {
        return new AchievementsInteractor(mWorkoutDaoHelper, mAwardsDaoHelper, mAchievementsDaoHelper, mWeekDaoHelper);
    }

    /* access modifiers changed from: package-private */
    @ActivityScope
    @Provides
    public AchievementsPresenterContract provideIAchievementsPresenter(Context context, AchievementsContract achievementsInteractor) {
        return new AchievementsPresenter(context, achievementsInteractor);
    }

    /* access modifiers changed from: package-private */
    @ActivityScope
    @Provides
    public GaugeInteractorContract provideIGaugeInteractor(WorkoutDaoHelper workoutDaoHelper, UserDaoHelper userDaoHelper, GoalsDaoHelper goalsDaoHelpe) {
        return new GaugeInteractor(workoutDaoHelper, userDaoHelper, goalsDaoHelpe);
    }

    /* access modifiers changed from: package-private */
    @ActivityScope
    @Provides
    public IGaugeHelper provideIGaugeHelper() {
        return new GaugeHelper();
    }

    /* access modifiers changed from: package-private */
    @ActivityScope
    @Provides
    public GaugePresenterContract provideIGaugePresenter(Context context, GaugeInteractorContract iGaugeInteractor, IGaugeHelper iGaugeHelper) {
        return new GaugePresenter(context, iGaugeInteractor, iGaugeHelper);
    }

    /* access modifiers changed from: package-private */
    @ActivityScope
    @Provides
    public ThisWeekSectionInteractorContract provideIThisWeekSectionInteractor(GoalsDaoHelper goalsDaoHelper, WeekDaoHelper weekDaoHelper, UserDaoHelper userDaoHelper, WorkoutDaoHelper workoutDaoHelper) {
        return new ThisWeekSectionInteractor(goalsDaoHelper, weekDaoHelper, userDaoHelper, workoutDaoHelper);
    }

    /* access modifiers changed from: package-private */
    @ActivityScope
    @Provides
    public IThisWeekHelper provideIThisWeekHelper(Context context) {
        return new ThisWeekHelper(context);
    }

    /* access modifiers changed from: package-private */
    @ActivityScope
    @Provides
    public ThisWeekPresenterContract provideIThisWeekPresenter(Context context, IThisWeekHelper thisWeekHelper, ThisWeekSectionInteractorContract thisWeekSectionInteractor) {
        return new ThisWeekPresenter(context, thisWeekHelper, thisWeekSectionInteractor);
    }
}
