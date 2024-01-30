package com.nautilus.omni.appmodules.home.domain.helpers;

import android.content.Context;
import com.nautilus.omni.model.dto.TrainingGoal;
import com.nautilus.omni.model.dto.Week;
import com.nautilus.omni.model.dto.Workout;
import java.util.List;

public interface IThisWeekHelper {

    public interface OnHelperFinishListener {
        void onDataComparedToTimeGoalSuccess(int i, int i2, int i3);

        void onDataComparedToWorkoutsPerWeekGoalSuccess(int i, int i2);

        void onLoadDataComparedToCalorieGoalSuccess(int i, int i2);

        void onLoadInfoComparedWithPreviousWeek(String str, String str2, String str3);
    }

    void loadDataComparedToCalorieGoal(TrainingGoal trainingGoal, TrainingGoal trainingGoal2, Week week, OnHelperFinishListener onHelperFinishListener);

    void loadDataComparedToTimeGoal(TrainingGoal trainingGoal, TrainingGoal trainingGoal2, Week week, OnHelperFinishListener onHelperFinishListener);

    void loadDataComparedToWorkoutsPerWeekGoal(TrainingGoal trainingGoal, Week week, OnHelperFinishListener onHelperFinishListener);

    void loadHeartRateInfo(Context context, List<Workout> list, List<Workout> list2, OnHelperFinishListener onHelperFinishListener);

    void loadInfo(int i, Week week, Week week2, Context context, int i2, OnHelperFinishListener onHelperFinishListener);

    void loadWattsInfo(Context context, List<Workout> list, List<Workout> list2, OnHelperFinishListener onHelperFinishListener);
}
