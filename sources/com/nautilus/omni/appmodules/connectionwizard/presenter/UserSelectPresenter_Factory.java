package com.nautilus.omni.appmodules.connectionwizard.presenter;

import android.content.Context;
import com.nautilus.omni.appmodules.connectionwizard.domain.interactors.ConnectionWizardInteractorContract;
import com.nautilus.omni.appmodules.connectionwizard.domain.receivers.WizardReceivers;
import com.nautilus.omni.appmodules.connectionwizard.view.SelectUserNumberActivityContract;
import dagger.MembersInjector;
import dagger.internal.Factory;
import dagger.internal.MembersInjectors;
import javax.inject.Provider;

public final class UserSelectPresenter_Factory implements Factory<UserSelectPresenter> {
    static final /* synthetic */ boolean $assertionsDisabled = (!UserSelectPresenter_Factory.class.desiredAssertionStatus());
    private final Provider<ConnectionWizardInteractorContract> connectionWizardInteractorProvider;
    private final Provider<Context> contextProvider;
    private final Provider<WizardReceivers> pWizardReceiversProvider;
    private final Provider<SelectUserNumberActivityContract> userNumberActivityProvider;
    private final MembersInjector<UserSelectPresenter> userSelectPresenterMembersInjector;

    public UserSelectPresenter_Factory(MembersInjector<UserSelectPresenter> userSelectPresenterMembersInjector2, Provider<Context> contextProvider2, Provider<ConnectionWizardInteractorContract> connectionWizardInteractorProvider2, Provider<SelectUserNumberActivityContract> userNumberActivityProvider2, Provider<WizardReceivers> pWizardReceiversProvider2) {
        if ($assertionsDisabled || userSelectPresenterMembersInjector2 != null) {
            this.userSelectPresenterMembersInjector = userSelectPresenterMembersInjector2;
            if ($assertionsDisabled || contextProvider2 != null) {
                this.contextProvider = contextProvider2;
                if ($assertionsDisabled || connectionWizardInteractorProvider2 != null) {
                    this.connectionWizardInteractorProvider = connectionWizardInteractorProvider2;
                    if ($assertionsDisabled || userNumberActivityProvider2 != null) {
                        this.userNumberActivityProvider = userNumberActivityProvider2;
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

    public UserSelectPresenter get() {
        return (UserSelectPresenter) MembersInjectors.injectMembers(this.userSelectPresenterMembersInjector, new UserSelectPresenter(this.contextProvider.get(), this.connectionWizardInteractorProvider.get(), this.userNumberActivityProvider.get(), this.pWizardReceiversProvider.get()));
    }

    public static Factory<UserSelectPresenter> create(MembersInjector<UserSelectPresenter> userSelectPresenterMembersInjector2, Provider<Context> contextProvider2, Provider<ConnectionWizardInteractorContract> connectionWizardInteractorProvider2, Provider<SelectUserNumberActivityContract> userNumberActivityProvider2, Provider<WizardReceivers> pWizardReceiversProvider2) {
        return new UserSelectPresenter_Factory(userSelectPresenterMembersInjector2, contextProvider2, connectionWizardInteractorProvider2, userNumberActivityProvider2, pWizardReceiversProvider2);
    }
}
