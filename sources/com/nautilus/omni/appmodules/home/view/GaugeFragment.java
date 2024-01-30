package com.nautilus.omni.appmodules.home.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.nautilus.omni.R;
import com.nautilus.omni.app.BaseFragment;
import com.nautilus.omni.appmodules.home.presenter.GaugePresenterContract;
import com.nautilus.omni.dependencyinjection.components.MainOmniComponent;
import com.nautilus.omni.util.Util;
import javax.inject.Inject;

public class GaugeFragment extends BaseFragment implements GaugeFragmentContract {
    private static final String KIND = "KIND";
    private static final int MAX_GAUGE_ROTATION = 276;
    @Inject
    GaugePresenterContract mIGaugePresenter;
    @BindView(2131755278)
    ImageView mImageViewGaugeKind;
    @BindView(2131755276)
    ImageView mImgViewNeedle;
    private int mKind = 0;
    @BindView(2131755279)
    TextView mTextViewCenterGauge;
    @BindView(2131755277)
    TextView mTextViewGaugeData;
    @BindView(2131755282)
    TextView mTxtEnableGoalsButNoData;
    @BindView(2131755281)
    TextView mTxtViewSubtitleGauge;

    public void setKind(int kind) {
        this.mKind = kind;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gauge, container, false);
        ButterKnife.bind((Object) this, view);
        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void checkSavedInstance(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            this.mKind = savedInstanceState.getInt(KIND);
            this.mIGaugePresenter.checkData();
        }
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((MainOmniComponent) getComponent(MainOmniComponent.class)).inject(this);
        this.mIGaugePresenter.setGauceFragment(this);
        checkSavedInstance(savedInstanceState);
        refreshData();
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KIND, this.mKind);
    }

    public void onResume() {
        super.onResume();
    }

    public void refreshData() {
        setIcon();
        setTitlesMessages();
        this.mIGaugePresenter.loadTextData(this.mKind);
    }

    private void setTitlesMessages() {
        switch (this.mKind) {
            case 0:
                this.mTextViewCenterGauge.setText(R.string.home_title_cal_burned);
                return;
            case 1:
                this.mTextViewCenterGauge.setText(R.string.home_title_avg_cal);
                return;
            case 2:
                this.mTextViewCenterGauge.setText(R.string.home_title_time_in);
                return;
            case 3:
                this.mTextViewCenterGauge.setText(R.string.home_title_avg_heart);
                return;
            case 4:
                this.mIGaugePresenter.loadDistanceBasedOnCurrentUnit();
                return;
            case 5:
                this.mIGaugePresenter.loadSpeedBasedOnCurrentUnit();
                return;
            case 6:
                this.mTextViewCenterGauge.setText(R.string.home_title_watts);
                return;
            case 7:
                this.mTextViewCenterGauge.setText(R.string.home_title_total);
                return;
            default:
                return;
        }
    }

    public void showSpeedLabelBasedOnCurrentUnit(int speed) {
        this.mTextViewCenterGauge.setText(speed);
    }

    public void showDistanceLabelBasedOnCurrentUnit(int distance) {
        this.mTextViewCenterGauge.setText(distance);
    }

    public void setIcon() {
        ViewGroup.LayoutParams params = this.mImageViewGaugeKind.getLayoutParams();
        switch (this.mKind) {
            case 0:
            case 1:
                params.height = (int) getResources().getDimension(R.dimen.image_view_gauge_calories_burned_height);
                params.width = (int) getResources().getDimension(R.dimen.image_view_gauge_calories_burned_height);
                this.mImageViewGaugeKind.setLayoutParams(params);
                this.mImageViewGaugeKind.setImageResource(R.drawable.ic_calories_gauge);
                return;
            case 2:
                params.height = (int) getResources().getDimension(R.dimen.image_view_gauge_time_height);
                params.width = (int) getResources().getDimension(R.dimen.image_view_gauge_time_width);
                this.mImageViewGaugeKind.setLayoutParams(params);
                this.mImageViewGaugeKind.setImageResource(R.drawable.ic_time_gauge);
                return;
            case 3:
                params.height = (int) getResources().getDimension(R.dimen.image_view_gauge_avg_heart_rate_height);
                params.width = (int) getResources().getDimension(R.dimen.image_view_gauge_avg_heart_rate_width);
                this.mImageViewGaugeKind.setLayoutParams(params);
                this.mImageViewGaugeKind.setImageResource(R.drawable.ic_heartrate_gauge);
                return;
            case 4:
                params.height = (int) getResources().getDimension(R.dimen.image_view_gauge_distance_height);
                params.width = (int) getResources().getDimension(R.dimen.image_view_gauge_distancee_width);
                this.mImageViewGaugeKind.setLayoutParams(params);
                this.mImageViewGaugeKind.setImageResource(R.drawable.ic_distance_gauge);
                return;
            case 5:
                params.height = (int) getResources().getDimension(R.dimen.image_view_gauge_avg_speed_height);
                params.width = (int) getResources().getDimension(R.dimen.image_view_gauge_avg_speed_width);
                this.mImageViewGaugeKind.setLayoutParams(params);
                this.mImageViewGaugeKind.setImageResource(R.drawable.ic_speed_gauge);
                return;
            case 6:
                params.height = (int) getResources().getDimension(R.dimen.image_view_gauge_watts_height);
                params.width = (int) getResources().getDimension(R.dimen.image_view_gauge_watts_width);
                this.mImageViewGaugeKind.setLayoutParams(params);
                this.mImageViewGaugeKind.setImageResource(R.drawable.ic_watts_gauge);
                return;
            case 7:
                this.mImageViewGaugeKind.setImageResource(R.drawable.ic_frequency_big);
                return;
            default:
                return;
        }
    }

    public void showCaloriesComparedWithCurrentGoal(int rotation, String lastWorkoutCaloriesData, String goalValue, int realPercent) {
        this.mImgViewNeedle.setRotation((float) rotation);
        this.mTextViewGaugeData.setText(lastWorkoutCaloriesData);
        this.mTxtViewSubtitleGauge.setVisibility(0);
        this.mTxtViewSubtitleGauge.setText(getString(R.string.home_lbl_compared_to_calorie_goal, new Object[]{goalValue, Integer.valueOf(realPercent)}));
    }

    public void showTimeComparedWithCurrentGoal(int rotation, int lastWorkoutTimeValue, String goalTime, int realPercent) {
        this.mImgViewNeedle.setRotation((float) rotation);
        this.mTextViewGaugeData.setText(Util.convertSecondsToTimeFormatInMinutesAndSeconds(lastWorkoutTimeValue));
        this.mTxtViewSubtitleGauge.setVisibility(0);
        this.mTxtViewSubtitleGauge.setText(getString(R.string.home_lbl_compared_to_time_goal, new Object[]{goalTime, Integer.valueOf(realPercent)}));
    }

    public void showCaloriesComparedToPreviousWorkout(int rotation, String calories, int realPercent) {
        this.mImgViewNeedle.setRotation((float) rotation);
        this.mTextViewGaugeData.setText(calories);
        this.mTxtViewSubtitleGauge.setVisibility(0);
        this.mTxtViewSubtitleGauge.setText(getString(R.string.home_lbl_compared_to_previous, new Object[]{Integer.valueOf(realPercent)}));
    }

    public void showAvgCalorieBurnRateComparedToPreviousWorkout(int rotation, String burnRate, int realPercent) {
        this.mImgViewNeedle.setRotation((float) rotation);
        this.mTextViewGaugeData.setText(burnRate);
        this.mTxtViewSubtitleGauge.setVisibility(0);
        this.mTxtViewSubtitleGauge.setText(getString(R.string.home_lbl_compared_to_previous, new Object[]{Integer.valueOf(realPercent)}));
    }

    public void showSpeedComparedToPreviousWorkout(int rotation, int realPercent, String workoutSpeed) {
        this.mImgViewNeedle.setRotation((float) rotation);
        this.mTxtViewSubtitleGauge.setVisibility(0);
        this.mTxtViewSubtitleGauge.setText(getString(R.string.home_lbl_compared_to_previous, new Object[]{Integer.valueOf(realPercent)}));
        this.mTextViewGaugeData.setText(workoutSpeed);
    }

    public void showWattsComparedToPreviousWorkout(int rotation, String watts, int realPercent) {
        this.mImgViewNeedle.setRotation((float) rotation);
        this.mTextViewGaugeData.setText(watts);
        this.mTxtViewSubtitleGauge.setVisibility(0);
        this.mTxtViewSubtitleGauge.setText(getString(R.string.home_lbl_compared_to_previous, new Object[]{Integer.valueOf(realPercent)}));
    }

    public void hideImageViewNeedle() {
        this.mImgViewNeedle.setVisibility(4);
    }

    public void showImageViewNeedle() {
        this.mImgViewNeedle.setVisibility(0);
    }

    public void showDistanceComparedToPreviousWorkout(int rotation, int realPercent, String workoutDistance) {
        this.mImgViewNeedle.setRotation((float) rotation);
        this.mTxtViewSubtitleGauge.setVisibility(0);
        this.mTxtViewSubtitleGauge.setText(getString(R.string.home_lbl_compared_to_previous, new Object[]{Integer.valueOf(realPercent)}));
        this.mTextViewGaugeData.setText(workoutDistance);
    }

    public void compareTimeToPreviousWorkout(int rotation, int lastTime, int realPercent) {
        this.mImgViewNeedle.setRotation((float) rotation);
        this.mTextViewGaugeData.setText(Util.convertSecondsToTimeFormatInMinutesAndSeconds(lastTime));
        this.mTxtViewSubtitleGauge.setVisibility(0);
        this.mTxtViewSubtitleGauge.setText(getString(R.string.home_lbl_compared_to_previous, new Object[]{Integer.valueOf(realPercent)}));
    }

    public void showCaloriesWithoutComparison(int calories) {
        this.mImgViewNeedle.setRotation(276.0f);
        this.mTextViewGaugeData.setText(String.valueOf(calories));
        this.mTxtViewSubtitleGauge.setVisibility(8);
    }

    public void showAvgCaloriesBurnedWithoutComparison(String value) {
        this.mImgViewNeedle.setRotation(276.0f);
        this.mTextViewGaugeData.setText(value);
        this.mTxtViewSubtitleGauge.setVisibility(8);
    }

    public void showSpeedWithoutComparison(String speed) {
        this.mTxtViewSubtitleGauge.setVisibility(8);
        this.mImgViewNeedle.setRotation(276.0f);
        this.mTextViewGaugeData.setText(speed);
    }

    public void showDistanceWithoutComparison(String distance) {
        this.mTxtViewSubtitleGauge.setVisibility(8);
        this.mImgViewNeedle.setRotation(276.0f);
        this.mTextViewGaugeData.setText(distance);
    }

    public void showWattsWithoutComparison(String watts) {
        this.mTxtViewSubtitleGauge.setVisibility(8);
        this.mImgViewNeedle.setRotation(276.0f);
        this.mTextViewGaugeData.setText(watts);
    }

    public void showHeartRateWithoutComparison(String heartRate) {
        this.mImgViewNeedle.setRotation(0.0f);
        this.mImgViewNeedle.setVisibility(4);
        this.mTextViewGaugeData.setText(heartRate);
        this.mTxtViewSubtitleGauge.setVisibility(8);
    }

    public void showTimeWithoutComparison(int time) {
        this.mImgViewNeedle.setRotation(276.0f);
        this.mTxtViewSubtitleGauge.setVisibility(4);
        this.mTextViewGaugeData.setText(Util.convertSecondsToTimeFormatInMinutesAndSeconds(time));
        this.mTxtViewSubtitleGauge.setVisibility(8);
    }

    public void showEmptyData() {
        this.mTxtViewSubtitleGauge.setText(R.string.home_no_data_available);
        this.mImgViewNeedle.setVisibility(0);
        switch (this.mKind) {
            case 0:
                this.mTextViewGaugeData.setText("0");
                this.mImgViewNeedle.setRotation(0.0f);
                return;
            case 1:
                this.mTextViewGaugeData.setText("0");
                this.mImgViewNeedle.setRotation(0.0f);
                return;
            case 2:
                this.mTextViewGaugeData.setText("00:00");
                this.mImgViewNeedle.setRotation(0.0f);
                return;
            case 3:
                this.mTextViewGaugeData.setText("0");
                this.mImgViewNeedle.setVisibility(8);
                this.mImgViewNeedle.setRotation(0.0f);
                return;
            case 4:
                this.mTextViewGaugeData.setText("0");
                this.mImgViewNeedle.setRotation(0.0f);
                return;
            case 5:
                this.mTextViewGaugeData.setText("0");
                this.mImgViewNeedle.setRotation(0.0f);
                return;
            case 6:
                this.mTextViewGaugeData.setText("0");
                this.mImgViewNeedle.setRotation(0.0f);
                return;
            default:
                return;
        }
    }

    public void loadTxtEnableGoalButNoData(int value, int totalSeconds) {
        switch (this.mKind) {
            case 0:
                this.mTxtEnableGoalsButNoData.setText(getResources().getString(R.string.home_no_data_calories_goals_format, new Object[]{Integer.valueOf(value)}));
                this.mTxtEnableGoalsButNoData.setVisibility(0);
                return;
            case 2:
                if (value < 60) {
                    this.mTextViewCenterGauge.setText(R.string.home_title_time_in);
                    this.mTxtEnableGoalsButNoData.setText(getResources().getString(R.string.home_title_goal, new Object[]{Util.convertSecondsToTimeFormatInMinutesAndSeconds(totalSeconds)}));
                } else {
                    this.mTextViewCenterGauge.setText(R.string.home_title_time_in_hour);
                    this.mTxtEnableGoalsButNoData.setText(getResources().getString(R.string.home_title_goal, new Object[]{Util.convertSecondsToDuration((long) totalSeconds, false)}));
                }
                this.mTxtEnableGoalsButNoData.setVisibility(0);
                return;
            default:
                this.mTxtEnableGoalsButNoData.setVisibility(8);
                return;
        }
    }
}
