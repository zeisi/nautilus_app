package com.nautilus.omni.dependencyinjection.modules.settings;

import android.content.Context;
import com.nautilus.omni.appmodules.settings.mainsection.interactor.SettingsInteractorContract;
import com.nautilus.omni.appmodules.settings.mainsection.presenter.SettingsPresenterContract;
import com.nautilus.omni.appmodules.settings.mainsection.view.SettingsViewContract;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class SettingsModule_ProvideSettingsPresenterFactory implements Factory<SettingsPresenterContract> {
    static final /* synthetic */ boolean $assertionsDisabled = (!SettingsModule_ProvideSettingsPresenterFactory.class.desiredAssertionStatus());
    private final Provider<Context> contextProvider;
    private final Provider<SettingsViewContract> iSettingsActivityProvider;
    private final SettingsModule module;
    private final Provider<SettingsInteractorContract> settingsInteractorProvider;

    public SettingsModule_ProvideSettingsPresenterFactory(SettingsModule module2, Provider<Context> contextProvider2, Provider<SettingsViewContract> iSettingsActivityProvider2, Provider<SettingsInteractorContract> settingsInteractorProvider2) {
        if ($assertionsDisabled || module2 != null) {
            this.module = module2;
            if ($assertionsDisabled || contextProvider2 != null) {
                this.contextProvider = contextProvider2;
                if ($assertionsDisabled || iSettingsActivityProvider2 != null) {
                    this.iSettingsActivityProvider = iSettingsActivityProvider2;
                    if ($assertionsDisabled || settingsInteractorProvider2 != null) {
                        this.settingsInteractorProvider = settingsInteractorProvider2;
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

    public SettingsPresenterContract get() {
        return (SettingsPresenterContract) Preconditions.checkNotNull(this.module.provideSettingsPresenter(this.contextProvider.get(), this.iSettingsActivityProvider.get(), this.settingsInteractorProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static Factory<SettingsPresenterContract> create(SettingsModule module2, Provider<Context> contextProvider2, Provider<SettingsViewContract> iSettingsActivityProvider2, Provider<SettingsInteractorContract> settingsInteractorProvider2) {
        return new SettingsModule_ProvideSettingsPresenterFactory(module2, contextProvider2, iSettingsActivityProvider2, settingsInteractorProvider2);
    }

    public static SettingsPresenterContract proxyProvideSettingsPresenter(SettingsModule instance, Context context, SettingsViewContract iSettingsActivity, SettingsInteractorContract settingsInteractor) {
        return instance.provideSettingsPresenter(context, iSettingsActivity, settingsInteractor);
    }
}
