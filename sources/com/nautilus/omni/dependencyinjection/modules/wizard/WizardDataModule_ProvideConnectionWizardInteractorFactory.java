package com.nautilus.omni.dependencyinjection.modules.wizard;

import com.nautilus.omni.appmodules.connectionwizard.domain.interactors.ConnectionWizardInteractorContract;
import com.nautilus.omni.dataaccess.OmniTrainerDaoHelper;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class WizardDataModule_ProvideConnectionWizardInteractorFactory implements Factory<ConnectionWizardInteractorContract> {
    static final /* synthetic */ boolean $assertionsDisabled = (!WizardDataModule_ProvideConnectionWizardInteractorFactory.class.desiredAssertionStatus());
    private final WizardDataModule module;
    private final Provider<OmniTrainerDaoHelper> omniTrainerDaoHelperProvider;

    public WizardDataModule_ProvideConnectionWizardInteractorFactory(WizardDataModule module2, Provider<OmniTrainerDaoHelper> omniTrainerDaoHelperProvider2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            if ($assertionsDisabled || omniTrainerDaoHelperProvider2 != null) {
                this.omniTrainerDaoHelperProvider = omniTrainerDaoHelperProvider2;
                return;
            }
            throw new AssertionError();
        }
        throw new AssertionError();
    }

    public ConnectionWizardInteractorContract get() {
        return (ConnectionWizardInteractorContract) Preconditions.checkNotNull(this.module.provideConnectionWizardInteractor(this.omniTrainerDaoHelperProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<ConnectionWizardInteractorContract> create(WizardDataModule module2, Provider<OmniTrainerDaoHelper> omniTrainerDaoHelperProvider2) {
        return new WizardDataModule_ProvideConnectionWizardInteractorFactory(module2, omniTrainerDaoHelperProvider2);
    }

    public static ConnectionWizardInteractorContract proxyProvideConnectionWizardInteractor(WizardDataModule instance, OmniTrainerDaoHelper omniTrainerDaoHelper) {
        return instance.provideConnectionWizardInteractor(omniTrainerDaoHelper);
    }
}
