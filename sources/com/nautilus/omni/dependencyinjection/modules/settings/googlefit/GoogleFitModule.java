package com.nautilus.omni.dependencyinjection.modules.settings.googlefit;

import android.content.Context;
import com.nautilus.omni.appmodules.settings.googlefit.interactor.GoogleFitInteractor;
import com.nautilus.omni.appmodules.settings.googlefit.interactor.GoogleFitInteractorContract;
import com.nautilus.omni.appmodules.settings.googlefit.presenter.GoogleFitPresenter;
import com.nautilus.omni.appmodules.settings.googlefit.presenter.GoogleFitPresenterContract;
import com.nautilus.omni.appmodules.settings.googlefit.view.ConnectionGoogleFitActivity;
import com.nautilus.omni.dataaccess.GoogleFitDaoHelper;
import com.nautilus.omni.dataaccess.OmniTrainerDaoHelper;
import com.nautilus.omni.databasemanager.DataBaseHelper;
import com.nautilus.omni.dependencyinjection.scopes.ActivityScope;
import com.nautilus.omni.permissions.PermissionAction;
import com.nautilus.omni.permissions.SupportPermissionActionImpl;
import dagger.Module;
import dagger.Provides;
import java.sql.SQLException;

@Module
public class GoogleFitModule {
    ConnectionGoogleFitActivity mConnectionGoogleFitActivity;

    public GoogleFitModule(ConnectionGoogleFitActivity mConnectionGoogleFitActivity2) {
        this.mConnectionGoogleFitActivity = mConnectionGoogleFitActivity2;
    }

    /* access modifiers changed from: package-private */
    @ActivityScope
    @Provides
    public ConnectionGoogleFitActivity provideConnectionGoogleFitActivity() {
        return this.mConnectionGoogleFitActivity;
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
    public GoogleFitInteractorContract providegGoogleFitInteractor(GoogleFitDaoHelper googleFitDaoHelper, OmniTrainerDaoHelper omniTrainerDaoHelper) {
        return new GoogleFitInteractor(googleFitDaoHelper, omniTrainerDaoHelper);
    }

    /* access modifiers changed from: package-private */
    @ActivityScope
    @Provides
    public GoogleFitPresenterContract provideGoogleFitPresenter(Context context, GoogleFitInteractorContract googleFitInteractor) {
        return new GoogleFitPresenter(context, googleFitInteractor);
    }

    /* access modifiers changed from: package-private */
    @ActivityScope
    @Provides
    public PermissionAction providePermissionAction(ConnectionGoogleFitActivity mainActivity) {
        return new SupportPermissionActionImpl(mainActivity);
    }
}
