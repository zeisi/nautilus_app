package com.nautilus.omni.dependencyinjection.components;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import com.j256.ormlite.dao.Dao;
import com.nautilus.omni.app.BaseActivity;
import com.nautilus.omni.app.BaseActivity_MembersInjector;
import com.nautilus.omni.appmodules.settings.googlefit.googlefitcommunication.GoogleFitHelper;
import com.nautilus.omni.dataaccess.AchievementsDaoHelper;
import com.nautilus.omni.dataaccess.AwardsDaoHelper;
import com.nautilus.omni.dataaccess.GoalsDaoHelper;
import com.nautilus.omni.dataaccess.OmniTrainerDaoHelper;
import com.nautilus.omni.dataaccess.ProductDaoHelper;
import com.nautilus.omni.dataaccess.UserDaoHelper;
import com.nautilus.omni.dataaccess.WeekDaoHelper;
import com.nautilus.omni.dataaccess.WorkoutDaoHelper;
import com.nautilus.omni.dataaccess.awards.MainAwardsDaoHelper;
import com.nautilus.omni.databasemanager.DataBaseHelper;
import com.nautilus.omni.dependencyinjection.modules.AppModule;
import com.nautilus.omni.dependencyinjection.modules.AppModule_ProvideApplicationContextFactory;
import com.nautilus.omni.dependencyinjection.modules.AppModule_ProvideGoogleFitHelperFactory;
import com.nautilus.omni.dependencyinjection.modules.AppModule_ProvideLocationSettingsUtilFactory;
import com.nautilus.omni.dependencyinjection.modules.AppModule_ProvideSharedPreferencesFactory;
import com.nautilus.omni.dependencyinjection.modules.AppModule_ProvidesApplicationContextFactory;
import com.nautilus.omni.dependencyinjection.modules.AppModule_ProvidesObservableObjectFactory;
import com.nautilus.omni.dependencyinjection.modules.DataModule;
import com.nautilus.omni.dependencyinjection.modules.DataModule_ProvideAchievementsDaoHelperFactory;
import com.nautilus.omni.dependencyinjection.modules.DataModule_ProvideAwardsDaoHelperFactory;
import com.nautilus.omni.dependencyinjection.modules.DataModule_ProvideDatabaseHelperFactory;
import com.nautilus.omni.dependencyinjection.modules.DataModule_ProvideFitServicesDaoHelperFactory;
import com.nautilus.omni.dependencyinjection.modules.DataModule_ProvideGoalsDaoHelperFactory;
import com.nautilus.omni.dependencyinjection.modules.DataModule_ProvideMainAwardsDaoHelperFactory;
import com.nautilus.omni.dependencyinjection.modules.DataModule_ProvideOmniTrainerDaoHelperFactory;
import com.nautilus.omni.dependencyinjection.modules.DataModule_ProvideProductDaoHelperFactory;
import com.nautilus.omni.dependencyinjection.modules.DataModule_ProvideUserDaoHelperFactory;
import com.nautilus.omni.dependencyinjection.modules.DataModule_ProvideWeekDaoHelperFactory;
import com.nautilus.omni.dependencyinjection.modules.DataModule_ProvideWorkoutDaoHelperFactory;
import com.nautilus.omni.dependencyinjection.modules.settings.underarmour.UnderArmourModule;
import com.nautilus.omni.dependencyinjection.modules.settings.underarmour.UnderArmourModule_ProvideUnderArmourAuthenticationServiceFactory;
import com.nautilus.omni.dependencyinjection.modules.settings.underarmour.UnderArmourModule_ProvideUnderArmourWorkoutServiceFactory;
import com.nautilus.omni.dependencyinjection.modules.settings.underarmour.UnderArmourModule_ProvideWorkoutTrackDaoFactory;
import com.nautilus.omni.dependencyinjection.modules.settings.underarmour.UnderArmourModule_ProvideWorkoutTrackDaoHelperFactory;
import com.nautilus.omni.dependencyinjection.modules.settings.underarmour.UnderArmourModule_ProvidesUnderArmourDatabaseHelperFactory;
import com.nautilus.omni.syncservices.ObservableObject;
import com.nautilus.omni.syncservices.syncserviceshelpers.FitServicesSyncDataHelper;
import com.nautilus.omni.util.LocationSettingsUtil;
import com.nautilus.underarmourconnection.database.UnderArmourDatabaseHelper;
import com.nautilus.underarmourconnection.database.WorkoutTrack;
import com.nautilus.underarmourconnection.database.WorkoutTrackDaoHelper;
import com.nautilus.underarmourconnection.services.authentication.AuthenticationService;
import com.nautilus.underarmourconnection.services.workouts.WorkoutService;
import dagger.MembersInjector;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class DaggerAppComponent implements AppComponent {
    static final /* synthetic */ boolean $assertionsDisabled = (!DaggerAppComponent.class.desiredAssertionStatus());
    private MembersInjector<BaseActivity> baseActivityMembersInjector;
    private Provider<AchievementsDaoHelper> provideAchievementsDaoHelperProvider;
    private Provider<Context> provideApplicationContextProvider;
    private Provider<AwardsDaoHelper> provideAwardsDaoHelperProvider;
    private Provider<DataBaseHelper> provideDatabaseHelperProvider;
    private Provider<FitServicesSyncDataHelper> provideFitServicesDaoHelperProvider;
    private Provider<GoalsDaoHelper> provideGoalsDaoHelperProvider;
    private Provider<GoogleFitHelper> provideGoogleFitHelperProvider;
    private Provider<LocationSettingsUtil> provideLocationSettingsUtilProvider;
    private Provider<MainAwardsDaoHelper> provideMainAwardsDaoHelperProvider;
    private Provider<OmniTrainerDaoHelper> provideOmniTrainerDaoHelperProvider;
    private Provider<ProductDaoHelper> provideProductDaoHelperProvider;
    private Provider<SharedPreferences> provideSharedPreferencesProvider;
    private Provider<AuthenticationService> provideUnderArmourAuthenticationServiceProvider;
    private Provider<WorkoutService> provideUnderArmourWorkoutServiceProvider;
    private Provider<UserDaoHelper> provideUserDaoHelperProvider;
    private Provider<WeekDaoHelper> provideWeekDaoHelperProvider;
    private Provider<WorkoutDaoHelper> provideWorkoutDaoHelperProvider;
    private Provider<WorkoutTrackDaoHelper> provideWorkoutTrackDaoHelperProvider;
    private Provider<Dao<WorkoutTrack, Integer>> provideWorkoutTrackDaoProvider;
    private Provider<Application> providesApplicationContextProvider;
    private Provider<ObservableObject> providesObservableObjectProvider;
    private Provider<UnderArmourDatabaseHelper> providesUnderArmourDatabaseHelperProvider;

    private DaggerAppComponent(Builder builder) {
        if ($assertionsDisabled || builder != null) {
            initialize(builder);
            return;
        }
        throw new AssertionError();
    }

    public static Builder builder() {
        return new Builder();
    }

    private void initialize(Builder builder) {
        this.providesApplicationContextProvider = DoubleCheck.provider(AppModule_ProvidesApplicationContextFactory.create(builder.appModule));
        this.provideSharedPreferencesProvider = DoubleCheck.provider(AppModule_ProvideSharedPreferencesFactory.create(builder.appModule, this.providesApplicationContextProvider));
        this.provideLocationSettingsUtilProvider = DoubleCheck.provider(AppModule_ProvideLocationSettingsUtilFactory.create(builder.appModule));
        this.baseActivityMembersInjector = BaseActivity_MembersInjector.create(this.provideSharedPreferencesProvider, this.provideLocationSettingsUtilProvider);
        this.provideApplicationContextProvider = DoubleCheck.provider(AppModule_ProvideApplicationContextFactory.create(builder.appModule));
        this.provideDatabaseHelperProvider = DoubleCheck.provider(DataModule_ProvideDatabaseHelperFactory.create(builder.dataModule, this.provideApplicationContextProvider));
        this.provideWorkoutDaoHelperProvider = DoubleCheck.provider(DataModule_ProvideWorkoutDaoHelperFactory.create(builder.dataModule, this.provideDatabaseHelperProvider));
        this.provideAwardsDaoHelperProvider = DoubleCheck.provider(DataModule_ProvideAwardsDaoHelperFactory.create(builder.dataModule, this.provideApplicationContextProvider, this.provideDatabaseHelperProvider));
        this.provideWeekDaoHelperProvider = DoubleCheck.provider(DataModule_ProvideWeekDaoHelperFactory.create(builder.dataModule, this.provideDatabaseHelperProvider));
        this.provideGoalsDaoHelperProvider = DoubleCheck.provider(DataModule_ProvideGoalsDaoHelperFactory.create(builder.dataModule, this.provideDatabaseHelperProvider));
        this.provideUserDaoHelperProvider = DoubleCheck.provider(DataModule_ProvideUserDaoHelperFactory.create(builder.dataModule, this.provideDatabaseHelperProvider));
        this.provideProductDaoHelperProvider = DoubleCheck.provider(DataModule_ProvideProductDaoHelperFactory.create(builder.dataModule, this.provideDatabaseHelperProvider));
        this.provideMainAwardsDaoHelperProvider = DoubleCheck.provider(DataModule_ProvideMainAwardsDaoHelperFactory.create(builder.dataModule, this.provideApplicationContextProvider, this.provideDatabaseHelperProvider));
        this.provideAchievementsDaoHelperProvider = DoubleCheck.provider(DataModule_ProvideAchievementsDaoHelperFactory.create(builder.dataModule, this.provideDatabaseHelperProvider));
        this.provideFitServicesDaoHelperProvider = DoubleCheck.provider(DataModule_ProvideFitServicesDaoHelperFactory.create(builder.dataModule, this.provideDatabaseHelperProvider));
        this.provideOmniTrainerDaoHelperProvider = DoubleCheck.provider(DataModule_ProvideOmniTrainerDaoHelperFactory.create(builder.dataModule, this.provideDatabaseHelperProvider));
        this.providesObservableObjectProvider = DoubleCheck.provider(AppModule_ProvidesObservableObjectFactory.create(builder.appModule));
        this.provideGoogleFitHelperProvider = DoubleCheck.provider(AppModule_ProvideGoogleFitHelperFactory.create(builder.appModule, this.provideApplicationContextProvider));
        this.providesUnderArmourDatabaseHelperProvider = DoubleCheck.provider(UnderArmourModule_ProvidesUnderArmourDatabaseHelperFactory.create(builder.underArmourModule, this.provideApplicationContextProvider));
        this.provideWorkoutTrackDaoProvider = DoubleCheck.provider(UnderArmourModule_ProvideWorkoutTrackDaoFactory.create(builder.underArmourModule, this.providesUnderArmourDatabaseHelperProvider));
        this.provideWorkoutTrackDaoHelperProvider = DoubleCheck.provider(UnderArmourModule_ProvideWorkoutTrackDaoHelperFactory.create(builder.underArmourModule, this.provideWorkoutTrackDaoProvider));
        this.provideUnderArmourWorkoutServiceProvider = DoubleCheck.provider(UnderArmourModule_ProvideUnderArmourWorkoutServiceFactory.create(builder.underArmourModule, this.provideApplicationContextProvider, this.provideWorkoutTrackDaoHelperProvider));
        this.provideUnderArmourAuthenticationServiceProvider = DoubleCheck.provider(UnderArmourModule_ProvideUnderArmourAuthenticationServiceFactory.create(builder.underArmourModule, this.provideApplicationContextProvider));
    }

    public void inject(BaseActivity baseActivity) {
        this.baseActivityMembersInjector.injectMembers(baseActivity);
    }

    public Context context() {
        return this.provideApplicationContextProvider.get();
    }

    public WorkoutDaoHelper getWorkoutDaoHelper() {
        return this.provideWorkoutDaoHelperProvider.get();
    }

    public AwardsDaoHelper getAwardsDaoHelper() {
        return this.provideAwardsDaoHelperProvider.get();
    }

    public WeekDaoHelper getWeekDaoHelper() {
        return this.provideWeekDaoHelperProvider.get();
    }

    public GoalsDaoHelper getGoalsDaoHelper() {
        return this.provideGoalsDaoHelperProvider.get();
    }

    public UserDaoHelper getUserDaoHelper() {
        return this.provideUserDaoHelperProvider.get();
    }

    public ProductDaoHelper getProductDaoHelper() {
        return this.provideProductDaoHelperProvider.get();
    }

    public DataBaseHelper getDatabaseHelper() {
        return this.provideDatabaseHelperProvider.get();
    }

    public MainAwardsDaoHelper getMainAwardsDaoHelper() {
        return this.provideMainAwardsDaoHelperProvider.get();
    }

    public AchievementsDaoHelper getAchievementsDaoHelper() {
        return this.provideAchievementsDaoHelperProvider.get();
    }

    public FitServicesSyncDataHelper getFitServicesDataHelper() {
        return this.provideFitServicesDaoHelperProvider.get();
    }

    public OmniTrainerDaoHelper getOmniTrainerDaoHelper() {
        return this.provideOmniTrainerDaoHelperProvider.get();
    }

    public SharedPreferences getSharedPreferences() {
        return this.provideSharedPreferencesProvider.get();
    }

    public ObservableObject getObservableObject() {
        return this.providesObservableObjectProvider.get();
    }

    public GoogleFitHelper getGoogleFitHelper() {
        return this.provideGoogleFitHelperProvider.get();
    }

    public WorkoutService getUnderArmourWorkoutService() {
        return this.provideUnderArmourWorkoutServiceProvider.get();
    }

    public AuthenticationService getUnderArmourAuthenticationService() {
        return this.provideUnderArmourAuthenticationServiceProvider.get();
    }

    public static final class Builder {
        /* access modifiers changed from: private */
        public AppModule appModule;
        /* access modifiers changed from: private */
        public DataModule dataModule;
        /* access modifiers changed from: private */
        public UnderArmourModule underArmourModule;

        private Builder() {
        }

        public AppComponent build() {
            if (this.appModule == null) {
                throw new IllegalStateException(AppModule.class.getCanonicalName() + " must be set");
            }
            if (this.dataModule == null) {
                this.dataModule = new DataModule();
            }
            if (this.underArmourModule == null) {
                this.underArmourModule = new UnderArmourModule();
            }
            return new DaggerAppComponent(this);
        }

        public Builder appModule(AppModule appModule2) {
            this.appModule = (AppModule) Preconditions.checkNotNull(appModule2);
            return this;
        }

        public Builder dataModule(DataModule dataModule2) {
            this.dataModule = (DataModule) Preconditions.checkNotNull(dataModule2);
            return this;
        }

        public Builder underArmourModule(UnderArmourModule underArmourModule2) {
            this.underArmourModule = (UnderArmourModule) Preconditions.checkNotNull(underArmourModule2);
            return this;
        }
    }
}
