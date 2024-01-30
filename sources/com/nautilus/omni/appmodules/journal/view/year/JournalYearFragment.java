package com.nautilus.omni.appmodules.journal.view.year;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.nautilus.omni.R;
import com.nautilus.omni.app.BaseFragment;
import com.nautilus.omni.app.Preferences;
import com.nautilus.omni.appmodules.journal.presenter.JournalMetrics;
import com.nautilus.omni.appmodules.journal.presenter.year.YearPresenter;
import com.nautilus.omni.dependencyinjection.components.MainOmniComponent;
import com.nautilus.omni.util.BroadcastKeys;
import com.nautilus.omni.util.ResourceHelper;
import com.nautilus.omni.util.Util;
import com.verticalbargraphiclibrary.VerticalBarGraphicView;
import java.util.ArrayList;
import java.util.Collections;
import javax.inject.Inject;
import org.joda.time.DateTime;

public class JournalYearFragment extends BaseFragment implements YearViewContract, AdapterView.OnItemSelectedListener {
    private static final String CURRENT_METRIC_TYPE = "CURRENT_METRIC_TYPE";
    private static final String CURRENT_YEAR_DATE = "CURRENT_YEAR_DATE";
    @BindView(2131755346)
    TextView journalTextYear;
    @BindView(2131755343)
    VerticalBarGraphicView mBarViewXYear;
    /* access modifiers changed from: private */
    public JournalMetrics mCurrentMetricType;
    private ArrayList<Double> mGraphicValues = new ArrayList<>();
    private int mSpinnerCounterChecker;
    @BindView(2131755342)
    Spinner mSpinnerYearMetrics;
    @BindView(2131755341)
    TextView mTxtViewUnitLabel;
    private int mUnit;
    @BindView(2131755347)
    ImageButton mYearAfterButton;
    @BindView(2131755345)
    ImageButton mYearBeforeButton;
    @Inject
    YearPresenter mYearPresenter;
    public BroadcastReceiver syncDataFinishedReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            JournalYearFragment.this.mYearPresenter.loadWorkoutsAfterSyncFinished(1, JournalYearFragment.this.mCurrentMetricType);
        }
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mSpinnerCounterChecker = 0;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_journal_year, container, false);
        ButterKnife.bind((Object) this, view);
        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((MainOmniComponent) getComponent(MainOmniComponent.class)).inject(this);
        this.mYearPresenter.setIYearView(this);
        loadData(savedInstanceState);
    }

    private void loadData(Bundle savedInstanceState) {
        if (savedInstanceState == null || this.mYearPresenter == null) {
            loadWorkoutsData();
        } else {
            restoreFragmentPreviousState(savedInstanceState);
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (this.mYearPresenter != null) {
            outState.putSerializable(CURRENT_YEAR_DATE, this.mYearPresenter.getCurrentWeekDate());
        }
        if (this.mCurrentMetricType != null) {
            outState.putString(CURRENT_METRIC_TYPE, this.mCurrentMetricType.name());
        }
    }

    private void restoreFragmentPreviousState(Bundle savedInstanceState) {
        this.mCurrentMetricType = JournalMetrics.valueOf(savedInstanceState.getString(CURRENT_METRIC_TYPE));
        this.mYearPresenter.setCurrentYearDate((DateTime) savedInstanceState.getSerializable(CURRENT_YEAR_DATE));
        loadFragmentData();
        this.mYearPresenter.loadWorkoutsAnnualInfo(1, this.mCurrentMetricType);
    }

    private void loadWorkoutsData() {
        loadFragmentData();
        this.mYearPresenter.loadWorkoutsAnnualInfo(1, this.mCurrentMetricType);
    }

    private void loadFragmentData() {
        initMetricsSpinner();
        initUnitLabelView();
        initBarViewXYearComponent();
    }

    public void onResume() {
        super.onResume();
        registerBroadcastReceivers();
        this.mUnit = this.mPreferences.getInt(Preferences.UNITS_SETTINGS, 0);
        this.mYearPresenter.setUnit(this.mUnit);
    }

    private void registerBroadcastReceivers() {
        LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).registerReceiver(this.syncDataFinishedReceiver, new IntentFilter(BroadcastKeys.SYNC_FINISHED));
    }

    private void initMetricsSpinner() {
        this.mCurrentMetricType = JournalMetrics.ELAPSED_TIME;
        this.mSpinnerYearMetrics.setOnItemSelectedListener(this);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(), R.layout.custom_journal_spinner_item, this.mYearPresenter.loadMetricsSpinnerInfo());
        dataAdapter.setDropDownViewResource(R.layout.custom_journal_drop_down_item);
        this.mSpinnerYearMetrics.setAdapter(dataAdapter);
    }

    private void initUnitLabelView() {
        this.mTxtViewUnitLabel.setVisibility(0);
        this.mTxtViewUnitLabel.setText(getString(R.string.unit_label_hrs_min));
    }

    private void initBarViewXYearComponent() {
        ArrayList<String> yearMonthsList = this.mYearPresenter.loadYearMonthsList();
        this.mBarViewXYear.setGraphicBarsColor(ContextCompat.getColor(getActivity(), R.color.journal_week_elapsed_time_bars_color));
        this.mBarViewXYear.setBottomTextList(yearMonthsList);
        this.mBarViewXYear.setDrawBottomValues(true);
        this.mBarViewXYear.setDrawLeftSideValues(true);
        this.mBarViewXYear.setGraphicLeftSideTextTypeface(ResourceHelper.getLatoLightTypeface(getActivity().getApplicationContext()));
        this.mBarViewXYear.setGraphicBarsBottomTextTypeface(ResourceHelper.getLatoLightTypeface(getActivity().getApplicationContext()));
        this.mBarViewXYear.setGraphicBarWidth(getYearGraphicBarWidth());
    }

    private float getYearGraphicBarWidth() {
        TypedValue outValue = new TypedValue();
        getResources().getValue(R.dimen.year_graphic_bar_width, outValue, true);
        return outValue.getFloat();
    }

    @OnClick({2131755345})
    public void getWorkoutInfoFromPreviousYear() {
        if (this.mYearPresenter.getCurrentUser() != null) {
            this.mYearPresenter.loadWorkoutsAnnualInfo(2, this.mCurrentMetricType);
        }
    }

    @OnClick({2131755347})
    public void getWorkoutInfoFromNextYear() {
        if (this.mYearPresenter.getCurrentUser() != null) {
            this.mYearPresenter.loadWorkoutsAnnualInfo(3, this.mCurrentMetricType);
        }
    }

    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        this.mSpinnerCounterChecker++;
        if (this.mSpinnerCounterChecker <= 1) {
            return;
        }
        if (position == JournalMetrics.ELAPSED_TIME.toInt()) {
            setElapsedTimeMetric();
        } else if (position == JournalMetrics.TOTAL_CALORIES.toInt()) {
            setCaloriesMetric();
        } else if (position == JournalMetrics.AVG_HEART_RATE.toInt()) {
            setAvgHeartRateMetric();
        } else if (position == JournalMetrics.DISTANCE.toInt()) {
            setDistanceMetric();
        } else if (position == JournalMetrics.POWER.toInt()) {
            if (Util.hasWatts(getActivity())) {
                setPowerMetric();
            } else {
                setSpeedMetric();
            }
        } else if (position == JournalMetrics.SPEED.toInt()) {
            setSpeedMetric();
        }
    }

    private void setElapsedTimeMetric() {
        this.mCurrentMetricType = JournalMetrics.ELAPSED_TIME;
        this.mTxtViewUnitLabel.setVisibility(0);
        this.mTxtViewUnitLabel.setText(getString(R.string.unit_label_hrs_min));
        this.mBarViewXYear.setGraphicBarsColor(ContextCompat.getColor(getActivity(), R.color.journal_week_elapsed_time_bars_color));
        this.mYearPresenter.loadWorkoutsInfoByMetricType(this.mCurrentMetricType);
    }

    private void setCaloriesMetric() {
        this.mCurrentMetricType = JournalMetrics.TOTAL_CALORIES;
        this.mTxtViewUnitLabel.setVisibility(8);
        this.mBarViewXYear.setGraphicBarsColor(ContextCompat.getColor(getActivity(), R.color.journal_week_total_calories_bars_color));
        this.mYearPresenter.loadWorkoutsInfoByMetricType(this.mCurrentMetricType);
    }

    private void setAvgHeartRateMetric() {
        this.mCurrentMetricType = JournalMetrics.AVG_HEART_RATE;
        this.mTxtViewUnitLabel.setVisibility(0);
        this.mTxtViewUnitLabel.setText(getString(R.string.unit_label_bpm));
        this.mBarViewXYear.setGraphicBarsColor(ContextCompat.getColor(getActivity(), R.color.journal_week_avg_heart_rate_bars_color));
        this.mYearPresenter.loadWorkoutsInfoByMetricType(this.mCurrentMetricType);
    }

    private void setDistanceMetric() {
        this.mCurrentMetricType = JournalMetrics.DISTANCE;
        this.mTxtViewUnitLabel.setVisibility(0);
        this.mBarViewXYear.setGraphicBarsColor(ContextCompat.getColor(getActivity(), R.color.journal_week_distance));
        setDistanceUnitLabel();
        this.mYearPresenter.loadWorkoutsInfoByMetricType(this.mCurrentMetricType);
    }

    private void setDistanceUnitLabel() {
        if (this.mUnit == 0) {
            this.mTxtViewUnitLabel.setText(getString(R.string.unit_label_miles));
        } else {
            this.mTxtViewUnitLabel.setText(getString(R.string.unit_label_kilometers));
        }
    }

    private void setPowerMetric() {
        this.mCurrentMetricType = JournalMetrics.POWER;
        this.mTxtViewUnitLabel.setVisibility(0);
        this.mBarViewXYear.setGraphicBarsColor(ContextCompat.getColor(getActivity(), R.color.journal_week_power));
        setPowerUnitLabel();
        this.mYearPresenter.loadWorkoutsInfoByMetricType(this.mCurrentMetricType);
    }

    private void setPowerUnitLabel() {
        this.mTxtViewUnitLabel.setText(getString(R.string.unit_label_watts));
    }

    private void setSpeedMetric() {
        this.mCurrentMetricType = JournalMetrics.SPEED;
        this.mTxtViewUnitLabel.setVisibility(0);
        this.mBarViewXYear.setGraphicBarsColor(ContextCompat.getColor(getActivity(), R.color.journal_week_speed));
        setSpeedUnitLabel();
        this.mYearPresenter.loadWorkoutsInfoByMetricType(this.mCurrentMetricType);
    }

    private void setSpeedUnitLabel() {
        if (this.mUnit == 0) {
            this.mTxtViewUnitLabel.setText(getString(R.string.unit_label_mph));
        } else {
            this.mTxtViewUnitLabel.setText(getString(R.string.unit_label_kph));
        }
    }

    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    public void showGraphicInfoAccordingWithMetricSelected(ArrayList<Double> graphicValues) {
        this.mGraphicValues = graphicValues;
        this.mYearPresenter.checkIfAreWorkoutsAvailableForNextYears();
        this.mYearPresenter.checkIfAreWorkoutsAvailableForPreviousYears();
        updateGraphicData();
    }

    public void updateYearTextView(String year) {
        this.journalTextYear.setText(year);
    }

    public void enableButtonYearBefore() {
        this.mYearBeforeButton.setEnabled(true);
        this.mYearBeforeButton.setImageResource(R.drawable.ic_button_left_active);
    }

    public void disableButtonYearBefore() {
        this.mYearBeforeButton.setEnabled(false);
        this.mYearBeforeButton.setImageResource(R.drawable.ic_button_left_disabled);
    }

    public void enableButtonYearAfter() {
        this.mYearAfterButton.setImageResource(R.drawable.ic_button_right_active);
        this.mYearAfterButton.setEnabled(true);
    }

    public void disableButtonYearAfter() {
        this.mYearAfterButton.setEnabled(false);
        this.mYearAfterButton.setImageResource(R.drawable.ic_button_right_disabled);
    }

    public void updateGraphicData() {
        this.mBarViewXYear.setIsTimeData(false);
        if (this.mCurrentMetricType == JournalMetrics.ELAPSED_TIME) {
            this.mBarViewXYear.setIsTimeData(true);
        }
        if (this.mGraphicValues.size() > 0) {
            this.mBarViewXYear.setDataList(this.mGraphicValues, ((Double) Collections.max(this.mGraphicValues)).doubleValue());
        }
    }

    public void onPause() {
        super.onPause();
        unregisterBroadcastReceivers();
    }

    private void unregisterBroadcastReceivers() {
        LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).unregisterReceiver(this.syncDataFinishedReceiver);
    }
}
