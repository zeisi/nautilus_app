package com.nautilus.omni.appmodules.home.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.nautilus.omni.R;
import com.nautilus.omni.app.BaseFragment;
import com.nautilus.omni.appmodules.home.presenter.ThisWeekPresenterContract;
import com.nautilus.omni.customviews.graphics.HorizontalBarGraphicView;
import com.nautilus.omni.dependencyinjection.components.MainOmniComponent;
import java.util.ArrayList;
import javax.inject.Inject;

public class ThisWeekSectionFragment extends BaseFragment implements ThisWeekSectionFragmentContract {
    private static final String KIND = "KIND_THIS_WEEK";
    @Inject
    ThisWeekPresenterContract iThisWeekPresenter;
    @BindView(2131755328)
    HorizontalBarGraphicView mBarHorizontalGraphicView;
    @BindView(2131755319)
    ImageView mImgViewIconStat;
    private int mKind;
    @BindView(2131755320)
    LinearLayout mLayoutHomeMiddleNoGraphic;
    @BindView(2131755325)
    LinearLayout mLayoutMiddleGraphicSection;
    @BindView(2131755318)
    TextView mTextViewWeekTitle;
    @BindView(2131755327)
    TextView mTxtViewGoalGraphic;
    @BindView(2131755324)
    TextView mTxtViewLastWeekNumber;
    @BindView(2131755322)
    TextView mTxtViewThisWeekNumber;

    public void setKind(int kind) {
        this.mKind = kind;
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KIND, this.mKind);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (this.mKind == 8) {
            return inflater.inflate(R.layout.fragment_home_middle_label, container, false);
        }
        View view = inflater.inflate(R.layout.fragment_home_middle_section, container, false);
        ButterKnife.bind((Object) this, view);
        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainOmniComponent) getComponent(MainOmniComponent.class)).inject(this);
        this.iThisWeekPresenter.setiThisWeekSectionFragment(this);
        if (savedInstanceState != null) {
            this.mKind = savedInstanceState.getInt(KIND);
        }
        if (this.mKind != 8) {
            setIcon();
            this.iThisWeekPresenter.loadFragment(this.mKind);
        }
    }

    public void refreshData() {
        if (this.mKind != 8) {
            setIcon();
            this.iThisWeekPresenter.loadFragment(this.mKind);
        }
    }

    public void showDataComparedToCalorieGoal(int totalCaloriePerWorkoutGoal, int totalWeekCalories) {
        this.mTxtViewGoalGraphic.setText(getResources().getString(R.string.home_title_goal, new Object[]{Integer.valueOf(totalCaloriePerWorkoutGoal)}));
    }

    public void showDataComparedToWorkoutsPerWeekGoal(int currentWorkoutsPerWeekGoalValue, int totalWorkouts) {
        this.mTxtViewGoalGraphic.setText(getResources().getString(R.string.home_title_goal, new Object[]{Integer.valueOf(currentWorkoutsPerWeekGoalValue)}));
    }

    public void showDataComparedToTimeGoal(String timeValue, String timeTitle, int weekTotalWorkoutsTimeInSeconds, int totalSeconds) {
        this.mTextViewWeekTitle.setText(timeTitle);
        this.mTxtViewGoalGraphic.setText(timeValue);
    }

    public void showDataComparedWithPreviousWeek(String txtViewThisWeekNumber, String txtViewLastWeekNumber, String txtViewTitle) {
        if (this.mTxtViewLastWeekNumber != null && this.mTxtViewThisWeekNumber != null && this.mTextViewWeekTitle != null) {
            this.mTxtViewThisWeekNumber.setText(txtViewThisWeekNumber);
            this.mTxtViewLastWeekNumber.setText(txtViewLastWeekNumber);
            this.mTextViewWeekTitle.setText(txtViewTitle);
        }
    }

    public void disableGoalGraphic() {
        this.mLayoutMiddleGraphicSection.setVisibility(8);
        this.mLayoutHomeMiddleNoGraphic.setVisibility(0);
    }

    public void enableGoalGraphic() {
        this.mLayoutMiddleGraphicSection.setVisibility(0);
        this.mLayoutHomeMiddleNoGraphic.setVisibility(8);
    }

    public void showHorizontalGraphicDataWhenGoalsEnabled(int backgroundColor, ArrayList<String> graphicTopValues, ArrayList<Double> horizontalGraphicValues, int graphicMaxValue) {
        this.mBarHorizontalGraphicView.setmFirsGraphicBarColor(backgroundColor);
        this.mBarHorizontalGraphicView.setmTopValuesTextColor(-1);
        this.mBarHorizontalGraphicView.setTopTextList(graphicTopValues);
        this.mBarHorizontalGraphicView.setDataList(horizontalGraphicValues, (double) graphicMaxValue);
    }

    private void setIcon() {
        ViewGroup.LayoutParams params = this.mImgViewIconStat.getLayoutParams();
        switch (this.mKind) {
            case 0:
            case 1:
                params.height = (int) getResources().getDimension(R.dimen.this_week_icon_height_avg);
                params.width = (int) getResources().getDimension(R.dimen.this_week_icon_width_avg);
                this.mImgViewIconStat.setLayoutParams(params);
                this.mImgViewIconStat.setImageResource(R.drawable.ic_calories_big);
                return;
            case 2:
                params.height = (int) getResources().getDimension(R.dimen.this_week_icon_time_height);
                params.width = (int) getResources().getDimension(R.dimen.this_week_icon_time_width);
                this.mImgViewIconStat.setLayoutParams(params);
                this.mImgViewIconStat.setImageResource(R.drawable.ic_time_big);
                return;
            case 3:
                params.height = (int) getResources().getDimension(R.dimen.this_week_icon_heart_rate_height);
                params.width = (int) getResources().getDimension(R.dimen.this_week_icon_heart_rate_width);
                this.mImgViewIconStat.setLayoutParams(params);
                this.mImgViewIconStat.setImageResource(R.drawable.ic_heartrate_big);
                return;
            case 4:
                params.height = (int) getResources().getDimension(R.dimen.this_week_icon_distance_height);
                params.width = (int) getResources().getDimension(R.dimen.this_week_icon_distance_width);
                this.mImgViewIconStat.setLayoutParams(params);
                this.mImgViewIconStat.setImageResource(R.drawable.ic_distance_big);
                return;
            case 5:
                params.height = (int) getResources().getDimension(R.dimen.this_week_icon_speed_height);
                params.width = (int) getResources().getDimension(R.dimen.this_week_icon_speed_width);
                this.mImgViewIconStat.setLayoutParams(params);
                this.mImgViewIconStat.setImageResource(R.drawable.ic_speed_big);
                return;
            case 6:
                params.height = (int) getResources().getDimension(R.dimen.this_week_icon_frecuency_height);
                params.width = (int) getResources().getDimension(R.dimen.this_week_icon_frecuency_width);
                this.mImgViewIconStat.setLayoutParams(params);
                this.mImgViewIconStat.setImageResource(R.drawable.ic_frequency_big);
                return;
            case 7:
                params.height = (int) getResources().getDimension(R.dimen.this_week_icon_watts_height);
                params.width = (int) getResources().getDimension(R.dimen.this_week_icon_watts_width);
                this.mImgViewIconStat.setLayoutParams(params);
                this.mImgViewIconStat.setImageResource(R.drawable.ic_watts_big);
                return;
            default:
                return;
        }
    }
}
