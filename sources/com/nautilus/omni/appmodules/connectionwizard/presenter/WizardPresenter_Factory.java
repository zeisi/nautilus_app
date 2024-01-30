package com.nautilus.omni.appmodules.connectionwizard.presenter;

import android.content.Context;
import com.nautilus.omni.appmodules.connectionwizard.domain.interactors.ConnectionWizardInteractorContract;
import com.nautilus.omni.appmodules.connectionwizard.domain.receivers.WizardReceivers;
import com.nautilus.omni.appmodules.connectionwizard.view.ConnectionWizardActivity;
import dagger.MembersInjector;
import dagger.internal.Factory;
import dagger.internal.MembersInjectors;
import javax.inject.Provider;

public final class WizardPresenter_Factory implements Factory<WizardPresenter> {
    static final /* synthetic */ boolean $assertionsDisabled = (!WizardPresenter_Factory.class.desiredAssertionStatus());
    private final Provider<ConnectionWizardActivity> connectionWizardActivityProvider;
    private final Provider<ConnectionWizardInteractorContract> connectionWizardInteractorProvider;
    private final Provider<Context> contextProvider;
    private final Provider<WizardReceivers> pWizardReceiversProvider;
    private final MembersInjector<WizardPresenter> wizardPresenterMembersInjector;

    public WizardPresenter_Factory(MembersInjector<WizardPresenter> wizardPresenterMembersInjector2, Provider<Context> contextProvider2, Provider<ConnectionWizardActivity> connectionWizardActivityProvider2, Provider<ConnectionWizardInteractorContract> connectionWizardInteractorProvider2, Provider<WizardReceivers> pWizardReceiversProvider2) {
        if ($assertionsDisabled || wizardPresenterMembersInjector2 != null) {
            this.wizardPresenterMembersInjector = wizardPresenterMembersInjector2;
            if ($assertionsDisabled || contextProvider2 != null) {
                this.contextProvider = contextProvider2;
                if ($assertionsDisabled || connectionWizardActivityProvider2 != null) {
                    this.connectionWizardActivityProvider = connectionWizardActivityProvider2;
                    if ($assertionsDisabled || connectionWizardInteractorProvider2 != null) {
                        this.connectionWizardInteractorProvider = connectionWizardInteractorProvider2;
                        if ($assertionsDisabled || pWizardReceiversProvider2 != null) {
                            this.pWizardReceiversProvider = pWizardReceiversProvider2;
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

    public WizardPresenter get() {
        return (WizardPresenter) MembersInjectors.injectMembers(this.wizardPresenterMembersInjector, new WizardPresenter(this.contextProvider.get(), this.connectionWizardActivityProvider.get(), this.connectionWizardInteractorProvider.get(), this.pWizardReceiversProvider.get()));
    }

    public static Factory<WizardPresenter> create(MembersInjector<WizardPresenter> wizardPresenterMembersInjector2, Provider<Context> contextProvider2, Provider<ConnectionWizardActivity> connectionWizardActivityProvider2, Provider<ConnectionWizardInteractorContract> connectionWizardInteractorProvider2, Provider<WizardReceivers> pWizardReceiversProvider2) {
        return new WizardPresenter_Factory(wizardPresenterMembersInjector2, contextProvider2, connectionWizardActivityProvider2, connectionWizardInteractorProvider2, pWizardReceiversProvider2);
    }
}
