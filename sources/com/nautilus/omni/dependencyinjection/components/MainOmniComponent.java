package com.nautilus.omni.dependencyinjection.components;

import com.nautilus.omni.appmodules.awards.view.AwardsFragment;
import com.nautilus.omni.appmodules.device.view.DeviceFragment;
import com.nautilus.omni.appmodules.goals.view.GoalsFragment;
import com.nautilus.omni.appmodules.home.view.AchievementsFragment;
import com.nautilus.omni.appmodules.home.view.GaugeFragment;
import com.nautilus.omni.appmodules.home.view.HomeFragment;
import com.nautilus.omni.appmodules.home.view.ThisWeekSectionFragment;
import com.nautilus.omni.appmodules.journal.view.JournalFragment;
import com.nautilus.omni.appmodules.journal.view.day.JournalDayFragment;
import com.nautilus.omni.appmodules.journal.view.week.JournalWeekFragment;
import com.nautilus.omni.appmodules.journal.view.year.JournalYearFragment;
import com.nautilus.omni.appmodules.mainactivity.view.MainActivity;
import com.nautilus.omni.dependencyinjection.modules.main.AwardsModule;
import com.nautilus.omni.dependencyinjection.modules.main.DeviceModule;
import com.nautilus.omni.dependencyinjection.modules.main.GoalsModule;
import com.nautilus.omni.dependencyinjection.modules.main.HomeModule;
import com.nautilus.omni.dependencyinjection.modules.main.JournalModule;
import com.nautilus.omni.dependencyinjection.modules.main.MainModule;
import com.nautilus.omni.dependencyinjection.scopes.ActivityScope;
import dagger.Component;

@ActivityScope
@Component(dependencies = {AppComponent.class}, modules = {GoalsModule.class, HomeModule.class, AwardsModule.class, DeviceModule.class, JournalModule.class, MainModule.class})
public interface MainOmniComponent {
    AwardsFragment getAwardsFragment();

    DeviceFragment getDeviceFragment();

    GoalsFragment getGoalFragment();

    HomeFragment getHomeFragment();

    JournalFragment getJournalFragment();

    void inject(AwardsFragment awardsFragment);

    void inject(DeviceFragment deviceFragment);

    void inject(GoalsFragment goalsFragment);

    void inject(AchievementsFragment achievementsFragment);

    void inject(GaugeFragment gaugeFragment);

    void inject(HomeFragment homeFragment);

    void inject(ThisWeekSectionFragment thisWeekSectionFragment);

    void inject(JournalDayFragment journalDayFragment);

    void inject(JournalWeekFragment journalWeekFragment);

    void inject(JournalYearFragment journalYearFragment);

    void inject(MainActivity mainActivity);
}
