package com.nautilus.omni.appmodules.journal.presenter.day.workoutlist;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import com.nautilus.omni.appmodules.journal.adapters.JournalDayAdapter;
import com.nautilus.omni.appmodules.journal.presenter.day.workoutlist.DayInteractorContract;
import com.nautilus.omni.dataaccess.AchievementsDaoHelper;
import com.nautilus.omni.dataaccess.WorkoutDaoHelper;
import com.nautilus.omni.model.dto.Achievement;
import com.nautilus.omni.model.dto.User;
import com.nautilus.omni.model.dto.Workout;
import com.nautilus.omni.util.BroadcastKeys;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.inject.Inject;
import org.joda.time.DateTime;

public class DayInteractor implements DayInteractorContract {
    private static final int NUMBER_OF_WORKOUTS = 10;
    private List<Achievement> mAchievementList;
    private AchievementsDaoHelper mAchievementsDaoHelper;
    private Context mContext;
    private User mCurrentUser;
    private List<Object> mDataList;
    private String mDivider = JournalDayAdapter.DIVIDER_ID;
    private Workout mMostRecentWorkout;
    private DateTime mNewSyncWorkoutsStartDate;
    private List<Workout> mOlderWorkoutList;
    private DateTime mOlderWorkoutStartDate;
    private DateTime mOlderWorkoutsEndDate;
    private Workout mOldestWorkout;
    private List<Workout> mTotalWorkoutsList;
    private WorkoutDaoHelper mWorkoutDaoHelper;

    @Inject
    public DayInteractor(Context context, WorkoutDaoHelper workoutDaoHelper, AchievementsDaoHelper achievementsDaoHelper) {
        this.mContext = context;
        this.mWorkoutDaoHelper = workoutDaoHelper;
        this.mAchievementsDaoHelper = achievementsDaoHelper;
    }

    public void setCurrentUser(User currentUser) {
        this.mCurrentUser = currentUser;
    }

    public void getTotalWorkoutsList() {
        if (this.mCurrentUser != null) {
            this.mTotalWorkoutsList = new ArrayList(this.mWorkoutDaoHelper.getAllWorkouts(false));
        }
    }

    public void loadWorkoutsList(DayInteractorContract.OnWorkoutsListLoaderListener onWorkoutsListLoaderListener) {
        this.mDataList = new ArrayList();
        onWorkoutsListLoaderListener.onWorkoutsListLoaded(buildWorkoutDataList());
    }

    public void loadOlderWorkoutsList(DayInteractorContract.OnOlderWorkoutsListLoaderListener onOlderWorkoutsListLoaderListener) {
        onOlderWorkoutsListLoaderListener.onOlderWorkoutsLoaded(getOlderWorkoutData());
    }

    public void sendStartSyncBroadcast() {
        sendStartSyncProcessBroadcast();
    }

    public void loadSelectedWorkout(int workoutId, DayInteractorContract.OnWorkoutSelectedLoaderListener onWorkoutSelectedLoaderListener) {
        Workout workout = this.mWorkoutDaoHelper.getWorkoutById(workoutId);
        if (workout != null) {
            onWorkoutSelectedLoaderListener.onWorkoutSelectedLoaded(workout);
        }
    }

    private void sendStartSyncProcessBroadcast() {
        Intent syncStartedIntent = new Intent();
        syncStartedIntent.setAction(BroadcastKeys.START_SYNC_PROCESS);
        LocalBroadcastManager.getInstance(this.mContext.getApplicationContext()).sendBroadcast(syncStartedIntent);
    }

    public void loadNewSyncedData(DayInteractorContract.OnNewSyncedDataLoaderListener onNewSyncedDataLoaderListener, boolean newWorkoutsLoaded) {
        if (newWorkoutsLoaded) {
            loadNewSyncedWorkouts(onNewSyncedDataLoaderListener);
        } else {
            onNewSyncedDataLoaderListener.onNewSyncedDataLoaded(new ArrayList());
        }
    }

    private void loadNewSyncedWorkouts(DayInteractorContract.OnNewSyncedDataLoaderListener onNewSyncedDataLoaderListener) {
        if (this.mTotalWorkoutsList.size() > 0) {
            onNewSyncedDataLoaderListener.onNewSyncedDataLoaded(getNewSyncedData());
        } else {
            onNewSyncedDataLoaderListener.onNewSyncedDataLoaded(buildWorkoutDataList());
        }
    }

    public List<Object> buildWorkoutDataList() {
        if (this.mTotalWorkoutsList != null && this.mTotalWorkoutsList.size() > 0) {
            buildFirstListOfWorkouts(this.mTotalWorkoutsList);
            setCurrentOlderWorkout();
            setMostRecentWorkout();
            if (this.mOlderWorkoutList.size() > 0) {
                generateDataList(false, this.mOlderWorkoutList, this.mOlderWorkoutStartDate, this.mOlderWorkoutsEndDate);
            }
        }
        return this.mDataList;
    }

    public List<Object> getNewSyncedData() {
        this.mDataList.clear();
        List<Workout> mostRecentWorkoutsList = getMostRecentWorkoutList(new ArrayList<>());
        if (mostRecentWorkoutsList.size() > 0) {
            sortList(mostRecentWorkoutsList);
            generateDataList(true, mostRecentWorkoutsList, this.mNewSyncWorkoutsStartDate, this.mNewSyncWorkoutsStartDate);
            appendNewDataToOlderData(mostRecentWorkoutsList);
            setMostRecentWorkout();
            setNewSyncedWorkoutsStartDate(this.mMostRecentWorkout.getmWorkoutDate().withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0));
        }
        return this.mDataList;
    }

    private List<Workout> getMostRecentWorkoutList(List<Workout> mostRecentWorkoutsList) {
        if (this.mMostRecentWorkout != null) {
            return this.mWorkoutDaoHelper.getMostRecentWorkouts(this.mMostRecentWorkout.getmWorkoutDate());
        }
        if (this.mCurrentUser != null) {
            mostRecentWorkoutsList = this.mWorkoutDaoHelper.getAllWorkouts(false);
        }
        if (mostRecentWorkoutsList.size() <= 0) {
            return mostRecentWorkoutsList;
        }
        buildFirstListOfWorkouts(mostRecentWorkoutsList);
        setCurrentOlderWorkout();
        setMostRecentWorkout();
        return mostRecentWorkoutsList;
    }

    private void appendNewDataToOlderData(List<Workout> mostRecentWorkoutsList) {
        mostRecentWorkoutsList.addAll(this.mTotalWorkoutsList);
        this.mTotalWorkoutsList = new ArrayList(mostRecentWorkoutsList);
    }

    private void sortList(List<Workout> workoutsList) {
        Collections.sort(workoutsList, new Comparator<Workout>() {
            public int compare(Workout w1, Workout w2) {
                if (w1.getmId() < w2.getmId()) {
                    return 1;
                }
                return -1;
            }
        });
    }

    public List<Object> getOlderWorkoutData() {
        this.mDataList.clear();
        if (this.mTotalWorkoutsList.size() > 10) {
            int end = this.mTotalWorkoutsList.indexOf(this.mOldestWorkout) + 10 + 1;
            if (end >= this.mTotalWorkoutsList.size()) {
                end = this.mTotalWorkoutsList.size();
            }
            getNextTenOlderWorkouts(this.mTotalWorkoutsList.indexOf(this.mOldestWorkout) + 1, end, this.mTotalWorkoutsList);
            setCurrentOlderWorkout();
            if (this.mOlderWorkoutList.size() > 0) {
                generateDataList(false, this.mOlderWorkoutList, this.mOlderWorkoutStartDate, this.mOlderWorkoutsEndDate);
            }
        }
        return this.mDataList;
    }

    private void setMostRecentWorkout() {
        if (this.mTotalWorkoutsList.size() > 0) {
            this.mMostRecentWorkout = this.mTotalWorkoutsList.get(0);
        }
    }

    private void setCurrentOlderWorkout() {
        if (this.mOlderWorkoutList.size() > 0) {
            this.mOldestWorkout = this.mOlderWorkoutList.get(this.mOlderWorkoutList.size() - 1);
        }
    }

    private void generateDataList(boolean isSyncNewData, List<Workout> workoutsDataList, DateTime workoutStartDate, DateTime workoutEndDate) {
        if (isSyncNewData) {
            this.mAchievementList = this.mAchievementsDaoHelper.getMoreNewAchievements(workoutStartDate, this.mCurrentUser);
        } else {
            this.mAchievementList = this.mAchievementsDaoHelper.getMoreOldAchievements(workoutStartDate, workoutEndDate, this.mCurrentUser);
        }
        String currentDay = workoutsDataList.get(0).getmWorkoutDate().dayOfYear().getAsText();
        String currentYear = workoutsDataList.get(0).getmWorkoutDate().year().getAsText();
        for (Workout workout : workoutsDataList) {
            if (isLastWorkoutForCurrentDay(currentDay, currentYear, workout)) {
                addDayAchievementsToDataList(currentDay, currentYear);
                currentDay = workout.getmWorkoutDate().dayOfYear().getAsText();
                currentYear = workout.getmWorkoutDate().year().getAsText();
            }
            if (!isDayInList(workout.getmWorkoutDate().dayOfYear().getAsText(), workout.getmWorkoutDate().year().getAsText())) {
                addDayToDataList(workout.getmWorkoutDate());
            }
            this.mDataList.add(workout);
            this.mDataList.add(this.mDivider);
        }
        addDayAchievementsToDataList(currentDay, currentYear);
    }

    private boolean isLastWorkoutForCurrentDay(String currentDay, String currentYear, Workout workout) {
        return !currentDay.equals(workout.getmWorkoutDate().dayOfYear().getAsText()) || !currentYear.equals(workout.getmWorkoutDate().year().getAsText());
    }

    private boolean isDayInList(String currentDay, String year) {
        if (this.mDataList.size() == 0) {
            return false;
        }
        for (Object object : this.mDataList) {
            if ((object instanceof DateTime) && ((DateTime) object).dayOfYear().getAsText().equals(currentDay) && ((DateTime) object).year().getAsText().equals(year)) {
                return true;
            }
        }
        return false;
    }

    private void addDayToDataList(DateTime date) {
        if (this.mDataList.size() > 0) {
            this.mDataList.remove(this.mDataList.size() - 1);
        }
        this.mDataList.add(date);
    }

    private void addDayAchievementsToDataList(String currentDay, String currentYear) {
        if (this.mDataList.size() > 0 && this.mAchievementList != null) {
            for (Achievement achievement : this.mAchievementList) {
                if (achievement.getmGoalAchievementDate().dayOfYear().getAsText().equals(currentDay) && achievement.getmGoalAchievementDate().year().getAsText().equals(currentYear)) {
                    this.mDataList.add(achievement);
                    this.mDataList.add(this.mDivider);
                }
            }
        }
    }

    private void buildFirstListOfWorkouts(List<Workout> workoutList) {
        getNextTenOlderWorkouts(0, 10, workoutList);
        setNewSyncedWorkoutsStartDate(workoutList.get(0).getmWorkoutDate().withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0));
    }

    private void setNewSyncedWorkoutsStartDate(DateTime date) {
        this.mNewSyncWorkoutsStartDate = date;
    }

    private void getNextTenOlderWorkouts(int start, int end, List<Workout> workoutList) {
        if (end > 10) {
            this.mOlderWorkoutList = new ArrayList(workoutList.subList(start, end));
        } else {
            this.mOlderWorkoutList = new ArrayList(workoutList);
        }
        setOlderWorkoutsStartDate();
        setOlderWorkoutsEndDate();
    }

    private void setOlderWorkoutsStartDate() {
        if (this.mOlderWorkoutList.size() > 0) {
            this.mOlderWorkoutStartDate = this.mOlderWorkoutList.get(this.mOlderWorkoutList.size() - 1).getmWorkoutDate().withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0);
        }
    }

    private void setOlderWorkoutsEndDate() {
        if (this.mOlderWorkoutList.size() > 0) {
            this.mOlderWorkoutsEndDate = this.mOlderWorkoutList.get(0).getmWorkoutDate().withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59);
        }
    }
}
