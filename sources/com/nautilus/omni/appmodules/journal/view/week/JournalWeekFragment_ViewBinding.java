package com.nautilus.omni.appmodules.journal.view.week;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.nautilus.omni.R;
import com.verticalbargraphiclibrary.VerticalBarGraphicView;

public class JournalWeekFragment_ViewBinding implements Unbinder {
    private JournalWeekFragment target;
    private View view2131755338;
    private View view2131755340;

    @UiThread
    public JournalWeekFragment_ViewBinding(final JournalWeekFragment target2, View source) {
        this.target = target2;
        target2.mBarViewXWeek = (VerticalBarGraphicView) Utils.findRequiredViewAsType(source, R.id.bar_view_week, "field 'mBarViewXWeek'", VerticalBarGraphicView.class);
        target2.mMetricsSpinner = (Spinner) Utils.findRequiredViewAsType(source, R.id.spinner_week, "field 'mMetricsSpinner'", Spinner.class);
        target2.mJournalTextDay = (TextView) Utils.findRequiredViewAsType(source, R.id.journal_txt_day, "field 'mJournalTextDay'", TextView.class);
        View view = Utils.findRequiredView(source, R.id.journal_btn_week_before, "field 'mBtnWeekBefore' and method 'getWorkoutInfoFromPreviousWeek'");
        target2.mBtnWeekBefore = (ImageButton) Utils.castView(view, R.id.journal_btn_week_before, "field 'mBtnWeekBefore'", ImageButton.class);
        this.view2131755338 = view;
        view.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target2.getWorkoutInfoFromPreviousWeek();
            }
        });
        View view2 = Utils.findRequiredView(source, R.id.journal_btn_week_after, "field 'mBtnWeekAfter' and method 'getWorkoutInfoFromNextWeek'");
        target2.mBtnWeekAfter = (ImageButton) Utils.castView(view2, R.id.journal_btn_week_after, "field 'mBtnWeekAfter'", ImageButton.class);
        this.view2131755340 = view2;
        view2.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target2.getWorkoutInfoFromNextWeek();
            }
        });
        target2.mTxtViewUnitLabel = (TextView) Utils.findRequiredViewAsType(source, R.id.text_view_units_label, "field 'mTxtViewUnitLabel'", TextView.class);
    }

    @CallSuper
    public void unbind() {
        JournalWeekFragment target2 = this.target;
        if (target2 == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        target2.mBarViewXWeek = null;
        target2.mMetricsSpinner = null;
        target2.mJournalTextDay = null;
        target2.mBtnWeekBefore = null;
        target2.mBtnWeekAfter = null;
        target2.mTxtViewUnitLabel = null;
        this.view2131755338.setOnClickListener((View.OnClickListener) null);
        this.view2131755338 = null;
        this.view2131755340.setOnClickListener((View.OnClickListener) null);
        this.view2131755340 = null;
    }
}
