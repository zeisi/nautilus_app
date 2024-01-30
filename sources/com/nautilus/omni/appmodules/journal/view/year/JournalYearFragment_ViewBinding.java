package com.nautilus.omni.appmodules.journal.view.year;

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

public class JournalYearFragment_ViewBinding implements Unbinder {
    private JournalYearFragment target;
    private View view2131755345;
    private View view2131755347;

    @UiThread
    public JournalYearFragment_ViewBinding(final JournalYearFragment target2, View source) {
        this.target = target2;
        target2.mBarViewXYear = (VerticalBarGraphicView) Utils.findRequiredViewAsType(source, R.id.bar_view_year, "field 'mBarViewXYear'", VerticalBarGraphicView.class);
        target2.mSpinnerYearMetrics = (Spinner) Utils.findRequiredViewAsType(source, R.id.spinner_year, "field 'mSpinnerYearMetrics'", Spinner.class);
        target2.journalTextYear = (TextView) Utils.findRequiredViewAsType(source, R.id.journal_txt_year, "field 'journalTextYear'", TextView.class);
        View view = Utils.findRequiredView(source, R.id.journal_btn_year_before, "field 'mYearBeforeButton' and method 'getWorkoutInfoFromPreviousYear'");
        target2.mYearBeforeButton = (ImageButton) Utils.castView(view, R.id.journal_btn_year_before, "field 'mYearBeforeButton'", ImageButton.class);
        this.view2131755345 = view;
        view.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target2.getWorkoutInfoFromPreviousYear();
            }
        });
        View view2 = Utils.findRequiredView(source, R.id.journal_btn_year_after, "field 'mYearAfterButton' and method 'getWorkoutInfoFromNextYear'");
        target2.mYearAfterButton = (ImageButton) Utils.castView(view2, R.id.journal_btn_year_after, "field 'mYearAfterButton'", ImageButton.class);
        this.view2131755347 = view2;
        view2.setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target2.getWorkoutInfoFromNextYear();
            }
        });
        target2.mTxtViewUnitLabel = (TextView) Utils.findRequiredViewAsType(source, R.id.text_view_units_label, "field 'mTxtViewUnitLabel'", TextView.class);
    }

    @CallSuper
    public void unbind() {
        JournalYearFragment target2 = this.target;
        if (target2 == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        target2.mBarViewXYear = null;
        target2.mSpinnerYearMetrics = null;
        target2.journalTextYear = null;
        target2.mYearBeforeButton = null;
        target2.mYearAfterButton = null;
        target2.mTxtViewUnitLabel = null;
        this.view2131755345.setOnClickListener((View.OnClickListener) null);
        this.view2131755345 = null;
        this.view2131755347.setOnClickListener((View.OnClickListener) null);
        this.view2131755347 = null;
    }
}
