package com.nautilus.omni.appmodules.home.view;

import com.nautilus.omni.model.dto.Achievement;
import com.nautilus.omni.model.dto.Workout;

public interface HomeFragmentContract {
    void achievementsSectionLastAchievementIsAvailable(Achievement achievement);

    void achievementsSectionLastAwardAndAchievementNoAvailable();

    void achievementsSectionLastAwardIsAvailable(int i, String str, String str2);

    void refreshData();

    void setPagerAdapter(Workout workout);

    void showDetailView();
}
