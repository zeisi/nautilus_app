package com.nautilus.omni.appmodules.awards.adapters.detail;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.nautilus.omni.R;

public class AwardDetailHeaderViewHolder_ViewBinding implements Unbinder {
    private AwardDetailHeaderViewHolder target;

    @UiThread
    public AwardDetailHeaderViewHolder_ViewBinding(AwardDetailHeaderViewHolder target2, View source) {
        this.target = target2;
        target2.mImgViewAwardIcon = (ImageView) Utils.findRequiredViewAsType(source, R.id.text_view_header_award_icon, "field 'mImgViewAwardIcon'", ImageView.class);
        target2.mHeaderDividerTop = Utils.findRequiredView(source, R.id.linear_layout_header_divider, "field 'mHeaderDividerTop'");
        target2.mHeaderDividerBottom = Utils.findRequiredView(source, R.id.linear_layout_header_divider_bottom, "field 'mHeaderDividerBottom'");
        target2.mTxtViewAwardDescription = (TextView) Utils.findRequiredViewAsType(source, R.id.text_view_header_award_description, "field 'mTxtViewAwardDescription'", TextView.class);
        target2.mTxtViewAwardDate = (TextView) Utils.findRequiredViewAsType(source, R.id.text_view_header_award_date, "field 'mTxtViewAwardDate'", TextView.class);
        target2.mTxtViewAwardValue = (TextView) Utils.findRequiredViewAsType(source, R.id.text_view_header_award_detail_value, "field 'mTxtViewAwardValue'", TextView.class);
    }

    @CallSuper
    public void unbind() {
        AwardDetailHeaderViewHolder target2 = this.target;
        if (target2 == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        target2.mImgViewAwardIcon = null;
        target2.mHeaderDividerTop = null;
        target2.mHeaderDividerBottom = null;
        target2.mTxtViewAwardDescription = null;
        target2.mTxtViewAwardDate = null;
        target2.mTxtViewAwardValue = null;
    }
}
