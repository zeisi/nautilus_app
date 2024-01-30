package com.nautilus.omni.appmodules.awardtypes.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.nautilus.omni.appmodules.awards.AwardValueBuilder;
import com.nautilus.omni.model.dto.Award;
import com.nautilus.omni.model.dto.AwardType;
import com.nautilus.omni.util.ResourceHelper;

public class AwardTypesViewHolder extends RecyclerView.ViewHolder {
    private static final float ALPHA = 0.3f;
    private AwardValueBuilder mAwardValueBuilder;
    private Context mContext;
    @BindView(2131755351)
    ImageView mImgViewAwardIcon;
    @BindView(2131755418)
    TextView mTxtViewAwardAlternativeTitle;
    @BindView(2131755416)
    TextView mTxtViewAwardTitle;
    @BindView(2131755417)
    TextView mTxtViewAwardValue;

    public AwardTypesViewHolder(View view, AwardValueBuilder awardValueBuilder) {
        super(view);
        ButterKnife.bind((Object) this, view);
        this.mContext = view.getContext();
        this.mAwardValueBuilder = awardValueBuilder;
    }

    public void bind(AwardType awardType, Award award) {
        if (award != null) {
            buildAvailableAwardRow(award);
        } else {
            buildUnavailableAwardRow(awardType);
        }
    }

    private void buildAvailableAwardRow(Award award) {
        this.mImgViewAwardIcon.setImageDrawable(ResourcesCompat.getDrawable(this.mContext.getResources(), award.getmAwardType().getmMediumImage(), (Resources.Theme) null));
        this.mImgViewAwardIcon.setAlpha(1.0f);
        this.mTxtViewAwardTitle.setText(ResourceHelper.getStringByKey(award.getmAwardType().getmName(), this.mContext));
        this.mTxtViewAwardValue.setText(this.mAwardValueBuilder.getAwardTypeValue(award));
        this.mTxtViewAwardValue.setVisibility(0);
        this.mTxtViewAwardTitle.setVisibility(0);
        this.mTxtViewAwardAlternativeTitle.setVisibility(8);
    }

    private void buildUnavailableAwardRow(AwardType awardType) {
        this.mImgViewAwardIcon.setImageDrawable(ResourcesCompat.getDrawable(this.mContext.getResources(), awardType.getmMediumImage(), (Resources.Theme) null));
        this.mImgViewAwardIcon.setAlpha(ALPHA);
        this.mTxtViewAwardAlternativeTitle.setText(ResourceHelper.getStringByKey(awardType.getmName(), this.mContext));
        this.mTxtViewAwardAlternativeTitle.setVisibility(0);
        this.mTxtViewAwardTitle.setVisibility(8);
        this.mTxtViewAwardValue.setVisibility(8);
    }
}
