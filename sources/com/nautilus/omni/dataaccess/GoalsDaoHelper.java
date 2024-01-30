package com.nautilus.omni.dataaccess;

import com.j256.ormlite.dao.Dao;
import com.nautilus.omni.model.dto.GoalType;
import com.nautilus.omni.model.dto.TrainingGoal;
import com.nautilus.omni.model.dto.User;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;

public class GoalsDaoHelper {
    private Dao<TrainingGoal, Integer> mTrainingGoalsDao;

    public GoalsDaoHelper(Dao<TrainingGoal, Integer> trainingGoalsDao) {
        this.mTrainingGoalsDao = trainingGoalsDao;
    }

    public void createGoal(TrainingGoal goal) {
        try {
            this.mTrainingGoalsDao.createIfNotExists(goal);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateGoal(TrainingGoal goal) {
        try {
            this.mTrainingGoalsDao.update(goal);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public TrainingGoal getMostRecentGoalByType(User currentUser, GoalType goalType) {
        try {
            return this.mTrainingGoalsDao.queryBuilder().orderBy(TrainingGoal.START_DATE, false).limit((Long) 1L).where().eq("user", currentUser).and().eq("goal_type", goalType).queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<TrainingGoal> getMostRecentGoals(User currentUser) {
        List<TrainingGoal> trainingGoalsList = new ArrayList<>();
        if (currentUser == null) {
            return trainingGoalsList;
        }
        try {
            return this.mTrainingGoalsDao.queryBuilder().limit((Long) 3L).orderBy(TrainingGoal.START_DATE, false).where().eq("user", currentUser).query();
        } catch (SQLException e) {
            e.printStackTrace();
            return trainingGoalsList;
        }
    }

    public List<TrainingGoal> getTrainingGoalsFromSpecificWeek(User currentUser, DateTime weekDate) {
        List<TrainingGoal> trainingGoalsList = new ArrayList<>();
        if (currentUser == null) {
            return trainingGoalsList;
        }
        try {
            return this.mTrainingGoalsDao.queryBuilder().where().eq("user", currentUser).and().le(TrainingGoal.START_DATE, weekDate).and().ge("end_date", weekDate).query();
        } catch (SQLException e) {
            e.printStackTrace();
            return trainingGoalsList;
        }
    }

    public TrainingGoal getGoalFromSpecificWeek(User currentUser, DateTime weekDate, GoalType goalType) {
        try {
            return this.mTrainingGoalsDao.queryBuilder().where().eq("user", currentUser).and().eq("goal_type", goalType).and().le(TrainingGoal.START_DATE, weekDate).and().ge("end_date", weekDate).queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void resetGoalsToNotAchieved() {
        try {
            for (TrainingGoal trainingGoal : this.mTrainingGoalsDao.queryBuilder().where().eq(TrainingGoal.ACHIEVED, true).query()) {
                trainingGoal.setmAchieved(false);
                this.mTrainingGoalsDao.update(trainingGoal);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
