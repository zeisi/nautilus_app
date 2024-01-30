package com.nautilus.omni.appmodules.awards.adapters.detail;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.nautilus.omni.R;

public class AwardDetailItemViewHolder_ViewBinding implements Unbinder {
    private AwardDetailItemViewHolder target;

    @UiThread
    public AwardDetailItemViewHolder_ViewBinding(AwardDetailItemViewHolder target2, View source) {
        this.target = target2;
        target2.mItemContainer = (LinearLayout) Utils.findRequiredViewAsType(source, R.id.item_container, "field 'mItemContainer'", LinearLayout.class);
        target2.mTxtViewItemDate = (TextView) Utils.findRequiredViewAsType(source, R.id.text_view_item_date, "field 'mTxtViewItemDate'", TextView.class);
        target2.mTxtViewItemValue = (TextView) Utils.findRequiredViewAsType(source, R.id.text_view_item_value, "field 'mTxtViewItemValue'", TextView.class);
        target2.mTxtViewBestWorkoutItem = (TextView) Utils.findRequiredViewAsType(source, R.id.text_view_best_workout_item, "field 'mTxtViewBestWorkoutItem'", TextView.class);
    }

    @CallSuper
    public void unbind() {
        AwardDetailItemViewHolder target2 = this.target;
        if (target2 == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        target2.mItemContainer = null;
        target2.mTxtViewItemDate = null;
        target2.mTxtViewItemValue = null;
        target2.mTxtViewBestWorkoutItem = null;
    }
}
