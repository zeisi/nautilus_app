package com.nautilus.omni.dependencyinjection.modules.wizard;

import android.content.Context;
import com.nautilus.omni.appmodules.connectionwizard.domain.interactors.ConnectionWizardInteractorContract;
import com.nautilus.omni.appmodules.connectionwizard.domain.receivers.WizardReceivers;
import com.nautilus.omni.appmodules.connectionwizard.presenter.UserSelectPresenterContract;
import com.nautilus.omni.appmodules.connectionwizard.view.SelectUserNumberActivityContract;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class UserSelectModule_ProvideWizardPresenterFactory implements Factory<UserSelectPresenterContract> {
    static final /* synthetic */ boolean $assertionsDisabled = (!UserSelectModule_ProvideWizardPresenterFactory.class.desiredAssertionStatus());
    private final Provider<Context> contextProvider;
    private final Provider<ConnectionWizardInteractorContract> iConnectionWizardInteractorProvider;
    private final UserSelectModule module;
    private final Provider<SelectUserNumberActivityContract> userNumberActivityProvider;
    private final Provider<WizardReceivers> wizardReceiversProvider;

    public UserSelectModule_ProvideWizardPresenterFactory(UserSelectModule module2, Provider<Context> contextProvider2, Provider<SelectUserNumberActivityContract> userNumberActivityProvider2, Provider<ConnectionWizardInteractorContract> iConnectionWizardInteractorProvider2, Provider<WizardReceivers> wizardReceiversProvider2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            if ($assertionsDisabled || contextProvider2 != null) {
                this.contextProvider = contextProvider2;
                if ($assertionsDisabled || userNumberActivityProvider2 != null) {
                    this.userNumberActivityProvider = userNumberActivityProvider2;
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

    public UserSelectPresenterContract get() {
        return (UserSelectPresenterContract) Preconditions.checkNotNull(this.module.provideWizardPresenter(this.contextProvider.get(), this.userNumberActivityProvider.get(), this.iConnectionWizardInteractorProvider.get(), this.wizardReceiversProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<UserSelectPresenterContract> create(UserSelectModule module2, Provider<Context> contextProvider2, Provider<SelectUserNumberActivityContract> userNumberActivityProvider2, Provider<ConnectionWizardInteractorContract> iConnectionWizardInteractorProvider2, Provider<WizardReceivers> wizardReceiversProvider2) {
        return new UserSelectModule_ProvideWizardPresenterFactory(module2, contextProvider2, userNumberActivityProvider2, iConnectionWizardInteractorProvider2, wizardReceiversProvider2);
    }

    public static UserSelectPresenterContract proxyProvideWizardPresenter(UserSelectModule instance, Context context, SelectUserNumberActivityContract userNumberActivity, ConnectionWizardInteractorContract iConnectionWizardInteractor, WizardReceivers wizardReceivers) {
        return instance.provideWizardPresenter(context, userNumberActivity, iConnectionWizardInteractor, wizardReceivers);
    }
}
