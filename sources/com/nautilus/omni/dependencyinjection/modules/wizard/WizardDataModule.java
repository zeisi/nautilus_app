package com.nautilus.omni.dependencyinjection.modules.wizard;

import android.content.Context;
import com.nautilus.omni.appmodules.connectionwizard.domain.interactors.ConnectionWizardInteractor;
import com.nautilus.omni.appmodules.connectionwizard.domain.interactors.ConnectionWizardInteractorContract;
import com.nautilus.omni.appmodules.connectionwizard.domain.receivers.WizardReceivers;
import com.nautilus.omni.dataaccess.OmniTrainerDaoHelper;
import com.nautilus.omni.dependencyinjection.scopes.ActivityScope;
import dagger.Module;
import dagger.Provides;

@Module
public class WizardDataModule {
    /* access modifiers changed from: package-private */
    @ActivityScope
    @Provides
    public ConnectionWizardInteractorContract provideConnectionWizardInteractor(OmniTrainerDaoHelper omniTrainerDaoHelper) {
        return new ConnectionWizardInteractor(omniTrainerDaoHelper);
    }

    /* access modifiers changed from: package-private */
    @ActivityScope
    @Provides
    public WizardReceivers provideReceivers(Context context) {
        return new WizardReceivers(context);
    }
}
