package com.nautilus.omni.dependencyinjection.modules.settings.underarmour;

import com.j256.ormlite.dao.Dao;
import com.nautilus.underarmourconnection.database.UnderArmourDatabaseHelper;
import com.nautilus.underarmourconnection.database.WorkoutTrack;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class UnderArmourModule_ProvideWorkoutTrackDaoFactory implements Factory<Dao<WorkoutTrack, Integer>> {
    static final /* synthetic */ boolean $assertionsDisabled = (!UnderArmourModule_ProvideWorkoutTrackDaoFactory.class.desiredAssertionStatus());
    private final UnderArmourModule module;
    private final Provider<UnderArmourDatabaseHelper> underArmourDatabaseHelperProvider;

    public UnderArmourModule_ProvideWorkoutTrackDaoFactory(UnderArmourModule module2, Provider<UnderArmourDatabaseHelper> underArmourDatabaseHelperProvider2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            if ($assertionsDisabled || underArmourDatabaseHelperProvider2 != null) {
                this.underArmourDatabaseHelperProvider = underArmourDatabaseHelperProvider2;
                return;
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public Dao<WorkoutTrack, Integer> get() {
        return (Dao) Preconditions.checkNotNull(this.module.provideWorkoutTrackDao(this.underArmourDatabaseHelperProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<Dao<WorkoutTrack, Integer>> create(UnderArmourModule module2, Provider<UnderArmourDatabaseHelper> underArmourDatabaseHelperProvider2) {
        return new UnderArmourModule_ProvideWorkoutTrackDaoFactory(module2, underArmourDatabaseHelperProvider2);
    }
}
