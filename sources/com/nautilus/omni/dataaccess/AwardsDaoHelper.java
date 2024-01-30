package com.nautilus.omni.dataaccess;

import android.content.Context;
import android.content.SharedPreferences;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.nautilus.omni.app.Preferences;
import com.nautilus.omni.appmodules.awards.AwardTypeEnum;
import com.nautilus.omni.model.dto.Award;
import com.nautilus.omni.model.dto.AwardType;
import com.nautilus.omni.model.dto.Workout;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.joda.time.DateTime;

public class AwardsDaoHelper {
    private Dao<Award, Integer> mAwardsDao;
    private Dao<AwardType, Integer> mAwardsTypeDao;
    private Context mContext;

    public AwardsDaoHelper(Context context, Dao<Award, Integer> awardsDao, Dao<AwardType, Integer> awardsTypeDao) {
        this.mAwardsDao = awardsDao;
        this.mAwardsTypeDao = awardsTypeDao;
        this.mContext = context;
    }

    public List<Award> getAllAwards() {
        List<Award> awardsList = new ArrayList<>();
        try {
            return this.mAwardsDao.queryBuilder().orderBy(Award.DATE, false).query();
        } catch (SQLException e) {
            e.printStackTrace();
            return awardsList;
        }
    }

    public Award getFirstAward() {
        Award award = new Award();
        try {
            return this.mAwardsDao.queryBuilder().orderBy(Award.DATE, false).queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
            return award;
        }
    }

    public List<Award> getAllAwards(String awardType) {
        List<Award> awardsList = new ArrayList<>();
        try {
            List<Award> awardsList2 = new ArrayList<>(this.mAwardsTypeDao.queryBuilder().where().eq("name", awardType).query().get(0).getmAwards());
            try {
                sortAwardListInDescendingOrder(awardsList2);
                return awardsList2;
            } catch (SQLException e) {
                e = e;
                awardsList = awardsList2;
                e.printStackTrace();
                return awardsList;
            }
        } catch (SQLException e2) {
            e = e2;
            e.printStackTrace();
            return awardsList;
        }
    }

    public void sortAwardListInDescendingOrder(List<Award> awardsList) {
        Collections.sort(awardsList, new Comparator<Award>() {
            public int compare(Award w1, Award w2) {
                if (w1.getmId() < w2.getmId()) {
                    return 1;
                }
                return -1;
            }
        });
    }

    public List<Object> getAllAvailableAwards(int unit) {
        List<Object> awardsList = new ArrayList<>();
        for (AwardType awardType : getAwardTypesList(unit)) {
            ArrayList<Award> currentAwardList = new ArrayList<>(awardType.getmAwards());
            if (currentAwardList.size() > 0) {
                awardsList.add(currentAwardList.get(currentAwardList.size() - 1));
            } else {
                awardsList.add(awardType);
            }
        }
        return awardsList;
    }

    private List<AwardType> getAwardTypesList(int unit) {
        List<AwardType> awardTypesList = new ArrayList<>();
        try {
            return this.mAwardsTypeDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return awardTypesList;
        }
    }

    public void createNewAward(AwardType awardType, Workout workout) {
        Award award = new Award();
        award.setmAwardType(awardType);
        award.setmValue("");
        award.setmWorkout(workout);
        award.setmDate(workout.getmWorkoutDate());
        try {
            this.mAwardsDao.create(award);
            saveWonAwardsState();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateAward(Workout workout, Award award) {
        award.setmWorkout(workout);
        award.setmDate(workout.getmWorkoutDate());
        try {
            this.mAwardsDao.update(award);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateAward(Award award) {
        try {
            this.mAwardsDao.update(award);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveWonAwardsState() {
        Context context = this.mContext;
        Context context2 = this.mContext;
        SharedPreferences.Editor editor = context.getSharedPreferences(Preferences.OMNI_TRAINER_PREFERENCES, 0).edit();
        editor.putBoolean(Preferences.WON_AWARDS, true);
        editor.commit();
    }

    public Award getLastAward(int unit) {
        try {
            return this.mAwardsDao.queryBuilder().orderBy(Award.DATE, false).queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Award getMostRecentAward(AwardTypeEnum awardType) {
        try {
            QueryBuilder<Award, Integer> awardQueryBuilder = this.mAwardsDao.queryBuilder();
            awardQueryBuilder.orderBy(Award.DATE, false).where().eq(Award.AWARD_TYPE, this.mAwardsTypeDao.queryBuilder().where().eq("name", awardType.toString()).query().get(0));
            return this.mAwardsDao.queryForFirst(awardQueryBuilder.prepare());
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteAwards(DateTime awardDate, AwardTypeEnum awardType) {
        try {
            this.mAwardsDao.delete(this.mAwardsDao.queryBuilder().where().gt(Award.DATE, awardDate).and().eq(Award.AWARD_TYPE, this.mAwardsTypeDao.queryBuilder().where().eq("name", awardType.toString()).query().get(0)).query());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteBestAward(Workout workout) {
        for (Award award : workout.getmAwards()) {
            if (award.getmAwardType().getmName().equals(AwardTypeEnum.BEST_WORKOUT.toString())) {
                workout.getmAwards().remove(award);
                try {
                    this.mAwardsDao.delete(award);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public AwardType getAwardTypeByName(String awardTypeName) {
        try {
            return this.mAwardsTypeDao.queryBuilder().where().eq("name", awardTypeName).queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
