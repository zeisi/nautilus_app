package com.nautilus.omni.dependencyinjection.modules.main;

import android.content.Context;
import com.nautilus.omni.appmodules.journal.presenter.day.workoutlist.DayInteractor;
import com.nautilus.omni.appmodules.journal.presenter.day.workoutlist.DayPresenter;
import com.nautilus.omni.appmodules.journal.presenter.week.WeekInteractor;
import com.nautilus.omni.appmodules.journal.presenter.week.WeekPresenter;
import com.nautilus.omni.appmodules.journal.presenter.year.YearInteractor;
import com.nautilus.omni.appmodules.journal.presenter.year.YearPresenter;
import com.nautilus.omni.appmodules.journal.view.JournalFragment;
import com.nautilus.omni.dataaccess.AchievementsDaoHelper;
import com.nautilus.omni.dataaccess.WorkoutDaoHelper;
import com.nautilus.omni.dependencyinjection.scopes.ActivityScope;
import dagger.Module;
import dagger.Provides;

@Module
public class JournalModule {
    JournalFragment mJournalView;

    public JournalModule(JournalFragment journalView) {
        this.mJournalView = journalView;
    }

    /* access modifiers changed from: package-private */
    @ActivityScope
    @Provides
    public JournalFragment provideJournalView() {
        return this.mJournalView;
    }

    /* access modifiers changed from: package-private */
    @ActivityScope
    @Provides
    public WeekInteractor provideWeekInteractor(Context context, WorkoutDaoHelper workoutDaoHelper) {
        return new WeekInteractor(context, workoutDaoHelper);
    }

    /* access modifiers changed from: package-private */
    @ActivityScope
    @Provides
    public WeekPresenter provideWeekPresenter(Context context, WeekInteractor weekInteractor) {
        return new WeekPresenter(context, weekInteractor);
    }

    /* access modifiers changed from: package-private */
    @ActivityScope
    @Provides
    public YearInteractor provideYearInteractor(Context context, WorkoutDaoHelper workoutDaoHelper) {
        return new YearInteractor(context, workoutDaoHelper);
    }

    /* access modifiers changed from: package-private */
    @ActivityScope
    @Provides
    public YearPresenter provideYearPresenter(Context context, YearInteractor yearInteractor) {
        return new YearPresenter(context, yearInteractor);
    }

    /* access modifiers changed from: package-private */
    @ActivityScope
    @Provides
    public DayInteractor provideDayInteractor(Context context, WorkoutDaoHelper workoutDaoHelper, AchievementsDaoHelper achievementsDaoHelper) {
        return new DayInteractor(context, workoutDaoHelper, achievementsDaoHelper);
    }

    /* access modifiers changed from: package-private */
    @ActivityScope
    @Provides
    public DayPresenter provideDayPresenter(Context context, DayInteractor dayInteractor) {
        return new DayPresenter(context, dayInteractor);
    }
}
