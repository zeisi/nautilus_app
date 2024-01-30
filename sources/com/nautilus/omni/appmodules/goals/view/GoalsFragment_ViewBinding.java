package com.nautilus.omni.appmodules.goals.view;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.nautilus.omni.R;

public class GoalsFragment_ViewBinding implements Unbinder {
    private GoalsFragment target;
    private View view2131755293;
    private View view2131755294;

    @UiThread
    public GoalsFragment_ViewBinding(final GoalsFragment target2, View source) {
        this.target = target2;
        target2.mSpinnerCaloriesPerWorkout = (Spinner) Utils.findRequiredViewAsType(source, R.id.spinner_calories, "field 'mSpinnerCaloriesPerWorkout'", Spinner.class);
        target2.mSpinnerTimePerWorkout = (Spinner) Utils.findRequiredViewAsType(source, R.id.spinner_time_workouts, "field 'mSpinnerTimePerWorkout'", Spinner.class);
        target2.mSpinnerWorkoutsPerWeek = (Spinner) Utils.findRequiredViewAsType(source, R.id.spinner_number_workouts, "field 'mSpinnerWorkoutsPerWeek'", Spinner.class);
        target2.mButtonsContainer = (RelativeLayout) Utils.findRequiredViewAsType(source, R.id.goals_buttons_container, "field 'mButtonsContainer'", RelativeLayout.class);
        View view = Utils.findRequiredView(source, R.id.button_cancel_goals, "field 'mCancelButton' and method 'cancelGoals'");
        target2.mCancelButton = (Button) Utils.castView(view, R.id.button_cancel_goals, "field 'mCancelButton'", Button.class);
        this.view2131755293 = view;
        view.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target2.cancelGoals();
            }
        });
        View view2 = Utils.findRequiredView(source, R.id.button_save_goals, "field 'mSaveButton' and method 'saveGoals'");
        target2.mSaveButton = (Button) Utils.castView(view2, R.id.button_save_goals, "field 'mSaveButton'", Button.class);
        this.view2131755294 = view2;
        view2.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target2.saveGoals();
            }
        });
        target2.mSwitchGoals = (SwitchCompat) Utils.findRequiredViewAsType(source, R.id.switch_goals, "field 'mSwitchGoals'", SwitchCompat.class);
        target2.mTxtViewCaloriesPerWorkout = (TextView) Utils.findRequiredViewAsType(source, R.id.text_view_calories, "field 'mTxtViewCaloriesPerWorkout'", TextView.class);
        target2.mTxtViewTimePerWorkout = (TextView) Utils.findRequiredViewAsType(source, R.id.text_view_time_workouts, "field 'mTxtViewTimePerWorkout'", TextView.class);
        target2.mTxtViewWorkoutsPerWeek = (TextView) Utils.findRequiredViewAsType(source, R.id.text_view_number_workouts, "field 'mTxtViewWorkoutsPerWeek'", TextView.class);
        target2.goalsTitleGone = Utils.findRequiredView(source, R.id.goals_title_gone, "field 'goalsTitleGone'");
    }

    @CallSuper
    public void unbind() {
        GoalsFragment target2 = this.target;
        if (target2 == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        target2.mSpinnerCaloriesPerWorkout = null;
        target2.mSpinnerTimePerWorkout = null;
        target2.mSpinnerWorkoutsPerWeek = null;
        target2.mButtonsContainer = null;
        target2.mCancelButton = null;
        target2.mSaveButton = null;
        target2.mSwitchGoals = null;
        target2.mTxtViewCaloriesPerWorkout = null;
        target2.mTxtViewTimePerWorkout = null;
        target2.mTxtViewWorkoutsPerWeek = null;
        target2.goalsTitleGone = null;
        this.view2131755293.setOnClickListener((View.OnClickListener) null);
        this.view2131755293 = null;
        this.view2131755294.setOnClickListener((View.OnClickListener) null);
        this.view2131755294 = null;
    }
}
