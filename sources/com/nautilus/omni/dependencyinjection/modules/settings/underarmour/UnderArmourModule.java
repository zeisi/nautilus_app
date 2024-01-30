package com.nautilus.omni.dependencyinjection.modules.settings.underarmour;

import android.content.Context;
import com.j256.ormlite.dao.Dao;
import com.nautilus.omni.appmodules.settings.underarmour.util.UnderArmourConstants;
import com.nautilus.underarmourconnection.database.UnderArmourDatabaseHelper;
import com.nautilus.underarmourconnection.database.WorkoutTrack;
import com.nautilus.underarmourconnection.database.WorkoutTrackDaoHelper;
import com.nautilus.underarmourconnection.services.authentication.AuthenticationService;
import com.nautilus.underarmourconnection.services.workouts.WorkoutService;
import dagger.Module;
import dagger.Provides;
import java.sql.SQLException;
import javax.inject.Singleton;

@Module
public class UnderArmourModule {
    @Singleton
    @Provides
    public UnderArmourDatabaseHelper providesUnderArmourDatabaseHelper(Context context) {
        UnderArmourDatabaseHelper underArmourDatabaseHelper = UnderArmourDatabaseHelper.getHelper(context);
        underArmourDatabaseHelper.getWritableDatabase();
        return underArmourDatabaseHelper;
    }

    @Singleton
    @Provides
    public Dao<WorkoutTrack, Integer> provideWorkoutTrackDao(UnderArmourDatabaseHelper underArmourDatabaseHelper) {
        try {
            return underArmourDatabaseHelper.getWorkoutTrackDao();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Singleton
    @Provides
    public WorkoutTrackDaoHelper provideWorkoutTrackDaoHelper(Dao<WorkoutTrack, Integer> workoutTrackDao) {
        return new WorkoutTrackDaoHelper(workoutTrackDao);
    }

    @Singleton
    @Provides
    public AuthenticationService provideUnderArmourAuthenticationService(Context context) {
        return new AuthenticationService(context, UnderArmourConstants.CLIENT_KEY, UnderArmourConstants.CLIENT_SECRET);
    }

    @Singleton
    @Provides
    public WorkoutService provideUnderArmourWorkoutService(Context context, WorkoutTrackDaoHelper workoutDaoHelper) {
        return new WorkoutService(context, UnderArmourConstants.CLIENT_KEY, UnderArmourConstants.CLIENT_SECRET, workoutDaoHelper);
    }
}
