package com.nautilus.omni.appmodules.awards.adapters.detail;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.nautilus.omni.appmodules.awards.AwardValueBuilder;
import com.nautilus.omni.model.dto.Award;

public class AwardDetailItemViewHolder extends AwardDetailViewHolder {
    AwardDetailItemBuilder mAwardDetailItemBuilder;
    @BindView(2131755411)
    public LinearLayout mItemContainer;
    @BindView(2131755414)
    public TextView mTxtViewBestWorkoutItem;
    @BindView(2131755412)
    public TextView mTxtViewItemDate;
    @BindView(2131755413)
    public TextView mTxtViewItemValue;

    public AwardDetailItemViewHolder(View itemView, AwardValueBuilder awardValueBuilder) {
        super(itemView);
        ButterKnife.bind((Object) this, itemView);
        this.mAwardDetailItemBuilder = new AwardDetailItemBuilder(itemView.getContext(), awardValueBuilder);
    }

    public void loadView(int position, Award award, boolean isBestWorkoutAward) {
        this.mAwardDetailItemBuilder.addItemRow(this, award, isBestWorkoutAward);
    }
}
