package com.nautilus.omni.dependencyinjection.modules.settings;

import android.content.Context;
import com.nautilus.omni.appmodules.settings.mainsection.interactor.SettingsInteractor;
import com.nautilus.omni.appmodules.settings.mainsection.interactor.SettingsInteractorContract;
import com.nautilus.omni.appmodules.settings.mainsection.presenter.SettingsPresenter;
import com.nautilus.omni.appmodules.settings.mainsection.presenter.SettingsPresenterContract;
import com.nautilus.omni.appmodules.settings.mainsection.view.SettingsActivity;
import com.nautilus.omni.appmodules.settings.mainsection.view.SettingsViewContract;
import com.nautilus.omni.dataaccess.GoogleFitDaoHelper;
import com.nautilus.omni.dataaccess.ProductDaoHelper;
import com.nautilus.omni.dataaccess.UserDaoHelper;
import com.nautilus.omni.databasemanager.DataBaseHelper;
import com.nautilus.omni.dependencyinjection.scopes.ActivityScope;
import com.nautilus.omni.permissions.PermissionAction;
import com.nautilus.omni.permissions.PermissionPresenter;
import com.nautilus.omni.permissions.SupportPermissionActionImpl;
import dagger.Module;
import dagger.Provides;
import java.sql.SQLException;

@Module
public class SettingsModule {
    SettingsActivity iSettingsActivity;

    public SettingsModule(SettingsActivity iSettingsActivity2) {
        this.iSettingsActivity = iSettingsActivity2;
    }

    /* access modifiers changed from: package-private */
    @ActivityScope
    @Provides
    public SettingsViewContract provideISettingsActivity() {
        return this.iSettingsActivity;
    }

    /* access modifiers changed from: package-private */
    @ActivityScope
    @Provides
    public SettingsActivity provideSettingsActivity() {
        return this.iSettingsActivity;
    }

    /* access modifiers changed from: package-private */
    @ActivityScope
    @Provides
    public GoogleFitDaoHelper provideGoogleFitDaoHelper(DataBaseHelper dataBaseHelper) {
        try {
            return new GoogleFitDaoHelper(dataBaseHelper.getFitServicesWorkoutDao());
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /* access modifiers changed from: package-private */
    @ActivityScope
    @Provides
    public SettingsInteractorContract provideSettingsInteractor(UserDaoHelper userDaoHelper, ProductDaoHelper productDaoHelper) {
        return new SettingsInteractor(userDaoHelper, productDaoHelper);
    }

    /* access modifiers changed from: package-private */
    @ActivityScope
    @Provides
    public SettingsPresenterContract provideSettingsPresenter(Context context, SettingsViewContract iSettingsActivity2, SettingsInteractorContract settingsInteractor) {
        return new SettingsPresenter(context, iSettingsActivity2, settingsInteractor);
    }

    /* access modifiers changed from: package-private */
    @ActivityScope
    @Provides
    public PermissionAction providePermissionAction(SettingsActivity awardTypesActivity) {
        return new SupportPermissionActionImpl(awardTypesActivity);
    }

    /* access modifiers changed from: package-private */
    @ActivityScope
    @Provides
    public PermissionPresenter providePermissionPresenter(PermissionAction permissionAction, SettingsActivity awardTypesActivity) {
        return new PermissionPresenter(permissionAction, awardTypesActivity);
    }
}
