package com.nautilus.omni.appmodules.home.presenter;

import com.nautilus.omni.appmodules.home.view.HomeFragmentContract;

public interface HomePresenterContract {
    void checkIsAvailableAchievements();

    int getThisWeekLabel();

    boolean isGoalsEnable();

    void loadAchievementsSection();

    void loadPagerAdapters();

    void onResume();

    void setHomeFragment(HomeFragmentContract homeFragmentContract);

    void updateThisWeekLabel();
}
