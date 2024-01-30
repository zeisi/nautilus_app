package com.nautilus.omni.appmodules.journal.presenter.week;

import android.content.Context;
import android.os.AsyncTask;
import com.nautilus.omni.R;
import com.nautilus.omni.appmodules.journal.presenter.JournalMetrics;
import com.nautilus.omni.appmodules.journal.presenter.JournalMetricsHelper;
import com.nautilus.omni.appmodules.journal.presenter.week.WeekInteractorContract;
import com.nautilus.omni.dataaccess.WorkoutDaoHelper;
import com.nautilus.omni.model.dto.User;
import com.nautilus.omni.model.dto.Workout;
import com.nautilus.omni.util.DateUtil;
import com.nautilus.omni.util.Util;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import org.joda.time.DateTime;
import org.joda.time.ReadableInstant;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class WeekInteractor implements WeekInteractorContract {
    public static final int MOST_RECENT_WEEK = 1;
    public static final int NEXT_WEEK = 3;
    public static final int PREVIOUS_WEEK = 2;
    private Context mContext;
    private JournalMetrics mCurrentMetricType;
    private User mCurrentUser;
    private DateTime mCurrentWeekDate;
    private DateTimeFormatter mDateTimeFormatter;
    /* access modifiers changed from: private */
    public ArrayList<Double> mGraphicValues = new ArrayList<>();
    private List<List<Workout>> mListOfWorkouts;
    private WeekInteractorContract.OnCurrentWeekDateUpdatedListener mOnCurrentWeekDateUpdatedListener;
    /* access modifiers changed from: private */
    public WeekInteractorContract.OnLoadWorkoutsWeeklyInfoListener mOnLoadWorkoutsWeeklyInfoListener;
    private int mUnit;
    private WorkoutDaoHelper mWorkoutDaoHelper;

    @Inject
    public WeekInteractor(Context context, WorkoutDaoHelper workoutDaoHelper) {
        this.mContext = context;
        this.mWorkoutDaoHelper = workoutDaoHelper;
        this.mDateTimeFormatter = DateTimeFormat.forPattern(this.mContext.getString(R.string.journal_date_format_alternative));
    }

    public void getMostRecentWorkoutDate(WeekInteractorContract.OnCurrentWeekDateUpdatedListener onCurrentWeekDateUpdatedListener) {
        this.mOnCurrentWeekDateUpdatedListener = onCurrentWeekDateUpdatedListener;
        if (this.mCurrentUser != null) {
            this.mCurrentWeekDate = this.mWorkoutDaoHelper.getMostRecentWorkoutDate(this.mCurrentUser);
        }
        this.mOnCurrentWeekDateUpdatedListener.onCurrentWeekDateUpdated(this.mCurrentWeekDate);
    }

    public String[] loadMetricsSpinnerInfo() {
        if (Util.hasWatts(this.mContext)) {
            return this.mContext.getResources().getStringArray(R.array.journal_metrics_international);
        }
        return this.mContext.getResources().getStringArray(R.array.journal_metrics);
    }

    public ArrayList<String> loadWeekDaysList() {
        return new ArrayList<>(Arrays.asList(this.mContext.getResources().getStringArray(R.array.journal_week_days)));
    }

    public boolean areWorkoutsAvailableForPreviousWeeks() {
        return this.mWorkoutDaoHelper.areWorkoutsAvailableForOlderWeeks(this.mCurrentUser, this.mCurrentWeekDate);
    }

    public boolean areWorkoutsAvailableForNextWeeks() {
        return this.mWorkoutDaoHelper.areWorkoutsAvailableForNextWeeks(this.mCurrentUser, this.mCurrentWeekDate);
    }

    public void loadWorkoutsWeeklyInfoSortedByDay(WeekInteractorContract.OnLoadWorkoutsWeeklyInfoListener onLoadWorkoutsWeeklyInfoListener, int weekType, JournalMetrics metricType) {
        this.mOnLoadWorkoutsWeeklyInfoListener = onLoadWorkoutsWeeklyInfoListener;
        this.mCurrentMetricType = metricType;
        new GetWorkoutsWeeklyInfo().execute(new Integer[]{Integer.valueOf(weekType)});
    }

    public void loadWorkoutsInfoByMetricType(JournalMetrics metricType, WeekInteractorContract.OnCalculateWorkoutsInfoByMetricListener onCalculateWorkoutsInfoByMetricListener) {
        this.mCurrentMetricType = metricType;
        this.mGraphicValues = getWorkoutsValuesByDay(this.mCurrentMetricType.toInt(), this.mListOfWorkouts);
        onCalculateWorkoutsInfoByMetricListener.onWorkoutInfoByMetricTypeCalculated(this.mGraphicValues, formatWeekDate());
    }

    private class GetWorkoutsWeeklyInfo extends AsyncTask<Integer, String, Boolean> {
        private GetWorkoutsWeeklyInfo() {
        }

        /* access modifiers changed from: protected */
        public Boolean doInBackground(Integer... params) {
            return Boolean.valueOf(WeekInteractor.this.loadWorkoutsInfo(params));
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if (result.booleanValue()) {
                WeekInteractor.this.mOnLoadWorkoutsWeeklyInfoListener.onWorkoutsWeeklyInfoLoaded(WeekInteractor.this.mGraphicValues, WeekInteractor.this.formatWeekDate());
            }
        }
    }

    /* access modifiers changed from: private */
    public boolean loadWorkoutsInfo(Integer... params) {
        switch (params[0].intValue()) {
            case 1:
                return getMostRecentWorkoutsSortedByDay();
            case 2:
                if (this.mCurrentWeekDate != null) {
                    return getWorkoutsFromWeekBeforeSortedByDay();
                }
                return false;
            case 3:
                if (this.mCurrentWeekDate != null) {
                    return getWorkoutsFromWeekAfterSortedByDay();
                }
                return false;
            default:
                return false;
        }
    }

    /* access modifiers changed from: private */
    public String formatWeekDate() {
        return this.mContext.getString(R.string.journal_date_week, new Object[]{DateUtil.getFirstDayOftWeek(this.mCurrentWeekDate).toString(this.mDateTimeFormatter)});
    }

    private boolean getMostRecentWorkoutsSortedByDay() {
        if (this.mCurrentWeekDate == null) {
            return false;
        }
        this.mListOfWorkouts = new ArrayList(getWorkoutsFromWeekSortedByDay(this.mCurrentWeekDate));
        this.mGraphicValues = getWorkoutsValuesByDay(this.mCurrentMetricType.toInt(), this.mListOfWorkouts);
        return true;
    }

    private boolean getWorkoutsFromWeekBeforeSortedByDay() {
        this.mListOfWorkouts = new ArrayList(getWorkoutsFromPreviousWeeks(this.mCurrentWeekDate));
        if (this.mListOfWorkouts.size() > 0) {
            return sortWorkoutInfoByDayAccordingWithMetric();
        }
        return false;
    }

    private boolean getWorkoutsFromWeekAfterSortedByDay() {
        this.mListOfWorkouts = new ArrayList(getWorkoutsFromNextWeeks(this.mCurrentWeekDate));
        if (this.mListOfWorkouts.size() > 0) {
            return sortWorkoutInfoByDayAccordingWithMetric();
        }
        return false;
    }

    private boolean sortWorkoutInfoByDayAccordingWithMetric() {
        this.mGraphicValues = getWorkoutsValuesByDay(this.mCurrentMetricType.toInt(), this.mListOfWorkouts);
        this.mCurrentWeekDate = assignCurrentDate();
        this.mOnCurrentWeekDateUpdatedListener.onCurrentWeekDateUpdated(this.mCurrentWeekDate);
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

    public List<List<Workout>> getWorkoutsFromPreviousWeeks(DateTime currentDate) {
        List<Workout> weekWorkoutsList = new ArrayList<>();
        List<List<Workout>> weekWorkoutsByDay = new ArrayList<>();
        DateTime firstRecordDate = this.mWorkoutDaoHelper.getOldestWorkoutDate(this.mCurrentUser);
        for (DateTime currentDate2 = currentDate.minusWeeks(1); noWorkoutsFoundFromPreviousWeek(firstRecordDate, currentDate2, weekWorkoutsList); currentDate2 = currentDate2.minusWeeks(1)) {
            weekWorkoutsList = this.mWorkoutDaoHelper.getWorkoutsFromSpecificWeek(currentDate2, true);
        }
        if (weekWorkoutsList.size() > 0) {
            return fillWorkoutsByDayList(weekWorkoutsList);
        }
        return weekWorkoutsByDay;
    }

    private boolean noWorkoutsFoundFromPreviousWeek(DateTime firstRecordDate, DateTime currentDate, List<Workout> weekWorkoutsList) {
        return (firstRecordDate.isBefore((ReadableInstant) DateUtil.getFirstDayOftWeek(currentDate)) || DateUtil.isDateBetween(DateUtil.getFirstDayOftWeek(currentDate), firstRecordDate, DateUtil.getLastDayOfWeek(currentDate))) && weekWorkoutsList.size() == 0;
    }

    public List<List<Workout>> getWorkoutsFromNextWeeks(DateTime currentDate) {
        List<Workout> weekWorkoutsList = new ArrayList<>();
        List<List<Workout>> weekByDayWorkoutsList = new ArrayList<>();
        DateTime mostRecentWorkoutDate = this.mWorkoutDaoHelper.getMostRecentWorkoutDate(this.mCurrentUser);
        for (DateTime currentDate2 = currentDate.plusWeeks(1); noWorkoutsFoundForNextWeek(mostRecentWorkoutDate, currentDate2, weekWorkoutsList); currentDate2 = currentDate2.plusWeeks(1)) {
            weekWorkoutsList = this.mWorkoutDaoHelper.getWorkoutsFromSpecificWeek(currentDate2, true);
        }
        if (weekWorkoutsList.size() > 0) {
            return fillWorkoutsByDayList(weekWorkoutsList);
        }
        return weekByDayWorkoutsList;
    }

    private boolean noWorkoutsFoundForNextWeek(DateTime mostRecentWorkoutDate, DateTime date, List<Workout> weekWorkoutsList) {
        return (mostRecentWorkoutDate.isAfter((ReadableInstant) DateUtil.getFirstDayOftWeek(date)) || DateUtil.isDateBetween(DateUtil.getFirstDayOftWeek(date), mostRecentWorkoutDate, DateUtil.getLastDayOfWeek(date))) && weekWorkoutsList.size() == 0;
    }

    public ArrayList<Double> getWorkoutsValuesByDay(int dataType, List<List<Workout>> weekWorkoutsList) {
        ArrayList<Double> dataArray = new ArrayList<>();
        if (weekWorkoutsList != null && weekWorkoutsList.size() > 0) {
            for (List<Workout> currentDayWorkoutList : weekWorkoutsList) {
                calculateWorkoutsMetricValue(dataType, dataArray, currentDayWorkoutList);
            }
        }
        return dataArray;
    }

    private void calculateWorkoutsMetricValue(int dataType, ArrayList<Double> dataArray, List<Workout> currentDayWorkoutList) {
        if (dataType == JournalMetrics.ELAPSED_TIME.toInt()) {
            dataArray.add(Double.valueOf(JournalMetricsHelper.getWorkoutsTotalElapsedTime(currentDayWorkoutList)));
        } else if (dataType == JournalMetrics.TOTAL_CALORIES.toInt()) {
            dataArray.add(Double.valueOf(JournalMetricsHelper.getWorkoutsTotalCalories(currentDayWorkoutList)));
        } else if (dataType == JournalMetrics.SPEED.toInt()) {
            calculateWorkoutsTotalAvgSpeed(dataArray, currentDayWorkoutList);
        } else if (dataType == JournalMetrics.AVG_HEART_RATE.toInt()) {
            dataArray.add(Double.valueOf(JournalMetricsHelper.getWorkoutsTotalAvgHeartRate(currentDayWorkoutList)));
        } else if (dataType == JournalMetrics.POWER.toInt()) {
            if (Util.hasWatts(this.mContext)) {
                dataArray.add(Double.valueOf(JournalMetricsHelper.getWorkoutsTotalAvgPower(currentDayWorkoutList)));
            } else {
                calculateWorkoutsTotalDistance(dataArray, currentDayWorkoutList);
            }
        } else if (dataType == JournalMetrics.DISTANCE.toInt()) {
            calculateWorkoutsTotalDistance(dataArray, currentDayWorkoutList);
        }
    }

    private void calculateWorkoutsTotalAvgSpeed(ArrayList<Double> dataArray, List<Workout> currentDayWorkoutList) {
        if (this.mUnit == 0) {
            dataArray.add(Double.valueOf(JournalMetricsHelper.getWorkoutsTotalAvgSpeedInMPH(currentDayWorkoutList)));
        } else {
            dataArray.add(Double.valueOf(JournalMetricsHelper.getWorkoutsTotalAvgSpeedInKPH(currentDayWorkoutList)));
        }
    }

    private void calculateWorkoutsTotalAvgPower(ArrayList<Double> dataArray, List<Workout> currentDayWorkoutList) {
        dataArray.add(Double.valueOf(JournalMetricsHelper.getWorkoutsTotalAvgPower(currentDayWorkoutList)));
    }

    private void calculateWorkoutsTotalDistance(ArrayList<Double> dataArray, List<Workout> currentDayWorkoutList) {
        if (this.mUnit == 0) {
            dataArray.add(Double.valueOf(JournalMetricsHelper.getWorkoutsTotalDistanceInMiles(currentDayWorkoutList)));
        } else {
            dataArray.add(Double.valueOf(JournalMetricsHelper.getWorkoutsTotalDistanceInKilometers(currentDayWorkoutList)));
        }
    }

    public List<List<Workout>> getWorkoutsFromWeekSortedByDay(DateTime date) {
        List<List<Workout>> workoutsByDayList = new ArrayList<>();
        List<Workout> weekWorkoutsList = this.mWorkoutDaoHelper.getWorkoutsFromSpecificWeek(date, true);
        if (weekWorkoutsList.size() > 0) {
            return fillWorkoutsByDayList(weekWorkoutsList);
        }
        return workoutsByDayList;
    }

    private List<List<Workout>> fillWorkoutsByDayList(List<Workout> workoutList) {
        List<List<Workout>> weekWorkoutsListByDay = new ArrayList<>();
        List<Workout> sundayWorkouts = new ArrayList<>();
        List<Workout> mondayWorkouts = new ArrayList<>();
        List<Workout> tuesdayWorkouts = new ArrayList<>();
        List<Workout> wednesdayWorkouts = new ArrayList<>();
        List<Workout> thursdayWorkouts = new ArrayList<>();
        List<Workout> fridayWorkouts = new ArrayList<>();
        List<Workout> saturdayWorkouts = new ArrayList<>();
        for (Workout workout : workoutList) {
            if (workout.getmWorkoutDate().getDayOfWeek() == 7) {
                sundayWorkouts.add(workout);
            }
            if (workout.getmWorkoutDate().getDayOfWeek() == 1) {
                mondayWorkouts.add(workout);
            }
            if (workout.getmWorkoutDate().getDayOfWeek() == 2) {
                tuesdayWorkouts.add(workout);
            }
            if (workout.getmWorkoutDate().getDayOfWeek() == 3) {
                wednesdayWorkouts.add(workout);
            }
            if (workout.getmWorkoutDate().getDayOfWeek() == 4) {
                thursdayWorkouts.add(workout);
            }
            if (workout.getmWorkoutDate().getDayOfWeek() == 5) {
                fridayWorkouts.add(workout);
            }
            if (workout.getmWorkoutDate().getDayOfWeek() == 6) {
                saturdayWorkouts.add(workout);
            }
        }
        weekWorkoutsListByDay.add(sundayWorkouts);
        weekWorkoutsListByDay.add(mondayWorkouts);
        weekWorkoutsListByDay.add(tuesdayWorkouts);
        weekWorkoutsListByDay.add(wednesdayWorkouts);
        weekWorkoutsListByDay.add(thursdayWorkouts);
        weekWorkoutsListByDay.add(fridayWorkouts);
        weekWorkoutsListByDay.add(saturdayWorkouts);
        return weekWorkoutsListByDay;
    }

    public void setCurrentWeekDate(DateTime currentWeekDate) {
        this.mCurrentWeekDate = currentWeekDate;
    }

    public void setCurrentUser(User currentUser) {
        this.mCurrentUser = currentUser;
    }

    public void setUnit(int unit) {
        this.mUnit = unit;
    }
}
