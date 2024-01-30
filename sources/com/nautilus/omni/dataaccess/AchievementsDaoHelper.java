package com.nautilus.omni.dataaccess;

import com.j256.ormlite.dao.Dao;
import com.nautilus.omni.model.dto.Achievement;
import com.nautilus.omni.model.dto.TrainingGoal;
import com.nautilus.omni.model.dto.User;
import com.nautilus.omni.model.dto.Workout;
import com.nautilus.omni.util.DateUtil;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;

public class AchievementsDaoHelper {
    private Dao<Achievement, Integer> mAchievementDao;

    public AchievementsDaoHelper(Dao<Achievement, Integer> achievementDao) {
        this.mAchievementDao = achievementDao;
    }

    public void deleteAchievements(DateTime dateOfWeek) {
        DateTime firstDateOfWeek = DateUtil.getFirstDayOftWeek(dateOfWeek);
        try {
            this.mAchievementDao.delete(this.mAchievementDao.queryBuilder().where().ge(Achievement.GOAL_ACHIEVEMENT_DATE, firstDateOfWeek).and().le(Achievement.GOAL_ACHIEVEMENT_DATE, DateUtil.getLastDayOfWeek(dateOfWeek)).query());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Achievement> getAllAchievements() {
        List<Achievement> achievementList = new ArrayList<>();
        try {
            return this.mAchievementDao.queryBuilder().query();
        } catch (SQLException e) {
            e.printStackTrace();
            return achievementList;
        }
    }

    public Achievement getLastAchievement(int i) {
        try {
            return this.mAchievementDao.queryBuilder().queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteExistentGoalAchievements(TrainingGoal currentGoal, User user) throws SQLException {
        this.mAchievementDao.delete(this.mAchievementDao.queryBuilder().where().eq("goal_type", currentGoal.getmGoalType()).and().eq("user", user).and().ge(Achievement.GOAL_ACHIEVEMENT_DATE, currentGoal.getmStartDate()).and().le(Achievement.GOAL_ACHIEVEMENT_DATE, currentGoal.getmEndDate()).query());
    }

    public void createNewGoalAchievement(Workout workout, User user, TrainingGoal currentGoal, String goalSelectedValue) {
        try {
            Achievement achievement = new Achievement();
            achievement.setmGoalType(currentGoal.getmGoalType());
            achievement.setmUser(user);
            achievement.setmGoalAchievedValue(goalSelectedValue);
            achievement.setmGoalAchievementDate(workout.getmWorkoutDate());
            this.mAchievementDao.create(achievement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Achievement> getMoreOldAchievements(DateTime workoutsStartDate, DateTime workoutsEndDate, User currentUser) {
        try {
            return this.mAchievementDao.queryBuilder().where().eq("user", currentUser).and().ge(Achievement.GOAL_ACHIEVEMENT_DATE, workoutsStartDate).and().le(Achievement.GOAL_ACHIEVEMENT_DATE, workoutsEndDate).query();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Achievement> getMoreNewAchievements(DateTime workoutsStartDate, User currentUser) {
        try {
            return this.mAchievementDao.queryBuilder().where().eq("user", currentUser).and().ge(Achievement.GOAL_ACHIEVEMENT_DATE, workoutsStartDate).query();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
