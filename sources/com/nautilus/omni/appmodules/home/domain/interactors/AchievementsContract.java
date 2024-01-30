package com.nautilus.omni.appmodules.home.domain.interactors;

import com.nautilus.omni.model.dto.Achievement;
import com.nautilus.omni.model.dto.Award;
import com.nautilus.omni.model.dto.Week;
import com.nautilus.omni.model.dto.Workout;
import java.util.List;

public interface AchievementsContract {

    public interface OnResult {
        void onGetAllAchievementsAndAwardsSuccess(List<Award> list, List<Achievement> list2);
    }

    void getAllAchievementsAndAwards(OnResult onResult);

    Workout getHighestAvgCalorieBurnWorkout(int i);

    Workout getHighestAvgSpeedWorkout(int i);

    Workout getHighestCalorieWorkout(int i);

    Workout getLongestDistanceWorkout(int i);

    Workout getLongestWorkout(int i);

    Week getWeekWithMostCalories();

    Week getWeekWithMostDistance();

    Week getWeekWithMostSpeed();

    Week getWeekWithMostTime();

    Week getWeekWithMostWorkouts();
}
