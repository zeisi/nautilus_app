package com.nautilus.omni.dependencyinjection.components;

import android.content.Context;
import android.content.SharedPreferences;
import com.nautilus.omni.appmodules.awards.AwardValueBuilder;
import com.nautilus.omni.appmodules.awards.interactor.AwardsInteractorContract;
import com.nautilus.omni.appmodules.awards.presenter.AwardsPresenterContract;
import com.nautilus.omni.appmodules.awards.view.AwardsFragment;
import com.nautilus.omni.appmodules.awards.view.AwardsFragment_MembersInjector;
import com.nautilus.omni.appmodules.device.presenter.DevicePresenter;
import com.nautilus.omni.appmodules.device.presenter.interactors.DeviceInteractor;
import com.nautilus.omni.appmodules.device.view.DeviceFragment;
import com.nautilus.omni.appmodules.device.view.DeviceFragment_MembersInjector;
import com.nautilus.omni.appmodules.goals.presenter.GoalsPresenter;
import com.nautilus.omni.appmodules.goals.presenter.interactors.LoadGoalsInteractor;
import com.nautilus.omni.appmodules.goals.presenter.interactors.SaveGoalsInteractor;
import com.nautilus.omni.appmodules.goals.view.GoalsFragment;
import com.nautilus.omni.appmodules.goals.view.GoalsFragment_MembersInjector;
import com.nautilus.omni.appmodules.home.domain.helpers.IGaugeHelper;
import com.nautilus.omni.appmodules.home.domain.helpers.IThisWeekHelper;
import com.nautilus.omni.appmodules.home.domain.interactors.AchievementsContract;
import com.nautilus.omni.appmodules.home.domain.interactors.GaugeInteractorContract;
import com.nautilus.omni.appmodules.home.domain.interactors.HomeInteractorContract;
import com.nautilus.omni.appmodules.home.domain.interactors.ThisWeekSectionInteractorContract;
import com.nautilus.omni.appmodules.home.presenter.AchievementsPresenterContract;
import com.nautilus.omni.appmodules.home.presenter.GaugePresenterContract;
import com.nautilus.omni.appmodules.home.presenter.HomePresenterContract;
import com.nautilus.omni.appmodules.home.presenter.ThisWeekPresenterContract;
import com.nautilus.omni.appmodules.home.view.AchievementsFragment;
import com.nautilus.omni.appmodules.home.view.AchievementsFragment_MembersInjector;
import com.nautilus.omni.appmodules.home.view.GaugeFragment;
import com.nautilus.omni.appmodules.home.view.GaugeFragment_MembersInjector;
import com.nautilus.omni.appmodules.home.view.HomeFragment;
import com.nautilus.omni.appmodules.home.view.HomeFragment_MembersInjector;
import com.nautilus.omni.appmodules.home.view.ThisWeekSectionFragment;
import com.nautilus.omni.appmodules.home.view.ThisWeekSectionFragment_MembersInjector;
import com.nautilus.omni.appmodules.journal.presenter.day.workoutlist.DayInteractor;
import com.nautilus.omni.appmodules.journal.presenter.day.workoutlist.DayPresenter;
import com.nautilus.omni.appmodules.journal.presenter.week.WeekInteractor;
import com.nautilus.omni.appmodules.journal.presenter.week.WeekPresenter;
import com.nautilus.omni.appmodules.journal.presenter.year.YearInteractor;
import com.nautilus.omni.appmodules.journal.presenter.year.YearPresenter;
import com.nautilus.omni.appmodules.journal.view.JournalFragment;
import com.nautilus.omni.appmodules.journal.view.day.JournalDayFragment;
import com.nautilus.omni.appmodules.journal.view.day.JournalDayFragment_MembersInjector;
import com.nautilus.omni.appmodules.journal.view.week.JournalWeekFragment;
import com.nautilus.omni.appmodules.journal.view.week.JournalWeekFragment_MembersInjector;
import com.nautilus.omni.appmodules.journal.view.year.JournalYearFragment;
import com.nautilus.omni.appmodules.journal.view.year.JournalYearFragment_MembersInjector;
import com.nautilus.omni.appmodules.mainactivity.presenter.GoalsReminderToastViewBuilder;
import com.nautilus.omni.appmodules.mainactivity.presenter.MainGoalsHelperInteractor;
import com.nautilus.omni.appmodules.mainactivity.presenter.MainPresenter;
import com.nautilus.omni.appmodules.mainactivity.presenter.MainPresenter_Factory;
import com.nautilus.omni.appmodules.mainactivity.view.MainActivity;
import com.nautilus.omni.appmodules.mainactivity.view.MainActivity_MembersInjector;
import com.nautilus.omni.appmodules.mainactivity.view.MainViewContract;
import com.nautilus.omni.dataaccess.AchievementsDaoHelper;
import com.nautilus.omni.dataaccess.AwardsDaoHelper;
import com.nautilus.omni.dataaccess.GoalsDaoHelper;
import com.nautilus.omni.dataaccess.ProductDaoHelper;
import com.nautilus.omni.dataaccess.UserDaoHelper;
import com.nautilus.omni.dataaccess.WeekDaoHelper;
import com.nautilus.omni.dataaccess.WorkoutDaoHelper;
import com.nautilus.omni.databasemanager.DataBaseHelper;
import com.nautilus.omni.dependencyinjection.modules.main.AwardsModule;
import com.nautilus.omni.dependencyinjection.modules.main.AwardsModule_ProvideAwardsViewFactory;
import com.nautilus.omni.dependencyinjection.modules.main.AwardsModule_ProvideIAwardsInteractorFactory;
import com.nautilus.omni.dependencyinjection.modules.main.AwardsModule_ProvideIAwardsPresenterFactory;
import com.nautilus.omni.dependencyinjection.modules.main.DeviceModule;
import com.nautilus.omni.dependencyinjection.modules.main.DeviceModule_ProvideDeviceInteractorFactory;
import com.nautilus.omni.dependencyinjection.modules.main.DeviceModule_ProvideDevicePresenterFactory;
import com.nautilus.omni.dependencyinjection.modules.main.DeviceModule_ProvideDeviceViewFactory;
import com.nautilus.omni.dependencyinjection.modules.main.GoalsModule;
import com.nautilus.omni.dependencyinjection.modules.main.GoalsModule_ProvideGoalsPresenterFactory;
import com.nautilus.omni.dependencyinjection.modules.main.GoalsModule_ProvideGoalsViewFactory;
import com.nautilus.omni.dependencyinjection.modules.main.GoalsModule_ProvideLoadGoalsInteractorFactory;
import com.nautilus.omni.dependencyinjection.modules.main.GoalsModule_ProvideSaveGoalsInteractorFactory;
import com.nautilus.omni.dependencyinjection.modules.main.HomeModule;
import com.nautilus.omni.dependencyinjection.modules.main.HomeModule_ProvideAchievementsInteractorFactory;
import com.nautilus.omni.dependencyinjection.modules.main.HomeModule_ProvideHomeViewFactory;
import com.nautilus.omni.dependencyinjection.modules.main.HomeModule_ProvideIAchievementsPresenterFactory;
import com.nautilus.omni.dependencyinjection.modules.main.HomeModule_ProvideIGaugeHelperFactory;
import com.nautilus.omni.dependencyinjection.modules.main.HomeModule_ProvideIGaugeInteractorFactory;
import com.nautilus.omni.dependencyinjection.modules.main.HomeModule_ProvideIGaugePresenterFactory;
import com.nautilus.omni.dependencyinjection.modules.main.HomeModule_ProvideIHomeInteractorFactory;
import com.nautilus.omni.dependencyinjection.modules.main.HomeModule_ProvideIHomePresenterFactory;
import com.nautilus.omni.dependencyinjection.modules.main.HomeModule_ProvideIThisWeekHelperFactory;
import com.nautilus.omni.dependencyinjection.modules.main.HomeModule_ProvideIThisWeekPresenterFactory;
import com.nautilus.omni.dependencyinjection.modules.main.HomeModule_ProvideIThisWeekSectionInteractorFactory;
import com.nautilus.omni.dependencyinjection.modules.main.JournalModule;
import com.nautilus.omni.dependencyinjection.modules.main.JournalModule_ProvideDayInteractorFactory;
import com.nautilus.omni.dependencyinjection.modules.main.JournalModule_ProvideDayPresenterFactory;
import com.nautilus.omni.dependencyinjection.modules.main.JournalModule_ProvideJournalViewFactory;
import com.nautilus.omni.dependencyinjection.modules.main.JournalModule_ProvideWeekInteractorFactory;
import com.nautilus.omni.dependencyinjection.modules.main.JournalModule_ProvideWeekPresenterFactory;
import com.nautilus.omni.dependencyinjection.modules.main.JournalModule_ProvideYearInteractorFactory;
import com.nautilus.omni.dependencyinjection.modules.main.JournalModule_ProvideYearPresenterFactory;
import com.nautilus.omni.dependencyinjection.modules.main.MainModule;
import com.nautilus.omni.dependencyinjection.modules.main.MainModule_ProvideAwardValueBuilderFactory;
import com.nautilus.omni.dependencyinjection.modules.main.MainModule_ProvidePermissionActionFactory;
import com.nautilus.omni.dependencyinjection.modules.main.MainModule_ProvidePermissionPresenterFactory;
import com.nautilus.omni.dependencyinjection.modules.main.MainModule_ProvidesGoalsReminderToastViewBuilderFactory;
import com.nautilus.omni.dependencyinjection.modules.main.MainModule_ProvidesMainActivityFactory;
import com.nautilus.omni.dependencyinjection.modules.main.MainModule_ProvidesMainGoalsHelperInteractorFactory;
import com.nautilus.omni.dependencyinjection.modules.main.MainModule_ProvidesMainViewContractFactory;
import com.nautilus.omni.permissions.PermissionAction;
import com.nautilus.omni.permissions.PermissionPresenter;
import com.nautilus.omni.util.LocationSettingsUtil_Factory;
import dagger.MembersInjector;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import dagger.internal.MembersInjectors;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class DaggerMainOmniComponent implements MainOmniComponent {
    static final /* synthetic */ boolean $assertionsDisabled = (!DaggerMainOmniComponent.class.desiredAssertionStatus());
    private MembersInjector<AchievementsFragment> achievementsFragmentMembersInjector;
    private MembersInjector<AwardsFragment> awardsFragmentMembersInjector;
    private Provider<Context> contextProvider;
    private MembersInjector<DeviceFragment> deviceFragmentMembersInjector;
    private MembersInjector<GaugeFragment> gaugeFragmentMembersInjector;
    private Provider<AchievementsDaoHelper> getAchievementsDaoHelperProvider;
    private Provider<AwardsDaoHelper> getAwardsDaoHelperProvider;
    private Provider<DataBaseHelper> getDatabaseHelperProvider;
    private Provider<GoalsDaoHelper> getGoalsDaoHelperProvider;
    private Provider<ProductDaoHelper> getProductDaoHelperProvider;
    private Provider<SharedPreferences> getSharedPreferencesProvider;
    private Provider<UserDaoHelper> getUserDaoHelperProvider;
    private Provider<WeekDaoHelper> getWeekDaoHelperProvider;
    private Provider<WorkoutDaoHelper> getWorkoutDaoHelperProvider;
    private MembersInjector<GoalsFragment> goalsFragmentMembersInjector;
    private MembersInjector<HomeFragment> homeFragmentMembersInjector;
    private MembersInjector<JournalDayFragment> journalDayFragmentMembersInjector;
    private MembersInjector<JournalWeekFragment> journalWeekFragmentMembersInjector;
    private MembersInjector<JournalYearFragment> journalYearFragmentMembersInjector;
    private MembersInjector<MainActivity> mainActivityMembersInjector;
    private Provider<MainPresenter> mainPresenterProvider;
    private Provider<AchievementsContract> provideAchievementsInteractorProvider;
    private Provider<AwardValueBuilder> provideAwardValueBuilderProvider;
    private Provider<AwardsFragment> provideAwardsViewProvider;
    private Provider<DayInteractor> provideDayInteractorProvider;
    private Provider<DayPresenter> provideDayPresenterProvider;
    private Provider<DeviceInteractor> provideDeviceInteractorProvider;
    private Provider<DevicePresenter> provideDevicePresenterProvider;
    private Provider<DeviceFragment> provideDeviceViewProvider;
    private Provider<GoalsPresenter> provideGoalsPresenterProvider;
    private Provider<GoalsFragment> provideGoalsViewProvider;
    private Provider<HomeFragment> provideHomeViewProvider;
    private Provider<AchievementsPresenterContract> provideIAchievementsPresenterProvider;
    private Provider<AwardsInteractorContract> provideIAwardsInteractorProvider;
    private Provider<AwardsPresenterContract> provideIAwardsPresenterProvider;
    private Provider<IGaugeHelper> provideIGaugeHelperProvider;
    private Provider<GaugeInteractorContract> provideIGaugeInteractorProvider;
    private Provider<GaugePresenterContract> provideIGaugePresenterProvider;
    private Provider<HomeInteractorContract> provideIHomeInteractorProvider;
    private Provider<HomePresenterContract> provideIHomePresenterProvider;
    private Provider<IThisWeekHelper> provideIThisWeekHelperProvider;
    private Provider<ThisWeekPresenterContract> provideIThisWeekPresenterProvider;
    private Provider<ThisWeekSectionInteractorContract> provideIThisWeekSectionInteractorProvider;
    private Provider<JournalFragment> provideJournalViewProvider;
    private Provider<LoadGoalsInteractor> provideLoadGoalsInteractorProvider;
    private Provider<PermissionAction> providePermissionActionProvider;
    private Provider<PermissionPresenter> providePermissionPresenterProvider;
    private Provider<SaveGoalsInteractor> provideSaveGoalsInteractorProvider;
    private Provider<WeekInteractor> provideWeekInteractorProvider;
    private Provider<WeekPresenter> provideWeekPresenterProvider;
    private Provider<YearInteractor> provideYearInteractorProvider;
    private Provider<YearPresenter> provideYearPresenterProvider;
    private Provider<GoalsReminderToastViewBuilder> providesGoalsReminderToastViewBuilderProvider;
    private Provider<MainActivity> providesMainActivityProvider;
    private Provider<MainGoalsHelperInteractor> providesMainGoalsHelperInteractorProvider;
    private Provider<MainViewContract> providesMainViewContractProvider;
    private MembersInjector<ThisWeekSectionFragment> thisWeekSectionFragmentMembersInjector;

    private DaggerMainOmniComponent(Builder builder) {
        if ($assertionsDisabled || builder != null) {
            initialize(builder);
            return;
        }
        throw new AssertionError();
    }

    public static Builder builder() {
        return new Builder();
    }

    private void initialize(final Builder builder) {
        this.provideGoalsViewProvider = DoubleCheck.provider(GoalsModule_ProvideGoalsViewFactory.create(builder.goalsModule));
        this.provideHomeViewProvider = DoubleCheck.provider(HomeModule_ProvideHomeViewFactory.create(builder.homeModule));
        this.provideAwardsViewProvider = DoubleCheck.provider(AwardsModule_ProvideAwardsViewFactory.create(builder.awardsModule));
        this.provideJournalViewProvider = DoubleCheck.provider(JournalModule_ProvideJournalViewFactory.create(builder.journalModule));
        this.provideDeviceViewProvider = DoubleCheck.provider(DeviceModule_ProvideDeviceViewFactory.create(builder.deviceModule));
        this.getSharedPreferencesProvider = new Factory<SharedPreferences>() {
            private final AppComponent appComponent = builder.appComponent;

            public SharedPreferences get() {
                return (SharedPreferences) Preconditions.checkNotNull(this.appComponent.getSharedPreferences(), "Cannot return null from a non-@Nullable component method");
            }
        };
        this.contextProvider = new Factory<Context>() {
            private final AppComponent appComponent = builder.appComponent;

            public Context get() {
                return (Context) Preconditions.checkNotNull(this.appComponent.context(), "Cannot return null from a non-@Nullable component method");
            }
        };
        this.providesMainViewContractProvider = DoubleCheck.provider(MainModule_ProvidesMainViewContractFactory.create(builder.mainModule));
        this.getAchievementsDaoHelperProvider = new Factory<AchievementsDaoHelper>() {
            private final AppComponent appComponent = builder.appComponent;

            public AchievementsDaoHelper get() {
                return (AchievementsDaoHelper) Preconditions.checkNotNull(this.appComponent.getAchievementsDaoHelper(), "Cannot return null from a non-@Nullable component method");
            }
        };
        this.getGoalsDaoHelperProvider = new Factory<GoalsDaoHelper>() {
            private final AppComponent appComponent = builder.appComponent;

            public GoalsDaoHelper get() {
                return (GoalsDaoHelper) Preconditions.checkNotNull(this.appComponent.getGoalsDaoHelper(), "Cannot return null from a non-@Nullable component method");
            }
        };
        this.getWorkoutDaoHelperProvider = new Factory<WorkoutDaoHelper>() {
            private final AppComponent appComponent = builder.appComponent;

            public WorkoutDaoHelper get() {
                return (WorkoutDaoHelper) Preconditions.checkNotNull(this.appComponent.getWorkoutDaoHelper(), "Cannot return null from a non-@Nullable component method");
            }
        };
        this.provideSaveGoalsInteractorProvider = DoubleCheck.provider(GoalsModule_ProvideSaveGoalsInteractorFactory.create(builder.goalsModule, this.getAchievementsDaoHelperProvider, this.getGoalsDaoHelperProvider, this.getWorkoutDaoHelperProvider));
        this.providesMainGoalsHelperInteractorProvider = DoubleCheck.provider(MainModule_ProvidesMainGoalsHelperInteractorFactory.create(builder.mainModule, this.contextProvider, this.provideSaveGoalsInteractorProvider, this.getGoalsDaoHelperProvider));
        this.providesMainActivityProvider = DoubleCheck.provider(MainModule_ProvidesMainActivityFactory.create(builder.mainModule));
        this.providesGoalsReminderToastViewBuilderProvider = DoubleCheck.provider(MainModule_ProvidesGoalsReminderToastViewBuilderFactory.create(builder.mainModule, this.providesMainActivityProvider));
        this.mainPresenterProvider = MainPresenter_Factory.create(MembersInjectors.noOp(), this.contextProvider, this.providesMainViewContractProvider, this.providesMainGoalsHelperInteractorProvider, this.providesGoalsReminderToastViewBuilderProvider);
        this.providePermissionActionProvider = DoubleCheck.provider(MainModule_ProvidePermissionActionFactory.create(builder.mainModule, this.providesMainActivityProvider));
        this.providePermissionPresenterProvider = DoubleCheck.provider(MainModule_ProvidePermissionPresenterFactory.create(builder.mainModule, this.providePermissionActionProvider, this.providesMainActivityProvider));
        this.mainActivityMembersInjector = MainActivity_MembersInjector.create(this.getSharedPreferencesProvider, LocationSettingsUtil_Factory.create(), this.mainPresenterProvider, this.providePermissionPresenterProvider);
        this.provideLoadGoalsInteractorProvider = DoubleCheck.provider(GoalsModule_ProvideLoadGoalsInteractorFactory.create(builder.goalsModule, this.contextProvider, this.getGoalsDaoHelperProvider));
        this.provideGoalsPresenterProvider = DoubleCheck.provider(GoalsModule_ProvideGoalsPresenterFactory.create(builder.goalsModule, this.contextProvider, this.provideLoadGoalsInteractorProvider, this.provideSaveGoalsInteractorProvider));
        this.goalsFragmentMembersInjector = GoalsFragment_MembersInjector.create(this.provideGoalsPresenterProvider);
        this.getAwardsDaoHelperProvider = new Factory<AwardsDaoHelper>() {
            private final AppComponent appComponent = builder.appComponent;

            public AwardsDaoHelper get() {
                return (AwardsDaoHelper) Preconditions.checkNotNull(this.appComponent.getAwardsDaoHelper(), "Cannot return null from a non-@Nullable component method");
            }
        };
        this.provideIHomeInteractorProvider = DoubleCheck.provider(HomeModule_ProvideIHomeInteractorFactory.create(builder.homeModule, this.getAwardsDaoHelperProvider, this.getWorkoutDaoHelperProvider, this.getAchievementsDaoHelperProvider));
        this.getWeekDaoHelperProvider = new Factory<WeekDaoHelper>() {
            private final AppComponent appComponent = builder.appComponent;

            public WeekDaoHelper get() {
                return (WeekDaoHelper) Preconditions.checkNotNull(this.appComponent.getWeekDaoHelper(), "Cannot return null from a non-@Nullable component method");
            }
        };
        this.provideAwardValueBuilderProvider = DoubleCheck.provider(MainModule_ProvideAwardValueBuilderFactory.create(builder.mainModule, this.contextProvider, this.getWeekDaoHelperProvider));
        this.provideIHomePresenterProvider = DoubleCheck.provider(HomeModule_ProvideIHomePresenterFactory.create(builder.homeModule, this.contextProvider, this.provideIHomeInteractorProvider, this.provideAwardValueBuilderProvider));
        this.homeFragmentMembersInjector = HomeFragment_MembersInjector.create(this.provideIHomePresenterProvider);
        this.provideAchievementsInteractorProvider = DoubleCheck.provider(HomeModule_ProvideAchievementsInteractorFactory.create(builder.homeModule, this.getWorkoutDaoHelperProvider, this.getAwardsDaoHelperProvider, this.getAchievementsDaoHelperProvider, this.getWeekDaoHelperProvider));
        this.provideIAchievementsPresenterProvider = DoubleCheck.provider(HomeModule_ProvideIAchievementsPresenterFactory.create(builder.homeModule, this.contextProvider, this.provideAchievementsInteractorProvider));
        this.achievementsFragmentMembersInjector = AchievementsFragment_MembersInjector.create(this.provideIAchievementsPresenterProvider, this.provideAwardValueBuilderProvider);
        this.getDatabaseHelperProvider = new Factory<DataBaseHelper>() {
            private final AppComponent appComponent = builder.appComponent;

            public DataBaseHelper get() {
                return (DataBaseHelper) Preconditions.checkNotNull(this.appComponent.getDatabaseHelper(), "Cannot return null from a non-@Nullable component method");
            }
        };
        this.getProductDaoHelperProvider = new Factory<ProductDaoHelper>() {
            private final AppComponent appComponent = builder.appComponent;

            public ProductDaoHelper get() {
                return (ProductDaoHelper) Preconditions.checkNotNull(this.appComponent.getProductDaoHelper(), "Cannot return null from a non-@Nullable component method");
            }
        };
        this.provideDeviceInteractorProvider = DoubleCheck.provider(DeviceModule_ProvideDeviceInteractorFactory.create(builder.deviceModule, this.contextProvider, this.getDatabaseHelperProvider, this.getProductDaoHelperProvider));
        this.provideDevicePresenterProvider = DoubleCheck.provider(DeviceModule_ProvideDevicePresenterFactory.create(builder.deviceModule, this.contextProvider, this.provideDeviceInteractorProvider));
        this.deviceFragmentMembersInjector = DeviceFragment_MembersInjector.create(this.provideDevicePresenterProvider, this.providePermissionPresenterProvider);
        this.getUserDaoHelperProvider = new Factory<UserDaoHelper>() {
            private final AppComponent appComponent = builder.appComponent;

            public UserDaoHelper get() {
                return (UserDaoHelper) Preconditions.checkNotNull(this.appComponent.getUserDaoHelper(), "Cannot return null from a non-@Nullable component method");
            }
        };
        this.provideIGaugeInteractorProvider = DoubleCheck.provider(HomeModule_ProvideIGaugeInteractorFactory.create(builder.homeModule, this.getWorkoutDaoHelperProvider, this.getUserDaoHelperProvider, this.getGoalsDaoHelperProvider));
        this.provideIGaugeHelperProvider = DoubleCheck.provider(HomeModule_ProvideIGaugeHelperFactory.create(builder.homeModule));
        this.provideIGaugePresenterProvider = DoubleCheck.provider(HomeModule_ProvideIGaugePresenterFactory.create(builder.homeModule, this.contextProvider, this.provideIGaugeInteractorProvider, this.provideIGaugeHelperProvider));
        this.gaugeFragmentMembersInjector = GaugeFragment_MembersInjector.create(this.provideIGaugePresenterProvider);
        this.provideIThisWeekHelperProvider = DoubleCheck.provider(HomeModule_ProvideIThisWeekHelperFactory.create(builder.homeModule, this.contextProvider));
        this.provideIThisWeekSectionInteractorProvider = DoubleCheck.provider(HomeModule_ProvideIThisWeekSectionInteractorFactory.create(builder.homeModule, this.getGoalsDaoHelperProvider, this.getWeekDaoHelperProvider, this.getUserDaoHelperProvider, this.getWorkoutDaoHelperProvider));
        this.provideIThisWeekPresenterProvider = DoubleCheck.provider(HomeModule_ProvideIThisWeekPresenterFactory.create(builder.homeModule, this.contextProvider, this.provideIThisWeekHelperProvider, this.provideIThisWeekSectionInteractorProvider));
        this.thisWeekSectionFragmentMembersInjector = ThisWeekSectionFragment_MembersInjector.create(this.provideIThisWeekPresenterProvider);
        this.provideIAwardsInteractorProvider = DoubleCheck.provider(AwardsModule_ProvideIAwardsInteractorFactory.create(builder.awardsModule, this.getAwardsDaoHelperProvider));
        this.provideIAwardsPresenterProvider = DoubleCheck.provider(AwardsModule_ProvideIAwardsPresenterFactory.create(builder.awardsModule, this.contextProvider, this.provideIAwardsInteractorProvider));
        this.awardsFragmentMembersInjector = AwardsFragment_MembersInjector.create(this.provideAwardValueBuilderProvider, this.provideIAwardsPresenterProvider);
        this.provideWeekInteractorProvider = DoubleCheck.provider(JournalModule_ProvideWeekInteractorFactory.create(builder.journalModule, this.contextProvider, this.getWorkoutDaoHelperProvider));
        this.provideWeekPresenterProvider = DoubleCheck.provider(JournalModule_ProvideWeekPresenterFactory.create(builder.journalModule, this.contextProvider, this.provideWeekInteractorProvider));
        this.journalWeekFragmentMembersInjector = JournalWeekFragment_MembersInjector.create(this.provideWeekPresenterProvider);
        this.provideYearInteractorProvider = DoubleCheck.provider(JournalModule_ProvideYearInteractorFactory.create(builder.journalModule, this.contextProvider, this.getWorkoutDaoHelperProvider));
        this.provideYearPresenterProvider = DoubleCheck.provider(JournalModule_ProvideYearPresenterFactory.create(builder.journalModule, this.contextProvider, this.provideYearInteractorProvider));
        this.journalYearFragmentMembersInjector = JournalYearFragment_MembersInjector.create(this.provideYearPresenterProvider);
        this.provideDayInteractorProvider = DoubleCheck.provider(JournalModule_ProvideDayInteractorFactory.create(builder.journalModule, this.contextProvider, this.getWorkoutDaoHelperProvider, this.getAchievementsDaoHelperProvider));
        this.provideDayPresenterProvider = DoubleCheck.provider(JournalModule_ProvideDayPresenterFactory.create(builder.journalModule, this.contextProvider, this.provideDayInteractorProvider));
        this.journalDayFragmentMembersInjector = JournalDayFragment_MembersInjector.create(this.provideDayPresenterProvider);
    }

    public GoalsFragment getGoalFragment() {
        return this.provideGoalsViewProvider.get();
    }

    public HomeFragment getHomeFragment() {
        return this.provideHomeViewProvider.get();
    }

    public AwardsFragment getAwardsFragment() {
        return this.provideAwardsViewProvider.get();
    }

    public JournalFragment getJournalFragment() {
        return this.provideJournalViewProvider.get();
    }

    public DeviceFragment getDeviceFragment() {
        return this.provideDeviceViewProvider.get();
    }

    public void inject(MainActivity mainActivity) {
        this.mainActivityMembersInjector.injectMembers(mainActivity);
    }

    public void inject(GoalsFragment goalsFragment) {
        this.goalsFragmentMembersInjector.injectMembers(goalsFragment);
    }

    public void inject(HomeFragment homeFragment) {
        this.homeFragmentMembersInjector.injectMembers(homeFragment);
    }

    public void inject(AchievementsFragment achievementsFragment) {
        this.achievementsFragmentMembersInjector.injectMembers(achievementsFragment);
    }

    public void inject(DeviceFragment deviceFragment) {
        this.deviceFragmentMembersInjector.injectMembers(deviceFragment);
    }

    public void inject(GaugeFragment gaugeFragment) {
        this.gaugeFragmentMembersInjector.injectMembers(gaugeFragment);
    }

    public void inject(ThisWeekSectionFragment thisWeekSectionFragment) {
        this.thisWeekSectionFragmentMembersInjector.injectMembers(thisWeekSectionFragment);
    }

    public void inject(AwardsFragment awardsFragment) {
        this.awardsFragmentMembersInjector.injectMembers(awardsFragment);
    }

    public void inject(JournalWeekFragment journalWeekFragment) {
        this.journalWeekFragmentMembersInjector.injectMembers(journalWeekFragment);
    }

    public void inject(JournalYearFragment journalYearFragment) {
        this.journalYearFragmentMembersInjector.injectMembers(journalYearFragment);
    }

    public void inject(JournalDayFragment journalDayFragment) {
        this.journalDayFragmentMembersInjector.injectMembers(journalDayFragment);
    }

    public static final class Builder {
        /* access modifiers changed from: private */
        public AppComponent appComponent;
        /* access modifiers changed from: private */
        public AwardsModule awardsModule;
        /* access modifiers changed from: private */
        public DeviceModule deviceModule;
        /* access modifiers changed from: private */
        public GoalsModule goalsModule;
        /* access modifiers changed from: private */
        public HomeModule homeModule;
        /* access modifiers changed from: private */
        public JournalModule journalModule;
        /* access modifiers changed from: private */
        public MainModule mainModule;

        private Builder() {
        }

        public MainOmniComponent build() {
            if (this.goalsModule == null) {
                throw new IllegalStateException(GoalsModule.class.getCanonicalName() + " must be set");
            } else if (this.homeModule == null) {
                throw new IllegalStateException(HomeModule.class.getCanonicalName() + " must be set");
            } else if (this.awardsModule == null) {
                throw new IllegalStateException(AwardsModule.class.getCanonicalName() + " must be set");
            } else if (this.journalModule == null) {
                throw new IllegalStateException(JournalModule.class.getCanonicalName() + " must be set");
            } else if (this.deviceModule == null) {
                throw new IllegalStateException(DeviceModule.class.getCanonicalName() + " must be set");
            } else if (this.mainModule == null) {
                throw new IllegalStateException(MainModule.class.getCanonicalName() + " must be set");
            } else if (this.appComponent != null) {
                return new DaggerMainOmniComponent(this);
            } else {
                throw new IllegalStateException(AppComponent.class.getCanonicalName() + " must be set");
            }
        }

        public Builder goalsModule(GoalsModule goalsModule2) {
            this.goalsModule = (GoalsModule) Preconditions.checkNotNull(goalsModule2);
            return this;
        }

        public Builder homeModule(HomeModule homeModule2) {
            this.homeModule = (HomeModule) Preconditions.checkNotNull(homeModule2);
            return this;
        }

        public Builder awardsModule(AwardsModule awardsModule2) {
            this.awardsModule = (AwardsModule) Preconditions.checkNotNull(awardsModule2);
            return this;
        }

        public Builder deviceModule(DeviceModule deviceModule2) {
            this.deviceModule = (DeviceModule) Preconditions.checkNotNull(deviceModule2);
            return this;
        }

        public Builder journalModule(JournalModule journalModule2) {
            this.journalModule = (JournalModule) Preconditions.checkNotNull(journalModule2);
            return this;
        }

        public Builder mainModule(MainModule mainModule2) {
            this.mainModule = (MainModule) Preconditions.checkNotNull(mainModule2);
            return this;
        }

        public Builder appComponent(AppComponent appComponent2) {
            this.appComponent = (AppComponent) Preconditions.checkNotNull(appComponent2);
            return this;
        }
    }
}
