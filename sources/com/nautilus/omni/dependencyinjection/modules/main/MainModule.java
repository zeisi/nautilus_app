package com.nautilus.omni.dependencyinjection.modules.main;

import android.content.Context;
import com.nautilus.omni.appmodules.awards.AwardValueBuilder;
import com.nautilus.omni.appmodules.goals.presenter.interactors.SaveGoalsInteractor;
import com.nautilus.omni.appmodules.mainactivity.presenter.GoalsReminderToastViewBuilder;
import com.nautilus.omni.appmodules.mainactivity.presenter.MainGoalsHelperInteractor;
import com.nautilus.omni.appmodules.mainactivity.view.MainActivity;
import com.nautilus.omni.appmodules.mainactivity.view.MainViewContract;
import com.nautilus.omni.dataaccess.GoalsDaoHelper;
import com.nautilus.omni.dataaccess.WeekDaoHelper;
import com.nautilus.omni.dependencyinjection.scopes.ActivityScope;
import com.nautilus.omni.permissions.PermissionAction;
import com.nautilus.omni.permissions.PermissionPresenter;
import com.nautilus.omni.permissions.SupportPermissionActionImpl;
import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {
    MainActivity mMainActivity;

    public MainModule(MainActivity mainActivity) {
        this.mMainActivity = mainActivity;
    }

    /* access modifiers changed from: package-private */
    @ActivityScope
    @Provides
    public MainActivity providesMainActivity() {
        return this.mMainActivity;
    }

    /* access modifiers changed from: package-private */
    @ActivityScope
    @Provides
    public MainViewContract providesMainViewContract() {
        return this.mMainActivity;
    }

    /* access modifiers changed from: package-private */
    @ActivityScope
    @Provides
    public GoalsReminderToastViewBuilder providesGoalsReminderToastViewBuilder(MainActivity mainActivity) {
        return new GoalsReminderToastViewBuilder(mainActivity);
    }

    /* access modifiers changed from: package-private */
    @ActivityScope
    @Provides
    public MainGoalsHelperInteractor providesMainGoalsHelperInteractor(Context context, SaveGoalsInteractor saveGoalsInteractor, GoalsDaoHelper goalsDaoHelper) {
        return new MainGoalsHelperInteractor(context, saveGoalsInteractor, goalsDaoHelper);
    }

    /* access modifiers changed from: package-private */
    @ActivityScope
    @Provides
    public PermissionAction providePermissionAction(MainActivity mainActivity) {
        return new SupportPermissionActionImpl(mainActivity);
    }

    /* access modifiers changed from: package-private */
    @ActivityScope
    @Provides
    public PermissionPresenter providePermissionPresenter(PermissionAction permissionAction, MainActivity mainActivity) {
        return new PermissionPresenter(permissionAction, mainActivity);
    }

    /* access modifiers changed from: package-private */
    @ActivityScope
    @Provides
    public AwardValueBuilder provideAwardValueBuilder(Context context, WeekDaoHelper weekDaoHelper) {
        return new AwardValueBuilder(context, weekDaoHelper);
    }
}
