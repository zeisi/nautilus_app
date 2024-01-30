package com.nautilus.omni.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.nautilus.omni.app.OmniApplication;
import com.nautilus.omni.app.Preferences;
import com.nautilus.omni.dataaccess.AchievementsDaoHelper;
import com.nautilus.omni.dataaccess.AwardsDaoHelper;
import com.nautilus.omni.dataaccess.GoalsDaoHelper;
import com.nautilus.omni.dataaccess.WeekDaoHelper;
import com.nautilus.omni.dataaccess.WorkoutDaoHelper;
import com.nautilus.omni.databasemanager.DataBaseHelper;
import com.nautilus.omni.dependencyinjection.components.AppComponent;
import com.nautilus.omni.model.dto.Award;
import com.nautilus.omni.model.dto.User;
import com.nautilus.omni.model.dto.Workout;
import com.nautilus.omni.syncservices.syncserviceshelpers.AwardSyncDataLoaderHelper;
import com.nautilus.omni.syncservices.syncserviceshelpers.FitServicesSyncDataHelper;
import com.nautilus.omni.syncservices.syncserviceshelpers.GoalsSyncVerifier;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class WorkoutsEvaluator {
    public static final String TAG = WorkoutsEvaluator.class.getSimpleName();
    private AchievementsDaoHelper mAchievementDaoHelper;
    private AwardSyncDataLoaderHelper mAwardDataLoaderHelper;
    private AwardsDaoHelper mAwardsDaoHelper;
    private Context mContext;
    private User mCurrentUser;
    private DataBaseHelper mDataBaseHelper;
    private FitServicesSyncDataHelper mFitServicesSyncDataHelper;
    private GoalsDaoHelper mGoalsDaoHelper;
    private boolean mGoalsEnabled;
    private GoalsSyncVerifier mGoalsSyncVerifier;
    private SharedPreferences mSettings;
    private int mUserIndex;
    private WeekDaoHelper mWeekDaoHelper;
    private WorkoutDaoHelper mWorkoutDaoHelper;

    public WorkoutsEvaluator(Context context) {
        this.mContext = context;
        AppComponent appComponent = ((OmniApplication) this.mContext).getAppComponent();
        getSharedPreferences();
        initDataBaseElements(appComponent);
        getCurrentUser();
        initHelpers();
    }

    private void getSharedPreferences() {
        this.mSettings = this.mContext.getSharedPreferences(Preferences.OMNI_TRAINER_PREFERENCES, 0);
        this.mUserIndex = this.mSettings.getInt(Preferences.USER_INDEX, -1);
        this.mGoalsEnabled = this.mSettings.getBoolean(Preferences.GOALS_ENABLED, false);
    }

    private void getCurrentUser() {
        try {
            List<User> usersList = this.mDataBaseHelper.getUserDao().queryBuilder().where().eq(User.USER_INDEX, Integer.valueOf(this.mUserIndex)).query();
            if (usersList.size() > 0) {
                this.mCurrentUser = usersList.get(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initHelpers() {
        if (this.mCurrentUser != null) {
            this.mAwardDataLoaderHelper = new AwardSyncDataLoaderHelper(this.mContext, this.mWorkoutDaoHelper, this.mAwardsDaoHelper, this.mWeekDaoHelper);
            this.mFitServicesSyncDataHelper.setCurrentUserNumber(this.mCurrentUser.getUserOmniTrainerIndex());
            this.mGoalsSyncVerifier = new GoalsSyncVerifier(this.mAchievementDaoHelper, this.mGoalsDaoHelper, this.mCurrentUser, this.mGoalsEnabled);
        }
    }

    private void initDataBaseElements(AppComponent appComponent) {
        this.mDataBaseHelper = appComponent.getDatabaseHelper();
        this.mGoalsDaoHelper = appComponent.getGoalsDaoHelper();
        this.mWorkoutDaoHelper = appComponent.getWorkoutDaoHelper();
        this.mAchievementDaoHelper = appComponent.getAchievementsDaoHelper();
        this.mAwardsDaoHelper = appComponent.getAwardsDaoHelper();
        this.mWeekDaoHelper = appComponent.getWeekDaoHelper();
        this.mFitServicesSyncDataHelper = appComponent.getFitServicesDataHelper();
    }

    public void reevaluateWorkouts() {
        List<Workout> workoutsList = this.mWorkoutDaoHelper.getAllWorkouts(true);
        if (workoutsList == null || workoutsList.size() <= 0) {
            Log.d(TAG, "DEBUG - REEVALUATION NOT NEEDED, DATABASE EMPTY...");
        } else {
            executeReevaluation(workoutsList);
        }
    }

    private void executeReevaluation(List<Workout> workoutsList) {
        try {
            Log.d(TAG, "DEBUG - REEVALUATING WORKOUTS...");
            this.mDataBaseHelper.resetTablesForInconsistentDateWorkouts();
            this.mDataBaseHelper.resetWorkouts();
            List<Workout> arrayList = new ArrayList<>(resetWorkoutsIds(workoutsList));
            try {
                for (Workout workout : arrayList) {
                    this.mWorkoutDaoHelper.createWorkout(workout);
                    this.mAwardDataLoaderHelper.updateWeekTable(workout);
                    this.mAwardDataLoaderHelper.checkForAwards(workout);
                    this.mGoalsSyncVerifier.verifyGoals(workout);
                    this.mFitServicesSyncDataHelper.addWorkoutToFitServicesTable(workout);
                }
                List<Workout> workoutsList2 = arrayList;
            } catch (SQLException e) {
                e = e;
                List<Workout> list = arrayList;
                e.printStackTrace();
            }
        } catch (SQLException e2) {
            e = e2;
            e.printStackTrace();
        }
    }

    private List<Workout> resetWorkoutsIds(List<Workout> workoutsList) {
        for (Workout workout : workoutsList) {
            workout.setmId(0);
            workout.setmAwards((Collection<Award>) null);
        }
        return workoutsList;
    }
}
