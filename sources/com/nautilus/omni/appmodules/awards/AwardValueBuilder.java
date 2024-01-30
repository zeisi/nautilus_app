package com.nautilus.omni.appmodules.awards;

import android.content.Context;
import com.nautilus.omni.R;
import com.nautilus.omni.appmodules.awards.adapters.detail.AwardDetailItemViewHolder;
import com.nautilus.omni.dataaccess.WeekDaoHelper;
import com.nautilus.omni.model.dto.Award;
import com.nautilus.omni.util.Constants;
import com.nautilus.omni.util.Util;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class AwardValueBuilder {
    private Context mContext;
    private DateTimeFormatter mDateFormatter = DateTimeFormat.forPattern(this.mContext.getString(R.string.award_type_full_date_format));
    private int mUnit = 0;
    private WeekDaoHelper mWeekDaoHelper;

    public AwardValueBuilder(Context context, WeekDaoHelper weekDaoHelper) {
        this.mContext = context;
        this.mWeekDaoHelper = weekDaoHelper;
    }

    public void setUnit(int unit) {
        this.mUnit = unit;
    }

    public String getAwardValue(Award award) {
        StringBuilder stringBuilder = new StringBuilder();
        if (award.getmAwardType().getmName().equals(AwardTypeEnum.MOST_CALORIES_WORKOUT.toString())) {
            return stringBuilder.append(award.getmWorkout().getmCalories()).append(this.mContext.getString(R.string.awards_cal)).toString();
        }
        if (award.getmAwardType().getmName().equals(AwardTypeEnum.HIGHEST_AVG_CALORIE.toString())) {
            return stringBuilder.append(Util.getAvgCalorieBurnRateAsStringOneDecimal(award.getmWorkout())).append(this.mContext.getString(R.string.awards_cal_avg)).toString();
        }
        if (award.getmAwardType().getmName().equals(AwardTypeEnum.THREE_OR_MORE_WORKOUTS_IN_A_WEEK.toString())) {
            return stringBuilder.append(this.mContext.getString(R.string.awards_three_times)).toString();
        }
        if (award.getmAwardType().getmName().equals(AwardTypeEnum.HIGHEST_AVG_SPEED.toString())) {
            return stringBuilder.append(Util.getAwardSpeedValueDependingOnCurrentUnit(this.mContext, this.mUnit, award)).toString();
        }
        if (award.getmAwardType().getmName().equals(AwardTypeEnum.LONGEST_DISTANCE.toString())) {
            return stringBuilder.append(Util.getAwardDistanceValueDependingOnCurrentUnit(this.mContext, this.mUnit, award)).toString();
        }
        if (award.getmAwardType().getmName().equals(AwardTypeEnum.LONGEST_WORKOUT.toString())) {
            return Util.convertSecondsToDuration((long) award.getmWorkout().getmElapsedTime(), true);
        }
        if (award.getmAwardType().getmName().equals(AwardTypeEnum.MOST_DISTANCE_PER_WEEK.toString())) {
            return getMostDistanceInWeek(award);
        }
        if (award.getmAwardType().getmName().equals(AwardTypeEnum.MOST_WORKOUTS_WEEK.toString())) {
            return stringBuilder.append(this.mContext.getString(R.string.awards_most)).toString();
        }
        if (award.getmAwardType().getmName().equals(AwardTypeEnum.FIRST_WORKOUT.toString())) {
            return stringBuilder.append(this.mContext.getString(R.string.awards_first)).toString();
        }
        if (award.getmAwardType().getmName().equals(AwardTypeEnum.BEST_WORKOUT.toString())) {
            return stringBuilder.append(this.mContext.getString(R.string.awards_best)).toString();
        }
        return "";
    }

    public String getAwardDetailItemValue(AwardDetailItemViewHolder holder, Award award) {
        StringBuilder stringBuilder = new StringBuilder();
        if (award.getmAwardType().getmName().equals(AwardTypeEnum.MOST_CALORIES_WORKOUT.toString())) {
            return stringBuilder.append(award.getmWorkout().getmCalories()).append(this.mContext.getString(R.string.awards_cal)).toString();
        }
        if (award.getmAwardType().getmName().equals(AwardTypeEnum.HIGHEST_AVG_CALORIE.toString())) {
            return stringBuilder.append(Util.getAvgCalorieBurnRateAsStringOneDecimal(award.getmWorkout())).append(Constants.EMPTY_SPACE).append(this.mContext.getString(R.string.awards_calories_min)).toString();
        }
        if (award.getmAwardType().getmName().equals(AwardTypeEnum.HIGHEST_AVG_SPEED.toString())) {
            return stringBuilder.append(Util.getAwardSpeedValueDependingOnCurrentUnit(this.mContext, this.mUnit, award)).toString();
        }
        if (award.getmAwardType().getmName().equals(AwardTypeEnum.LONGEST_DISTANCE.toString())) {
            return stringBuilder.append(Util.getAwardDistanceValueDependingOnCurrentUnit(this.mContext, this.mUnit, award)).toString();
        }
        if (award.getmAwardType().getmName().equals(AwardTypeEnum.LONGEST_WORKOUT.toString())) {
            return Util.convertSecondsToDuration((long) award.getmWorkout().getmElapsedTime(), true);
        }
        if (award.getmAwardType().getmName().equals(AwardTypeEnum.MOST_DISTANCE_PER_WEEK.toString())) {
            return getMostDistanceInWeek(award);
        }
        if (award.getmAwardType().getmName().equals(AwardTypeEnum.MOST_WORKOUTS_WEEK.toString()) || award.getmAwardType().getmName().equals(AwardTypeEnum.THREE_OR_MORE_WORKOUTS_IN_A_WEEK.toString())) {
            return stringBuilder.append(getAmountWorkoutsInWeek(award.getmDate())).append(this.mContext.getString(R.string.awards_times_label)).toString();
        }
        return "";
    }

    public String getAwardTypeValue(Award award) {
        StringBuilder stringBuilder = new StringBuilder();
        if (award.getmAwardType().getmName().equals(AwardTypeEnum.MOST_CALORIES_WORKOUT.toString())) {
            return stringBuilder.append(award.getmWorkout().getmCalories()).append(this.mContext.getString(R.string.awards_cal)).append(Constants.HYPHEN).append(award.getmDate().toString(this.mDateFormatter)).toString();
        }
        if (award.getmAwardType().getmName().equals(AwardTypeEnum.HIGHEST_AVG_CALORIE.toString())) {
            return stringBuilder.append(Util.getAvgCalorieBurnRateAsStringOneDecimal(award.getmWorkout())).append(this.mContext.getString(R.string.awards_cal_avg)).append(Constants.HYPHEN).append(award.getmDate().toString(this.mDateFormatter)).toString();
        }
        if (award.getmAwardType().getmName().equals(AwardTypeEnum.LONGEST_DISTANCE.toString())) {
            return stringBuilder.append(Util.getAwardDistanceValueDependingOnCurrentUnit(this.mContext, this.mUnit, award)).append(Constants.HYPHEN).append(award.getmDate().toString(this.mDateFormatter)).toString();
        }
        if (award.getmAwardType().getmName().equals(AwardTypeEnum.MOST_DISTANCE_PER_WEEK.toString())) {
            return stringBuilder.append(getMostDistanceInWeek(award)).append(Constants.HYPHEN).append(award.getmDate().toString(this.mDateFormatter)).toString();
        }
        if (award.getmAwardType().getmName().equals(AwardTypeEnum.HIGHEST_AVG_SPEED.toString())) {
            return stringBuilder.append(Util.getAwardSpeedValueDependingOnCurrentUnit(this.mContext, this.mUnit, award)).append(Constants.HYPHEN).append(award.getmDate().toString(this.mDateFormatter)).toString();
        }
        if (award.getmAwardType().getmName().equals(AwardTypeEnum.THREE_OR_MORE_WORKOUTS_IN_A_WEEK.toString()) || award.getmAwardType().getmName().equals(AwardTypeEnum.FIRST_WORKOUT.toString())) {
            return award.getmDate().toString(this.mDateFormatter);
        }
        if (award.getmAwardType().getmName().equals(AwardTypeEnum.MOST_WORKOUTS_WEEK.toString())) {
            return stringBuilder.append(getAmountWorkoutsInWeek(award.getmDate())).append(this.mContext.getString(R.string.awards_times_label)).append(Constants.HYPHEN).append(award.getmDate().toString(this.mDateFormatter)).toString();
        }
        if (award.getmAwardType().getmName().equals(AwardTypeEnum.BEST_WORKOUT.toString())) {
            return stringBuilder.append(this.mContext.getString(R.string.awards, new Object[]{Integer.valueOf(award.getmWorkout().getmAwards().size() - 1)})).append(Constants.HYPHEN).append(award.getmDate().toString(this.mDateFormatter)).toString();
        } else if (award.getmAwardType().getmName().equals(AwardTypeEnum.LONGEST_WORKOUT.toString())) {
            return stringBuilder.append(Util.convertSecondsToDuration((long) award.getmWorkout().getmElapsedTime(), true)).append(Constants.HYPHEN).append(award.getmDate().toString(this.mDateFormatter)).toString();
        } else {
            return "";
        }
    }

    public int getAmountWorkoutsInWeek(DateTime dateTime) {
        return this.mWeekDaoHelper.getWeek(dateTime).getmTotalWorkouts();
    }

    public String getMostDistanceInWeek(Award award) {
        return Util.getWeekDistanceValueDependingOnCurrentUnit(this.mContext, this.mUnit, this.mWeekDaoHelper.getWeek(award.getmDate()));
    }
}
