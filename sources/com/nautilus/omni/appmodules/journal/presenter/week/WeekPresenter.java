package com.nautilus.omni.appmodules.journal.presenter.week;

import android.content.Context;
import com.nautilus.omni.app.BasePresenter;
import com.nautilus.omni.app.Preferences;
import com.nautilus.omni.appmodules.journal.presenter.JournalMetrics;
import com.nautilus.omni.appmodules.journal.presenter.week.WeekInteractorContract;
import com.nautilus.omni.appmodules.journal.view.week.WeekViewContract;
import com.nautilus.omni.model.dto.User;
import java.util.ArrayList;
import javax.inject.Inject;
import org.joda.time.DateTime;

public class WeekPresenter extends BasePresenter implements WeekPresenterContract, WeekInteractorContract.OnLoadWorkoutsWeeklyInfoListener, WeekInteractorContract.OnCalculateWorkoutsInfoByMetricListener, WeekInteractorContract.OnCurrentWeekDateUpdatedListener {
    private User mCurrentUser;
    private DateTime mCurrentWeekDate;
    private WeekViewContract mIWeekView;
    private int mUserIndex;
    private WeekInteractor mWeekInteractor;

    @Inject
    public WeekPresenter(Context context, WeekInteractor weekInteractor) {
        super(context);
        getCurrentUserFromDatabase();
        this.mWeekInteractor = weekInteractor;
        this.mWeekInteractor.setCurrentUser(this.mCurrentUser);
    }

    private void getCurrentUserFromDatabase() {
        this.mUserIndex = this.mPreferences.getInt(Preferences.USER_INDEX, -1);
        this.mCurrentUser = this.mUserDaoHelper.getCurrentUser(this.mUserIndex);
    }

    public String[] loadMetricsSpinnerInfo() {
        return this.mWeekInteractor.loadMetricsSpinnerInfo();
    }

    public ArrayList<String> loadWeekDaysList() {
        return this.mWeekInteractor.loadWeekDaysList();
    }

    public void loadWorkoutsWeeklyInfo(int weekType, JournalMetrics metricType) {
        if (weekType == 1) {
            this.mWeekInteractor.getMostRecentWorkoutDate(this);
        }
        this.mWeekInteractor.loadWorkoutsWeeklyInfoSortedByDay(this, weekType, metricType);
    }

    public void loadWorkoutsInfoByMetricType(JournalMetrics metricType) {
        if (this.mCurrentUser != null && this.mCurrentWeekDate != null) {
            this.mWeekInteractor.loadWorkoutsInfoByMetricType(metricType, this);
        }
    }

    public void loadWorkoutsAfterSyncFinished(int weekType, JournalMetrics metricType) {
        this.mWeekInteractor.getMostRecentWorkoutDate(this);
        this.mWeekInteractor.loadWorkoutsWeeklyInfoSortedByDay(this, weekType, metricType);
    }

    public void onWorkoutInfoByMetricTypeCalculated(ArrayList<Double> graphicValues, String weekDate) {
        this.mIWeekView.showGraphicInfoAccordingWithMetricSelected(graphicValues);
        this.mIWeekView.updateWeekDateTextView(weekDate);
    }

    public void onWorkoutsWeeklyInfoLoaded(ArrayList<Double> graphicValues, String weekDate) {
        this.mIWeekView.showGraphicInfoAccordingWithMetricSelected(graphicValues);
        this.mIWeekView.updateWeekDateTextView(weekDate);
    }

    public void checkIfAreWorkoutsAvailableForPreviousWeeks() {
        if (this.mWeekInteractor.areWorkoutsAvailableForPreviousWeeks()) {
            this.mIWeekView.enableButtonWeekBefore();
        } else {
            this.mIWeekView.disableButtonWeekBefore();
        }
    }

    public void checkIfAreWorkoutsAvailableForNextWeeks() {
        if (this.mWeekInteractor.areWorkoutsAvailableForNextWeeks()) {
            this.mIWeekView.enableButtonWeekAfter();
        } else {
            this.mIWeekView.disableButtonWeekAfter();
        }
    }

    public void onCurrentWeekDateUpdated(DateTime currentWeekDate) {
        this.mCurrentWeekDate = currentWeekDate;
    }

    public void setCurrentWeekDate(DateTime currentWeekDate) {
        this.mCurrentWeekDate = currentWeekDate;
        this.mWeekInteractor.setCurrentWeekDate(this.mCurrentWeekDate);
    }

    public void setIWeekView(WeekViewContract weekview) {
        this.mIWeekView = weekview;
    }

    public DateTime getCurrentWeekDate() {
        return this.mCurrentWeekDate;
    }

    public User getCurrentUser() {
        return this.mCurrentUser;
    }

    public void setUnit(int unit) {
        this.mUnit = unit;
        this.mWeekInteractor.setUnit(this.mUnit);
    }
}
