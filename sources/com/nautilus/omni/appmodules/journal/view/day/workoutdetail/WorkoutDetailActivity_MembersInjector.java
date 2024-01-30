package com.nautilus.omni.appmodules.journal.view.day.workoutdetail;

import android.content.SharedPreferences;
import com.nautilus.omni.app.BaseActivity_MembersInjector;
import com.nautilus.omni.appmodules.journal.presenter.day.workoutdetail.WorkoutDetailPresenter;
import com.nautilus.omni.permissions.PermissionPresenter;
import com.nautilus.omni.util.LocationSettingsUtil;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class WorkoutDetailActivity_MembersInjector implements MembersInjector<WorkoutDetailActivity> {
    static final /* synthetic */ boolean $assertionsDisabled = (!WorkoutDetailActivity_MembersInjector.class.desiredAssertionStatus());
    private final Provider<LocationSettingsUtil> mLocationSettingsUtilProvider;
    private final Provider<PermissionPresenter> mPermissionPresenterProvider;
    private final Provider<SharedPreferences> mSettingsProvider;
    private final Provider<WorkoutDetailPresenter> mWorkoutDetailPresenterProvider;

    public WorkoutDetailActivity_MembersInjector(Provider<SharedPreferences> mSettingsProvider2, Provider<LocationSettingsUtil> mLocationSettingsUtilProvider2, Provider<WorkoutDetailPresenter> mWorkoutDetailPresenterProvider2, Provider<PermissionPresenter> mPermissionPresenterProvider2) {
        if ($assertionsDisabled || mSettingsProvider2 != null) {
            this.mSettingsProvider = mSettingsProvider2;
            if ($assertionsDisabled || mLocationSettingsUtilProvider2 != null) {
                this.mLocationSettingsUtilProvider = mLocationSettingsUtilProvider2;
                if ($assertionsDisabled || mWorkoutDetailPresenterProvider2 != null) {
                    this.mWorkoutDetailPresenterProvider = mWorkoutDetailPresenterProvider2;
                    if ($assertionsDisabled || mPermissionPresenterProvider2 != null) {
                        this.mPermissionPresenterProvider = mPermissionPresenterProvider2;
                        return;
                    }
                    throw new AssertionError();
                }
                throw new AssertionError();
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public static MembersInjector<WorkoutDetailActivity> create(Provider<SharedPreferences> mSettingsProvider2, Provider<LocationSettingsUtil> mLocationSettingsUtilProvider2, Provider<WorkoutDetailPresenter> mWorkoutDetailPresenterProvider2, Provider<PermissionPresenter> mPermissionPresenterProvider2) {
        return new WorkoutDetailActivity_MembersInjector(mSettingsProvider2, mLocationSettingsUtilProvider2, mWorkoutDetailPresenterProvider2, mPermissionPresenterProvider2);
    }

    public void injectMembers(WorkoutDetailActivity instance) {
        if (instance == null) {
            throw new NullPointerException("Cannot inject members into a null reference");
        }
        instance.mSettings = this.mSettingsProvider.get();
        BaseActivity_MembersInjector.injectMLocationSettingsUtil(instance, this.mLocationSettingsUtilProvider);
        instance.mWorkoutDetailPresenter = this.mWorkoutDetailPresenterProvider.get();
        instance.mPermissionPresenter = this.mPermissionPresenterProvider.get();
    }

    public static void injectMWorkoutDetailPresenter(WorkoutDetailActivity instance, Provider<WorkoutDetailPresenter> mWorkoutDetailPresenterProvider2) {
        instance.mWorkoutDetailPresenter = mWorkoutDetailPresenterProvider2.get();
    }

    public static void injectMPermissionPresenter(WorkoutDetailActivity instance, Provider<PermissionPresenter> mPermissionPresenterProvider2) {
        instance.mPermissionPresenter = mPermissionPresenterProvider2.get();
    }
}
