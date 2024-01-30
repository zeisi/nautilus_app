package com.nautilus.omni.dependencyinjection.modules.wizard;

import android.content.Context;
import com.nautilus.omni.appmodules.connectionwizard.domain.interactors.ConnectionWizardInteractorContract;
import com.nautilus.omni.appmodules.connectionwizard.presenter.DevicesListPresenterContract;
import com.nautilus.omni.appmodules.connectionwizard.view.OmniTrainerListActivityContract;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class DevicesListModule_ProvideDevicesListPresenterFactory implements Factory<DevicesListPresenterContract> {
    static final /* synthetic */ boolean $assertionsDisabled = (!DevicesListModule_ProvideDevicesListPresenterFactory.class.desiredAssertionStatus());
    private final Provider<Context> contextProvider;
    private final DevicesListModule module;
    private final Provider<ConnectionWizardInteractorContract> pConnectionWizardInteractorProvider;
    private final Provider<OmniTrainerListActivityContract> pOmniTrainerListActivityProvider;

    public DevicesListModule_ProvideDevicesListPresenterFactory(DevicesListModule module2, Provider<Context> contextProvider2, Provider<ConnectionWizardInteractorContract> pConnectionWizardInteractorProvider2, Provider<OmniTrainerListActivityContract> pOmniTrainerListActivityProvider2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            if ($assertionsDisabled || contextProvider2 != null) {
                this.contextProvider = contextProvider2;
                if ($assertionsDisabled || pConnectionWizardInteractorProvider2 != null) {
                    this.pConnectionWizardInteractorProvider = pConnectionWizardInteractorProvider2;
                    if ($assertionsDisabled || pOmniTrainerListActivityProvider2 != null) {
                        this.pOmniTrainerListActivityProvider = pOmniTrainerListActivityProvider2;
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

    public DevicesListPresenterContract get() {
        return (DevicesListPresenterContract) Preconditions.checkNotNull(this.module.provideDevicesListPresenter(this.contextProvider.get(), this.pConnectionWizardInteractorProvider.get(), this.pOmniTrainerListActivityProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<DevicesListPresenterContract> create(DevicesListModule module2, Provider<Context> contextProvider2, Provider<ConnectionWizardInteractorContract> pConnectionWizardInteractorProvider2, Provider<OmniTrainerListActivityContract> pOmniTrainerListActivityProvider2) {
        return new DevicesListModule_ProvideDevicesListPresenterFactory(module2, contextProvider2, pConnectionWizardInteractorProvider2, pOmniTrainerListActivityProvider2);
    }

    public static DevicesListPresenterContract proxyProvideDevicesListPresenter(DevicesListModule instance, Context context, ConnectionWizardInteractorContract pConnectionWizardInteractor, OmniTrainerListActivityContract pOmniTrainerListActivity) {
        return instance.provideDevicesListPresenter(context, pConnectionWizardInteractor, pOmniTrainerListActivity);
    }
}
