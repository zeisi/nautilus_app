package com.nautilus.omni.appmodules.awards.adapters.detail;

import android.content.Context;
import com.nautilus.omni.R;
import com.nautilus.omni.appmodules.awards.AwardTypeEnum;
import com.nautilus.omni.appmodules.awards.AwardValueBuilder;
import com.nautilus.omni.model.dto.Award;
import com.nautilus.omni.util.Constants;
import com.nautilus.omni.util.ResourceHelper;
import com.nautilus.omni.util.Util;

public class AwardHeaderBuilder {
    private AwardValueBuilder mAwardValueBuilder;
    private Context mContext;
    private int mUnit;

    public AwardHeaderBuilder(Context context, AwardValueBuilder awardValueBuilder, int unit) {
        this.mContext = context;
        this.mUnit = unit;
        this.mAwardValueBuilder = awardValueBuilder;
    }

    public void setUnit(int mUnit2) {
        this.mUnit = mUnit2;
    }

    public String getAwardHeaderValue(Award award) {
        StringBuilder stringBuilder = new StringBuilder();
        if (award.getmAwardType().getmName().equals(AwardTypeEnum.MOST_CALORIES_WORKOUT.toString())) {
            return stringBuilder.append(ResourceHelper.getStringByKey(award.getmAwardType().getmDescription(), this.mContext)).append(Constants.EMPTY_SPACE).append(award.getmWorkout().getmCalories()).toString();
        }
        if (award.getmAwardType().getmName().equals(AwardTypeEnum.HIGHEST_AVG_CALORIE.toString())) {
            return stringBuilder.append(ResourceHelper.getStringByKey(award.getmAwardType().getmDescription(), this.mContext)).append(Constants.EMPTY_SPACE).append(Util.getAvgCalorieBurnRateAsStringOneDecimal(award.getmWorkout())).toString();
        }
        if (award.getmAwardType().getmName().equals(AwardTypeEnum.LONGEST_DISTANCE.toString())) {
            return stringBuilder.append(ResourceHelper.getStringByKey(award.getmAwardType().getmDescription(), this.mContext)).append(Constants.EMPTY_SPACE).append(Util.getAwardDistanceValueDependingOnCurrentUnit(this.mContext, this.mUnit, award)).toString();
        }
        if (award.getmAwardType().getmName().equals(AwardTypeEnum.MOST_WORKOUTS_WEEK.toString())) {
            return stringBuilder.append(ResourceHelper.getStringByKey(award.getmAwardType().getmDescription(), this.mContext)).append(Constants.EMPTY_SPACE).append(this.mAwardValueBuilder.getAmountWorkoutsInWeek(award.getmDate())).toString();
        }
        if (award.getmAwardType().getmName().equals(AwardTypeEnum.HIGHEST_AVG_SPEED.toString())) {
            return stringBuilder.append(ResourceHelper.getStringByKey(award.getmAwardType().getmDescription(), this.mContext)).append(Constants.EMPTY_SPACE).append(Util.getAwardSpeedValueDependingOnCurrentUnit(this.mContext, this.mUnit, award)).toString();
        }
        if (award.getmAwardType().getmName().equals(AwardTypeEnum.BEST_WORKOUT.toString())) {
            return stringBuilder.append(ResourceHelper.getStringByKey(award.getmAwardType().getmDescription(), this.mContext)).append(Constants.EMPTY_SPACE).append(this.mContext.getString(R.string.awards, new Object[]{Integer.valueOf(award.getmWorkout().getmAwards().size() - 1)})).toString();
        } else if (award.getmAwardType().getmName().equals(AwardTypeEnum.MOST_DISTANCE_PER_WEEK.toString())) {
            return stringBuilder.append(ResourceHelper.getStringByKey(award.getmAwardType().getmDescription(), this.mContext)).append(Constants.EMPTY_SPACE).append(this.mAwardValueBuilder.getMostDistanceInWeek(award)).toString();
        } else {
            if (award.getmAwardType().getmName().equals(AwardTypeEnum.LONGEST_WORKOUT.toString())) {
                return stringBuilder.append(ResourceHelper.getStringByKey(award.getmAwardType().getmDescription(), this.mContext)).append(Constants.EMPTY_SPACE).append(Util.convertSecondsToDuration((long) award.getmWorkout().getmElapsedTime(), true)).toString();
            }
            return ResourceHelper.getStringByKey(award.getmAwardType().getmDescription(), this.mContext);
        }
    }
}
