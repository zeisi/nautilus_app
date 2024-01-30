package com.nautilus.omni.dependencyinjection.modules;

import android.content.Context;
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
import com.nautilus.omni.syncservices.syncserviceshelpers.FitServicesSyncDataHelper;
import dagger.Module;
import dagger.Provides;
import java.sql.SQLException;
import javax.inject.Singleton;

@Module
public class DataModule {
    /* access modifiers changed from: package-private */
    @Singleton
    @Provides
    public DataBaseHelper provideDatabaseHelper(Context context) {
        DataBaseHelper databaseHelper = DataBaseHelper.getHelper(context);
        databaseHelper.getWritableDatabase();
        return databaseHelper;
    }

    /* access modifiers changed from: package-private */
    @Singleton
    @Provides
    public WorkoutDaoHelper provideWorkoutDaoHelper(DataBaseHelper dataBaseHelper) {
        try {
            return new WorkoutDaoHelper(dataBaseHelper.getWorkoutDao());
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /* access modifiers changed from: package-private */
    @Singleton
    @Provides
    public AwardsDaoHelper provideAwardsDaoHelper(Context context, DataBaseHelper dataBaseHelper) {
        try {
            return new AwardsDaoHelper(context, dataBaseHelper.getAwardsDao(), dataBaseHelper.getAwardTypeDao());
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /* access modifiers changed from: package-private */
    @Singleton
    @Provides
    public WeekDaoHelper provideWeekDaoHelper(DataBaseHelper dataBaseHelper) {
        try {
            return new WeekDaoHelper(dataBaseHelper.getWeekDao());
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /* access modifiers changed from: package-private */
    @Singleton
    @Provides
    public GoalsDaoHelper provideGoalsDaoHelper(DataBaseHelper dataBaseHelper) {
        try {
            return new GoalsDaoHelper(dataBaseHelper.getTrainingGoalsDao());
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /* access modifiers changed from: package-private */
    @Singleton
    @Provides
    public AchievementsDaoHelper provideAchievementsDaoHelper(DataBaseHelper dataBaseHelper) {
        try {
            return new AchievementsDaoHelper(dataBaseHelper.getAchievementDao());
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /* access modifiers changed from: package-private */
    @Singleton
    @Provides
    public UserDaoHelper provideUserDaoHelper(DataBaseHelper dataBaseHelper) {
        try {
            return new UserDaoHelper(dataBaseHelper.getUserDao());
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /* access modifiers changed from: package-private */
    @Singleton
    @Provides
    public ProductDaoHelper provideProductDaoHelper(DataBaseHelper dataBaseHelper) {
        try {
            return new ProductDaoHelper(dataBaseHelper.getProductDao());
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /* access modifiers changed from: package-private */
    @Singleton
    @Provides
    public MainAwardsDaoHelper provideMainAwardsDaoHelper(Context context, DataBaseHelper dataBaseHelper) {
        try {
            return new MainAwardsDaoHelper(context, dataBaseHelper.getAwardsDao(), dataBaseHelper.getAwardTypeDao());
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /* access modifiers changed from: package-private */
    @Singleton
    @Provides
    public FitServicesSyncDataHelper provideFitServicesDaoHelper(DataBaseHelper dataBaseHelper) {
        try {
            return new FitServicesSyncDataHelper(dataBaseHelper.getFitServicesWorkoutDao());
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /* access modifiers changed from: package-private */
    @Singleton
    @Provides
    public OmniTrainerDaoHelper provideOmniTrainerDaoHelper(DataBaseHelper dataBaseHelper) {
        try {
            return new OmniTrainerDaoHelper(dataBaseHelper.getOmniDao());
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
