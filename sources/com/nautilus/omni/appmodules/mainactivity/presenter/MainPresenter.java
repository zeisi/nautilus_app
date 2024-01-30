package com.nautilus.omni.appmodules.mainactivity.presenter;

import android.content.Context;
import com.nautilus.omni.app.BasePresenter;
import com.nautilus.omni.app.Preferences;
import com.nautilus.omni.appmodules.mainactivity.presenter.MainGoalsHelperInteractorContract;
import com.nautilus.omni.appmodules.mainactivity.view.MainViewContract;
import com.nautilus.omni.model.dto.User;
import javax.inject.Inject;

public class MainPresenter extends BasePresenter implements MainPresenterContract, MainGoalsHelperInteractorContract.CurrentGoalsCheckListener {
    private User mCurrentUser;
    private GoalsReminderToastViewBuilder mGoalsReminderToastViewBuilder;
    private MainGoalsHelperInteractor mMainGoalsHelperInteractor;
    private MainViewContract mMainView;
    private int mUserIndex;

    @Inject
    public MainPresenter(Context context, MainViewContract mainView, MainGoalsHelperInteractor mainGoalsHelperInteractor, GoalsReminderToastViewBuilder goalsReminderToastViewBuilder) {
        super(context);
        this.mMainView = mainView;
        getCurrentUserFromDatabase();
        this.mMainGoalsHelperInteractor = mainGoalsHelperInteractor;
        this.mMainGoalsHelperInteractor.setCurrentUser(this.mCurrentUser);
        this.mGoalsReminderToastViewBuilder = goalsReminderToastViewBuilder;
    }

    private void getCurrentUserFromDatabase() {
        this.mUserIndex = this.mPreferences.getInt(Preferences.USER_INDEX, -1);
        this.mCurrentUser = this.mUserDaoHelper.getCurrentUser(this.mUserIndex);
    }

    public void checkCurrentGoals() {
        this.mMainGoalsHelperInteractor.checkCurrentGoals(this);
    }

    public void onDefaultGoalsSaved() {
        this.mGoalsReminderToastViewBuilder.showGoalsReminderToast();
    }

    public void onTimeGoalsUpdated() {
        this.mMainView.showTimeGoalUpdatedDialog();
    }

    public void setGoalsEnabled(boolean goalsEnabled) {
        this.mMainGoalsHelperInteractor.setGoalsEnabled(goalsEnabled);
    }

    public void setShouldShowTimeGoalUpdatedDialog(boolean showTimeGoalUpdatedDialog) {
        this.mMainGoalsHelperInteractor.setShouldShowTimeGoalUpdatedDialog(showTimeGoalUpdatedDialog);
    }

    public void onDestroy() {
        this.mGoalsReminderToastViewBuilder = null;
    }
}
