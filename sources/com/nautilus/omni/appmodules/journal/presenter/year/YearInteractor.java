package com.nautilus.omni.appmodules.journal.presenter.year;

import android.content.Context;
import android.os.AsyncTask;
import com.nautilus.omni.R;
import com.nautilus.omni.appmodules.journal.presenter.JournalMetrics;
import com.nautilus.omni.appmodules.journal.presenter.JournalMetricsHelper;
import com.nautilus.omni.appmodules.journal.presenter.year.YearInteractorContract;
import com.nautilus.omni.dataaccess.WorkoutDaoHelper;
import com.nautilus.omni.model.dto.User;
import com.nautilus.omni.model.dto.Workout;
import com.nautilus.omni.util.Util;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import org.joda.time.DateTime;

public class YearInteractor implements YearInteractorContract {
    public static final int MOST_RECENT_YEAR = 1;
    public static final int NEXT_YEAR = 3;
    public static final int PREVIOUS_YEAR = 2;
    private Context mContext;
    private JournalMetrics mCurrentMetricType;
    private User mCurrentUser;
    /* access modifiers changed from: private */
    public DateTime mCurrentYearDate;
    /* access modifiers changed from: private */
    public ArrayList<Double> mGraphicValues = new ArrayList<>();
    private List<List<Workout>> mListOfWorkouts;
    private int mMostRecentWorkoutYear;
    private int mOldestWorkoutYear;
    private YearInteractorContract.OnCurrentYearDateUpdatedListener mOnCurrentYearDateUpdatedListener;
    /* access modifiers changed from: private */
    public YearInteractorContract.OnLoadWorkoutsAnnualInfoListener mOnLoadWorkoutsAnnualInfoListener;
    private int mUnit;
    private WorkoutDaoHelper mWorkoutDaoHelper;

    @Inject
    public YearInteractor(Context context, WorkoutDaoHelper workoutDaoHelper) {
        this.mContext = context;
        this.mWorkoutDaoHelper = workoutDaoHelper;
    }

    public void initData() {
        loadMostRecentWorkoutYear();
        loadOldestWorkoutYear();
    }

    private void loadMostRecentWorkoutYear() {
        if (this.mCurrentUser != null) {
            DateTime mostRecentWorkoutDate = this.mWorkoutDaoHelper.getMostRecentWorkoutDate(this.mCurrentUser);
            this.mMostRecentWorkoutYear = mostRecentWorkoutDate != null ? mostRecentWorkoutDate.getYear() : new DateTime().getYear();
        }
    }

    private void loadOldestWorkoutYear() {
        if (this.mCurrentUser != null) {
            DateTime oldestWorkoutDate = this.mWorkoutDaoHelper.getOldestWorkoutDate(this.mCurrentUser);
            this.mOldestWorkoutYear = oldestWorkoutDate != null ? oldestWorkoutDate.getYear() : new DateTime().getYear();
        }
    }

    public void getMostRecentWorkoutDate(YearInteractorContract.OnCurrentYearDateUpdatedListener onCurrentYearDateUpdatedListener) {
        this.mOnCurrentYearDateUpdatedListener = onCurrentYearDateUpdatedListener;
        if (this.mCurrentUser != null) {
            this.mCurrentYearDate = this.mWorkoutDaoHelper.getMostRecentWorkoutDate(this.mCurrentUser);
        }
        this.mOnCurrentYearDateUpdatedListener.onCurrentYearDateUpdated(this.mCurrentYearDate);
    }

    public String[] loadMetricsSpinnerInfo() {
        if (Util.hasWatts(this.mContext)) {
            return this.mContext.getResources().getStringArray(R.array.journal_metrics_international);
        }
        return this.mContext.getResources().getStringArray(R.array.journal_metrics);
    }

    public ArrayList<String> loadYearMonthsList() {
        return new ArrayList<>(Arrays.asList(this.mContext.getResources().getStringArray(R.array.journal_year_months)));
    }

    public boolean areWorkoutsAvailableForPreviousYears() {
        return this.mWorkoutDaoHelper.areWorkoutsAvailableForOlderYears(this.mCurrentUser, this.mCurrentYearDate);
    }

    public boolean areWorkoutsAvailableForNextYears() {
        return this.mWorkoutDaoHelper.areWorkoutsAvailableForNextYears(this.mCurrentUser, this.mCurrentYearDate);
    }

    public void loadWorkoutsAnnualInfoSortedByDay(YearInteractorContract.OnLoadWorkoutsAnnualInfoListener onLoadWorkoutsAnnualInfoListener, int yearType, JournalMetrics metricType) {
        this.mOnLoadWorkoutsAnnualInfoListener = onLoadWorkoutsAnnualInfoListener;
        this.mCurrentMetricType = metricType;
        new GetMostRecentWorkouts().execute(new Integer[]{Integer.valueOf(yearType)});
    }

    public void loadWorkoutsInfoByMetricType(JournalMetrics metricType, YearInteractorContract.OnCalculateWorkoutsInfoByMetricListener onCalculateWorkoutsInfoByMetricListener) {
        this.mCurrentMetricType = metricType;
        this.mGraphicValues = getYearArrayValuesByMonth(this.mCurrentMetricType.toInt(), this.mListOfWorkouts);
        onCalculateWorkoutsInfoByMetricListener.onWorkoutInfoByMetricTypeCalculated(this.mGraphicValues, String.valueOf(this.mCurrentYearDate.getYear()));
    }

    private class GetMostRecentWorkouts extends AsyncTask<Integer, String, Boolean> {
        private GetMostRecentWorkouts() {
        }

        /* access modifiers changed from: protected */
        public Boolean doInBackground(Integer... params) {
            return Boolean.valueOf(YearInteractor.this.loadWorkoutsInfo(params));
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if (result.booleanValue()) {
                YearInteractor.this.mOnLoadWorkoutsAnnualInfoListener.onWorkoutsAnnualInfoLoaded(YearInteractor.this.mGraphicValues, String.valueOf(YearInteractor.this.mCurrentYearDate.getYear()));
            }
        }
    }

    /* access modifiers changed from: private */
    public boolean loadWorkoutsInfo(Integer... params) {
        switch (params[0].intValue()) {
            case 1:
                return getMostRecentWorkoutsSortedByMonth();
            case 2:
                if (this.mCurrentYearDate != null) {
                    return getWorkoutsFromYearBeforeSortedByMonth();
                }
                return false;
            case 3:
                if (this.mCurrentYearDate != null) {
                    return getWorkoutsFromNextYearSortedByMonth();
                }
                return false;
            default:
                return false;
        }
    }

    private boolean getMostRecentWorkoutsSortedByMonth() {
        if (this.mCurrentYearDate == null) {
            return false;
        }
        this.mListOfWorkouts = new ArrayList(getWorkoutsFromYearSortedByMonth(this.mCurrentYearDate));
        this.mGraphicValues = getYearArrayValuesByMonth(this.mCurrentMetricType.toInt(), this.mListOfWorkouts);
        return true;
    }

    private boolean getWorkoutsFromYearBeforeSortedByMonth() {
        this.mListOfWorkouts = new ArrayList(getWorkoutsFromPreviousYear(this.mCurrentYearDate));
        if (this.mListOfWorkouts.size() > 0) {
            return sortWorkoutInfoByDayAccordingWithMetric();
        }
        return false;
    }

    private boolean getWorkoutsFromNextYearSortedByMonth() {
        this.mListOfWorkouts = new ArrayList(getWorkoutsFromNextYearSortedByMonth(this.mCurrentYearDate));
        if (this.mListOfWorkouts.size() > 0) {
            return sortWorkoutInfoByDayAccordingWithMetric();
        }
        return false;
    }

    private boolean sortWorkoutInfoByDayAccordingWithMetric() {
        this.mGraphicValues = getYearArrayValuesByMonth(this.mCurrentMetricType.toInt(), this.mListOfWorkouts);
        this.mCurrentYearDate = assignCurrentDate();
        this.mOnCurrentYearDateUpdatedListener.onCurrentYearDateUpdated(this.mCurrentYearDate);
        return true;
    }

    private DateTime assignCurrentDate() {
        for (List<Workout> workoutList : this.mListOfWorkouts) {
            if (workoutList.size() > 0) {
                return workoutList.get(0).getmWorkoutDate();
            }
        }
        return null;
    }

    public List<List<Workout>> getWorkoutsFromYearSortedByMonth(DateTime date) {
        List<List<Workout>> workoutsByMonthList = new ArrayList<>();
        List<Workout> currentYearWorkouts = this.mWorkoutDaoHelper.getWorkoutsFromSpecificYear(date, false);
        if (currentYearWorkouts.size() > 0) {
            return fillWorkoutsMonthList(currentYearWorkouts);
        }
        return workoutsByMonthList;
    }

    public List<List<Workout>> getWorkoutsFromPreviousYear(DateTime currentDate) {
        DateTime currentDate2 = currentDate.minusYears(1);
        List<List<Workout>> yearWorkoutsByMonth = new ArrayList<>();
        while (!this.mWorkoutDaoHelper.areWorkoutsAvailableForSelectedYear(currentDate2) && areOlderWorkoutsAvailable(currentDate2)) {
            currentDate2 = currentDate2.minusYears(1);
        }
        List<Workout> yearWorkoutsList = this.mWorkoutDaoHelper.getWorkoutsFromSpecificYear(currentDate2, false);
        if (yearWorkoutsList.size() > 0) {
            return fillWorkoutsMonthList(yearWorkoutsList);
        }
        return yearWorkoutsByMonth;
    }

    private boolean areOlderWorkoutsAvailable(DateTime currentDate) {
        return this.mOldestWorkoutYear != 0 && this.mOldestWorkoutYear <= currentDate.getYear();
    }

    public List<List<Workout>> getWorkoutsFromNextYearSortedByMonth(DateTime currentDate) {
        DateTime currentDate2 = currentDate.plusYears(1);
        List<List<Workout>> yearWorkoutsByMonth = new ArrayList<>();
        while (!this.mWorkoutDaoHelper.areWorkoutsAvailableForSelectedYear(currentDate2) && areMoreRecentWorkoutsAvailable(currentDate2)) {
            currentDate2 = currentDate2.plusYears(1);
        }
        List<Workout> yearWorkoutsList = this.mWorkoutDaoHelper.getWorkoutsFromSpecificYear(currentDate2, false);
        if (yearWorkoutsList.size() > 0) {
            return fillWorkoutsMonthList(yearWorkoutsList);
        }
        return yearWorkoutsByMonth;
    }

    private boolean areMoreRecentWorkoutsAvailable(DateTime currentDate) {
        return this.mMostRecentWorkoutYear >= currentDate.getYear();
    }

    public ArrayList<Double> getYearArrayValuesByMonth(int workoutType, List<List<Workout>> yearWorkoutsList) {
        ArrayList<Double> dataArray = new ArrayList<>();
        if (yearWorkoutsList != null && yearWorkoutsList.size() > 0) {
            for (List<Workout> currentMonthWorkoutList : yearWorkoutsList) {
                calculateWorkoutsMetricValue(workoutType, dataArray, currentMonthWorkoutList);
            }
        }
        return dataArray;
    }

    private void calculateWorkoutsMetricValue(int dataType, ArrayList<Double> dataArray, List<Workout> currentMonthWorkoutList) {
        if (dataType == JournalMetrics.ELAPSED_TIME.toInt()) {
            dataArray.add(Double.valueOf(JournalMetricsHelper.getWorkoutsTotalElapsedTime(currentMonthWorkoutList)));
        } else if (dataType == JournalMetrics.TOTAL_CALORIES.toInt()) {
            dataArray.add(Double.valueOf(JournalMetricsHelper.getWorkoutsTotalCalories(currentMonthWorkoutList)));
        } else if (dataType == JournalMetrics.SPEED.toInt()) {
            calculateWorkoutsTotalAvgSpeed(dataArray, currentMonthWorkoutList);
        } else if (dataType == JournalMetrics.AVG_HEART_RATE.toInt()) {
            dataArray.add(Double.valueOf(JournalMetricsHelper.getWorkoutsTotalAvgHeartRate(currentMonthWorkoutList)));
        } else if (dataType == JournalMetrics.POWER.toInt()) {
            if (Util.hasWatts(this.mContext)) {
                dataArray.add(Double.valueOf(JournalMetricsHelper.getWorkoutsTotalAvgPower(currentMonthWorkoutList)));
            } else {
                calculateWorkoutsTotalDistance(dataArray, currentMonthWorkoutList);
            }
        } else if (dataType == JournalMetrics.DISTANCE.toInt()) {
            calculateWorkoutsTotalDistance(dataArray, currentMonthWorkoutList);
        }
    }

    private void calculateWorkoutsTotalAvgSpeed(ArrayList<Double> dataArray, List<Workout> currentMonthWorkoutList) {
        if (this.mUnit == 0) {
            dataArray.add(Double.valueOf(JournalMetricsHelper.getWorkoutsTotalAvgSpeedInMPH(currentMonthWorkoutList)));
        } else {
            dataArray.add(Double.valueOf(JournalMetricsHelper.getWorkoutsTotalAvgSpeedInKPH(currentMonthWorkoutList)));
        }
    }

    private void calculateWorkoutsTotalDistance(ArrayList<Double> dataArray, List<Workout> currentMonthWorkoutList) {
        if (this.mUnit == 0) {
            dataArray.add(Double.valueOf(JournalMetricsHelper.getWorkoutsTotalDistanceInMiles(currentMonthWorkoutList)));
        } else {
            dataArray.add(Double.valueOf(JournalMetricsHelper.getWorkoutsTotalDistanceInKilometers(currentMonthWorkoutList)));
        }
    }

    private List<List<Workout>> fillWorkoutsMonthList(List<Workout> workoutList) {
        List<List<Workout>> weekWorkoutsListByDay = new ArrayList<>();
        List<Workout> januaryWorkouts = new ArrayList<>();
        List<Workout> februaryWorkouts = new ArrayList<>();
        List<Workout> marchWorkouts = new ArrayList<>();
        List<Workout> aprilWorkouts = new ArrayList<>();
        List<Workout> mayWorkouts = new ArrayList<>();
        List<Workout> juneWorkouts = new ArrayList<>();
        List<Workout> julyWorkouts = new ArrayList<>();
        List<Workout> augustWorkouts = new ArrayList<>();
        List<Workout> septemberWorkouts = new ArrayList<>();
        List<Workout> octoberWorkouts = new ArrayList<>();
        List<Workout> novemberWorkouts = new ArrayList<>();
        List<Workout> decemberWorkouts = new ArrayList<>();
        for (Workout workout : workoutList) {
            if (workout.getmWorkoutDate().getMonthOfYear() == 1) {
                januaryWorkouts.add(workout);
            }
            if (workout.getmWorkoutDate().getMonthOfYear() == 2) {
                februaryWorkouts.add(workout);
            }
            if (workout.getmWorkoutDate().getMonthOfYear() == 3) {
                marchWorkouts.add(workout);
            }
            if (workout.getmWorkoutDate().getMonthOfYear() == 4) {
                aprilWorkouts.add(workout);
            }
            if (workout.getmWorkoutDate().getMonthOfYear() == 5) {
                mayWorkouts.add(workout);
            }
            if (workout.getmWorkoutDate().getMonthOfYear() == 6) {
                juneWorkouts.add(workout);
            }
            if (workout.getmWorkoutDate().getMonthOfYear() == 7) {
                julyWorkouts.add(workout);
            }
            if (workout.getmWorkoutDate().getMonthOfYear() == 8) {
                augustWorkouts.add(workout);
            }
            if (workout.getmWorkoutDate().getMonthOfYear() == 9) {
                septemberWorkouts.add(workout);
            }
            if (workout.getmWorkoutDate().getMonthOfYear() == 10) {
                octoberWorkouts.add(workout);
            }
            if (workout.getmWorkoutDate().getMonthOfYear() == 11) {
                novemberWorkouts.add(workout);
            }
            if (workout.getmWorkoutDate().getMonthOfYear() == 12) {
                decemberWorkouts.add(workout);
            }
        }
        weekWorkoutsListByDay.add(januaryWorkouts);
        weekWorkoutsListByDay.add(februaryWorkouts);
        weekWorkoutsListByDay.add(marchWorkouts);
        weekWorkoutsListByDay.add(aprilWorkouts);
        weekWorkoutsListByDay.add(mayWorkouts);
        weekWorkoutsListByDay.add(juneWorkouts);
        weekWorkoutsListByDay.add(julyWorkouts);
        weekWorkoutsListByDay.add(augustWorkouts);
        weekWorkoutsListByDay.add(septemberWorkouts);
        weekWorkoutsListByDay.add(octoberWorkouts);
        weekWorkoutsListByDay.add(novemberWorkouts);
        weekWorkoutsListByDay.add(decemberWorkouts);
        return weekWorkoutsListByDay;
    }

    public void setCurrentYearDate(DateTime currentYearDate) {
        this.mCurrentYearDate = currentYearDate;
    }

    public void setCurrentUser(User currentUser) {
        this.mCurrentUser = currentUser;
    }

    public void setUnit(int unit) {
        this.mUnit = unit;
    }
}
