package com.nautilus.omni.appmodules.journal.presenter.year;

import android.content.Context;
import com.nautilus.omni.app.BasePresenter;
import com.nautilus.omni.app.Preferences;
import com.nautilus.omni.appmodules.journal.presenter.JournalMetrics;
import com.nautilus.omni.appmodules.journal.presenter.year.YearInteractorContract;
import com.nautilus.omni.appmodules.journal.view.year.YearViewContract;
import com.nautilus.omni.model.dto.User;
import java.util.ArrayList;
import javax.inject.Inject;
import org.joda.time.DateTime;

public class YearPresenter extends BasePresenter implements YearPresenterContract, YearInteractorContract.OnCurrentYearDateUpdatedListener, YearInteractorContract.OnCalculateWorkoutsInfoByMetricListener, YearInteractorContract.OnLoadWorkoutsAnnualInfoListener {
    private User mCurrentUser;
    private DateTime mCurrentYearDate;
    private YearViewContract mIYearView;
    private int mUserIndex;
    private YearInteractor mYearInteractor;

    @Inject
    public YearPresenter(Context context, YearInteractor yearInteractor) {
        super(context);
        getCurrentUserFromDatabase();
        this.mYearInteractor = yearInteractor;
    }

    private void getCurrentUserFromDatabase() {
        this.mUserIndex = this.mPreferences.getInt(Preferences.USER_INDEX, -1);
        this.mCurrentUser = this.mUserDaoHelper.getCurrentUser(this.mUserIndex);
    }

    public String[] loadMetricsSpinnerInfo() {
        return this.mYearInteractor.loadMetricsSpinnerInfo();
    }

    public ArrayList<String> loadYearMonthsList() {
        return this.mYearInteractor.loadYearMonthsList();
    }

    public void loadWorkoutsAnnualInfo(int yearType, JournalMetrics metricType) {
        if (yearType == 1) {
            initYearInteractorData();
        }
        this.mYearInteractor.loadWorkoutsAnnualInfoSortedByDay(this, yearType, metricType);
    }

    private void initYearInteractorData() {
        this.mYearInteractor.setCurrentUser(this.mCurrentUser);
        this.mYearInteractor.initData();
        this.mYearInteractor.getMostRecentWorkoutDate(this);
    }

    public void onWorkoutsAnnualInfoLoaded(ArrayList<Double> graphicValues, String year) {
        this.mIYearView.showGraphicInfoAccordingWithMetricSelected(graphicValues);
        this.mIYearView.updateYearTextView(year);
    }

    public void loadWorkoutsInfoByMetricType(JournalMetrics metricType) {
        if (this.mCurrentUser != null && this.mCurrentYearDate != null) {
            this.mYearInteractor.loadWorkoutsInfoByMetricType(metricType, this);
        }
    }

    public void loadWorkoutsAfterSyncFinished(int yearType, JournalMetrics metricType) {
        initYearInteractorData();
        this.mYearInteractor.loadWorkoutsAnnualInfoSortedByDay(this, yearType, metricType);
    }

    public void onWorkoutInfoByMetricTypeCalculated(ArrayList<Double> graphicValues, String year) {
        this.mIYearView.showGraphicInfoAccordingWithMetricSelected(graphicValues);
        this.mIYearView.updateYearTextView(year);
    }

    public void checkIfAreWorkoutsAvailableForPreviousYears() {
        if (this.mYearInteractor.areWorkoutsAvailableForPreviousYears()) {
            this.mIYearView.enableButtonYearBefore();
        } else {
            this.mIYearView.disableButtonYearBefore();
        }
    }

    public void checkIfAreWorkoutsAvailableForNextYears() {
        if (this.mYearInteractor.areWorkoutsAvailableForNextYears()) {
            this.mIYearView.enableButtonYearAfter();
        } else {
            this.mIYearView.disableButtonYearAfter();
        }
    }

    public void onCurrentYearDateUpdated(DateTime currentYearDate) {
        this.mCurrentYearDate = currentYearDate;
    }

    public void setCurrentYearDate(DateTime currentYearDate) {
        this.mCurrentYearDate = currentYearDate;
        this.mYearInteractor.setCurrentYearDate(this.mCurrentYearDate);
    }

    public void setIYearView(YearViewContract yearView) {
        this.mIYearView = yearView;
    }

    public DateTime getCurrentWeekDate() {
        return this.mCurrentYearDate;
    }

    public User getCurrentUser() {
        return this.mCurrentUser;
    }

    public void setUnit(int unit) {
        this.mUnit = unit;
        this.mYearInteractor.setUnit(this.mUnit);
    }
}
