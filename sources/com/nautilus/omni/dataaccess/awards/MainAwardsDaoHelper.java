package com.nautilus.omni.dataaccess.awards;

import android.content.Context;
import android.content.res.TypedArray;
import com.j256.ormlite.dao.Dao;
import com.nautilus.omni.R;
import com.nautilus.omni.model.dto.Award;
import com.nautilus.omni.model.dto.AwardType;
import com.nautilus.omni.util.ResourceHelper;
import java.sql.SQLException;
import java.util.List;

public class MainAwardsDaoHelper {
    public static final int BEST_WORKOUT_AWARD = 9;
    public static final int FIRST_WORKOUT_AWARD = 8;
    public static final int HIGHEST_AVG_CALORIES_AWARD = 1;
    public static final int HIGHEST_SPEED_AWARD = 7;
    public static final int LONGEST_DISTANCE_AWARD = 5;
    public static final int LONGEST_WORKOUT_AWARD = 4;
    public static final int MOST_CALORIES_AWARD = 0;
    public static final int MOST_DISTANCE_AWARD = 6;
    public static final int MOST_WORKOUTS_PER_WEEK_AWARD = 2;
    public static final int THREE_WORKOUTS_IN_WEEK_AWARD = 3;
    private Dao<Award, Integer> mAwardsDao;
    private Dao<AwardType, Integer> mAwardsTypeDao;
    private Context mContext;

    public MainAwardsDaoHelper(Context context, Dao<Award, Integer> awardsDao, Dao<AwardType, Integer> awardsTypeDao) {
        this.mAwardsDao = awardsDao;
        this.mAwardsTypeDao = awardsTypeDao;
        this.mContext = context;
    }

    public void createAllAwardTypes() {
        try {
            List<TypedArray> awardsArray = ResourceHelper.getMultiTypedArray(this.mContext, this.mContext.getString(R.string.awards_key));
            int currentAwardsAmount = (int) this.mAwardsTypeDao.countOf();
            int newAmountOfAwards = awardsArray.size() - currentAwardsAmount;
            if (currentAwardsAmount == 0) {
                loadAwards(0, awardsArray);
            } else if (newAmountOfAwards > 0) {
                loadAwards(awardsArray.size() - newAmountOfAwards, awardsArray);
            } else {
                updateAwardResources(0, awardsArray);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadAwards(int initPosition, List<TypedArray> awardsArray) throws SQLException {
        for (int counter = initPosition; counter < awardsArray.size(); counter++) {
            TypedArray item = awardsArray.get(counter);
            AwardType awardType = new AwardType();
            awardType.setmName(item.getString(0));
            awardType.setmDescription(item.getString(1));
            awardType.setmCircle(item.getResourceId(2, -1));
            awardType.setmSmallImage(item.getResourceId(3, -1));
            awardType.setmBigImage(item.getResourceId(4, -1));
            awardType.setmColor(item.getResourceId(5, -1));
            awardType.setmMediumImage(item.getResourceId(6, -1));
            this.mAwardsTypeDao.create(awardType);
        }
    }

    private void updateAwardResources(int initPosition, List<TypedArray> awardsArray) {
        try {
            for (AwardType awardType : this.mAwardsTypeDao.queryBuilder().orderBy("id", true).query()) {
                executeAwardResourcesUpdate(awardType, initPosition, awardsArray);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void executeAwardResourcesUpdate(AwardType awardType, int initPosition, List<TypedArray> awardsArray) {
        int awardPosition = initPosition;
        while (awardPosition < awardsArray.size()) {
            try {
                TypedArray item = awardsArray.get(awardPosition);
                if (awardType.getmName().equals(item.getString(0)) || isEqualToOldAwardName(awardPosition, awardType.getmName())) {
                    awardType.setmName(item.getString(0));
                    awardType.setmDescription(item.getString(1));
                    awardType.setmCircle(item.getResourceId(2, -1));
                    awardType.setmSmallImage(item.getResourceId(3, -1));
                    awardType.setmBigImage(item.getResourceId(4, -1));
                    awardType.setmColor(item.getResourceId(5, -1));
                    awardType.setmMediumImage(item.getResourceId(6, -1));
                    this.mAwardsTypeDao.update(awardType);
                }
                awardPosition++;
            } catch (SQLException e) {
                e.printStackTrace();
                return;
            }
        }
    }

    private boolean isEqualToOldAwardName(int awardPosition, String awardName) {
        if (awardPosition == 0) {
            return awardName.equals(this.mContext.getString(R.string.old_most_calories_award_name));
        }
        if (awardPosition == 1) {
            return awardName.equals(this.mContext.getString(R.string.old_highest_avg_calories_award_name));
        }
        if (awardPosition == 2) {
            return awardName.equals(this.mContext.getString(R.string.old_most_workouts_week_award_name));
        }
        if (awardPosition == 3) {
            return awardName.equals(this.mContext.getString(R.string.old_three_workouts_in_week_award_name));
        }
        if (awardPosition == 4) {
            return awardName.equals(this.mContext.getString(R.string.old_longest_workout_award_name));
        }
        if (awardPosition == 5) {
            return awardName.equals(this.mContext.getString(R.string.old_longest_distance_award_name));
        }
        if (awardPosition == 6) {
            return awardName.equals(this.mContext.getString(R.string.old_most_distance_award_name));
        }
        if (awardPosition == 7) {
            return awardName.equals(this.mContext.getString(R.string.old_highest_speed_award_name));
        }
        if (awardPosition == 8) {
            return awardName.equals(this.mContext.getString(R.string.old_first_workout_award_name));
        }
        if (awardPosition == 9) {
            return awardName.equals(this.mContext.getString(R.string.old_best_workout_award_name));
        }
        return false;
    }
}
