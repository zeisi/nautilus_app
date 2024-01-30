package com.nautilus.omni.appmodules.home.presenter;

import com.nautilus.omni.appmodules.home.view.ThisWeekSectionFragmentContract;

public interface ThisWeekPresenterContract {
    void loadFragment(int i);

    void setiThisWeekSectionFragment(ThisWeekSectionFragmentContract thisWeekSectionFragmentContract);
}
