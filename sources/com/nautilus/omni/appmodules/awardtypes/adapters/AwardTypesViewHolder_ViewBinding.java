package com.nautilus.omni.appmodules.awardtypes.adapters;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.nautilus.omni.R;

public class AwardTypesViewHolder_ViewBinding implements Unbinder {
    private AwardTypesViewHolder target;

    @UiThread
    public AwardTypesViewHolder_ViewBinding(AwardTypesViewHolder target2, View source) {
        this.target = target2;
        target2.mImgViewAwardIcon = (ImageView) Utils.findRequiredViewAsType(source, R.id.image_view_award_type_icon, "field 'mImgViewAwardIcon'", ImageView.class);
        target2.mTxtViewAwardTitle = (TextView) Utils.findRequiredViewAsType(source, R.id.text_view_award_type_title, "field 'mTxtViewAwardTitle'", TextView.class);
        target2.mTxtViewAwardAlternativeTitle = (TextView) Utils.findRequiredViewAsType(source, R.id.text_view_award_type_alternative_title, "field 'mTxtViewAwardAlternativeTitle'", TextView.class);
        target2.mTxtViewAwardValue = (TextView) Utils.findRequiredViewAsType(source, R.id.text_view_award_type_value, "field 'mTxtViewAwardValue'", TextView.class);
    }

    @CallSuper
    public void unbind() {
        AwardTypesViewHolder target2 = this.target;
        if (target2 == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        target2.mImgViewAwardIcon = null;
        target2.mTxtViewAwardTitle = null;
        target2.mTxtViewAwardAlternativeTitle = null;
        target2.mTxtViewAwardValue = null;
    }
}
