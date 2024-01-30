package com.nautilus.omni.appmodules.awards.adapters.detail;

import android.content.Context;
import com.nautilus.omni.R;
import com.nautilus.omni.appmodules.awards.AwardTypeEnum;
import com.nautilus.omni.appmodules.awards.AwardValueBuilder;
import com.nautilus.omni.model.dto.Award;
import com.nautilus.omni.util.Constants;
import com.nautilus.omni.util.DateUtil;
import com.nautilus.omni.util.ResourceHelper;
import org.joda.time.ReadableInstant;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class AwardDetailItemBuilder {
    private AwardValueBuilder mAwardValueBuilder;
    private Context mContext;
    private DateTimeFormatter mDateFormatter = DateTimeFormat.forPattern(this.mContext.getString(R.string.award_date_format));

    public AwardDetailItemBuilder(Context context, AwardValueBuilder awardValueBuilder) {
        this.mContext = context;
        this.mAwardValueBuilder = awardValueBuilder;
    }

    public void addItemRow(AwardDetailItemViewHolder holder, Award award, boolean isBestWorkoutAward) {
        if (isBestWorkoutAward) {
            setBestWorkoutItem(holder, award);
        } else {
            setRegularWorkoutItem(holder, award);
        }
    }

    private void setRegularWorkoutItem(AwardDetailItemViewHolder holder, Award award) {
        holder.mTxtViewBestWorkoutItem.setVisibility(8);
        holder.mItemContainer.setVisibility(0);
        if (isWeeklyAward(award)) {
            setWeeklyAwardValues(holder, award);
        } else {
            setRegularAwardValues(holder, award);
        }
    }

    private boolean isWeeklyAward(Award award) {
        return award.getmAwardType().getmName().equals(AwardTypeEnum.THREE_OR_MORE_WORKOUTS_IN_A_WEEK.toString()) || award.getmAwardType().getmName().equals(AwardTypeEnum.MOST_WORKOUTS_WEEK.toString()) || award.getmAwardType().getmName().equals(AwardTypeEnum.MOST_DISTANCE_PER_WEEK.toString());
    }

    private void setWeeklyAwardValues(AwardDetailItemViewHolder holder, Award award) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.mContext.getString(R.string.award_detail_week_of));
        holder.mTxtViewItemDate.setText(stringBuilder.append(this.mDateFormatter.print((ReadableInstant) DateUtil.getFirstDayOftWeek(award.getmDate())).toUpperCase()));
        holder.mTxtViewItemValue.setText(this.mAwardValueBuilder.getAwardDetailItemValue(holder, award));
    }

    private void setRegularAwardValues(AwardDetailItemViewHolder holder, Award award) {
        holder.mTxtViewItemDate.setText(award.getmDate().toString(this.mDateFormatter).toUpperCase());
        holder.mTxtViewItemValue.setText(this.mAwardValueBuilder.getAwardDetailItemValue(holder, award));
    }

    private void setBestWorkoutItem(AwardDetailItemViewHolder holder, Award award) {
        holder.mItemContainer.setVisibility(8);
        holder.mTxtViewBestWorkoutItem.setVisibility(0);
        holder.mTxtViewBestWorkoutItem.setText(ResourceHelper.getStringByKey(award.getmAwardType().getmDescription(), this.mContext).replace(Constants.COLON_SEPARATOR, ""));
    }
}
