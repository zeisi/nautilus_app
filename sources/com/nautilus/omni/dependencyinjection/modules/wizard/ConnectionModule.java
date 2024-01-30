package com.nautilus.omni.dependencyinjection.modules.wizard;

import android.content.Context;
import com.nautilus.omni.appmodules.connectionwizard.domain.interactors.ConnectionWizardInteractorContract;
import com.nautilus.omni.appmodules.connectionwizard.domain.receivers.WizardReceivers;
import com.nautilus.omni.appmodules.connectionwizard.presenter.WizardPresenter;
import com.nautilus.omni.appmodules.connectionwizard.presenter.WizardPresenterContract;
import com.nautilus.omni.appmodules.connectionwizard.view.ConnectionWizardActivity;
import com.nautilus.omni.dependencyinjection.scopes.ActivityScope;
import com.nautilus.omni.permissions.PermissionAction;
import com.nautilus.omni.permissions.PermissionPresenter;
import com.nautilus.omni.permissions.SupportPermissionActionImpl;
import dagger.Module;
import dagger.Provides;

@Module
public class ConnectionModule {
    ConnectionWizardActivity connectionWizardActivity;

    public ConnectionModule(ConnectionWizardActivity connectionWizardActivity2) {
        this.connectionWizardActivity = connectionWizardActivity2;
    }

    /* access modifiers changed from: package-private */
    @ActivityScope
    @Provides
    public ConnectionWizardActivity provideConnectionWizardActivity() {
        return this.connectionWizardActivity;
    }

    /* access modifiers changed from: package-private */
    @ActivityScope
    @Provides
    public WizardPresenterContract provideWizardPresenter(Context context, ConnectionWizardActivity connectionWizardActivity2, ConnectionWizardInteractorContract iConnectionWizardInteractor, WizardReceivers wizardReceivers) {
        return new WizardPresenter(context, connectionWizardActivity2, iConnectionWizardInteractor, wizardReceivers);
    }

    /* access modifiers changed from: package-private */
    @ActivityScope
    @Provides
    public PermissionAction providePermissionAction(ConnectionWizardActivity connectionWizardActivity2) {
        return new SupportPermissionActionImpl(connectionWizardActivity2);
    }

    /* access modifiers changed from: package-private */
    @ActivityScope
    @Provides
    public PermissionPresenter providePermissionPresenter(PermissionAction permissionAction, ConnectionWizardActivity connectionWizardActivity2) {
        return new PermissionPresenter(permissionAction, connectionWizardActivity2);
    }
}
