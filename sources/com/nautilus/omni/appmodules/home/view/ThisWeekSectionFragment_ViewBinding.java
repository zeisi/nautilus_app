package com.nautilus.omni.appmodules.home.view;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.nautilus.omni.R;
import com.nautilus.omni.customviews.graphics.HorizontalBarGraphicView;

public class ThisWeekSectionFragment_ViewBinding implements Unbinder {
    private ThisWeekSectionFragment target;

    @UiThread
    public ThisWeekSectionFragment_ViewBinding(ThisWeekSectionFragment target2, View source) {
        this.target = target2;
        target2.mImgViewIconStat = (ImageView) Utils.findRequiredViewAsType(source, R.id.imageViewIconStat, "field 'mImgViewIconStat'", ImageView.class);
        target2.mTextViewWeekTitle = (TextView) Utils.findRequiredViewAsType(source, R.id.textViewWeekTitle, "field 'mTextViewWeekTitle'", TextView.class);
        target2.mTxtViewThisWeekNumber = (TextView) Utils.findRequiredViewAsType(source, R.id.textViewThisWeekNumber, "field 'mTxtViewThisWeekNumber'", TextView.class);
        target2.mTxtViewLastWeekNumber = (TextView) Utils.findRequiredViewAsType(source, R.id.textViewLastWeekNumber, "field 'mTxtViewLastWeekNumber'", TextView.class);
        target2.mTxtViewGoalGraphic = (TextView) Utils.findRequiredViewAsType(source, R.id.textViewGoalGraphic, "field 'mTxtViewGoalGraphic'", TextView.class);
        target2.mBarHorizontalGraphicView = (HorizontalBarGraphicView) Utils.findRequiredViewAsType(source, R.id.horizontalBarGraphicThisWeek, "field 'mBarHorizontalGraphicView'", HorizontalBarGraphicView.class);
        target2.mLayoutHomeMiddleNoGraphic = (LinearLayout) Utils.findRequiredViewAsType(source, R.id.layoutHomeMiddleNoGraphic, "field 'mLayoutHomeMiddleNoGraphic'", LinearLayout.class);
        target2.mLayoutMiddleGraphicSection = (LinearLayout) Utils.findRequiredViewAsType(source, R.id.layoutMiddleGraphicSection, "field 'mLayoutMiddleGraphicSection'", LinearLayout.class);
    }

    @CallSuper
    public void unbind() {
        ThisWeekSectionFragment target2 = this.target;
        if (target2 == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        target2.mImgViewIconStat = null;
        target2.mTextViewWeekTitle = null;
        target2.mTxtViewThisWeekNumber = null;
        target2.mTxtViewLastWeekNumber = null;
        target2.mTxtViewGoalGraphic = null;
        target2.mBarHorizontalGraphicView = null;
        target2.mLayoutHomeMiddleNoGraphic = null;
        target2.mLayoutMiddleGraphicSection = null;
    }
}
