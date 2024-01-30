package com.nautilus.omni.dependencyinjection.modules.wizard;

import android.content.Context;
import com.nautilus.omni.appmodules.connectionwizard.domain.interactors.ConnectionWizardInteractorContract;
import com.nautilus.omni.appmodules.connectionwizard.presenter.DevicesListPresenter;
import com.nautilus.omni.appmodules.connectionwizard.presenter.DevicesListPresenterContract;
import com.nautilus.omni.appmodules.connectionwizard.view.OmniTrainerListActivityContract;
import com.nautilus.omni.dependencyinjection.scopes.ActivityScope;
import dagger.Module;
import dagger.Provides;

@Module
public class DevicesListModule {
    OmniTrainerListActivityContract omniTrainerListActivity;

    public DevicesListModule(OmniTrainerListActivityContract omniTrainerListActivity2) {
        this.omniTrainerListActivity = omniTrainerListActivity2;
    }

    /* access modifiers changed from: package-private */
    @ActivityScope
    @Provides
    public OmniTrainerListActivityContract provideOmniTrainerListActivity() {
        return this.omniTrainerListActivity;
    }

    /* access modifiers changed from: package-private */
    @ActivityScope
    @Provides
    public DevicesListPresenterContract provideDevicesListPresenter(Context context, ConnectionWizardInteractorContract pConnectionWizardInteractor, OmniTrainerListActivityContract pOmniTrainerListActivity) {
        return new DevicesListPresenter(context, pConnectionWizardInteractor, pOmniTrainerListActivity);
    }
}
