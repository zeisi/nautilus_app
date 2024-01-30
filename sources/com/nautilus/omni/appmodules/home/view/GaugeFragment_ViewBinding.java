package com.nautilus.omni.appmodules.home.view;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.nautilus.omni.R;

public class GaugeFragment_ViewBinding implements Unbinder {
    private GaugeFragment target;

    @UiThread
    public GaugeFragment_ViewBinding(GaugeFragment target2, View source) {
        this.target = target2;
        target2.mImageViewGaugeKind = (ImageView) Utils.findRequiredViewAsType(source, R.id.imageViewGaugeKind, "field 'mImageViewGaugeKind'", ImageView.class);
        target2.mTextViewCenterGauge = (TextView) Utils.findRequiredViewAsType(source, R.id.textViewCenterGauge, "field 'mTextViewCenterGauge'", TextView.class);
        target2.mTextViewGaugeData = (TextView) Utils.findRequiredViewAsType(source, R.id.textViewGaugeData, "field 'mTextViewGaugeData'", TextView.class);
        target2.mTxtViewSubtitleGauge = (TextView) Utils.findRequiredViewAsType(source, R.id.textViewSubtitleGauge, "field 'mTxtViewSubtitleGauge'", TextView.class);
        target2.mImgViewNeedle = (ImageView) Utils.findRequiredViewAsType(source, R.id.imageViewNeedle, "field 'mImgViewNeedle'", ImageView.class);
        target2.mTxtEnableGoalsButNoData = (TextView) Utils.findRequiredViewAsType(source, R.id.txtEnableGoalsButNoData, "field 'mTxtEnableGoalsButNoData'", TextView.class);
    }

    @CallSuper
    public void unbind() {
        GaugeFragment target2 = this.target;
        if (target2 == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        target2.mImageViewGaugeKind = null;
        target2.mTextViewCenterGauge = null;
        target2.mTextViewGaugeData = null;
        target2.mTxtViewSubtitleGauge = null;
        target2.mImgViewNeedle = null;
        target2.mTxtEnableGoalsButNoData = null;
    }
}
