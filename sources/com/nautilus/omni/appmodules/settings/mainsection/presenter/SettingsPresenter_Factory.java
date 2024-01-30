package com.nautilus.omni.appmodules.settings.mainsection.presenter;

import android.content.Context;
import com.nautilus.omni.appmodules.settings.mainsection.interactor.SettingsInteractorContract;
import com.nautilus.omni.appmodules.settings.mainsection.view.SettingsViewContract;
import dagger.MembersInjector;
import dagger.internal.Factory;
import dagger.internal.MembersInjectors;
import javax.inject.Provider;

public final class SettingsPresenter_Factory implements Factory<SettingsPresenter> {
    static final /* synthetic */ boolean $assertionsDisabled = (!SettingsPresenter_Factory.class.desiredAssertionStatus());
    private final Provider<Context> contextProvider;
    private final Provider<SettingsViewContract> iSettingsActivityProvider;
    private final Provider<SettingsInteractorContract> pSettingsInteractorProvider;
    private final MembersInjector<SettingsPresenter> settingsPresenterMembersInjector;

    public SettingsPresenter_Factory(MembersInjector<SettingsPresenter> settingsPresenterMembersInjector2, Provider<Context> contextProvider2, Provider<SettingsViewContract> iSettingsActivityProvider2, Provider<SettingsInteractorContract> pSettingsInteractorProvider2) {
        if ($assertionsDisabled || settingsPresenterMembersInjector2 != null) {
            this.settingsPresenterMembersInjector = settingsPresenterMembersInjector2;
            if ($assertionsDisabled || contextProvider2 != null) {
                this.contextProvider = contextProvider2;
                if ($assertionsDisabled || iSettingsActivityProvider2 != null) {
                    this.iSettingsActivityProvider = iSettingsActivityProvider2;
                    if ($assertionsDisabled || pSettingsInteractorProvider2 != null) {
                        this.pSettingsInteractorProvider = pSettingsInteractorProvider2;
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

    public SettingsPresenter get() {
        return (SettingsPresenter) MembersInjectors.injectMembers(this.settingsPresenterMembersInjector, new SettingsPresenter(this.contextProvider.get(), this.iSettingsActivityProvider.get(), this.pSettingsInteractorProvider.get()));
    }

    public static Factory<SettingsPresenter> create(MembersInjector<SettingsPresenter> settingsPresenterMembersInjector2, Provider<Context> contextProvider2, Provider<SettingsViewContract> iSettingsActivityProvider2, Provider<SettingsInteractorContract> pSettingsInteractorProvider2) {
        return new SettingsPresenter_Factory(settingsPresenterMembersInjector2, contextProvider2, iSettingsActivityProvider2, pSettingsInteractorProvider2);
    }
}
