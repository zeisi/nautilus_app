package com.nautilus.omni.appmodules.home.view;

import com.nautilus.omni.model.dto.Achievement;
import com.nautilus.omni.model.dto.Award;
import java.util.List;

public interface AchievementFragmentContract {
    void setAwardsAndAchievements(List<Award> list, List<Achievement> list2, int i);

    void setCaloriesBurnedPerWorkout(String str);
}
