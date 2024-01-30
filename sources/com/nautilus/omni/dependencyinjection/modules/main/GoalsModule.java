package com.nautilus.omni.dependencyinjection.modules.main;

import android.content.Context;
import com.nautilus.omni.appmodules.goals.presenter.GoalsPresenter;
import com.nautilus.omni.appmodules.goals.presenter.interactors.LoadGoalsInteractor;
import com.nautilus.omni.appmodules.goals.presenter.interactors.SaveGoalsInteractor;
import com.nautilus.omni.appmodules.goals.view.GoalsFragment;
import com.nautilus.omni.dataaccess.AchievementsDaoHelper;
import com.nautilus.omni.dataaccess.GoalsDaoHelper;
import com.nautilus.omni.dataaccess.WorkoutDaoHelper;
import com.nautilus.omni.dependencyinjection.scopes.ActivityScope;
import dagger.Module;
import dagger.Provides;

@Module
public class GoalsModule {
    GoalsFragment mGoalsView;

    public GoalsModule(GoalsFragment goalsView) {
        this.mGoalsView = goalsView;
    }

    /* access modifiers changed from: package-private */
    @ActivityScope
    @Provides
    public GoalsFragment provideGoalsView() {
        return this.mGoalsView;
    }

    /* access modifiers changed from: package-private */
    @ActivityScope
    @Provides
    public LoadGoalsInteractor provideLoadGoalsInteractor(Context context, GoalsDaoHelper goalsDaoHelper) {
        return new LoadGoalsInteractor(context, goalsDaoHelper);
    }

    /* access modifiers changed from: package-private */
    @ActivityScope
    @Provides
    public SaveGoalsInteractor provideSaveGoalsInteractor(AchievementsDaoHelper achievementsDaoHelper, GoalsDaoHelper goalsDaoHelper, WorkoutDaoHelper workoutDaoHelper) {
        return new SaveGoalsInteractor(achievementsDaoHelper, goalsDaoHelper, workoutDaoHelper);
    }

    /* access modifiers changed from: package-private */
    @ActivityScope
    @Provides
    public GoalsPresenter provideGoalsPresenter(Context context, LoadGoalsInteractor goalsInteractor, SaveGoalsInteractor saveGoalsInteractor) {
        return new GoalsPresenter(context, goalsInteractor, saveGoalsInteractor);
    }
}
