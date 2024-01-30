package com.nautilus.omni.appmodules.goals.presenter.interactors;

import android.os.AsyncTask;
import com.nautilus.omni.appmodules.goals.presenter.interactors.SaveGoalsInteractorContract;
import com.nautilus.omni.dataaccess.AchievementsDaoHelper;
import com.nautilus.omni.dataaccess.GoalsDaoHelper;
import com.nautilus.omni.dataaccess.WorkoutDaoHelper;
import com.nautilus.omni.model.dto.GoalType;
import com.nautilus.omni.model.dto.TrainingGoal;
import com.nautilus.omni.model.dto.User;
import com.nautilus.omni.model.dto.Workout;
import com.nautilus.omni.util.DateUtil;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.joda.time.DateTime;

public class SaveGoalsInteractor implements SaveGoalsInteractorContract {
    private AchievementsDaoHelper mAchievementDaoHelper;
    /* access modifiers changed from: private */
    public TrainingGoal mCurrentCaloriePerWorkoutGoal;
    private DateTime mCurrentDate = new DateTime();
    private User mCurrentUser;
    /* access modifiers changed from: private */
    public TrainingGoal mCurrentWorkoutTimePerWorkoutGoal;
    /* access modifiers changed from: private */
    public TrainingGoal mCurrentWorkoutsPerWeekGoal;
    private GoalsDaoHelper mGoalsDaoHelper;
    /* access modifiers changed from: private */
    public SaveGoalsInteractorContract.OnGoalsSavedListener mOnGoalsSavedListener;
    private WorkoutDaoHelper mWorkoutDaoHelper;
    private List<Workout> mWorkoutsList = new ArrayList();

    @Inject
    public SaveGoalsInteractor(AchievementsDaoHelper achievementDaoHelper, GoalsDaoHelper goalsDaoHelper, WorkoutDaoHelper workoutDaoHelper) {
        this.mAchievementDaoHelper = achievementDaoHelper;
        this.mWorkoutDaoHelper = workoutDaoHelper;
        this.mGoalsDaoHelper = goalsDaoHelper;
    }

    public void setCurrentUser(User mCurrentUser2) {
        this.mCurrentUser = mCurrentUser2;
    }

    public void initWorkoutsList() {
        this.mWorkoutsList = new ArrayList();
    }

    public void saveGoals(SaveGoalsInteractorContract.OnGoalsSavedListener onGoalsSavedListener, String caloriesSelectedValue, String workoutNumberSelectedValue, String workoutTimeSelectedValue) {
        this.mOnGoalsSavedListener = onGoalsSavedListener;
        new SaveGoalsInDataBase().execute(new String[]{caloriesSelectedValue, workoutNumberSelectedValue, workoutTimeSelectedValue});
    }

    private class SaveGoalsInDataBase extends AsyncTask<String, String, Boolean> {
        public static final int CALORIES_GOAL_INDEX = 0;
        public static final int NUMBER_GOAL_INDEX = 1;
        public static final int TIME_GOAL_INDEX = 2;

        private SaveGoalsInDataBase() {
        }

        /* access modifiers changed from: protected */
        public Boolean doInBackground(String... params) {
            return Boolean.valueOf(SaveGoalsInteractor.this.saveGoalsData(params[0], params[1], params[2]));
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            SaveGoalsInteractor.this.mOnGoalsSavedListener.onGoalsSaved(SaveGoalsInteractor.this.mCurrentCaloriePerWorkoutGoal, SaveGoalsInteractor.this.mCurrentWorkoutsPerWeekGoal, SaveGoalsInteractor.this.mCurrentWorkoutTimePerWorkoutGoal);
        }
    }

    public void disableGoals() {
        this.mAchievementDaoHelper.deleteAchievements(this.mCurrentDate);
    }

    public boolean saveGoalsData(String caloriesPerWorkoutGoal, String workoutsPerWeekGoal, String timePerWorkoutGoal) {
        getWorkoutsFromCurrentWeek();
        return saveGoal(caloriesPerWorkoutGoal, this.mCurrentCaloriePerWorkoutGoal, GoalType.CALORIES_PER_WORKOUT) || saveGoal(workoutsPerWeekGoal, this.mCurrentWorkoutsPerWeekGoal, GoalType.WORKOUTS_PER_WEEK) || saveGoal(timePerWorkoutGoal, this.mCurrentWorkoutTimePerWorkoutGoal, GoalType.TIME_PER_WORKOUT);
    }

    private boolean saveGoal(String goalSelectedValue, TrainingGoal currentGoal, GoalType goalType) {
        if (currentGoal == null) {
            try {
                createNewGoal(goalSelectedValue, currentGoal, goalType);
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            updateGoalNew(goalSelectedValue, currentGoal);
        }
        return true;
    }

    private void createNewGoal(String goalSelectedValue, TrainingGoal currentGoal, GoalType goalType) throws SQLException {
        TrainingGoal trainingGoal = buildNewTrainingGoal(goalType, goalSelectedValue);
        this.mGoalsDaoHelper.createGoal(trainingGoal);
        TrainingGoal currentGoal2 = trainingGoal;
        if (goalType.equals(GoalType.CALORIES_PER_WORKOUT)) {
            this.mCurrentCaloriePerWorkoutGoal = currentGoal2;
        } else if (goalType.equals(GoalType.WORKOUTS_PER_WEEK)) {
            this.mCurrentWorkoutsPerWeekGoal = currentGoal2;
        } else {
            this.mCurrentWorkoutTimePerWorkoutGoal = currentGoal2;
        }
        updateGoalNew(goalSelectedValue, currentGoal2);
    }

    private TrainingGoal buildNewTrainingGoal(GoalType goalType, String goalValue) {
        TrainingGoal trainingGoal = new TrainingGoal();
        trainingGoal.setmAchieved(false);
        trainingGoal.setmGoalType(goalType);
        trainingGoal.setmGoalValue(goalValue);
        trainingGoal.setmStartDate(DateUtil.getFirstDayOfCurrentWeek());
        trainingGoal.setmEndDate(DateUtil.getLastDayOfCurrentWeek());
        trainingGoal.setmUser(this.mCurrentUser);
        trainingGoal.setmAccumulatedGoalValue(0);
        return trainingGoal;
    }

    private void updateGoalNew(String goalSelectedValue, TrainingGoal currentGoal) throws SQLException {
        if (!DateUtil.isDateBetween(currentGoal.getmStartDate(), this.mCurrentDate, currentGoal.getmEndDate())) {
            return;
        }
        if (currentGoal.getmGoalType().equals(GoalType.CALORIES_PER_WORKOUT) || currentGoal.getmGoalType().equals(GoalType.TIME_PER_WORKOUT)) {
            handleCalorieAndTimeGoals(goalSelectedValue, currentGoal);
        } else {
            handleWorkoutsPerWeekGoal(goalSelectedValue, currentGoal);
        }
    }

    private void handleCalorieAndTimeGoals(String goalSelectedValue, TrainingGoal currentGoal) throws SQLException {
        updateGoalsWhenCurrentlyAchieved(goalSelectedValue, currentGoal);
    }

    private void handleWorkoutsPerWeekGoal(String goalSelectedValue, TrainingGoal currentGoal) throws SQLException {
        if (currentGoal.ismAchieved()) {
            updateGoalsWhenCurrentlyAchieved(goalSelectedValue, currentGoal);
        } else {
            updateGoalsWhenNotCurrentlyAchieved(goalSelectedValue, currentGoal);
        }
    }

    private void updateGoalsWhenNotCurrentlyAchieved(String goalSelectedValue, TrainingGoal currentGoal) throws SQLException {
        if (!isLowerGoal(goalSelectedValue, currentGoal.getmGoalType())) {
            makeGoalAchievedUpdate(goalSelectedValue, currentGoal, false);
        } else if (isNewGoalPreviouslyAchieved(goalSelectedValue, currentGoal)) {
            makeGoalAchievedUpdate(goalSelectedValue, currentGoal, true);
        } else {
            makeGoalAchievedUpdate(goalSelectedValue, currentGoal, false);
        }
    }

    private void updateGoalsWhenCurrentlyAchieved(String goalSelectedValue, TrainingGoal currentGoal) throws SQLException {
        this.mAchievementDaoHelper.deleteExistentGoalAchievements(currentGoal, this.mCurrentUser);
        if (isNewGoalPreviouslyAchieved(goalSelectedValue, currentGoal)) {
            makeGoalAchievedUpdate(goalSelectedValue, currentGoal, true);
        } else {
            makeGoalAchievedUpdate(goalSelectedValue, currentGoal, false);
        }
    }

    private void makeGoalAchievedUpdate(String caloriesSelectedValue, TrainingGoal currentGoal, boolean achieved) throws SQLException {
        currentGoal.setmGoalValue(caloriesSelectedValue);
        currentGoal.setmAchieved(achieved);
        this.mGoalsDaoHelper.updateGoal(currentGoal);
    }

    private boolean isNewGoalPreviouslyAchieved(String goalSelectedValue, TrainingGoal currentGoal) throws SQLException {
        if (currentGoal.getmGoalType().equals(GoalType.CALORIES_PER_WORKOUT)) {
            return updateIfNewCalorieGoalPreviouslyAchieved(goalSelectedValue, currentGoal);
        }
        if (currentGoal.getmGoalType().equals(GoalType.WORKOUTS_PER_WEEK)) {
            return updateIfNewWorkoutsPerWeekGoalPreviouslyAchieved(goalSelectedValue, currentGoal);
        }
        return updateIfNewTimePerWorkoutGoalPreviouslyAchieved(goalSelectedValue, currentGoal);
    }

    private boolean updateIfNewCalorieGoalPreviouslyAchieved(String goalSelectedValue, TrainingGoal currentGoal) throws SQLException {
        boolean result = false;
        int goalCaloriesCurrentValue = Integer.parseInt(goalSelectedValue);
        for (Workout workout : this.mWorkoutsList) {
            if (workout.getmCalories() >= goalCaloriesCurrentValue) {
                this.mAchievementDaoHelper.createNewGoalAchievement(workout, this.mCurrentUser, currentGoal, goalSelectedValue);
                result = true;
            }
        }
        currentGoal.setmAccumulatedGoalValue(0);
        this.mGoalsDaoHelper.updateGoal(currentGoal);
        return result;
    }

    private boolean updateIfNewWorkoutsPerWeekGoalPreviouslyAchieved(String goalSelectedValue, TrainingGoal currentGoal) throws SQLException {
        boolean result = false;
        int goalWorkoutNumberCurrentValue = Integer.parseInt(goalSelectedValue);
        int goalCounterWorkoutNumber = 0;
        for (Workout workout : this.mWorkoutsList) {
            goalCounterWorkoutNumber++;
            if (goalCounterWorkoutNumber >= goalWorkoutNumberCurrentValue && !result) {
                this.mAchievementDaoHelper.createNewGoalAchievement(workout, this.mCurrentUser, currentGoal, goalSelectedValue);
                result = true;
            }
        }
        currentGoal.setmAccumulatedGoalValue(goalCounterWorkoutNumber);
        this.mGoalsDaoHelper.updateGoal(currentGoal);
        return result;
    }

    private boolean updateIfNewTimePerWorkoutGoalPreviouslyAchieved(String goalSelectedValue, TrainingGoal currentGoal) throws SQLException {
        int selectedMinutes = Integer.parseInt(goalSelectedValue.substring(0, 2));
        int totalSelectedSeconds = (selectedMinutes * 60) + Integer.parseInt(goalSelectedValue.substring(3, 5));
        if (this.mWorkoutsList.size() > 0) {
            return checkIfTimeGoalAchieved(totalSelectedSeconds, goalSelectedValue, currentGoal);
        }
        return false;
    }

    private boolean checkIfTimeGoalAchieved(int totalSelectedSeconds, String goalSelectedValue, TrainingGoal currentGoal) throws SQLException {
        boolean result = false;
        for (Workout workout : this.mWorkoutsList) {
            if (workout.getmElapsedTime() >= totalSelectedSeconds) {
                this.mAchievementDaoHelper.createNewGoalAchievement(workout, this.mCurrentUser, currentGoal, goalSelectedValue);
                result = true;
            }
        }
        return result;
    }

    private boolean isLowerGoal(String goalSelectedValue, GoalType goalType) {
        if (goalType.equals(GoalType.CALORIES_PER_WORKOUT)) {
            return isLowerCaloriesPerWorkoutGoal(goalSelectedValue);
        }
        if (goalType.equals(GoalType.WORKOUTS_PER_WEEK)) {
            return isLowerWorkoutsPerWeekGoal(goalSelectedValue);
        }
        return isLowerTimePerWorkoutGoal(goalSelectedValue);
    }

    private boolean isLowerCaloriesPerWorkoutGoal(String caloriesSelectedValue) {
        int selectedCalories = Integer.parseInt(caloriesSelectedValue);
        int previousCalories = Integer.parseInt(this.mCurrentCaloriePerWorkoutGoal.getmGoalValue());
        return selectedCalories < previousCalories || selectedCalories == previousCalories;
    }

    private boolean isLowerWorkoutsPerWeekGoal(String workoutNumberSelectedValue) {
        int selectedWorkoutNumber = Integer.parseInt(workoutNumberSelectedValue);
        int previousWorkoutNumber = Integer.parseInt(this.mCurrentWorkoutsPerWeekGoal.getmGoalValue());
        return selectedWorkoutNumber < previousWorkoutNumber || selectedWorkoutNumber == previousWorkoutNumber;
    }

    private boolean isLowerTimePerWorkoutGoal(String workoutTimeSelectedValue) {
        int selectedHours = Integer.parseInt(workoutTimeSelectedValue.substring(0, 2));
        int selectedMinutes = Integer.parseInt(workoutTimeSelectedValue.substring(3));
        int previousHours = Integer.parseInt(this.mCurrentWorkoutTimePerWorkoutGoal.getmGoalValue().substring(0, 2));
        int previousMinutes = Integer.parseInt(this.mCurrentWorkoutTimePerWorkoutGoal.getmGoalValue().substring(3));
        if (selectedHours < previousHours) {
            return true;
        }
        if (selectedHours > previousHours) {
            return false;
        }
        if (selectedMinutes < previousMinutes || selectedMinutes == previousMinutes) {
            return true;
        }
        return false;
    }

    private void getWorkoutsFromCurrentWeek() {
        if (this.mWorkoutsList.size() == 0) {
            this.mWorkoutsList = this.mWorkoutDaoHelper.getWorkoutsFromSpecificWeek(this.mCurrentDate, true);
        }
    }

    public void setmCurrentCaloriePerWorkoutGoal(TrainingGoal mCurrentCaloriePerWorkoutGoal2) {
        this.mCurrentCaloriePerWorkoutGoal = mCurrentCaloriePerWorkoutGoal2;
    }

    public void setmCurrentWorkoutsPerWeekGoal(TrainingGoal mCurrentWorkoutsPerWeekGoal2) {
        this.mCurrentWorkoutsPerWeekGoal = mCurrentWorkoutsPerWeekGoal2;
    }

    public void setmCurrentWorkoutTimePerWorkoutGoal(TrainingGoal mCurrentWorkoutTimePerWorkoutGoal2) {
        this.mCurrentWorkoutTimePerWorkoutGoal = mCurrentWorkoutTimePerWorkoutGoal2;
    }
}
