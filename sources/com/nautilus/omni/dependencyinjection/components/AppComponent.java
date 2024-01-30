package com.nautilus.omni.dependencyinjection.components;

import android.content.Context;
import android.content.SharedPreferences;
import com.nautilus.omni.app.BaseActivity;
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
import com.nautilus.omni.dependencyinjection.modules.DataModule;
import com.nautilus.omni.dependencyinjection.modules.settings.underarmour.UnderArmourModule;
import com.nautilus.omni.syncservices.ObservableObject;
import com.nautilus.omni.syncservices.syncserviceshelpers.FitServicesSyncDataHelper;
import com.nautilus.underarmourconnection.services.authentication.AuthenticationService;
import com.nautilus.underarmourconnection.services.workouts.WorkoutService;
import dagger.Component;
import javax.inject.Singleton;

@Singleton
@Component(modules = {AppModule.class, DataModule.class, UnderArmourModule.class})
public interface AppComponent {
    Context context();

    AchievementsDaoHelper getAchievementsDaoHelper();

    AwardsDaoHelper getAwardsDaoHelper();

    DataBaseHelper getDatabaseHelper();

    FitServicesSyncDataHelper getFitServicesDataHelper();

    GoalsDaoHelper getGoalsDaoHelper();

    GoogleFitHelper getGoogleFitHelper();

    MainAwardsDaoHelper getMainAwardsDaoHelper();

    ObservableObject getObservableObject();

    OmniTrainerDaoHelper getOmniTrainerDaoHelper();

    ProductDaoHelper getProductDaoHelper();

    SharedPreferences getSharedPreferences();

    AuthenticationService getUnderArmourAuthenticationService();

    WorkoutService getUnderArmourWorkoutService();

    UserDaoHelper getUserDaoHelper();

    WeekDaoHelper getWeekDaoHelper();

    WorkoutDaoHelper getWorkoutDaoHelper();

    void inject(BaseActivity baseActivity);
}
