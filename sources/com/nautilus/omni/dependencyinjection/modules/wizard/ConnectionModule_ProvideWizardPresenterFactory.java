package com.nautilus.omni.dependencyinjection.modules.wizard;

import android.content.Context;
import com.nautilus.omni.appmodules.connectionwizard.domain.interactors.ConnectionWizardInteractorContract;
import com.nautilus.omni.appmodules.connectionwizard.domain.receivers.WizardReceivers;
import com.nautilus.omni.appmodules.connectionwizard.presenter.WizardPresenterContract;
import com.nautilus.omni.appmodules.connectionwizard.view.ConnectionWizardActivity;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class ConnectionModule_ProvideWizardPresenterFactory implements Factory<WizardPresenterContract> {
    static final /* synthetic */ boolean $assertionsDisabled = (!ConnectionModule_ProvideWizardPresenterFactory.class.desiredAssertionStatus());
    private final Provider<ConnectionWizardActivity> connectionWizardActivityProvider;
    private final Provider<Context> contextProvider;
    private final Provider<ConnectionWizardInteractorContract> iConnectionWizardInteractorProvider;
    private final ConnectionModule module;
    private final Provider<WizardReceivers> wizardReceiversProvider;

    public ConnectionModule_ProvideWizardPresenterFactory(ConnectionModule module2, Provider<Context> contextProvider2, Provider<ConnectionWizardActivity> connectionWizardActivityProvider2, Provider<ConnectionWizardInteractorContract> iConnectionWizardInteractorProvider2, Provider<WizardReceivers> wizardReceiversProvider2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            if ($assertionsDisabled || contextProvider2 != null) {
                this.contextProvider = contextProvider2;
                if ($assertionsDisabled || connectionWizardActivityProvider2 != null) {
                    this.connectionWizardActivityProvider = connectionWizardActivityProvider2;
                    if ($assertionsDisabled || iConnectionWizardInteractorProvider2 != null) {
                        this.iConnectionWizardInteractorProvider = iConnectionWizardInteractorProvider2;
                        if ($assertionsDisabled || wizardReceiversProvider2 != null) {
                            this.wizardReceiversProvider = wizardReceiversProvider2;
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
        throw new AssertionError();
    }

    public WizardPresenterContract get() {
        return (WizardPresenterContract) Preconditions.checkNotNull(this.module.provideWizardPresenter(this.contextProvider.get(), this.connectionWizardActivityProvider.get(), this.iConnectionWizardInteractorProvider.get(), this.wizardReceiversProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<WizardPresenterContract> create(ConnectionModule module2, Provider<Context> contextProvider2, Provider<ConnectionWizardActivity> connectionWizardActivityProvider2, Provider<ConnectionWizardInteractorContract> iConnectionWizardInteractorProvider2, Provider<WizardReceivers> wizardReceiversProvider2) {
        return new ConnectionModule_ProvideWizardPresenterFactory(module2, contextProvider2, connectionWizardActivityProvider2, iConnectionWizardInteractorProvider2, wizardReceiversProvider2);
    }

    public static WizardPresenterContract proxyProvideWizardPresenter(ConnectionModule instance, Context context, ConnectionWizardActivity connectionWizardActivity, ConnectionWizardInteractorContract iConnectionWizardInteractor, WizardReceivers wizardReceivers) {
        return instance.provideWizardPresenter(context, connectionWizardActivity, iConnectionWizardInteractor, wizardReceivers);
    }
}
