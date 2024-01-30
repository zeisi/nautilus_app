package com.nautilus.omni.syncservices.syncserviceshelpers;

import android.content.Context;
import android.util.Log;
import com.nautilus.omni.app.OmniApplication;
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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.joda.time.ReadableInstant;

public class WorkoutSyncDataLoaderHelper {
    public static final String TAG = WorkoutSyncDataLoaderHelper.class.getSimpleName();
    private AchievementsDaoHelper mAchievementDaoHelper;
    private AwardSyncDataLoaderHelper mAwardDataLoaderHelper;
    private AwardsDaoHelper mAwardsDaoHelper;
    private User mCurrentUser;
    private DataBaseHelper mDataBaseHelper;
    private FitServicesSyncDataHelper mFitServicesSyncDataHelper;
    private GoalsDaoHelper mGoalsDaoHelper;
    private boolean mGoalsEnabled;
    private GoalsSyncVerifier mGoalsSyncVerifier;
    private boolean mIsThereAnyInconsistentWorkout = false;
    private Workout mMostRecentWorkout;
    private WeekDaoHelper mWeekDaoHelper;
    private WorkoutDaoHelper mWorkoutDaoHelper;
    private ArrayList<Workout> mWorkoutsList;

    public WorkoutSyncDataLoaderHelper(Context context, User currentUser, ArrayList<Workout> workoutsList, boolean goalsEnabled) {
        AppComponent appComponent = ((OmniApplication) context).getAppComponent();
        initDataBaseElements(appComponent);
        this.mWorkoutsList = workoutsList;
        this.mCurrentUser = currentUser;
        this.mGoalsEnabled = goalsEnabled;
        getMostRecentWorkout();
        this.mAwardDataLoaderHelper = new AwardSyncDataLoaderHelper(context, this.mWorkoutDaoHelper, this.mAwardsDaoHelper, this.mWeekDaoHelper);
        this.mFitServicesSyncDataHelper.setCurrentUserNumber(this.mCurrentUser.getUserOmniTrainerIndex());
        this.mGoalsSyncVerifier = new GoalsSyncVerifier(this.mAchievementDaoHelper, this.mGoalsDaoHelper, this.mCurrentUser, this.mGoalsEnabled);
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

    private void getMostRecentWorkout() {
        this.mMostRecentWorkout = this.mWorkoutDaoHelper.getLastWorkout();
    }

    public boolean saveWorkouts() {
        try {
            if (this.mMostRecentWorkout == null) {
                this.mMostRecentWorkout = this.mWorkoutsList.get(this.mWorkoutsList.size() - 1);
            }
            for (int workoutCounter = this.mWorkoutsList.size() - 1; workoutCounter > -1; workoutCounter--) {
                Workout workout = this.mWorkoutsList.get(workoutCounter);
                if (workout.getmWorkoutDate().isBefore((ReadableInstant) this.mMostRecentWorkout.getmWorkoutDate())) {
                    this.mIsThereAnyInconsistentWorkout = true;
                }
                this.mWorkoutDaoHelper.createWorkout(workout);
                this.mAwardDataLoaderHelper.updateWeekTable(workout);
                this.mAwardDataLoaderHelper.checkForAwards(workout);
                this.mGoalsSyncVerifier.verifyGoals(workout);
                this.mMostRecentWorkout = this.mWorkoutDaoHelper.getLastWorkout();
                this.mFitServicesSyncDataHelper.addWorkoutToFitServicesTable(workout);
            }
            if (this.mIsThereAnyInconsistentWorkout) {
                reevaluateWorkouts();
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void reevaluateWorkouts() {
        try {
            Log.d(TAG, "DEBUG - INCONSISTENT WORKOUTS - REEVALUATING");
            this.mDataBaseHelper.resetTablesForInconsistentDateWorkouts();
            List<Workout> workoutsList = this.mWorkoutDaoHelper.getAllWorkouts(true);
            this.mDataBaseHelper.resetWorkouts();
            this.mGoalsDaoHelper.resetGoalsToNotAchieved();
            for (Workout workout : new ArrayList<>(resetWorkoutsIds(workoutsList))) {
                this.mWorkoutDaoHelper.createWorkout(workout);
                this.mAwardDataLoaderHelper.updateWeekTable(workout);
                this.mAwardDataLoaderHelper.checkForAwards(workout);
                this.mGoalsSyncVerifier.verifyGoals(workout);
                this.mFitServicesSyncDataHelper.addWorkoutToFitServicesTable(workout);
            }
        } catch (SQLException e) {
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
