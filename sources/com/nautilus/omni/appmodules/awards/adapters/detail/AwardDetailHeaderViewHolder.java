package com.nautilus.omni.appmodules.awards.adapters.detail;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.nautilus.omni.appmodules.awards.AwardValueBuilder;
import com.nautilus.omni.model.dto.Award;

public class AwardDetailHeaderViewHolder extends AwardDetailViewHolder {
    AwardDetailHeaderBuilder mAwardDetailHeaderBuilder;
    @BindView(2131755410)
    View mHeaderDividerBottom;
    @BindView(2131755408)
    View mHeaderDividerTop;
    @BindView(2131755406)
    ImageView mImgViewAwardIcon;
    @BindView(2131755353)
    TextView mTxtViewAwardDate;
    @BindView(2131755409)
    TextView mTxtViewAwardDescription;
    @BindView(2131755407)
    TextView mTxtViewAwardValue;

    public AwardDetailHeaderViewHolder(View itemView, AwardValueBuilder awardValueBuilder, int unit) {
        super(itemView);
        ButterKnife.bind((Object) this, itemView);
        this.mAwardDetailHeaderBuilder = new AwardDetailHeaderBuilder(itemView.getContext(), awardValueBuilder, unit);
    }

    public void loadView(int position, Award award, boolean isBestWorkoutAward) {
        this.mAwardDetailHeaderBuilder.addHeaderRow(this, award);
    }
}
