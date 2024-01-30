package com.nautilus.omni.appmodules.journal.presenter.day.workoutdetail;

import android.content.Context;
import com.nautilus.omni.R;
import com.nautilus.omni.app.BasePresenter;
import com.nautilus.omni.appmodules.journal.view.day.workoutdetail.WorkoutDetailViewContract;
import com.nautilus.omni.model.dto.Workout;
import com.nautilus.omni.util.Util;
import org.joda.time.format.DateTimeFormat;

public class WorkoutDetailPresenter extends BasePresenter implements WorkoutDetailPresenterContract {
    private static final int TWO_DIGITS = 99;
    private Context mContext;
    private WorkoutDetailViewContract mIWorkoutDetailView;
    private Workout mWorkout;

    public WorkoutDetailPresenter(Context context, WorkoutDetailViewContract workoutDetailView) {
        super(context);
        this.mContext = context;
        this.mIWorkoutDetailView = workoutDetailView;
    }

    public void prepareWorkoutInfo(Workout workout) {
        this.mWorkout = workout;
        prepareWorkoutDate();
        prepareWorkoutElapsedTime();
        prepareWorkoutCalories();
        prepareWorkoutAvgCalories();
        prepareWorkoutHeartRate();
        prepareWorkoutAvgPower();
        prepareWorkoutSpeed();
        prepareWorkoutDistance();
    }

    private void prepareWorkoutDate() {
        this.mIWorkoutDetailView.showWorkoutDate(this.mWorkout.getmWorkoutDate().toString(DateTimeFormat.forPattern(this.mContext.getString(R.string.workout_detail_date))));
    }

    private void prepareWorkoutElapsedTime() {
        this.mIWorkoutDetailView.showWorkoutElapsedTime(Util.convertSecondsToDuration((long) this.mWorkout.getmElapsedTime(), true));
    }

    private void prepareWorkoutCalories() {
        this.mIWorkoutDetailView.showWorkoutTotalCalories(String.valueOf(this.mWorkout.getmCalories()));
    }

    private void prepareWorkoutAvgCalories() {
        if (this.mWorkout.getmAvgCalorieBurnRate() > 99.0f) {
            showWorkoutAvgCaloriesWithOneDecimal();
        } else {
            showWorkoutAvgCaloriesWithTwoDecimals();
        }
    }

    private void showWorkoutAvgCaloriesWithOneDecimal() {
        this.mIWorkoutDetailView.showWorkoutAvgCaloriesPerMinute(Util.getAvgCalorieBurnRateAsStringOneDecimal(this.mWorkout));
    }

    private void showWorkoutAvgCaloriesWithTwoDecimals() {
        this.mIWorkoutDetailView.showWorkoutAvgCaloriesPerMinute(Util.getAvgCalorieBurnRateAsStringTwoDecimal(this.mWorkout));
    }

    private void prepareWorkoutHeartRate() {
        this.mIWorkoutDetailView.showWorkoutHeartRate(String.valueOf(this.mWorkout.getmAvgHeartRate()));
    }

    private void prepareWorkoutAvgPower() {
        this.mIWorkoutDetailView.showWorkoutAvgWatts(Util.getWorkoutPowerAsStringTwoDecimals(this.mWorkout));
    }

    private void prepareWorkoutSpeed() {
        if (this.mUnit == 0) {
            showWorkoutSpeedInMiles();
        } else {
            showWorkoutSpeedInKm();
        }
    }

    private void showWorkoutSpeedInMiles() {
        this.mIWorkoutDetailView.showWorkoutSpeed(Util.getWorkoutSpeedInMilesAsString(this.mWorkout), this.mContext.getString(R.string.workout_speed_mph));
    }

    private void showWorkoutSpeedInKm() {
        this.mIWorkoutDetailView.showWorkoutSpeed(Util.getWorkoutSpeedInKilometersAsString(this.mWorkout), this.mContext.getString(R.string.workout_speed_kph));
    }

    private void prepareWorkoutDistance() {
        if (this.mUnit == 0) {
            showWorkoutDistanceInMiles();
        } else {
            showWorkoutDistanceInKm();
        }
    }

    private void showWorkoutDistanceInMiles() {
        this.mIWorkoutDetailView.showWorkoutDistance(Util.getWorkoutDistanceInMilesAsString(this.mWorkout), this.mContext.getString(R.string.workout_miles));
    }

    private void showWorkoutDistanceInKm() {
        this.mIWorkoutDetailView.showWorkoutDistance(Util.getWorkoutDistanceInKilometersAsString(this.mWorkout), this.mContext.getString(R.string.workout_kilometers));
    }
}
