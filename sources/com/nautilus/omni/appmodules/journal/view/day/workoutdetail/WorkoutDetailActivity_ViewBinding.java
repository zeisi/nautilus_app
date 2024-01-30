package com.nautilus.omni.appmodules.journal.view.day.workoutdetail;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.nautilus.omni.R;

public class WorkoutDetailActivity_ViewBinding implements Unbinder {
    private WorkoutDetailActivity target;

    @UiThread
    public WorkoutDetailActivity_ViewBinding(WorkoutDetailActivity target2) {
        this(target2, target2.getWindow().getDecorView());
    }

    @UiThread
    public WorkoutDetailActivity_ViewBinding(WorkoutDetailActivity target2, View source) {
        this.target = target2;
        target2.mToolbar = (Toolbar) Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'mToolbar'", Toolbar.class);
        target2.mTxtViewToolbarTitle = (TextView) Utils.findRequiredViewAsType(source, R.id.toolbar_title, "field 'mTxtViewToolbarTitle'", TextView.class);
        target2.mTxtViewWorkoutDate = (TextView) Utils.findRequiredViewAsType(source, R.id.text_view_date_value, "field 'mTxtViewWorkoutDate'", TextView.class);
        target2.mTxtViewElapsedTimeValue = (TextView) Utils.findRequiredViewAsType(source, R.id.text_view_elapsed_time, "field 'mTxtViewElapsedTimeValue'", TextView.class);
        target2.mTxtViewCaloriesBurnedValue = (TextView) Utils.findRequiredViewAsType(source, R.id.text_view_calories_burned, "field 'mTxtViewCaloriesBurnedValue'", TextView.class);
        target2.mTxtViewAvgCaloriesBurnedValue = (TextView) Utils.findRequiredViewAsType(source, R.id.text_view_average_calories_burned, "field 'mTxtViewAvgCaloriesBurnedValue'", TextView.class);
        target2.mTxtViewHeartRateValue = (TextView) Utils.findRequiredViewAsType(source, R.id.text_view_heart_rate_avg, "field 'mTxtViewHeartRateValue'", TextView.class);
        target2.mTxtViewAvgWattsValue = (TextView) Utils.findRequiredViewAsType(source, R.id.text_view_watts_avg, "field 'mTxtViewAvgWattsValue'", TextView.class);
        target2.mTxtViewAvgSpeed = (TextView) Utils.findRequiredViewAsType(source, R.id.text_view_avg_speed, "field 'mTxtViewAvgSpeed'", TextView.class);
        target2.mTxtViewDistance = (TextView) Utils.findRequiredViewAsType(source, R.id.text_view_distance, "field 'mTxtViewDistance'", TextView.class);
        target2.mTxtViewAvgSpeedUnit = (TextView) Utils.findRequiredViewAsType(source, R.id.text_view_speed_unit, "field 'mTxtViewAvgSpeedUnit'", TextView.class);
        target2.mTxtViewDistanceUnit = (TextView) Utils.findRequiredViewAsType(source, R.id.text_view_distance_unit, "field 'mTxtViewDistanceUnit'", TextView.class);
        target2.mPowerDivider = (TextView) Utils.findRequiredViewAsType(source, R.id.power_divider, "field 'mPowerDivider'", TextView.class);
        target2.mPowerLabelLayout = (RelativeLayout) Utils.findRequiredViewAsType(source, R.id.power_label_layout, "field 'mPowerLabelLayout'", RelativeLayout.class);
        target2.mPowerValueLayout = (RelativeLayout) Utils.findRequiredViewAsType(source, R.id.power_value_layout, "field 'mPowerValueLayout'", RelativeLayout.class);
    }

    @CallSuper
    public void unbind() {
        WorkoutDetailActivity target2 = this.target;
        if (target2 == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        target2.mToolbar = null;
        target2.mTxtViewToolbarTitle = null;
        target2.mTxtViewWorkoutDate = null;
        target2.mTxtViewElapsedTimeValue = null;
        target2.mTxtViewCaloriesBurnedValue = null;
        target2.mTxtViewAvgCaloriesBurnedValue = null;
        target2.mTxtViewHeartRateValue = null;
        target2.mTxtViewAvgWattsValue = null;
        target2.mTxtViewAvgSpeed = null;
        target2.mTxtViewDistance = null;
        target2.mTxtViewAvgSpeedUnit = null;
        target2.mTxtViewDistanceUnit = null;
        target2.mPowerDivider = null;
        target2.mPowerLabelLayout = null;
        target2.mPowerValueLayout = null;
    }
}
