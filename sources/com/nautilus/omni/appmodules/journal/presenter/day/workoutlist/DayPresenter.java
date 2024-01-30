package com.nautilus.omni.appmodules.journal.presenter.day.workoutlist;

import android.content.Context;
import com.nautilus.omni.app.BasePresenter;
import com.nautilus.omni.app.Preferences;
import com.nautilus.omni.appmodules.journal.presenter.day.workoutlist.DayInteractorContract;
import com.nautilus.omni.appmodules.journal.view.day.DayViewContract;
import com.nautilus.omni.model.dto.User;
import com.nautilus.omni.model.dto.Workout;
import java.util.List;
import javax.inject.Inject;

public class DayPresenter extends BasePresenter implements DayPresenterContract, DayInteractorContract.OnWorkoutsListLoaderListener, DayInteractorContract.OnOlderWorkoutsListLoaderListener, DayInteractorContract.OnNewSyncedDataLoaderListener, DayInteractorContract.OnWorkoutSelectedLoaderListener {
    private User mCurrentUser;
    private DayInteractor mDayInteractor;
    private DayListBuilderInteractor mDayListBuilderInteractor;
    private int mFirstVisibleItem;
    private DayViewContract mIDayView;
    private boolean mLoading = true;
    private boolean mNewWorkoutsLoaded = false;
    private int mPreviousTotal = 0;
    private int mTotalItemCount;
    private int mUserIndex;
    private int mVisibleItemCount;
    private int mVisibleThreshold = 5;

    @Inject
    public DayPresenter(Context context, DayInteractor dayInteractor) {
        super(context);
        getCurrentUserFromDatabase();
        this.mDayInteractor = dayInteractor;
        this.mDayInteractor.setCurrentUser(this.mCurrentUser);
        this.mDayListBuilderInteractor = new DayListBuilderInteractor(this.mDayInteractor);
    }

    private void getCurrentUserFromDatabase() {
        this.mUserIndex = this.mPreferences.getInt(Preferences.USER_INDEX, -1);
        this.mCurrentUser = this.mUserDaoHelper.getCurrentUser(this.mUserIndex);
    }

    public void loadWorkoutsList() {
        this.mDayInteractor.getTotalWorkoutsList();
        this.mDayInteractor.loadWorkoutsList(this);
    }

    public void onWorkoutsListLoaded(List<Object> dataList) {
        this.mDayListBuilderInteractor.setDataList(dataList);
        this.mIDayView.showWorkoutsList(dataList);
    }

    public void loadOlderData(int visibleListItemCount, int totalListItemCount, int firstVisibleItemPosition) {
        loadOlderDataWhenScroll(visibleListItemCount, totalListItemCount, firstVisibleItemPosition);
    }

    private void loadOlderDataWhenScroll(int visibleListItemCount, int totalItemCount, int firstVisibleItemPosition) {
        this.mVisibleItemCount = visibleListItemCount;
        this.mTotalItemCount = totalItemCount;
        this.mFirstVisibleItem = firstVisibleItemPosition;
        if (this.mLoading) {
            updateLoadingOlderDataState();
        }
        if (endOfListHasBeenReached()) {
            loadOlderWorkoutsList();
        }
    }

    private void updateLoadingOlderDataState() {
        if (this.mTotalItemCount > this.mPreviousTotal) {
            this.mLoading = false;
            this.mPreviousTotal = this.mTotalItemCount;
        }
    }

    private boolean endOfListHasBeenReached() {
        return !this.mLoading && this.mTotalItemCount - this.mVisibleItemCount <= this.mFirstVisibleItem + this.mVisibleThreshold;
    }

    private void loadOlderWorkoutsList() {
        this.mDayInteractor.loadOlderWorkoutsList(this);
        this.mLoading = true;
    }

    public void onOlderWorkoutsLoaded(List<Object> dataList) {
        this.mIDayView.refreshListWithOlderData(this.mDayListBuilderInteractor.getOlderData(dataList));
    }

    public void sendBroadcastToSyncNewData() {
        this.mDayInteractor.sendStartSyncBroadcast();
    }

    public void loadNewSyncedData(boolean newWorkoutsLoaded) {
        this.mNewWorkoutsLoaded = newWorkoutsLoaded;
        this.mDayInteractor.loadNewSyncedData(this, newWorkoutsLoaded);
    }

    public void onNewSyncedDataLoaded(List<Object> dataList) {
        this.mIDayView.refreshListWithNewSyncedData(this.mDayListBuilderInteractor.getNewSyncedData(this.mNewWorkoutsLoaded, dataList));
    }

    public void getSelectedWorkout(int workoutId) {
        this.mDayInteractor.loadSelectedWorkout(workoutId, this);
    }

    public void onWorkoutSelectedLoaded(Workout workout) {
        this.mIDayView.openWorkoutDetailScreen(workout);
    }

    public void setIDayView(DayViewContract dayView) {
        this.mIDayView = dayView;
    }
}
