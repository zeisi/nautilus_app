package com.nautilus.omni.dependencyinjection.modules.settings.underarmour;

import com.j256.ormlite.dao.Dao;
import com.nautilus.underarmourconnection.database.WorkoutTrack;
import com.nautilus.underarmourconnection.database.WorkoutTrackDaoHelper;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class UnderArmourModule_ProvideWorkoutTrackDaoHelperFactory implements Factory<WorkoutTrackDaoHelper> {
    static final /* synthetic */ boolean $assertionsDisabled = (!UnderArmourModule_ProvideWorkoutTrackDaoHelperFactory.class.desiredAssertionStatus());
    private final UnderArmourModule module;
    private final Provider<Dao<WorkoutTrack, Integer>> workoutTrackDaoProvider;

    public UnderArmourModule_ProvideWorkoutTrackDaoHelperFactory(UnderArmourModule module2, Provider<Dao<WorkoutTrack, Integer>> workoutTrackDaoProvider2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            if ($assertionsDisabled || workoutTrackDaoProvider2 != null) {
                this.workoutTrackDaoProvider = workoutTrackDaoProvider2;
                return;
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public WorkoutTrackDaoHelper get() {
        return (WorkoutTrackDaoHelper) Preconditions.checkNotNull(this.module.provideWorkoutTrackDaoHelper(this.workoutTrackDaoProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<WorkoutTrackDaoHelper> create(UnderArmourModule module2, Provider<Dao<WorkoutTrack, Integer>> workoutTrackDaoProvider2) {
        return new UnderArmourModule_ProvideWorkoutTrackDaoHelperFactory(module2, workoutTrackDaoProvider2);
    }
}
