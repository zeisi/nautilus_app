package com.nautilus.omni.appmodules.awards.adapters.detail;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import com.nautilus.omni.R;
import com.nautilus.omni.appmodules.awards.AwardTypeEnum;
import com.nautilus.omni.appmodules.awards.AwardValueBuilder;
import com.nautilus.omni.model.dto.Award;
import com.nautilus.omni.model.dto.AwardType;
import com.nautilus.omni.util.Util;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class AwardDetailHeaderBuilder {
    private AwardHeaderBuilder mAwardHeaderBuilder;
    private AwardValueBuilder mAwardValueBuilder;
    private Context mContext;
    private DateTimeFormatter mDateFormatter = DateTimeFormat.forPattern(this.mContext.getString(R.string.award_date_format));
    private int mUnit;

    public AwardDetailHeaderBuilder(Context context, AwardValueBuilder awardValueBuilder, int unit) {
        this.mContext = context;
        this.mAwardValueBuilder = awardValueBuilder;
        this.mUnit = unit;
        this.mAwardHeaderBuilder = new AwardHeaderBuilder(this.mContext, this.mAwardValueBuilder, this.mUnit);
    }

    public void addHeaderRow(AwardDetailHeaderViewHolder holder, Award award) {
        AwardType awardType = award.getmAwardType();
        holder.mImgViewAwardIcon.setImageDrawable(ResourcesCompat.getDrawable(this.mContext.getResources(), awardType.getmBigImage(), (Resources.Theme) null));
        addHeaderDividers(holder, awardType);
        setAwardDescription(holder, award);
        setHeaderDateValue(holder, award);
        setHeaderValue(holder, award);
    }

    private void setHeaderDateValue(AwardDetailHeaderViewHolder holder, Award award) {
        if (isWeeklyAward(award.getmAwardType())) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.mContext.getString(R.string.award_detail_week_of));
            holder.mTxtViewAwardDate.setText(stringBuilder.append(award.getmDate().toString(this.mDateFormatter).toUpperCase()));
            return;
        }
        holder.mTxtViewAwardDate.setText(award.getmDate().toString(this.mDateFormatter));
    }

    private void setHeaderValue(AwardDetailHeaderViewHolder holder, Award award) {
        AwardType awardType = award.getmAwardType();
        if (awardType.getmName().equals(AwardTypeEnum.MOST_CALORIES_WORKOUT.toString()) || awardType.getmName().equals(AwardTypeEnum.HIGHEST_AVG_CALORIE.toString())) {
            setCalorieValue(holder, award, award.getmAwardType());
        } else {
            holder.mTxtViewAwardValue.setVisibility(8);
        }
    }

    private boolean isWeeklyAward(AwardType awardType) {
        return awardType.getmName().equals(AwardTypeEnum.MOST_WORKOUTS_WEEK.toString()) || awardType.getmName().equals(AwardTypeEnum.THREE_OR_MORE_WORKOUTS_IN_A_WEEK.toString()) || awardType.getmName().equals(AwardTypeEnum.MOST_DISTANCE_PER_WEEK.toString());
    }

    private void addHeaderDividers(AwardDetailHeaderViewHolder holder, AwardType awardType) {
        holder.mHeaderDividerTop.setBackgroundColor(ContextCompat.getColor(this.mContext, awardType.getmColor()));
        holder.mHeaderDividerBottom.setBackgroundColor(ContextCompat.getColor(this.mContext, awardType.getmColor()));
        if (awardType.getmName().equals(AwardTypeEnum.FIRST_WORKOUT.toString()) || awardType.getmName().equals(AwardTypeEnum.BEST_WORKOUT.toString())) {
            holder.mHeaderDividerBottom.setVisibility(8);
        } else {
            holder.mHeaderDividerBottom.setVisibility(0);
        }
    }

    private void setAwardDescription(AwardDetailHeaderViewHolder holder, Award award) {
        holder.mTxtViewAwardDescription.setText(this.mAwardHeaderBuilder.getAwardHeaderValue(award));
    }

    private void setCalorieValue(AwardDetailHeaderViewHolder holder, Award award, AwardType awardType) {
        holder.mTxtViewAwardValue.setVisibility(0);
        if (awardType.getmName().equals(AwardTypeEnum.MOST_CALORIES_WORKOUT.toString())) {
            holder.mTxtViewAwardValue.setText(String.valueOf(award.getmWorkout().getmCalories()));
            return;
        }
        holder.mTxtViewAwardValue.setText(new StringBuilder().append(Util.getAvgCalorieBurnRateAsStringOneDecimal(award.getmWorkout())).append(this.mContext.getString(R.string.award_avg)));
    }
}
