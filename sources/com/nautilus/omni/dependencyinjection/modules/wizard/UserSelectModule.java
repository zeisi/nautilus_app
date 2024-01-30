package com.nautilus.omni.dependencyinjection.modules.wizard;

import android.content.Context;
import com.nautilus.omni.appmodules.connectionwizard.domain.interactors.ConnectionWizardInteractorContract;
import com.nautilus.omni.appmodules.connectionwizard.domain.receivers.WizardReceivers;
import com.nautilus.omni.appmodules.connectionwizard.presenter.UserSelectPresenter;
import com.nautilus.omni.appmodules.connectionwizard.presenter.UserSelectPresenterContract;
import com.nautilus.omni.appmodules.connectionwizard.view.SelectUserNumberActivityContract;
import com.nautilus.omni.dependencyinjection.scopes.ActivityScope;
import dagger.Module;
import dagger.Provides;

@Module
public class UserSelectModule {
    SelectUserNumberActivityContract selectUserNumberActivity;

    public UserSelectModule(SelectUserNumberActivityContract selectUserNumberActivity2) {
        this.selectUserNumberActivity = selectUserNumberActivity2;
    }

    /* access modifiers changed from: package-private */
    @ActivityScope
    @Provides
    public SelectUserNumberActivityContract provideISelectUserNumberActivity() {
        return this.selectUserNumberActivity;
    }

    /* access modifiers changed from: package-private */
    @ActivityScope
    @Provides
    public UserSelectPresenterContract provideWizardPresenter(Context context, SelectUserNumberActivityContract userNumberActivity, ConnectionWizardInteractorContract iConnectionWizardInteractor, WizardReceivers wizardReceivers) {
        return new UserSelectPresenter(context, iConnectionWizardInteractor, userNumberActivity, wizardReceivers);
    }
}
