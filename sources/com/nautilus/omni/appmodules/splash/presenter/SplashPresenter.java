package com.nautilus.omni.appmodules.splash.presenter;

import android.content.Context;
import com.nautilus.omni.R;
import com.nautilus.omni.app.BasePresenter;
import com.nautilus.omni.app.OmniApplication;
import com.nautilus.omni.app.Preferences;
import com.nautilus.omni.appmodules.splash.presenter.interactor.SplashInteractorContract;
import com.nautilus.omni.appmodules.splash.view.SplashViewContract;
import com.nautilus.omni.dataaccess.AchievementsDaoHelper;
import com.nautilus.omni.dataaccess.GoalsDaoHelper;
import com.nautilus.omni.dataaccess.WorkoutDaoHelper;
import com.nautilus.omni.dependencyinjection.components.AppComponent;
import com.nautilus.omni.model.dto.User;
import com.nautilus.omni.util.Util;
import java.util.Locale;
import javax.inject.Inject;

public class SplashPresenter extends BasePresenter implements SplashPresenterContract, SplashInteractorContract.OnSplashResult {
    AchievementsDaoHelper mAchievementDaoHelper;
    private User mCurrentUser;
    GoalsDaoHelper mGoalsDaoHelper;
    boolean mIsFirstTimeAppExecutes = this.mPreferences.getBoolean(Preferences.FIRST_RUN, true);
    SplashInteractorContract mSplashInteractor;
    SplashViewContract mSplashView;
    private int mUserIndex;
    WorkoutDaoHelper mWorkoutDaoHelper;

    @Inject
    public SplashPresenter(Context context, SplashInteractorContract splashInteractor, SplashViewContract splashActivity) {
        super(context);
        this.mSplashInteractor = splashInteractor;
        this.mSplashView = splashActivity;
    }

    public void checkFirstPreferences() {
        int mDatabaseVersion = this.mPreferences.getInt(Preferences.DATABASE_VERSION, 2);
        if (this.mIsFirstTimeAppExecutes || mDatabaseVersion < 2) {
            updateFirstPreferences();
        }
    }

    public void updateAwardTypes() {
        this.mSplashInteractor.updateAwardTypes(this.mPreferences.getInt(Preferences.AWARD_TYPES_VERSION_KEY, 1), this);
    }

    public void showUpdatingDatabaseToast() {
        if (timeGoalShouldBeUpdated()) {
            this.mSplashView.showUpdatingDatabaseToast(this.mContext.getString(R.string.updating_database));
        }
    }

    public void updateTimeGoalValues() {
        getCurrentUserFromDatabase();
        boolean goalsEnabled = this.mPreferences.getBoolean(Preferences.GOALS_ENABLED, false);
        if (timeGoalShouldBeUpdated()) {
            initDataBaseElements();
            new GoalsUpdater(goalsEnabled, this.mWorkoutDaoHelper, this.mGoalsDaoHelper, this.mAchievementDaoHelper, this.mCurrentUser, this.mPreferences).updateTimeGoalValues();
            return;
        }
        this.mPreferences.edit().putBoolean(Preferences.TIME_GOAL_VALUES_ALREADY_UPDATED, true).commit();
    }

    private boolean timeGoalShouldBeUpdated() {
        boolean goalsEnabled = this.mPreferences.getBoolean(Preferences.GOALS_ENABLED, false);
        if (!this.mPreferences.getBoolean(Preferences.HAS_OMNI_TRAINER_DEVICE_SET, false) || !goalsEnabled || this.mPreferences.getBoolean(Preferences.TIME_GOAL_VALUES_ALREADY_UPDATED, false)) {
            return false;
        }
        return true;
    }

    private void getCurrentUserFromDatabase() {
        this.mUserIndex = this.mPreferences.getInt(Preferences.USER_INDEX, -1);
        this.mCurrentUser = this.mUserDaoHelper.getCurrentUser(this.mUserIndex);
    }

    private void initDataBaseElements() {
        AppComponent appComponent = ((OmniApplication) this.mContext).getAppComponent();
        this.mGoalsDaoHelper = appComponent.getGoalsDaoHelper();
        this.mWorkoutDaoHelper = appComponent.getWorkoutDaoHelper();
        this.mAchievementDaoHelper = appComponent.getAchievementsDaoHelper();
    }

    public void createAwardTypesInDatabase() {
        this.mSplashInteractor.createAwardTypesInDatabase();
    }

    public void startNextActivity() {
        boolean hasOmniTrainerDeviceSet = this.mPreferences.getBoolean(Preferences.HAS_OMNI_TRAINER_DEVICE_SET, false);
        if (this.mIsFirstTimeAppExecutes) {
            this.mSplashView.startTutorialActivity();
        } else if (hasOmniTrainerDeviceSet) {
            this.mSplashView.startBLECallbacksHandlerService();
            this.mSplashView.startHomeActivity();
        } else {
            this.mSplashView.startConnectionWizard();
        }
    }

    private void updateFirstPreferences() {
        this.mPreferences.edit().putInt(Preferences.UNITS_SETTINGS, Util.getMetricBasedOnCurrentLocale(Locale.getDefault())).apply();
        this.mPreferences.edit().putBoolean(Preferences.FIRST_RUN, false).apply();
        this.mPreferences.edit().putInt(Preferences.DATABASE_VERSION, 2).apply();
    }

    public void onUpdateAwardTypesSuccess() {
        this.mPreferences.edit().putInt(Preferences.AWARD_TYPES_VERSION_KEY, 1).apply();
        this.mPreferences.edit().putBoolean(Preferences.AWARD_TYPES_FIRST_UPDATE_KEY, false).apply();
    }
}
